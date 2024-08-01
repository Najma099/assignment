import java.util.Scanner;

public class QuadraticProbingHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private int size;
    private Entry<K, V>[] table;

    private static class Entry<K, V> {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

    @SuppressWarnings("unchecked")
    public QuadraticProbingHashMap() {
        table = new Entry[INITIAL_CAPACITY];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        size = 0;
        for (Entry<K, V> entry : oldTable) {
            if (entry != null && !entry.isDeleted) {
                insert(entry.key, entry.value);
            }
        }
    }

    public void insert(K key, V value) {
        if ((double) size / table.length >= LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        int index = hash(key);
        int i = 0;
        while (table[index] != null && !table[index].isDeleted && !table[index].key.equals(key)) {
            i++;
            index = (hash(key) + i * i) % table.length;
        }

        if (table[index] == null || table[index].isDeleted) {
            size++;
        }

        table[index] = new Entry<>(key, value);
    }

    public boolean find(K key) {
        int index = hash(key);
        int i = 0;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key.equals(key)) {
                return true;
            }
            i++;
            index = (hash(key) + i * i) % table.length;
        }
        return false;
    }

    public void remove(K key) {
        int index = hash(key);
        int i = 0;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key.equals(key)) {
                table[index].isDeleted = true;
                size--;
                return;
            }
            i++;
            index = (hash(key) + i * i) % table.length;
        }
    }

    public void printTable() {
        System.out.println("-------------------");
        System.out.println("|  Index  |  Key  |  Value |");
        System.out.println("-------------------");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isDeleted) {
                System.out.printf("|   %d     |   %2d  |   %2s   |%n", i, table[i].key, table[i].value);
            } else {
                System.out.printf("|   %d     |  NULL |  NULL  |%n", i);
            }
        }
        System.out.println("-------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of HashTable:");
        int size = scanner.nextInt();
        QuadraticProbingHashMap<Integer, String> hashTable = new QuadraticProbingHashMap<>();

        int numEle = 100000;
        while (numEle > size) {
            System.out.println("Enter the number of elements you want to insert in Hash Table:");
            numEle = scanner.nextInt();
            if (numEle > size) {
                System.out.println("Please enter the number of elements less than the size of the hash table.");
            }
        }

        for (int i = 0; i < numEle; i++) {
            System.out.println("Enter the key:");
            int key = scanner.nextInt();
            System.out.println("Enter the value:");
            String value = scanner.next();
            hashTable.insert(key, value);
        }

        // Example operations
        hashTable.printTable();
        System.out.println("Find key 1: " + hashTable.find(1));
        System.out.println("Remove key 1:");
        hashTable.remove(1);
        hashTable.printTable();
        System.out.println("Find key 1: " + hashTable.find(1));

        scanner.close();
    }
}

