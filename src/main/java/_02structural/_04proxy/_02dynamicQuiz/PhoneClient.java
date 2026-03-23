package _02structural._04proxy._02dynamicQuiz;

public class PhoneClient implements Client,Server {

    /**
     *
     */
    @Override
    public void connect() {
        System.out.println("手机客户端连接中。。。");
    }

    /**
     *
     */
    @Override
    public void communicate() {
        System.out.println("手机客户端通信中。。。");
    }
}
