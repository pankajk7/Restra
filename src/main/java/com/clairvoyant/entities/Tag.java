package com.clairvoyant.entities;

import java.io.Serializable;

public class Tag implements Serializable{
    String id;
    Tags[] tags;

    public class Tags{
        String name;
        int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class TagList{
        Tag[] tag;

        public Tag[] getTag() {
            return tag;
        }

        public void setTag(Tag[] tag) {
            this.tag = tag;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Tags[] getTags() {
        return tags;
    }

    public void setTags(Tags[] tags) {
        this.tags = tags;
    }
}
