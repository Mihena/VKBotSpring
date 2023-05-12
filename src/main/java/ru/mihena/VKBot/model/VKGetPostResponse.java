package ru.mihena.VKBot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VKGetPostResponse {
    private Response response;
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Attachment {
        private String type;
        private Photo photo;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Comments {
        private int can_post;
        private int count;
        private boolean groups_can_post;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Donut {
        private boolean is_donut;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private Donut donut;
        private Comments comments;
        private int marked_as_ads;
        private double short_text_rate;
        private String hash;
        private String type;
        private ArrayList<Attachment> attachments;
        private int date;
        private int from_id;
        private int id;
        private Likes likes;
        private int owner_id;
        private PostSource post_source;
        private String post_type;
        private Reposts reposts;
        private String text;
        private Views views;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Likes {
        private int can_like;
        private int count;
        private int user_likes;
        private int can_publish;
        private boolean repost_disabled;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Photo {
        private int album_id;
        private int date;
        private int id;
        private int owner_id;
        private String access_key;
        private int post_id;
        private ArrayList<Size> sizes;
        private String text;
        private int user_id;
        private boolean has_tags;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PostSource {
        private String platform;
        private String type;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Reposts {
        private int count;
        private int user_reposted;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private int count;
        private ArrayList<Item> items;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Size {
        private int height;
        private String type;
        private int width;
        private String url;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Views {
        private int count;
    }
}
