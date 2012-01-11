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



public class ActionDetailActivity extends Activity {
	private Button		finishButton;
	private EditText	contentText;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_detail);
        setTitle(R.string.action_content_title);
        finishButton = (Button)findViewById(R.id.button_collect_detail_finish);
        contentText = (EditText)findViewById(R.id.editText_collect_detail_content);
        
        finishButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		String collectContent =  contentText.getText().toString();
        		Toast.makeText(ActionDetailActivity.this, 
        				collectContent + " " + getString(R.string.add_success), 
        				Toast.LENGTH_SHORT).show(); 
        		//Intent intent = new Intent();
        		//intent.setClass(CollectDetailActivity.this, CollectActivity.class);
        		//startActivity(intent);
        		Intent intent = getIntent();
                String projectIdString = intent.getStringExtra("projectId");
                String functionString = intent.getStringExtra("functionName");
                DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
                
                if (functionString.equals("add")){
                	long projectId = Long.parseLong(projectIdString);   
            		
            		if (projectId == -1){
            			dbHelper.insertNullWorkAction(collectContent);
            		}else
            		{
            			dbHelper.insertWorkAction(collectContent, projectId, 0);
            		}
                }else{
                	String actionIdString = intent.getStringExtra("actionId");
                	Long actionId = Long.parseLong(actionIdString);
                	dbHelper.updateWorkActionContent(actionId, collectContent);
                }
       		
        		ActionDetailActivity.this.finish();
        	}
        });
	}
}
