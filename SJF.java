package CAOS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SJF {
	public void method(ArrayList<Process> ProcessList) {
		int size = ProcessList.size();
		int[] waittime = new int[size];
		int[] tat = new int[size];
		waittime[0] = 0;
		int count = 0;
		PriorityQueue<Process> ReadyQueue = new PriorityQueue<Process>((x,y) ->(int)( x.burst_time-y.burst_time));
//		Queue<Process> ReadyQueue = new LinkedList<Process>();
		ReadyQueue.add(ProcessList.get(0));
		int first_arrival_time = (int) ProcessList.get(0).arrival_time;
		int ct = first_arrival_time;
		int p = 1;
		int total_waittime = 0;
		int queuecnt = 1;
//		System.out.println(ProcessList);
		while (ct < Main.time && p<Main.num_proc-1) {
			while (queuecnt<Main.num_proc && ProcessList.get(queuecnt).arrival_time<ct) {
				ReadyQueue.add(ProcessList.get(queuecnt));
				queuecnt++;
			}
			Process proc = ReadyQueue.poll();
//			System.out.println(proc);
			tat[p] = (int) (proc.burst_time + waittime[p]);
			proc.turn_around_time = (int) (ct + proc.burst_time - proc.arrival_time);
			if (proc.turn_around_time<0) {
				System.out.println("negative");
				System.out.println(proc);
			}
			waittime[p+1] = tat[p];
//			waittime[p] = waittime[p-1] + (int)proc.burst_time;
//			waittime[p] = waittime[p] - (int)proc.arrival_time;
			total_waittime+=waittime[p];
			p++;
			ct+=proc.burst_time;
		}
		if (p<Main.num_proc) {
			System.out.println((Main.num_proc-p) + " processes could not be executed");

		}
//		System.out.println("ct = " + ct);
		int awt = total_waittime/Main.num_proc;
		System.out.println("avg wait time= "+ awt);
		
//		System.out.println(ProcessList);
//		System.out.println("poisson");
		
		for (int i = 0 ; i<Main.num_proc ; i++) {
//			System.out.println("Turn around time for process " + (i+1) + " = " + tat[i]);
			System.out.println("Turn around time for process " + (i+1) + " = " +(int)( ProcessList.get(i).turn_around_time));
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
