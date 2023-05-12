package ru.mihena.VKBot.commands.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.core.ResponseManager;
import ru.mihena.VKBot.core.ScheduleEngine;
import ru.mihena.VKBot.model.ImageEntity;
import ru.mihena.VKBot.model.ImageService;
import ru.mihena.VKBot.utils.Answer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("wednesday")
@Slf4j
public class Wednesday implements Command {
    @Autowired
    private ScheduleEngine scheduleEngine;
    private final String name;
    @Autowired
    private ResponseManager responseManager;

    @Autowired
    private ImageService imageService;

    public Wednesday(@Value("wednesday") String name) {
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
        return "It is Wednesday. mY dudes";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "wednesday",
                "Среда",
                "лягушка",
                "жаба",
                "dudes"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.USER;
    }

    @Override
    public Answer execute() {
        if (scheduleEngine.getList() == null) scheduleEngine.initList();
        responseManager.forceSendById("It is Wednesday. mY dudes", new File(String.valueOf(scheduleEngine.getList().get((int) (1 + Math.random() * scheduleEngine.getList().size() - 1)))));
        return new Answer("test message");
    }
}
