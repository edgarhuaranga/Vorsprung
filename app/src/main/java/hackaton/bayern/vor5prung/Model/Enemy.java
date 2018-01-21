package hackaton.bayern.vor5prung.Model;

/**
 * Created by edhuar on 20.01.18.
 */

public class Enemy {
    String name;
    int logoResource;

    public Enemy(String name, int logoResource) {
        this.name = name;
        this.logoResource = logoResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogoResource() {
        return logoResource;
    }

    public void setLogoResource(int logoResource) {
        this.logoResource = logoResource;
    }
}
