import java.net.*;
import java.util.ArrayList;
import java.net.*;

public class ContServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// recebe o tempo de todos os processos

			MulticastSocket mcs = new MulticastSocket(6000);
			InetAddress grp = InetAddress.getByName("239.0.0.1");

			mcs.joinGroup(grp);

			int t = 0;
			// Contar o qauntidade de processo

			String data;

			while (t < 3) {
				// do something
				// pause to avoid churning
				byte[] b = new byte[1204];
				DatagramPacket pkg = new DatagramPacket(b, b.length);
				mcs.receive(pkg);

				data = new String(pkg.getData(), 0, pkg.getLength());
				ProcessIdentity iden = new ProcessIdentity(Integer.parseInt(data), pkg.getPort());
				Main.cont.add(iden);
				//System.out.println(data);
				t++;
			}

			//System.out.println(Main.cont.size());

			int i = 0;
			for (ProcessIdentity j : Main.cont) {
				// enviar  para os processos
				byte[] cont = new byte[1024];
				cont = Integer.toString(Main.cont.size()).getBytes();
				DatagramPacket senCont = new DatagramPacket(cont, cont.length, grp, j.getPort());
				mcs.send(senCont);
				System.out.println(i);
				i++;
				for (ProcessIdentity p : Main.cont) {
					byte[] test = Integer.toString(p.getPort()).getBytes();
					DatagramPacket ts = new DatagramPacket(test, test.length, grp, j.getPort());
					// System.out.println("teste" + p.getPort());
					mcs.send(ts);
					System.out.println(i);
					i++;
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
