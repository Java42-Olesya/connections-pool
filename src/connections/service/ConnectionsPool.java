package connections.service;

import connections.dto.Connection;

public interface ConnectionsPool extends Iterable<Connection>{
boolean addConnection(Connection connection);
Connection getConnection(int id);
int getSize();

}
