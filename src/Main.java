import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static List<ProcessIdentity> cont = new ArrayList<ProcessIdentity>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Process t = new Process(1,5,6001);
		Process t2 = new Process(2,3,6002);
		Process t3= new Process(3,  1,6003);
		
		
		  t.start();
		  
		  t2.start();
		  
		  t3.start();
		 
		  try {
			t.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

}
