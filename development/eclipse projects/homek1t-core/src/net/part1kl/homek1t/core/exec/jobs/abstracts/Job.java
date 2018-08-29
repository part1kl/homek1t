/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.exec.jobs.abstracts;

import java.util.Date;
import java.util.concurrent.Callable;

import net.part1kl.homek1t.core.exec.jobs.type.JobType;
import net.part1kl.homek1t.core.util.nameable.Date2;
import net.part1kl.homek1t.core.util.nameable.Nameable;

/** Jobs are pre-made functions that require their own thread to run. Job takes no parameters and returns nothing
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
 * 			<td> net.part1kl.homek1t.core.exec.jobs.abstracts </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Class: </th>
 * 			<td> Job </td>
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
public abstract class Job extends Date2 implements Callable<Job>, Nameable{

	/**Whether the Job is still running*/
	private boolean RUNNING;
	/**Set the job to running*/
	protected void setRunning() {RUNNING = true;}
	/**Sets the job to not running. Called from within {@code execute()}*/
	public void breakRun() {RUNNING = false;}
	/**@return true if the Job is not yet complete*/
	public boolean isRunning() {return RUNNING;}
	
	
	/**Determines whether the job is instant, scheduled, or looping*/
	private JobType TYPE;
	/**Sets the job type*/
	public void setType(JobType type) {TYPE=type;}
	/**@return the job type*/
	public JobType getJobType() {return TYPE;}
	
	
	/**If the job is scheduled, this is the millisecond count from Date.getTime(). If the job is looping, this is the delay time between executions of the job*/
	private long TYPE_INFO;
	/**Sets the type-specific info*/
	public void setTypeInfo(long info) {TYPE_INFO = info;}
	/**@return the type-specific info*/
	public long getTypeInfo() {return TYPE_INFO;}
	
	
	
	
	@Override
	public Job call() {
		setRunning();
		execute();
		return this;
	}
	
	
	/**Automatically called when the Job is executed.*/
	public abstract void execute();
}
