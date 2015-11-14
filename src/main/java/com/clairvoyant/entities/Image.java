package com.clairvoyant.entities;

import java.io.Serializable;

/**
 * Created by Clairvoyant on 27-10-2015.
 */
public class Image implements Serializable {
    String id;
    String image_id;
    String pic_ref_id;

     public class ImageList{
         public Image[] getImage() {
             return image;
         }

         public void setImage(Image[] image) {
             this.image = image;
         }

         Image[] image;
     }
    public String getPic_ref_id() {
        return pic_ref_id;
    }

    public void setPic_ref_id(String pic_ref_id) {
        this.pic_ref_id = pic_ref_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
