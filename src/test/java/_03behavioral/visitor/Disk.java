package _03behavioral.visitor;

public class Disk extends Hardware{
    public Disk(String command) {
        super(command);
    }

    @Override
    public void accept(Vistor vistor) {
        vistor.visitDisk(this);
    }
}
