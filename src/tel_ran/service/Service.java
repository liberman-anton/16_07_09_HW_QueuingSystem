package tel_ran.service;

public class Service {
Task[] task;
int nTask;
	
	public Service(int nTask) {
	this.nTask = nTask;
	task = new Task[nTask];
}

	public boolean isEmpty(){
		for(int i = 0; i < nTask; i++){
			if(this.task[i] == null) return true;
		}
		return false;
	}
	
	public Task[] takeTask(int time){
		Task[] res = new Task[nTask];
		int j = 0;
		for(int i = 0; i < nTask; i++){
			if((this.task[i] != null) && (time <= this.task[i].startTime + this.task[i].duration))
				res[j++] = this.task[i] ;
			if((this.task[i] != null) && (time == this.task[i].startTime + this.task[i].duration + 1)) 
					this.task[i] = null;
		}
	return res;
		
	}
	public boolean addTask(Task task, int time){
		if(this.isEmpty()){
			for(int i = 0; i < nTask; i++){
				if(this.task[i] == null){
					this.task[i] = task;
					this.task[i].setStartTime(time);
					this.task[i].setInService(true);
					 return true;
				}
			}
		}
		return false;
	}
}
