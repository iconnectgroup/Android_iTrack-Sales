package com.itrack.Utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

public class AndroidLogger {

	private static long SIZE = 0;
	private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	private static final String TAG = "Logger";
	File file;
	File directory;
	String fileName = "";
	int numOfFiles;
	String dirName = "";
	ArrayList<String> FileNamesList = new ArrayList<String>();

	public void writeLog(String txt) {

		if (isExternalStorageWritable())
			appendLog(txt);

	}

	public void setMaxFileSizeInKilobytes(long size) {
		SIZE = size * 1024; // converted to bytes
	}

	public void setMaxFiles(int number) {
		this.numOfFiles = number;
		FileNamesList.clear();

		// pre generate the filenames
		// num of names depends on the num
		// of files that are allowed to
		// reside in the directory
		for (int i = 1; i <= numOfFiles; i++) {
			FileNamesList.add(GenerateFileName(fileName, i));
		}
	}

	public void setLogFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setLogDirName(String dirName) {
		this.dirName = dirName;

	}

	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	// Gets the text to be appended
	public String GetPrependString() {
		Date date = new Date();
		String str = (String) DateFormat.format(DATE_FORMAT, date);
		return str;
	}

	public void appendLog(String text) {
		text = GetPrependString() + " " + text;

		directory = new File(Environment.getExternalStorageDirectory(), dirName);
		directory.mkdir();

		File logFile = new File(directory + "/" + fileName);
		if (!logFile.exists()) {
			try {
				Log.d("Sushil", "in if "+logFile.getPath());
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Log.d("Sushil", "in else "+logFile.getPath());
		}
		try {

			FileWriter fw = new FileWriter(logFile, true);
			fw.append("\r\n " + text); // required for NOTEPAD for new line
			fw.append(System.getProperty("line.separator")); // works on wordpad
																// for new line
																// but not for
																// notepad
			fw.close();

			if (logFile.length() > SIZE) {
				renameFile(logFile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void renameFile(File from) {
		File newFile = null;

		for (int i = 0; i < numOfFiles; i++) {
			newFile = new File(directory + "/" + FileNamesList.get(i));

			if (newFile.exists()) {

				if (i == numOfFiles - 1) {

					// maximum file limit reached
					// got to delete the file
					// recreate it to
					newFile.delete();
					newFile = new File(directory + "/" + FileNamesList.get(i));
					from.renameTo(newFile);
				}

			} else {
				from.renameTo(newFile);
			}

		}
	}

	// Gets the filename without extension
	private String GenerateFileName(String filename, int i) {
		filename = filename.split("\\.")[0];
		filename = filename + "-" + i + ".txt";
		return filename;
	}

}
