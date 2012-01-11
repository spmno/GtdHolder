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

public class ProjectTitleCompleteActivity extends Activity {
	private Button		finishButton;
	private EditText	contentText;
	private long		collectId;
	private String projectTitle;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        
        setTitle(R.string.change_project_title);
        finishButton = (Button)findViewById(R.id.completeProjectButton);
        contentText = (EditText)findViewById(R.id.projectTitleTextView);
        
        Intent intent = getIntent();
        String value = intent.getStringExtra("collectId");
        collectId = Long.parseLong(value);
        projectTitle = intent.getStringExtra("projectTitle");
        contentText.setText(projectTitle);
        
        finishButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		String collectContent =  contentText.getText().toString();
        		Toast.makeText(ProjectTitleCompleteActivity.this, 
        				getString(R.string.project_prompt), 
        				Toast.LENGTH_SHORT).show(); 
        		Intent intent = new Intent();
        		intent.putExtra("projectTitle", projectTitle);
        		intent.setClass(ProjectTitleCompleteActivity.this, ProjectListDetailActivity.class);
        		startActivity(intent);
        		DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        		dbHelper.insertWorkProject(collectContent);
        		dbHelper.deleteCollect(collectId);
        		finish();
        	}
        });
	}
}
