package com.senchenko.aliens.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface Content {
    HashMap<String, Object> getRequestAttributes();
    HashMap<String, String[]> getRequestParameters();
    HashMap<String, Object> getSessionAttributes();
    void extractValues(HttpServletRequest request);
    void insertAttributes(HttpServletRequest request);
}
