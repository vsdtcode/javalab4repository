import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class CentralizedConnectionManagerTest {
    private CentralizedConnectionManager manager;
    private ConnectionPool mockConnectionPool;
    private QueryExecutor mockQueryExecutor;
    private TransactionManager mockTransactionManager;

    @BeforeEach
    void setUp() {
        // екземпляр Singleton класу
        manager = CentralizedConnectionManager.getInstance();

        //
        mockConnectionPool = mock(ConnectionPool.class);
        mockQueryExecutor = mock(QueryExecutor.class);
        mockTransactionManager = mock(TransactionManager.class);


        //
        manager.setConnectionPool(mockConnectionPool);
        manager.setQueryExecutor(mockQueryExecutor);
        manager.setTransactionManager(mockTransactionManager);

    }

    @Test
    public void CCMInitTest() {
        // Перевірка, що екземпляр класу не null
        assertNotNull(CentralizedConnectionManager.getInstance());
    }

    @Test
    public void testGetConnection() {
        // Тестування getConnection
        manager.getConnection();
        verify(mockConnectionPool).getConnection();
    }

    @Test
    public void testReleaseConnection() {
        // Тестування releaseConnection
        Object connection = new Object();
        manager.releaseConnection(connection);
        verify(mockConnectionPool).releaseConnection(connection);
    }

    @Test
    void testCloseAllConnections() {
        manager.closeAllConnections();

        // Перевірка, що метод closeAllConnections був викликаний  у ConnectionPool
        verify(mockConnectionPool, times(1)).closeAllConnections();
    }

    @Test
    void testGetAvailableConnections() {

        when(mockConnectionPool.getAvailableConnections()).thenReturn(5);

        // Перевірка поверненого значення з мок
        int availableConnections = manager.getAvailableConnections();
        assertEquals(5, availableConnections);

        // Перевірка виклику методу getAvailableConnections у ConnectionPool
        verify(mockConnectionPool, times(1)).getAvailableConnections();
    }

    @Test
    void testGetActiveConnections() {

        when(mockConnectionPool.getActiveConnections()).thenReturn(3);

        // Перевірка поверненого значення з мок
        int activeConnections = manager.getActiveConnections();
        assertEquals(3, activeConnections);

        // Перевірка виклику методу getActiveConnections у ConnectionPool
        verify(mockConnectionPool, times(1)).getActiveConnections();
    }







    @Test
    public void testExecuteRead() {
        // Тестування executeRead
        String context = "testContext";
        String query = "testQuery";
        when(mockQueryExecutor.executeRead(context, query)).thenReturn("Mock Result");

        Object result = manager.executeRead(context, query);

        assertEquals("Mock Result", result);
        verify(mockQueryExecutor).executeRead(context, query);
    }

    @Test
    void testExecuteWrite() {

        String context = "testContext";
        String query = "query";
        String data = "data";
        when(mockQueryExecutor.executeWrite(context, query, data)).thenReturn("Write Success");

        // Виклик методу executeWrite
        Object result = manager.executeWrite(context, query, data);

        // Проверка поверненого результату
        assertEquals("Write Success", result);

        // Перевірка виклику методу executeWrite у QueryExecutor
        verify(mockQueryExecutor, times(1)).executeWrite(context, query, data);
    }

    @Test
    void testExecuteWithParams() {

        String context = "testContext";
        String query = "SELECT * FROM users WHERE id = ?";
        Object[] params = {1};
        when(mockQueryExecutor.executeWithParams(context, query, params)).thenReturn("Params Success");

        // Виклик методу executeWithParams
        Object result = manager.executeWithParams(context, query, params);

        // Перевірка поіерненого результату
        assertEquals("Params Success", result);

        // Перевірка виклику методу executeWithParams у QueryExecutor
        verify(mockQueryExecutor, times(1)).executeWithParams(context, query, params);
    }




    @Test
    public void testBeginTransaction() {
        // Тестування beginTransaction
        String context = "testContext";
        manager.beginTransaction(context);
        verify(mockTransactionManager).beginTransaction(context);
    }

    @Test
    public void testCommitTransaction() {
        // Тестування commitTransaction
        String context = "testContext";
        manager.commitTransaction(context);
        verify(mockTransactionManager).commitTransaction(context);
    }

    @Test
    public void testRollbackTransaction() {
        // Тестування rollbackTransaction
        String context = "testContext";
        manager.rollbackTransaction(context);
        verify(mockTransactionManager).rollbackTransaction(context);
    }

    @Test
    public void testIsTransactionActive() {
        // Тестування isTransactionActive
        String context = "testContext";
        when(mockTransactionManager.isTransactionActive(context)).thenReturn(true);

        boolean isActive = manager.isTransactionActive(context);

        assertTrue(isActive);
        verify(mockTransactionManager).isTransactionActive(context);
    }



    @Test
    void testExecuteReadWithNullQuery() {
        assertThrows(IllegalArgumentException.class, () -> manager.executeRead("context", null));
    }

    @Test
    void testExecuteWriteWithoutQueryExecutor() {
        manager.setQueryExecutor(null);
        assertThrows(IllegalStateException.class, () -> manager.executeWrite("context", "query", "data"));
    }


    }

