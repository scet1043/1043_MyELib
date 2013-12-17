package com.example.myelib;

import android.app.Application;

   
public class Global_Application extends Application 
{                                        
	
//	String Url="http://192.168.1.12/MyELibwebservice/Service.asmx/"; // hawa's home wifi
	//String Url="http://172.16.8.89/NewELibService/Service.asmx/"; // mcalab wifi
	String Url="http://172.16.16.151/MyELibwebservice/Service.asmx/"; // hawa's home wifi
	String EbookUrl="http://172.16.16.151/MyELibwebservice";
	String LibCardID;     
	
	public String getEbookUrl()
	{
		return this.EbookUrl;
	}
	public String getUrl()
	{
		return this.Url;
	}
	public void setLibCardID(String LibCardID)
	{
		this.LibCardID=LibCardID;
	}     
	public String getLibCardID()
	{
		return this.LibCardID;
	}	
}           