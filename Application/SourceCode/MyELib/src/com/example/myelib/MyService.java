package com.example.myelib;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyService extends Service {
	JSONArray jarray;
	JSONObject jObject;  
	String Url;
	IssuedBookDetailData ibkdtl;
	Global_Application ga;
	String stud_libcard_no;
	int n=0;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "inside service", Toast.LENGTH_LONG).show();
		 ga=(Global_Application)(getApplicationContext());
		 Bundle bd=intent.getExtras();
		 stud_libcard_no=bd.get("LibCardNo").toString(); 
		Status_Issue_Detail();
		//Intent i=new Intent(getApplicationContext(),MyNewActivity.class);
		//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//startActivity(i);
		return super.onStartCommand(intent, flags, startId);
	}
	public void Status_Issue_Detail()   
	{	                    
		Url=ga.getUrl()+"Status_Issue_Detail";
		stud_libcard_no=ga.getLibCardID();
		Handler handler = new Handler()   
		{   
			public void handleMessage(Message message) 
			{               
					switch (message.what)          
					{
							case HttpConnection.DID_START: 
							{
								//Toast.makeText(getApplicationContext(),"start==> ", Toast.LENGTH_LONG).show();
								break;     
							}                              
							case HttpConnection.DID_SUCCEED:      
							{                        
								try
								{    
								 String response = message.obj.toString();								
								 jObject = new JSONObject(response);
									String ResponseCode = jObject.getString("ResponseCode");
									//Toast.makeText(getApplicationContext(),"inside succeess "+response, Toast.LENGTH_LONG).show();
									if (ResponseCode.compareTo("1") == 0)
									{
										jarray = jObject.getJSONArray("IssueList");
										for (int i = 0; i < jarray.length(); i++) 
										{
																			     
											ibkdtl=new IssuedBookDetailData();
							
											ibkdtl.setBook_ID(jarray.getJSONObject(i).getString("bk_id"));
											ibkdtl.setBook_Seq_ID(jarray.getJSONObject(i).getString("bk_seq_id"));
											ibkdtl.setBook_Name(jarray.getJSONObject(i).getString("bk_name"));
											
											
											ibkdtl.setBook_Author(jarray.getJSONObject(i).getString("bk_author"));
											ibkdtl.setBook_Publisher(jarray.getJSONObject(i).getString("bk_publisher"));
											ibkdtl.setIssue_Date(jarray.getJSONObject(i).getString("issue_date"));
											ibkdtl.setReturn_Date(jarray.getJSONObject(i).getString("return_date"));
											                           									

										      
											//String dt1="07/29/2013 11:12:13 AM";
											String dt1=ibkdtl.getReturn_Date();
											  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
										        String date = format.format(Date.parse(dt1));
										      //  Toast.makeText(getApplicationContext(), "after conveting: "+date, Toast.LENGTH_LONG).show();
									       	String dd=date.substring(0, 2);       					
									       	String mm=date.substring(3, 5);				
									        String yyyy=date.substring(6,10);
									        
									      
									       
									        System.out.println("\n\nDD-MM-YYYY"+dd+"-"+mm+"-"+yyyy);
									     //   Toast.makeText(getApplicationContext(), "\n\nDD-MM-YYYY"+dd+"-"+mm+"-"+yyyy, Toast.LENGTH_LONG).show();
									        Calendar calendar1 = Calendar.getInstance();
										    Calendar calendar2 = Calendar.getInstance();
														      //calendar2.set(2012, 04, 02)
										    calendar1.set(Integer.parseInt(yyyy),Integer.parseInt(mm)-1,Integer.parseInt(dd));
										    System.out.println("\n\nDATE1"+calendar1);
											System.out.println("\n\nDATE3"+calendar2);					     				
										     				
										     long milsecs1= calendar1.getTimeInMillis();
										     long milsecs2 = calendar2.getTimeInMillis();
										     long diff = milsecs2 - milsecs1;
										     long dsecs = diff / 1000;
										     long dminutes = diff / (60 * 1000);
										     long dhours = diff / (60 * 60 * 1000);
										     long ddays = diff / (24 * 60 * 60 * 1000);
										     System.out.println("\n\nYour Day Difference="+ddays); 
										     n++;
										  //   Toast.makeText(getApplicationContext(), "Your Day Difference="+ddays, Toast.LENGTH_LONG).show();
										    
										      if(ddays<=2)
										     {
										    			 
										    	  NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
												     String ttl=n+"Book Return Alert";
											    		Notification notification = new Notification(R.drawable.notification_icon1,"Book Return Alert", System.currentTimeMillis());
											    		PendingIntent contentIntent = PendingIntent.getActivity(MyService.this, 0, new Intent(MyService.this, MyService.class), 0);
											    		notification.setLatestEventInfo(MyService.this,"Book Return Alert","Book return date is coming", contentIntent);
											    		notificationManager.notify(1, notification);  		
																							  
												//  Toast.makeText(getApplicationContext(), "Return book: "+ibkdtl.getBook_Name(), Toast.LENGTH_LONG).show();
										    	 System.out.println("\n\nRETURN UR BOOK");	
										    	// Toast.makeText(getApplicationContext(), "RETURN UR BOOK", Toast.LENGTH_LONG).show();
										    	  
										      }
										}                   
										
									
									}
									 if (ResponseCode.compareTo("2") == 0)
									  {
										  Toast.makeText(getApplicationContext(),"Some server side error occured", Toast.LENGTH_LONG).show();
									  }
									 if (ResponseCode.compareTo("3") == 0)
									  {
										Toast.makeText(getApplicationContext(),"Currenly No Book Issued ",Toast.LENGTH_LONG).show();
									  }
								            
								}    
								 catch (Exception e)
							     {
							        	 Toast.makeText(getApplicationContext(),"Sorry some ERROR occured: "+e.getMessage(), Toast.LENGTH_LONG).show();
								 } 
								                     	   
								                  
								break;
							}   
							case HttpConnection.DID_ERROR: 
							{
								//Toast.makeText(getApplicationContext(),"err ", Toast.LENGTH_LONG).show();
								Exception e = (Exception) message.obj;
								e.printStackTrace();
								String err="Connection failed.";
							
									StackTraceElement arr[]= e.getStackTrace();
									String s="";
									for (int i=0;i<arr.length;i++)
									{
										s=s+arr[i]+" ";
									}
									
								//Toast.makeText(getApplicationContext(),"err "+e.getMessage(), Toast.LENGTH_LONG).show();
									Toast.makeText(getApplicationContext(),"Error: Connection Failed", Toast.LENGTH_LONG).show();
								break;   
							}          
					}         
				}                                                      
			};                                       
			HttpConnection obj= new HttpConnection(handler);			   
			String[] key={"stud_libcard_no"};
			String[] value={stud_libcard_no};
			//String[] value={editText1.getText().toString(),editText2.getText().toString()};		
			obj.setkey(key);
			obj.setvalue(value);
			obj.post(Url,"0");    
			                 
		}


	

}
