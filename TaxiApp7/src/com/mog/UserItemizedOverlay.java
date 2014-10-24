package com.mog;

import java.util.ArrayList;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;



/**
 * @author Martin O'Grady (B00028697)
 *  date: 29/04/12
 *  Objective: This class is used as an array for all user overlay 
 *  this class can be used to add/remove/create/set action to the overlay
 */

public class UserItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	//Create an Array for all the markers
		private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
		
		private Context context;
		
		
		public UserItemizedOverlay(Drawable defaultMarker) {
			  super(boundCenterBottom(defaultMarker));
		}
		
		
		public UserItemizedOverlay(Drawable defaultMarker, Context context) {
			  this(defaultMarker);
			  this.context = context;
		}

		@Override
		protected OverlayItem createItem(int i) {
			return mapOverlays.get(i);
		}

		@Override
		public int size() {
			return mapOverlays.size();
		}
		
		@Override
		public boolean onTap(int index) {
			
			OverlayItem item = mapOverlays.get(index);
			Dialog dialog = new Dialog(context);

			dialog.setContentView(R.layout.userdialog);
			dialog.setTitle(item.getTitle());

			TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setText(item.getSnippet());
			
			dialog.show();
			return true;
			
		}
		
		//This method adds overlays to the arraylist 
		//Any time you add an overlayitem to the arraylist we must call populate()
		//When populate is called it will call the create item method.
		public void addOverlay(OverlayItem overlayitem) {
			this.mapOverlays.add(overlayitem);
			
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

