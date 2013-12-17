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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

public class SearchActivity extends TabGroupActivity {
	ListView booklist;
	String keyword;
	JSONArray jarray;
	JSONObject jObject;
	String Url;
	Button btnlogout,btnGo;
	EditText editSearch;
	ArrayAdapter<String> adapter;
	SearchedBookDetailData sbkdtl;
	ArrayList<SearchedBookDetailData> sbk;
	ArrayList<String> bk_name;
	Global_Application ga;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchactivity);
		ga=((Global_Application)getApplicationContext());
		//BkDetail_all();
		  btnlogout=(Button)findViewById(R.id.btnlogout);
	        btnlogout.setOnClickListener(new View.OnClickListener() {
				                
				@Override
				public void onClick(View arg0) {
					
					Intent i=new Intent(getApplicationContext(),MainActivity.class);
		    		startActivity(i);  
					
				}
			
	        });
        editSearch=(EditText) findViewById(R.id.editSearch);
        btnGo=(Button)findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
			                
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();
				keyword=editSearch.getText().toString();
				 BkDetail_Keyword();
				
			}
		
        });
		 booklist=(ListView)findViewById(R.id.booklist);
		
	 
				
 
	} 
	public void BkDetail_Keyword()   
	{	                    
		Url=ga.getUrl()+"BkDetail_Keyword";
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
									sbk=new ArrayList<SearchedBookDetailData>() ;
									bk_name=new ArrayList<String>();								
									String response = message.obj.toString();								
									jObject = new JSONObject(response);
									String ResponseCode = jObject.getString("ResponseCode");
									
									if (ResponseCode.compareTo("1") == 0)
									{
								         
										jarray = jObject.getJSONArray("BkDetail");
										for (int i = 0; i < jarray.length(); i++) 
										{
											sbkdtl=new SearchedBookDetailData();
											sbkdtl.setBook_ID(jarray.getJSONObject(i).getString("Dbk_id"));
											sbkdtl.setBook_Name(jarray.getJSONObject(i).getString("Dbk_name"));
											bk_name.add(jarray.getJSONObject(i).getString("Dbk_name"));
											
											sbkdtl.setBook_Author(jarray.getJSONObject(i).getString("Dbk_auther"));
											sbkdtl.setBook_Publisher(jarray.getJSONObject(i).getString("Dbk_publisher"));								
											sbkdtl.setBook_Desc(jarray.getJSONObject(i).getString("Dbk_desc"));
											sbkdtl.setBook_Stock(jarray.getJSONObject(i).getString("Dbk_stock"));
											sbkdtl.setBook_Avail(jarray.getJSONObject(i).getString("Dbk_avail"));
																		
																				
											sbk.add(sbkdtl);
										}                   
										
										adapter=new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1, bk_name);	      
										// Assign adapter to ListView
										booklist.setAdapter(adapter); 
									    booklist.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
												    //Toast.makeText(getApplicationContext(),"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
												    Intent intent=new Intent(SearchActivity.this, SearchedBookDetailsActivity.class);
												    
												    String clickedItem=bk_name.get(position);
												    
												    SearchedBookDetailData clickedSearchedData=sbk.get(position);
												    
												     intent.putExtra("bk_id",clickedSearchedData.getBook_ID());
												     intent.putExtra("bk_name",clickedSearchedData.getBook_Name());
												     intent.putExtra("author",clickedSearchedData.getBook_Author());
												     intent.putExtra("publication",clickedSearchedData.getBook_Publisher());
												     intent.putExtra("desc",clickedSearchedData.getBook_Desc());
												     intent.putExtra("book_stock",clickedSearchedData.getBook_Stock());
												     intent.putExtra("book_avail",clickedSearchedData.getBook_Avail());
													startChildActivity("SearchedBookDetailsActivity",intent);
												  }
										});
 
									} 
									 if (ResponseCode.compareTo("2") == 0)
									  {
										  Toast.makeText(getApplicationContext(),"Some server side error occured", Toast.LENGTH_LONG).show();
									  }
									 if (ResponseCode.compareTo("3") == 0)
									  {
										Toast.makeText(getApplicationContext(),"Sorry,No Match Found",Toast.LENGTH_LONG).show();
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
							///	Toast.makeText(getApplicationContext(),"err ", Toast.LENGTH_LONG).show();
								Exception e = (Exception) message.obj;
								e.printStackTrace();
								String err="Connection failed.";
							
									StackTraceElement arr[]= e.getStackTrace();
									String s="";
									for (int i=0;i<arr.length;i++)
									{
										s=s+arr[i]+" ";
									}
									Toast.makeText(getApplicationContext(),"Error: Connection Failed", Toast.LENGTH_LONG).show();
								//Toast.makeText(getApplicationContext(),"err "+e.getMessage(), Toast.LENGTH_LONG).show();
								break;   
							}          
					}         
				}                                                      
			};                                       
			HttpConnection obj= new HttpConnection(handler);			   
			String[] key={"bk_keyword"};
			String[] value={keyword};
			//String[] value={editText1.getText().toString(),editText2.getText().toString()};		
			obj.setkey(key);
			obj.setvalue(value);
			obj.post(Url,"0");    
			                 
		}
	public void BkDetail_all()   
	{	                    
		Url=ga.getUrl()+"BkDetail_all";
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
									sbk=new ArrayList<SearchedBookDetailData>() ;
									bk_name=new ArrayList<String>();								
									String response = message.obj.toString();								
									jObject = new JSONObject(response);
									String ResponseCode = jObject.getString("ResponseCode");
									
									if (ResponseCode.compareTo("1") == 0)
									{
								         
										jarray = jObject.getJSONArray("BkDetail");
										for (int i = 0; i < jarray.length(); i++) 
										{
											sbkdtl=new SearchedBookDetailData();
											sbkdtl.setBook_ID(jarray.getJSONObject(i).getString("Dbk_id"));
											sbkdtl.setBook_Name(jarray.getJSONObject(i).getString("Dbk_name"));
											bk_name.add(jarray.getJSONObject(i).getString("Dbk_name"));
											
											sbkdtl.setBook_Author(jarray.getJSONObject(i).getString("Dbk_auther"));
											sbkdtl.setBook_Publisher(jarray.getJSONObject(i).getString("Dbk_publisher"));								
											sbkdtl.setBook_Desc(jarray.getJSONObject(i).getString("Dbk_desc"));
											sbkdtl.setBook_Stock(jarray.getJSONObject(i).getString("Dbk_stock"));
											sbkdtl.setBook_Avail(jarray.getJSONObject(i).getString("Dbk_avail"));
																		
																				
											sbk.add(sbkdtl);
										}                   
										
										adapter=new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1, bk_name);	      
										// Assign adapter to ListView
										booklist.setAdapter(adapter); 
									    booklist.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
												   
												    Intent intent=new Intent(SearchActivity.this, SearchedBookDetailsActivity.class);
												    
												    String clickedItem=bk_name.get(position);
												    
												    SearchedBookDetailData clickedSearchedData=sbk.get(position);
												    
												     intent.putExtra("bk_id",clickedSearchedData.getBook_ID());
												     intent.putExtra("bk_name",clickedSearchedData.getBook_Name());
												     intent.putExtra("author",clickedSearchedData.getBook_Author());
												     intent.putExtra("publication",clickedSearchedData.getBook_Publisher());
												     intent.putExtra("desc",clickedSearchedData.getBook_Desc());
												     intent.putExtra("book_stock",clickedSearchedData.getBook_Stock());
												     intent.putExtra("book_avail",clickedSearchedData.getBook_Avail());
													startChildActivity("SearchedBookDetailsActivity",intent);
												  }
										});
 
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
		/*	String[] key={"bk_keyword"};
			String[] value={keyword};
			//String[] value={editText1.getText().toString(),editText2.getText().toString()};		
			obj.setkey(key);
			obj.setvalue(value);*/
			obj.post(Url,"0");    
			                 
		}               

}
