package ru.mihena.VKBot.commands.commands;

import api.longpoll.bots.model.objects.basic.Message;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.model.ImageService;
import ru.mihena.VKBot.utils.Answer;

import java.text.SimpleDateFormat;

@Component("дата")
public class Image implements Command {

    private final String name;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ApplicationContext context;

    public Image(@Value("изображение")String name) {
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
        return "Выдаёт случайную или нумерованную сохранённую пикчу, пример пикча, пикча 2, пикча 8";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "пикча",
                "изображение"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.USER;
    }

    @Override
    public Answer execute() {
        
        if(args.length>1) {
            imageService.findById(Long.parseLong(args[1]));
        } else  {
           imageService.findById(Math.random());
        }
    }
}
