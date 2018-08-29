/**
 * See README for licensing information
*/
package net.part1kl.homek1t.core.util.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.part1kl.homek1t.core.util.collections.HkArrayList;
import net.part1kl.homek1t.core.util.collections.HkConcurrentQueue;

/** Parent class for loggers. Please use StandardLog, ErrorLog, or DebugLog if you want to see any logs
 * 
 * TODO: add Job creation when enabling log types
 * TODO: add Email parameter to enableEmailLogging()
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
 * 			<td> net.part1kl.homek1t.core.util.logging </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Class: </th>
 * 			<td> Logger </td>
 * 		</tr>
 * 		<tr>
 * 			<th> Version: </th>
 * 			<td> 0.1 </td>
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
public abstract class Logger {

	/**Whether the Logger is currently recording logs*/
	private volatile static boolean LOGGING = false;
	/**Enable log recording*/
	public static void enableLogging() {LOGGING=true;}
	/**Disable log recording*/
	public static void disableLogging() {LOGGING=false;}
	/**Returns true if the Logger is currently recording logs*/
	public static boolean isLogging() {return LOGGING;}
	
	
	/**Whether to send logs to the system console*/
	private static boolean LOG_TO_CONSOLE = false;
	/**Enables logging to the system console*/
	public static void enableConsoleLogging() {LOG_TO_CONSOLE = true;}
	/**Disables logging to the system console*/
	public static void disableConsoleLogging() {LOG_TO_CONSOLE = false;}
	/**Returns true if sending logs to system console*/
	public static boolean isConsoleLogging() {return LOG_TO_CONSOLE;}
	
	
	/**Whether to send logs as emails*/
	private static boolean LOG_TO_EMAIL = false;
	/**Enables sending logs as emails
	 * 
	 * @param minPerEmail number of minutes between each log email
	 */
	public static void enableEmailLogging(double minPerEmail) {
		MINUTES_PER_EMAIL = minPerEmail;
		LOG_TO_EMAIL = true;
	}
	/**Disables sending logs as emails*/
	public static void disableEmailLogging() {LOG_TO_EMAIL = false;}
	/**Returns true if sending logs as emails*/
	public static boolean isEmailLogging() {return LOG_TO_EMAIL;}
	
	/**Number of minutes between each log email*/
	private static double MINUTES_PER_EMAIL;
	/**@return number of minutes between each log email*/
	public static double getMinPerEmail() {return MINUTES_PER_EMAIL;}
	
	/**Holds all log messages until they are emailed*/
	private volatile static HkConcurrentQueue<String> EMAIL_LOGS = new HkConcurrentQueue<String>();
	/**Adds a log to EMAIL_LOGS
	 * 
	 * @param log log to be added
	 */
	private synchronized static void addEmailLog(String log) {EMAIL_LOGS.add(log);}
	/**Empties logs from EMAIL_LOGS into an HkArrayList
	 * 
	 * @return HkArrayList containing all logs in chronological order
	 */
	public synchronized static HkArrayList<String> getEmailLogs(){return EMAIL_LOGS.toArrayListFlush();}
	
	
	/**Whether to save logs as files*/
	private static boolean LOG_TO_FILE = false;
	/**Enables saving logs as files
	 * 
	 * @param minPerFile number of minutes between each log file
	 * @param filePath path to save log files. Must include closing character to indicate folder ( / or \ )
	 */
	public static void enableFileLogging(double minPerFile, String filePath) {
		MINUTES_PER_FILE = minPerFile;
		FILE_PATH = filePath;
		LOG_TO_FILE = true;
	}
	/**Disables saving logs as files*/
	public static void disableFileLogging() {LOG_TO_FILE = false;}
	/**Returns true if saving logs as files*/
	public static boolean isFileLogging() {return LOG_TO_FILE;}
	
	/**Number of minutes between each log file*/
	private static double MINUTES_PER_FILE;
	/**@return number of minutes between each log file*/
	public static double getMinPerFile() {return MINUTES_PER_FILE;}
	
	/**Path to save log files.*/
	private static String FILE_PATH;
	/**@return path to save log files*/
	public static String getFilePath() {return FILE_PATH;}
	
	/**Holds all log messages until they are saved to file*/
	private volatile static HkConcurrentQueue<String> FILE_LOGS = new HkConcurrentQueue<String>();
	/**Adds a log to FILE_LOGS
	 * 
	 * @param log log to be added
	 */
	private synchronized static void addFileLog(String log) {EMAIL_LOGS.add(log);}
	/**Empties logs from FILE_LOGS into an HkArrayList
	 * 
	 * @return HkArrayList containing all logs in chronological order
	 */
	public synchronized static HkArrayList<String> getFileLogs(){return FILE_LOGS.toArrayListFlush();}
	
	
	
	
	/**Submit a log
	 * 
	 * @param type LogType enum value for the type of log being made. This will only be used if System console logging is enabled
	 * @param the message to be logged. A date and time will automatically be placed in front of it
	 */
	public synchronized static void log(LogType type, String log) {
		if(LOGGING) {
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss >>> ");
			log = format.format(new Date())+log;
			if(LOG_TO_CONSOLE) {
				switch(type) {
				case STANDARD: System.out.print("LOG: ");
					break;
				case ERROR: System.err.print("ERROR: ");
					break;
				case DEBUG: System.out.print("DEBUG: ");
					break;
				}
				System.out.println(": "+log);
			}
			if(LOG_TO_EMAIL)
				addEmailLog(log);
			if(LOG_TO_FILE)
				addFileLog(log);
		}
	}
	
}
