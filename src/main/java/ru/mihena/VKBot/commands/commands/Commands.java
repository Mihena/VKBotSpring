package ru.mihena.VKBot.commands.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.*;
import ru.mihena.VKBot.utils.Answer;

import java.util.Arrays;

@Component("команды")
public class Commands implements Command{

    @Autowired
    private CommandManager commandManager;
    private final String name;

    public Commands(@Value("команды")String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Выводит список команд";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "commands",
                "команды",
                "начать",
                "begin",
                "cmd"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.USER;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Answer execute() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Вот что я умею\n\n");

        for(Command command : commandManager.getCommands()) {
            if(commandManager.hasAccess()) {
                try {
                    stringBuilder.append(command.toString())
                            .append(": ")
                            .append(command.getDescription())
                            .append("\n")
                            .append("Варианты использования: ")
                            .append(Arrays.toString(command.getAliases()))
                            .append("\n");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            stringBuilder.append("\n");
        }
        return new Answer(stringBuilder.toString());
    }
}
