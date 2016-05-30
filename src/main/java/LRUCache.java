import java.util.HashMap;

/**
 * LRU Cache implementation using map with values linked one to another for quick access, insert and remove.
 * The cache supports two main operations:
 * 1. put(key, value)
 * 2. value get(key)
 *
 * @author sbaron
 * @since 5/30/16
 */
public class LRUCache {
    private int cacheCapacity;
    private Node head;
    private Node tail;
    private HashMap<Integer, Node> cache = new HashMap<Integer, Node>();

    public LRUCache(int cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    /**
     * Using this method you can add elements to the cache
     * 1. every new element will replace the head node
     * 2. if the element exists:
     * 2.1 - if the cache is full, the tail node will be removed
     * 2.2 - the element will replace the head node which will be moved one place back
     * 2.3 - the prev and next elements of the node will point one to another
     *
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        //cache hit - the node should be shifted to head
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.setValue(value);
            shiftToHead(node);
        } else {
            //new node - should be added to the cache
            Node newNode = new Node(key, value);

            //cache is full - the lru node should be removed
            if (cache.size() >= cacheCapacity) {
                cache.remove(tail.getKey());
                removeTail();
            }

            setHead(newNode);
            cache.put(key, newNode);
        }
    }

    /**
     * Using this method you can access to data in the cache
     * once accessed, the node with the given key will be shifted to head
     *
     * @param key
     * @return
     */
    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            shiftToHead(node);

            return node.getValue();
        }

        return -1;
    }


    /**
     * This method is responsible for removing the tail and setting the node before the tail to be the new tail
     */
    private void removeTail() {
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
        }
    }


    /**
     * If the element exists in the cache and accessed or inserted with another value,
     * the node will be shifted to head
     *
     * @param node
     */
    private void shiftToHead(Node node) {

        //the node is already in the head - nothing should be done
        if (node.getPrev() == null) {
            return;
        }

        //the node is tail
        if (node.getNext() == null) {
            removeTail();
        }
        //the node is somewhere in the middle
        else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }

        //move to head
        setHead(node);
    }

    /**
     * Sets node to be the new head, the old head will be placed right after the new one
     * @param node
     */
    private void setHead(Node node) {
        node.setNext(head);
        node.setPrev(null);

        if (head != null) {
            head.setPrev(node);
        }

        head = node;

        if (tail == null) {
            tail = head;
        }
    }


    /**
     * Returns the cache state in a String separated by spaces
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node position = head;
        while (position != null) {
            sb.append(position.getValue());
            sb.append(" ");
            position = position.getNext();
        }

        return sb.toString();
    }
}