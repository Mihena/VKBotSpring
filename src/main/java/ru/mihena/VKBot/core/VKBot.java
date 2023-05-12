package ru.mihena.VKBot.core;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.basic.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Commander;

@Component
@Slf4j
public class VKBot extends LongPollBot {

    private long id;
    private Message message;
    private final String token;
    @Autowired
    private ResponseManager responseManager;
    @Autowired
    @Lazy
    private Commander commander;
    @Value("${vk.vk_admin_id}")
    private int adminId;

    public VKBot(@Value("${vk.vk_access_token}") String token) {
        this.token = token;}

    @Override
    public void onMessageNew(MessageNew messageNewEvent) {
        Message message = messageNewEvent.getMessage();
        setMessage(message);
        setId(message.getFromId());

        if (message.hasText())
            if (message.getText().equalsIgnoreCase("пидор"))
                responseManager.send("Пошёл нахуй");
            else if (message.getText().equalsIgnoreCase("test"))
                responseManager.forceSendAdmin("", message);
            else execute();
    }

    @Override
    public String getAccessToken() {
        return this.token;
    }

    @Bean("message")
    @Lazy
    @Scope("prototype")
    public Message getMessage() {
        return this.message;
    }

    @Bean
    public VkBotsMethods getApi() {
        return vk;
    }

    @Bean("id")
    @Scope("prototype")
    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    private void execute() {
        responseManager.send(commander.execute());
    }
}
