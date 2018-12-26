package com.senchenko.aliens.controller;

import com.senchenko.aliens.command.Command;
import com.senchenko.aliens.command.CommandFactory;
import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.manager.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/web")
public class AliensServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("testtt");
        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(request);
        CommandFactory factory = CommandFactory.INSTANCE;
        System.out.println("testtttt");
        CommandResult result = factory.getCommand(requestContent).execute(requestContent);
        requestContent.insertAttributes(request);
        if (result.getResponseType().equals(CommandResult.ResponseType.INVALIDATE)) {
            request.getSession().invalidate();
        }

        if (result != null || result.getResponseType().equals(CommandResult.ResponseType.REDIRECT)) {
            response.sendRedirect(result.getPage());
        } else {
            request.getSession().setAttribute("nullPage", MessageManager.EN.getMessage("nullpage"));
            response.sendRedirect(request.getContextPath() + PageManager.getProperty("index"));
        }
//        String page = null;
//        ActionCommand command = ActionFactory.defineCommand(request);
//        page = command.execute(request);
//        if (page != null){
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//            dispatcher.forward(request, response);
//        }else {
//            page = PageManager.getProperty("index");
//            request.getSession().setAttribute("nullPage", MessageManager.EN.getMessage("nullpage"));
//            response.sendRedirect(request.getContextPath() + page);
//        }
        }
    }
