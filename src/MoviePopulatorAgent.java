import com.fasterxml.jackson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MoviePopulatorAgent {

	Movie currMovie;
	Map<String, String> movieDataMap = new HashMap<String, String>();
	private static Pattern WHITESPACE_PATTERN = Pattern
			.compile("\\p{javaWhitespace}+");
	private final String notApply = "N/A";

	public MoviePopulatorAgent(Movie m, String JSONLocation) {
		currMovie = m;
		this.populateDataMapfromJSON(JSONLocation);
		this.populateAllFields();
	}

	/**
	 * The populator takes movies that have already been instantiated, which
	 * means they already have an ID. From here, we will bind JSON to build the
	 * rest of each movie object.
	 */

	// START by using Jackson to convert json to map!

	// converting json to Map

	public void populateDataMapfromJSON(String loc) {
		try {
			byte[] mapData = Files.readAllBytes(Paths.get(loc));

			ObjectMapper objectMapper = new ObjectMapper();
			movieDataMap = objectMapper.readValue(mapData, HashMap.class);
			// System.out.println("Map is: "+ movieDataMap);
		} catch (IOException e) {
			System.out.println("");
			System.out
					.println("An error has occured in loading a text file in the movie class populator");
			e.printStackTrace();
		}
	}

	/**
	 * Now that the HashMap<String,String> is populated, a method must exist for
	 * each variable in Movie.class to populate it. Some will be simple variable
	 * assignments but some will involve casting or computation (dividing date
	 * in 3 fields, etc).
	 */

	public void populateAllFields() {
		// insert submethods here
		populateTitle();
		populateMPAARating();
		populateDate();
		populateRuntime();
		populateGenre();
		populateDirector();
		populateActors();
		populatePlot();
		populateCountry();
		populateAwards();
		populateMetascore();
		populateIMDBScore();
		populateTomatometer();
		populateBoxOffice();

	}

	// Field-by-Field Submethods
	private void populateTitle() {
		currMovie.title = movieDataMap.get("Title");
	}

	// MPAARating computation
	private void populateMPAARating() {
		String rating = movieDataMap.get("Rated");
		rating = rating.toUpperCase();
		switch (rating) {
		case "G":
			currMovie.MPAARating = 1;
			break;
		case "PG":
			currMovie.MPAARating = 2;
			break;
		case "PG-13":
			currMovie.MPAARating = 3;
			break;
		case "R":
			currMovie.MPAARating = 4;
			break;
		case "NC-17":
			currMovie.MPAARating = 5;
		default:
			currMovie.MPAARating = 999999; // for error detection
			System.out.println("Error occured in parsing rating for "
					+ currMovie.title); // log error
			break;
		}

	}

	// populates day, month, and year
	private void populateDate() {

		String compositeDate = movieDataMap.get("Released");
		Scanner sn = new Scanner(compositeDate);
		// set day because it is the first value
		currMovie.releaseDay = Integer.parseInt(sn.next());
		// parse month from str to int
		String substringMonth = sn.next();
		substringMonth = substringMonth.toUpperCase();
		switch (substringMonth) {

		case "JAN":
			currMovie.releaseMonth = 1;
			break;
		case "FEB":
			currMovie.releaseMonth = 2;
			break;
		case "MAR":
			currMovie.releaseMonth = 3;
			break;
		case "APR":
			currMovie.releaseMonth = 4;
			break;
		case "MAY":
			currMovie.releaseMonth = 5;
			break;
		case "JUN":
			currMovie.releaseMonth = 6;
			break;
		case "JUL":
			currMovie.releaseMonth = 7;
			break;
		case "AUG":
			currMovie.releaseMonth = 8;
			break;
		case "SEP":
			currMovie.releaseMonth = 9;
			break;
		case "OCT":
			currMovie.releaseMonth = 10;
			break;
		case "NOV":
			currMovie.releaseMonth = 11;
			break;
		case "DEC":
			currMovie.releaseMonth = 12;
			break;
		default:
			currMovie.releaseMonth = 999999;
			break;

		}// endCase

		// parseyeartoken
		currMovie.releaseYear = Integer.parseInt(sn.next());
		sn.close();

	}// endDatePopulate

	private void populateRuntime() {
		Scanner sp = new Scanner(movieDataMap.get("Runtime"));
		currMovie.runtime = Integer.parseInt(sp.next());
		sp.close();
	}

	private void populateGenre() {
		Scanner so = new Scanner(movieDataMap.get("Genre"));
		currMovie.genre1 = so.next().replace(",", "");
		if (so.hasNext()) {
			currMovie.genre2 = so.next().replace(",", "");
			if (so.hasNext()) {
				currMovie.genre3 = so.next().replace(",", "");
			}
		}
		so.close();
	}

	private void populateDirector() {
		currMovie.director = movieDataMap.get("Director");
	}

	private void populateActors() {

		Scanner sp = new Scanner(movieDataMap.get("Actors"));
		sp.useDelimiter(",");
		currMovie.actorOne = sp.next().replace(",", "");
		if (sp.hasNext()) {
			currMovie.actorTwo = sp.next().replace(",", "");
			if (sp.hasNext()) {
				currMovie.actorThree = sp.next().replace(",", "");
			}
			sp.useDelimiter(WHITESPACE_PATTERN);
			if (sp.hasNext() && currMovie.actorThree.isEmpty()) {
				currMovie.actorThree = sp.next() + " " + sp.next();
				currMovie.actorThree = currMovie.actorThree.trim();
			}

		}

		sp.close();
	}

	private void populatePlot() {
		currMovie.plot = movieDataMap.get("Plot");
	}

	private void populateCountry() {
		currMovie.country = movieDataMap.get("Country");
	}

	private void populateAwards() {
		String raw = movieDataMap.get("Awards");
		int mentions = 0;
		Scanner sq = new Scanner(raw);
		while (sq.hasNext()) {
			if (sq.hasNextInt()) {
				mentions = mentions + sq.nextInt();
			}
			sq.next();
		}

		sq.close();
		currMovie.awardssum = mentions;
	}

	private void populateMetascore() {
		if (!movieDataMap.get("Metascore").isEmpty()) {
			currMovie.metascore = Integer.parseInt(movieDataMap
					.get("Metascore"));
		}

		if (movieDataMap.get("Metascore").isEmpty()) {
			System.out.println("Error on " + "," + currMovie.title + " ,"
					+ currMovie.ID);
			File errorfile = new File(
					"C:/Desktop/Users/Emeril/Desktop/OMDB/ERROR_FOLDER/errorfile.txt");
			PrintWriter pf = null;
			try {
				pf = new PrintWriter(errorfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pf.append("\t\n" + "Error on " + "," + currMovie.title + ","
					+ currMovie.ID);
		}

	}

	private void populateIMDBScore() {

		if (!movieDataMap.get("imdbRating").isEmpty()) {
			currMovie.imdbRating = Float.parseFloat(movieDataMap
					.get("imdbRating"));
		}

	}

	private void populateIMDBVotes() {

		if (!movieDataMap.get("imdbVotes").isEmpty()) {
			currMovie.imdbVotes = Integer.parseInt(movieDataMap
					.get("imdbVotes"));
		}

	}

	private void populateTomatometer() {

		if (!movieDataMap.get("tomatoUserMeter").isEmpty()) {
			currMovie.tomatoMeter = Integer.parseInt(movieDataMap
					.get("tomatoUserMeter"));
		}

	}

	private void populateBoxOffice() {
		currMovie.boxoffice = movieDataMap.get("BoxOffice");
	}

}
