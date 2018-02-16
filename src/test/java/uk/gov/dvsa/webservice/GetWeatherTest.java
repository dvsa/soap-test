package uk.gov.dvsa.webservice;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for GetWeather SOAP service
 *
 * @author Simon Cutts
 *
 */
public class GetWeatherTest extends TestCase {
	static Configuration cfg;

	static {
		cfg = Configuration.newInstance();
	}

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public GetWeatherTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(GetWeatherTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testNoWeatherReportFound() {
		// Prepare a SOAP message by configuring the host, port, URL and soap
		// action of the web service
		Soap s = new Soap(cfg.getHost(), cfg.getPort(), "globalweather.asmx", "http://www.webserviceX.NET/GetWeather",
				null);

		// Load the SOAP message from the file system
		String response = s.send(new File("src/test/resources/weather-soap.xml"));

		// Check Data Not Found returned in the response and output
		// response in case a failure is returned.
		assertTrue(response, response.contains("Data Not Found"));
	}
}
