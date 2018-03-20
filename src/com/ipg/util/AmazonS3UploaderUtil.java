/**
 * 
 */
package com.ipg.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ipg.listener.AmazonRekognitionListener;

/**
 * @author jeewan Kadangamage
 *
 */
public class AmazonS3UploaderUtil {
	
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	
	
	public static String uploadToAmazonS3Bucket(HttpServletRequest request) {
		String bucketName = AmazonRekognitionListener.getAmazonS3Bucket(request.getServletContext());
		File file = retrieveFilesFromRequestBody(request);
		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
		PutObjectResult imagePutObjectResult = null;
	        try {
	            System.out.println("Uploading a new object to S3 from a file\n");
	           imagePutObjectResult = s3client.putObject(new PutObjectRequest(
	            		                 bucketName, file.getName(), file));

	         } catch (AmazonServiceException ase) {
	            System.out.println("Caught an AmazonServiceException, which " +
	            		"means your request made it " +
	                    "to Amazon S3, but was rejected with an error response" +
	                    " for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
	            System.out.println("Caught an AmazonClientException, which " +
	            		"means the client encountered " +
	                    "an internal error while trying to " +
	                    "communicate with S3, " +
	                    "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        }
	        return file.getName();
	}
	
	//Jeewan: code from http://www.codejava.net/java-ee/servlet/eclipse-file-upload-servlet-with-apache-common-file-upload
	public static File retrieveFilesFromRequestBody(HttpServletRequest request) {
		
		// checks if the request actually contains upload file
//		if (!ServletFileUpload.isMultipartContent(request)) {
//		    PrintWriter writer = response.getWriter();
//		    writer.println("Request does not contain upload data");
//		    writer.flush();
//		    return;
//		}
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		 
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		// constructs the directory path to store upload file
		String uploadPath = request.getServletContext().getRealPath("")
		    + File.separator + UPLOAD_DIRECTORY;
		
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
		    uploadDir.mkdir();
		}
		
		List<FileItem> formItems;
		File storeFile = null;
		try {
			formItems = upload.parseRequest(request);
			Iterator<FileItem> iter = formItems.iterator();
			
			// iterates over form's fields
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();
			    // processes only fields that are not form fields
			    if (!item.isFormField()) {
			        String fileName = new File(item.getName()).getName();
			        String filePath = uploadPath + File.separator + fileName;
			        storeFile = new File(filePath);
			 
			        // saves the file on disk
			        item.write(storeFile);
			        break;
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return storeFile;
	}

}
