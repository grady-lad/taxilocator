package com.mog;


import java.util.List;


import java.util.Vector;


import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.mog.taxiLocation.LocalBinder;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
/**
 * @author Martin O'Grady (B00028697)
 * date: 29/0412
 * Objective: This activity will display the locations of the user and the closest taxis 
 *            on a google map API it request these locations from the service it is bounded to
 *         
 */

public class ShowMap extends MapActivity {
	
	private MapView mapView;
    private OverlayItem overlayitem;
    private OverlayItem UsersOverlay;
	private List<Overlay> mapOverlays;
	private TaxiItemizedOverlay itemizedOverlay;
	private UserItemizedOverlay UserItemOverlay;
    private Drawable drawable;
    private Drawable UserDrawable;
    boolean bounder = false;
    private taxiLocation loc;
    private Handler handler = new Handler();
    private MapController mapController;
    private GeoPoint User;
    private Vector<GeoPoint> TaxisVec;
    private Vector<Integer> TaxiLat;
    private Vector<Integer> TaxiLng;
    private Vector<String> TaxiFName;
    private Vector<String> TaxiLName;
    private Vector<Integer> TaxiPhone;
    private JSONObject taxiLocation;
    private JSONArray a;
    private String TaxiPhoneNum;
    private String TaxiName;
    private static Context context;
    
