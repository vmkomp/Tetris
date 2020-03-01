import java.awt.Color;
import java.awt.Graphics2D;

public class Palikat {
	public int[][] koordinaatit;
	public int variArvo;
	public int ruutujenMaaraPalikassa;
	
	// Palikan konstruktori. Alusta arrayt, variArvot ja palikan koordinaatit.
	public Palikat(int[][] koordinaatit, int variArvo, Pelilauta pelilauta) {
		
		this.koordinaatit = koordinaatit;
		ruutujenMaaraPalikassa = 0;
		int uusiX = 0;
		int uusiY = 0;
		this.variArvo = variArvo;

		// Palikan koordinaattien sovitus liikkuvaanKarttaan.
		for(int i=0; i< koordinaatit.length; i++) {
			for(int j=0; j<koordinaatit[i].length; j++) {
				if(koordinaatit[i][j] != 0) {
					ruutujenMaaraPalikassa++;
					uusiX = i;
					uusiY = j+3;
				}
				pelilauta.alustaPalikka(uusiX, uusiY, variArvo);
			}
		}
	}
	
	public int[][] annaKoordinaatit() {
		return koordinaatit;
	}
	
	public int annaVariArvo() {
		return variArvo;
	}
}
