package com.example.myelib;



import com.example.myelib.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class ApplicationTabPage extends TabActivity
{
                
	 Resources res;
	 TabHost tabHost;
	 TabSpec obj,obj1;	 
	 Intent intent; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
       
                
        tabHost = getTabHost();             
        res = getResources();
        
        obj=tabHost.newTabSpec("tab1");
        tabHost.addTab(obj
                .setIndicator("Home",res.getDrawable(R.drawable.homeicon))
                .setContent(new Intent(this, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        obj=tabHost.newTabSpec("tab2");
        tabHost.addTab(obj
                .setIndicator("Search",res.getDrawable(R.drawable.searchicon))
                .setContent(new Intent(this, SearchActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        obj=tabHost.newTabSpec("tab3");
        tabHost.addTab(obj
                .setIndicator("Ebooks",res.getDrawable(R.drawable.ebookicon))
                .setContent(new Intent(this, EbookActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        obj=tabHost.newTabSpec("tab4");   
        tabHost.addTab(obj
                .setIndicator("Suggestions",res.getDrawable(R.drawable.email_suggest))
                .setContent(new Intent(this, SendEmailActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
                 
        
        Log.i("inside ", "tab");
        /*  obj=tabHost.newTabSpec("tab4");
              
        if (ga.getChangeTab()==4)
        {
        	tabHost.setCurrentTab(4);
        	ga.setChangeTab(0);
        }              
        else
        {
        tabHost.setCurrentTab(0);
        }
        
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) 
        { 
        	//Toast.makeText(getApplicationContext(),ga.getNoOfCartItem()+"inside tab ",Toast.LENGTH_LONG).show();
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));//#186CC4
            tv.setTypeface(null,Typeface.BOLD);
                   
        } */    
      
       // TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
       // tv.setTextColor(Color.parseColor("#186CC4"));
    }
    
   
  //code to fix orientation of screeen
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		    super.onConfigurationChanged(newConfig);
		    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
    
}