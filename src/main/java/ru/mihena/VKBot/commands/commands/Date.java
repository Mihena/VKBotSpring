package ru.mihena.VKBot.commands.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.utils.Answer;

import java.text.SimpleDateFormat;

@Component("дата")
public class Date implements Command {

    private final String name;

    public Date(@Value("дата")String name) {
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
        return "Выводит текущую дату";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "data",
                "дата",
                "число"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.USER;
    }

    @Override
    public Answer execute() {
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        String responseText = "Дата сервера " + date.format(System.currentTimeMillis());
        return new Answer(responseText);
    }
}
