import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * add an Iterator to the AssociativeArray class, so that it's easier to iterate over the key-value pairs.
 */
public class AssociativeArray<K, V> implements Iterable<KVPair<K, V>> {
    static final int DEFAULT_CAPACITY = 16; // Default capacity of the associative array

    private KVPair<K, V>[] pairs; // Array to store the key-value pairs
    private int size; // Current number of key-value pairs in the associative array

    /**
     * Constructor initializes the associative array with a default capacity.
     */
    @SuppressWarnings("unchecked")
    public AssociativeArray() {
        this.pairs = (KVPair<K, V>[]) Array.newInstance(KVPair.class, DEFAULT_CAPACITY);
        this.size = 0;
    }

    /**
     * Sets or adds a key-value pair to the associative array.
     * @param key The key to set in the associative array.
     * @param value The value associated with the key.
     * @throws NullKeyException If the key is null.
     */
    public void set(K key, V value) throws NullKeyException {
        if (key == null) {
            throw new NullKeyException("Key cannot be null.");
        }
        for (int i = 0; i < size; i++) {
            if (pairs[i].getKey().equals(key)) {
                pairs[i].value = value;
                return;
            }
        }
        ensureCapacity();
        pairs[size++] = new KVPair<>(key, value);
    }

    /**
     * Retrieves the value associated with the specified key.
     * @param key The key whose value is to be retrieved.
     * @return The value associated with the key.
     * @throws KeyNotFoundException If the key is not found.
     */
    public V get(K key) throws KeyNotFoundException {
        if (key == null) {
            throw new KeyNotFoundException("Key cannot be null.");
        }
        for (int i = 0; i < size; i++) {
            if (pairs[i].getKey().equals(key)) {
                return pairs[i].getValue();
            }
        }
        throw new KeyNotFoundException("Key not found: " + key);
    }

    /**
     * Checks if the associative array contains the specified key.
     * @param key The key to check in the associative array.
     * @return True if the key exists, otherwise false.
     */
    public boolean hasKey(K key) {
        if (key == null) return false;
        for (int i = 0; i < size; i++) {
            if (pairs[i].getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a key-value pair from the associative array based on the key provided.
     * @param key The key of the pair to be removed.
     * @throws KeyNotFoundException If the key does not exist in the array.
     */
    public void remove(K key) throws KeyNotFoundException {
        if (key == null) {
            throw new KeyNotFoundException("Key cannot be null.");
        }
        for (int i = 0; i < size; i++) {
            if (pairs[i].getKey().equals(key)) {
                System.arraycopy(pairs, i + 1, pairs, i, size - i - 1);
                pairs[--size] = null;
                return;
            }
        }
        throw new KeyNotFoundException("Key not found: " + key);
    }

    /**
     * Returns the number of key-value pairs currently stored in the associative array.
     * @return The number of key-value pairs.
     */
    public int size() {
        return this.size;
    }

    /**
     * Ensures that the array has enough capacity to add new key-value pairs.
     * If the array is full, it doubles its capacity.
     */
    private void ensureCapacity() {
        if (size == pairs.length) {
            pairs = java.util.Arrays.copyOf(pairs, 2 * size);
        }
    }

    /**
     * Provides an iterator over the key-value pairs in the associative array.
     * @return An iterator that can be used to iterate over the pairs.
     */
    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new Iterator<KVPair<K, V>>() {
            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < size;
            }

            public KVPair<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return pairs[currentIndex++];
            }

            public void remove() {
                throw new UnsupportedOperationException("Remove operation is not supported.");
            }
        };
    }
}
