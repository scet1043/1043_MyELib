package com.example.myelib;

import com.example.myelib.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;




public class BookDetailsActivity  extends TabGroupActivity {
	Button btnrequest;
	Button btnlogout;
	TabHost tabhost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetailsactivity);
    //  tabhost=(TabHost)getTabHost().setCurrentTab(2);
        btnlogout=(Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
			                
			@Override
			public void onClick(View arg0) {
				
				Intent i=new Intent(getApplicationContext(),MainActivity.class);
	    		startActivity(i);  
				
			}
		
        });
        btnrequest=(Button)findViewById(R.id.btnrequest);
        btnrequest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub
				
			}
		});
          
    }  
    @Override
    public void onBackPressed() {   
    		Intent i=new Intent(getApplicationContext(),SearchActivity.class);
    		startActivity(i);      		
            //super.onBackPressed(); // allows standard use of backbutton for page 1
          
         
    }
}