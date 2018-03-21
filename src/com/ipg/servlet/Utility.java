package com.ipg.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utility{
	public static boolean checkCookieUsername(Cookie[] cookies) {
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user")) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean checkCookieMain(Cookie[] cookies) {
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("main")) {
					return true;
				}
			}
		}
		return false;
	}
}
