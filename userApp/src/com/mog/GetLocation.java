package com.mog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import android.location.LocationListener;

/**
 * @author Martin
 * date: 29/04/12
 * Objective: This service will get the coordinates of the application perodically 
 *            and send them locations to the web back end 
 */

public class GetLocation extends Service  {

	private double lat;
	private double lng;
	private double lat2;
	private double lng2;
	private LocationManager locationManager;
	private LocationListener locationlistener;
	private String data;
    private URLConnection tc;
    private BufferedReader in;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/**
	 * This method will be used when the activity is first started. The method defines how 
	 * a location will be obtained and how often the location should be obtained.
	 */
	
	public void onStart(Intent intent, int startid) {
		
		
		Bundle bundle = intent.getExtras();
		data = (String) bundle.getString("id");// get the user id string
		
		lat2 = 0;//used to set latitude value to 0 if stopped requesting location updates
		lng2 = 0;//used to set longitude value to 0 if stopped requesting location updates
		
        int minTime = 30000; // 30 seconds in milliseconds (how often location will be requested)
		
	    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    
	    MyLocationListener myLocListener = new MyLocationListener();
	    
	    locationlistener = myLocListener;
	    
	    /**
	     * Get location fix via GPS , every 30 seconds and use the mylocationlister to get the updates
	     */
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, 1, myLocListener );
		
		Toast.makeText(this, "Sending Co-ordinates this may take a while" , Toast.LENGTH_LONG).show();
		
		
	}
	
	/**
	 *  @author Martin
	 * date:29/04/12
	 * Objective: This class requests locations updates based on the criteria above
	 */
	
	public class MyLocationListener implements LocationListener
	{

		public void onLocationChanged(Location loc) {
			
			/**
			 * Checks if location does not equal to null and passes the location results
			 * to the sendCo method
			 */
			if(loc != null){
				
			 lat = loc.getLatitude();
			 lng = loc.getLongitude();
			    
		SendCo(lat = loc.getLatitude() , lng = loc.getLongitude());
			 
				  
	    }
	
			
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		
       }
	   /**
	    * This method will stop listening for location updates and call the method to send the
	    * coordinates to the web back end , and hand it lat and long values with the value of 0
	    */
	    public void onDestroy() {
		Toast.makeText(this, "Stopped Sending Co-ordinates", Toast.LENGTH_LONG).show();
		SendCo(lat2 , lng2);
		locationManager.removeUpdates(locationlistener);
		
	    }
	
	
	    /**
	     * @param lat1
	     * @param lng1
	     * 
	     * This method will recieve the lat and long values from the on destroy method or location listener method
	     * and insert these locations into the database on the web backend.
	     */
	    public void SendCo(double lat1 , double lng1){
		
		try{
		
	    URL twitter = new URL("http://www.closest-taxis.com/update.php?id='" + data + "'" + "&latt=" + lat1 + "&lngg=" + lng1);

		Toast.makeText(this, "sent " + lat1 + " " + " " + lng1, Toast.LENGTH_LONG).show();

		 tc = twitter.openConnection();
		 in = new BufferedReader(new InputStreamReader(tc.getInputStream()));

		String line,s="";
		
		while ((line = in.readLine()) != null) 
		{
			s=s+line;
		}
		
		}catch(Exception e){
			
			Toast.makeText(this, "Please Make Sure You Have Your Internet Connection Turned On", Toast.LENGTH_LONG).show();
	
		}
		
		finally{
			
		try{
			
			in.close();
			
		}catch(Exception e){
			
		Toast.makeText(this, "Buffered Reader could not close", Toast.LENGTH_LONG).show();	
			
		}
			
			
			
		}


}
	
	
	
	

}

