package com.b649.hoosierpool;

final class Session {
	static String EmailID=null;
	static int UserID=-1;
	public static void setSession(String EmailID, int UserID){
		Session.EmailID=EmailID;
		Session.UserID=UserID;
	}
	public static String getEmailID(){
		return EmailID;
	}
	public static int getUserID(){
		return UserID;
	}
	public static int isSet(){
		if(EmailID!=null && UserID!=-1){
			return 1;
		}
		else{
			return 0;
		}
	}
	public static void Reset(){
		EmailID=null;
		UserID=-1;
	}
}
