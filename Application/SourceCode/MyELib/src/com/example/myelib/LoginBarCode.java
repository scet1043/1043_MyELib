package com.example.myelib;


import org.json.JSONArray;
import org.json.JSONObject;

import com.example.myelib.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginBarCode extends TabGroupActivity 	
{
	private Button scanBtn;
	private TextView formatTxt, contentTxt;
	String scanContent ;
	JSONArray jarray;
	JSONObject jObject;
	String stud_libcard_no;
	Global_Application ga;
	String Url;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		ga=(Global_Application)(getApplicationContext());
		Url=ga.getUrl()+"Login_barcode";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginbarcode);
		//instantiate UI items
		scanBtn = (Button)findViewById(R.id.scan_button);
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);

		//listen for clicks
		scanBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			IntentIntegrator scanIntegrator = new IntentIntegrator(LoginBarCode.this);
					//start scanning
			scanIntegrator.initiateScan();
				
			}
		});
	}
	 @Override
	    public void onBackPressed() {   
	    		Intent i=new Intent(getApplicationContext(),MainActivity.class);
	    		startActivity(i);      		
	            //super.onBackPressed(); // allows standard use of backbutton for page 1
	          
	         
	    }


	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		//check we have a valid result
		if (scanningResult != null) {
			//get content from Intent Result
			scanContent = scanningResult.getContents();
			//get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
			//output to UI
			//formatTxt.setText("FORMAT: "+scanFormat);
			//contentTxt.setText("CONTENT: "+scanContent);
			Toast.makeText(getApplicationContext(), scanContent+" ",Toast.LENGTH_LONG).show();
			stud_libcard_no=scanContent;
			
			Login_barcode();
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), "Invalid login!!!", Toast.LENGTH_SHORT);	toast.show();
		}      
	}
	public void Login_barcode()   
	 {	              
			
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
								//Toast.makeText(getApplicationContext(), "response==>"+response, Toast.LENGTH_LONG).show();	
								 
								if (ResponseCode.compareTo("1") == 0)
								{
								  
								  ga.setLibCardID(stud_libcard_no);
								  startChildActivity("LogintoHomepage",new Intent(LoginBarCode.this, ApplicationTabPage.class));
							      	}
							  if (ResponseCode.compareTo("3") == 0)
							  {
								  Toast.makeText(getApplicationContext(),"Invalid Username Or Password", Toast.LENGTH_LONG).show();
							  }
							  if (ResponseCode.compareTo("2") == 0)
							  {
								  Toast.makeText(getApplicationContext(),"Some server side error occured", Toast.LENGTH_LONG).show();
							  }
							 
							}    	
								/*if (ResponseCode.compareTo("1") == 0)
									{
								 // Toast.makeText(getApplicationContext(),"response==> "+response, Toast.LENGTH_LONG).show();
								//String ResponseCode=jObject.getString("ResponseCode");
								
								        Intent editrec = new Intent(getParent(), ApplicationTabPage.class);
									 	TabGroupActivity parentActivity = (TabGroupActivity)getParent();
									 	parentActivity.startChildActivity("Login", editrec);       
									}
								}   */   
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
									
								Toast.makeText(getApplicationContext(),"err "+e.getMessage(), Toast.LENGTH_LONG).show();
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
