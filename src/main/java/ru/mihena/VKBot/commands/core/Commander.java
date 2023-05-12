package ru.mihena.VKBot.commands.core;

import api.longpoll.bots.model.objects.basic.Message;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.Exceptions.AccessDeniedException;
import ru.mihena.VKBot.model.UserEntity;
import ru.mihena.VKBot.model.UserService;
import ru.mihena.VKBot.utils.Answer;
import ru.mihena.VKBot.utils.VKAPIParser;

@Component
public class Commander {

    @Autowired
    private UserService userService;
    @Autowired
    private CommandManager commandManager;
    @Autowired
    @Lazy
    private Message message;
    @Autowired
    private VKAPIParser vkapiParser;
    private final Logger logger = LoggerFactory.getLogger(Commander.class);
    @Autowired
    @Lazy
    private UserEntity user;

    public Answer execute() {
        if(commandManager.hasCommand()) {
            checkUser();
            try {
                if (commandManager.hasAccess()) {
                    return commandManager.getCommandByAlias().execute();
                } else {
                    throw new AccessDeniedException("У пользователя нет доступа к этой команде");
                }
            } catch (AccessDeniedException e) {
                logger.info(message.getId() + " недостаточно прав для выполнения " + message.getText());
                return new Answer("У вас нет доступа к этой команде");
            } catch (NullPointerException ignored) {
                return null;
            }
        }
        return null;
    }

    private void checkUser() {
//        getPeerId()); //ПОЛЬЗОВАТЕЛЬ ИЛИ ID БЕСЕДЫ
//        getFromId()); //ВСЕГДА ПОЛЬЗОВАТЕЛЬ
        if(!userService.hasUserById((long)(message.getFromId()))) {
            UserEntity user;
            String[] fullName;
            try {
                fullName = vkapiParser.getFullName();
                user = new UserEntity((long) message.getFromId(), "user", fullName[0], fullName[1]);
                userService.updateUser(user);
            } catch (JSONException e) {
                System.exit(-1);
            }
        }

    }
}