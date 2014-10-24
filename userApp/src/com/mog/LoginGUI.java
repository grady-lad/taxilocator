package com.mog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
import android.widget.EditText;
import android.widget.Toast;

public class LoginGUI extends Activity {
	
private EditText UserNameText;
private EditText PasswordText;
private String UserNameString;
private String PasswordString;
private Button LoginButton;
private String line1;
private String s1;
private int s2;
private int LoginSuccess;
private int LoginFail;
private String UserIDString;
private Bundle SendBundle;
private BufferedReader init;
	
	public void onCreate(Bundle savedInstanceState) {
		   
		   super.onCreate(savedInstanceState);
		        setContentView(R.layout.main3);
		        setUpVariables();
		        Bundle bundle = getIntent().getExtras();
				UserIDString = (String) bundle.getString("id");
		        
		    }

	
	
	
	public void setUpVariables(){
		
		LoginSuccess = 1;
		LoginFail = 2;
		SendBundle = new Bundle();
		
		UserNameText = (EditText) findViewById(R.id.UserNameText);
		PasswordText = (EditText) findViewById(R.id.PassText2);
		
		LoginButton = (Button) findViewById(R.id.LoginButton);
    	LoginButton.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v) 
    		{
    			try {
    				LoginDetails(v);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				
    			}
    		}
    	}
    	);
		
	}
	
	public void LoginDetails(View v){
		
		UserNameString = UserNameText.getText().toString();
		PasswordString = PasswordText.getText().toString();
		
		
		try{
			   URL CoPage = new URL("http://www.closest-taxis.com/LoginUser.php?ID=" + UserNameString +"&Pass=" + PasswordString);
			    	URLConnection FirstCon = CoPage.openConnection();
			    	 init = new BufferedReader(new InputStreamReader(FirstCon.getInputStream()));
			    	
			    	 line1 = "";
			    	 s1 = "";
			    	
			    	while ((line1 = init.readLine()) != null) 
			    	{
			    		s2 = Integer.parseInt(s1+line1);
			    	}
			    	
			    	if(s2 == LoginSuccess){
			    		
			    		Context context = getApplicationContext();
			 			CharSequence text = "Login Successful";
			 			int duration = Toast.LENGTH_SHORT;

			 			Toast toast = Toast.makeText(context, text, duration);
			 			toast.show();
			 			
			 			Intent MyIntent = new Intent((v.getContext()), SendLocation.class);
			 			SendBundle.putString("id", UserNameString);
			 			MyIntent.putExtras(SendBundle);
		                startActivityForResult(MyIntent, 0);
			    		
			    		
			    		
			    	}
			    	
			    	else if(s2 == LoginFail){
			    		
			    		
			    		Context context = getApplicationContext();
			 			CharSequence text = "Please Make sure your entering your password correctly";
			 			int duration = Toast.LENGTH_SHORT;

			 			Toast toast = Toast.makeText(context, text, duration);
			 			toast.show();	
			    		
			    		
			    	}

				   
			    	
			    	}catch(Exception e){
			    		
			    		Context context = getApplicationContext();
			 			CharSequence text = "Do you have a valid internet connection ?" + e;
			 			int duration = Toast.LENGTH_SHORT;

			 			Toast toast = Toast.makeText(context, text, duration);
			 			toast.show();
			    		
			    		
			    	}
		
		finally{
			
			try{
				
				init.close();
				
			}catch(Exception e){
				
				
				
				
			}
			
		}
		
		
		
	}
}
