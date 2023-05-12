package ru.mihena.VKBot.commands.commands;

import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.methods.impl.messages.GetByConversationMessageId;
import api.longpoll.bots.model.objects.additional.PhotoSize;
import api.longpoll.bots.model.objects.basic.Message;
import api.longpoll.bots.model.objects.media.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.model.ImageEntity;
import ru.mihena.VKBot.utils.Answer;
import ru.mihena.VKBot.utils.ImageFromURLsaver;
import java.util.ArrayList;
import java.util.List;

@Component("saveImage")
@Slf4j
public class SaveImage implements Command {

    @Value("${vkbot.version}")
    private String version;
    @Autowired
    private ImageFromURLsaver imageFromURLsaver;
    @Autowired
    ApplicationContext context;
    private final String name;

    public SaveImage(@Value("saveImage")String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Сохраняет присланные изображения в папку wednesday";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "save",
                "сохр"
        };
    }

    @Override
    public RoleName getAccessLevel() {
        return RoleName.ADMIN;
    }

    @Override
    public String toString() {
        return this.name;
    }
    @Override
    public Answer execute() {
        Message message = context.getBean(Message.class);
        VkBotsMethods api = context.getBean(VkBotsMethods.class);
        try {
            List<Attachment> list;
            if (message.isCropped()) {
                GetByConversationMessageId.ResponseBody response = api
                        .messages
                        .getByConversationMessageId()
                        .setPeerId(message.getPeerId())
                        .setConversationMessageIds(message.getConversationMessageId())
                        .execute();
                list = response.getResponse().getItems().get(0).getAttachments();
            } else {
                list = message.getAttachments();
            }

//            imageFromURLsaver.saveAttachment(gifs, "wednesday", "gif");
//            imageFromURLsaver.saveAttachment(photos, "wednesday", "jpg");
            return new Answer("saved for wednesday");
        } catch (VkApiException e) {
            log.error(e.toString());
            return null;
        }
    }
}
