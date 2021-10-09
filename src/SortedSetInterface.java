public interface SortedSetInterface<E> {
    /** Adds an element to the skiplist. Returns false if element was not present.*/
    public boolean add (E element);
    /** Removes and returns an element from the skiplist. Returns nothing if element does not exist*/
    public E remove (E element);
    /** Finds the predecessor nodes on each level of the skiplist*/
    public E find (E element);
    /** Returns the number of elements in the skiplist*/
    public int size ();
}