import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCacheImpl<K, V> extends LRUCache<K, V> {
    private final Map<K, Node> data = new HashMap<>();

    public LRUCacheImpl(int capacity) {
        super(capacity);
    }

    @Override
    protected void doPut(@NotNull K key, @NotNull V value) {
        Validate.notNull(key);
        Validate.notNull(value);
        Node node = data.get(key);
        if (node != null) {
            removeNode(node);
        }
        addNode(new Node(key, value));
    }

    @Override
    protected Optional<V> doGet(@NotNull K key) {
        Validate.notNull(key);
        Node node = data.get(key);
        if (node == null) {
            return Optional.empty();
        }
        removeNode(node);
        addNode(new Node(node.key, node.value));
        return Optional.of(node.value);
    }

    @Override
    protected int doSize() {
        int result = 0;
        Node cur = head;
        while (cur != null) {
            result++;
            cur = cur.prev;
        }
        return result;
    }

    private void addNode(@NotNull Node newNode) {
        Validate.notNull(newNode);
        if (size() == capacity) {
            if (capacity == 0) {
                return;
            }
            removeNode(tail);
        }
        if (head != null) {
            head.next = newNode;
            newNode.prev = head;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        data.put(newNode.key, newNode);
    }

    private void removeNode(@NotNull Node node) {
        Validate.notNull(node);
        data.remove(node.key);
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            tail = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            head = node.prev;
        }
    }
}
