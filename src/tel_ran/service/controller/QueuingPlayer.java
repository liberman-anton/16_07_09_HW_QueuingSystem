package tel_ran.service.controller;

import tel_ran.generation.Generator;
import tel_ran.queues.EmptyQueueException;
import tel_ran.queues.LimitedQueue;
import tel_ran.queues.OutOfLimitException;
import tel_ran.service.Service;
import tel_ran.service.Task;

public class QueuingPlayer {
	static int probability = 50;
	static int minDuration = 20;
	static int maxDuration = 25;
	static int END_TIME = 1000;
	static int LIMIT = 100;
	static int N_TASKS = 10;
	
	public static void main(String[] args) {
		Generator generator = new Generator(minDuration,maxDuration,probability);
		Service service = new Service(N_TASKS);
		LimitedQueue<Task> queue = new LimitedQueue<Task>(LIMIT);
		int currenIter = 0;
		int rejected = 0;
		int waitingTime = 0;
		int idleTime = 0;
		int numberTask = 0;
		while(currenIter != END_TIME){
			Task task = generator.generate(currenIter);
			if(task != null){
				try {
						queue.add(task);
					} catch (OutOfLimitException e) {
						rejected++;
					}
			}
			Task[] taskInService = service.takeTask(currenIter);
			int nTasksInServices = 0;;
			for(int i = 0; i < N_TASKS; i++){
				if(taskInService[i] != null){
					nTasksInServices++;
					if(taskInService[i].getStartTime() + taskInService[i].getDuration() == currenIter){
						numberTask++;
					}
				}
			}
			if(nTasksInServices == N_TASKS) waitingTime++;
			if(service.isEmpty()){
				try{
					Task taskOffered = queue.offer();
					service.addTask(taskOffered, currenIter);
				}catch(EmptyQueueException e){
					idleTime++;
				}
			}
			currenIter++;
		}
		if(numberTask != 0) 
			System.out.println("waitingTime/numberTask: " + (double)waitingTime/numberTask);
		System.out.println("waitingTime: " + waitingTime);
		System.out.println("numberTask: " + numberTask);
		System.out.println("idleTime: " + idleTime);
		System.out.println("rejected: " + rejected);
	}
}
