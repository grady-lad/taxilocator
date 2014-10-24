package com.mog;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


/**Author: Martin O'Grady
 * Date: 29/04/12
 * Description: This activity will the first activity that loads when the user loads the app
 *              it will prompt the user with two options: Login or Register and load an activity
 *              based on the users selection
 */




public class WelcomeGUI extends Activity implements OnClickListener {

public String UserIDString; //Used to create ID for the user
public Intent LoginIntent; //Intent used to start the login activity
public Intent RegIntent; // Intent used to start the registration activity
public Bundle LoginBundle; //This bundle will pass data to the login activity
public Bundle RegBundle; //This bundle will pass data to the registration activity


   /**
    * The onCreate method will layout the activity according to main2.xml
    * This method will also call the two methods that need to be perormed once the activity
    * has started	
    */

	public void onCreate(Bundle savedInstanceState) {
		   
		   super.onCreate(savedInstanceState);
		        setContentView(R.layout.main2);//use main2.xml to desgin the activity
		        setUpVariables();
		        createId();
		    }
	
	/**
	 * The following method creates the GUI components for the activity and adds clicklisteners
	 */
	
	public void setUpVariables(){
		
		Button LoginButton = (Button) findViewById(R.id.LoginButton);
		Button RegisterButton = (Button) findViewById(R.id.RegisterButton);
		
		LoginBundle = new Bundle();
        RegBundle = new Bundle();
		
		LoginButton.setOnClickListener((OnClickListener) this);
		RegisterButton.setOnClickListener((OnClickListener) this);
		
	}
	
	/**
	 * createId will create an id a unique ID for the user will will be made up of the devices
	 * serial number, android id, and device id
	 */
	
	public void createId(){

	    TelephonyManager tm = (TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
	    final String DeviceId, SerialNum, androidId;
	    DeviceId = tm.getDeviceId(); //gets the device id
	    SerialNum = tm.getSimSerialNumber();// gets device serial number
	    androidId = Secure.getString(getContentResolver(),Secure.ANDROID_ID);// gets android id
	    
	    //below makes a unique id for the device based on the three strings above and hash's it to have a length < 32
	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)DeviceId.hashCode() << 32) | SerialNum.hashCode());
	    UserIDString = deviceUuid.toString();	
	   
	    }
	
	
		
	/**
	 * This method will perform neccarcy actions if a button has been clicked 
	 */
    public void onClick(View v){
    	
    	
    	/**
    	 * The following switch statement will determine will button has been clicked 
    	 * and load the corresponding activity to that button
    	 */
    	
        switch(v.getId()){
        
        case R.id.LoginButton:
        	
        	Intent MyIntent = new Intent((v.getContext()), LoginGUI.class);//Create an Intent for the login activity
        	
        	LoginBundle.putString("id" , UserIDString);//put useridstring into the login bundle
 		    MyIntent.putExtras(LoginBundle);// put the bundle into the login intent
            startActivityForResult(MyIntent, 0);// start the login intent
        	
        	break;
        	
        case R.id.RegisterButton:
        	
        	Intent MyIntent2 = new Intent((v.getContext()), RegisterGUI.class);//create intent for registration activity
          
        	RegBundle.putString("id" , UserIDString);//put the useridstring into the registration bundle
 		    MyIntent2.putExtras(RegBundle);//add the bundle to the registration intent
            startActivityForResult(MyIntent2, 0);//// load the intent which is the registration activity 
        	
        	break;
        	
        }
        
}

}
