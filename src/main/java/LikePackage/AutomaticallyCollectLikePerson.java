package LikePackage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomaticallyCollectLikePerson {
	
	
	public void mainLikePerson() throws InterruptedException {
		
		CountProgramExecution c=new CountProgramExecution();
		
//		login page
			WebDriverManager.chromedriver().setup();
			RemoteWebDriver driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://www.instagram.com/");
//			10 second wait
			Thread.sleep(10000);
//	    	creating object for Excel class
	    	ExcelExample excelObj=new ExcelExample();
	    	UserDetailsClass userdetails=excelObj.readUserDetails();
	    	
//			user name
			String username=userdetails.userName;
//			password
			String password=userdetails.password;	
			
//			post url
			ArrayList<String> postUrlArray=userdetails.postUrl;

//			send message
			String sendMessage=userdetails.sendMessage;
//			explicit wait
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//			return true or false based on time and count of execution of program
			System.out.println("c.writeCount()"+c.writeCount());
			if(!c.writeCount()) {
				driver.quit();
			}
			else {

//			username Element
			WebElement userNameElement=wait.until(ExpectedConditions.presenceOfElementLocated
					(ByXPath.xpath("//input[@name='username']")));
			userNameElement.sendKeys(username);
//			5 second wait
			Thread.sleep(5000);
//			password element
			WebElement passwordElement=wait.until(ExpectedConditions.presenceOfElementLocated
	(ByXPath.xpath("//input[@name='password']")));
			passwordElement.sendKeys(password);
			
//			10 second wait
			Thread.sleep(10000);
//			login button
			WebElement submitElement=wait.until(ExpectedConditions.elementToBeClickable
	(ByXPath.xpath("//*[contains(text(),'Log in')]")));
			
			if(submitElement.isEnabled()) {
				submitElement.click();
			}
			
//			5 second wait
			Thread.sleep(5000);

//			home page
//			not now button1 
			WebElement NotNowElement1=wait.until(ExpectedConditions.elementToBeClickable
					(ByXPath.xpath("//*[contains(text(),'Not Now')]")));
			if(NotNowElement1.isEnabled()) {
				NotNowElement1.click();
			}
//			5 second wait
			Thread.sleep(5000);
//			not now button2
			WebElement NotNowElement2=wait.until(ExpectedConditions.
					elementToBeClickable(ByXPath.xpath("//*[contains(text(),'Not Now')]")));
			if(NotNowElement2.isEnabled()) {
				NotNowElement2.click();
			}
//			5 second wait
			Thread.sleep(5000);
//			Home profile
			WebElement profileElement=wait.until(ExpectedConditions.elementToBeClickable(ByXPath.xpath("//a[.='Profile']")));
			if(profileElement.isEnabled()) {
				profileElement.click();
			}
//			5 second wait
			Thread.sleep(5000);

//			get post parent element	
			
//			WebElement postElementParent=wait.until(ExpectedConditions.
//					presenceOfElementLocated(
//							ByXPath.xpath("//div[contains(@class,'_ac7v  _al3n')]")));
//			List<WebElement> childElements=postElementParent.findElements(By.xpath("//div[contains(@class,'_aabd _aa8k  _al3l')]"));
	//	
			ArrayList<InstagramPerson> personList=new ArrayList<InstagramPerson>();
			

	        for(int eachPost=0;eachPost<postUrlArray.size();eachPost++) {
	        	personList.addAll(collectLikeCount(driver,wait,postUrlArray.get(eachPost)));
	        }
	        

//	    	get data from excel
	    	ArrayList<InstagramPerson> existingPersonList=excelObj.readData();
//	    	get new person details
	    	ArrayList<InstagramPerson> newPersonList=new ArrayList<InstagramPerson>();
	    	for(InstagramPerson eachPerson:personList) {
	    		int count=0;
	    		for(InstagramPerson eachPersonInExcel:existingPersonList) {
	    			
	    			if(eachPerson.personUrl.equals(eachPersonInExcel.personUrl) 
	    					&& eachPerson.postName.equals(eachPersonInExcel.postName)) {
	    				count++;
	    				break;
	    			}
	    		}
	    		if(count==0) {
	    			newPersonList.add(eachPerson);
	    		}
	    	}
	    	
//	    	System.out.println(newPersonList);
//	    	calling the sendmessage method
			
	    	for(InstagramPerson eachPerson:newPersonList) {
	    		sendMessage(driver,wait,eachPerson.personName,sendMessage);
	    	}
	    	
//	    	writing new person in excel
	    	excelObj.writeData(newPersonList);

	    	
//			5 second wait
			Thread.sleep(5000);
//			logout part

			WebElement moreButton=wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class='xdy9tzy']/following-sibling::span[1]")));
			moreButton.click();
//			5 second wait
			Thread.sleep(5000);
			WebElement logoutButton=wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//span[text()='Log out']")));
			logoutButton.click();
//			5 second wait
			Thread.sleep(5000);
//			quit browser
			driver.quit();
			}	
	}

	public ArrayList<InstagramPerson> collectLikeCount(RemoteWebDriver driver, WebDriverWait wait,String postUrl) throws InterruptedException {
		
		
		driver.get(postUrl);
		
//		5 second wait
		Thread.sleep(5000);
		
		
		ArrayList<InstagramPerson> eachPostPersonList=new ArrayList<InstagramPerson>();
		
	//  select like element
	//  previous xpath (//span[contains(@class,'x1lliihq x1plvlek')]//a)[3]
		try {
			WebElement likeElement=wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//span[contains(text(),'like')]")));
			likeElement.click();
		}
		catch(Exception e) {
			ArrayList<InstagramPerson> emptyArrayList=new ArrayList<InstagramPerson>();
			return emptyArrayList;
		}
		
//		5 second wait
		Thread.sleep(5000);
		
		List<WebElement> child=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ByXPath.
				xpath("//div[contains(@class,'x1dm5mii x16mil14')]")));
		
