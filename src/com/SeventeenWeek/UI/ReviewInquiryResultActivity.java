package com.SeventeenWeek.UI;

import com.SeventeenWeek.R;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ReviewInquiryResultActivity extends Activity {
	private ListView projectList;
	private ListAdapter listAdapter;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        setTitle(R.string.inquiry_result);
        projectList = (ListView)findViewById(R.id.work_list);
        
        DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        
        Intent intent = getIntent();
        String startDateString = intent.getStringExtra("startDatePara");
        String endDateString = intent.getStringExtra("endDatePara");
        
        Cursor cursor = dbHelper.selectReview(startDateString, endDateString);
        
        startManagingCursor(cursor);
        
        listAdapter = new SimpleCursorAdapter(
        		this, 
        		android.R.layout.simple_expandable_list_item_1,
        		cursor,
        		new String[]{"create_date"},
        		new int[] {android.R.id.text1});
        
        projectList.setAdapter(listAdapter);
        
        projectList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                    long id) {

            	Intent intent = new Intent();
        		Long tempProjectId = Long.valueOf(id);
        		        		
                intent.putExtra("reviewId", tempProjectId.toString());
                
        		intent.setClass(ReviewInquiryResultActivity.this, ReviewDetailActivity.class);
        		startActivity(intent);
            	
            	
            	
                //setTitle("点击第"+arg2+"个项目");
            	//点击project项目，进入相关action
            }
        });
        
	}
}
