package CAOS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MultiLevel {

	public void method(ArrayList<Process> ProcessList) {
		int size = ProcessList.size();
		int[] waittime = new int[size];
		int[] tat = new int[size];
		waittime[0] = 0;
		int count = 0;

		Queue<Process> ReadyQueue = new LinkedList<Process>();
		Queue<Process> SystemQueue = new LinkedList<Process>();
		Queue<Process> InteractiveQueue = new LinkedList<Process>();
		Queue<Process> BatchQueue = new LinkedList<Process>();

		ArrayList<Process> SystemList = new ArrayList<Process>();
		ArrayList<Process> InteractiveList= new ArrayList<Process>();
		ArrayList<Process> BatchList = new ArrayList<Process>();
		Random rand = new Random();

		for (int i=0; i<ProcessList.size(); i++) {
			int num = rand.nextInt(3)+1;
			if (num==1) {
				SystemList.add(ProcessList.get(i));
			}
			else if (num==2) {
				InteractiveList.add(ProcessList.get(i));
			}
			else {
				BatchList.add(ProcessList.get(i));
			}
		}

		SystemQueue.add(SystemList.get(0));
		int first_arrival_time = (int) ProcessList.get(0).arrival_time;
		int ct = first_arrival_time;
		int p = 1;
		int total_waittime = 0;
		int queuecnt = 1;
		int time_quantum = 2;
		waittime[0]=0;
		tat[0]=(int) ProcessList.get(0).burst_time;
		int comp=0;
		int SystemTime = Main.time/2;
		int InteractiveTime = (Main.time*3)/10;
		int BatchTime = (Main.time*2)/10;

		while (ct < SystemTime && SystemQueue.size() > 0) {
			//			System.out.println("inf1" + ct + " " + Main.time );
			while (queuecnt<SystemList.size() && SystemList.get(queuecnt).arrival_time<ct) {
				SystemQueue.add(SystemList.get(queuecnt));
				queuecnt++;
				//				System.out.println("inf2");
			}
			int wait_time = 0;
			for (int i=0;i<SystemQueue.size();i++) {
				Process proc = SystemQueue.poll();
				//				proc.turn_around_time+=wait_time;
				if (proc.burst_time>time_quantum) {
					proc.burst_time-=time_quantum;
					SystemQueue.add(proc);
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
		}
		System.out.println("comp1 = " + comp);
		if (ct < SystemTime) {
			InteractiveTime+=(SystemTime-ct);
		}
		
		InteractiveQueue.add(InteractiveList.get(0));
		ct = 0;queuecnt = 1;
		while (ct < InteractiveTime && InteractiveQueue.size()>0) {
			//			System.out.println("inf1" + ct + " " + Main.time );
			while (queuecnt<InteractiveList.size() && InteractiveList.get(queuecnt).arrival_time<ct) {
				InteractiveQueue.add(InteractiveList.get(queuecnt));
				queuecnt++;
				//				System.out.println("inf2");
			}
			int wait_time = 0;
			for (int i=0;i<InteractiveQueue.size();i++) {
				Process proc = InteractiveQueue.poll();
				//				proc.turn_around_time+=wait_time;
				if (proc.burst_time>time_quantum) {
					proc.burst_time-=time_quantum;
					InteractiveQueue.add(proc);
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
		}
		
		System.out.println("comp2 = " + comp);
		if (ct < InteractiveTime) {
			BatchTime+=(InteractiveTime-ct);
		}
//		int wait_time_queue3 = 0;
//		System.out.println("sixe = " + BatchList.size());
		BatchQueue.add(BatchList.get(0));
		
		ct = 0;queuecnt = 1;
		while (ct < BatchTime && p<BatchList.size()-1) {
			while (queuecnt<BatchList.size() && BatchList.get(queuecnt).arrival_time<ct) {
				BatchQueue.add(BatchList.get(queuecnt));
				queuecnt++;
//				System.out.println("added");
			}
			Process proc = BatchQueue.poll();
//			System.out.println(proc);
//			System.out.println("p = " + p );
//			System.out.println("wt 0 = " + waittime[0]);
//			System.out.println("bt =  " + proc.burst_time);
			waittime[p] = waittime[p-1] + (int)proc.burst_time;
			waittime[p] = waittime[p] - (int)proc.arrival_time;
			total_waittime+=waittime[p];
			proc.turn_around_time = (int) (ct + proc.burst_time - proc.arrival_time);
			tat[p]=waittime[p]+(int)proc.burst_time;
			p++;
			ct+=proc.burst_time;
			comp++;
		}

		System.out.println("comp3 = " + comp);
		//		System.out.println("ct = " + ct);
		if (comp<Main.num_proc) {
			System.out.println((Main.num_proc-comp) + " processes could not be executed");

		}
		for (int i=0 ; i<SystemList.size(); i++) {
			SystemList.get(i).turn_around_time -= SystemList.get(i).arrival_time;
		}
		for (int i=0 ; i<InteractiveList.size(); i++) {
			InteractiveList.get(i).turn_around_time -= InteractiveList.get(i).arrival_time;
		}
		int wait_time=0;
		for (int i=0 ; i<SystemList.size(); i++) {
			wait_time += SystemList.get(i).turn_around_time - SystemList.get(i).burst_time2;
		}
		for (int i=0 ; i<InteractiveList.size(); i++) {
			wait_time += InteractiveList.get(i).turn_around_time - InteractiveList.get(i).burst_time2;
		}
		wait_time+=total_waittime;
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
