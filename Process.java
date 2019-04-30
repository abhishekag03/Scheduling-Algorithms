package CAOS;

import java.util.Random;

public class Process implements Comparable<Process> {
	double arrival_time;
	double burst_time;
	int turn_around_time;	
	int priority;
	double burst_time2;
	
	Random random = new Random();
	
	Process(double t1, double t2){
		arrival_time = t1;
		burst_time = t2;
		priority = random.nextInt(10) + 1;
		burst_time2 = t2;
	}
	@Override
	public String toString() {
		return "Process [arrival_time=" + arrival_time + ", burst_time=" + burst_time + "]";
	}
	@Override
	public int compareTo(Process P) {
		return (int)(this.arrival_time - P.arrival_time);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}


}
