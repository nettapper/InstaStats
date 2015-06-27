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

import client.ClientControl;
import org.json.*;

public class InstaStats {
    
    public InstaStats() {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientControl control = new ClientControl();
        String s = control.search("conner_d23", 2);
        
        JSONObject jo = new JSONObject(s);
        JSONArray dataArray = jo.getJSONArray("data");
        JSONObject person1 = dataArray.getJSONObject(0);
        String id = person1.getString("id");
        
        System.out.println("HEY THERE");
        
        System.out.println(id);
        
        System.out.println("BYE THERE");
        //Create Array of type String to include the Image IDs
        //Loop through them all finding the 3 with the most likes
    }
    
}
