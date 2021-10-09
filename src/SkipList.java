import java.lang.reflect.Array;
import java.util.Random;
import java.util.Stack;
/** Implementation of a simple SkipList utilizing a separate node class and a sortedset interface*/
public class SkipList<E extends Comparable<E>> implements SortedSetInterface<E> {

    private Node<E> head;
    private int height;
    private int numElts = 0;
    private Random rand;
    private double p;

    private int countOps = 0;

    public boolean add(E x) {
        // find the predecessor nodes on each level of the list
        Stack<Node<E>> preds = findAllPreds(x);
        Node<E> pred0 = preds.peek();
        // return false if element x was already present
        if(pred0.nextNodes[0] != null && pred0.nextNodes[0].data.equals(x)) return false;
        // create a new node for element x, and generate its height
        Node<E> newNode = new Node<E>(x, chooseHeight());
        int newHeight = newNode.getHeight();
        // increase node height if necessary
        if (newHeight > height) {
            if (newHeight > head.nextNodes.length-1) {
                Node<E>[] temp = (Node<E>[]) Array.newInstance(Node.class, newHeight+1);
                for(int i = 0; i < head.nextNodes.length; i++) {
                    temp[i] = head.nextNodes[i];
                }
                head.nextNodes = temp;
            }
            for(int i = newHeight; i > height; i--) {
                head.nextNodes[i] = newNode;
            }
            height = newHeight;
        }
        // add x after its predecessor on each level within x's height
        if(pred0.nextNodes[0]==null){
            pred0.nextNodes[0] = newNode;
            return true;
        }
        else{
            int i = 0;
            while(preds.size()>0 && i<=newHeight){
                Node<E> yurt = preds.pop();
                newNode.nextNodes[i] = yurt.nextNodes[i];
                yurt.nextNodes[i] = newNode;
                i++;
            }
        }
        // now we have one more element stored
        numElts++;
        return true;
    }

    public E remove(E x) {
        // find the predecessor nodes on each level of the list
        Stack<Node<E>> preds = findAllPreds(x);
        // if the element wasn't present, nothing to return
        Node<E> pred0 = preds.peek();
        if(pred0.nextNodes[0] == null) return null; // empty list
        if(pred0.nextNodes[0] != null && !pred0.nextNodes[0].data.equals(x)) return null; // non-empty list, elt x wasn't present
        // if the element was present, now we need to remove it from each level on which it appears
        Node<E> current = pred0.nextNodes[0];
        for(int i = 0; i<=current.getHeight(); i++){
            Node <E> prenode = preds.pop();
            prenode.nextNodes[i] = current.nextNodes[i];
        }
        // now we have one fewer element stored
        numElts--;
        return current.data;
    }

    // find the predecessor nodes on each level of the list
    public E find(E x) {
        Stack<Node<E>> preds = findAllPreds(x);
        Node<E> level0 = preds.pop(); // the top of the stack is the predecessor on level 0
        if (level0.nextNodes[0] == null) return null;
        else return level0.nextNodes[0].data;
    }

    // return the number of elements stored
    public int size() {
        return numElts;
    }

    protected Stack<Node<E>> findAllPreds(E x) {
        countOps = 0;
        // this method should return a stack containing the predecessor nodes of element x on each level,
        // with the predecessor on level 0 at the top of the stack and the predecessor at the highest
        // level on the bottom of the stackB
        Stack<Node<E>> predecessors = new Stack<>();
        Node<E> node = head;
        for (int i = height; i>=0; i--) {
            if (numElts >=0){
                while(node.nextNodes[i]!=null&&node.nextNodes[i].data.compareTo(x)<0){
                    node = node.nextNodes[i];
                    countOps++;
                }
            }
            predecessors.push(node);
            countOps++;
        }
        return predecessors;
    }

    public void print() {
        for(int i = 0; i <= height; i++) {
            Node<E> y = head;
            while(y.nextNodes[i] != null) {
                y = y.nextNodes[i];
                System.out.print(y.data + " ");
            }
            System.out.println();
        }
    }

    //method to count the number of operations that occur every time findAllPreds() method is ran.
    public int getOps() {
        return countOps;
    }

    //method to simulate probability when choosing the height of a given node. P is the probability that
    //a given node will be promoted to the next height level that it has not yet occupied.
    private int chooseHeight() {
        int level = 0;
        double flip = rand.nextDouble();
        while(flip < p) {
            level++;
            flip = rand.nextDouble();
        }
        return level;
    }

    //various constructors
    public SkipList() {
        head = new Node<E>(null, 0);
        height = 0;
        p = 0.5;
        rand = new Random();
    }

    public SkipList(int seed) {
        head = new Node<E>(null, 0);
        height = 0;
        p = 0.5;
        rand = new Random(seed);
    }

    public SkipList(double prob) {
        head = new Node<E>(null,0);
        height = 0;
        p = prob;
        rand = new Random();
    }

    public SkipList(double prob, int seed) {
        head = new Node<E>(null,0);
        height = 0;
        p = prob;
        rand = new Random(seed);
    }

}
