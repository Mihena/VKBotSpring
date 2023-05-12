package ru.mihena.VKBot.commands.commands;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.utils.Answer;
import ru.mihena.VKBot.utils.ImageFromURLsaver;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.core.ResponseManager;
import ru.mihena.VKBot.utils.VKAPIParser;
import java.io.IOException;

@Component("мем")
public class Meme implements Command {

    @Autowired
    private VKAPIParser vkapiParser;
    private final Logger logger = LoggerFactory.getLogger(Meme.class);
    private final String name;

    public Meme(@Value("мем")String name) {
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
        return "Прислает мем из паблика \"Лига плохих шуток\" или \"Анекдотык атегории Б+\"";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "мем",
                "меме",
                "mem",
                "meme",
                "лпш"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.USER;
    }

    @Override
    public Answer execute() {
        try {
            Answer answer = vkapiParser.getMeme();
            if(Math.random()>0.5) answer.setText("Держи мем\n\n" + answer.getText());
            else answer.setText("Опа, мемчик\n\n" + answer.getText());
            return answer;
        } catch (ArrayIndexOutOfBoundsException | JSONException e) {
            logger.error(e.toString());
            return new Answer("Ошибка сервера");
        }
    }
}
