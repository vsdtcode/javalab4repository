public interface TransactionManager {
    void beginTransaction(Object context);
    void commitTransaction(Object context);
    void rollbackTransaction(Object context);
    boolean isTransactionActive(Object context);
}

class TransactionManagerOne implements TransactionManager {
    public void beginTransaction(Object context) {
        System.out.println("Початок транзакції");
    }

    public void commitTransaction(Object context) {
        System.out.println("Здійснення транзакції");
    }

    public void rollbackTransaction(Object context) {
        System.out.println("Відміна транзакції");
    }

    public boolean isTransactionActive(Object context){
        return true;
    }
}
