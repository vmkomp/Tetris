import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Pelilauta {
	public int lauta2D[][];
	public int nelionPituus;
	
	public Pelilauta(int rivi, int sarake) {
		lauta2D = new int[rivi][sarake];
		for(int i=0; i< lauta2D.length; i++) {
			for(int j=0; j< lauta2D[0].length; j++) {
				if(i==1 && j==5) {
					lauta2D[i][j] = 1;
				} else {
					lauta2D[i][j] = 0;
				}
				
			}
		}
		
		nelionPituus = 20;
	}
	
	/**
	 * Luo uuden pelilaudan.
	 * @param lauta2D 	Taulukko, joka sis�llytt�� palikoiden v�rien arvot.
	 */
	public void luoLauta(Graphics2D g) {
		for(int i=0; i< lauta2D.length; i++) {
			for(int j=0; j< lauta2D[0].length; j++) {
				if(lauta2D[i][j] == 0) {
					g.setColor(Color.white);
					g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				}else {
					g.setColor(Color.yellow);
					g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				}

				
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.black);
				g.drawRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
			}
		}
	}
	
	// Palikan grafiikoiden luonti.
	public void luoPalikka(Graphics2D g, int xKoordinaatti, int yKoordinaatti, Color color) {
		
		for(int i=0; i< lauta2D.length; i++) {
			for(int j=0; j< lauta2D[0].length; j++) {
				
				if(i==xKoordinaatti && j==yKoordinaatti) {
					g.setColor(color);
					g.fillRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * nelionPituus, i * nelionPituus, nelionPituus, nelionPituus);
				}
			}
		}
			
	}
}