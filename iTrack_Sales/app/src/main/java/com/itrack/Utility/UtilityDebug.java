package com.itrack.Utility;

import android.util.Log;

public class UtilityDebug {

	/**
	 * (UtilityDebug Class is used to show logs)
	 * 
	 * @author UtilityDebug
	 */

	/**
	 * (UtilityDebug Members Declarations)
	 * 
	 * @author UtilityDebug
	 */

	private static final String Log_Tag = "iTrackPLS";

	/**
	 * Method to Log Message
	 * 
	 * @param Log_Message
	 * @param LogType
	 */
	public static void Debug(int LogType, String Log_Message) {

		switch (LogType) {
		// Case 1- To Show Message as Debug
		case 1:
			Log.d(Log_Tag, Log_Message);
			break;
		// Case 2- To Show Message as Error
		case 2:
			Log.e(Log_Tag, Log_Message);
			break;
		// Case 3- To Show Message as Information
		case 3:
			Log.i(Log_Tag, Log_Message);
			break;
		// Case 4- To Show Message as Verbose
		case 4:
			Log.v(Log_Tag, Log_Message);
			break;
		// Case 5- To Show Message as Assert
		case 5:
			Log.w(Log_Tag, Log_Message);
			break;
		// Case Default- To Show Message as System Print
		default:
			System.out.println(Log_Message);
			break;
		}
	}

}
