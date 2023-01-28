import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LRUCacheTest {

    @Test
    void testEmpty() {
        var cache = new LRUCacheImpl<String, Integer>(1);

        assertThat(cache.size()).isEqualTo(0);
        assertThat(cache.get("")).isEmpty();
    }

    @Test
    void testPut() {
        var cache = new LRUCacheImpl<String, Integer>(2);

        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);

        assertThat(cache.get("1")).isEmpty();
        assertThat(cache.get("2")).contains(2);
        assertThat(cache.get("3")).contains(3);
    }

    @Test
    void testPutExisting() {
        var cache = new LRUCacheImpl<String, Integer>(2);

        cache.put("1", 1);
        cache.put("1", -1);

        assertThat(cache.get("1")).contains(-1);
    }

    @Test
    void testPutExistingPushesToFront() {
        var cache = new LRUCacheImpl<String, Integer>(2);

        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("1", -1);
        cache.put("3", 3);

        assertThat(cache.get("1")).contains(-1);
        assertThat(cache.get("2")).isEmpty();
        assertThat(cache.get("3")).contains(3);
    }

    @Test
    void testGet() {
        var cache = new LRUCacheImpl<String, Integer>(2);

        cache.put("1", 1);
        cache.put("2", 2);

        assertThat(cache.get("1")).contains(1);
        assertThat(cache.get("2")).contains(2);
    }

    @Test
    void testGetPushesToFront() {
        var cache = new LRUCacheImpl<String, Integer>(2);

        cache.put("1", 1);
        cache.put("2", 2);
        var toFront = cache.get("1");
        cache.put("3", 3);

        assertThat(toFront).contains(1);
        assertThat(cache.get("1")).contains(1);
        assertThat(cache.get("2")).isEmpty();
        assertThat(cache.get("3")).contains(3);
    }

    @Test
    void testSize() {
        var cache = new LRUCacheImpl<String, Integer>(2);

        cache.put("1", 1);
        assertThat(cache.size()).isEqualTo(1);
        cache.put("1", -1);
        assertThat(cache.size()).isEqualTo(1);
        cache.put("2", 2);
        assertThat(cache.size()).isEqualTo(2);
        cache.put("3", 3);
        assertThat(cache.size()).isEqualTo(2);
    }

    @Test
    void testNonPositiveCapacity() {
        assertThatIllegalArgumentException().isThrownBy(() -> new LRUCacheImpl<String, Integer>(0));
        assertThatIllegalArgumentException().isThrownBy(() -> new LRUCacheImpl<String, Integer>(-1));
    }

    @Test
    void testNullPut() {
        var cache = new LRUCacheImpl<String, Integer>(1);
        assertThatIllegalArgumentException().isThrownBy(() -> cache.put(null, 1));
        assertThatIllegalArgumentException().isThrownBy(() -> cache.put("1", null));
    }

    @Test
    void testNullGet() {
        var cache = new LRUCacheImpl<String, Integer>(1);
        assertThatIllegalArgumentException().isThrownBy(() -> cache.get(null));
    }
}
