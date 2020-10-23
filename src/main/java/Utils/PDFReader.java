package Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;

public class PDFReader
{
	public static String getDataFromPDF(WebDriver driver, String filePath) throws IOException{
		driver.get(filePath);
		
		String currentURL = driver.getCurrentUrl();
		URL url = new URL(currentURL);
		
		InputStream is = url.openStream();
		BufferedInputStream bufferIs = new BufferedInputStream(is);
		
		PDDocument document = PDDocument.load(bufferIs);
		String pdfContent = new PDFTextStripper().getText(document);
		
		return pdfContent;
		
	}
}
