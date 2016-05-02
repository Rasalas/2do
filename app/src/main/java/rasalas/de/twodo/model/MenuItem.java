package rasalas.de.twodo.model;

import java.util.ArrayList;

import rasalas.de.twodo.R;

/**
 * Created by Rasalas on 02.05.2016.
 */
public class MenuItem {
    Integer image;
    String primText;
    String secText;

    public MenuItem(Integer image, String primText, String secText) {
        this.image = image;
        this.primText = primText;
        this.secText = secText;
    }

    public static ArrayList<MenuItem> createMenuList() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem(R.drawable.ic_filter_list, "Test", "TestTestTest"));
        menuItems.add(new MenuItem(R.drawable.ic_event_black_24dp, "Test2", "TestiTestiTesti"));

        return menuItems;
    }

    public Integer getImageId() {
        return image;
    }

    public String getPrimText() {
        return primText;
    }

    public String getSecText() {
        return secText;
    }
}
