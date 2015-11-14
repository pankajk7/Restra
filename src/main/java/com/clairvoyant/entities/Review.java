package com.clairvoyant.entities;

import java.io.Serializable;

/**
 * Created by Clairvoyant on 27-10-2015.
 */
public class Review implements Serializable {
    String id;
    String usr_msg;
    String rating;
    String restro_id;

    public class ReviewList{
        public Review[] getReview() {
            return review;
        }

        public void setReview(Review[] review) {
            this.review = review;
        }

        Review[] review;
    }

    public String getRestro_id() {
        return restro_id;
    }

    public void setRestro_id(String restro_id) {
        this.restro_id = restro_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUsr_msg() {
        return usr_msg;
    }

    public void setUsr_msg(String usr_msg) {
        this.usr_msg = usr_msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
