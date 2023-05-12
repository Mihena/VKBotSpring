package ru.mihena.VKBot.commands.commands;

import api.longpoll.bots.model.objects.basic.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.model.UserEntity;
import ru.mihena.VKBot.model.UserService;
import ru.mihena.VKBot.utils.Answer;
import ru.mihena.VKBot.utils.TFAManager;

@Component("ключ")
public class SetAdmin implements Command {

    @Autowired
    private TFAManager manager;
    @Autowired
    private UserService userService;
    @Autowired
    ApplicationContext context;
    private final Logger logger = LoggerFactory.getLogger(SetAdmin.class);
    private final String name;

    public SetAdmin(@Value("ключ")String name) {
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
        return "2FA аутентификация, для получения прав админа";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "ключ",
                "key"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.USER;
    }

    @Override
    public Answer execute() {
        Message message = context.getBean(Message.class);
        if(verify(message.getText().split(" "))) {
            UserEntity user = userService.findById((long)message.getFromId());
            user.setRoleName("admin");
            userService.updateUser(user);
            logger.info("id: "+user.getId()+" " + user.getFirstName()+ " " + user.getLastname() + " the user has received admin rights");
            return new Answer("Access accepted");
        } else return new Answer("Access denied");
    }

    private boolean verify(String[] args) {
        if(args.length<2 || args[1]==null) return false;
        return manager.verifyCode(args[1]);
    }
}
