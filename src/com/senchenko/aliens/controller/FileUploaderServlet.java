package com.senchenko.aliens.controller;

import com.senchenko.aliens.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploaderServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIR = "images/heroes";
    private static final String FILE_PASS_ATTRIBUTE = "filePath";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String ALIENS_SERVLET_URL = "/web";
    private static final String SUCCESSFUL_UPLOAD_MESSAGE = "fileUploadSuccessful";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String applicationPath = req.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if(!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        try {
            for(Part part : req.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                    req.setAttribute(FILE_PASS_ATTRIBUTE,
                            File.separator + UPLOAD_DIR + File.separator + part.getSubmittedFileName());
                    req.setAttribute(RESULT_ATTRIBUTE, part.getSubmittedFileName() +
                            MessageManager.getMessage(SUCCESSFUL_UPLOAD_MESSAGE));
                    req.getRequestDispatcher(ALIENS_SERVLET_URL).forward(req, resp);
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error in I/O", e);
            resp.sendRedirect(ALIENS_SERVLET_URL);
        }
    }
}
