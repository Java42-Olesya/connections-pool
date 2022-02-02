package connections.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import connections.dto.Connection;

class ConnectionsPoolTest<T> {

	private static final int LIMIT = 4;
	private static final int ID1 = 1;
	private static final int ID2 = 2;
	private static final int ID3 = 3;
	private static final int ID4 = 4;
	private static final int ID5 = 5;
	private static final int ID6 = 6;
	private static final String ADDRESS = "address";
	private static final int PORT = 123;

	ConnectionsPool connectionsPool;
	Connection connect1 = new Connection(ID1, ADDRESS, PORT);
	Connection connect2 = new Connection(ID2, ADDRESS, PORT);
	Connection connect3 = new Connection(ID3, ADDRESS, PORT);
	Connection connect4 = new Connection(ID4, ADDRESS, PORT);
	Connection connect5 = new Connection(ID5, ADDRESS, PORT);
	List<Connection> connectionList = Arrays.asList(connect1, connect2, connect3);

	@BeforeEach
	void setUp() throws Exception {
		connectionsPool = new ConnectionsPoolImpl(LIMIT);
		connectionList.forEach(connectionsPool::addConnection);
	}

	@Test
	void testAddConnection() {
		assertFalse(connectionsPool.addConnection(connect1));
		assertTrue(connectionsPool.addConnection(connect4));
		assertEquals(LIMIT, connectionsPool.getSize());
		connectionsPool.addConnection(connect5);
		assertEquals(LIMIT, connectionsPool.getSize());
		Connection[] expected = new Connection[] {connect2, connect3, connect4, connect5};
		compareConnection(expected);
	
	}

	@Test
	void testGetConnection() {
		assertEquals(connect2, connectionsPool.getConnection(ID2));
		Connection[] expected = new Connection[] { connect1, connect3, connect2 };
		compareConnection(expected);
		assertNull(connectionsPool.getConnection(ID6));

	}
	
	@Test
	void getFirstConnection() {
		Connection[] expected = new Connection[] {connect2, connect3, connect1};
		connectionsPool.getConnection(ID1);
		compareConnection(expected);
	}
	
	@Test
	void getLastConnection() {
		Connection[] expected = new Connection[] {connect1, connect2, connect3};
		connectionsPool.getConnection(ID3);
		compareConnection(expected);	
	}

	private void compareConnection(Connection[] expected) {
		int i = 0;
		for (Connection c : connectionsPool) {
			assertEquals(expected[i++], c);
		}
	}

}
