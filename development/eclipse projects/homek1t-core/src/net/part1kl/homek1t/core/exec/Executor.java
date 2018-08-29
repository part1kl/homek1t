/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.exec;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import net.part1kl.homek1t.core.exec.jobs.abstracts.Job;
import net.part1kl.homek1t.core.exec.jobs.abstracts.JobWithParameters;
import net.part1kl.homek1t.core.exec.jobs.abstracts.JobWithParametersAndReturns;
import net.part1kl.homek1t.core.exec.jobs.abstracts.JobWithReturn;
import net.part1kl.homek1t.core.util.collections.HkArrayList;
import net.part1kl.homek1t.core.util.collections.HkConcurrentQueue;
import net.part1kl.homek1t.core.util.collections.hashmaps.HkHashMap;
import net.part1kl.homek1t.core.util.logging.LogType;
import net.part1kl.homek1t.core.util.logging.Logger;

/** Manages execution and data return of all Jobs and Tasks being submitted, and handles their returning data
 * 
 * 
 * <br><br>
 * <style>
 *		table {
 *  		border-collapse: collapse;
 *		}
 *		table, tr {
 *			border: 1px solid black;
 *		}
 *	</style>
 * <table>
 * 		<tr>
 * 			<th> Project: </th>
 * 			<td> homek1t-core </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Package: </th>
 * 			<td> net.part1kl.homek1t.core.exec </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Class: </th>
 * 			<td> Executor </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Version: </th>
 * 			<td> 0.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Date Created: </th>
 * 			<td> Aug 26, 2018 </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Author: </th>
 * 			<td> part1kl </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Year: </th>
 * 			<td> 2018 </td>
 * 		</tr>
 * </table>
 */
public class Executor {
	
