package com.b649.hoosierpool;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NewPost extends ActionBarActivity {
	EditText etSource, etDestination,etPrice, etMobile, etDate, etTime, etTotalMembers, etPickupLocation;
	Intent intent;
	BusinessLogic blObj;
	AlertDialog.Builder dlgAlert;
	
	public void dialog(String dialogMessage){
		dlgAlert.setMessage(dialogMessage);
		dlgAlert.setTitle("Hoosier Pool");
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
    
	
	
	public void cancel(View view){
		etSource.setText("");
		etDestination.setText("");
		etPrice.setText("");
		etMobile.setText("");
		etDate.setText("");
		etTime.setText("");
		etTotalMembers.setText("");
		etPickupLocation.setText("");
	}
	
	public void post(View view){
		String recSource=etSource.getText().toString(), recDestination=etDestination.getText().toString(),recPrice=etPrice.getText().toString(),recMobile=etMobile.getText().toString(),recDate=etDate.getText().toString(),recTime=etTime.getText().toString(), recTotalMembers=etTotalMembers.getText().toString(), recPickupLocation=etPickupLocation.getText().toString();
		String result=blObj.newPost(Session.getUserID()+"", recSource, recDestination,recPrice, recMobile, recDate, recTime, recTotalMembers, recPickupLocation);
    	if(result==null){
    		dialog("Server Connection Error");
    		Integer.parseInt("0");
    	}
    	else if(result.equals("0")){	
    		dialog("Email ID or is Invalid");
    	}
    	else if(result.equals("") || Integer.parseInt(result)<0){
    		dialog(result);
    	}
    	if(Integer.parseInt(result.trim())>0){
    		dlgAlert.setPositiveButton("Posted", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					intent=new Intent(getApplicationContext(),Post.class);
		    		startActivity(intent);
		    		NewPost.this.finish();
				}
			});
    		
    	}
    	else{
    		dialog("Unknown Error");
    	}
	}
	
	@Override
	public void onStart(){
		super.onStart();
		etSource=(EditText) findViewById(R.id.etNPSource);
		etDestination=(EditText) findViewById(R.id.etNPDestination);
		etPrice=(EditText) findViewById(R.id.etNPPrice);
		etMobile=(EditText) findViewById(R.id.etNPMobile);
		etDate=(EditText) findViewById(R.id.etNPDate);
		etTime=(EditText) findViewById(R.id.etNPTime);
		etTotalMembers=(EditText) findViewById(R.id.etNPTotalMembers);
		etPickupLocation=(EditText) findViewById(R.id.etNPPickUpLocation);
		etTime=(EditText) findViewById(R.id.etNPTime);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_new_post);

		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		blObj=new BusinessLogic();
		dlgAlert= new AlertDialog.Builder(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.relative_new_post,
					container, false);
			return rootView;
		}
	}

}
