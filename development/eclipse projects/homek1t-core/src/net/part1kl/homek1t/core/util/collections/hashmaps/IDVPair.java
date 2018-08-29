/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.collections.hashmaps;

import net.part1kl.homek1t.core.util.exceptions.InstanceNotFoundException;
import net.part1kl.homek1t.core.util.nameable.IDCoded;
import net.part1kl.homek1t.core.util.nameable.Nameable;

/** Sublcass of KVPair that uses Integer as the type for the key. Values passed in must be an instance of IDCoded
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
 * 			<td> IDVPair </td>
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
public class IDVPair<V> extends KVPair<Integer, V> {

	/**Creates a new IDVPair<V>, which extends KVPair<Integer,V>
	 * 
	 * @param value
	 * @throws InstanceNotFoundException
	 */
	public IDVPair(V value) throws InstanceNotFoundException {
		super( (value instanceof IDCoded ? ((IDCoded) value).getID():null), value);
		if(getKey().equals(null))
			throw new InstanceNotFoundException("Value parameter is not an instance of IDCoded.");
	}

}
