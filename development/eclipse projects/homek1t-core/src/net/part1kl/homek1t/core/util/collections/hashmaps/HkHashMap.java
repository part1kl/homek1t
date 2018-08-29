/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.collections.hashmaps;

import java.util.HashMap;

import net.part1kl.homek1t.core.util.collections.HkArrayList;

/** Customized HashMap that includes some extra functionality
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
 * 			<td> HkHashMap </td>
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
public class HkHashMap<K,V> extends HashMap<K,V>{
	
	/**Required to remove compilation warning*/
	private static final long serialVersionUID = 1L;

	/**Default constructor, makes an HkHashMap with an initial size of 0*/
	public HkHashMap() {
		super(0);
	}
	
	
	
	
	/**Makes an HkHashMap with an initial size of 0, and populates it with data from provided KVPairs
	 * 
	 * @param pairs KVPairs to be added to the map
	 */
	public HkHashMap(HkArrayList<KVPair<K,V>> pairs) {
		super(0);
		for(KVPair<K,V> pair : pairs)
			put(pair.getKey(), pair.getValue());
	}
	
	
	
	
	/**@return the value for given key, removing it from the HkHashMap*/
	public V pull(K key) {return remove(key);}
	
}
