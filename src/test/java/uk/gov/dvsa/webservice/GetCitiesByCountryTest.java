package uk.gov.dvsa.webservice;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for GetCitiesByCountry SOAP service
 *
 * @author Simon Cutts
 *
 */
public class GetCitiesByCountryTest extends TestCase {
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
	public GetCitiesByCountryTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(GetCitiesByCountryTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testFindBelfast() {
		// Prepare a SOAP message by configuring the host, port, URL and soap
		// action of the web service
		Soap s = new Soap(cfg.getHost(), cfg.getPort(), "globalweather.asmx",
				"http://www.webserviceX.NET/GetCitiesByCountry", null);

		// Load the SOAP message from the file system
		String response = s.send(new File("src/test/resources/cities-soap.xml"));

		// Check Belfast is a UK city returned in the response and output
		// response in case a failure is returned.
		assertTrue(response, response.contains("Belfast"));
	}
}
