package ru.mihena.VKBot.commands.commands;

import api.longpoll.bots.model.objects.basic.Message;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.commands.core.Command;
import ru.mihena.VKBot.commands.core.RoleName;
import ru.mihena.VKBot.utils.Answer;
import ru.mihena.VKBot.utils.OWMap;
import ru.mihena.VKBot.utils.VKAPIParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@Component("время")
public class Time implements Command {
        @Autowired
        private OWMap owMap;
        @Autowired
        private VKAPIParser vkApiParser;
        @Autowired
        private ApplicationContext context;
        private final String name;

        public Time(@Value("время")String name) {
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
                return "Возвращает время в текущем городе, пример использования: Время Москва, Время Торонто";
        }

        @Override
        public String[] getAliases() {
                return new String[]{
                        "время",
                        "time"
                };
        }

        @Override
        public RoleName getAccessLevel() {
                return RoleName.USER;
        }

        @Override
        public Answer execute() {
                Message message = context.getBean(Message.class);
                ArrayList<String> args = new ArrayList<>(Arrays.stream(message.getText().split(" ")).toList());
                String responseMessage = "";

                if (args.size() < 2) {
                        try {
                                args.add(1,vkApiParser.getCity());
                        } catch (JSONException e) {
                                e.printStackTrace();
                                args.add(1,"Орёл");
                        }
                }
                try {
                        StringBuilder temp = new StringBuilder();
                        temp.append(args.get(1).split("")[0].toUpperCase(Locale.ROOT)).append(args.get(1).substring(1)).append(" ");
                        temp.deleteCharAt(temp.length() - 1);
                        responseMessage = owMap.getTime(temp.toString());
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return new Answer(responseMessage);
        }
}
