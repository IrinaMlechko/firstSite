package com.example.firstsite.controller;

import java.io.*;

import com.example.firstsite.command.Command;
import com.example.firstsite.command.CommandType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.example.firstsite.util.RequestParameterName.COMMAND;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandStr = request.getParameter(COMMAND);
        Command command = CommandType.define(commandStr);
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    public void destroy() {
    }
}