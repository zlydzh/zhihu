package com.zly.Util;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class Latest implements Serializable {
    private List<TopStoriesEntity> top_stories;
    private List<StoriesEntity> stories;
    private String date;

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class StoriesEntity implements Serializable {
        private List<String> images;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String toString() {
            return "image: " + images +
                    " type: " + type +
                    " id: " + id +
                    " ga_prefix: "  + ga_prefix +
                    " title: " + title;
        }
    }

    public static class TopStoriesEntity {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String toString() {
            return "image: " + image +
                    " type: " + type +
                    " id: " + id +
                    " ga_prefix: "  + ga_prefix +
                    " title: " + title;
        }
    }
}
