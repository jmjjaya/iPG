package com.ipg.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ipg.listener.AmazonS3Listener;

/**
 * Servlet implementation class PhotoGalleryServlet
 */
@WebServlet("/PhotoGalleryServlet")
public class PhotoGalleryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoGalleryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bucketName = AmazonS3Listener.getAmazonS3Bucket(request.getServletContext());
		ServletContext context = request.getSession().getServletContext();
		AmazonS3 s3client = AmazonS3Listener.getAmazonS3Cliet(context);
		ListObjectsV2Result result;
		try {
			final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(16);
			do {               
	               result = s3client.listObjectsV2(req);
	               
	               for (S3ObjectSummary objectSummary : 
	                   result.getObjectSummaries()) {
	                   System.out.println(" - " + objectSummary.getKey() + "  " +
	                           "(size = " + objectSummary.getSize() + 
	                           ")");
	               }
	               req.setContinuationToken(result.getNextContinuationToken());
	            } while(result.isTruncated() == true ); 
		
		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, " +
            		"which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, " +
            		"which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
		
	}
}
