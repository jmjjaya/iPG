package com.ipg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import com.amazonaws.services.rekognition.model.S3Object;
import com.google.gson.Gson;
import com.ipg.listener.AmazonRekognitionListener;
import com.ipg.listener.AmazonS3Listener;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String imageName = AmazonS3UploaderUtil.uploadToAmazonS3Bucket(request);
		ServletContext context = request.getSession().getServletContext();
		String bucket = AmazonS3Listener.getAmazonS3Bucket(context);
		AmazonRekognition rekognitionClient = AmazonRekognitionListener.getRekognitionClient(context);
		PrintWriter out = response.getWriter();

		RecognizeCelebritiesRequest celebDectRequest = new RecognizeCelebritiesRequest()
				.withImage(new Image().withS3Object(new S3Object().withName(imageName).withBucket(bucket)));

		RecognizeCelebritiesResult result = rekognitionClient.recognizeCelebrities(celebDectRequest);

		//Jeewan: Convert to Json Object
		List<Celebrity> celebs = result.getCelebrityFaces();
		JSONObject celebsJson = new JSONObject();
		JSONArray allDataArray = new JSONArray();

		for (Celebrity celebrity : celebs) {
			System.out.println(celebrity.getName());
			JSONObject celeb = new JSONObject();
			celeb.put("Name", celebrity.getName());
			celeb.put("url", celebrity.getUrls().get(0));
			allDataArray.put(celeb);
			
		}
		System.out.println(allDataArray);
		System.out.println(celebsJson);
		out.print(celebsJson.put("celebs",allDataArray).toString());
		out.flush();
	}
}
