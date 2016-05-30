import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author sbaron
 * @since 5/30/16
 */
public class LRUCacheTest {

    @Test
    public void getWithEmptyCache() {
        LRUCache lruCache = new LRUCache(5);

        assertEquals(lruCache.get(1), -1);
    }

    @Test
    public void addingTwoValues() {
        LRUCache lruCache = new LRUCache(2);

        lruCache.put(1, 1);
        lruCache.put(2, 2);

        String expected = "2 1";

        assertEquals(lruCache.toString().trim(), expected);
    }


    @Test
    public void cacheIsSmallerThenValues() {
        LRUCache lruCache = new LRUCache(1);

        lruCache.put(1, 1);
        lruCache.put(2, 2);

        String expected = "2";

        assertEquals(lruCache.toString().trim(), expected);
    }

    @Test
    public void cacheHit() {
        LRUCache lruCache = new LRUCache(3);

        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        lruCache.put(2, 2);

        String expected = "2 3 1";

        assertEquals(lruCache.toString().trim(), expected);
    }

    @Test
    public void cacheMiss() {
        LRUCache lruCache = new LRUCache(3);

        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        lruCache.put(4, 4);

        String expected = "4 3 2";

        assertEquals(lruCache.toString().trim(), expected);
    }

    @Test
    public void getFromCache() {
        LRUCache lruCache = new LRUCache(3);

        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        int expectedValue = lruCache.get(2);

        String expected = "2 3 1";

        assertEquals(lruCache.toString().trim(), expected);
        assertEquals(2, expectedValue);
    }
}
