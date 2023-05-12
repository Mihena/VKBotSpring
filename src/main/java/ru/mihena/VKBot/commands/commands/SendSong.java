package ru.mihena.VKBot.commands.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.utils.Answer;

@Component("песня")
public class SendSong implements Command {

    private final String name;

    public SendSong(@Value("песня")String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Временная сервисная команда";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "песня",
                "трек"
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
        return new Answer("(https://vk.com/audio119652972_456239401_4a0d6f884ff8a1e98f)kamome sano - </emotional>");
    }
}
