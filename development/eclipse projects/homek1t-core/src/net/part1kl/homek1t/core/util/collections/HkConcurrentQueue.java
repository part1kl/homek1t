/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.collections;

import java.util.concurrent.ConcurrentLinkedQueue;

/** Customized ConcurrentLinkedQueue that includes some extra functionality
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
 * 			<td> homek1t-testing </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Package: </th>
 * 			<td> net.part1kl.homek1t.core.util.collections </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Class: </th>
 * 			<td> HkConcurrentQueue </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Version: </th>
 * 			<td> 0.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Date Created: </th>
 * 			<td> Aug 25, 2018 </td>
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
public class HkConcurrentQueue<E> extends ConcurrentLinkedQueue<E>{
	
	/**Copies this HkConcurrentQueue and leaves it intact
	 * 
	 * @return copy of this HkConcurrentQueue
	 */
	public synchronized HkConcurrentQueue<E> copy(){
		HkConcurrentQueue<E> ret = this;
		return ret;
	}
	
	/**Copies this HkConcurrentQueue and leaves it empty
	 * 
	 * @return copy of this HkConcurrentQueue
	 */
	public synchronized HkConcurrentQueue<E> copyAndFlush() {
		HkConcurrentQueue<E> ret = new HkConcurrentQueue<E>();
		while(!this.isEmpty())
			ret.add(this.poll());
		return ret;
	}
	
	/**Copies the contents of this HkConcurrentQueue to an HkArrayList and leaves it intact
	 * 
	 * @return copy of this HkConcurrentQueue
	 */
	public synchronized HkArrayList<E> toArrayList(){
		HkArrayList<E> ret = new HkArrayList<E>();
		HkConcurrentQueue<E> backup = this;
		while(!this.isEmpty())
			ret.add(this.poll());
		merge(backup);
		return ret;
	}
	
	/**Copies the contents of this HkConcurrentQueue to an HkArrayList and leaves it empty
	 * 
	 * @return copy of this HkConcurrentQueue
	 */
	public synchronized HkArrayList<E> toArrayListFlush(){
		HkArrayList<E> ret = new HkArrayList<E>();
		while(!this.isEmpty())
			ret.add(this.poll());
		return ret;
	}
	
	
	/**Adds all objects from an HkConcurrentQueue onto the tail of this one. This method does not remove any objects from the provided queue
	 * 
	 * @param queue objects to be added
	 */
	public synchronized void append(HkConcurrentQueue<E> queue) {
		HkConcurrentQueue<E> clone = queue;
		while(!clone.isEmpty())
			this.add(clone.poll());
	}
	
	/**Adds all objects from an HkArrayList onto the tail of this queue in the order they were placed in the HkArrayList
	 * 
	 * @param array objects to be added
	 */
	public synchronized void append(HkArrayList<E> array) {
		for(E o : array) {
			this.add(o);
		}
	}
	
	/**Adds all objects from an HkConcurrentQueue onto the tail of this one. This method removes all objects from the provided queue
	 * 
	 * @param queue objects to be added
	 */
	public synchronized void merge(HkConcurrentQueue<E> queue) {
		while(!queue.isEmpty())
			this.add(queue.poll());
	}
}
