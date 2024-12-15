class CentralizedConnectionManager {
    private static CentralizedConnectionManager instance;
    private ConnectionPool connectionPool;
    private QueryExecutor queryExecutor;
    private TransactionManager transactionManager;
    private CacheManager<String, String> cacheManager;

    private CentralizedConnectionManager() {

    }

    public static synchronized CentralizedConnectionManager getInstance() {
        if (instance == null) {
            instance = new CentralizedConnectionManager();
        }
        return instance;
    }

    //Перевірки
    private void checkContext(Object context) {
        if (context == null) {
            throw new IllegalStateException("Помилка");
        }
    }

    private void checkQueryExecutor(Object queryExecutor) {
        if (queryExecutor == null) {
            throw new IllegalStateException("Відсутня ініціалізація");
        }
    }
    private void checkQuery(Object query){
        if (query == null) {
            throw new IllegalArgumentException("Помилка запиту");
        }
    }

    private void checkKey(String key) {
        if (key == null) {
            throw new IllegalStateException("Помилка задання ключа");
        }
    }

    private void checkValue(String value) {
        if (value == null) {
            throw new IllegalStateException("Помилка задання значення");
        }
    }

    // set

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void setQueryExecutor(QueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setCacheManager(CacheManager<String, String> cacheManager) {
        this.cacheManager = cacheManager;
    }


    //методи для ConnectionPool

    public void getConnection() {
        connectionPool.getConnection();
    }

    public void releaseConnection(Object connection) {
        connectionPool.releaseConnection(connection);
    }

    public void closeAllConnections(){
        connectionPool.closeAllConnections();
    }

    public int getAvailableConnections(){
        return connectionPool.getAvailableConnections();
    }

    public int getActiveConnections(){
        return connectionPool.getActiveConnections();
    }

    //методи для QueryExecutor
    public Object executeRead(Object context, Object query){
        checkQueryExecutor(queryExecutor);
        checkQuery(query);
        return queryExecutor.executeRead(context, query);
    }

    public Object executeWrite(Object context, Object query, Object data){
        checkQueryExecutor(queryExecutor);
        checkQuery(query);
        return queryExecutor.executeWrite(context,query,data);
    }

    public Object executeWithParams(Object context, Object query, Object... params){
        checkQueryExecutor(queryExecutor);
        checkQuery(query);
        return queryExecutor.executeWithParams(context, query, params);
    }

    //методи для TransactionManager
    public void beginTransaction(Object context){
        checkContext(context);
        transactionManager.beginTransaction(context);
    }


    public void commitTransaction(Object context){
        checkContext(context);
        transactionManager.commitTransaction(context);
    }
    public void rollbackTransaction(Object context){
        checkContext(context);
        transactionManager.rollbackTransaction(context);
    }
    public boolean isTransactionActive(Object context){
        checkContext(context);
        return  transactionManager.isTransactionActive(context);
    }

    //методи для CacheManager
    public void put(String key, String value){
        cacheManager.put(key,value);
    }

    public void get(String key){
        checkKey(key);
        cacheManager.get(key);
    }

    public void remove(String key){
        checkKey(key);
        cacheManager.remove(key);
    }

    public boolean contains(String key){
        return cacheManager.contains(key);
    }

    public void clear(){
        cacheManager.clear();
    }

    public int size(){
        return cacheManager.size();
    }



}
