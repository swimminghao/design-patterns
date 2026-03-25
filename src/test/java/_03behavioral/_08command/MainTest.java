package _03behavioral._08command;

public class MainTest {

    public static void main(String[] args) {


//        LeiReceiver leiReceiver = new LeiReceiver();
//        leiReceiver.travel();

        TeacherTongInvoker invoker = new TeacherTongInvoker();
        invoker.setCommand(new OnlineCommand());

        invoker.call();
    }
}
