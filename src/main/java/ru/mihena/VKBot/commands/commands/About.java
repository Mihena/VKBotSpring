package ru.mihena.VKBot.commands.commands;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.model.ImageEntity;
import ru.mihena.VKBot.model.ImageService;
import ru.mihena.VKBot.utils.Answer;
import ru.mihena.VKBot.utils.ImageFromURLsaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.BatchUpdateException;

@Component("about")
@Slf4j
public class About implements Command {

    @Value("${vkbot.version}")
    private String version;
    private final String name;

    @Autowired
    private ImageService imageService;

    public About(@Value("about")String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return "Предоставляет краткую информацию об авторе бота";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "credits",
                "about",
                "об",
                "автор"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.USER;
    }

    @Override
    public Answer execute() {
        if(!version.equalsIgnoreCase("dev")) {
            String string = "Автор бота: @id119652972(Александр Скопинский)\n" +
                    "Сервис погоды: openweathermap.org\n" +
                    "Библиотека для API VK: github.com/yvasyliev/java-vk-bots-long-poll-api\n" +
                    "версия " + version;
            return new Answer(string);
        } else {
            try {
                ImageEntity imageEntity = new ImageEntity(Path.of("wednesday/wednesdaye78b9e4e-9d64-472f-a7e8-db9935112091.gif"));
                imageService.updateImage(imageEntity);
                ImageEntity entity = imageService.findById(1L);
                Answer answer = new Answer("developer build is on running do not use the bot");
                File file = new File("memes/"+imageEntity.getId() + "." + entity.getType());
                Files.write(file.toPath(), imageEntity.getImage());
                answer.getFiles().add(file);
                return answer;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }
        return null;
    }
}
