/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.exec.jobs.abstracts;

/** Jobs are pre-made functions that require their own thread to run. JobWithReturn does not take parameters but returns an object of type {@code R}
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
 * 			<td> JobWithReturn </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Version: </th>
 * 			<td> 0.1 </td>
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
public abstract class JobWithReturn<R> extends Job {

	/**Data returned from execution*/
	private R RETURN;
	/**Retrieves the data returned from execution*/
	public R getReturn() {return RETURN;}
	
	
	/**Inherited from Job*/
	@Override
	public void execute() {
		RETURN = request();
	}
	/**Automatically called when the Job is executed.
	 * 
	 * @param parameters The parameters to be used during execution
	 */
	public abstract R request();
}
