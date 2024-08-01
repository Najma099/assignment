


import java.util.LinkedList;

/**
 * A simple implementation of a HashMap in Java.
 * This implementation provides basic functionalities like put, get, remove, and containsKey.
 * It also supports automatic rehashing when the load factor exceeds a certain threshold.
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * 
 * @author NajmaK
 */
class HashMap<K, V> {
    
    /**
     * Represents a key-value pair stored in the HashMap.
     */
    private class Node {
        K key;
        V value;

        /**
         * Constructs a new Node with the specified key and value.
         *
         * @param key the key
         * @param value the value
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int n; // number of nodes
    private int N; // number of buckets
    private LinkedList<Node>[] buckets; // array of linked lists

    /**
     * Constructs a new HashMap with an initial capacity of 4.
     */
    @SuppressWarnings("unchecked")
    public HashMap() {
        this.N = 4;
        this.buckets = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            this.buckets[i] = new LinkedList<>();
        }
    }

    /**
     * Computes the hash code for a given key and maps it to a bucket index.
     *
     * @param key the key
     * @return the bucket index
     */
    private int hashFunction(K key) {
        int bi = key.hashCode();
        return Math.abs(bi) % N;
    }

    /**
     * Searches for a key in the linked list at the specified bucket index.
     *
     * @param key the key to search for
     * @param bi the bucket index
     * @return the index of the node containing the key, or -1 if not found
     */
    private int searchInLL(K key, int bi) {
        LinkedList<Node> ll = buckets[bi];
        for (int i = 0; i < ll.size(); i++) {
            if (ll.get(i).key.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Rehashes the HashMap by doubling the number of buckets and redistributing the nodes.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        LinkedList<Node>[] oldBuckets = buckets;
        this.N = N * 2;
        this.buckets = new LinkedList[N];

        for (int i = 0; i < N; i++) {
            this.buckets[i] = new LinkedList<>();
        }

        this.n = 0;
        for (int i = 0; i < oldBuckets.length; i++) {
            LinkedList<Node> ll = oldBuckets[i];
            for (int j = 0; j < ll.size(); j++) {
                Node node = ll.get(j);
                put(node.key, node.value);
            }
        }
    }

    /**
     * Associates the specified value with the specified key in the map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    public void put(K key, V value) {
        int bi = hashFunction(key);
        int di = searchInLL(key, bi);

        if (di == -1) {
            buckets[bi].add(new Node(key, value));
            n++;
        } else {
            Node node = buckets[bi].get(di);
            node.value = value;
        }

        double loadFactor = (double) n / N;
        if (loadFactor > 0.75) {
            rehash();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V get(K key) {
        int bi = hashFunction(key);
        int di = searchInLL(key, bi);

        if (di == -1) {
            return null;
        } else {
            return buckets[bi].get(di).value;
        }
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key the key whose mapping is to be removed from the map
     */
    public void remove(K key) {
        int bi = hashFunction(key);
        int di = searchInLL(key, bi);

        if (di != -1) {
            buckets[bi].remove(di);
            n--;
        }
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key the key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        int bi = hashFunction(key);
        int di = searchInLL(key, bi);

        return di != -1;
    }
}

public class SeperateChaining {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("India", 19);
        map.put("China", 20);

        System.out.println(map.get("India")); // Output: 19
        System.out.println(map.get("China")); // Output: 20
        System.out.println(map.containsKey("India")); // Output: true
        System.out.println(map.containsKey("USA")); // Output: false

        map.remove("India");
        System.out.println(map.get("India")); // Output: null
    }
}
