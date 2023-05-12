package ru.mihena.VKBot.commands.core;

import api.longpoll.bots.model.objects.basic.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.model.UserService;

import java.util.List;

@Component
public class CommandManager {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationContext context;
    @Lazy
    @Autowired
    private List<Command> commands;

    public List<Command> getCommands() {
        return commands;
    }

    public boolean hasAccess() {
        Command command = getCommandByAlias();
        if(userService.findById((long)context.getBean(Message.class).getFromId()).getRole().equals(RoleName.ADMIN)) return true;
        else return !command.getAccessLevel().equals(RoleName.ADMIN);
    }

    public Command getCommandByAlias() throws NullPointerException {
        String commandName = context.getBean(Message.class).getText().split(" ")[0];
        for(Command command: commands) {
            for (String alias: command.getAliases()) {
                if(commandName.equalsIgnoreCase(alias)) {
                    return command;
                }
            }
        }
        throw new NullPointerException("Can`t find a command");
    }

    public boolean hasCommand() {
        String command = context.getBean(Message.class).getText().split(" ")[0];
        for (Command command1 : commands) {
            for (String alias: command1.getAliases()) {
                if(alias.equalsIgnoreCase(command)) return true;
            }
        }
        return false;
    }
}