//		postName
		String postName="post".concat(postUrl);

//		collected like given list
		for(int i=0;i<child.size();i++) {
			
//			String xpath="(//div[@class='_aarf']//a)[".concat(Integer.toString(i+1)).concat("]");
			String xpath="(//div[contains(@class,'xozqiw3 x1q0g3np')])[".concat(Integer.toString(i+1)).concat("]");
			WebElement eachPerson=child.get(i).findElement(By.xpath(xpath));

			String [] personArray=eachPerson.getText().split("\n");
			String personUrl=personArray[1];
			String personName=personArray[0];
			eachPostPersonList.add(new InstagramPerson(postName,personName,personUrl , true));
		}

//		5 second wait
		Thread.sleep(5000);
//		close the like page
		WebElement closeElement=wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[contains(@aria-label,'Close')]")));
		closeElement.click();
		
		return eachPostPersonList;
		
	}



	public void sendMessage(RemoteWebDriver driver, WebDriverWait wait,String personName,String sendMessage) throws InterruptedException {
		
//		5 second wait
		Thread.sleep(5000);
//	    finding direct message element
		WebElement messageButtonElement=wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//a[contains(@aria-label,'Direct messaging')]")));
		
		messageButtonElement.click();
		
//		5 second wait
		Thread.sleep(5000);
		
//		click send message button
		WebElement sendMessageBtnElement=wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[text()='Send message']")));
		sendMessageBtnElement.click();

		
//		5 second wait
		Thread.sleep(5000);
		
//		search input box
		
		WebElement searchBoxElement=wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[name='queryBox']")));
		
//		5 second wait
		Thread.sleep(5000);
//		find people
//		personName="thamizh.HD";
		searchBoxElement.sendKeys(personName);

		
//		5 second wait
		Thread.sleep(5000);
		
		try {
			String personXpath="//span[contains(text(),'".concat(personName.toLowerCase()).concat("')]");
			WebElement searchPeopleElement=driver.findElement(By.xpath(personXpath));
			searchPeopleElement.click();
		}
		catch(Exception e) {
//			get check box name
			WebElement labelCheckBoxElement=driver.findElement(By.xpath("//label[contains(@for,'ContactSearchResultCheckbox')]"));
			WebElement searchResultCheckBox=labelCheckBoxElement.findElement(
					By.xpath("(//input[contains(@name,'ContactSearchResultCheckbox')])[1]"));
					
			searchResultCheckBox.click();
		}




//		5 second wait
		Thread.sleep(5000);
	// click chat button
		
		WebElement chatButton=wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[text()='Chat']")));
		
		chatButton.click();
		
		
//		5 second wait
		Thread.sleep(5000);
//		find message box
		
		WebElement messageBox=wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@aria-label='Message']")));
		messageBox.sendKeys(sendMessage);

		
//		5 second wait
		Thread.sleep(5000);
//		send button
		WebElement sendButtonElement=wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[text()='Send']")));
		sendButtonElement.click();
		Thread.sleep(5000);

	}

public static void main(String[] args) throws InterruptedException {
	AutomaticallyCollectLikePerson a=new AutomaticallyCollectLikePerson();
	a.mainLikePerson();
}
}
