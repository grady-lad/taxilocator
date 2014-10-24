package com.mog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 *  @author Martin O'Grady (B00028697)
 *  date: 29/04/12
 *  Objective: This service will be bounded to the activity which is used to show the taxis locations
 *             the activity that is bounded to this service will return the whereabouts of a users
 *             location, and also a JSON file containing the closest taxis
 */

public class taxiLocation extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private JSONObject o;
    private LocationManager locationManager;
    private Location location;
    private String context;
    private String provider;
    private double UserLat;
    private double UserLng;
    private double UserLat1;
    private double UserLng1;
    private GeoPoint UserLocation;
    private BufferedReader in;
    private BufferedReader init;
    

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        taxiLocation getService() {
            // Return this instance of LocalService so clients can call public methods
            return taxiLocation.this;
        }
    }

    //Once the activity is bounded to the service we set the criteria to obtain a location
    // and get that location
    public IBinder onBind(Intent intent) {
    	setCritera();
    	getUserLocation();
    	return mBinder;
        
    }
    
    public void setCritera(){
    	
    	context = Context.LOCATION_SERVICE;
        this.locationManager = (LocationManager)getSystemService(context);
        this.provider = LocationManager.NETWORK_PROVIDER;// use network_provider to get location
        location = locationManager.getLastKnownLocation(provider);//gets the whereabouts of the users previous location
    	
    }
    
    public GeoPoint getUserLocation(){
		
    	if (location != null) {
			  
    		//These Lat and Lng values will be used to map the users location
    		UserLat = location.getLatitude() * 1E6;
			UserLng = location.getLongitude() * 1E6;
			
			//These Lat and Lng values will be used to send the users Location to the database
			UserLat1 = location.getLatitude();
			UserLng1 = location.getLongitude();
			
			UserLocation = new GeoPoint( (int) UserLat ,(int) UserLng);
    	}
    	
    	//call the method to get the closest taxis 
    	getLocations();
    	return UserLocation;
    		
    }

    
    /**
     * @return
     * This method will send the users location to the web back end and download a JSON file 
     * containing the closest taxis. The method will then return the JSON file 
     */
    
    
    public JSONObject getLocations() {
    	
    	try{
    		/**
    		 * Send user location to the web back end 
    		 */
    		
    		URL CoPage = new URL("http://www.closest-taxis.com/GetCo.php?Lat="+UserLat1+"&Lng="+UserLng1);
    		URLConnection FirstCon = CoPage.openConnection();
    	    init = new BufferedReader(new InputStreamReader(FirstCon.getInputStream()));
    		
    		String line1,s1="";
    		
    		while ((line1 = init.readLine()) != null) 
    		{
    			s1=s1+line1;
    		}
    		
    		/**
    		 * Request the JSON file from the web backend 
    		 */
    		URL twitter = new URL("http://www.closest-taxis.com/results.json");
    		URLConnection tc = twitter.openConnection();
    		in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
    		
    		String line,s = "";
    		
    		while ((line = in.readLine()) != null) 
    		{
    			s=s+line;
    		}
    		
    		/**
    		 * Create a JSON Object based on the JSON page
    		 */
    	    o = new JSONObject(s);
    	    		
    		
    	}catch(Exception e){
    		
    	Toast.makeText(this, "no taxis near your location", Toast.LENGTH_LONG).show();	
    		
    	}
    	
    	finally{
    		
    	try{
    		
    		init.close();
    		in.close();
    		
    		
    		
    	}catch(Exception e){
    		
    		
    Toast.makeText(this, "Could Not Close Connection", Toast.LENGTH_LONG).show();	
    		
    	}
    		
    	}
    	
    	
    	return o;
    	
   
    	}
    	
    }

    		
    		
   
    		
    	   
        	
    		
	
    
    	
    	
    
    
   
