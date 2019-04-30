package CAOS;
import java.util.*;

public class Main {
	public static Random rand = new Random();
	public static int time, num_proc;
	public static double getNext() {
		double lambda = 0.10;
//		Random rand = new Random();
		return  Math.log(1-rand.nextDouble())/(-lambda);
	}

	private static int getPoissonRandom(double mean) {
		Random r = new Random();
		double L = Math.exp(-mean);
		int k = 0;
		double p = 1.0;
		do {
			p = p * r.nextDouble();
			k++;
		} while (p > L); 	
		return k - 1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner( System.in );
		System.out.println("Enter total running time in seconds");
		Queue<Process> ReadyQueue = new LinkedList<Process>();
		time = sc.nextInt();
		ArrayList<Process> ProcessList = new ArrayList<Process>();
		num_proc = 100;
		for (int i = 0 ; i<num_proc ; i++) {
			Process p = new Process(getPoissonRandom(3),getNext());
			ProcessList.add(p);
			
//			System.out.println(getNext());
		}
		Collections.sort(ProcessList);
//		FCFS obj = new FCFS();
//		obj.method(ProcessList);
//		SJF obj2 = new SJF();
//		obj2.method(ProcessList);
//		Priority obj3 = new Priority();
//		obj3.method(ProcessList);
		
//		RoundRobin obj4 = new RoundRobin();
//		obj4.method(ProcessList);
		
		MultiLevel obj5 = new MultiLevel();
		obj5.method(ProcessList);
		
		Feedback obj6 = new Feedback();
		obj6.method(ProcessList);
	}

}
