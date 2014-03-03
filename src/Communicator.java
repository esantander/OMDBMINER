import java.io.*;
import java.util.*;
import java.net.*;

public class Communicator {

	File idList;
	int horizon;
	File directory;
	Set<String> ids = new HashSet<String>();
	String currentID;
	private final String USER_AGENT = "Mozilla/5.0";

	/**
	 * the contructor for this class takes a file. The file should contain a
	 * list of imdbID, each ID on its own line. The constructor includes a
	 * horizon for testing purposes.
	 * 
	 * @param File
	 *            sourcefile, sourcedirectory for JSON, int horizon
	 */
	public Communicator(File f, File dir, int q) {

		idList = f;
		horizon = q;
		try {
			Scanner s = new Scanner(idList);
			int horizoncounter = 0;
			while (horizoncounter < horizon) {
				ids.add(s.nextLine().trim());
				horizoncounter++;
			}
			s.close();

		} catch (FileNotFoundException e) {
			System.out.println("Communicator could not find source ID file");
			e.printStackTrace();
		}
		directory = dir;
		try {
			this.requestAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.requestAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This constructor lacks a horizon and can be used to scan an entire file
	 * of IDs. Must include source directory for JSON.
	 * 
	 * @param f
	 */
	public Communicator(File f, File dir) {

		idList = f;
		try {
			Scanner s = new Scanner(idList);
			while (s.hasNext()) {
				ids.add(s.next());
			}
			s.close();
			currentID = ids.iterator().next();
		} catch (FileNotFoundException e) {
			System.out.println("Communicator could not find source ID file");
		}
		directory = dir;
		try {
			this.requestAll();
		} catch (IOException e) {
			// Catches IOE
			e.printStackTrace();
		} catch (InterruptedException e) {
			// Catches IE
			e.printStackTrace();
		}
	}

	/**
	 * This method requests each imdbID from the OMDB API using an HTTPRequest.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void requestAll() throws IOException, InterruptedException {
		int UNIVERSAL_COUNTER = 0;
		PrintWriter pw = null;
		System.out.println("");
		System.out.println("-------------Initializing " + ids.size()
				+ " requests.----------");
		System.out.println("");
		horizon = ids.size();
		// read in an ID and send request (main loop)
		while (UNIVERSAL_COUNTER < horizon) {
			currentID = ids.iterator().next();

			String url = "http://www.omdbapi.com/?i=" + currentID
					+ "&tomatoes=TRUE";
			ids.remove(currentID);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);

			// STATUS_INFORMER 1
			if (UNIVERSAL_COUNTER % 1000 == 0 && UNIVERSAL_COUNTER > 999) {
				System.out.println("");
				System.out
						.println("This message is printed every 1000 completed requests: ");
				System.out.println("The current count of processed queries is "
						+ UNIVERSAL_COUNTER);
				System.out.println("");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			// Read stream
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// File is printed to target *directory*
			String tempstring = directory + "/" + currentID + ".txt";
			File iu = new File(tempstring);
			pw = new PrintWriter(iu);
			pw.println(response.toString());
			pw.flush();
			UNIVERSAL_COUNTER++;

			// STATUS_INFORMER 2
			if (UNIVERSAL_COUNTER % 500 == 0 && UNIVERSAL_COUNTER > 499) {
				System.out.println("");
				System.out.println("----------------");
				System.out.println("");
				System.out
						.println("This message is printed every 500 requests");
				System.out.println("");
				System.out.println("Thread is nominal.");
				System.out.println("");
				System.out.println("----------------");
			}

			// To prevent the OMDB API from getting mad
			Thread.sleep(200);

		}// endWhile

		pw.close();
	}

}
