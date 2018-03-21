package com.ipg.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import com.amazonaws.services.rekognition.model.S3Object;
import com.ipg.listener.AmazonRekognitionListener;
import com.ipg.util.AmazonS3UploaderUtil;

/**
 * Servlet implementation class CelebrityRecognitionServlet
 */
@WebServlet("/CelebrityRecognitionServlet")
public class CelebrityRecognitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CelebrityRecognitionServlet() {
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
		String imageName = AmazonS3UploaderUtil.uploadToAmazonS3Bucket(request);
		ServletContext context = request.getSession().getServletContext();
		String bucket = AmazonRekognitionListener.getAmazonS3Bucket(context);
		AmazonRekognition rekognitionClient = AmazonRekognitionListener.getRekognitionClient(context);
		
		RecognizeCelebritiesRequest celebDectRequest = new RecognizeCelebritiesRequest()
				.withImage(new Image().withS3Object(new S3Object().withName(imageName).withBucket(bucket)));
		
		RecognizeCelebritiesResult result= rekognitionClient.recognizeCelebrities(celebDectRequest);
		
		////////////////////////////////////////////
		//Display recognized celebrity information
		
        List<Celebrity> celebs=result.getCelebrityFaces();
        System.out.println(celebs.size() + " celebrity(s) were recognized.\n");

        for (Celebrity celebrity: celebs) {
            System.out.println("Celebrity recognized: " + celebrity.getName());
            System.out.println("Celebrity ID: " + celebrity.getId());
            BoundingBox boundingBox=celebrity.getFace().getBoundingBox();
            System.out.println("position: " +
               boundingBox.getLeft().toString() + " " +
               boundingBox.getTop().toString());
            System.out.println("Further information (if available):");
            for (String url: celebrity.getUrls()){
               System.out.println(url);
            }
            System.out.println();
         }
         System.out.println(result.getUnrecognizedFaces().size() + " face(s) were unrecognized.");
     }
		///////////////////////////////////////////
}
