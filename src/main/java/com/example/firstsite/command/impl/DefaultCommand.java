package com.example.firstsite.command.impl;

import com.example.firstsite.command.Command;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.firstsite.command.constant.PageName.INDEX_PAGE;

public class DefaultCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return INDEX_PAGE;
    }
}
