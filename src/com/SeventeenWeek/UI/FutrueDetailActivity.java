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

public class FutrueDetailActivity extends Activity {
	private Button		finishButton;
	private EditText	contentText;
	private String 		functionString;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_detail);
        setTitle(R.string.future);
        finishButton = (Button)findViewById(R.id.button_collect_detail_finish);
        contentText = (EditText)findViewById(R.id.editText_collect_detail_content);
        
        Intent intent = getIntent();
        functionString = intent.getStringExtra("functionName");
        
        if (functionString.equals("edit")){
        	String contentString = intent.getStringExtra("collectContent");
        	contentText.setText(contentString);
        }else{
        	
        }
        	
        intent.getIntExtra("actionId", 0);
        finishButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		String collectContent =  contentText.getText().toString();
        		Toast.makeText(FutrueDetailActivity.this, 
        				collectContent + " " + getString(R.string.add_success), 
        				Toast.LENGTH_SHORT).show(); 

        		DataBaseOpenHelper dbHelper = DataBaseOpenHelper.getInstance();
        		Intent intent = getIntent();
        		if (functionString.equals("edit")){
        			long id = intent.getLongExtra("collectId", 0);
        			dbHelper.updateFutrue(id, collectContent);
        		}else{
        			dbHelper.insertFutrue(collectContent);
        		}
        		
        		FutrueDetailActivity.this.finish();
        	}
        });
	}
}
