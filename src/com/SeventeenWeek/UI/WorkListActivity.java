package com.SeventeenWeek.UI;

import com.SeventeenWeek.R;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

import java.util.ArrayList;


public class WorkListActivity extends Activity {
	private ArrayList<String> workKindString;
	ListView workKindList;
	private ListAdapter listAdapter;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        setTitle(R.string.work_list_title);
        workKindList = (ListView)findViewById(R.id.work_list);
        workKindString = new ArrayList<String>();
        workKindString.add(getString(R.string.project));
        workKindString.add(getString(R.string.action));
        
        listAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, 
        		workKindString);
        
        workKindList.setAdapter(listAdapter);
        
        workKindList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	switch(arg2)
            	{
            	case 0:
            	{
            		Intent intent = new Intent();
            		intent.setClass(WorkListActivity.this, ProjectListActivity.class);
            		startActivity(intent);
            		
            		break;
            	}
            	
            	case 1:
            	{
            		Intent intent = new Intent();
            		Long tempProjectId = Long.valueOf(-1);

                    intent.putExtra("projectId", tempProjectId.toString());
            		intent.setClass(WorkListActivity.this, ActionListActivity.class);
            		startActivity(intent);
            		break;
            	}
            	}
            }
        });
	}
	
}
