package com.example.myelib;
 
import com.example.myelib.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class SendEmailActivity extends TabGroupActivity {
 
	Button buttonSend,btnlogout;
	TextView textTo;
	EditText textSubject;
	EditText textMessage;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendemailactivity);
		
		  btnlogout=(Button)findViewById(R.id.btnlogout);
	        btnlogout.setOnClickListener(new View.OnClickListener() {
				                
				@Override
				public void onClick(View arg0) {
					
					Intent i=new Intent(getApplicationContext(),MainActivity.class);
		    		startActivity(i);  
					
				}
			
	        });
 
		buttonSend = (Button) findViewById(R.id.buttonSend);
		textTo = (TextView) findViewById(R.id.textViewTo);
		
		textSubject = (EditText) findViewById(R.id.editTextSubject);
		textMessage = (EditText) findViewById(R.id.editTextMessage);
 
		buttonSend.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			  //String to = "xyzh123x@gmail.com";
				String to = "hawa.mulla@gmail.com";
			  String subject = textSubject.getText().toString();
			  String message = textMessage.getText().toString();
      
			  Intent email = new Intent(Intent.ACTION_SEND);
			  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "xyzh123x@gmail.com"});
			  email.putExtra(Intent.EXTRA_CC, new String[]{ to});
			  email.putExtra(Intent.EXTRA_BCC, new String[]{to});
			  email.putExtra(Intent.EXTRA_SUBJECT, subject);
			  email.putExtra(Intent.EXTRA_TEXT, message);

			  //need this to prompts email client only
			  email.setType("message/rfc822");
			  
			  startActivity(Intent.createChooser(email, "Choose an Email client :"));
			  
			}
		});
	}
}