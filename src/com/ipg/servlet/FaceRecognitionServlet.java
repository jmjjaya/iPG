package com.ipg.servlet;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipg.util.DetectObject;

/**
 * @author Tram
 *
 * 
 */

/**

/**
 * Servlet implementation class FaceRecognitionServlet
 */
@WebServlet("/FaceRecognitionServlet")

public class FaceRecognitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FaceRecognitionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// System.out.println("1");
		DetectObject data = new DetectObject();
		PrintWriter out = response.getWriter();
		String imageurlcloud = data.loadFile(request);

		String api = "https://dev.sighthoundapi.com/v1/detections?type=face,person&faceOption=gender,landmark,age,emotion,pose";
		String accessToken = "BFLjOL9bHHC6ReLdgaqVYfKSZHLC8d8b3Cz4";

		try {

			JsonObject jsonBody = data.getDataFromAPI(api, accessToken, imageurlcloud);
			HttpSession session = request.getSession();
			session.setAttribute("face", jsonBody);
			session.setAttribute("urlface", imageurlcloud);

			out.print(jsonBody);
			out.flush();
			System.out.print(jsonBody);
		} catch (IOException ex) {
			Logger.getLogger(FaceRecognitionServlet.class.getName()).log(Level.SEVERE, null, ex);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
