package com.example.myelib;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.myelib.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EbookActivity extends TabGroupActivity {
	ListView booklist;
	Button btnlogout;
	String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
			  "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			  "Linux", "OS/2" };
	String[] bookname;
	String Url;
	Global_Application ga;
	JSONArray jarray;
	JSONObject jObject;   
	ArrayList<EBookDetailData> ebk;
	EBookDetailData ebkdtl;
	ListAdapter adapter;
	ListView listView;
	             
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebookactivity);
		ga=(Global_Application)(getApplicationContext());
		
		
		listView = (ListView) findViewById(R.id.booklist);		
		ebk=new ArrayList<EBookDetailData>();
		Ebook();            
		  btnlogout=(Button)findViewById(R.id.btnlogout);
	        btnlogout.setOnClickListener(new View.OnClickListener() {
				                
				@Override
				public void onClick(View arg0) {
					
					Intent i=new Intent(getApplicationContext(),MainActivity.class);
		    		startActivity(i);  
					
				}
			
	        });
		// booklist=(ListView)findViewById(R.id.booklist);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);


				// Assign adapter to ListView
				//booklist.setAdapter(adapter); 
				/*booklist.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
						    Toast.makeText(getApplicationContext(),
						      "Click ListItem Number " + position, Toast.LENGTH_LONG)
						      .show();
						  }
				});*/
				
				
 
	}
	
	
	public void Ebook()   
	 {	              
		Url=ga.getUrl()+"Ebook";
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
								//Toast.makeText(getApplicationContext(),"inside succeess ", Toast.LENGTH_LONG).show();
								 String response = message.obj.toString();								
								 jObject = new JSONObject(response);
									String ResponseCode = jObject.getString("ResponseCode");
									
									if (ResponseCode.compareTo("1") == 0)
									{
										jarray = jObject.getJSONArray("EBookList");
										for (int i = 0; i < jarray.length(); i++) 
										{
											ebkdtl=new EBookDetailData();
											ebkdtl.setEbook_ID(jarray.getJSONObject(i).getString("Ebk_id"));
											ebkdtl.setEbook_Name(jarray.getJSONObject(i).getString("Ebk_name"));
											ebkdtl.setEbook_Author(jarray.getJSONObject(i).getString("Ebk_author"));
											ebkdtl.setEbook_Publisher(jarray.getJSONObject(i).getString("Ebk_publisher"));								
											ebkdtl.setEbook_CoverImg(jarray.getJSONObject(i).getString("Ebk_coverimg"));
											ebkdtl.setEbook_Base64(jarray.getJSONObject(i).getString("Ebk_base64img"));
											ebkdtl.setEbook_Pdfpath(jarray.getJSONObject(i).getString("Ebk_pdfpath"));								
																				
											ebk.add(ebkdtl);
										}                        
										adapter=new ListAdapter(EbookActivity.this,android.R.id.text1, ebk);	      
										// Assign adapter to ListView
										listView.setAdapter(adapter);	

									} else
										Toast.makeText(getApplicationContext(),"Message:Records Not Found ",Toast.LENGTH_LONG).show();

								            
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
									
								//Toast.makeText(getApplicationContext(),"err "+e.getMessage(), Toast.LENGTH_LONG).show();
									Toast.makeText(getApplicationContext(),"Error: Connection Failed", Toast.LENGTH_LONG).show();
								break;   
							}          
					}         
				}                                                      
			};                                       
			HttpConnection obj= new HttpConnection(handler);			   
			String[] key={};
			String[] value={};
			//String[] value={editText1.getText().toString(),editText2.getText().toString()};		
			obj.setkey(key);
			obj.setvalue(value);
			obj.post(Url,"0");    
			                 
		}                       
	@Override
    public void onBackPressed() {   
    		//Intent i=new Intent(getApplicationContext(),SearchActivity.class);
    		//startActivity(i);      		
            //super.onBackPressed(); // allows standard use of backbutton for page 1    
    }
}
