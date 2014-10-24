package com.mog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Martin O'Grady (B00028697)
 * date: 27/04/1990
 * Objective: This activity will be show when the app first loads it will show a button 
 *            which will be used to load the map the shows the users and taxis locations
 *
 */

public class GuiFront extends Activity  {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        setUpVariables();
    }
	
  public void setUpVariables(){
	  
	  
	Button load = (Button) findViewById(R.id.button1);
  	load.setOnClickListener(new View.OnClickListener() {
          
  		public void onClick(View view) {
              Intent MyIntent = new Intent((view.getContext()), ShowMap.class);
              startActivityForResult(MyIntent, 0);
          }

      });
  
  
  }
	  
  }


