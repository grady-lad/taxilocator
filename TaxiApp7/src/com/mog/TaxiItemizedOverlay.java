package com.mog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;


import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * @author Martin O'Grady (B00028697)
 *  date: 29/04/12
 *  Objective: This class is used as an array for all the taxi drivers overlays 
 *  this class can be used to add/remove/create/set action to the overlays
 */

public class TaxiItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	//Create an Array for all the markers
	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	
	private Context context;
	
	
	private String ThePhoneNum;
	

	
	public TaxiItemizedOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
	}
	
	public TaxiItemizedOverlay(Drawable defaultMarker, Context context) {
		  this(defaultMarker);
		  this.context = context;
	}

	
	protected OverlayItem createItem(int i) {
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		return mapOverlays.size();
	}
	
	//the following adds a button the the dialog box which will be used to ring the taxi driver
	public boolean onTap(int index) {
		
		OverlayItem item = mapOverlays.get(index);
		Dialog dialog = new Dialog(context);

		dialog.setContentView(R.layout.dialog);
		dialog.setTitle(item.getTitle());

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(item.getSnippet());
		ThePhoneNum = item.getSnippet();
		Button CallButton = (Button) dialog.findViewById(R.id.CallButton);
		CallButton.setOnClickListener(new View.OnClickListener()
    	{
    		public void onClick(View v) 
    		{
    			try {
    				showMessage();
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				
    			}
    		}
    	}
    	);
		dialog.show();
		return true;
		
	}
	
	//This method adds overlays to the arraylist 
	//Any time you add an overlayitem to the arraylist we must call populate()
	//When populate is called it will call the create item method.
	public void addOverlay(OverlayItem overlayitem) {
		this.mapOverlays.add(overlayitem);
		
	}
	
	public void showMessage(){
		
		try{
		Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(ThePhoneNum));
        context.startActivity(callIntent);
		
		}
		catch(Exception e)
		{
			
		}
		

		}

	

	public void clear() {
        mapOverlays.clear();
       
    }
	
	public void removeOverlay(OverlayItem overlay) {
        mapOverlays.remove(overlay);
        
    }
	
	
	
	public void populateNow(){
	    populate();
	}

}
