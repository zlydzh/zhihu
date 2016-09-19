package com.zly.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class Latest {
    private LinkedList<StoriesEntity> mStoriesLinkedList;
    private ArrayList<TopStoriesEntity> mTopStoriesArrayList;

    public LinkedList<StoriesEntity> getStoriesLinkedList() {
        return mStoriesLinkedList;
    }

    public void setStoriesLinkedList(LinkedList<StoriesEntity> mStoriesLinkedList) {
        this.mStoriesLinkedList = mStoriesLinkedList;
    }

    public ArrayList<TopStoriesEntity> getTopStoriesArrayList() {
        return mTopStoriesArrayList;
    }

    public void setTopStoriesArrayList(ArrayList<TopStoriesEntity> mTopStoriesArrayList) {
        this.mTopStoriesArrayList = mTopStoriesArrayList;
    }

    public static class StoriesEntity {
        private List<String> mImage;
        private String mType;
        private String mId;
        private String mGa_prefix;
        private String mTitle;

        public List<String> getmImage() {
            return mImage;
        }

        public void setmImage(List<String> mImage) {
            this.mImage = mImage;
        }

        public String getmType() {
            return mType;
        }

        public void setmType(String mType) {
            this.mType = mType;
        }

        public String getmId() {
            return mId;
        }

        public void setmId(String mId) {
            this.mId = mId;
        }

        public String getmGa_prefix() {
            return mGa_prefix;
        }

        public void setmGa_prefix(String mGa_prefix) {
            this.mGa_prefix = mGa_prefix;
        }

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String toString() {
            return "image: " + mImage +
                    " type: " + mType +
                    " id: " + mId +
                    " ga_prefix: "  + mGa_prefix +
                    " title: " + mTitle;
        }
    }

    public static class TopStoriesEntity {
        private String mImage;
        private String mType;
        private String mId;
        private String mGa_prefix;
        private String mTitle;

        public String getmImage() {
            return mImage;
        }

        public void setmImage(String mImage) {
            this.mImage = mImage;
        }

        public String getmType() {
            return mType;
        }

        public void setmType(String mType) {
            this.mType = mType;
        }

        public String getmId() {
            return mId;
        }

        public void setmId(String mId) {
            this.mId = mId;
        }

        public String getmGa_prefix() {
            return mGa_prefix;
        }

        public void setmGa_prefix(String mGa_prefix) {
            this.mGa_prefix = mGa_prefix;
        }

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String toString() {
            return "image: " + mImage +
                    " type: " + mType +
                    " id: " + mId +
                    " ga_prefix: "  + mGa_prefix +
                    " title: " + mTitle;
        }
    }
}
