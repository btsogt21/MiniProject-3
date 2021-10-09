import java.lang.reflect.Array;
/** Node class utilized by SkipList.java*/
public class Node<E> {
    protected E data;
    protected Node<E>[] nextNodes;

    // method to return the height of a given node
    public int getHeight() {
        return nextNodes.length - 1;
    }

    //Constructor
    public Node(E elt, int height){
        data = elt;
        nextNodes = (Node<E>[]) Array.newInstance(Node.class, height+1);
    }
}
