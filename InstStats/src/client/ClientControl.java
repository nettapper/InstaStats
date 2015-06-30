/**
 * ClientControl.java
 * 
 * An example client class that works with the GameCenter Server
 * 
 * @author Calvin Bochulak, Conner Dunn
 */
package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONObject;

public class ClientControl {
	
	public static final int PORT = 65535;
	public static final String ADDRESS = "https://api.instagram.com/v1";
	
	public static final String USERS = "users";
	public static final String SEARCH = "search";
	public static final String MEDIA = "media";
	public static final String RECENT = "recent";
	
	public static final String ACCESS_TOKEN = "access_token";
	public static final String QUERY = "q";
	public static final String COUNT = "count";
	public static final String MAX_ID = "max_id";
	
	protected ArrayList<String> paths;
	protected String userSessionID;
	
	public ClientControl() {
		
	}
	
	public static String search(String code, String query) {
		return search(code, query, 1);
	}
	
	public static String search(String token, String query, int count) {
		String address = String.format("%s/%s/%s?%s=%s&%s=%s&%s=%d", ADDRESS, USERS, SEARCH, ACCESS_TOKEN, token, QUERY, query, COUNT, count);
		return connect(address);
	}
	
	public static String findRecentPhotos(String token, String query) {
		String address = String.format("%s/%s/%s/%s/%s?%s=%s", ADDRESS, USERS, query, MEDIA, RECENT, ACCESS_TOKEN, token);
		return connect(address);
	}
	
	public static String findRecentPhotos(String token, String query, String maxID) {
		String address = String.format("%s/%s/%s/%s/%s?%s=%s?%s=%s", ADDRESS, USERS, query, MEDIA, RECENT, ACCESS_TOKEN, token, MAX_ID, maxID);
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
		//DataOutputStream output;
		
		try { // Try to open a connection to the server
		    link = new URL(path);
		    connection = (HttpURLConnection) link.openConnection();
		    connection.setAllowUserInteraction(true);
		    connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.setDoInput(true);
		    connection.setInstanceFollowRedirects(false);

		    try { // Try to read the data from server
		    	
				input = connection.getInputStream();
				while (input.available() <= 0); //no data from server, waiting...
				
				StringBuilder buffer = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
				String line = null;
				
				while((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				data = buffer.toString();
				
			    input.close();
			} catch (Exception e) { 
				e.printStackTrace(); 
			}
		    
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// DEBUGGING //
		
		System.out.println("Client succesfully connected to   : " + path); // Debugging
		System.out.println("Data To Server: ");
		System.out.println("Data From Server: " + data);
		System.out.println("Response Code: " + getResponseCode(data));
		System.out.println("---------------------------------------");
		
		// END //
		
		return data;
	}
	
	public static int getResponseCode(String jString) {
        JSONObject jObject = new JSONObject(jString);
        JSONObject jObject2 = jObject.getJSONObject("meta");
        return jObject2.getInt("code");
	}
}