package com.example.myelib;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;



import com.example.myelib.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends TabGroupActivity {

	JSONArray jarray;
	JSONObject jObject;   
	Button btnlogout;
	TextView t1,t2,t3,t4,t5,t6,t7;
	ListView booklist;
	String stud_libcard_no;
	String Url;
	ArrayAdapter<String> adapter;
	IssuedBookDetailData ibkdtl;
	ArrayList<IssuedBookDetailData> ibk;
	ArrayList<String> BookName;
	Global_Application ga;
	Dialog dilog;
	LinearLayout lt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

    //  tabhost=(TabHost)getTabHost().setCurrentTab(2);
        
        ga=(Global_Application)(getApplicationContext());         
        stud_libcard_no=ga.getLibCardID();
        lt=(LinearLayout)findViewById(R.id.linlayout);
   
        
        btnlogout=(Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
			                
			@Override
			public void onClick(View arg0) {
				
				Intent i=new Intent(getApplicationContext(),MainActivity.class);
	    		startActivity(i);     
				
			}
		          
        });
        booklist=(ListView)findViewById(R.id.booklist);
        Stud_Detail();
        Status_Issue_Detail();

        //Service calling for notification
       
        Intent intent=new Intent(this, MyService.class);
        intent.putExtra("LibCardNo", ga.getLibCardID());
        PendingIntent pintent=PendingIntent.getService(getApplicationContext(), 0, intent, 0);
        Calendar cal=Calendar.getInstance();
        AlarmManager alarm=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
    //    alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),10*1000, pintent); 
        alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);
          
    }      

    @Override
    public void onBackPressed() {   
    		//Intent i=new Intent(getApplicationContext(),MainActivity.class);
    		//startActivity(i);      		
            //super.onBackPressed(); // allows standard use of backbutton for page 1
          
         
    }
public void Stud_Detail()   
{	       
	Url=ga.getUrl()+"Stud_Detail";
		
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
							 
							  if (ResponseCode.compareTo("1") == 0)
								{					                
					              String stud_fname = jObject.getString("stud_fname");
					              String stud_lname = jObject.getString("stud_lname");
					              String stud_dept = jObject.getString("stud_dept");
					              String stud_class = jObject.getString("stud_class");
								  String stud_enroll_no = jObject.getString("stud_enroll_no");
								  String stud_email = jObject.getString("stud_email");
								  String stud_mob_no = jObject.getString("stud_mob_no");
								  t1=(TextView) findViewById(R.id.txtHome1);
								  t2=(TextView) findViewById(R.id.txtHome2);
								  t3=(TextView) findViewById(R.id.txtHome3);
								  t4=(TextView) findViewById(R.id.txtHome4);
								  t5=(TextView) findViewById(R.id.txtHome5);
								  t6=(TextView) findViewById(R.id.txtHome6);
								  t7=(TextView) findViewById(R.id.txtHome7);
								  
								  t1.setText(stud_libcard_no);
								  t2.setText(stud_enroll_no);
								  t3.setText(stud_fname+" "+stud_lname);
								  t4.setText(stud_dept);
								  t5.setText(stud_class);
								  t6.setText(stud_email);
								  t7.setText(stud_mob_no);
								  

								}
							  if (ResponseCode.compareTo("2") == 0)
							  {
								  Toast.makeText(getApplicationContext(),"Some server side error occured", Toast.LENGTH_LONG).show();
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

public void Status_Issue_Detail()   
{	                    
	Url=ga.getUrl()+"Status_Issue_Detail";
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
								ibk=new ArrayList<IssuedBookDetailData>() ;
								BookName=new ArrayList<String>();
							//Toast.makeText(getApplicationContext(),"inside succeess ", Toast.LENGTH_LONG).show();
							 String response = message.obj.toString();								
							 jObject = new JSONObject(response);
								String ResponseCode = jObject.getString("ResponseCode");
								//Toast.makeText(getApplicationContext(),"inside succeess "+response, Toast.LENGTH_LONG).show();
								if (ResponseCode.compareTo("1") == 0)
								{ lt.setVisibility(LinearLayout.VISIBLE);
									jarray = jObject.getJSONArray("IssueList");
									for (int i = 0; i < jarray.length(); i++) 
									{
																		     
										ibkdtl=new IssuedBookDetailData();
						
										ibkdtl.setBook_ID(jarray.getJSONObject(i).getString("bk_id"));
										ibkdtl.setBook_Seq_ID(jarray.getJSONObject(i).getString("bk_seq_id"));
										ibkdtl.setBook_Name(jarray.getJSONObject(i).getString("bk_name"));
										BookName.add(jarray.getJSONObject(i).getString("bk_name"));
										
										ibkdtl.setBook_Author(jarray.getJSONObject(i).getString("bk_author"));
										ibkdtl.setBook_Publisher(jarray.getJSONObject(i).getString("bk_publisher"));
										ibkdtl.setIssue_Date(jarray.getJSONObject(i).getString("issue_date"));
										ibkdtl.setReturn_Date(jarray.getJSONObject(i).getString("return_date"));
										                           									
										ibk.add(ibkdtl);
									}                   
									
									adapter=new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1,android.R.id.text1, BookName);	      
									// Assign adapter to ListView
									booklist.setAdapter(adapter); 
										booklist.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
											   //Toast.makeText(getApplicationContext(),"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
											  
											    Intent intent=new Intent(HomeActivity.this, IssuedBookDetailsActivity.class);
											    
											    String clickedItem=BookName.get(position);
											    
											    IssuedBookDetailData clickedIssuedData=ibk.get(position);
											     intent.putExtra("bk_id",clickedIssuedData.getBook_ID());
											     intent.putExtra("bk_seq_id",clickedIssuedData.getBook_Seq_ID());
											     intent.putExtra("bk_name",clickedIssuedData.getBook_Name());
											     intent.putExtra("author",clickedIssuedData.getBook_Author());
											     intent.putExtra("publication",clickedIssuedData.getBook_Publisher());
											     intent.putExtra("issue_date",clickedIssuedData.getIssue_Date());
											     intent.putExtra("return_date",clickedIssuedData.getReturn_Date());
											    
												startChildActivity("IssuedBookDetailsActivity",intent);
											  }
									});	

								}
								 if (ResponseCode.compareTo("2") == 0)
								  {
									  Toast.makeText(getApplicationContext(),"Some server side error occured", Toast.LENGTH_LONG).show();
								  }
								 if (ResponseCode.compareTo("3") == 0)
								  {
									 lt.setVisibility(LinearLayout.INVISIBLE);
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
							Toast.makeText(getApplicationContext(),"err ", Toast.LENGTH_LONG).show();
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