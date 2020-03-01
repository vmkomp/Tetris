import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pelilauta {
	private int variTaulukko[][];
	private int staattinenTaulukko[][];
	public int nelionPituus;
	private int liikkuvaTaulukko[][];
	
	public Pelilauta(int rivi, int sarake) {
		
		variTaulukko = new int[rivi][sarake];
		staattinenTaulukko = new int[rivi][sarake];
		liikkuvaTaulukko = new int[rivi][sarake];
		nelionPituus = 20;
		
		for(int i=0; i< variTaulukko.length; i++) {
			for(int j=0; j< variTaulukko[0].length; j++) {
				
				variTaulukko[i][j] = 0;
				staattinenTaulukko[i][j] = 0;
				liikkuvaTaulukko[i][j]=0;
			}
		}
	}
	
	/**
	 * Luo uuden pelilaudan.
	 * @param variTaulukko 	Taulukko, joka sisällyttää palikoiden värien arvot.
	 */

	public void luoLauta(Graphics2D g, Palikat p) {
		for(int i=0; i< variTaulukko.length; i++) {
			for(int j=0; j< variTaulukko[0].length; j++) {
				
				if(liikkuvaTaulukko[i][j] == 1){
					
					piirraVari(g, p.annaVariArvo(), i, j);
				} else if(staattinenTaulukko[i][j] != 0) {
					
					piirraVari(g, staattinenTaulukko[i][j], i, j);
				} else {
					
				g.setColor(Color.white);
				g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
			}

				
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.black);
				g.drawRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
			}
		}
	}
	
	public void piirraVari(Graphics2D g, int arvo, int i, int j) {
			switch(arvo) {
			case 1:
				g.setColor(Color.blue);
				g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				break;
			case 2:
				g.setColor(Color.red);
				g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				break;
			case 3:
				g.setColor(Color.green);
				g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				break;
			case 4:
				g.setColor(Color.orange);
				g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				break;
		}
	}
	
	// Palikan grafiikoiden luonti.
	public void alustaPalikka(int xKoordinaatti, int yKoordinaatti, int arvo) {
		
		variTaulukko[xKoordinaatti][yKoordinaatti] = arvo;
		liikkuvaTaulukko[xKoordinaatti][yKoordinaatti] = 1;
	}
	
	public int[][] annaStaattinenTaulukko() {
		return staattinenTaulukko;
	}
	
	public int[][] annaLiikkuvaTaulukko() {
		return liikkuvaTaulukko;
	}

}
