package com.SeventeenWeek.UI;

import com.SeventeenWeek.R;

import com.SeventeenWeek.R.layout;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;

import android.view.View.OnCreateContextMenuListener;
import android.content.Intent;
import android.content.DialogInterface;
import android.database.Cursor;

import java.util.HashMap;

public class CollectListActivity extends Activity {
	private Button addButton;
	private Button backButton;
	private ListView contentList;
	private ListAdapter listAdapter;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect);
        setTitle(R.string.collect_list_title);
        //addButton = (Button)findViewById(R.id.button_collect_add);
        //backButton = (Button)findViewById(R.id.button_collect_back);
        contentList = (ListView)findViewById(R.id.collect_list_view);
       /* 
        addButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		Intent intent = new Intent();
        		intent.setClass(CollectActivity.this, CollectDetailActivity.class);
        		startActivity(intent);
        		//CollectActivity.this.finish();
        	}
        });
        
        backButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		CollectActivity.this.finish();
        	}
        });
        */
        DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        
        Cursor cursor = dbHelper.selectCollect();
        
        startManagingCursor(cursor);
        
        listAdapter = new SimpleCursorAdapter(
        		this, 
        		android.R.layout.simple_expandable_list_item_1,
        		cursor,
        		new String[]{"content"},
        		new int[] {android.R.id.text1});
        
        contentList.setAdapter(listAdapter);
        
        contentList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(CollectListActivity.this, CollectDetailActivity.class);
                intent.putExtra("collectId", id);
                TextView textView = (TextView)view;
                String content = textView.getText().toString();
                intent.putExtra("collectContent", content);
                intent.putExtra("functionName", "edit");
                startActivity(intent);
            }
        });
        
        contentList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
                //String title = getString(R.string.collect_title);
                //String deleteString = getString(R.string.delete_string);
            	menu.setHeaderTitle(R.string.collect_title); 
                menu.add(0, 1, 0, R.string.delete_string);
                menu.add(0, 2, 0, R.string.move_to_project_string); 
                menu.add(0, 3, 0, R.string.move_to_action_string);
            }
        }); 
        
      
        
	}
	
	//长按菜单响应函数
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
    	switch(item.getItemId())
    	{
    	case 0:
    	{
    		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        	
        	delete(info.id, item.getItemId());
    		break;
    	}
    	
    	case 1:
    	{
    		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    		 Intent intent = new Intent();
             //Intent传递参数
    		 Long collectId = Long.valueOf(info.id);
    		 TextView textView = (TextView)info.targetView;
    		 String projectTitle= textView.getText().toString(); 
             intent.putExtra("collectId", collectId.toString());
             intent.putExtra("projectTitle", projectTitle);
             intent.setClass(CollectListActivity.this, ProjectTitleCompleteActivity.class);
             startActivity(intent);
             finish();
    		break;
    	}
    	case 2:
    	{
    		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
   		 	Intent intent = new Intent();
            //Intent传递参数
   		 	Long collectId = Long.valueOf(info.id);
   		 	
   		 	TextView textView = (TextView)info.targetView;
   		 	String actionTitle= textView.getText().toString(); 
            intent.putExtra("collectId", collectId.toString());
            intent.putExtra("actionTitle", actionTitle);
            intent.setClass(CollectListActivity.this, ActionTitleCompleteActivity.class);
            startActivity(intent);
            finish();
    		break;
    	}
    	}
    	
    	
        return super.onContextItemSelected(item);
    }
    
    private void delete(final long rowId, final int itemId){
        if(rowId>0){
            new AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_string) + "  " + getNameById(rowId))
            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	DataBaseOpenHelper.getInstance().deleteCollect((int)rowId);
                	DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
                    
                    Cursor cursor = dbHelper.selectCollect();
                    
                    startManagingCursor(cursor);
                    
                    listAdapter = new SimpleCursorAdapter(
                    		CollectListActivity.this, 
                    		android.R.layout.simple_expandable_list_item_1,
                    		cursor,
                    		new String[]{"content"},
                    		new int[] {android.R.id.text1});
                    
                    contentList.setAdapter(listAdapter);
                	
                }
            })
            .setNegativeButton(getString(R.string.cancel), null)
            .show();
        }
        
    }
    
    private String getNameById(long id){
    	DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
    	Long itemId = Long.valueOf((int)id);
    	Cursor cursor = dbHelper.selectCollect(itemId);
    	String content = "";
    	if (cursor.moveToFirst()){
    		int index = cursor.getColumnIndex("content");  
            content =  cursor.getString(index);
    	}

        return content;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {  
        menu.add(0, 0, 0, R.string.add);  
        //menu.add(0, 1, 1, R.string.delete_string);  
        //menu.add(0, 2, 2, R.string.move_to_project_string);    
        return true;    
    }  
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) 
    {  
        int itemid = item.getItemId();  
        switch(itemid){  
        
        	case 0: //增加收集
        	{
        		Intent intent = new Intent();
        		intent.putExtra("functionName", "add");
        		intent.setClass(CollectListActivity.this, CollectDetailActivity.class);
        		startActivity(intent);
        		break;  
        	}
        	case 1: //删除
        	{
        		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        		delete(info.id, item.getItemId());
        		break;  
        	}
        	case 2: //加入项目列表
        	{
        		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        		Intent intent = new Intent();
        		//Intent传递参数
        		Long collectId = Long.valueOf(info.id);
        		TextView textView = (TextView)info.targetView;
        		String projectTitle= textView.getText().toString(); 
        		intent.putExtra("collectId", collectId.toString());
        		intent.putExtra("projectTitle", projectTitle);
        		intent.setClass(CollectListActivity.this, ProjectTitleCompleteActivity.class);
        		startActivity(intent);
        		finish();
        		break;  
        	}
        	case 3:  //加入行动列表
        	{
         		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
       		 	Intent intent = new Intent();
                //Intent传递参数
       		 	Long collectId = Long.valueOf(info.id);
       		 	
       		 	TextView textView = (TextView)info.targetView;
       		 	String actionTitle= textView.getText().toString(); 
                intent.putExtra("collectId", collectId.toString());
                intent.putExtra("actionTitle", actionTitle);
                intent.setClass(CollectListActivity.this, ActionTitleCompleteActivity.class);
                startActivity(intent);
                finish();
        		break;
        	}
        	default:  
        		break;  
        }  
        return true;  
    }  
}
