package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aivlev on 1/26/16.
 */
public class Menu {

    private String title;
    private String link;
    private List<Menu> subMenu;

    public Menu() {
    }

    public Menu(String title, String link) {
        this(title, link, new ArrayList<>());
    }

    public Menu(String title, String link, List<Menu> subMenu) {
        this.title = title;
        this.link = link;
        this.subMenu = subMenu;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
    }
}
