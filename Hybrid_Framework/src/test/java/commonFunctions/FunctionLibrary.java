package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



import utilities.PropertyFileUtil;

public class FunctionLibrary {

	public static WebDriver driver;
	
	
	//method for Launching Browser
	public static WebDriver startBrowser()throws Throwable{
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		return driver;
	}
	
	// method for Launching URL in a Browser
	public static void openUrl() throws Throwable {
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}
	
	//method for wait for Element to Visible
	public static void waitForElement(String Locator_Type, String Locator_Value, String Test_Data) {
		
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
		
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
		else if (Locator_Type.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		else if (Locator_Type.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
	}
	
	//method for TextBoxes
	public static void typeAction(String Locator_Type, String Locator_Value, String Test_Data) {
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		else if (Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		else if (Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
	}
	
	// method for Buttons, Radio buttons, Check boxes, Hyper links, Images
	public static void clickAction(String Locator_Type, String Locator_Value) {
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locator_Value)).click();
		}
		else if (Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locator_Value)).click();
		}
		else if (Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
	}
	
	// method for Validating Page Title of a WebPage
	public static void validateTitle(String Expected) {
		String Actual = driver.getTitle();
		try {
		Assert.assertEquals(Actual, Expected, "Title is not Matching");
		}catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	//method for closing Browser
	public static void closeBrowser() {
		driver.quit();
	}
	
	//method for Date Formating
	public static String dateFormat() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD hh_mm_ss");
		return df.format(date);
	}
	
	// method for List Boxes
	public static void dropDownAction(String Locator_Type, String Locator_Value, String Test_Data) {
		
		int index = Integer.parseInt(Test_Data);
		
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			new Select(driver.findElement(By.xpath(Locator_Value))).selectByIndex(index);
		}
		else if (Locator_Type.equalsIgnoreCase("id")) {
			new Select(driver.findElement(By.id(Locator_Value))).selectByIndex(index);
		}
		else if (Locator_Type.equalsIgnoreCase("name")) {
			new Select(driver.findElement(By.name(Locator_Value))).selectByIndex(index);
		}
			
	}
	
	//method for Capturing Stock Number and Store in NotePad
	public static void stockCapture(String Locator_Type, String Locator_Value) throws Throwable {
		
		String Captured_Data = "";
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			Captured_Data = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("id")) {
			Captured_Data = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("name")) {
			Captured_Data = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		
		FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(Captured_Data);
		bw.flush();
		bw.close();
	}
	
	//Validating Stock Number in Stock Item Table
	public static void validateStock() throws Throwable {
		
		FileReader fr = new FileReader("./CaptureData/StockNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String Exp_Data = br.readLine();
		br.close();
		
		if (!(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).isDisplayed())) 
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchIcon"))).click();
		
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).sendKeys(Exp_Data);
		
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchButton"))).click();
		
		String Actual_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[8]/div/span/span")).getText();
		
		Assert.assertEquals(Actual_Data, Exp_Data, "Stock Number is Not Matching");
			
	}
	
	//method for Capturing Supplier Number and Store in Note pad
	
	public static void supplierCapture(String Locator_Type, String Locator_Value) throws Throwable  {
		String Captured_Data = "";
		if (Locator_Type.equalsIgnoreCase("xpath")) {
			Captured_Data = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("id")) {
			Captured_Data = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("name")) {
			Captured_Data = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		
		FileWriter fw = new FileWriter("./CaptureData/SupplierNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(Captured_Data);
		bw.flush();
		bw.close();
		
	}
	
	//Validating Supplier Number in Supplier Table
	
		public static void validateSupplier() throws Throwable {
			
			FileReader fr = new FileReader("./CaptureData/SupplierNumber.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String Exp_Data = br.readLine();
			br.close();
			
			if (!(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).isDisplayed())) 
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchIcon"))).click();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).sendKeys(Exp_Data);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchButton"))).click();
			
			String Actual_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[6]/div/span/span")).getText();
			
			Assert.assertEquals(Actual_Data, Exp_Data, "Supplier Number is Not Matching");
				
		}
		
		//method for Capturing Customer Number and Store in Note pad
		
		public static void customerCapture(String Locator_Type, String Locator_Value) throws Throwable  {
			String Captured_Data = "";
			if (Locator_Type.equalsIgnoreCase("xpath")) {
				Captured_Data = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
			}
			else if (Locator_Type.equalsIgnoreCase("id")) {
				Captured_Data = driver.findElement(By.id(Locator_Value)).getAttribute("value");
			}
			else if (Locator_Type.equalsIgnoreCase("name")) {
				Captured_Data = driver.findElement(By.name(Locator_Value)).getAttribute("value");
			}
			
			FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(Captured_Data);
			bw.flush();
			bw.close();
			
		}
		
		//Validating Customer Number in Customer Table
		
			public static void validateCustomer() throws Throwable {
				
				FileReader fr = new FileReader("./CaptureData/CustomerNumber.txt");
				BufferedReader br = new BufferedReader(fr);
				
				String Exp_Data = br.readLine();
				br.close();
				
				if (!(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).isDisplayed())) 
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchIcon"))).click();
				
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).clear();
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchBox"))).sendKeys(Exp_Data);
				
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("SearchButton"))).click();
				
				String Actual_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[5]/div/span/span")).getText();
				
				Assert.assertEquals(Actual_Data, Exp_Data, "Customer Number is Not Matching");
					
			}
	
}
