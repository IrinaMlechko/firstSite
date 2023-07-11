package com.example.firstsite.command.impl;

import com.example.firstsite.command.Command;
import com.example.firstsite.entity.Credentials;
import com.example.firstsite.entity.User;
import com.example.firstsite.exception.CommandException;
import com.example.firstsite.exception.ServiceException;
import com.example.firstsite.service.UserService;
import com.example.firstsite.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.example.firstsite.util.Message.LOGIN_ALREADY_EXISTS_MESSAGE;
import static com.example.firstsite.util.PageName.MAIN_PAGE;
import static com.example.firstsite.util.PageName.REGISTRATION_PAGE;
import static com.example.firstsite.util.RequestAttributeName.FAILED;
import static com.example.firstsite.util.RequestAttributeName.USER;
import static com.example.firstsite.util.RequestParameterName.*;

public class SignUpCommand implements Command {

    static Logger logger = LogManager.getLogger();

    UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String dateOfBirthStr = request.getParameter(DATE_OF_BIRTH);
        String dateFormat = "dd-MM-yyyy";
        UserService userService = UserServiceImpl.getInstance();
        String page;
        try {
            if (!userService.existsByLogin(login)) {
                LocalDate dateOfBirth;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                    dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter);
                } catch (DateTimeParseException e) {
                    throw new CommandException("Error parsing date of birth", e);
                }
                User user = new User(0, firstName, lastName, dateOfBirth);
                int userId = userService.createUser(user);

                Credentials credentials = new Credentials(0, login, password, userId);
                userService.createCredentials(credentials);
                request.setAttribute(USER, firstName);
                page = MAIN_PAGE;
            } else {
                // Пользователь с таким логином уже существует
                request.setAttribute(FAILED, LOGIN_ALREADY_EXISTS_MESSAGE);
                page = REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            logger.error("Error by creating " + e);
            throw new CommandException(e);
        }
        return page;
    }
}
