import java.util.HashMap;
import java.util.Map;

class Node {
    int key;
    String value;
    Node prev;
    Node next;

    Node(int key, String value) {
        this.key = key;
        this.value = value;
    }
}

public class LRU {
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head;
    private final Node tail;

    public LRU(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(-1, "");
        this.tail = new Node(-1, "");
        head.next = tail;
        tail.prev = head;
    }

    public String get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            moveToTail(node);
            return node.value;
        }
        return "-1";
    }

    public void put(int key, String value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            moveToTail(node);
        } else {
            if (cache.size() >= capacity) {
                removeHead();
            }
            Node newNode = new Node(key, value);
            addToTail(newNode);
            cache.put(key, newNode);
        }
    }

    private void moveToTail(Node node) {
        removeNode(node);
        addToTail(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToTail(Node node) {
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
    }

    private void removeHead() {
        Node node = head.next;
        removeNode(node);
        cache.remove(node.key);
    }

    public static void main(String[] args) {
        LRU lruCache = new LRU(4);
        lruCache.put(1, "Value_1");
        lruCache.put(2, "Value_2");
        lruCache.put(3, "Value_3");
        lruCache.put(4, "Value_4");

        System.out.println(lruCache.get(2));

        lruCache.put(5, "Value_5");

        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(2));
    }
}
