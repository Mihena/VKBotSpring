package ru.mihena.VKBot.utils;

import api.longpoll.bots.model.objects.basic.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.core.ResponseManager;
import ru.mihena.VKBot.model.VKGetPostResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VKAPIParser {

    @Value("${vk.vk_access_token}")
    private String API;
    @Value("${vk.vk_API_version}")
    private String vAPI;
    @Value("${vk.vk_service_token}")
    private String service;
    @Autowired
    private ApplicationContext context;
    private final Logger logger = LoggerFactory.getLogger(VKAPIParser.class);

    public String[] getFullName() throws JSONException {

        String urlAddress = "https://api.vk.com/method/users.get?user_ids="+(context.getBean(Message.class)).getFromId().toString()+"&access_token="+API+"&v="+vAPI;


        JSONObject object = new JSONObject(URLCall(urlAddress));
        String[] name = new String[2];
        name[1] = object.getJSONArray("response")
                .getJSONObject(0)
                .getString("last_name");
        name[0] = object.getJSONArray("response")
                .getJSONObject(0)
                .getString("first_name");
        return name;
    }

    public String getCity() throws JSONException {
        String urlAddress = "https://api.vk.com/method/users.get?user_ids="+context.getBean(Message.class).getFromId().toString()+"&fields=city&access_token="+API+"&v="+vAPI;

        JSONObject object = new JSONObject(URLCall(urlAddress));
        return object.getJSONArray("response").getJSONObject(0).getJSONObject("city").getString("title");

    }

    public Answer getMeme() throws JSONException {

        ObjectMapper objectMapper = new ObjectMapper();
        Answer answer = new Answer(" ");
        String urlAddress;
        double choice = Math.random();

        if(choice>0.5) {
            urlAddress = "https://api.vk.com/method/wall.get?owner_id=-158275105&count=1&offset=" + (int) (1 + Math.random() * 500) + "&access_token=" + service + "&v=" + vAPI; //Лига плохих шуток
        } else {
            urlAddress = "https://api.vk.com/method/wall.get?owner_id=-149279263&count=1&offset=" + (int) (1 + Math.random() * 500) + "&access_token=" + service + "&v=" + vAPI; //Анекдоты категории Б
        }
        //String urlAddress = "https://api.vk.com/method/wall.get?owner_id=-208419426&count=1&offset="+1+"&access_token="+service+"&v="+vAPI; //Для тестов в своём паблике
        try {

            VKGetPostResponse response = objectMapper.readValue(URLCall(urlAddress), VKGetPostResponse.class);

            if(choice>0.5) {
                if(response.getResponse()
                        .getItems()
                        .get(0)
                        .getAttachments()
                        .size()>0) {
                    for(VKGetPostResponse.Attachment attachment : response.getResponse().getItems().get(0).getAttachments()) {
                        int tempWidth = 0;
                        String tempUrl = "";
                        for (VKGetPostResponse.Size size : attachment.getPhoto().getSizes()) {

                            if(size.getWidth() > tempWidth) {
                                tempWidth = size.getWidth();
                                tempUrl = size.getUrl();
                            }
                        }
                        answer.getUrls().add(tempUrl);
                    }
                }
            }

            String text = response.getResponse().getItems().get(0).getText();

            if(hasURL(text)) return getMeme();
            answer.setText(text);
        } catch (JSONException e) {
            logger.error(e.toString());
            context.getBean(ResponseManager.class).forceSendAdmin(e.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    private String URLCall(String urlAddress) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public boolean hasURL(String text) {
        Pattern pattern = Pattern.compile("(https://|vk.me)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}
