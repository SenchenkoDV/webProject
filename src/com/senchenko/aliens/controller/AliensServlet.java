package com.senchenko.aliens.controller;

import com.senchenko.aliens.command.CommandFactory;
import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.manager.MessageManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/web")
public class AliensServlet extends HttpServlet {
    private static final String ERROR_PAGE_ATTRIBUTE = "nullPage";
    private static final String ERROR_PAGE_MESSAGE = "nullPage";
    private static final String INDEX_PAGE = "index";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(request);
        CommandFactory factory = CommandFactory.INSTANCE;
        CommandResult result = factory.getCommand(requestContent).execute(requestContent);
        requestContent.insertAttributes(request);
        if (result.getResponseType().equals(CommandResult.ResponseType.INVALIDATE)) {
            request.getSession().invalidate();
        }

        if (result != null || result.getResponseType().equals(CommandResult.ResponseType.REDIRECT)) {
            response.sendRedirect(result.getPage());
        } else {
            request.getSession().setAttribute(ERROR_PAGE_ATTRIBUTE, MessageManager.EN.getMessage(ERROR_PAGE_MESSAGE));
            response.sendRedirect(request.getContextPath() + PageManager.getProperty(INDEX_PAGE));
        }
    }
}
