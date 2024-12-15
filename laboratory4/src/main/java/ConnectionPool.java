public interface ConnectionPool {
    void getConnection();
    void releaseConnection(Object connection);
    void closeAllConnections();
    int getAvailableConnections();
    int getActiveConnections();
}

class ConnectionPoolOne implements ConnectionPool {
    public void getConnection() {
        System.out.println("Отримано підключення до бази даних");
    }

    public void releaseConnection(Object connection) {
        System.out.println("Підключення звільнено");
    }

    public void closeAllConnections() {
        System.out.println("Всі підключення закриті");
    }

    public int getAvailableConnections() {
        return 5;
    }

    public int getActiveConnections() {
        return 1;
    }
}
