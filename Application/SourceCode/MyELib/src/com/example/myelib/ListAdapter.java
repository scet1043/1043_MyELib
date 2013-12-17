package com.example.myelib;

import java.util.ArrayList;
import java.util.List;

import com.example.myelib.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends ArrayAdapter<EBookDetailData> {
	private List<EBookDetailData> articles;
	private Context context;
	private final LayoutInflater inflator;
	Global_Application ga;
                  


	private static class ViewHolder {
		public ImageView iconView;
		public TextView nameTextView;
		public TextView bottomText;
		public TextView tryontext;
		
	}

	public ListAdapter(Context context, int textViewResourceId,
			ArrayList<EBookDetailData> articles) {
		super(context, textViewResourceId, articles);
		this.articles = articles;
		this.context = context;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//Toast.makeText(context," name "+articles.size(),Toast.LENGTH_LONG).show();
		//BitmapManager.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon));
	}   
       
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
              
		if (convertView == null) {
			convertView = inflator.inflate(R.layout.row, null);
			

			TextView nameTextView = (TextView) convertView
					.findViewById(R.id.title);
			
			TextView bottomText = (TextView) convertView
					.findViewById(R.id.bottomtext);
			ImageView iconView = (ImageView) convertView
					.findViewById(R.id.article_icon);
			           
			TextView tryontext=(TextView) convertView.findViewById(R.id.notused);			
			
			holder = new ViewHolder();
			holder.nameTextView = nameTextView;
			holder.bottomText = bottomText;
			holder.iconView = iconView;
			holder.tryontext=tryontext;
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
                 
		final EBookDetailData article = articles.get(position);
		
		holder.nameTextView.setText(article.getEbook_Name());//
		holder.bottomText.setText("Author: "+article.getEbook_Author());//article.getCountry_Cost() + " | "	+ article.getCurrency()
		holder.tryontext.setText("Publisher: "+article.getEbook_Publisher());
		holder.iconView.setTag(position);
		
		byte[] decodedString = Base64.decode(article.getEbook_Base64(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		//BitmapManager.INSTANCE.loadBitmap(article.getEbook_CoverImg(), holder.iconView, 130,80);
	//Toast.makeText(context,"len=>"+article.getmobimageurl().length(),Toast.LENGTH_LONG).show();
		holder.iconView.setBackgroundDrawable(new BitmapDrawable(context.getResources(),decodedByte));
		//BitmapManager.INSTANCE.setPlaceholder(decodedByte);
		holder.iconView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Intent i=new Intent(getContext(),OpenPDFWeb.class);
				i.putExtra("path", "http://172.16.8.89/ELibService"+article.getEbook_Pdfpath().toString().substring(1));
				getContext().startActivity(i);
				*/
				
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://172.16.8.89/NewElibService"+article.getEbook_Pdfpath().toString().substring(1)));
				getContext().startActivity(i); 
				 
				Toast.makeText(context, "Your File is downloaded, You can Read It", Toast.LENGTH_LONG).show();
			}      
		});       
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ga=new Global_Application(); 
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ga.getEbookUrl()+article.getEbook_Pdfpath().toString().substring(1)));
				//Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://172.16.8.89/NewElibService"+article.getEbook_Pdfpath().toString().substring(1)));
				getContext().startActivity(i); 
				 
				Toast.makeText(context, "Your File is downloaded, You can Read It", Toast.LENGTH_LONG).show();
			}
		}) ;      
		return convertView;
	}
}       
