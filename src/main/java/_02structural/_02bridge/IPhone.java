package _02structural._02bridge;

public class IPhone  extends AbstractPhone{

    @Override
    String getPhone() {
        return "IPhone："+sale.getSaleInfo();
    }
}
