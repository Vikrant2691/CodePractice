package InnerClassChallenge;

public class MyLinkedList implements NodeList {

    private ListItem root = null;

    public MyLinkedList(ListItem root) {
        this.root = root;
    }


    @Override
    public ListItem getRoot() {
        return this.root;
    }

    @Override
    public boolean addItem(ListItem item) {

        if (this.root == null) {
            this.root = item;
            return true;
        }

        ListItem currentItem = this.root;

        while (currentItem != null) {

            int comparison = currentItem.compareTo(item);
            if (comparison > 0) {
                if (currentItem.next() != null) {
                    currentItem = currentItem.next();
                } else {
                    currentItem.setNext(item).setPrevious(currentItem);
                    return true;
                }
            } else if (comparison < 0) {
                if (currentItem.previous() != null) {
                    ListItem previous = currentItem.previous();
                    currentItem.setPrevious(item).setPrevious(previous);
                    item.setNext(currentItem);
                    previous.setNext(item);
                } else {
                    item.setNext(currentItem).setPrevious(item);
                    this.root = item;
                }
                return true;
            } else {
                System.out.println(item.getValue() + " already present, not added");
                return false;
            }

        }
        return false;
    }

    @Override
    public boolean removeItem(ListItem item) {

        ListItem currentItem= this.getRoot();




        return false;
    }

    @Override
    public void traverse(ListItem root) {
        if (root == null) {
            System.out.println("List is empty");
        } else {
            while (root != null) {
                System.out.println(root.getValue());
                root=root.next();
            }
        }
    }

    public ListItem traverseRec(ListItem root) {
        if (root == null) {
            System.out.println("List is empty");
            return null;
        } else {
            if(root.next()!=null) {
                System.out.println(root.getValue());
                return traverseRec(root.next());
            }
            else{
                return null;
            }

        }
    }
}

