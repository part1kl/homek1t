/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.collections.hashmaps;

import net.part1kl.homek1t.core.util.exceptions.InstanceNotFoundException;
import net.part1kl.homek1t.core.util.logging.LogType;
import net.part1kl.homek1t.core.util.logging.Logger;

/** Subclass of HkHashMap that uses {@code Nameable} objects as values to automatically assign keys
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
 * 			<td> net.part1kl.homek1t.core.util.collections.hashmaps </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Class: </th>
 * 			<td> HkHashMapNameable </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Version: </th>
 * 			<td> 0.1 </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Date Created: </th>
 * 			<td> Aug 27, 2018 </td>
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
public class HkHashMapNameable<V> extends HkHashMap<String, V> {

	/**Required to remove compilation warning*/
	private static final long serialVersionUID = 1L;

	
	
	
	/**makes a new entry to the map using this value and its name as the key*/
	public HkHashMapNameable<V> put(V value) {
		NamedVPair<V> pair;
		try {
			pair = new NamedVPair<V>(value);
			put(pair.getKey(), pair.getValue());
		} catch (InstanceNotFoundException e) { 
			Logger.log(LogType.ERROR, "HkHashMapNameable: "+e.getMessage());
		}
		return this;
	}
}
