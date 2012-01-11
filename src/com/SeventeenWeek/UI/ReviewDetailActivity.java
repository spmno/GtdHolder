package com.SeventeenWeek.UI;

import com.SeventeenWeek.R;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class ReviewDetailActivity extends Activity {
	private Button		finishButton;
	private EditText	contentText;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_detail);
        setTitle(R.string.review);
        finishButton = (Button)findViewById(R.id.button_collect_detail_finish);
        contentText = (EditText)findViewById(R.id.editText_collect_detail_content);
        
        Intent intent = getIntent();
        String reviewIdString = intent.getStringExtra("reviewId");
        
        if (reviewIdString.length() > 0){
        	DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        	Long reviewId = Long.valueOf(reviewIdString);
        	Cursor cursor = dbHelper.selectReview(reviewId);
        	if (cursor.moveToFirst()){
	    		int index = cursor.getColumnIndex("content");  
	            String content =  cursor.getString(index);
	            contentText.setText(content);
	    	}
        }
        
        
        finishButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		String collectContent =  contentText.getText().toString();
        		Toast.makeText(ReviewDetailActivity.this, 
        				collectContent + " " + getString(R.string.add_success), 
        				Toast.LENGTH_SHORT).show(); 
        		//Intent intent = new Intent();
        		//intent.setClass(CollectDetailActivity.this, CollectActivity.class);
        		//startActivity(intent);
        		DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        		dbHelper.insertReview(collectContent);
        		ReviewDetailActivity.this.finish();
        	}
        });
	}
}
