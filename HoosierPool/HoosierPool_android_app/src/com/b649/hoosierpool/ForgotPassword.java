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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPassword extends ActionBarActivity {
	EditText etAnswer;
	TextView tvSecurityQuestion;
	EditText etPassword;
	EditText etPasswordAgain;
	Button update, ok, cancel;
	Button discard;
	AlertDialog.Builder dlgAlert;
	BusinessLogic blObj;
	Intent intent;
	
	@Override
	public void onStart(){
		super.onStart();
		etAnswer=(EditText)findViewById(R.id.etFPAnswer);
		etPassword=(EditText)findViewById(R.id.etFPPassword);
		etPasswordAgain=(EditText)findViewById(R.id.etFPPasswordAgain);
		tvSecurityQuestion=(TextView)findViewById(R.id.tvFPSecurityQuestion);
		Bundle b=getIntent().getExtras();
		tvSecurityQuestion.setText(b.getString("SecurityQuestion"));
	}
	
	public void cancel(View view){
		etAnswer.setText("");
	}
	
	public void dialog(String dialogMessage){
		dlgAlert.setMessage(dialogMessage);
		dlgAlert.setTitle("Hoosier Pool");
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	public void ok(View view){
		String recAnswer=etAnswer.getText().toString();
		if(!recAnswer.equals("")){
			Bundle b=getIntent().getExtras();
			if(recAnswer.equals(b.getString("Answer"))){
				etPassword.setEnabled(true);
				etPasswordAgain.setEnabled(true);
				update.setEnabled(true);
				discard.setEnabled(true);
				etAnswer.setEnabled(false);
				tvSecurityQuestion.setEnabled(false);
				ok.setEnabled(false);
				cancel.setEnabled(false);
			}
			else{
				dialog("Answer is Invalid.");
			}
		}
		else{
			dialog("Answer is Blank.");
		}
	}
	
	public void update(View view){
		String recPassword=etPassword.getText().toString();
		String recPasswordAgain=etPasswordAgain.getText().toString();
		Bundle b=getIntent().getExtras();
		String recEmailID=b.getString("EmailID");
		if(!recPassword.equals("")){
			if(!recPasswordAgain.equals("")){
				if(recPassword.equals(recPasswordAgain)){
					String result=blObj.verifyUser(recEmailID,recPassword);
		        	if(result==null){
		        		dialog("Server Connection Error");
		        	}
		        	else if(result.trim().equals("1")){
		        		dialog("Password Updated.");
		        		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								intent=new Intent(getApplicationContext(),LoginScreen.class);
				        		startActivity(intent);
				        		ForgotPassword.this.finish();
							}
						});
		        	}
		        	else if(result.trim().equals("0")){	
		        		dialog("Some Error Occured.");
		        	}
		        	else{
		        		dialog("Unknown Error.");
		        	}
				}
				else{
					dialog("Password not same.");
		    		etPasswordAgain.setText("");
				}
			}
			else{
				etPasswordAgain.setHint("Password Again is Blank");
			}
		}
		else{
			etPassword.setHint("Password is Blank");
		}
	}
	
	public void discard(View view){
		etPassword.setText("");
		etPasswordAgain.setText("");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_forgot_password);

		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		dlgAlert  = new AlertDialog.Builder(this);
		blObj= new BusinessLogic();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
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
			View rootView = inflater.inflate(R.layout.relative_forgot_password,
					container, false);
			return rootView;
		}
	}

}
