package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups= {"Datadriven"}) // geting Data provider from different class
	public void verify_loginDDT(String email, String pwd, String exp) {
		
		logger.info("***** Starting TC003_LoginDDT ******** ");
		
		try
		{
			
				//Home Page
				HomePage hp = new HomePage(driver);
				hp.clickMyAccount();
				logger.info("Clicked on MyAccount link");
				hp.clickLogin();
				logger.info("Clicked on MyAccount link");
				
				//Login page
				LoginPage lp = new LoginPage(driver);
				lp.setEmail(email);
				lp.setPassword(pwd);
				lp.clickLogin();
				
				//MyAccount page
				MyAccountPage macc = new MyAccountPage(driver);
				boolean targetPage = macc.isMyAccountPageExists();
				
				/*Data is valid  --> login success --> test pass --> logout
				data is valid ---> login failed ----> test failed 
		
				data is invalid ---> login success --> test fail --> logout
				data is iinvalid ----> login failed ---> test pass
		
				*/
				
				if(exp.equalsIgnoreCase("Valid")) {
					if(targetPage == true) {
						macc.clickLogout();
						Assert.assertTrue(true);		
					}
					else
					{
						Assert.assertTrue(false);
					}
				}
				
				if(exp.equalsIgnoreCase("Invalid")) {
					if(targetPage == true) {
						macc.clickLogout();
						Assert.assertTrue(false);
					}
					else {
						Assert.assertTrue(true);
					}
				}
		
		}
		catch(Exception e) {
			e.printStackTrace();
			Assert.fail("Test failed due to : " + e.getMessage());
		}
		
		//Thread.sleep(3000);
		logger.info("***** Finished TC003_LoginDDT ******** ");
		
	}

}
