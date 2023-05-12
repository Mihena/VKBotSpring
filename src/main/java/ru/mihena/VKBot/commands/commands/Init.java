package ru.mihena.VKBot.commands.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.core.ScheduleEngine;
import ru.mihena.VKBot.utils.Answer;


@Component("init")
public class Init implements Command {

    @Value("${vkbot.version}")
    private String version;
    @Autowired
    private ScheduleEngine scheduleEngine;
    private final String name;

    public Init(@Value("about")String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Реинициализирует листы";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"init"};
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.ADMIN;
    }

    @Override
    public Answer execute() {
        scheduleEngine.initList();
        return new Answer("Image lists are reinitialized");
    }
}
