package com.example.myelib;

import com.example.myelib.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button b1,b2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity);
		b1=(Button) findViewById(R.id.button1);
		b2=(Button) findViewById(R.id.button2);
		
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {				
				
				Intent i = new Intent(MainActivity.this,LoginLibNo.class);
				startActivity(i);
				                 
			}       
		});
		b2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {				
				
				Intent i = new Intent(MainActivity.this,LoginBarCode.class);
				startActivity(i);
				                 
			}       
		});
		
	}
	
		 @Override
		    public void onBackPressed() {   
		    		Intent i=new Intent(getApplicationContext(),MainActivity.class);
		    		startActivity(i);      		
		            //super.onBackPressed(); // allows standard use of backbutton for page 1
		          	
	}
}
