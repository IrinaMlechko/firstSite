package com.example.firstsite.controller;

import com.example.firstsite.command.Command;
import com.example.firstsite.command.CommandType;
import com.example.firstsite.exception.CommandException;
import com.example.firstsite.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.example.firstsite.command.constant.PageName.SERVER_ERROR_PAGE;
import static com.example.firstsite.command.constant.RequestAttributeName.ERROR_MSG;
import static com.example.firstsite.command.constant.RequestParameterName.COMMAND;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    static Logger logger = LogManager.getLogger();

    public void init() {
        ConnectionPool.getInstance();
        logger.info("Init servlet" + this.getServletInfo());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter(COMMAND);
        Command command = CommandType.define(commandStr);
        String page;
        try {
            page = command.execute(request);
            request.getRequestDispatcher(page).forward(request, response);
        } catch (CommandException e) {
            request.setAttribute(ERROR_MSG, e.getLocalizedMessage());
            request.getRequestDispatcher(SERVER_ERROR_PAGE).forward(request, response);
        }
    }

    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        logger.info("Servlet destroyed" + this.getServletName());
    }
}