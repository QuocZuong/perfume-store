package Lib;

import jakarta.servlet.http.Part;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ImageUploader {

    public static final String IMGUR_API_ENDPOINT = "https://api.imgur.com/3/image";
    public static final String IMGUR_CLIENT_ID = "87da474f87f4754";

    public static String uploadImageToCloud(Part imagePart) throws IOException {
        String imgURL = "/RESOURCES/images/icons/default-perfume.png";
        File imageFile = convertPartToFile(imagePart);

        // Create URL object
        URL url = new URL(IMGUR_API_ENDPOINT);
        HttpURLConnection URLconn = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        URLconn.setRequestMethod("POST");

        // Set the Authorization header
        URLconn.setRequestProperty("Authorization", "Client-ID " + IMGUR_CLIENT_ID);

        // Enable input and output streams
        URLconn.setDoInput(true);
        URLconn.setDoOutput(true);

        // Create a boundary for the multipart/form-data
        String boundary = "---------------------------" + System.currentTimeMillis();

        // Set the content type as multipart/form-data with the boundary
        URLconn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        // Create the request body
        DataOutputStream rq = new DataOutputStream(URLconn.getOutputStream());
        rq.writeBytes("--" + boundary + "\r\n");
        rq.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"" + imageFile.getName() + "\"\r\n");
        rq.writeBytes("Content-Type: application/octet-stream\r\n\r\n");

        // Read the image file and write it to the rq body
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            rq.write(buffer, 0, bytesRead);
        }
        rq.writeBytes("\r\n");
        rq.writeBytes("--" + boundary + "--\r\n");
        rq.flush();
        rq.close();

        // Get the response
        int responseCode = URLconn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            InputStreamReader inputStreamReader = new InputStreamReader(URLconn.getInputStream(),
                    StandardCharsets.UTF_8);
            StringBuilder rp = new StringBuilder();
            int c;
            while ((c = inputStreamReader.read()) != -1) {
                rp.append((char) c);
            }
            inputStreamReader.close();

            // Extract the image URL from the response
            String imageUrl = extractImageUrl(rp.toString());
            imgURL = imageUrl;

            // Display the image URL
            System.out.println("Image uploaded successfully. Image URL: " + imageUrl);

        } else {
            System.out.println("Error occurred while uploading the image. Response Cod");
        }

        URLconn.disconnect();
        fileInputStream.close();
        return imgURL;
    }

    public static File convertPartToFile(Part part) throws IOException {
        // String fileName = part.getSubmittedFileName();
        File tempFile = File.createTempFile("temp", null);
        tempFile.deleteOnExit();

        try ( InputStream inputStream = part.getInputStream();  OutputStream outputStream = new FileOutputStream(tempFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

    private static String extractImageUrl(String response) {
        // Parse the JSON response to extract the image URL
        int startIndex = response.indexOf("\"link\":\"") + 8;
        int endIndex = response.indexOf("\"", startIndex);
        return response.substring(startIndex, endIndex);
    }

}
