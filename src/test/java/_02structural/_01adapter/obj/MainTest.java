package _02structural._01adapter.obj;

import _02structural._01adapter.MoviePlayer;

public class MainTest {
    public static void main(String[] args) {

        // MeiYanDecorator decorator = new MeiYanDecorator(manTikTok);
        JPMoviePlayerAdapter adapter = new JPMoviePlayerAdapter(new MoviePlayer());

        adapter.play();
    }

}
