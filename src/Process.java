import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Process extends Thread {

	private int identity;
	private int temp;
	private int port;

	List<Integer> list = new ArrayList<Integer>();

	Process(int identiy, int temp, int port) {
		this.identity = identiy;
		this.temp = temp;
		this.port = port;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			// Enviar tempo para o servidor mostrando que tem interesse

			byte[] b = Integer.toString(temp).getBytes();
			InetAddress add = InetAddress.getByName("239.0.0.1");
			MulticastSocket ds = new MulticastSocket(port);
			ds.joinGroup(add);
			DatagramPacket pkg = new DatagramPacket(b, b.length, add, 6000);
			ds.send(pkg);

			byte[] receiveCont = new byte[1024];
			DatagramPacket pkgCont = new DatagramPacket(receiveCont, receiveCont.length);
			// receber contador
			ds.receive(pkgCont);

			int cont = Integer.parseInt(new String(pkgCont.getData(), 0, pkgCont.getLength()));
			sleep(2000);
			for (int i = 0; i < cont; i++) {
				byte[] rec = new byte[1204];
				// receber resposta
				pkg = new DatagramPacket(rec, rec.length);
				ds.receive(pkg);
				System.out.println(new String(pkg.getData(), 0, pkg.getLength()));
				byte[] sendTime = Integer.toString(temp).getBytes();
				DatagramPacket sendMult = new DatagramPacket(sendTime, sendTime.length, add,
						Integer.parseInt(new String(pkg.getData(), 0, pkg.getLength())));
				ds.send(sendMult);

			}
			sleep(2000);
			List<ProcessIdentity> lis = new ArrayList<ProcessIdentity>();
			for (int i = 0; i < cont; i++) {
				byte[] receivMsn = new byte[1024];
				pkg = new DatagramPacket(receivMsn, receivMsn.length);
				ds.receive(pkg);

				System.out.println("identity :" + identity + " " + new String(pkg.getData(), 0, pkg.getLength()));
				ProcessIdentity pi = new ProcessIdentity(
						Integer.parseInt(new String(pkg.getData(), 0, pkg.getLength())), pkg.getPort());

				if (new String(pkg.getData(), 0, pkg.getLength()) != "OK") {
					if (temp < pi.getTemp()) {
						File file = new File("teste.txt");
						if (file.exists()) {
							FileWriter fw = new FileWriter(file, true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write("Identity: " + identity + " " + "Time:" + temp);
							bw.newLine();
							bw.close();
							fw.close();
							byte[] ok = "OK".getBytes();
							DatagramPacket dtp = new DatagramPacket(ok, ok.length, add, pi.getPort());
							ds.send(dtp);
							resume();
						}
					}
				} else {
					File file = new File("teste.txt");
					if (file.exists()) {
						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write("Identity: " + identity + " " + "Time:" + temp);
						bw.newLine();
						bw.close();
						fw.close();
						resume();

					}
				}
			}

			/*
			 * int u = 0; while (u < cont) { byte[] receivMsn = new byte[1024];
			 * 
			 * pkg = new DatagramPacket(receivMsn, receivMsn.length); ds.receive(pkg); //
			 * comparar se for Ok String var = new String(pkg.getData(), 0,
			 * pkg.getLength());
			 * 
			 * if (var != "OK") { int comp = Integer.parseInt(var); if (comp < temp) {
			 * byte[] sendMsn = "OK".getBytes(); DatagramPacket DestMsn = new
			 * DatagramPacket(sendMsn, sendMsn.length, add, pkg.getPort());
			 * ds.send(DestMsn); } else { list.add(pkg.getPort());
			 * 
			 * File file = new File("teste.txt"); if (file.exists()) { FileWriter fw = new
			 * FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fw);
			 * bw.write("Identity: " +identity + " " +"Time:"+ temp); bw.newLine();
			 * bw.close(); fw.close();
			 * 
			 * }
			 * 
			 * for (Integer c : list) { byte[] sendMsn = "OK".getBytes(); DatagramPacket
			 * DestMsn = new DatagramPacket(sendMsn, sendMsn.length, add, c);
			 * ds.send(DestMsn); }
			 * 
			 * 
			 * } }u++; }
			 */

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
