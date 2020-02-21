import java.awt.Color;
import java.awt.Graphics2D;

public class Palikat {
	
	public int arvo;
	
	// Palikan konstruktori. Antaa "rakennusohjeet" pelilaudalle.
	public Palikat(int[][] koordinaatit, Color color, Pelilauta pelilauta, Graphics2D g) {
		int uusiX = 0;
		int uusiY = 0;
		
		for(int i=0; i< koordinaatit.length; i++) {
			for(int j=0; j<koordinaatit[i].length; j++) {
				if(koordinaatit[i][j] == 1) {
					uusiX = i;
					uusiY = j+3;
				}
				pelilauta.luoPalikka(g, uusiX, uusiY, color);
			}
		}
	}
}
