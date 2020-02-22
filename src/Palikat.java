import java.awt.Color;
import java.awt.Graphics2D;

public class Palikat {
	
	// Palikan konstruktori. Antaa "rakennusohjeet" pelilaudalle.
	public Palikat(int[][] koordinaatit, int arvo, Pelilauta pelilauta) {

		int uusiX = 0;
		int uusiY = 0;
		
		for(int i=0; i< koordinaatit.length; i++) {
			for(int j=0; j<koordinaatit[i].length; j++) {
				if(koordinaatit[i][j] != 0) {
					uusiX = i;
					uusiY = j+3;
				}
				pelilauta.luoPalikkaGrafiikat(uusiX, uusiY, arvo);
			}
		}
	}
}
