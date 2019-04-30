package CAOS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FCFS {
	
	public void method(ArrayList<Process> ProcessList) {
		int size = ProcessList.size();
		int[] waittime = new int[size];
		int[] tat = new int[size];
		waittime[0] = 0;
		int count = 0;
		Queue<Process> ReadyQueue = new LinkedList<Process>();
		ReadyQueue.add(ProcessList.get(0));
		int first_arrival_time = (int) ProcessList.get(0).arrival_time;
		int ct = first_arrival_time;
		int p = 1;
		int total_waittime = 0;
		int queuecnt = 1;
		tat[0]=(int) ProcessList.get(0).burst_time;
		while (ct < Main.time && p<Main.num_proc-1) {
			while (queuecnt<Main.num_proc && ProcessList.get(queuecnt).arrival_time<ct) {
				ReadyQueue.add(ProcessList.get(queuecnt));
				queuecnt++;
			}
			Process proc = ReadyQueue.poll();
//			System.out.println(proc);
			waittime[p] = waittime[p-1] + (int)proc.burst_time;
			waittime[p] = waittime[p] - (int)proc.arrival_time;
			total_waittime+=waittime[p];
			proc.turn_around_time = (int) (ct + proc.burst_time - proc.arrival_time);
			tat[p]=waittime[p]+(int)proc.burst_time;
			p++;
			ct+=proc.burst_time;
		}
		
//		System.out.println("ct = " + ct);
		if (p<Main.num_proc) {
			System.out.println((Main.num_proc-p) + " processes could not be executed");

		}
		
		int awt = total_waittime/p;
		System.out.println("avg wait time= "+ awt);
		for (int i=0;i<Main.num_proc;i++) {
			System.out.println("Turn around time for process " + (i+1) + " = " + ProcessList.get(i).turn_around_time);
		}
//		System.out.println(ProcessList);
//		System.out.println("poisson");
		
//		for (int i = 0 ; i<100 ; i++) {
//			System.out.println(waittime[i]);
//		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
