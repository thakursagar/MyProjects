package com.b649.hoosierpool;

import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LoginScreen extends ActionBarActivity {
	EditText etEmailID, etPassword;
	Intent intent;
	BusinessLogic blObj;
	AlertDialog.Builder dlgAlert;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_login_screen);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }*/
        dlgAlert  = new AlertDialog.Builder(this);
		blObj=new BusinessLogic();
    }
    
    @Override
	public void onStart(){
		super.onStart();
		if(Session.isSet()==1){
			intent=new Intent(getApplicationContext(),Home.class);
    		startActivity(intent);
    		LoginScreen.this.finish();
		}
		else{
			etEmailID=(EditText) findViewById(R.id.etLogEmailID);
			etPassword=(EditText) findViewById(R.id.etLogPassword);
		}
	}
    
    public void dialog(String dialogMessage){
		dlgAlert.setMessage(dialogMessage);
		dlgAlert.setTitle("Hoosier Pool");
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
    
    public void login(View view){
    	String recEmail=etEmailID.getText().toString();
        String recPassword=etPassword.getText().toString();
        if(recEmail.equals("")){
        	etEmailID.setHint("Email ID Blank");
        }
        if(recPassword.equals("")){
        	etPassword.setHint("Password Blank");
        }
        if(!recEmail.equals("") &&  !blObj.validateEmail(recEmail)){
        	etEmailID.setText("");
        	etEmailID.setHint("Email ID not valid");
        }
        if(!recEmail.equals("") && !recPassword.equals("") && blObj.validateEmail(recEmail)){
        	
    		String result=blObj.verifyUser(recEmail, recPassword);
        	if(result==null){
        		dialog("Server Connection Error");
        		Integer.parseInt("0");
        	}
        	else if(result.equals("0")){	
        		dialog("Email ID is Invalid");
        	}
        	else if(result.equals("") || Integer.parseInt(result)<0){
        		dialog(result);
        	}
        	if(Integer.parseInt(result.trim())>0){
        		Session.setSession(recEmail, Integer.parseInt(result));
        		
        		intent=new Intent(getApplicationContext(),Home.class);
        		startActivity(intent);
        		LoginScreen.this.finish();
        	}
        	else{
        		dialog("Unknown Error");
        	}
        }   
    }
    
    public void register(View view){
    	intent=new Intent(getApplicationContext(),Register.class);
		startActivity(intent);
    }
    
    public void forgotPassword(View view){
    	String recEmailID=etEmailID.getText().toString();
    	if(!recEmailID.equals("")){
    		if(blObj.validateEmail(recEmailID)){
    			String result=new BusinessLogic().getSecurityInfo(recEmailID);
    			if(result != null){
	    			try{
	    				JSONArray jArray = new JSONArray(result);
	    				JSONObject json = jArray.getJSONObject(0);
	    				String SecurityQuestion=json.getString("SecurityQuestion");
	    				String Answer=json.getString("Answer");
	    				Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
	    				Bundle b=new Bundle();
	    				b.putString("SecurityQuestion", SecurityQuestion);
	    				b.putString("Answer", Answer);
		    			intent.putExtras(b);
		    			startActivity(intent);
	    			}
	    			catch(Exception E){
	    				dialog("Error Fetching Security Information.");
	    			}
    			}
    		}
    		else{
        		etEmailID.setHint("Email ID Invalid");
        		etPassword.setHint("Password");
        	}
    	}
    	else{
    		etEmailID.setHint("Email ID Blank");
    		etPassword.setHint("Password");
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_screen, menu);
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
            View rootView = inflater.inflate(R.layout.relative_login_screen, container, false);
            return rootView;
        }
    }

}
