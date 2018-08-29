/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.collections.hashmaps;

import net.part1kl.homek1t.core.util.exceptions.InstanceNotFoundException;
import net.part1kl.homek1t.core.util.nameable.Nameable;

/** Sublcass of KVPair that uses String as the type for the key. Values passed in must be an instance of Nameable
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
 * 			<td> NamedVPair </td>
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
public class NamedVPair<V> extends KVPair<String, V> {

	/**Creates a new NamedVPair<V>, which extends KVPair<String,V>
	 * 
	 * @param value
	 * @throws InstanceNotFoundException
	 */
	public NamedVPair(V value) throws InstanceNotFoundException {
		super( (value instanceof Nameable ? ((Nameable) value).getName():null), value);
		if(getKey().equals(null))
			throw new InstanceNotFoundException("Value parameter is not an instance of Nameable.");
	}

}
