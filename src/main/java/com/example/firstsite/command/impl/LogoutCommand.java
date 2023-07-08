package com.example.firstsite.command.impl;

import com.example.firstsite.command.Command;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.firstsite.util.PageName.INDEX_PAGE;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return INDEX_PAGE;
    }
}
