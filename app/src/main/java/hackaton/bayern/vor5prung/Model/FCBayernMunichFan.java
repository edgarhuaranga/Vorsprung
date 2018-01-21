package hackaton.bayern.vor5prung.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by edhuar on 21.01.18.
 */

public class FCBayernMunichFan {
    String name;
    String phoneNumber;
    LatLng position;
    String photoURL;

    public FCBayernMunichFan(String name, String phoneNumber, LatLng position, String photoURL) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
