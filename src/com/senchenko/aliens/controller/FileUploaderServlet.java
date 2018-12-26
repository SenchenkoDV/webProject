//package com.senchenko.aliens.controller;
//
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//import java.io.File;
//import java.io.IOException;
//
//@WebServlet(urlPatterns = {"/upload"})
//@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024
//        , maxFileSize = 1024 * 1024 * 5
//        , maxRequestSize = 1024 * 1024 * 5 * 5)
//public class FileUploaderServlet extends HttpServlet {
//    private static final Logger logger = LogManager.getLogger();
//    private static final String UPLOAD_DIR = "images/heroes";
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String applicationPath = req.getServletContext().getRealPath("");
//        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
//        File fileSaveDir = new File(uploadFilePath);
//        if(!fileSaveDir.exists()) {
//            fileSaveDir.mkdirs();
//        }
//        try {
//            for(Part part : req.getParts()) {
//                if (part.getSubmittedFileName() != null) {
//                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
//                    req.setAttribute("result", part.getSubmittedFileName() + " upload successfully");
//                }
//            }
//        } catch (IOException e) {
//            logger.log(Level.ERROR, "Error in I/O", e);
//        }
//    }
//}
