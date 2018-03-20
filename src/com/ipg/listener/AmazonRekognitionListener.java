package com.ipg.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

/**
 * @author Jeewan Kadangamage
 *
 * 
 */

/**
 * Application Lifecycle Listener implementation class AmazonRekognitionListener
 *
 */
public class AmazonRekognitionListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public AmazonRekognitionListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext context = arg0.getServletContext();
		setRekognitionClient(context);
		setAmazonS3Bucket(context);
	}

	/*
	 * Jeewan: Set Amazon Recognition Credential attribute
	 * 'rekognition-client'
	 */
	public static void setRekognitionClient(ServletContext context) {
		AWSCredentials credentials;
		
		// Jeewan: Fetch the credentials Access Key Id and Secret Access Key from credentials file
		try {
			credentials = new ProfileCredentialsProvider().getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
		}

		// Jeewan: Create AmazonRekognitionClientBuilder with S3 bucket region and our credentials.
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		context.setAttribute("rekognition-client", rekognitionClient);
	}

	/*
	 * Jeewan: Initializing Amazon S3 bucket 'amazon-S3-bucket'
	 */
	public static void setAmazonS3Bucket(ServletContext context) {
		context.setAttribute("amazon-S3-bucket", "ipgjmjjaya");
	}
	
	/*Get rekognition-client
	 * */
	public static AmazonRekognition getRekognitionClient(ServletContext context) {
		return (AmazonRekognition)context.getAttribute("rekognition-client");
	}
	
	/*
	 * */
	public static String getAmazonS3Bucket(ServletContext context) {
		return (String)context.getAttribute("amazon-S3-bucket");
	}
}
