import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pelilauta {
	public int lauta2D[][];
	public int nelionPituus;
	
	public Pelilauta(int rivi, int sarake) {
		lauta2D = new int[rivi][sarake];
		for(int i=0; i< lauta2D.length; i++) {
			for(int j=0; j< lauta2D[0].length; j++) {
					lauta2D[i][j] = 0;	
			}
		}
		
		nelionPituus = 20;
	}
	
	/**
	 * Luo uuden pelilaudan.
	 * @param lauta2D 	Taulukko, joka sisällyttää palikoiden värien arvot.
	 */
	public void luoLauta(Graphics2D g) {
		for(int i=0; i< lauta2D.length; i++) {
			for(int j=0; j< lauta2D[0].length; j++) {

				g.setColor(Color.white);
				g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.black);
				g.drawRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
			}
		}
	}
	
	// Palikan grafiikoiden luonti.
	public void luoPalikkaGrafiikat(Graphics2D g, int xKoordinaatti, int yKoordinaatti, int arvo) {
		

			lauta2D[xKoordinaatti][yKoordinaatti] = arvo;
			switch(arvo) {
			case 1:
				g.setColor(Color.blue);
				g.fillRect(yKoordinaatti * nelionPituus, xKoordinaatti * nelionPituus, nelionPituus, nelionPituus);
				break;
			case 2:
				g.setColor(Color.red);
				g.fillRect(yKoordinaatti * nelionPituus, xKoordinaatti * nelionPituus, nelionPituus, nelionPituus);
				break;
		}
			g.setStroke(new BasicStroke(3));
			g.setColor(Color.black);
			g.drawRect(yKoordinaatti * nelionPituus, xKoordinaatti * nelionPituus, nelionPituus, nelionPituus);
	
	
	}

}
