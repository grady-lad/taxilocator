package com.mog;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author Martin O'Grady 
 * date: 29/04/12
 * Objective: The objective of this class is to start/stop the service that is responsible
 *            for sending the apps location
 */


public class SendLocation extends Activity {
  
	
private ScrollView sv;
private LinearLayout ll;
private TextView tv;
private Button b2;
private Button b1;
private Intent Int1;
private Bundle b;
private String data;

   public void onCreate(Bundle savedInstanceState) {
   
	//The following will setup the necessary methods to create the buttons for the activity
	// and also get the username string from the activity that started it
   super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        declarer();
        builder();
        Bundle bundle = getIntent().getExtras();
		data = (String) bundle.getString("id");//get the username string
        Int1 =new Intent(this,GetLocation.class); //intent for get location service
         b = new Bundle();
    }
    
   
	   
   
    private void builder() 
    {	   
    	
    	//set up the view for the class 
    	ll.addView(b2);
    	ll.addView(b1);
    	b2.setText("Send Location");
    	b1.setText("Stop");
    	ll.addView(tv);
    	sv.addView(ll);    	    
    	this.setContentView(sv);
    }

    private void declarer()
    {
    	//set up the GUI components 
    	sv= new ScrollView(this);
    	ll=new LinearLayout(this);
    	ll.setVerticalScrollBarEnabled(true);
    	tv=new TextView(this);
    	b2=new Button(this);
    	b2.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v) 
    		{
    			//the following will start the sevrice for sending the location
    			try {
    				
    	        b.putString("id" , data);
    		    Int1.putExtras(b);
    			startService(Int1);
    			
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				tv.setText("problemo \n"+e);e.printStackTrace();
    			}
    		}
    	}
    	);
    	
    	b1=new Button(this);
    	b1.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v) 
    		{
    			try {
    				
    			stopService(Int1);
    				
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				tv.setText("problemo \n"+e);e.printStackTrace();
    			}
    		}
    	}
    	);
    		
    	
    	}
    
  
   
    
}