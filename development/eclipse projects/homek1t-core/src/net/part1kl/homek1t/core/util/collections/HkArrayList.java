/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.collections;

import java.util.ArrayList;

/** Customized ArrayList that includes some extra functionality
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
 * 			<td> HkArrayList </td>
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
public class HkArrayList<E> extends ArrayList<E> {

	/**Default constructor. See default ArrayLists*/
	public HkArrayList() {super();}
	
	/**Creates a default HkArrayList and fills it.
	 * 
	 * @param fill data to be added (in the order it is given)
	 */
	public HkArrayList(E... fill) {
		super();
		for(E f: fill) {
			add(f);
		}
	}
	
	/**Returns everything as an array cast to the type declared when making the HkArrayList
	 * 
	 * @return type-formatted array of all stored objects
	 */
	public E[] toTypeArray() {
		E[] arr = (E[])toArray();
		return arr;
	}
}
