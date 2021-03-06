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
public class FrontServlet extends HttpServlet {
    private static final String ERROR_PAGE_ATTRIBUTE = "nullPage";
    private static final String ERROR_PAGE_MESSAGE = "nullPage";
    private static final String INDEX_PAGE = "index";
    private static final String EMPTY_PAGE = "";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestContent requestContent = new RequestContent();
        request.getSession();
        requestContent.extractValues(request);
        CommandFactory factory = CommandFactory.INSTANCE;
        CommandResult result = factory.getCommand(requestContent).execute(requestContent);
        requestContent.insertAttributes(request);
        if (result.getResponseType().equals(CommandResult.ResponseType.FORWARD)) {
            request.getRequestDispatcher(result.getPage()).forward(request,response);
        } else if (result.getResponseType().equals(CommandResult.ResponseType.REDIRECT)) {
            response.sendRedirect(result.getPage());
        } else if (result.getResponseType().equals(CommandResult.ResponseType.INVALIDATE)) {
            request.getSession().invalidate();
            response.sendRedirect(PageManager.getProperty(INDEX_PAGE));
        } else if (result.getResponseType().equals(CommandResult.ResponseType.STAY_ON_PAGE)) {
            request.getRequestDispatcher(EMPTY_PAGE).forward(request, response);
        } else {
            request.getSession().setAttribute(ERROR_PAGE_ATTRIBUTE, MessageManager.getMessage(ERROR_PAGE_MESSAGE));
            response.sendRedirect(request.getContextPath() + PageManager.getProperty(INDEX_PAGE));
        }
    }
}
