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
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class LoginLibNo extends TabGroupActivity 	
{
	
	Button btnlogin;
	TabHost tabhost;
	String webmethodurl;
	JSONArray jarray;
	JSONObject jObject;             
	EditText editText1,editText2;
	String stud_libcard_no;
	String Url;
	Global_Application ga;
	@Override               
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlibno);	
		ga=(Global_Application)(getApplicationContext());
		
		
		btnlogin=(Button)findViewById(R.id.btnlogin);
	    editText1=(EditText)findViewById(R.id.editText1);
	    editText2=(EditText)findViewById(R.id.editText2);
	    btnlogin.setOnClickListener(new OnClickListener() {
			                 
			@Override
			public void onClick(View v) {
			
				Login();
				
			}            
		})  ;          
	                               
}                   
      
public void Login()   
 {	                    
	Url=ga.getUrl()+"Login";
	
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
							 //editText1.setText(response);
							// Toast.makeText(getApplicationContext(), response+"", Toast.LENGTH_LONG).show();
							 jObject = new JSONObject(response);
							  String ResponseCode = jObject.getString("ResponseCode");
							 
							  if (ResponseCode.compareTo("1") == 0)
								{
								  stud_libcard_no=editText2.getText().toString();
								  ga.setLibCardID(stud_libcard_no);
								  startChildActivity("LogintoHomepage",new Intent(LoginLibNo.this, ApplicationTabPage.class));
							      	}
							  if (ResponseCode.compareTo("2") == 0)
							  {
								  Toast.makeText(getApplicationContext(),"Some server side error occured", Toast.LENGTH_LONG).show();
							  }
							  if (ResponseCode.compareTo("3") == 0)
							  {
								  Toast.makeText(getApplicationContext(),"Invalid Username Or Password", Toast.LENGTH_LONG).show();
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
						//	Toast.makeText(getApplicationContext(),"err ", Toast.LENGTH_LONG).show();
							Exception e = (Exception) message.obj;
							e.printStackTrace();
							String err="Connection failed.";
						
								StackTraceElement arr[]= e.getStackTrace();
								String s="";
								for (int i=0;i<arr.length;i++)
								{
									s=s+arr[i]+" ";
								}
								//editText1.setText(s+" hello");
							//Toast.makeText(getApplicationContext(),"err "+e.getMessage(), Toast.LENGTH_LONG).show();
								Toast.makeText(getApplicationContext(),"Error: Connection Failed", Toast.LENGTH_LONG).show();
							break;   
						}          
				}         
			}                                                   
		};                                       
		HttpConnection obj= new HttpConnection(handler);			   
		String[] key={"stud_enroll_no","stud_libcard_no"};
		String[] value={editText1.getText().toString(),editText2.getText().toString()};
		//String[] value={editText1.getText().toString(),editText2.getText().toString()};		
		obj.setkey(key);
		obj.setvalue(value);
		obj.post(Url,"0");    
		                 
	}                       
@Override
public void onBackPressed() {   
		Intent i=new Intent(getApplicationContext(),MainActivity.class);
		startActivity(i);      		
        //super.onBackPressed(); // allows standard use of backbutton for page 1
      
     
}     


}
