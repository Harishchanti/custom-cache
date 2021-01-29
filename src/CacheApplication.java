import exception.ElementNotFoundException;
import service.CacheImplementation;
import service.ICache;
import service.Strategy;

public class CacheApplication {
    public static void main(String[] args) {

        ICache cache = new CacheImplementation(5);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        cache.put("D", 4);
        cache.put("E", 5);

        try {
            System.out.println(cache.get("B"));
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }

        cache.put("F", 41);

        System.out.println(cache.size());

        try {
            System.out.println(cache.get("A"));
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }

        // expriry the based on

        ((CacheImplementation<?, ?>) cache).setStrategy(Strategy.TIME);

        ((CacheImplementation<?, ?>) cache).cleanUp();


    }
}
