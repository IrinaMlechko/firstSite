package com.example.firstsite.controller;

import java.io.*;

import com.example.firstsite.command.Command;
import com.example.firstsite.command.CommandType;
import com.example.firstsite.exception.CommandException;
import com.example.firstsite.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.firstsite.util.PageName.SERVER_ERROR_PAGE;
import static com.example.firstsite.util.RequestParameterName.COMMAND;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    static Logger logger = LogManager.getLogger();

    public void init() {
        ConnectionPool.getInstance();
        logger.info("Init servlet" + this.getServletInfo());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String commandStr = request.getParameter(COMMAND);
        Command command = CommandType.define(commandStr);
        String page;
        try {
            page = command.execute(request);
            request.getRequestDispatcher(page).forward(request,response);
        } catch (CommandException e) {
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher(SERVER_ERROR_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        logger.info("Servlet destroyed" + this.getServletName());

    }
}