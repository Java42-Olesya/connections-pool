package connections.dto;

public class Connection {
	private int id;
	private String ipAddress;
	private int port;

	public Connection(int id, String ipAddress, int port) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public Connection() {

	}

	public int getId() {
		return id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "Connection [id=" + id + ", ipAddress=" + ipAddress + ", port=" + port + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connection other = (Connection) obj;
		if (id != other.id)
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}
