class BinaryTree {
    Node root;

    public static Node{
        int key;
        Node left;
        Node right;

        Node(Integer item){
            key=item;
            left=right=null;
        }
    }

    BinaryTree(Integer key) {
        root = new Node(key);
    }

    BinaryTree() {
        root = null;
    }

    public void treeTraversal(BinaryTree tree) {


    }

    void inOrder(Node node) {
        if (node == null) {
            return;

        }
        inOrder(node.left);
        System.out.print(node.key + " ");
        inOrder(node.right);
    }

    void preOrder(Node node) {
        if (node == null) {
            return;

        }
        System.out.print(node.key + " ");
        preOrder(node.left);
        preOrder(node.right);
    }


    void postOrder(Node node) {
        if (node == null) {
            return;

        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.key + " ");
    }


    int height(Node root) {
        if (root == null)
            return 0;
        else {

            int lheight = height(root.left);
            int rheight = height(root.right);

            if (lheight > rheight) {
                return (1 + lheight);
            } else
                return (1 + rheight);

        }

    }


    void printLevelOrder() {
        Integer h = height(root);

        for (int i = 1; i <= h; i++) {
            printGivenLevel(root, i);
            System.out.println();
        }

    }


    void printGivenLevel(Node root, int level) {
        if (root == null)
            return;
        if (level == 1) {
            System.out.print(root.key);
        } else if (level > 1) {
            printGivenLevel(root.left, level - 1);
            printGivenLevel(root.right, level - 1);
        }
    }


    void insert(int key) {

        root = insertRec(root, key);
    }

    Node insertRec(Node root, int key) {

        Node n = new Node(6);

        if (root == null || root.key == key) {
            return root;
        }

        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        return root;

    }


    void search(Node root, Integer key) {


        if (root == null) {
            System.out.println("Element not Found");
            return;
        }

        if (root.key == key) {
            System.out.println("Found the element");
            return;
        }

        if (root.key > key) {
            search(root.left, key);
        } else {
            search(root.right, key);
        }

    }


    public static void main(String[] args) {

        BinaryTree tree = new BinaryTree();

        tree.root = new Node(10);

        tree.root.left = new Node(-5);
        tree.root.right = new Node(16);
        tree.root.right.right = new Node(18);
        tree.root.left.left = new Node(-8);
        tree.root.left.right = new Node(7);
        tree.preOrder(tree.root);
        tree.insert(6);
        tree.preOrder(tree.root);
        /*System.out.println(" ");
        tree.preOrder(tree.root);
        System.out.println(" ");
        tree.postOrder(tree.root);
        System.out.println(" ");
        tree.printLevelOrder();
*/
        tree.search(tree.root, -5);


    }
}
