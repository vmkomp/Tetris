import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Pelilogiikka extends JPanel implements KeyListener, ActionListener{

	private int score = 0;
	private Timer timer;
	private int delay = 150;
	private Pelilauta pelilauta;
	private int rivi, sarake;
	private boolean initialized;
	Palikat currentPalikka;
	
	public Pelilogiikka() {
		
		initialized = false;
		rivi = 20;
		sarake = 10;
		pelilauta = new Pelilauta(rivi, sarake);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	// KESKENERƒINEN!! Keksik‰‰ lis‰‰ logiikkaa palikoille ja luokaa uusia metodeja.
	public void liikutaPalikkaa() {
		
		for(int i=rivi-2; i>=0; i--) {
			for(int j=sarake-1; j>=0; j--) {
				
				if(onkoVarattuStaattinen(i,j,0) && onkoVarattuLiikkuva(i,j,1)) {
					if(saakoLiikkua(i, j)) {
						updateLiikkuvaKoordinaatti(i,j,0);
						updateLiikkuvaKoordinaatti(i+1, j, 1);
						
					} else {
						updateStaattinenLauta(i,j);
						for (int[] row: pelilauta.annaLiikkuvaTaulukko())
						    Arrays.fill(row, 0);
					}
				}
			}
		}	
	}
	
	// Oli helpompi tehd‰ kummallekkin suunnalle oma metodi.
	// Oikealle liikkuminen renderˆid‰‰n taulukon oikeasta alakulmasta alkaen.
	public void liikuOikealle() {
		
		for(int i=rivi-1; i>=0; i--) {
			for(int j=sarake-1; j>=0; j--) {
				if(onkoVarattuStaattinen(i,j,0) && onkoVarattuLiikkuva(i,j,1) && j<=sarake-1) {

					updateLiikkuvaKoordinaatti(i,j,0);
					updateLiikkuvaKoordinaatti(i, j+1, 1);
				}
			}
		}		
	}
	
	// Vasemmalla liikkuminen renderˆid‰‰n vasemmalta yl‰kulmasta alkaen.
	public void liikuVasemmalle() {
		
		for(int i=0; i<rivi; i++) {
			for(int j=0; j<sarake; j++) {
				
				if(onkoVarattuStaattinen(i,j,0) && onkoVarattuLiikkuva(i,j,1) && j<=sarake-1) {
					
					updateLiikkuvaKoordinaatti(i,j,0);
					updateLiikkuvaKoordinaatti(i, j-1, 1);
				}
			}
		}		
	}
	
	public void updateStaattinenLauta(int x, int y) {
		
		updateStaattinenKoordinaatti(x,y,currentPalikka.annaVariArvo());
		updateNaapuri(x,y);
	}
	
	/**
	 * Laudan renderˆinti alkaa oikeasta alakulmasta, palikan pisteen pit‰‰ tarkistaa kaikki palikan viereisetkin pisteet silt‰ varalta, ettei
	 * 
	 * @param x
	 * @param y
	 */
	public void updateNaapuri(int x, int y) {
	
			if(y< sarake-1) {
				tarkistaNaapuri(x,y,0,1);
			}
			
			if(y>0) {
				tarkistaNaapuri(x,y,0,-1);
			}
			
			if(x<rivi-1) {
				tarkistaNaapuri(x,y,1,0);
			}
			
			tarkistaNaapuri(x,y,-1,0);
	}
	
	public void tarkistaNaapuri(int x, int y, int lisaysX, int lisaysY) {
		
		if(onkoVarattuStaattinen(x+lisaysX, y+lisaysY, 0) && onkoVarattuLiikkuva(x,y, pelilauta.annaLiikkuvaTaulukko()[x+lisaysX][y+lisaysY])){
			
			updateStaattinenKoordinaatti(x+lisaysX, y+lisaysY, currentPalikka.annaVariArvo());
			updateNaapuri(x+lisaysX, y + lisaysY);
		}
	}
	
	public boolean saakoLiikkua(int i, int j) {

		if((onkoVarattuLiikkuva(i+1,j,0) && onkoVarattuStaattinen(i+1,j,0))) {
				if(tarkistaViereiset(i,j)) {	
					return true;
				}
		}
		
		return false;
	}
	
	public boolean tarkistaViereiset(int x, int y) {
		try {
			int palikanKokoX = currentPalikka.annaKoordinaatit().length;
			for(int i=1; i<= palikanKokoX; i++) {
				
				if(onkoVarattuLiikkuva(x,y-i,1) && !onkoVarattuStaattinen(x+1,y-i,0)) {
					return false;
				}
			}
			// Helpompi ignoorata indexOutOfBoundsException kokonaan, sill‰ se ei vaikuta mihink‰‰n t‰ss‰ metodissas.
		} catch(Exception e) {}
		return true;
	}
	
	public boolean onkoVarattuStaattinen(int i, int j, int onkoVarattu) {
		return pelilauta.annaStaattinenTaulukko()[i][j] == onkoVarattu;
	}
	
	public boolean onkoVarattuLiikkuva(int i, int j, int onkoVarattu) {
		return pelilauta.annaLiikkuvaTaulukko()[i][j] == onkoVarattu;
	}
	
	public void updateLiikkuvaKoordinaatti(int i, int j, int uusiArvo) {
		pelilauta.annaLiikkuvaTaulukko()[i][j] = uusiArvo;
	}
	
	public void updateStaattinenKoordinaatti(int i, int j, int uusiArvo) {
		pelilauta.annaStaattinenTaulukko()[i][j] = uusiArvo;
	}
	
	// T‰ss‰ metodissa p‰‰tet‰‰n palikan muoto ja v‰ri.
	// HYVIN KESKENERƒINEN !!!!!!!!!--------------------
	public void luoPalikka() {
		Random r = new Random();
		int arvo = r.nextInt(4)+1;
		int arvo2 = r.nextInt(4)+1;
		
		int[][] pArray = {{arvo, 0,0}, {arvo,arvo,arvo}, {0,0,0}};
		
		int[][] pArray2 = {{arvo, 0,0}, {arvo,arvo,arvo}, {0,0,0}};
		int[][] pArray3 = {{arvo, 0,arvo}, {arvo,arvo,arvo}, {0,0,0}};
		int[][] pArray4 = {{arvo, arvo,arvo}, {0,0,0}, {0,0,0}};
		int[][] pArray5 = {{arvo, arvo,arvo}, {arvo,arvo,arvo}, {0,0,0}};
		ArrayList<int[][]> muodot = new ArrayList<int[][]>();
		muodot.add(pArray);
		muodot.add(pArray2);
		muodot.add(pArray3);
		muodot.add(pArray4);
		muodot.add(pArray5);
		System.out.println(arvo2);
		
		currentPalikka = new Palikat(muodot.get(0), arvo2, pelilauta);
		//System.out.println(Arrays.deepToString(pelilauta.palikkaKoordinaatit).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}
	
	/**
	 * Piirt‰‰ ikkunan, pelilaudan ja kaikki muu grafiikkaan kuuluvan. 
	 * P‰ivitys tapahtuu delayn(1000ms) v‰lein.
	 * 
	 *  @param g 	Vastaa pelilaudan ulkopuolisista grafiikoista.
	 */
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		pelilauta.luoLauta((Graphics2D)g, currentPalikka);
		if(!initialized) {
			luoPalikka();	
			initialized = true;
		}
	}
	
	
	// Ruudun p‰ivistys tapahtuu t‰‰ll‰
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//System.out.println(Arrays.deepToString(pelilauta.palikkaKoordinaatit).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		timer.start();
		repaint();
		liikutaPalikkaa();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()) {
			case(KeyEvent.VK_ENTER):
				luoPalikka();
				break;
			case(KeyEvent.VK_RIGHT):
				liikuOikealle();
				break;
			case(KeyEvent.VK_LEFT):
				liikuVasemmalle();
				break;
		}	
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
