package driverFactory;



import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {

	public static WebDriver driver;
	String Inputpath = "./FileInput/controller.xlsx";
	String Outputpath = "./FileOutput/hybridResult.xlsx";
	String MSheet = "MasterTestCases";
	ExtentReports report;
	ExtentTest logger;

	public void startTest() throws Throwable {
		
		ExcelFileUtil xl = new ExcelFileUtil(Inputpath);
		//iterate MSheet
		for (int i = 1; i <=xl.rowCount(MSheet); i++) {

			if(xl.getCellData(MSheet, i, 2).equalsIgnoreCase("Y")) {
				String MStatus = "";
				String Module = xl.getCellData(MSheet, i, 1);
				report = new ExtentReports("./target/ExtentReports/"+Module+FunctionLibrary.dateFormat()+".html");
				logger = report.startTest(Module);

		
				for (int j = 1; j <=xl.rowCount(Module); j++) {
					String Description = xl.getCellData(Module, j, 0);
					String Object_name = xl.getCellData(Module, j, 1);
					String Locator_Type = xl.getCellData(Module, j, 2);
					String Locator_Value = xl.getCellData(Module, j, 3);
					String Test_Data = xl.getCellData(Module, j, 4);
					try {
						if (Object_name.equalsIgnoreCase("startBrowser")) {
							FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("openUrl")) {
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("validateTitle")) {
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("dropDownAction")) {
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_name.equalsIgnoreCase("stockCapture")) {
							FunctionLibrary.stockCapture(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_name.equalsIgnoreCase("validateStock")) {
							FunctionLibrary.validateStock();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("supplierCapture")) {
							FunctionLibrary.supplierCapture(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("validateSupplier")) {
							FunctionLibrary.validateSupplier();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("customerCapture")) {
							FunctionLibrary.customerCapture(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_name.equalsIgnoreCase("validateCustomer")) {
							FunctionLibrary.validateCustomer();
							logger.log(LogStatus.INFO, Description);
						}
						
						logger.log(LogStatus.PASS, Description);
						MStatus = "True";
						xl.setCellData(Module, j, 5, "Pass", Outputpath);

					}catch (Throwable e) {
						logger.log(LogStatus.FAIL, Description);
						MStatus = "False";
						xl.setCellData(Module, j, 5, "Pass", Outputpath);
						System.out.println(e.getMessage());
					}
					
					if (MStatus.equalsIgnoreCase("True")) {
						xl.setCellData(MSheet, i, 3, "Pass", Outputpath);
					}
					else if (MStatus.equalsIgnoreCase("False")) {
						xl.setCellData(MSheet, i, 3, "Fail", Outputpath);
					}
					
					report.endTest(logger);
					report.flush();
				}


			}
			else if (xl.getCellData(MSheet, i, 2).equalsIgnoreCase("N")) {
				xl.setCellData(MSheet, i, 3, "Blocked", Outputpath);
			}

		}

	}


}
