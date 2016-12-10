package com.vroozi.customerui.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mortennobel.imagescaling.ResampleOp;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.model.CatalogProxy;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;

public class Utils {

    private enum ImageSize {
        THUMBNAIL(46, 23),
        MEDIUM(104, 52),
        LARGE(135, 70);

        //    small - 50w x 40h
        //      medium - 125w x 110h
//        large - 275w x 260h


        int width;
        int height;

        ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    };

    public static boolean isNullOrVoid(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String getDateAsString(Date date) {
        String format="M/d/yyyy";

        String formatedDateString;

        if(date==null) {
            return "";
        }
        Format formatter;
        formatter = new SimpleDateFormat(format);
        formatedDateString = formatter.format(date);
        return formatedDateString;
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public static String readFileAsString(String filePath) throws IOException {
        if(StringUtils.isEmpty(filePath)) return null;

        File file = new File(filePath);
        if(!file.exists()) return null;

        int len = 0;
        char[] chr = new char[4096];
        final StringBuffer buffer = new StringBuffer();

        final FileReader reader = new FileReader(file);
        try {
            while ((len = reader.read(chr)) > 0) {
                buffer.append(chr, 0, len);
            }
        } finally {
            reader.close();
        }
        return buffer.toString();
    }

    public static void generateImage(String imageFile, String fileName, String location) throws IOException {
        BufferedImage sourceImage = ImageIO.read(
                new File(imageFile));

        String ext = "JPG";
        if(imageFile.contains(".")){
            ext = imageFile.substring(imageFile.lastIndexOf(".")+1, imageFile.length());
        }
        for (ImageSize imageSize : ImageSize.values()) {
            try{
                if (sourceImage.getHeight() > imageSize.height || sourceImage.getWidth() > imageSize.width)
                {

                    double newWidth = sourceImage.getWidth();
                    double newHeight = sourceImage.getHeight();

                    if (newWidth > imageSize.width)
                    {
                        newHeight = (imageSize.width * sourceImage.getHeight()) / sourceImage.getWidth();
                        newWidth = imageSize.width;
                    }

                    if (newHeight > imageSize.height)
                    {
                        newWidth = (imageSize.height * sourceImage.getWidth()) / sourceImage.getHeight();
                        newHeight = imageSize.height;
                    }

                    ResampleOp resampleOp = new ResampleOp ((int) newWidth,(int) newHeight);
                    BufferedImage newBImage = resampleOp.filter(sourceImage, null);
                    ImageIO.write(newBImage, ext, new File(location + File.separator + imageSize + "_" + fileName));
                }
                else
                {
                    ResampleOp resampleOp = new ResampleOp (sourceImage.getWidth(), sourceImage.getHeight());
                    BufferedImage newBImage = resampleOp.filter(sourceImage, null);
                    ImageIO.write(newBImage, ext, new File(location + File.separator + imageSize + "_" + fileName));
                }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
    }

    public static String getFormatedDate(String dateFormat, Date date) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static void generateImage(String imageFile, String fileName, String location, double width, double height) throws IOException {
        BufferedImage sourceImage = ImageIO.read(
                new File(imageFile));
        try{
            String ext = "JPG";
            if(imageFile.contains(".")){
                ext = imageFile.substring(imageFile.lastIndexOf(".")+1, imageFile.length());
            }
            if (sourceImage.getHeight() > height || sourceImage.getWidth() > width)
            {

                double newWidth = sourceImage.getWidth();
                double newHeight = sourceImage.getHeight();

                if (newWidth > width)
                {
                    newHeight = (width * sourceImage.getHeight()) / sourceImage.getWidth();
                    newWidth = width;
                }

                if (newHeight > height)
                {
                    newWidth = (height * sourceImage.getWidth()) / sourceImage.getHeight();
                    newHeight = height;
                }

                ResampleOp resampleOp = new ResampleOp ((int) newWidth,(int) newHeight);
                BufferedImage newBImage = resampleOp.filter(sourceImage, null);
                ImageIO.write(newBImage, ext, new File(location + File.separator + fileName));
            }
            else
            {
                ResampleOp resampleOp = new ResampleOp (sourceImage.getWidth(), sourceImage.getHeight());
                BufferedImage newBImage = resampleOp.filter(sourceImage, null);
                ImageIO.write(newBImage, ext, new File(location + File.separator + fileName));
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
