import java.io.BufferedReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import acmx.export.java.io.FileReader;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
	HashMap<String, String> map;
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		map = new HashMap<String, String>();
		BufferedReader rd;
		String line = "";
		String name = "";
		try {
			rd = new BufferedReader(new FileReader(filename));
			while(true) {
				line = rd.readLine();
				if(line == null) {
					rd.close();
					break;
				}
				StringTokenizer st = new StringTokenizer(line);
				int counter = 0;
			    while (st.hasMoreTokens()) {
			        if (counter == 0) {
			        	name = st.nextToken();
			        	counter++;
			        } else {
			        	break;
			        }
			    }
				map.put(name, line);
			}
		} catch (Exception e) {	
			System.out.println("File Doesn't exist");
		}	
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		for (String tmp : map.keySet()) {
			if (tmp.equals(name)) {
				NameSurferEntry entry = new NameSurferEntry(map.get(tmp)); 
				return entry;
			}
		}
		return null;
	}
}

