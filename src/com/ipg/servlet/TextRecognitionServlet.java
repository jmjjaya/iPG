package com.ipg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.google.gson.Gson;
import com.ipg.listener.AmazonRekognitionListener;

/**
 * Servlet implementation class TextRecognitionServlet
 */
@WebServlet("/TextRecognitionServlet")
public class TextRecognitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TextRecognitionServlet() {
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
		String photo = "uniid.jpg";
		ServletContext context = request.getSession().getServletContext();
		String bucket = AmazonRekognitionListener.getAmazonS3Bucket(context);
		AmazonRekognition rekognitionClient = AmazonRekognitionListener.getRekognitionClient(context);
		PrintWriter out = response.getWriter();
		
		//Send the request to Amazon API to detect text
		DetectTextRequest textDetectRequest = new DetectTextRequest()
				.withImage(new Image().withS3Object(new S3Object().withName(photo).withBucket(bucket)));

		try {
			DetectTextResult result = rekognitionClient.detectText(textDetectRequest);
			List<TextDetection> textDetections = result.getTextDetections();
			
			String json = new Gson().toJson(textDetections);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(json);
			out.flush();
			/*System.out.println("Detected lines and words for " + photo);
			for (TextDetection text : textDetections) {

				System.out.println("Detected: " + text.getDetectedText());
				System.out.println("Confidence: " + text.getConfidence().toString());
				System.out.println("Id : " + text.getId());
				System.out.println("Parent Id: " + text.getParentId());
				System.out.println("Type: " + text.getType());
				System.out.println();
			}*/
		} catch (AmazonRekognitionException e) {
			e.printStackTrace();
		}
	}

}
