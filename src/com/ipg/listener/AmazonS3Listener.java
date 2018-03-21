package com.ipg.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Application Lifecycle Listener implementation class AmazonS3Listener
 *
 */
@WebListener
public class AmazonS3Listener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public AmazonS3Listener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	ServletContext context = arg0.getServletContext();
    	setAmazonS3Bucket(context);
    	setAmazonS3Client(context);
    	
    	
    }
	
    public static void setAmazonS3Client(ServletContext context) {
    	AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
    	context.setAttribute("amazons3-client", s3client);
    }
    
    /*
	 * Initializing Amazon S3 bucket 'amazon-S3-bucket'
	 */
	public static void setAmazonS3Bucket(ServletContext context) {
		context.setAttribute("amazon-S3-bucket", "ipgjmjjaya");
	}
	
	/*
	 * */
	public static String getAmazonS3Bucket(ServletContext context) {
		return (String)context.getAttribute("amazon-S3-bucket");
	}
	
	public static AmazonS3 getAmazonS3Cliet(ServletContext context) {
		return (AmazonS3)context.getAttribute("amazons3-client");
	}
}
