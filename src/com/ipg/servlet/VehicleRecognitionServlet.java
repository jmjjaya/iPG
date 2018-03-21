package com.ipg.servlet;

import java.io.IOException;
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

import com.cloudinary.Cloudinary;


import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

/**
 * Servlet implementation class VehicleRecognitionServlet
 */
@WebServlet("/VehicleRecognitionServlet")
public class VehicleRecognitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VehicleRecognitionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DetectObject data = new DetectObject();
		String imageUrl = request.getParameter("imagedir");
		String imageurlcloud = data.loadFile(imageUrl);
		
        String api = "https://dev.sighthoundapi.com/v1/recognition?objectType=vehicle";
        String accessToken = "BFLjOL9bHHC6ReLdgaqVYfKSZHLC8d8b3Cz4";

        try {

         
            JsonObject jsonBody = data.getDataFromAPI(api, accessToken, imageurlcloud);
	        HttpSession session = request.getSession();
	        session.setAttribute("vehicle", jsonBody);
	        session.setAttribute("urlvehicle", imageurlcloud);
	        
	        System.out.println(jsonBody);
	        
	        RequestDispatcher e =  request.getRequestDispatcher("vehicle.jsp");
			e.forward(request, response);
			
		          
            System.out.print(jsonBody);
        }   catch (IOException ex) {
            Logger.getLogger(FaceRecognitionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
