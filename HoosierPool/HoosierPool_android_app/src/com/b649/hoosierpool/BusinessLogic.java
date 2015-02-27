package com.b649.hoosierpool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
//import org.json.JSONArray;
//import org.json.JSONObject;

class BusinessLogic{
	InputStream is = null;
	StringBuilder sb=null;
	List<NameValuePair> input=new ArrayList<NameValuePair>();
	HttpPost httppost=null;
	static HttpClient httpclient=new DefaultHttpClient();
	HttpResponse response=null;
	HttpEntity entity=null;
	String sIPAdress="192.168.2.6:80";
	
	public boolean validateEmail(String target){
    	final String EMAIL_PATTERN = 
    			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    	Pattern pattern=Pattern.compile(EMAIL_PATTERN);
    	Matcher matcher=pattern.matcher(target);
		return matcher.matches();
    }
	
	
	public String registerUser(String userName, String password, String securityQuestion, String answer){
		try{
			String result,url="http://"+sIPAdress+"/HoosierPool/RegisterUser.php";
			input.clear();
			input.add(new BasicNameValuePair("EmailID",userName.trim()));
			input.add(new BasicNameValuePair("Password",password.trim()));
			input.add(new BasicNameValuePair("SecurityQuestion",securityQuestion.trim()));
			input.add(new BasicNameValuePair("Answer",answer.trim()));
			httppost=new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(input,"UTF-8"));
			//httpclient = new DefaultHttpClient();
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			String line="";
      
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			return result.trim();
		}
		catch(Exception e){
			Log.e("Error" ,e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String getSecurityInfo(String emailId){
		try{
			String result, url="http://"+sIPAdress+"/HoosierPool/GetSecurityInfo.php";
			input.clear();
			input.add(new BasicNameValuePair("EmailID",emailId.trim()));
			httppost=new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(input,"UTF-8"));
			
			//httpclient = new DefaultHttpClient();
        
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			String line="";
      
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			return result;
		}
		catch(Exception e){
			Log.e("Error" ,e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public String verifyUser(String userName, String password){
		try{
			String result, url="http://"+sIPAdress+"/HoosierPool/LoginAuthentication.php";
			input.clear();
			input.add(new BasicNameValuePair("EmailID",userName.trim()));
			input.add(new BasicNameValuePair("Password",password.trim()));
			httppost=new HttpPost(url.trim());
			httppost.setEntity(new UrlEncodedFormEntity(input,"UTF-8"));
			//httpclient = new DefaultHttpClient();
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			String line="";
			while ((line = reader.readLine()) != null) {
				sb.append(line );	
			}
			is.close();
			result=sb.toString();
			//System.out.println(result);
			/*System.out.println("hello"+result);
			JSONArray jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println();
			}*/
			return result;
		}
		catch(Exception e){
			
			Log.e("Error" ,e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public String newPost(String userID, String source, String destination, String price, String mobile, String date, String time, String totalMembers, String pickupLocation){
		try{
			String result, url="http://"+sIPAdress+"/HoosierPool/InsertHostPost.php";
			input.clear();
			input.add(new BasicNameValuePair("UserID",userID.trim()));
			input.add(new BasicNameValuePair("Source",source.trim()));
			input.add(new BasicNameValuePair("Destination",destination.trim()));
			input.add(new BasicNameValuePair("Price",price.trim()));
			input.add(new BasicNameValuePair("Mobile",mobile.trim()));
			input.add(new BasicNameValuePair("Date",date.trim()));
			input.add(new BasicNameValuePair("Time",time.trim()));
			input.add(new BasicNameValuePair("TotalMembers",totalMembers.trim()));
			input.add(new BasicNameValuePair("PickupLocation",pickupLocation.trim()));
			httppost=new HttpPost(url.trim());
			httppost.setEntity(new UrlEncodedFormEntity(input,"UTF-8"));
			//httpclient = new DefaultHttpClient();
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			String line="";
			while ((line = reader.readLine()) != null) {
				sb.append(line );	
			}
			is.close();
			result=sb.toString();
			return result;
		}
		catch(Exception e){
			Log.e("Error" ,e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String editPost(String userID, String source, String destination, String price, String mobile, String date, String time, String totalMembers, String pickupLocation){
		try{
			String result, url="http://"+sIPAdress+"/HoosierPool/EditHostPost.php";
			input.clear();
			input.add(new BasicNameValuePair("UserID",userID.trim()));
			input.add(new BasicNameValuePair("Source",source.trim()));
			input.add(new BasicNameValuePair("Destination",destination.trim()));
			input.add(new BasicNameValuePair("Price",price.trim()));
			input.add(new BasicNameValuePair("Mobile",mobile.trim()));
			input.add(new BasicNameValuePair("Date",date.trim()));
			input.add(new BasicNameValuePair("Time",time.trim()));
			input.add(new BasicNameValuePair("TotalMembers",totalMembers.trim()));
			input.add(new BasicNameValuePair("PickupLocation",pickupLocation.trim()));
			httppost=new HttpPost(url.trim());
			httppost.setEntity(new UrlEncodedFormEntity(input,"UTF-8"));
			//httpclient = new DefaultHttpClient();
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			String line="";
			while ((line = reader.readLine()) != null) {
				sb.append(line );	
			}
			is.close();
			result=sb.toString();
			return result;
		}
		catch(Exception e){
			Log.e("Error" ,e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
