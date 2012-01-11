package com.SeventeenWeek.UI;

import com.SeventeenWeek.R;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.HashMap;

public class ProjectListActivity extends Activity {
	
	private ListView projectList;
	private ListAdapter listAdapter;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        setTitle(R.string.project);
        projectList = (ListView)findViewById(R.id.work_list);
        
        DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        
        Cursor cursor = dbHelper.selectWorkProject();
        
        startManagingCursor(cursor);
        
        listAdapter = new SimpleCursorAdapter(
        		this, 
        		android.R.layout.simple_expandable_list_item_1,
        		cursor,
        		new String[]{"content"},
        		new int[] {android.R.id.text1});
        
        projectList.setAdapter(listAdapter);
        
        projectList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                    long id) {

            	Intent intent = new Intent();
        		Long tempProjectId = Long.valueOf(id);
        		TextView textView = (TextView)view;
        		String projectTitle= textView.getText().toString(); 
        		
        		intent.putExtra("projectTitle", projectTitle);
                intent.putExtra("projectId", tempProjectId.toString());
                
        		intent.setClass(ProjectListActivity.this, ActionListActivity.class);
        		startActivity(intent);
            	
            	
            	
                //setTitle("点击第"+arg2+"个项目");
            	//点击project项目，进入相关action
            }
        });
        
	}
	
	
}
