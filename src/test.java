import java.io.*;
import java.net.*;

public class test extends Thread {

	private int temp;
	private int port;

	test(int temp, int port) {
		this.port = port;
		this.temp = temp;
	}

	public void run() {

		try {
			byte[] b = Integer.toString(temp).getBytes();
			InetAddress add = InetAddress.getByName("239.0.0.1");
			MulticastSocket ds = new MulticastSocket(port);
			ds.joinGroup(add);
			DatagramPacket pkg = new DatagramPacket(b, b.length, add, 6000);
			ds.send(pkg);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
