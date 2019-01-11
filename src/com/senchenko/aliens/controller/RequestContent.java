package com.senchenko.aliens.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    private HashMap<String, Object> requestAttributes = new HashMap<>();
    private HashMap<String, String[]> requestParameters = new HashMap<>();
    private HashMap<String, Object> sessionAttributes = new HashMap<>();

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void extractValues(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attribute = attributeNames.nextElement();
            requestAttributes.put(attribute, request.getAttribute(attribute));
        }
        attributeNames = request.getParameterNames();
        while (attributeNames.hasMoreElements()) {
            String attribute = attributeNames.nextElement();
            requestParameters.put(attribute, request.getParameterValues(attribute));
        }
        attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attribute = attributeNames.nextElement();
            sessionAttributes.put(attribute, request.getSession().getAttribute(attribute));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> attribute : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(attribute.getKey(), attribute.getValue());
        }
        for (Map.Entry<String, Object> attribute : requestAttributes.entrySet()) {
            request.setAttribute(attribute.getKey(), attribute.getValue());
        }
        for (Map.Entry<String, String[]> attribute : requestParameters.entrySet()) {
            request.getSession().setAttribute(attribute.getKey(), attribute.getValue());
        }
    }
}
