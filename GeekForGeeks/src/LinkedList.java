public class LinkedList {

    Node head;

    static class Node{
        int data;
        Node next;

        Node(Integer d){ data =d;}
    }

    public void printList(){
        Node n= head;

        while (n!=null){
            System.out.print(n.data+" ");
            n=n.next;
        }
    }

    public void push(Integer d){
        Node n= new Node(d);
        n.next=head;
        head=n;
    }

    public void add(Node after,Integer d){
        Node n = new Node(d);
        n.next=after.next;
        after.next=n;
    }

    public Node findEnd(){
        Node n=head;
        while(n!=null){
         if(n.next==null){
             return n;
         }
         else{
             n=n.next;
         }
        }
        return  null;
    }
    public void addEnd(Integer d){
        Node n=new Node(d);
        Node end = findEnd();
        end.next=n;
    }

    public void delete(Integer d){

        Node n= head;
        Node prev= null;
        if (n!= null && n.data == d)
        {
            head = n.next; // Changed head
            return;
        }
        while (n!=null){
            if(n.data==d){
                break;
            }
            else{
                prev=n;
                n=n.next;
            }
        }

        prev.next=n.next;
        n.next=null;
    }

    public Integer findLength(){
        Integer count=0;

        Node n=head;

        while (n!=null){
            count++;
            n=n.next;
        }
        return count;
    }

    public Integer findLengthRecursive(Node n){

        if(n==null){
            return 0;
        }

        return 1+findLengthRecursive(n.next);
    }


    public void swapNodes(int one,int two){
        Node n=head;
        Node prev=null;
        Node firstPrev=null;
        Node secondPrev=null;
        Node first=head;
        Node second=head;


        while(first!=null && first.data!=one){
            firstPrev=first;
            first=first.next;
        }

        while(second!=null && second.data!=two){
            secondPrev=second;
            second=second.next;
        }

        System.out.println("Nodes to be swapped: "+first.data+" "+second.data);
        System.out.println("Their previous elements are :"+firstPrev.data+" "+secondPrev.data);

        firstPrev.next=second;
        secondPrev.next=first;

        Node temp=first.next;
        first.next=second.next;
        second.next=temp;

    }

    public void swapNodes2(int x, int y)
    {
        // Nothing to do if x and y are same
        if (x == y) return;

        // Search for x (keep track of prevX and CurrX)
        Node prevX = null, currX = head;
        while (currX != null && currX.data != x)
        {
            prevX = currX;
            currX = currX.next;
        }

        // Search for y (keep track of prevY and currY)
        Node prevY = null, currY = head;
        while (currY != null && currY.data != y)
        {
            prevY = currY;
            currY = currY.next;
        }

        // If either x or y is not present, nothing to do
        if (currX == null || currY == null)
            return;

        // If x is not head of linked list
        if (prevX != null)
            prevX.next = currY;
        else //make y the new head
            head = currY;

        // If y is not head of linked list
        if (prevY != null)
            prevY.next = currX;
        else // make x the new head
            head = currX;

        // Swap next pointers
        Node temp = currX.next;
        currX.next = currY.next;
        currY.next = temp;
    }

    public Node findElement(Integer d){

        return null;
    }

    public static void main(String[] args) {
        LinkedList l = new LinkedList();

        l.head = new Node(4);
        Node second = new Node(3);
        Node third = new Node(5);


        l.head.next=second;
        second.next=third;

        l.push(6);
        l.push(7);
        l.add(second,8);
        l.addEnd(10);
        l.printList();
        l.swapNodes(5,4);
        l.printList();
        System.out.println("\n"+l.findLengthRecursive(l.head));


    }
}
