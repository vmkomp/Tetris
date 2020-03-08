import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
	
	
	public void luoLauta(Graphics2D g, Muoto p, int score) {
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
		g.setColor(Color.RED);
		Font font = new Font("Serif", Font.PLAIN, 32);
		g.setFont(font);
		g.drawString(("Pisteet: " + score), 40, 430);
		
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
	
	
	public Muoto alustaMuoto(int arvo, Pelilauta pelilauta) {
		int[][] pArray = {{arvo, 0,0}, {arvo,arvo,arvo}};
		
		int[][] pArray2 = {{arvo, 0,0}, {arvo,arvo,arvo}};
		int[][] pArray3 = {{arvo, 0,arvo}, {arvo,arvo,arvo}};
		int[][] pArray4 = {{arvo, arvo,arvo}};
		int[][] pArray5 = {{arvo, arvo,arvo}, {arvo,arvo,arvo}};
		
		ArrayList<int[][]> muodot = new ArrayList<int[][]>();
		muodot.add(pArray);
		muodot.add(pArray2);
		muodot.add(pArray3);
		muodot.add(pArray4);
		muodot.add(pArray5);
		
		return new Muoto(muodot.get(arvo), arvo, pelilauta);
	}
	// Palikan grafiikoiden luonti.
	public void alustaMuotoKoordinaatit(int xKoordinaatti, int yKoordinaatti, int arvo) {
		
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
