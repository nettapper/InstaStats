/**
 * InstaStats.java
 * 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * @author Calvin Bochulak, Conner Dunn, ahmedrashid
 */
package instastats;

import client.ClientControl;

public class InstaStats {

	public InstaStats() {
		
	}
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientControl control = new ClientControl();
        String s = control.search("conner_d23", 2);
    }

}
