package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups= {"Sanity","Master"})
	public void verifyLogin() {
		
		logger.info("*** Starting TC0002_LoginTest ********** ");
		
		try {
		//Home page
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("*******clicked on MyAccount Link ******");
		hp.clickLogin();
		logger.info("******Clicked on Login Link *******");
		
		//Login page
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		logger.info("****Clicked on Login Button *******");
		
		//MyAccount page
		MyAccountPage macc = new MyAccountPage(driver);
		
		boolean targetPage = macc.isMyAccountPageExists();
		
		Assert.assertTrue(targetPage);//Assert.assertEquals(targetPage, true, "Login Failed");

		
		}
		catch(Exception e) {
			Assert.fail();
		}
		
		logger.info("*** Finished TC0002_LoginTest ********** ");
		
	}

}
