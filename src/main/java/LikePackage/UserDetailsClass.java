package LikePackage;

import java.util.ArrayList;

public class UserDetailsClass {
	String userName;
	String password;
	String sendMessage;
	ArrayList<String> postUrl;
	
	public UserDetailsClass(String userName,String password,String sendMessage, ArrayList<String> postUrl ) {
		this.userName=userName;
		this.password=password;
		this.sendMessage=sendMessage;
		this.postUrl=postUrl;
	}
	
	public String toString() {
		System.out.println(postUrl);
		String result=userName.concat(" ").concat(password).concat(" ").concat(sendMessage);
		return result;
	}

}
