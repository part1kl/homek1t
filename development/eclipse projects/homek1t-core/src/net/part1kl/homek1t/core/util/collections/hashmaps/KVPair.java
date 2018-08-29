/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.collections.hashmaps;

import net.part1kl.homek1t.core.util.exceptions.InstanceNotFoundException;
import net.part1kl.homek1t.core.util.nameable.IDCoded;
import net.part1kl.homek1t.core.util.nameable.Nameable;

/** KVPair and subclasses are partner classes to HkHashMap, which allows passing in KVPairs at declaration
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
 * 			<td> net.part1kl.homek1t.core.util.collections </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Class: </th>
 * 			<td> KVPair </td>
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
public class KVPair<K,V> {

	/**Key for this pair*/
	private K KEY;
	/**@return key for this pair*/
	public K getKey() {return KEY;}
	
	/**Value for this pair*/
	private V VALUE;
	/**@return value for this pair*/
	public V getValue() {return VALUE;}
	
	
	
	
	/**Creates a new KVPair<K,V>
	 * 
	 * @param key
	 * @param value
	 */
	public KVPair(K key, V value) {
		KEY = key;
		VALUE = value;
	}
}
