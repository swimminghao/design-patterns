package _02structural._01adapter.clazz;

import _02structural._01adapter.MoviePlayer;

public class MainTest {

    public static void main(String[] args) {

        MoviePlayer player = new MoviePlayer();
        JPMoviePlayerAdapter adapter = new JPMoviePlayerAdapter(player);


        adapter.play();
//        player.play();

    }
}
