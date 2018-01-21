package hackaton.bayern.vor5prung.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import hackaton.bayern.vor5prung.R;

/**
 * Created by edhuar on 20.01.18.
 */

public class Utils {

    public static ArrayList<Enemy> getUpcomingGames(){
        ArrayList<Enemy> upcomingGames = new ArrayList<>();
        upcomingGames.add(new Enemy("Werder Bremen", R.drawable.bremen));
        upcomingGames.add(new Enemy("Hoffenheim", R.drawable.hoffenheim));
        upcomingGames.add(new Enemy("Friburgo", R.drawable.friburgo));
        upcomingGames.add(new Enemy("RB Leipzig", R.drawable.leipzig));
        upcomingGames.add(new Enemy("Hamburg", R.drawable.hamburg));
        upcomingGames.add(new Enemy("Borussia Dormunt", R.drawable.bremen));
        upcomingGames.add(new Enemy("Frankfurt", R.drawable.frankfurt));
        upcomingGames.add(new Enemy("Hannover", R.drawable.hannover));

        return upcomingGames;
    }

    public static ArrayList<FCBayernMunichFan> bayernMunichFans(){
        ArrayList<FCBayernMunichFan> fans = new ArrayList<>();
        fans.add(new FCBayernMunichFan("Edgar Huaranga","+49123786141324", new LatLng(48.2267985,11.6771379), "https://scontent-frt3-2.xx.fbcdn.net/v/t1.0-9/15941485_1348098251878072_5058844172150348009_n.jpg?oh=b1f8d4743838da7893fa78a3e18ebf14&oe=5AEBD653"));
        fans.add(new FCBayernMunichFan("Name1","+491141324", new LatLng(48.2267965,11.6721379), "https://scontent-frt3-2.xx.fbcdn.net/v/t1.0-9/15941485_1348098251878072_5058844172150348009_n.jpg?oh=b1f8d4743838da7893fa78a3e18ebf14&oe=5AEBD653"));
        fans.add(new FCBayernMunichFan("Name2","+491141324", new LatLng(48.2267985,11.6776379), "https://scontent-frt3-2.xx.fbcdn.net/v/t1.0-9/15941485_1348098251878072_5058844172150348009_n.jpg?oh=b1f8d4743838da7893fa78a3e18ebf14&oe=5AEBD653"));
        fans.add(new FCBayernMunichFan("Name3","+491231324", new LatLng(48.2267985,11.6771379), "https://scontent-frt3-2.xx.fbcdn.net/v/t1.0-9/15941485_1348098251878072_5058844172150348009_n.jpg?oh=b1f8d4743838da7893fa78a3e18ebf14&oe=5AEBD653"));
        fans.add(new FCBayernMunichFan("Madalin Cosma","+40726229151", new LatLng(48.1382694,11.4975091), "https://www.facebook.com/photo.php?fbid=2034148616869457&l=2c059b549c"));
        return fans;
    }
}
