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

public class Register extends ActionBarActivity {
	
	EditText etEmailID, etPassword, etPasswordAgain, etSecurityQuestion, etAnswer;
	Intent intentLogin;
	BusinessLogic blObj;
	AlertDialog.Builder dlgAlert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_register);
		
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		blObj=new BusinessLogic();
		dlgAlert  = new AlertDialog.Builder(this);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		etEmailID=(EditText) findViewById(R.id.etRegEmailId);
		etPassword=(EditText) findViewById(R.id.etRegPassword);
		etPasswordAgain=(EditText) findViewById(R.id.etRegPasswordAgain);
		etSecurityQuestion=(EditText) findViewById(R.id.etRegSecurityQuestion);
		etAnswer=(EditText) findViewById(R.id.etRegAnswer);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
	
	public void cancel(View view){
		etAnswer.setText("");
		etEmailID.setText("");
		etPassword.setText("");
		etPasswordAgain.setText("");
		etSecurityQuestion.setText("");
	}
	
	public void dialog(String dialogMessage){
		dlgAlert.setMessage(dialogMessage);
		dlgAlert.setTitle("Hoosier Pool");
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	public void signup(View view){
		String recEmailID=etEmailID.getText().toString();
        String recPassword=etPassword.getText().toString();
        String recPasswordAgain=etPasswordAgain.getText().toString();
        String recSecurityQuestion=etSecurityQuestion.getText().toString();
        String recAnswer=etAnswer.getText().toString();
        if(recEmailID.equals("")){
        	etEmailID.setHint("Email ID Blank");
        }
        if(recPassword.equals("")){
        	etPassword.setHint("Password Blank");
        }
        if(recPasswordAgain.equals("")){
        	etPasswordAgain.setHint("Password Again Blank");
        }
        if(recSecurityQuestion.equals("")){
        	etSecurityQuestion.setHint("Security Question Blank");
        }
        if(recAnswer.equals("")){
        	etAnswer.setHint("Answer Blank");
        }
        if(!recEmailID.equals("") && !blObj.validateEmail(recEmailID)){
        	etEmailID.setHint("Email ID Invalid");
        }
        if(!recPassword.equals("") && !recPasswordAgain.equals("") && !recPassword.equals(recPasswordAgain)){
        	etPasswordAgain.setHint("Passwords don't match");
        }
        if(!recEmailID.equals("") && !recPassword.equals("") && !recPasswordAgain.equals("") && !recSecurityQuestion.equals("") && !recAnswer.equals("") && recPassword.equals(recPasswordAgain) && blObj.validateEmail(recEmailID)){
			String result=blObj.registerUser(recEmailID,recPassword,recSecurityQuestion,recAnswer);
			if(result==null){
	    		dialog("Server Connection Error.");
	    	}
	    	else if(result.equals("1")){	
	    		dialog("User Registered.");
	    		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Register.this.finish();
						intentLogin=new Intent(getApplicationContext(),LoginScreen.class);
			    		startActivity(intentLogin);
					}
				});
	    	}
	    	else if(result.equals("0")){	
	    		dialog("Email ID already present.");
	    	}
	    	else{
	    		dialog("Unknown Error.");
	    	}
        }
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
			View rootView = inflater.inflate(R.layout.relative_register,
					container, false);
			return rootView;
		}
	}

}
