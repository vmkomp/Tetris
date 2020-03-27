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
	
	public Pelilauta(int rivi, int sarake, int[][] ladattuPeli) {
		
		variTaulukko = new int[rivi][sarake];
		staattinenTaulukko = ladattuPeli;
		liikkuvaTaulukko = new int[rivi][sarake];
		nelionPituus = 20;
		
		for(int i=0; i< variTaulukko.length; i++) {
			for(int j=0; j< variTaulukko[0].length; j++) {
				
				variTaulukko[i][j] = 0;
				//staattinenTaulukko[i][j] = 0;
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
		
		// S tarkoittaa suorakulmiota. Muut palikat nimetty ulkoasua vastaavaksi kirjaimeksi.
		switch (arvo) {
			case 0:
				S suorakulmio = new S();
				suorakulmio.sovitaPeliKenttaan(pelilauta);
				return (Muoto)suorakulmio;
			case 1:
				L l = new L();
				l.sovitaPeliKenttaan(pelilauta);
				return (Muoto)l;
			case 2:
				M m = new M();
				m.sovitaPeliKenttaan(pelilauta);
				return (Muoto)m;
			case 3:
				I i = new I();
				i.sovitaPeliKenttaan(pelilauta);
				return (Muoto)i;
				
			default:
				L defaultShape = new L();
				defaultShape.sovitaPeliKenttaan(pelilauta);
				return (Muoto)defaultShape;
		}
	}
	// Palikan grafiikoiden luonti.

	public void asetaLiikkuvaTaulukko(int xKoordinaatti, int yKoordinaatti, int arvo) {
		liikkuvaTaulukko[xKoordinaatti][yKoordinaatti] = 1;
	}
	
	public int[][] annaStaattinenTaulukko() {
		return staattinenTaulukko;
	}
	
	public int[][] annaLiikkuvaTaulukko() {
		return liikkuvaTaulukko;
	}

}
