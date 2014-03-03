import java.io.*;
import java.util.*;

public class Executor {
	File idList;
	File directory;
	File csvlocation;
	Set<String> directorylist = new HashSet<String>();

	public Executor(File src, File dst, File dstCSV) {

		idList = src;
		directory = dst;
		csvlocation = dstCSV;

	}

	/**
	 * This method would mine everything, produce JSON files within a given
	 * directory, and then generate a single csv.
	 */
	public void mineAll() throws Exception {
		Communicator talk = new Communicator(idList, directory);
		generateCSV();

	}

	public void mineJSON() {
		Communicator talk = new Communicator(idList, directory);
	}

	/**
	 * GenerateCSV may be called by itself if a directory with JSON files
	 * already exists.
	 */
	public void generateCSV() throws FileNotFoundException {
		// read all file names inside directory
		this.listFilesForFolder(directory);
		PrintWriter px = new PrintWriter(csvlocation);
		px.println("ID, Title, MPAA Rating, Release Day, Release Month, Release Year, Runtime, Genre 1, Genre 2, Genre 3, Director, Actor One, Actor Two, Actor Three, Country, Awards(mentions+wins), Metascore, imdbRating, imdbVotes, Tomato Meter, Tomato Rating, Box Office");
		for (String s : directorylist) {
			Movie currMovie = new Movie(s);
			String locale = directory.getAbsolutePath() + "\\" + s;
			MoviePopulatorAgent pop = new MoviePopulatorAgent(currMovie, locale);
			px.println(currMovie.toString());
			px.flush();
		}

		px.close();
		System.out.println("CSV generation complete");
		System.out.println("All complete");

	}

	// HelperMethod
	public void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				directorylist.add(fileEntry.getName());
			}
		}
	}

}
