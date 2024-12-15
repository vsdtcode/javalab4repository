public interface CacheManager <K,V>{
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    boolean contains(K key);
    void clear();
    int size();
}

class CacheManagerOne implements CacheManager<String, String>{
    public void put(String key, String value) {
        System.out.println("Поклали інформацію за ключем: " + key + "та значенням: " + value);
    }


    public String get(String key) {
        System.out.println("Дістали інформацію за ключем: ");
        return key;
    }

    public void remove(String key) {
        System.out.println("Прибрали інформацію за ключем: " + key);
    }

    public boolean contains(String key) {
        return true;
    }

    public void clear() {
        System.out.println("Очищення кешу");
    }

    public int size() {
        return 2;
    }
}
