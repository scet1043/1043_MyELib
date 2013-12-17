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




public class SearchedBookDetailsActivity  extends TabGroupActivity {
	SearchedBookDetailData sbdata;
	Button btnlogout;
	TextView bk_id,bk_name,author,publication,desc,book_stock,book_avail;
	TabHost tabhost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchedbookdetailsactivity);
    //  tabhost=(TabHost)getTabHost().setCurrentTab(2);
        btnlogout=(Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
			                
			@Override
			public void onClick(View arg0) {
				
				Intent i=new Intent(getApplicationContext(),MainActivity.class);
	    		startActivity(i);  
				
			}
		
        });
       bk_id=(TextView) findViewById(R.id.txt_searched1);
   	   bk_name=(TextView) findViewById(R.id.txt_searched2);
   	   author=(TextView) findViewById(R.id.txt_searched3);
   	   publication=(TextView) findViewById(R.id.txt_searched4);
   	   desc=(TextView) findViewById(R.id.txt_searched5);
   	   book_stock=(TextView) findViewById(R.id.txt_searched6);
   	   book_avail=(TextView) findViewById(R.id.txt_searched7);
   	   
   	   Bundle bundle=getIntent().getExtras();

   		bk_id.setText(bundle.getString("bk_id"));
   		bk_name.setText(bundle.getString("bk_name"));
   		author.setText(bundle.getString("author"));
   		publication.setText(bundle.getString("publication"));
   		desc.setText(bundle.getString("desc"));
   		book_stock.setText(bundle.getString("book_stock"));
   		book_avail.setText(bundle.getString("book_avail"));
          
    }  
    @Override
    public void onBackPressed() {   
    		Intent i=new Intent(getApplicationContext(),SearchActivity.class);
    		startActivity(i);      		
          //  super.onBackPressed(); // allows standard use of backbutton for page 1
          
         
    }
}