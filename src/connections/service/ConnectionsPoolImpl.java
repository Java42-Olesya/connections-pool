package connections.service;

import java.util.HashMap;
import java.util.Iterator;
import connections.dto.Connection;


public class ConnectionsPoolImpl implements ConnectionsPool{
	private static class Node {
		Connection connection;
		Node prev;
		Node next;

		public Node(Connection connection) {
			this.connection = connection;
		}
	}

	private class ConnectionsPoolIterator implements Iterator<Connection> {	
	Node current = list.head; 

	@Override
	public boolean hasNext() {
		return current != null;
	}
		@Override
		public Connection next() {
			Connection res = current.connection;
		current = current.next;
			return res;
		}
	}

	private static class ConnectionsList{
		Node head;
		Node tail;
		
		public void add(Node connectionNode) {
			if(head == null) {
				head = tail = connectionNode;
			}else {
				tail.next = connectionNode;
				connectionNode.prev = tail;
				tail = connectionNode;
			}
		}
	}

	ConnectionsList list = new ConnectionsList();
	HashMap<Integer, Node> mapConnections = new HashMap<>();
	
	int connectionsPoolLimit;
	
	public ConnectionsPoolImpl(int limit) {
		this.connectionsPoolLimit = limit;
	}

	@Override
	public boolean addConnection(Connection connection) {
		int connectId = connection.getId();
		Node connectionNode = new Node(connection);
		if(mapConnections.containsKey(connectId)) {
			return false;
			
		}else {
			list.add(connectionNode);
			if(mapConnections.size() >= connectionsPoolLimit){
				removeConnection();	
			}
			mapConnections.putIfAbsent(connection.getId(), connectionNode);
		}
		return true;
	}

	
	private void removeConnection() {
		int mostOldConnectId = list.head.connection.getId();
		list.head = list.head.next;
		list.head.prev = null;
		mapConnections.remove(mostOldConnectId);
			
	}

	@Override
	public Connection getConnection(int id) {
		if(!mapConnections.containsKey(id)) {
			return null;
		}
		Node connection = mapConnections.get(id);
		if(connection == list.head) {
			redirectHeadReference(connection);
		}
		else if(connection != list.tail){
			redirectReferences(connection);
		}
		return connection.connection;
	}

	private void redirectHeadReference(Node connection) {
		list.head = list.head.next;
		list.head.prev = null;
		list.tail.next = connection;
		connection.prev = list.tail;
		connection.next = null;
		list.tail = connection;
		
	}

	private void redirectReferences(Node connection) {
		connection.prev.next = connection.next;
		connection.next.prev = connection.prev;
		list.tail.next = connection;
		connection.prev = list.tail;
		connection.next = null;
		list.tail = connection;
	}

	@Override
	public int getSize() {
		return mapConnections.size();
	}

	@Override
	public Iterator<Connection> iterator() {
		
		return new ConnectionsPoolIterator();
	}
	
}
