package com.example.firstsite.command.impl;

import com.example.firstsite.command.Command;
import com.example.firstsite.exception.CommandException;
import com.example.firstsite.exception.ServiceException;
import com.example.firstsite.service.UserService;
import com.example.firstsite.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.firstsite.util.Message.LOGIN_FAILED_MESSAGE;
import static com.example.firstsite.util.PageName.INDEX_PAGE;
import static com.example.firstsite.util.PageName.MAIN_PAGE;
import static com.example.firstsite.util.RequestAttributeName.FAILED;
import static com.example.firstsite.util.RequestAttributeName.USER;
import static com.example.firstsite.util.RequestParameterName.LOGIN;
import static com.example.firstsite.util.RequestParameterName.PASSWORD;

public class LoginCommand implements Command {
    UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserService userService = UserServiceImpl.getInstance();
        String page;
        try{
        if (userService.authenticate(login, password)) {
            request.setAttribute(USER, login);
            page = MAIN_PAGE;
        } else {
            request.setAttribute(FAILED, LOGIN_FAILED_MESSAGE);
            page = INDEX_PAGE;
        }} catch(ServiceException e){
            throw new CommandException(e);
        }
        return page;
    }
}