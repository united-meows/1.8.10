package pisi.unitedmeows.meowlib.etc;

public class Tail<X> {

    private Tail<X> down;

    private X[] elements;
    private int length;
    private final static int DEEP_LOOK_SIZE = 250;

    public Tail() {
        elements = (X[]) new Object[length = 5];
    }
    public Tail(int length) {
        elements = (X[]) new Object[this.length = length];
    }

    public X[] getElements() {
        return elements;
    }

    public int getLength() {
        return length;
    }

    public void setDown(Tail<X> down) {
        this.down = down;
    }

    public Tail<X> getDown() {
        return down;
    }

    public void add(X element) {
        if (elements.length > length) {
            /* Throw an exception */
            //TODO: Exception
        }

        Tail<X> lookFreePlace = this;
        while (lookFreePlace.down != null) {
            lookFreePlace = lookFreePlace.down;
        }
        boolean needNewChild = lookFreePlace.elements[lookFreePlace.elements.length-1] != null;
        if (needNewChild) {
            down = new Tail<>(length);
            down.addElement(element,down.elements);
        } else {
            addElement(element,lookFreePlace.elements);
        }
    }
    private void addElement(X element, X[] elements) {
        int i = 0;
        while (elements[i] != null) {
            i++;
        }
        elements[i] = element;
    }
    public void remove(X element) {
        remove(element,false);
    }
    public void remove(X element, boolean lookForChildren) {
        boolean found = false;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == element) {
                found = true;
                removeAt(i);
                break;
            }
        }
        if (lookForChildren && !found) {
            int deepLooking = 0;
            Tail<X> children = down;
            while (children != null) {
                for (int i = 0; i < children.elements.length; i++) {
                    if (children.elements[i] == element) {
                        found = true;
                        removeAt(i);
                        break;
                    }
                }
                if (found) {
                    break;
                } else {
                    children = children.down;
                    deepLooking++;
                    if (deepLooking > DEEP_LOOK_SIZE) {
                        /* Throw out of memory for this list */
                        //TODO: Exception
                        break;
                    }
                }
            }
        }
        if (!found) {
            /* Throw List doesn't have an object like that */
            //TODO: Exception
        }
    }
    public void removeAt(int index) {
        elements[index] = null;
        /* Will be replaced with System.arrayCopy */
        for (int i = index + 1; i < elements.length && elements[i] != null; i++) {
            elements[i - 1] = elements[i];
        }
        Tail<X> children = down;
        while (children != null && elements[elements.length - 1] == null && children.elements.length > 0) {
            elements[elements.length - 1] = children.elements[0];
            //Removed because its not necessary -> children.elements[0] = null;
            /* Will be replaced with System.arrayCopy */
            for (int i = 1; i < children.elements.length && children.elements[i] != null; i++) {
                children.elements[i - 1] = children.elements[i];
            }
            children = children.down;
        }
    }
}
