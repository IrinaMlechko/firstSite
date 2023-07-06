package com.example.firstsite.command;

import com.example.firstsite.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    String execute (HttpServletRequest request) throws CommandException;
}
