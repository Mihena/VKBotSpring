package ru.mihena.VKBot.utils;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

@Component
public class OWMap {

    @Value("${vk.OW_access_token}")
    String API;

    public String getWeather(String cityName) throws JSONException {
        StringBuilder builder = new StringBuilder();
        String urlAddress = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API + "&units=metric";
        String output = getUrlContent(urlAddress);

        if(!output.equals("err1")) {
            if (!output.isEmpty()) { // Нет ошибки и такой город есть

                SimpleDateFormat date2 = new SimpleDateFormat("dd.MM.yyyy");
                JSONObject obj = new JSONObject(output); // Обрабатываем JSON и устанавливаем данные в текстовые надписи

                //Выборка состояния погоды
                String weather = obj.getJSONArray("weather").getJSONObject(0).getString("main");
                if(weather.equals("Rain")) weather = "\uD83C\uDF27 Дождь \uD83C\uDF27";
                if(weather.equals("Clouds")) weather = "☁ Облачно ☁";
                if(weather.equals("Clear")) weather = "☀ Ясно ☀";
                if(weather.equals("Snow")) weather = "\uD83C\uDF28 Снег \uD83C\uDF28";

                //Сборка вывода
                builder.append("\uD83D\uDCC5 Погода в городе ").append(cityName.substring(0, 1).toUpperCase()).append(cityName.substring(1)).append(" на ").append(date2.format(System.currentTimeMillis())).append(" \uD83D\uDCC5\n");
                builder.append("\n");
                builder.append(weather).append("\n");
                builder.append("Температура: ").append(Math.round(obj.getJSONObject("main").getDouble("temp"))).append(" °С\n");
                builder.append("Ощущается: ").append(Math.round(obj.getJSONObject("main").getDouble("feels_like"))).append(" °С\n");
                builder.append("Максимум: ").append(Math.round(obj.getJSONObject("main").getDouble("temp_max"))).append(" °С\n");
                builder.append("Минимум: ").append(Math.round(obj.getJSONObject("main").getDouble("temp_min"))).append(" °С\n");
                builder.append("Давление: ").append(Math.round(obj.getJSONObject("main").getDouble("pressure")*0.75006156)).append(" мм рт. ст.\n");
            }
            return builder.toString();
        } else {
            return "Город не найден";
        }
    }

        private static String getUrlContent (String urlAddress) {
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
                return "err1";
            }

            return content.toString();
        }

        public String getTime(String cityName) throws IOException {
            SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
            long UTC = System.currentTimeMillis()-10800000;
            StringBuilder builder = new StringBuilder();
            String[] temp = cityName.split("-");
            StringBuilder city = new StringBuilder();
            for(int i = 0;i<temp.length;i++) {
                temp[i] = temp[i].substring(0,1).toUpperCase() + temp[i].substring(1);
                //System.out.println(temp[i]);
                city.append(temp[i]).append("-");
            }
            cityName = city.substring(0,city.length()-1);
            String urlAddress = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API + "&units=metric";
            String output = getUrlContent(urlAddress);
            if (!output.equals("err1")) {
                if (!output.isEmpty()) { // Нет ошибки и такой город есть
                    JSONObject obj = null; // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                    try {
                        obj = new JSONObject(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        builder.append("Время в городе ").append(cityName).append(" ").append(date.format(UTC + (obj.getInt("timezone") * 1000L)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                return builder.toString();
            } else {
                return "Город не найден";
            }
        }
}
