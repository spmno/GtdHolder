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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;

import android.view.View.OnCreateContextMenuListener;
import android.content.Intent;
import android.content.DialogInterface;
import android.database.Cursor;

public class ProjectListDetailActivity extends Activity {
	private Button addButton;
	private Button backButton;
	private ListView contentList;
	private ListAdapter listAdapter;
	private String	projectTitle;
	private long		projectId;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_edit);
        //setTitle(R.string.work_list_title);
        //addButton = (Button)findViewById(R.id.button_collect_add);
        //backButton = (Button)findViewById(R.id.button_collect_back);
        contentList = (ListView)findViewById(R.id.collect_list_view);
        Intent intent = getIntent();
        projectTitle = intent.getStringExtra("projectTitle");
        setTitle(projectTitle);
        /*
        addButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		Integer tempProjectId = Integer.valueOf(projectId);

                intent.putExtra("projectId", tempProjectId.toString());
                intent.setClass(ProjectListDetailActivity.this, ActionDetailActivity.class);
        		startActivity(intent);
        		//CollectActivity.this.finish();
        	}
        });
        
        backButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		
        		ProjectListDetailActivity.this.finish();
        	}
        });
        */
        DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        Cursor projectCursor = dbHelper.selectWorkProject(projectTitle);
        if (projectCursor.moveToFirst()){
        	 int idColumnIndex = projectCursor.getColumnIndex("_id");
             projectId = projectCursor.getInt(idColumnIndex);
             
             Cursor cursor = dbHelper.selectWorkProjectAction(projectId);
             
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
                 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                         long arg3) {
                     //setTitle("点击第"+arg2+"个项目");
                 }
             });
             
             contentList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
                 
                 @Override
                 public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
                     //String title = getString(R.string.collect_title);
                     //String deleteString = getString(R.string.delete_string);
                 	menu.setHeaderTitle(R.string.project); 
                     menu.add(0, 1, 0, R.string.delete_string);
                     //menu.add(0, 1, 0, "弹出长按菜单1"); 
                 }
             }); 
    	}
       
        
 
        
	}
	
	//长按菜单响应函数
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    	
    	delete(info.id, item.getItemId());
    	
        return super.onContextItemSelected(item);
    }
    
    private void delete(final long rowId, final int itemId){
        if(rowId>0){
            new AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_string) + "  " + getNameById(rowId))
            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
                	dbHelper.deleteWorkAction((int)rowId);
                	
                    Cursor projectCursor = dbHelper.selectWorkProject(projectTitle);
                    int idColumnIndex = projectCursor.getColumnIndex("_id");
                    long projectId = projectCursor.getInt(idColumnIndex);
                    
                    Cursor cursor = dbHelper.selectWorkProjectAction(projectId);
                    
                    startManagingCursor(cursor);
                    
                    listAdapter = new SimpleCursorAdapter(
                    		ProjectListDetailActivity.this, 
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
    	Long itemId = Long.valueOf(id);
    	Cursor cursor = dbHelper.selectWorkAction(itemId);
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
        		Long tempProjectId = Long.valueOf(projectId);

                intent.putExtra("projectId", tempProjectId.toString());
                intent.putExtra("functionName", "add");
        		intent.setClass(ProjectListDetailActivity.this, ActionDetailActivity.class);
        		startActivity(intent);
        		break;  
        	}
        	case 1: //删除
        	{
        		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        		delete(info.id, item.getItemId());
        		break;  
        	}
     
        	default:  
        		break;  
        }  
        return true;  
    }  
}


