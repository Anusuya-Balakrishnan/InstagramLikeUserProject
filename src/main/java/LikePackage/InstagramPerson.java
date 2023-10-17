package LikePackage;

public class InstagramPerson {
	String postName;
	String personName;
	String personUrl;
	boolean response;
	
	public InstagramPerson(String postName, String personName,String personUrl,boolean response) {
		this.postName=postName;
		this.personName=personName;
		this.personUrl=personUrl;
		this.response=response;
	}
	public String toString() {
		return this.postName+" "+this.personName+" "+this.personUrl+" "+Boolean.toString(this.response);
	}
	
	public static void findPerson() {
		String personurl="https://www.instagram.com/thamizh.hd/";
		String[] stringarray=personurl.split("/");
		String personName=stringarray[3];
		System.out.println(personName);
		
	}
}