	/**Executor of all jobs*/
	private volatile static JobExecutor EXECUTOR;
	/**Thred to run {@code EXECUTOR}*/
	private volatile static Thread EXEC_THREAD;
	/**Determines how many jobs can be running at the same time*/
	private volatile static int MAX_RUNNING_JOBS;
	/**Starts {@code EXECUTOR} Job*/
	public synchronized static void initExecutor(int maxRunningJobs) {
		Executor obj = new Executor();
		EXECUTOR = obj.new JobExecutor();
		EXECUTOR.setParameters(new HkArrayList<Integer>(maxRunningJobs));
		
		MAX_RUNNING_JOBS = maxRunningJobs;
		RUNNING = true;
		ACCEPTING_NEW = true;
		JOBS_AWAITING_EXECUTION = new HkConcurrentQueue<Job>();
		JOBS_AWAITING_EXECUTION_IDS = new HkConcurrentQueue<Integer>();
		JOB_HISTORY = new HkConcurrentQueue<Job>();
		EXECUTED_JOB_MAP = new HkHashMap<Integer, HkArrayList<Job>>();
		RUNNING_JOB_COUNT = new AtomicInteger(0);
		INSTANT_JOB_COUNT = new AtomicInteger(0);
		SCHEDULED_JOB_COUNT = new AtomicInteger(0);
		LOOPING_JOB_COUNT = new AtomicInteger(0);
		
		FutureTask<Job> execFuture = new FutureTask<Job>(EXECUTOR);
		EXEC_THREAD = new Thread(execFuture);
		EXEC_THREAD.start();
	}
	
	
	
	
	/**Whether the Executor is running*/
	private volatile static boolean RUNNING;
	/**Whether the Executor is accepting new jobs to the queue*/
	private volatile static boolean ACCEPTING_NEW;
	
	
	
	
	/**Queue holding jobs that are waiting to be executed*/
	private volatile static HkConcurrentQueue<Job> JOBS_AWAITING_EXECUTION;
	
	
	/**Queue holding unique IDs. Order in the queue matches perfectly with {@code JOBS_AWAITING_EXECUTION}*/
	private volatile static HkConcurrentQueue<Integer> JOBS_AWAITING_EXECUTION_IDS;
	/**Counter used to assign unique IDs when adding a job to the queue*/
	private volatile static int JAEID_COUNTER = 0;
	
	
	/**Queue holding jobs that have already been executed*/
	private volatile static HkConcurrentQueue<Job> JOB_HISTORY;
	
	
	/**Holds copies of jobs (or sets of jobs if job is looping) that return values, using their IDs as keys so jobs that created them can retrieve the returned data*/
	private volatile static HkHashMap<Integer,HkArrayList<Job>> EXECUTED_JOB_MAP;
	
	
	
	
	/**If the Executor is accepting new jobs, it adds the passed job to the execution queue, and returns the ID of the job*/
	public synchronized static int addJob(Job job) {
		Logger.log(LogType.DEBUG, "Job Adding");
		if(ACCEPTING_NEW) {
			int id = JAEID_COUNTER++;
			JOBS_AWAITING_EXECUTION.offer(job);
			JOBS_AWAITING_EXECUTION_IDS.offer(id);
			Logger.log(LogType.DEBUG, "Job Added: "+JOBS_AWAITING_EXECUTION.size());
			return id;
		}
		else return -1;
		
	}
	
	
	/**Adds a job (or set of jobs if job is looping) to {@code EXECUTED_JOB_MAP}*/
	protected synchronized static void addRecord(HkArrayList<Job> jobs, int ID) {
		JOB_HISTORY.offer(jobs.get(0));
		if(jobs.get(0) instanceof JobWithReturn || jobs.get(0) instanceof JobWithParametersAndReturns) {
			EXECUTED_JOB_MAP.put(ID, jobs);
		}
	}
	/**@return true if a job (or set of jobs if job is looping) has been recorded in {@code EXECUTED_JOB_MAP}*/
	public synchronized static boolean checkRecord(int ID) {
		return EXECUTED_JOB_MAP.containsKey(ID);
	}
	/**@return the asked for job (or set of jobs if job is looping) from the record using the given ID, and removes it from the record*/
	public synchronized static HkArrayList<Job> getRecord(int ID) {
		return EXECUTED_JOB_MAP.pull(ID);
	}
	
	
	
	
	/**Currently running jobs. Includes scheduled jobs only when they're running, not when they're waiting*/
	private static volatile AtomicInteger RUNNING_JOB_COUNT;
	/**Currently active instant jobs*/
	private static volatile AtomicInteger INSTANT_JOB_COUNT;
	/**Currently waiting scheduled jobs. Only includes jobs that are waiting, not ones that are executing*/
	private static volatile AtomicInteger SCHEDULED_JOB_COUNT;
	/**Currently looping jobs*/
	private static volatile AtomicInteger LOOPING_JOB_COUNT;
	
	
	
	
	/**Causes the calling thread to wait*/
	private void hkWait(long millis) {try {wait(millis);} catch(Exception e) {Logger.log(LogType.ERROR, "Thread wait was interrupted.");}}
	/**Casues the calling thread to sleep*/
	private void hkSleep(long millis) {try {Thread.sleep(millis);} catch(Exception e) {Logger.log(LogType.ERROR, "Thread sleep was interrupted."); e.printStackTrace();}}
	
	
	
	
	/**
	 * Dedicated {@code JobWithParameters<Integer>} subclass that handles execution of jobs
	 * 
	 * 
	 * <br><br>
	 * <style>
	 *		table {
	 *  		border-collapse: collapse;
	 *		}
	 *		table, tr {
	 *			border: 1px solid black;
	 *		}
	 *	</style>
	 * <table>
	 * 		<tr>
	 * 			<th> Project: </th>
	 * 			<td> homek1t-core </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Package: </th>
	 * 			<td> net.part1kl.homek1t.core.exec </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Class: </th>
	 * 			<td> JobExecutor </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Version: </th>
	 * 			<td> 0.1 </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Date Created: </th>
	 * 			<td> Aug 28, 2018 </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Author: </th>
	 * 			<td> part1kl </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Year: </th>
	 * 			<td> 2018 </td>
	 * 		</tr>
	 * </table>
	 */
	private class JobExecutor extends JobWithParameters<Integer> {

