package com.SeventeenWeek.UI;

import java.util.Map;

import com.SeventeenWeek.R;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.CheckedTextView;
import android.widget.AdapterView.OnItemClickListener;




public class ActionListActivity extends Activity {
	
	private ListView projectList;
	
	private long	projectId;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.list);
        projectList = (ListView)findViewById(R.id.work_list);
        
        Intent intent = getIntent();
        String projectIdString = intent.getStringExtra("projectId");
        String projectTitle = intent.getStringExtra("projectTitle");
        setTitle(projectTitle);
        projectId = Long.parseLong(projectIdString);
        
        
        
        DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        
        Cursor cursor = dbHelper.selectWorkProjectAction(projectId);
        
        SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder() {
        	  
        	  @Override
        	  public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        	   if(view instanceof CheckedTextView) {
        		CheckedTextView checkEdit = (CheckedTextView)view;
        		/*
        		checkEdit.setOnClickListener(new View.OnClickListener() {	
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						CheckedTextView checkEdit = (CheckedTextView)v;
						String actionContent = checkEdit.getText().toString();
						DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
						Cursor cursor = dbHelper.selectWorkAction(actionContent);
						int idColumnIndex = cursor.getColumnIndex("_id");
						int stateColumnIndex = cursor.getColumnIndex("work_action_state");
						if (cursor.moveToFirst()){
							int idValue = cursor.getInt(idColumnIndex);
							int stateValue = cursor.getInt(stateColumnIndex);
							
							if (stateValue == 1){
								dbHelper.updateWorkActionState(idValue,0);
								checkEdit.setChecked(false);
							}else
							{
								dbHelper.updateWorkActionState(idValue, 1);
								checkEdit.setChecked(true);
							}
				    	}
					
					}
        			
        		});
        		*/
        		int stateColumnIndex = cursor.getColumnIndex("work_action_state");
        	    int value = cursor.getInt(stateColumnIndex);
        	    String text = cursor.getString(columnIndex);
        	    checkEdit.setText(text);
        	    if(value==1) {
        	    	checkEdit.setChecked(true);
        	    	return true;
        	    }
        	    else if(value==0) {
        	    	checkEdit.setChecked(false);
        	    	return true;
        	    }
        	    else
        	     return false;
        	   }
        	   return false;
        	  }
        	 };
        
        startManagingCursor(cursor);
        
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
        		ActionListActivity.this, 
        		android.R.layout.simple_list_item_multiple_choice,
        		cursor,
        		new String[]{"content", "work_action_state"},
        		new int[] {android.R.id.text1, android.R.id.checkbox});
        
        projectList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // toggle checked status and refresh our cursor
            	CheckedTextView checkEdit = (CheckedTextView)view;
            	DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
				Cursor cursor = dbHelper.selectWorkAction(id);
				
				int stateColumnIndex = cursor.getColumnIndex("work_action_state");
				if (cursor.moveToFirst()){
					
					int stateValue = cursor.getInt(stateColumnIndex);
					
					if (stateValue == 1){
						dbHelper.updateWorkActionState(id,0);
						checkEdit.setChecked(false);
					}else
					{
						dbHelper.updateWorkActionState(id, 1);
						checkEdit.setChecked(true);
					}
		    	}
            }
        });
        
        listAdapter.setViewBinder(viewBinder);
        
        projectList.setAdapter(listAdapter);

        
        projectList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
                //String title = getString(R.string.collect_title);
                //String deleteString = getString(R.string.delete_string);
            	//super.onCreateContextMenu(menu, v, menuInfo);   
            	
            	TextView listItem = (TextView)((AdapterView.AdapterContextMenuInfo)menuInfo).targetView;
            	String title = listItem.getText().toString();
            	menu.setHeaderTitle(title); 
                menu.add(0, 1, 0, R.string.delete_string);

            }
        }); 
 	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){   
   			if (projectId == -1){
   				return true;
   			}
	        menu.add(0, 0, 0, R.string.add);  
	        //menu.add(0, 1, 1, R.string.delete_string);  
	        //menu.add(0, 2, 2, R.string.move_to_project_string);    
	        return true;    
	}  
	
	@Override
	public void onPause(){
		super.onPause();
		DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
		SimpleCursorAdapter listAdapter = (SimpleCursorAdapter)projectList.getAdapter();
		if (listAdapter.getCount() <= 0)
			return;
		
		if (projectId == -1){
			for (int i = 0; i < listAdapter.getCount(); ++i){
				CheckedTextView view = (CheckedTextView)projectList.getChildAt(i);
				if (view.isChecked()){
					Cursor cursor = (Cursor) listAdapter.getItem(i);
					int idIndex = cursor.getColumnIndex("_id");
					long actionId = cursor.getLong(idIndex);
					dbHelper.deleteWorkAction(actionId);
				}
			}
			
		}else{

			for (int i=0; i < listAdapter.getCount(); ++i){
				CheckedTextView view = (CheckedTextView)projectList.getChildAt(i);
				if (view.isChecked() == false){
					return;
				}
			}
			
			
			dbHelper.deleteWorkProjectAction(projectId);
			dbHelper.deleteWorkProject(projectId);
		}
		
		
	}
	    
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){  
	    	
	        int itemid = item.getItemId();  
	        switch(itemid){  
	        
	        	case 0: //增加收集
	        	{

	        		Intent intent = new Intent();
	        		Long tempProjectId = Long.valueOf(projectId);

	                intent.putExtra("projectId", tempProjectId.toString());
	                intent.putExtra("functionName", "add");
	        		intent.setClass(ActionListActivity.this, ActionDetailActivity.class);
	        		startActivity(intent);
	        		break;  
	        	}
	        	case 1:
		    	{
		    		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		        	
		        	delete(info.id);
		    		break;
		    	}
	        	
	     
	        	default:  
	        		break;  
	        }  
	        return true;  
	}  
	    
	public boolean onContextItemSelected(MenuItem item) {
	        //setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
	    	switch(item.getItemId()){
	    		case 1:{
	    			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	        	
	    			delete(info.id);
	    			break;
	    		}

	    	}
	    	
	    	
	    return super.onContextItemSelected(item);
	}
	    
	private void delete(final long rowId){
	        if(rowId>0){
	            new AlertDialog.Builder(this)
	            .setTitle(getString(R.string.delete_string) + "  " + getNameById(rowId))
	            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                	DataBaseOpenHelper.getInstance().deleteWorkAction((int)rowId);
	                	DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
	                    
	                    Cursor cursor = dbHelper.selectWorkProjectAction(projectId);
	                    
	                    SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder() {
	                  	  
	                  	  @Override
	                  	  public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
	                  	   if(view instanceof CheckedTextView) {
	                  		CheckedTextView checkEdit = (CheckedTextView)view;
	                  	    int value = cursor.getInt(columnIndex);
	                  	    String text = cursor.getString(columnIndex);
	                  	    checkEdit.setText(text);
	                  	    if(value==1) {
	                  	    	checkEdit.setChecked(true);
	                  	    	return true;
	                  	    }
	                  	    else if(value==0) {
	                  	    	checkEdit.setChecked(false);
	                  	    	return true;
	                  	    }
	                  	    else
	                  	     return false;
	                  	   }
	                  	   return false;
	                  	  }
	                  	 };
	                    
	                    startManagingCursor(cursor);
	                    
	                    SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
	                    		ActionListActivity.this, 
	                    		android.R.layout.simple_list_item_multiple_choice,
	                    		cursor,
	                    		new String[]{"content", "work_action_state"},
	                    		new int[] {android.R.id.text1, android.R.id.checkbox});
	                    listAdapter.setViewBinder(viewBinder);
	                    projectList.setAdapter(listAdapter);
	                	
	                }
	            })
	            .setNegativeButton(getString(R.string.cancel), null)
	            .show();
	        }
	        
	}
	    
	private String getNameById(long id){
	    	DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
	    	Long itemId = Long.valueOf(id);
	    	Cursor cursor = dbHelper.selectWorkAction(itemId);
	    	String content = "";
	    	if (cursor.moveToFirst()){
	    		int index = cursor.getColumnIndex("content");  
	            content =  cursor.getString(index);
	    	}

	        return content;
	}
}

