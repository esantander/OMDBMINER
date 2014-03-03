import java.io.*;
import java.util.*;

/**
 * This program is a command-line java tool
 * that mines the OMDB API for movie data
 * related to a list of imdbIDs that are provided
 * to it on instantiation. It also accepts a path
 * for a directory to store the downloaded JSON files.
 * Finally, it accepts a location to output a parsed CSV
 * of all JSON files but the data binding part is prone
 * to malfunction. 
 * 
 * At current, this tool may be best just for use 
 * in downloading JSON files related to IMDBids.
 * @author Emeril Santander
 *
 */
public class Main {

	// class vars
	File idList;
	File directory;
	File csvlocation;

	public static void main(String[] args) {

		int argsnum = args.length;

		//print help
		if (argsnum < 3)
		{
			System.err.println("Please see readme.txt for required parameters");
			System.err
					.println("Parameter 1 is a *full path* for a list of IMDBids");
			System.err
					.println("Parameter 2 is a *full path* for a directory to hold JSON files");
			System.err
					.println("Parameter 3 is a *full path* for a file location  (including name) for a final csv file");
			System.err
			.println("Parameter 3 can also just be a J if you are just mining for JSON to a directory");
		}
		
		//mine all
		if (argsnum == 3 && args[2].length() > 1) {
			File idList = new File(
					args[0]);
			File directory = new File(
					args[1]);
			File csvlocation = new File(
					args[2]);
			Executor exec = new Executor(idList, directory, csvlocation);
			try {
				exec.mineAll();
			} catch (Exception E) {
				E.printStackTrace();
			}

		}
		
		//JSON-only mode
		if (argsnum == 3 && args[2].length() == 1) {
			File idList = new File(
					args[0]);
			File directory = new File(
					args[1]);
			Executor exec = new Executor(idList, directory, null);
			exec.mineJSON();

		} else { //print help
			System.err.println("Please see readme.txt for required parameters");
			System.err
					.println("Parameter 1 is a *full path* for a list of IMDBids");
			System.err
					.println("Parameter 2 is a *full path* for a directory to hold JSON files");
			System.err
					.println("Parameter 3 is a *full path* for a file location  (including name) for a final csv file");
			System.err
			.println("Parameter 3 can also just be a J if you are just mining for JSON to a directory");

		}

	}
}
