package com.yakarex.animequiz.fragments;

import com.yakarex.animequiz.activities.CreditsActivity;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.activities.MainFragActivity;
import com.yakarex.animequiz.utils.ChangeLog;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragOptions extends Fragment implements OnItemClickListener{
	
	ChangeLog cl;
	boolean audio;
	boolean vibration;
	Context context;
	ListView lview;
	TextView textSing;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.frag_options, container, false);
		
		lview= (ListView) rootView.findViewById(R.id.listView1);
		lview.setOnItemClickListener(this);
		
		
		context = getActivity().getApplicationContext();
		
		 cl = new ChangeLog(getActivity());
		 
		 SharedPreferences settings = getActivity().getSharedPreferences("com.yacarex.animequiz", 0);
		 
		 audio = settings.getBoolean("audioOff", false);
		 vibration= settings.getBoolean("vibrationOff", false);
		 
		 textSing= (TextView) lview.getChildAt(8 - lview.getFirstVisiblePosition());
		
		
		return rootView;
	}
	
public void onItemClick(AdapterView<?> arg0, View arg1, int itemid, long arg3) {
		
		if (itemid==0){
			
			String message= getString(R.string.resetmessage);
			String yes=getString(R.string.yes);
			String no=getString(R.string.no);
			
			new AlertDialog.Builder(getActivity())
	           .setMessage(message)
	           .setCancelable(false)
	           .setPositiveButton(yes, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   ((MainFragActivity)getActivity()).resetScore();
	               }
	           })
	           .setNegativeButton(no, null)
	           .show();
		}
		
		if(itemid==1){
			cl.getFullLogDialog().show();
		}
		
		if(itemid==2){
			Uri uri = Uri.parse("market://details?id=com.yakarex.animequiz");
	    	Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    	try {
	    	  startActivity(goToMarket);
	    	} catch (ActivityNotFoundException e) {
	    	  Toast.makeText(getActivity(), "Couldn't launch ", Toast.LENGTH_LONG).show();
	    	}
		}
		
		if(itemid==3){
			Uri uri = Uri.parse("https://www.facebook.com/AndroidAnimeChallenge");
	    	Intent goToFb = new Intent(Intent.ACTION_VIEW, uri);
	    	try {
	    	  startActivity(goToFb);
	    	} catch (ActivityNotFoundException e) {
	    	  Toast.makeText(getActivity(), "Couldn't launch ", Toast.LENGTH_LONG).show();
	    	}
		}
		
		if(itemid==4){
			AlertDialog.Builder sendamail = new AlertDialog.Builder(getActivity());
			PackageInfo pInfo = null;
			
			try {
				pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			
			final String mailto= "yacarexgames@gmail.com";
			final String subjet= "Anime Challenge"+pInfo.versionName;
			
			final EditText input = new EditText(getActivity());
			sendamail.setView(input);

			sendamail.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				Intent mailintent = new Intent(Intent.ACTION_SEND);
				mailintent.setType("message/rfc822");
				mailintent.putExtra(Intent.EXTRA_EMAIL  , new String[]{mailto});
				mailintent.putExtra(Intent.EXTRA_SUBJECT, subjet);
				mailintent.putExtra(Intent.EXTRA_TEXT   , value);
				
				try {
				    startActivity(Intent.createChooser(mailintent, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			  
			  }
			});

			sendamail.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
				  
			  }
			});

			sendamail.show();
			
		}
		
		if(itemid==5){
			Intent credits= new Intent(getActivity(), CreditsActivity.class);
			startActivity(credits);

		}
		
		if(itemid==6){
			
			((MainFragActivity)getActivity()).audioManager();
			
		}
		
		if(itemid==7){
			
			((MainFragActivity)getActivity()).vibrationManager();
		}
		if(itemid==8){

			boolean isSignedIn= ((MainFragActivity)getActivity()).isSignedIn();

			if(isSignedIn){
				((MainFragActivity)getActivity()).signOutclicked();
				((MainFragActivity)getActivity()).setIsSignedIn(false);
				Toast signOut= Toast.makeText(getContext(), R.string.logout, Toast.LENGTH_SHORT);
				signOut.show();
			}
			else {
				((MainFragActivity)getActivity()).signInClicked();
				((MainFragActivity)getActivity()).setIsSignedIn(true);
			}
			
		}
		
		if(itemid==9){
			
			Uri uri = Uri.parse("market://details?id=com.yacarex.landscapesbggallery");
	    	Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    	try {
	    	  startActivity(goToMarket);
	    	} catch (ActivityNotFoundException e) {
	    	  Toast.makeText(getActivity(), "Couldn't launch ", Toast.LENGTH_LONG).show();
	    	}
			
		}
	
	}
	

}
