package _02structural._04proxy._02dynamicQuiz;

public class MainTest {
    public static void main(String[] args) {
        CLient cLient = new PhoneCLient();
        CLient proxy = ClientProxy.connect(cLient);

        proxy.connect();
    }

}
