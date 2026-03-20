package _02structural._04proxy._02dynamicQuiz;

import javax.sound.midi.Soundbank;

public class PhoneCLient implements CLient {
    /**
     *
     */
    @Override
    public void connect() {
        System.out.println("手机连接终端。。。。。");
    }
}
