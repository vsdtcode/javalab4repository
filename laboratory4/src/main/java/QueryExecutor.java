public interface QueryExecutor {
    Object executeRead(Object context, Object query);
    Object executeWrite(Object context, Object query, Object data);
    Object executeWithParams(Object context, Object query, Object... params);
}

class QueryExecutorOne implements QueryExecutor {

    public Object executeRead(Object context, Object query) {
        System.out.println("Зчитування" + query);
        return null;
    }

    public Object executeWrite(Object context, Object query, Object data) {
        System.out.println("Запис: " + query + " дані " + data);
        return null;
    }

    public Object executeWithParams(Object context, Object query, Object... params) {
        System.out.println("Виконання запиту з параметрами " + query);
        return null;
    }
}
