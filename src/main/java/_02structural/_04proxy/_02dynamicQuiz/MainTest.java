package _02structural._04proxy._02dynamicQuiz;

public class MainTest {
    public static void main(String[] args) {
        Client client = new PhoneClient();
        Client proxy = ClientProxy.connect(client);
        proxy.connect();

        ((Server)proxy).communicate();
    }
}
