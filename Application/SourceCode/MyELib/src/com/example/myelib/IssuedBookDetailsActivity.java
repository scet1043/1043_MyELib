package com.example.myelib;

import com.example.myelib.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;




public class IssuedBookDetailsActivity  extends TabGroupActivity {
	IssuedBookDetailData ibdata;
	Button btnlogout;
	TextView bk_id,bk_name,author,publication,issue_date,return_date;
	TabHost tabhost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issuedbookdetailsactivity);
    //  tabhost=(TabHost)getTabHost().setCurrentTab(2);
        btnlogout=(Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
			                
			@Override
			public void onClick(View arg0) {
				
				Intent i=new Intent(getApplicationContext(),MainActivity.class);
	    		startActivity(i);  
				
			}
		
        });
       bk_id=(TextView) findViewById(R.id.txt_issued1);
   	   bk_name=(TextView) findViewById(R.id.txt_issued2);
   	   author=(TextView) findViewById(R.id.txt_issued3);
   	   publication=(TextView) findViewById(R.id.txt_issued4);
   	   issue_date=(TextView) findViewById(R.id.txt_issued5);
   	   return_date=(TextView) findViewById(R.id.txt_issued6);
   	   
   	   Bundle bundle=getIntent().getExtras();

   		bk_id.setText(bundle.getString("bk_id"));
   		bk_name.setText(bundle.getString("bk_name"));
   		author.setText(bundle.getString("author"));
   		publication.setText(bundle.getString("publication"));
   		issue_date.setText(bundle.getString("issue_date"));
   		return_date.setText(bundle.getString("return_date"));
   		
          
    }  
    @Override
    public void onBackPressed() {   
    		Intent i=new Intent(getApplicationContext(),HomeActivity.class);
    		startActivity(i);      		
          //  super.onBackPressed(); // allows standard use of backbutton for page 1
          
         
    }
}