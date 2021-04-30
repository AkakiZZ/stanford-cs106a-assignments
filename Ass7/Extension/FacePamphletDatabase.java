/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import acm.graphics.GImage;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		profiles = new HashMap<>();	
		loadDatabase();
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		profiles.put(profile.getName(), profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		if (!profiles.containsKey(name))
			return null;
		return profiles.get(name);
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (containsProfile(name)) {
			Iterator<String> friends = profiles.get(name).getFriends();
			while(friends.hasNext()) {
				profiles.get(friends.next()).removeFriend(name);
			}
			profiles.remove(name);
		}	
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if (profiles.containsKey(name)) {
			return true;
		}
		return false;
	}
	

	/**
	 * Writes new data in a text file
	 * Information is saved in this format:
	 * Name (status) [friends] {image file}
	 * n For example: Jake (Swimming) [Eric,Dave,Aaron,Julie] {JakeB.jpg} 
	 */
	public void writeData() {
		FileWriter fw;
		try {
			fw = new FileWriter(FILE_NAME);
			PrintWriter pw = new PrintWriter(fw);
			pw.print("");
			for (String tmp : profiles.keySet()) {
				pw.println(profiles.get(tmp));
			}
			pw.close();
		} catch (IOException e) {
		}
	}
	
	/*
	 * Loads information about profiles from a text file 
	 */
	private void loadDatabase() {
		BufferedReader rd;
		String line = "";
		try {
			rd = new BufferedReader(new FileReader(FILE_NAME));
			while (true) {
				line = rd.readLine();
				if (line == null) {
					rd.close();
					break;
				} else {
					StringTokenizer st = new StringTokenizer(line);
					int counter = 0;
					String name = "";
					String status = "";
					String friends = "";
					String imageURL = "";
					while (st.hasMoreTokens()) {
						if (counter == 0) {
							name = st.nextToken();
						} else if (counter == 1) {
							status = st.nextToken();
						} else if (counter == 2){
							friends = st.nextToken();
						} else {
							imageURL = st.nextToken();
						}
						counter++;
					}
					formatData(name, status, friends, imageURL);
				}
			}
			rd.close();
		} catch (Exception e){
			
		}
	} 
	
	/*
	 * Formats loaded information
	 * Creates profiles and adds them into profiles map
	 */
	private void formatData(String name, String status, String friends, String imageURL) {
		status = status.substring(1);
		status = status.substring(0, status.length() - 1);
		friends = friends.substring(1);
		friends = friends.substring(0, friends.length() - 1);
		FacePamphletProfile profile = new FacePamphletProfile(name);
		profile.setStatus(status);
		StringTokenizer st = new StringTokenizer(friends, ",");
		while(st.hasMoreTokens()) {
			profile.addFriend(st.nextToken());
		}
		imageURL = imageURL.substring(1);
		imageURL = imageURL.substring(0, imageURL.length() - 1);
		try {
			GImage image = new GImage(imageURL);
			profile.setImage(image, imageURL);
		} catch (Exception e) {
		}
		System.out.println(profile);
		addProfile(profile);
	}
	
	
	private HashMap<String, FacePamphletProfile> profiles;

}
