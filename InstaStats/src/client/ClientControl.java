/**
 * ClientControl.java
 * 
 * An example client class that works with the GameCenter Server
 * 
 * @author Calvin Bochulak, Conner Dunn
 */
package client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientControl {
		
	public static final String IP_ADDRESS = "api.instagram.com";
	public static final int PORT = 65535;
	public static final String ADDRESS = "https://api.instagram.com/v1/";
	
	public static final String USERS = "users/";
	public static final String SEARCH = "search/";
	public static final String MEDIA = "media/";
	
	public static final String ACCESS_CODE = "../secret.txt";
	public static String code;
	
	protected ArrayList<String> paths;
	protected String userSessionID;
	
	public ClientControl() {
		
		
		// DEBUGGING //
		String s = this.getClass().getResource("").getPath() + ACCESS_CODE;
		System.out.println(s);
		File infile = new File(s);
		try {
			Scanner scan = new Scanner(infile);
			scan.useDelimiter("\\n");
			this.code = scan.next();
			scan.close();
		} catch (Exception e) { e.printStackTrace(); }
		// END DEGUBBING //
		
	}
	
	public static String search(String query) {
		return search(query, 1);
	}
	
	public static String search(String query, int count) {
		String address = String.format("https://api.instagram.com/v1/users/search?access_token=%s&q=%s&count=%d", code, query, count);
		return connect(address);
	}
	
	public static String findRecentPhotos(String query) {
		String address = String.format("https://api.instagram.com/v1/users/%s/media/recent", query);
		return connect(address);
	}
	
	public static String findRecentPhotos(String query, String maxID) {
		String address = String.format("https://api.instagram.com/v1/users/%s/media/recent?max_id=%s", query, maxID);
		return connect(address);
	}
	
	/**
	 * Connects to the server and returns the associated json string
	 * 
	 * @param path The path connecting to
	 * 
	 * @return String The data from the server
	 */
	public static String connect(String path) {		
		
		System.out.println("Client attempting a connection to : " + path); // Debugging
		
		HttpURLConnection connection;
		URL link;
		InputStream input;
		String data = "";
		DataOutputStream output;
		
		try {
		    link = new URL(path);
		    connection = (HttpURLConnection) link.openConnection();
		    connection.setAllowUserInteraction(true);
		    connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.setDoInput(true);
		    connection.setInstanceFollowRedirects(false);
		    
		    

		    try {  //Try to read the data from server
		    	//System.out.println(connection.getResponseCode());
		    	
				input = connection.getInputStream();
				while (input.available() <= 0); //no data from server, waiting...
				while (input.available() > 0) {
					int number = input.read();
					data += (char) number;
					System.out.print(" " + (char) number);
				}
			    input.close();
			} catch (Exception e) {
				e.printStackTrace();
				//data = gsonClientPack; //in the case of an exception, return the original pack
			}
		    
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// DEBUGGING //
		
		System.out.println("Client succesfully connected to   : " + path); // Debugging
		System.out.println("Data To Server: ");
		System.out.println("Data From Server: " + data);
		System.out.println("---------------------------------------");
		
		// END //
		
		return data;
	}
	
	public static void main(String[] args) {
		
		new ClientControl();
	}
}