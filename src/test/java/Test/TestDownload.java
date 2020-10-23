package Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utils.PDFReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestDownload
{
	WebDriver driver;
	File file;

	@BeforeTest
	public void setUp() throws InterruptedException
	{

		file = new File(RandomStringUtils.randomAlphanumeric(7));
		file.mkdir();
		Thread.sleep(5000);
		
		String folderName = file.getAbsolutePath();
		//System.out.println("Folder Path : -> "+folderName);

		WebDriverManager.chromedriver().setup();

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", folderName);
		chromePrefs.put("download.prompt_for_download", "false");
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--test-type");
		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		dc.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new ChromeDriver(dc);
		driver.manage().window().maximize();
	}

	@Test
	public void downloadAndReadFile() throws InterruptedException, IOException
	{
		driver.get("https://the-internet.herokuapp.com/");
		driver.findElement(By.cssSelector("#content > ul > li:nth-child(17) > a")).click();
		driver.findElement(By.cssSelector("#content > div > a:nth-child(8)")).click();
		Thread.sleep(10000);
		String filePath = null;
		
		for (File f : file.listFiles())
		{
			filePath = f.getAbsolutePath().toString();
			//System.out.println(f.getAbsolutePath().toString());
			break;
		}
		
		System.out.println(PDFReader.getDataFromPDF(driver, filePath));

	}

	@AfterTest
	public void tearDown() throws InterruptedException
	{
		Thread.sleep(10000);
		driver.quit();
		for (File f : file.listFiles())
		{
			f.delete();
		}
		file.delete();
	}
}
