/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.exec.jobs.abstracts;

import net.part1kl.homek1t.core.util.collections.HkArrayList;

/** Jobs are pre-made functions that require their own thread to run. JobWithParameters takes parameters but returns nothing
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
 * 			<td> JobWithParameters </td>
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
public abstract class JobWithParameters<P> extends Job{

	/**Stores parameters for execution*/
	private HkArrayList<P> PARAMETERS;
	/**Set the parameters for execution*/
	public void setParameters(HkArrayList<P> parameters) { PARAMETERS = parameters;}
	
	
	/**Inherited from Job. */
	@Override
	public void execute() {
		submit(PARAMETERS);
	}
	/**Automatically called when the Job is executed.
	 * 
	 * @param parameters The parameters to be used during execution
	 */
	public abstract void submit(HkArrayList<P> parameters);
}
