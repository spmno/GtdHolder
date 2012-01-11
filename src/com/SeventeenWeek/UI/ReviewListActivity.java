package com.SeventeenWeek.UI;

import java.util.ArrayList;

import com.SeventeenWeek.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ReviewListActivity extends Activity {
	private ArrayList<String> reviewFunctionString;
	ListView reviewFunctionList;
	private ListAdapter listAdapter;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        setTitle(R.string.review);
        reviewFunctionList = (ListView)findViewById(R.id.work_list);
        reviewFunctionString = new ArrayList<String>();
        reviewFunctionString.add(getString(R.string.add));
        reviewFunctionString.add(getString(R.string.inquiry));
        
        listAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, 
        		reviewFunctionString);
        
        reviewFunctionList.setAdapter(listAdapter);
        
        reviewFunctionList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	switch(arg2)
            	{
            	case 0:
            	{
            		Intent intent = new Intent();
            		intent.putExtra("reviewId", "");
            		intent.setClass(ReviewListActivity.this, ReviewDetailActivity.class);
            		startActivity(intent);
            		
            		break;
            	}
            	
            	case 1:
            	{
            		Intent intent = new Intent();
            		Long tempProjectId = Long.valueOf(-1);

                    intent.putExtra("projectId", tempProjectId.toString());
            		intent.setClass(ReviewListActivity.this, ReviewInquiryActivity.class);
            		startActivity(intent);
            		break;
            	}
            	}
            }
        });
	}
}
