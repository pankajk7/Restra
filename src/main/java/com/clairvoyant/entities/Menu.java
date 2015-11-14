package com.clairvoyant.entities;

import java.io.Serializable;

/**
 * Created by Clairvoyant on 27-10-2015.
 */
public class Menu implements Serializable {
    String id;
    String restro_id;
    String pic_id;

    public class MenuList{
        public Menu[] getMenu() {
            return menu;
        }

        public void setMenu(Menu[] menu) {
            this.menu = menu;
        }

        Menu[] menu;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestro_id() {
        return restro_id;
    }

    public void setRestro_id(String restro_id) {
        this.restro_id = restro_id;
    }

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }
}
