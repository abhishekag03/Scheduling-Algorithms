package CAOS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {

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
		int time_quantum = 2;
		tat[0]=(int) ProcessList.get(0).burst_time;
		int comp=0;
		while (ct < Main.time && ReadyQueue.size()>0) {
//			System.out.println("inf1" + ct + " " + Main.time );
			while (queuecnt<Main.num_proc && ProcessList.get(queuecnt).arrival_time<ct) {
				ReadyQueue.add(ProcessList.get(queuecnt));
				queuecnt++;
//				System.out.println("inf2");
			}
			int wait_time = 0;
			for (int i=0;i<ReadyQueue.size();i++) {
				Process proc = ReadyQueue.poll();
//				proc.turn_around_time+=wait_time;
				if (proc.burst_time>time_quantum) {
					proc.burst_time-=time_quantum;
					ReadyQueue.add(proc);
					ct+=time_quantum;
					//					proc.turn_around_time+=proc.burst_time;
				}
				else {
					ct+=proc.burst_time;
					proc.burst_time=0;
					proc.turn_around_time = ct;
//					System.out.println("ct = " + ct);
					comp++;
				}
			}

			//			System.out.println(proc);
			//			waittime[p] = waittime[p-1] + (int)proc.burst_time;
			//			waittime[p] = waittime[p] - (int)proc.arrival_time;
			//			total_waittime+=waittime[p];
			//			tat[p]=waittime[p]+(int)proc.burst_time;
//			p++;
//			ct+=ProcessList.get(p-1).burst_time;
		}

		//		System.out.println("ct = " + ct);
		if (comp<Main.num_proc) {
			System.out.println((Main.num_proc-comp) + " processes could not be executed");

		}
		for (int i=0 ; i<ProcessList.size(); i++) {
			 ProcessList.get(i).turn_around_time -= ProcessList.get(i).arrival_time;
		}
		
		int wait_time=0;
		for (int i=0 ; i<ProcessList.size(); i++) {
			wait_time += ProcessList.get(i).turn_around_time - ProcessList.get(i).burst_time2;
		}
		int awt = wait_time/comp;
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
