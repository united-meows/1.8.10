package pisi.unitedmeows.meowlib.etc;

public class MeowList<X> {

    private Tail<X> currentTail;

    public MeowList() {
        currentTail = new Tail<>();
    }
    public MeowList(int tailLength) {
        currentTail = new Tail<>(tailLength);
    }

    public void push(X element) {
        currentTail.add(element);
    }
    public void kick(X element) { currentTail.remove(element); }
    public void kick(X element, boolean deepLook) {
        currentTail.remove(element, deepLook);
    }
    public void kick(int index) {
        currentTail.removeAt(index);
    }
}
