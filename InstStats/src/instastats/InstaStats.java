/**
 * InstaStats.java
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 *
 * @author Calvin Bochulak, Conner Dunn, ahmedrashid
 */
package instastats;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ClientControl;

public class InstaStats {
	
	private static final String localCodePath = "../secret.txt";
	private String accessCode;
    
    public InstaStats() {
        this.accessCode = getAccessCode();
        
        // DEBUGGING //
        
        String results = ClientControl.search(accessCode, "conner_d23", 2);
        
        JSONObject jObject = new JSONObject(results);
        JSONArray jArray = jObject.getJSONArray("data");
        JSONObject firstPerson = jArray.getJSONObject(0);
        
        System.out.println("Array: " + jArray);
        System.out.println("First Person: " + firstPerson);
        System.out.println("---------------------------------------");
        
        results = ClientControl.findRecentPhotos(accessCode, "206586355");
        
        // END //
    }
    
    public static void main(String[] args) {
    	new InstaStats();
    }
    
    private String getAccessCode() {
    	String codePath = this.getClass().getResource("").getPath() + localCodePath;
    	File codeFile = new File(codePath);
    	String code = null;
    	try {
			Scanner scanner = new Scanner(codeFile);
			code = scanner.next();
			scanner.close();
		} catch (FileNotFoundException e) { e.printStackTrace(); }
    	 	
    	return code;
    }
    
}
