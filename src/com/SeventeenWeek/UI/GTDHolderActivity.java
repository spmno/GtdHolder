package com.SeventeenWeek.UI;

import java.util.ArrayList;

import com.SeventeenWeek.R;
import com.SeventeenWeek.DB.DataBaseOpenHelper;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.content.Intent;

public class GTDHolderActivity extends Activity {
    /** Called when the activity is first created. */
	/* 用GRIDVIEW的实现
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle(R.string.gtdholer_title);
        GridView gridView = (GridView)findViewById(R.id.mainGridView);
        gridView.setAdapter(new ImageAdapter(this));
        
        gridView.setOnItemClickListener(new OnItemClickListener() {  
        	  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
                //弹出单击的GridView元素的位置  
                //Toast.makeText(GTDHolderActivity.this,mThumbIds[position], Toast.LENGTH_SHORT).show();
                switch(position)
                {
                case 0:
                {
            		Intent intent = new Intent();
            		intent.setClass(GTDHolderActivity.this, CollectActivity.class);
            		Toast.makeText(GTDHolderActivity.this,getString(R.string.collect_prompt), Toast.LENGTH_SHORT).show();
            		startActivity(intent);
            		//GTDHolderActivity.this.finish();
                	break;
                }
                case 1:
                {
                	Intent intent = new Intent();
                	intent.setClass(GTDHolderActivity.this, WorkListActivity.class);
                	startActivity(intent);
                	break;
                }
                default:
                	break;
                
                }
          
            }  
        }); 
        
        DataBaseOpenHelper.initOpenHelper(this);
        
    }
    
    private class ImageAdapter extends BaseAdapter{  
        private Context mContext;  
  
        public ImageAdapter(Context context) {  
            this.mContext=context;  
        }  
  
        @Override  
        public int getCount() {  
            return mThumbIds.length;  
        }  
  
        @Override  
        public Object getItem(int position) {  
            return mThumbIds[position];  
        }  
  
        @Override  
        public long getItemId(int position) {  
            // TODO Auto-generated method stub  
            return 0;  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            //定义一个ImageView,显示在GridView里  
            ImageView imageView;  
            if(convertView==null){  
                imageView=new ImageView(mContext);  
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));  
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);  
                imageView.setPadding(8, 8, 8, 8);  
            }else{  
                imageView = (ImageView) convertView;  
            }  
            imageView.setImageResource(mThumbIds[position]);  
            return imageView;  
        }  
          
  
          
    }  
    //展示图片  
    private Integer[] mThumbIds = {  
           R.drawable.img1,
           R.drawable.img2,
           R.drawable.img3,
           R.drawable.img4
    };  
    */
	
	private ArrayList<String> functionListString;
	ListView functionList;
	private ListAdapter listAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        setTitle(R.string.gtdholer_title);
        functionList = (ListView)findViewById(R.id.work_list);
        functionListString = new ArrayList<String>();
        functionListString.add(getString(R.string.inbox));
        functionListString.add(getString(R.string.work_list));
        functionListString.add(getString(R.string.review));
        //functionListString.add(getString(R.string.today));
        functionListString.add(getString(R.string.future));
        //functionListString.add(getString(R.string.info));
        
        listAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, 
        		functionListString);
        
        functionList.setAdapter(listAdapter);
        
        DataBaseOpenHelper.initOpenHelper(this);
        
        functionList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	switch(arg2)
            	{
            	case 0:
            	{
            		Intent intent = new Intent();
            		intent.setClass(GTDHolderActivity.this, CollectListActivity.class);
            		Toast.makeText(GTDHolderActivity.this,getString(R.string.collect_prompt), Toast.LENGTH_SHORT).show();
            		
            		startActivity(intent);
            		break;
            	}
            	
            	case 1:
            	{
            		Intent intent = new Intent();
            		intent.setClass(GTDHolderActivity.this, WorkListActivity.class);
            		startActivity(intent);
            		break;
            	}
               	case 2:
            	{
            		Intent intent = new Intent();
            		intent.setClass(GTDHolderActivity.this, ReviewListActivity.class);
            		startActivity(intent);
            		break;
            	}
            	
            	/*case 3:
            	{
            		Intent intent = new Intent();
            		intent.setClass(GTDHolderActivity.this, ActionListActivity.class);
            		startActivity(intent);
            		break;
            	}*/
               	case 3:
            	{
            		Intent intent = new Intent();
            		intent.setClass(GTDHolderActivity.this, FutureListActivity.class);
            		Toast.makeText(GTDHolderActivity.this,getString(R.string.future_prompt), Toast.LENGTH_SHORT).show();
            		startActivity(intent);
            		break;
            	}
            	
            	case 5:
            	{
            		Intent intent = new Intent();
            		intent.setClass(GTDHolderActivity.this, ActionListActivity.class);
            		startActivity(intent);
            		break;
            	}
            	}
            }
        });
	}
    
   
}  
