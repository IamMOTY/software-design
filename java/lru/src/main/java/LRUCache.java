import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

public abstract class LRUCache<K, V> {
    protected final int capacity;
    protected Node head = null;
    protected Node tail = null;

    public LRUCache(int capacity) {
        Validate.isTrue(capacity > 0);
        this.capacity = capacity;
    }

    public void put(@NotNull K key, @NotNull V value) {
        Validate.notNull(key);
        Validate.notNull(value);
        int oldSize = size();
        doPut(key, value);
        Validate.isTrue(size() >= oldSize);
        Validate.notNull(head);
        Validate.notNull(tail);
        Validate.isTrue(head.value == value);
    }

    @NotNull
    public Optional<V> get(@NotNull K key) {
        Validate.notNull(key);
        int oldSize = size();
        var res = doGet(key);
        Validate.isTrue(size() == oldSize);
        Validate.isTrue(res.isEmpty() || (head.value == res.get()));
        return res;
    }

    public int size() {
        int result = doSize();
        Validate.inclusiveBetween(0, capacity, result);
        return result;
    }

    protected abstract void doPut(@NotNull K key, @NotNull V value);

    protected abstract Optional<V> doGet(@NotNull K key);

    protected abstract int doSize();

    protected class Node {
        @NotNull
        K key;
        @NotNull
        V value;

        @Nullable
        Node prev, next;

        public Node(@NotNull K key, @NotNull V value) {
            Validate.notNull(key);
            Validate.notNull(value);
            this.key = key;
            this.value = value;
        }
    }
}
