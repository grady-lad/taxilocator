package com.mog;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * @author Martin O'Grady
 * date: 29/04/2012
 * Objective: The objective of this class is to register the users details into the web backend
 */


public class RegisterGUI extends Activity {
	
private EditText FirstNameText;
private EditText LastNameText;
private EditText PhoneNumText;
private EditText PasswordText;
private EditText UserNameText;
private Button RegisterButton;
private int AcceptedInt;
private int RejectedInt;
private String UserNameString;
private String FirstNameString;
private String LastNameString;
private String PhoneNumString;
private String PasswordString;
private String IDString;
private String line1;
private String s1;
private Integer s2;
private Bundle SendBundle;
private BufferedReader init;


	/**
	 * This method will get the the information from the activity that started it
	 * in this case this it will be getting the device id from the welcomeGUI activity
	 * it will also call the methods that need to be executed when this activity is first loaded
	 */

	public void onCreate(Bundle savedInstanceState) {
		   
		   super.onCreate(savedInstanceState);
		        setContentView(R.layout.main4);
		        setUpVariables();
		        Bundle bundle = getIntent().getExtras();
				IDString = (String) bundle.getString("id");// get the string which has the identifier "id"
		    }
	
	
    /** This method will set up the necessary variables and create the GUI components for the activity
	  *
	  */
	public void setUpVariables() {
		
		AcceptedInt = 1; // this number will be used to indicate that the registration successful
		RejectedInt = 2; // this number will be used to indicate that the registration was unsuccessful 
		
		SendBundle = new Bundle();//this bundle will be used to pass the username to the send location activity
		
		UserNameText = (EditText) findViewById(R.id.UserText);//text field to get the username
		FirstNameText = (EditText) findViewById(R.id.FirstText);// text field to get first name
		LastNameText = (EditText) findViewById(R.id.LastText);// text field to get last name
    	PhoneNumText = (EditText) findViewById(R.id.PhoneText);// test field to get phone num
    	PasswordText = (EditText) findViewById(R.id.PassText);//text field to get password
    	
    	UserNameText.setText("Enter UserName Here");
    	FirstNameText.setText("Enter First Name Here");
    	LastNameText.setText("Enter Last Name Here");
    	PhoneNumText.setText("Enter Phone Number Here");
    	
    	RegisterButton = (Button) findViewById(R.id.TheRegister);
    	RegisterButton.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v) 
    		{
    			try {
    				RegisterDetails(v);// method which will be performed when the registration button is clicked
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				
    			}
    		}
    	}
    	);
    	
	}
	
	public void RegisterDetails(View v){
		
		//the following gets the inputs of the textfields 
		UserNameString = UserNameText.getText().toString();
		FirstNameString = FirstNameText.getText().toString();
		LastNameString = LastNameText.getText().toString();
		PhoneNumString = PhoneNumText.getText().toString();
		PasswordString = PasswordText.getText().toString();
		
		
	/**
	 * The following will add the inputs to the text field to the URL which contains the 
	 * location of the PHP page which will register the users details. It will then 
	 * download a response from the PHP page and check whether the response is a 
	 * 1 (successful) or 2 (unsuccessful)
	 */
		
	try{
		
	        URL CoPage = new URL("http://www.closest-taxis.com/RegisterUser.php?FName=" + FirstNameString + "&LName=" + LastNameString + "&ID=" + UserNameString + "&Phone=" + PhoneNumString + "&Pass=" + PasswordString);
	    	URLConnection FirstCon = CoPage.openConnection(); // open a connection to the url above
	    	init = new BufferedReader(new InputStreamReader(FirstCon.getInputStream()));//get the content
	    	
	    	 line1 = "";
	    	 s1 = "";
	    	
	    	while ((line1 = init.readLine()) != null) 
	    	{
	    		s2 = Integer.parseInt(s1+line1); //parse the PHP page repsonse into an integer
	    	}
	    	
	    	
	    	/**
	    	 * The following checks if the PHP page response has a value of one or two
	    	 */
	    	
	    	if(s2 == AcceptedInt){
	    		
	    		//inform user registration successful
	    		Context context = getApplicationContext();
	 			CharSequence text = "Registration Succesful";
	 			int duration = Toast.LENGTH_LONG;

	 			Toast toast = Toast.makeText(context, text, duration);
	 			toast.show();
	 			
	 			//add username to the send location intent and load the send location intent
	 			Intent MyIntent = new Intent((v.getContext()), SendLocation.class);
	 			SendBundle.putString("id", UserNameString);
	 			MyIntent.putExtras(SendBundle);
                startActivityForResult(MyIntent, 0);
	    		
	    		
	    		
	    	}
	    	
	    	else if(s2 == RejectedInt){
	    		
	    	  //inform user that registration was unsuccessful
	    		Context context = getApplicationContext();
	 			CharSequence text = "Try picking a different username";
	 			int duration = Toast.LENGTH_SHORT;

	 			Toast toast = Toast.makeText(context, text, duration);
	 			toast.show();	
	    		
	    		
	    		
	    	}

		   
	    	
	    	}catch(Exception e){
	    		
	    		Context context = getApplicationContext();
	 			CharSequence text = "Please make sure you have a valid internet connection";
	 			int duration = Toast.LENGTH_SHORT;

	 			Toast toast = Toast.makeText(context, text, duration);
	 			toast.show();
	    		
	    		
	    	}
		
		finally{
			
			try{
				
				//close the connection
				init.close();
				
			}catch(Exception e){
				
				
				
			}
			
		}
			
	}
			
		
}

	
