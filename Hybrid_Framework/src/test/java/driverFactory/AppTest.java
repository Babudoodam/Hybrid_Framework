package driverFactory;

import org.testng.annotations.Test;

public class AppTest {

@Test
public void kickStart() throws Throwable {
	
	DriverScript test = new DriverScript();
	test.startTest();
	
}
	
}
