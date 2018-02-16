package uk.gov.dvsa.webservice;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.FileUtils;

/**
 * Send a SOAP message over HTTP or HTTPS.
 * 
 * <p>
 * HTTPS will only work if the private certificate of the target machine has
 * been imported into the JMV running this code. Public certificates should work
 * fine
 * </p>
 * 
 * @author Simon Cutts
 * 
 */
public class Soap {
	public static final String SOAP_ACTION = "SOAPAction";
	public static final String ACCEPT = "Accept";
	public static final String SOAP_MEDIA_TYPE = "application/soap+xml,application/" + "dime,multipart/related,text/*";
	public static final String ENCODING = "utf-8";
	public static final String MIME_TYPE = "text/xml";

	private String url;
	private final String host;
	private String soapAction;
	private int port;
	private Credentials credentials;

	/**
	 * Create an instance of Soap. The parameters make up the full URL of the
	 * end point
	 * 
	 * @param host
	 *            the name of the host and protocol, e.g. http://localhost or
	 *            https://www.webservicex.net/
	 * @param port
	 *            the port number, e.g. 7001, can be null
	 * @param path
	 *            the URL portion of the proxy, without host or port
	 */
	public Soap(String host, String port, String path) {
		this.host = host;
		if (port != null) {
			this.port = Integer.parseInt(port);
			url = host + ":" + port + path;
		} else {
			url = host + path;
		}
	}

	/**
	 * Create an instance of Soap. The parameters make up the full URL of the
	 * end point
	 * 
	 * @param host
	 *            the name of the host and protocol, e.g. http://localhost or
	 *            https://www.webservicex.net/
	 * @param port
	 *            the port number, e.g. 7001, can be null
	 * @param path
	 *            the URL portion of the proxy, without host or port
	 * @param soapAction
	 *            the name of the operation on the WSDL to call, can be null
	 * @param credentials
	 *            the username and password if required to authenticate the
	 *            request, can be null
	 */
	public Soap(String host, String port, String path, String soapAction, Credentials credentials) {
		this(host, port, path);
		this.soapAction = soapAction;
		this.credentials = credentials;
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	/**
	 * Send the the SOAP message envelope, includes header and body from a File
	 * 
	 * @param soapFile
	 */
	public String send(File soapFile) {
		try {
			Charset chr = null;
			String message = FileUtils.readFileToString(soapFile, chr);
			return send(message);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Send the the SOAP message envelope, includes header and body
	 * 
	 * @param soapMessage
	 */
	public String send(String soapMessage) {
		PostMethod post = new PostMethod(url);
		post.setRequestHeader(ACCEPT, SOAP_MEDIA_TYPE);

		// Add the SOAP action, if WSDL has multiple operations
		if (soapAction != null) {
			post.setRequestHeader(SOAP_ACTION, soapAction);
		} else {
			post.setRequestHeader(SOAP_ACTION, "");
		}
		/*
		 * Request content will be retrieved directly from the input stream
		 */
		try {
			RequestEntity entity = new StringRequestEntity(soapMessage, MIME_TYPE, ENCODING);
			post.setRequestEntity(entity);

			HttpClient client = new HttpClient();

			// Provide authentication details, if required
			if (credentials != null) {
				client.getState().setCredentials(new AuthScope(host, port),
						new UsernamePasswordCredentials(credentials.getUsername(), credentials.getPassword()));
			}

			client.executeMethod(post);
			return post.getResponseBodyAsString();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
