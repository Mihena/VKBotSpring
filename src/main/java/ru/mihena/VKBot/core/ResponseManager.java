package ru.mihena.VKBot.core;

import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.methods.impl.messages.Send;
import api.longpoll.bots.model.objects.basic.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.utils.Answer;
import ru.mihena.VKBot.utils.ImageFromURLsaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Component
@Slf4j
public class ResponseManager {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ImageFromURLsaver imageFromURLsaver;
    @Value("${vk.vk_access_token}")
    private String token;
    @Value("${vk.vk_admin_id}")
    private int adminId;

    public void send(Answer messageToSend) {
        if (messageToSend != null) {
            try {
                int peerId = context.getBean(Message.class).getPeerId();
                Send send = context.getBean(VkBotsMethods.class)
                        .messages
                        .send()
                        .setRandomId((int) (Math.random() * 100000))
                        .setPeerId(peerId)
                        .setMessage(messageToSend.getText() + "");

                if (messageToSend.hasUrls()) {
                    ArrayList<File> files = imageFromURLsaver.saveAttachment(messageToSend.getUrls(), "memes", "jpg");
                    for (File f : files) send.addPhoto(f);
                }
                if (messageToSend.hasFiles()) {
                    for(File f : messageToSend.getFiles()) send.addDoc(f);
                }
                send.execute();
                imageFromURLsaver.clearFolder("memes");
            } catch (VkApiException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void send(String messageToSend) {
        try {
            int peerId = context.getBean(Message.class).getPeerId();
            context.getBean(VkBotsMethods.class)
                    .messages
                    .send()
                    .setRandomId((int)(Math.random()*100000))
                    .setPeerId(peerId)
                    .setMessage(messageToSend)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    public void forceSendById(String messageToSend, int id) {
        try {
            context.getBean(VkBotsMethods.class)
                    .messages
                    .send()
                    .setRandomId((int)(Math.random()*100000))
                    .setPeerId(id)
                    .setMessage(messageToSend)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    public void forceSendById(String messageToSend,File file, int id) {
        try {
            context.getBean(VkBotsMethods.class)
                    .messages
                    .send()
                    .setRandomId((int)(Math.random()*100000))
                    .setPeerId(id)
                    .setMessage(messageToSend)
                    .addPhoto(file)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    public void forceSendById(String messageToSend,File file) {
        try {
            context.getBean(VkBotsMethods.class)
                    .messages
                    .send()
                    .setRandomId((int)(Math.random()*100000))
                    .setPeerId(context.getBean(Message.class).getPeerId())
                    .setMessage(messageToSend)
                    .addPhoto(file)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }


    public void postInPublicWall(int publicId, File file) {
        //TODO make post int public later
    }

    public void forceSendAdmin(String messageToSend, Message message) {
        try {
            context.getBean(VkBotsMethods.class)
                    .messages
                    .send()
                    .setRandomId((int)(Math.random()*100000))
                    .setPeerId(adminId)
                    .setMessage(messageToSend)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    public void forceSendAdmin(String messageToSend) {
        try {
            int peerId = adminId; //ADMIN VK ID
            context.getBean(VkBotsMethods.class)
                    .messages
                    .send()
                    .setRandomId((int)(Math.random()*100000))
                    .setPeerId(peerId)
                    .setMessage(messageToSend)
                    .execute();
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }
}
