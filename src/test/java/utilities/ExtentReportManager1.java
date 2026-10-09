package utilities;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager1 implements ITestListener, ISuiteListener {
	
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	
	private static final Set<String> browsers = ConcurrentHashMap.newKeySet();
	private static final Set<String> oses = ConcurrentHashMap.newKeySet();
	
	static String repName;
	
	public synchronized void onStart(ITestContext testContext){
		
		// collect per-<test> values (chrome / edge)
		browsers.add(testContext.getCurrentXmlTest().getParameter("browser"));
		oses.add(testContext.getCurrentXmlTest().getParameter("os"));
		
		if(extent == null) {   // create report only once
			
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			repName = "Test-Report" + timeStamp + ".html";
			sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
			
			sparkReporter.config().setDocumentTitle("opencart Automation Report");
			sparkReporter.config().setReportName("opencart Functional Testing");
			sparkReporter.config().setTheme(Theme.DARK);
			
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Application", "opencart");
			extent.setSystemInfo("Module", "Admin");
			extent.setSystemInfo("Sub Module", "Customers");
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			extent.setSystemInfo("Environment", "QA");
			
			List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
			if(!includeGroups.isEmpty()) {
				extent.setSystemInfo("Groups", includeGroups.toString());
			}
		}
	}
	
	public void onTestSuccess(ITestResult result) {
		
		ExtentTest t = extent.createTest(result.getTestClass().getName());
		test.set(t);
		t.assignCategory(result.getMethod().getGroups());
		t.log(Status.PASS, result.getName() + " got successfully executed");
	}
	
	public void onTestFailure(ITestResult result) {
		
		ExtentTest t = extent.createTest(result.getTestClass().getName());
		test.set(t);
		t.assignCategory(result.getMethod().getGroups());
		
		t.log(Status.FAIL, result.getName() + " got failed");
		t.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			t.addScreenCaptureFromPath(imgPath);
		}catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		
		ExtentTest t = extent.createTest(result.getTestClass().getName());
		test.set(t);
		t.assignCategory(result.getMethod().getGroups());
		
		t.log(Status.SKIP, result.getName() + " got skipped");
		if(result.getThrowable() != null) {
			t.log(Status.INFO, result.getThrowable().getMessage());
		}
	}
	
	// runs ONCE after all <test> tags finish
	public void onFinish(ISuite suite) {
		
		extent.setSystemInfo("Operating System", String.join(", ", oses));
		extent.setSystemInfo("Browser", String.join(", ", browsers));
		
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		/*try {
			URL url = new URL("file:///"+System.getProperty("user.dir") + "\\reports\\"+repName);
			
			//Create the email message 
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("yousuffm27@gmail.com", "password"));
			email.setSSLOnConnect(true);
			email.setFrom("yousuffm27@gmail.com"); //Sender
			email.setSubject("Test Results");
			email.setMsg("Please find Attached Report .....");
			email.addTo("yousuffm27@gmail.com");//Receiver
			email.attach(url, "extent report", "please check report....");
			email.send();
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
	}

}