		@Override
		public String getName() {
			return "JobExecutor";
		}

		
		@Override
		public void submit(HkArrayList<Integer> maxRunningJobs) {
			while(RUNNING) {
				while(Executor.JOBS_AWAITING_EXECUTION.isEmpty()) { //TODO: This loop doesn't see when a job is added
					Logger.log(LogType.DEBUG, "Sleeping: "+JOBS_AWAITING_EXECUTION.size());
					hkSleep(500);
				}
				Job execJob = JOBS_AWAITING_EXECUTION.poll();
				int execJobID = JOBS_AWAITING_EXECUTION_IDS.poll();
				
			}
		}
		
	}
	
	/**
	 * Runnable that actually executes the job so the JobExecutor can continue processing waiting jobs
	 * 
	 * 
	 * <br><br>
	 * <style>
	 *		table {
	 *  		border-collapse: collapse;
	 *		}
	 *		table, tr {
	 *			border: 1px solid black;
	 *		}
	 *	</style>
	 * <table>
	 * 		<tr>
	 * 			<th> Project: </th>
	 * 			<td> homek1t-core </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Package: </th>
	 * 			<td> net.part1kl.homek1t.core.exec </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Class: </th>
	 * 			<td> Exec </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Version: </th>
	 * 			<td> 0.1 </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Date Created: </th>
	 * 			<td> Aug 28, 2018 </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Author: </th>
	 * 			<td> part1kl </td>
	 * 		</tr>
	 * 		<tr>
	 * 			<th> Year: </th>
	 * 			<td> 2018 </td>
	 * 		</tr>
	 * </table>
	 */
	private class Exec implements Runnable {

		private Job JOB;
		private int ID;
		
		/**Creates a new Exec with the job and ID (only used if job returns)
		 * 
		 * @param job Job to be executed
		 * @param id ID of the job
		 */
		public Exec(Job job, int id) {JOB = job; ID = id;}
		

		public void run() {			
			try {
				FutureTask<Job> waiter;
				Thread thread;
				HkArrayList<Job> record = new HkArrayList<Job>();
				
				switch(JOB.getJobType()) {
				case INSTANT:
					INSTANT_JOB_COUNT.incrementAndGet();
					RUNNING_JOB_COUNT.incrementAndGet();
					
					waiter = new FutureTask<Job>(JOB);
					thread = new Thread(waiter);
					thread.start();
					while(!waiter.isDone()) hkWait(500);
					
					record.add(waiter.get());
					
					RUNNING_JOB_COUNT.decrementAndGet();
					INSTANT_JOB_COUNT.decrementAndGet();
					break;
				case SCHEDULED:
					SCHEDULED_JOB_COUNT.incrementAndGet();
					
					while(new Date().getTime()<JOB.getTypeInfo())hkWait(500);
					
					SCHEDULED_JOB_COUNT.decrementAndGet();
					RUNNING_JOB_COUNT.incrementAndGet();
					
					waiter = new FutureTask<Job>(JOB);
					thread = new Thread(waiter);
					thread.start();
					while(!waiter.isDone()) hkWait(500);
					
					record.add(waiter.get());
					
					RUNNING_JOB_COUNT.decrementAndGet();
					break;
				case LOOPING:
					LOOPING_JOB_COUNT.incrementAndGet();
					RUNNING_JOB_COUNT.incrementAndGet();
					
					boolean run = true;
					long last = System.currentTimeMillis();
					while(run) {
						waiter = new FutureTask<Job>(JOB);
						thread = new Thread(waiter);
						thread.start();
						while(!waiter.isDone()) hkWait(500);
						
						Job lastIteration = waiter.get();
						record.add(lastIteration);
						
						if(!lastIteration.isRunning()) {run = false; break;}
						
						while(System.currentTimeMillis()-last < JOB.getTypeInfo()) hkWait(50);
					}
					
					RUNNING_JOB_COUNT.decrementAndGet();
					LOOPING_JOB_COUNT.decrementAndGet();
					break;
				}
				
				Executor.addRecord(record, ID);
			} catch (InterruptedException | ExecutionException e) {
				Logger.log(LogType.ERROR, "Exec: Failed to get returned job from execution.");
			}
		}
	}
}
