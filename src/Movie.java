import java.math.BigInteger;


/**
 * The movie object creates a convenient
 * container for all the data that is parsed
 * by MoviePopulatorAgent.
 * 
 * Having an object such as this one may enable
 * allows flexibility to manipulate the movies
 * as objects, although creating a movie object
 * was probably not entirely warranted for this 
 * project. 
 * @author Emeril
 *
 */
public class Movie {

	public String ID;
	public String title;
	public int MPAARating; // 1=G 2=PG 3=PG-13 4=R 5=NC-17
	public int releaseDay;
	public int releaseMonth;
	public int releaseYear;
	public int runtime; // in minutes
	public String genre1; // primarygenre
	public String genre2; // secondary genre
	public String genre3; // tertiary genre
	public String director;
	public String actorOne; // star
	public String actorTwo; // supportingactor
	public String actorThree; // supportingactor2
	public String plot; // plot field
	public String country; // country
	public int awardssum; // sum of nominations and wins
	public int metascore; // metacritic score
	public float imdbRating; // IMDB score
	public int imdbVotes;// IMDBvotes
	public int tomatoMeter; // tomatometer
	public int tomatoRating; // tomatorating
	public String boxoffice;

	/**
	 * Simple Constructor that takes an imdbID
	 * 
	 * @param imdbID
	 */
	public Movie(String imdbID) {

		ID = imdbID;

	}

	// toString override
	@Override
	public String toString() {
		return ID.replace(".txt", "") + "," + title + "," + MPAARating + ","
				+ releaseDay + "," + releaseMonth + "," + releaseYear + ","
				+ runtime + "," + genre1 + "," + genre2 + "," + genre3 + ","
				+ director + "," + actorOne + "," + actorTwo + "," + actorThree
				+ "," + country + "," + awardssum + "," + metascore + ","
				+ imdbRating + "," + imdbVotes + "," + tomatoMeter + ","
				+ tomatoRating + "," + boxoffice + ",";
	}

	/**
	 * HELPER METHODS
	 * 
	 */
	public String getID() {
		return this.ID;
	}

	// Title
	public String getTitle() {
		return this.title;
	}

	public Boolean hasTitle() {
		if (this.title != null) {
			return true;
		}
		return false;
	}

	private void setTitle(String s) {
		this.title = "" + s;
	}

	// MPAA Rating
	public int getMPAARating() {
		return this.MPAARating;
	}

	public boolean hasMPAARating() {
		if (this.MPAARating > 0) {
			return true;
		}
		return false;
	}

	private void setMPAARating(int n) {
		this.MPAARating = n;
	}

	// Title
	// others

}
