package com.example.firstsite.command;

import com.example.firstsite.command.impl.DefaultCommand;
import com.example.firstsite.command.impl.LoginCommand;
import com.example.firstsite.command.impl.LogoutCommand;
import com.example.firstsite.command.impl.SignUpCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGNUP(new SignUpCommand()),
    DEFAULT(new DefaultCommand());
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        Command command;
        if (commandStr != null) {
            try {
                CommandType commandType = CommandType.valueOf(commandStr.toUpperCase());
                command = commandType.command;
            } catch (IllegalArgumentException e) {
                command = DEFAULT.command;
            }
        } else {
            command = DEFAULT.command;
        }
        return command;
    }
}
