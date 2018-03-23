package com.ipg.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
/**
 * @author Tram
 *
 * 
 */
public class DetectObject {

    public JsonObject getDataFromAPI(String api, String accessToken, String imageUrl) throws IOException {

        JsonReader jReader;
        JsonObject jsonBody;
        
        JsonObject jsonImage = Json.createObjectBuilder().add("image", imageUrl).build();
        URL apiURL = new URL(api);
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-Access-Token", accessToken);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        byte[] body = jsonImage.toString().getBytes();
        connection.setFixedLengthStreamingMode(body.length);
        OutputStream os = connection.getOutputStream();
        os.write(body);
        os.flush();
        int httpCode = connection.getResponseCode();
        if (httpCode == 200) {
            jReader = Json.createReader(connection.getInputStream());
            jsonBody = jReader.readObject();
            System.out.println(jsonBody);
            return jsonBody;
        } else {
            jReader = Json.createReader(connection.getErrorStream());
            JsonObject jsonError = jReader.readObject();
            System.out.println(jsonError);
            return jsonError;
        }

    }
    
    
    public String loadFile(HttpServletRequest request) throws IOException {
    	
    	
    	 Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hiepnguyen",
                "api_key", "887872463762523",
                "api_secret", "GRZ1_ZDbl9ivRxLyBN3MVAvlPko"));

             	File toUpload = AmazonS3UploaderUtil.retrieveFilesFromRequestBody(request);
             
             Map<String, Object> uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
             String imageurlcloud = (String)uploadResult.get("secure_url");
             
             return imageurlcloud;
    }

    public String loadFile1(HttpServletRequest request) throws IOException {
    	
    	
   	 Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
               "cloud_name", "hiepnguyen",
               "api_key", "887872463762523",
               "api_secret", "GRZ1_ZDbl9ivRxLyBN3MVAvlPko"));

            	File toUpload = AmazonS3UploaderUtil.retrieveFilesFromRequestBody(request);
            
            Map<String, Object> uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
            String imageurlcloud = (String)uploadResult.get("secure_url");
            
            return imageurlcloud;
   }
}
