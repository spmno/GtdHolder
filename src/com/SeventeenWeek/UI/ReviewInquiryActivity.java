package com.SeventeenWeek.UI;

import com.SeventeenWeek.R;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReviewInquiryActivity extends Activity {
	private Button inquireButton;
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry);
        setTitle(R.string.inquiry);
        inquireButton = (Button)findViewById(R.id.confirmDateButton);
        startDatePicker = (DatePicker)findViewById(R.id.startDatePicker);
        endDatePicker = (DatePicker)findViewById(R.id.endDatePicker);
        
      
        inquireButton.setOnClickListener(new Button.OnClickListener(){
              public void onClick(View v){
            	  
            	  int startYear = startDatePicker.getYear();
            	  int startMonth = startDatePicker.getMonth();
            	  int startDay = startDatePicker.getDayOfMonth();
            	  int endYear = endDatePicker.getYear();
            	  int endMonth = endDatePicker.getMonth();
            	  int endDay = endDatePicker.getDayOfMonth();
            	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            	  Date startDate = new Date(startYear-1900, startMonth, startDay);
            	  Date endDate = new Date(endYear-1900, endMonth, endDay);
            	  String startDateString = dateFormat.format(startDate);
            	  String endDateString = dateFormat.format(endDate);
            	  
            	  
         		  Intent intent = new Intent();
         		  
                  intent.putExtra("startDatePara", startDateString);
                  intent.putExtra("endDatePara", endDateString);
                  intent.setClass(ReviewInquiryActivity.this, ReviewInquiryResultActivity.class);
                  startActivity(intent);
                  finish();
            	  
              }
          });
	}
}
