package com.vroozi.customerui.servlets;

/**
 * User: qureshit
 * Date: 10/4/12
 * Time: 3:30 PM
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Image servlet for serving from absolute path.
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
public class ImageServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String imagePath;
    private String itemImagePath;

    // Actions ------------------------------------------------------------------------------------

    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        ResourceBundle loadBundle = ResourceBundle.getBundle("adminui", Locale.getDefault());
        //path to store images at
        this.imagePath = loadBundle.getString("flagIconUploadPath");
        this.itemImagePath = loadBundle.getString("itemImagesPath");

        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\images".
        // In UNIX, it is just straightforward "/images".
        // If you have stored files in the WebContent of a WAR, for example in the
        // "/WEB-INF/images" folder, then you can retrieve the absolute path by:
        // this.imagePath = getServletContext().getRealPath("/WEB-INF/images");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // Get requested image by path info.
        String requestedImage = request.getPathInfo();
        String imageType = request.getParameter("type");
        if(imageType==null || imageType.equals("")) {
        	imageType = "original";
        }
        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Decode the file name (might contain spaces and on) and prepare file object.
        File image = new File(imagePath, URLDecoder.decode(requestedImage, "UTF-8"));
        File itemImage = new File(itemImagePath+"/"+imageType, URLDecoder.decode(requestedImage, "UTF-8"));
        //File itemImageOrig = new File(itemImagePath+"/original", URLDecoder.decode(requestedImage, "UTF-8"));

        if(!requestedImage.contains(".jpg") && !requestedImage.contains(".png") && !requestedImage.contains(".gif") &&
                !requestedImage.contains(".tif") && !requestedImage.contains(".bmp") && !requestedImage.contains(".jpeg")){
            List<String> list = new ArrayList<String>();
            list.add(".jpg");list.add(".png");list.add(".gif");list.add(".tif");list.add(".bmp");list.add(".jpeg");
            for(String ext : list){
                File f = new File(itemImagePath, URLDecoder.decode(requestedImage+ext, "UTF-8"));
                if(f.exists() && f.isFile()){
                    requestedImage = requestedImage+ext;
                    break;
                }
            }
        }

        File itemImageOrig = new File(itemImagePath, URLDecoder.decode(requestedImage, "UTF-8"));
        
        File downloadImage = null;
        String imageName="";
        // Check if file actually exists in filesystem.
        if (image.exists()  && image.isFile()) {
        	downloadImage = image;
        } else if (itemImage.exists() && itemImage.isFile()) {
        	downloadImage = itemImage;
        } else if (itemImageOrig.exists() && itemImageOrig.isFile()) {
        	downloadImage = itemImageOrig;
        } else {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
//            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            String contentType = "";
            String contentLength = "";

            // Open streams.
            imageName = downloadImage.getName();
            contentLength =  String.valueOf(downloadImage.length());
            contentType = getServletContext().getMimeType(downloadImage.getName());
            input = new BufferedInputStream(new FileInputStream(downloadImage), DEFAULT_BUFFER_SIZE);

            /// Check if file is actually an image (avoid download of other files by hackers!).
            // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
            if (contentType == null || !contentType.startsWith("image")) {
                // Do your thing if the file appears not being a real image.
                // Throw an exception, or send 404, or show default/warning image, or just ignore it.
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                return;
            }

            response.setHeader("Content-Length", contentLength);
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

}