    //public ShowMap(HelloItemizedOverlay helloItemizedOverlay) {
		// TODO Auto-generated constructor stub
	//}

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        onStart();
        setUpMap();
		
		
    }
	
	protected void onDestroy(){
		
		onStop();
		
	}
    
    /**
     * This method bind the activity to the local service 
     * and start the thread which will run every 45 seconds to request the closest taxi locations
     */
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, taxiLocation.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        startHandlerTask();
        Context context = getApplicationContext();
		CharSequence text = "Please Wait while we try to get your coordinates";
	    int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
			
        
        
    }

    //used to unbind from the service
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (bounder) {
            unbindService(mConnection);
            bounder = false;
        }
    }
        	
        
        /**
         * The following will create the map and create relevant variables for it such as overlays
         */
        public void setUpMap(){
        	
        	mapView = (MapView) findViewById(R.id.mapview);    
            mapView.setBuiltInZoomControls(true);
            mapView.setStreetView(true);
    		
            //This will be the list for the markers
    		mapOverlays = mapView.getOverlays();
    		  
    		drawable = this.getResources().getDrawable(R.drawable.combined1);//Marker for taxi drivers
    		UserDrawable = this.getResources().getDrawable(R.drawable.androidmarker);//Marker for users
    		
    		itemizedOverlay = new TaxiItemizedOverlay(drawable, this);//Instantiate itemized overlay class 
    		UserItemOverlay = new UserItemizedOverlay(UserDrawable, this);// Instantiate user overlay class
    		
		    mapController = mapView.getController();
			mapView.postInvalidate();//refresh the map
			mapController.setZoom(13);// set the zoom of the map when first loaded
			
		   
		}
        
        /**
         * The handler allows multiple UI components to run alongside each other
         * it also starts the thread of requesting and mapping locations 
         */
        public void startHandlerTask(){
    		
    		
    		handler.post(GetTaxiLocations);
    	
    	}
        
        /**
         * The following method will be ran every 45 seconds. It will check that the activity is bounded to the service
         * Once bounded it will check if the map contains any overlays.
         * If there are no overlays then it will call the method to map the locations.
         * If there are existing overlays on the map then it will call the method to remove those overlays 
         * and call the other method to map the new overlays
         */
        
        public Runnable GetTaxiLocations= new Runnable(){
    		
    		public void run() {
    			
    			Log.d("deeeeeee", "You are Here");
    			
    			if(bounder == true){
    				
                  if(mapOverlays.isEmpty()){
    		        	
    		        	MapLocations();
    		        }
    				
                  else if(!mapOverlays.isEmpty()){
    					
    		        	RemoveLocations();
    		        	MapLocations();
    		        }
    		
    				
    			}
    			
    			
    			
    			handler.postDelayed(GetTaxiLocations, 45000);
    		}
    		};
    		
    	
        
        public void RemoveLocations(){
        	
        	/**
        	 * The following will clear the array which contains the overlays for the taxi drivers
        	 * and refresh the map
        	 */
        	itemizedOverlay.clear();
        	mapView.postInvalidate();
        	
        	
        }
        
        public void MapLocations(){
        	
     
        
        	User = loc.getUserLocation();// accesses the method in the service which gets the users location
        	
        	//add the user to the overlay array and set the text to be displayed when the overlay is clicked
        	UsersOverlay = new OverlayItem(User , "Welcome", "This Represents Where You \n are on the map");
        	
        	//add the overlay to the array which contains the users overlays
        	UserItemOverlay.addOverlay(UsersOverlay);
        	
        	//populate the array
        	UserItemOverlay.populateNow();
        	
        	//add the user overlay to the array which contains the overlays to be mapped 
        	mapOverlays.add(UserItemOverlay);
        	
        	
        	try{
        	//access the method in the service which returns a JSONObject
        	taxiLocation = loc.getLocations();
        	//create a JSONArray based on the JSONObject and look for the id "posts"
        	a = taxiLocation.getJSONArray("posts");
        	
        	//create a vector for all the latitude values of the taxis 
        	TaxiLat = new Vector<Integer>( a.length());
        	//create a vector for all the longitude values of the taxis
        	TaxiLng = new Vector<Integer>( a.length());
        	//create a vector for all the geopoints of the taxi drivers 
        	TaxisVec = new Vector<GeoPoint>(a.length());
        	//create a vector for all the taxi drivers first names
        	TaxiFName = new Vector<String>(a.length());
        	//create a vector for all the taxi drivers last names
        	TaxiLName = new Vector<String>(a.length());
        	//create a vector for all the taxi drivers phone numbers 
        	TaxiPhone = new Vector<Integer>(a.length());
        	
        	//for loop to iterate through the JSONArray
        	for(int i = 0; i < a.length(); i ++){
        		
        		
        		taxiLocation = a.getJSONObject(i);
        		//add all the values from the JSON file with the id of "latitude" to the taxiLat vector
        		TaxiLat.add((int) (taxiLocation.getDouble("Latitude")* 1E6));
        		//add all the values from the JSON file with the id of "longitude" to the taxiLon vector
        		TaxiLng.add((int) (taxiLocation.getDouble("lontitude")* 1E6));
        		/**
        		 * Get all the lat and long values and add them to the geopoint vector
        		 */
        		TaxisVec.add(new GeoPoint( (int) TaxiLat.get(i), (int) TaxiLng.get(i)));
        		
        		
        		/**
        		 * The following will get the first/last and phone numbers of the taxi drivers
        		 * and add them to their corresponding vectors 
        		 */
        		TaxiFName.add(taxiLocation.getString("FName"));
        		TaxiLName.add(taxiLocation.getString("LName"));
        		TaxiPhone.add(taxiLocation.getInt("PhoneNum"));
        		
        		/**
        		 * The following will set the text to be displayed once a taxi overlay is clicked
        		 */
        		TaxiName = "Name: "+ TaxiFName.get(i) + " " + TaxiLName.get(i);
        		TaxiPhoneNum= "tel: 0" + TaxiPhone.get(i);
        
        		/**
        		 * The following will create overlays for the taxi drivers and add them to
        		 * the overlay array, and the overlay array to for the map
        		 */
        		overlayitem = new OverlayItem((GeoPoint) TaxisVec.get(i), TaxiName, TaxiPhoneNum);
        		itemizedOverlay.addOverlay(overlayitem);
            	itemizedOverlay.populateNow();
            	mapOverlays.add(itemizedOverlay);
        	
        	}
        	
        	}catch(Exception e){
        		
        		
        	}
        	
        	
        	mapView.postInvalidate();
        	mapController.animateTo(User);
			mapController.setZoom(13);
        	
        	
        	   	
        }
        
       /**The following is used to bind the activity to the service
        * 
        */
        private ServiceConnection mConnection = new ServiceConnection() {

	        public void onServiceConnected(ComponentName className,
	                
	        	IBinder service) {
	            // We've bound to LocalService, cast the IBinder and get LocalService instance
	            LocalBinder binder = (LocalBinder) service;
	            loc = binder.getService();
	            bounder = true;
	        }

	        public void onServiceDisconnected(ComponentName arg0) {
	            bounder = false;
	        }
	    };
        
       
		@Override
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}
    
    
}