import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Pelilogiikka extends JPanel implements KeyListener, ActionListener{

	private Timer timer;
	private Muoto nykyinenMuoto;
	private Pelilauta pelilauta;
	
	private int delay = 150;
	private int rivi, sarake;
	
	public int pisteet;
	
	int[][] staattinenTaulukko;
	
	
	public Pelilogiikka() {
		
		pisteet = 0;
		Main.score=0;
		rivi = 20;
		sarake = 10;
		pelilauta = new Pelilauta(rivi, sarake);
		
		

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
	}
	
	enum Liikkumistyyppi {
		SIVULLE,
		ALAS
	}
	
	
	public void liikutaAlas() {
		
		Liikkumistyyppi liikkumistyyppi = Liikkumistyyppi.ALAS;
		
		// 1,0: ALAS
		// 0,-1: VASEMMALLE
		// 0, 1: OIKEALLE
		IteroituTaulukko iteroituTaulukko = new IteroituTaulukko(1, 0);
		iteroituTaulukko.iteroiTaulukko();
		tuhoaRivi();
		
		paivitaTaulukko(iteroituTaulukko.saakoLiikkua(), iteroituTaulukko.pisteet(), liikkumistyyppi,1,0);
	}
	
	
	public void liikuSivulle(int suunta) {
		
		Liikkumistyyppi liikkumistyyppi = Liikkumistyyppi.SIVULLE;
		// 1,0: ALAS
		// 0,-1: VASEMMALLE
		// 0, 1: OIKEALLE
		IteroituTaulukko iteroituTaulukko = new IteroituTaulukko(0, suunta);
		iteroituTaulukko.iteroiTaulukko();
		
		paivitaTaulukko(iteroituTaulukko.saakoLiikkua(), iteroituTaulukko.pisteet(), liikkumistyyppi,0,suunta);
	}
	
	public void kaannaMuoto() {
		
		IteroituTaulukko iteroituTaulukko = new IteroituTaulukko(0,-1);
		IteroituTaulukko iteroituTaulukko2 = new IteroituTaulukko(0,1);
		iteroituTaulukko.iteroiTaulukko();
		iteroituTaulukko2.iteroiTaulukko();
		if(iteroituTaulukko.saakoLiikkua() && iteroituTaulukko2.saakoLiikkua()) {
			paivitaTaulukkoRotaatio(iteroituTaulukko.pisteet(), iteroituTaulukko.normalisointiX(), iteroituTaulukko.normalisointiY());
		}
		
	}
	
	private class IteroituTaulukko{
		
		public boolean saakoLiikkua;
		public ArrayList<Piste> pisteet;
		private int lisaaX, lisaaY, normalisointiX, normalisointiY;
		
		public IteroituTaulukko(int lisaaX, int lisaaY){
			
			pisteet = new ArrayList<Piste>();
			saakoLiikkua = true;
			this.lisaaX = lisaaX;
			this.lisaaY = lisaaY;
		}

		public boolean saakoLiikkua() {
			return saakoLiikkua;
		}
		public ArrayList<Piste> pisteet(){
			return pisteet;
		}
		public int normalisointiX() {
			return normalisointiX;
		}
		public int normalisointiY() {
			return normalisointiY;
		}
		
		public void iteroiTaulukko() {
			normalisointiX = 1000;
			normalisointiY = 1000;
			for(int i=rivi-1; i>=0; i--) {
				for(int j=sarake-1; j>=0; j--) {
					if(onkointattuLiikkuva(i,j,1)) {
						
						try {
							if(!onkointattuStaattinen(i+lisaaX,j+lisaaY,0)) {
								saakoLiikkua = false;
							}
							
								pisteet.add(new Piste(i,j));
								
								// Rotaatiota intten palikan koordinaatiston normalisointiin vaaditut luvut.
								normalisointiX = paivitaNormalisaatioLuku(normalisointiX, i);
								normalisointiY = paivitaNormalisaatioLuku(normalisointiY, j);
								
								
						//exceptionin tapahtuessa on ulkona pelilaudasta, jolloin halutaan saada liike lakkaamaan.
						} catch(ArrayIndexOutOfBoundsException e) {
							pisteet.add(new Piste(i,j));
							saakoLiikkua = false;
						}

					}
				}
			}
		}
	}
	
	public void luoUusiMuoto() {
		int[][] tyhjaTaulukko = new int[rivi][sarake];
		nollaaTaulukko(tyhjaTaulukko);
		if(Arrays.deepEquals(tyhjaTaulukko, pelilauta.annaLiikkuvaTaulukko())) {
			luoPalikka();
		}
	}
	
	public void tuhoaRivi() {

		boolean isFilled = false;
		for (int row = 0; row < pelilauta.annaStaattinenTaulukko().length; row++) {
		    isFilled = true;
		    for (int col = 0; col < pelilauta.annaStaattinenTaulukko()[row].length; col++) {
		        if (pelilauta.annaStaattinenTaulukko()[row][col] == 0) {
		            isFilled = false;
		        }
		    }
		    if(isFilled) {
		    	Arrays.fill(pelilauta.annaStaattinenTaulukko()[row], 0);
		    	Main.score ++;
		    }
		}

	}
	
	public int paivitaNormalisaatioLuku(int pienin, int vertailtava) {
		if(pienin > vertailtava) {
			return vertailtava;
		}
		return pienin;
	}
	
	public void paivitaTaulukkoRotaatio(ArrayList<Piste> pisteet, int normalisointiX, int normalisointiY) {
		//Luo pisteet
		luoUudetPisteetRotaatio(pisteet, normalisointiX, normalisointiY);
		
		// Lis‰‰ uudet pisteet liikkuvaan koordinaatistoon.
		lisaaUudetPisteetRotaatio(pisteet, normalisointiX, normalisointiY);
		
	}
	
	public void luoUudetPisteetRotaatio(ArrayList<Piste> pisteet, int normalisointiX, int normalisointiY) {
		
		for(int i=0; i<pisteet.size(); i++) {
			int uusiX = pisteet.get(i).annaX()-normalisointiX;
			int uusiY = pisteet.get(i).annaY()-normalisointiY;
			int uudempiX =  -uusiY;
			int uudempiY = uusiX;
			pisteet.set(i, new Piste(uudempiX, uudempiY));
		}
	}
	
	public void lisaaUudetPisteetRotaatio(ArrayList<Piste> pisteet, int normalisointiX, int normalisointiY) {
		
		nollaaTaulukko(pelilauta.annaLiikkuvaTaulukko());
		
		for(Piste piste : pisteet) {
			updateLiikkuvaKoordinaatti(piste.annaX()+normalisointiX, piste.annaY()+ normalisointiY,1);
		}
		pisteet.clear();
	}
	
	public void paivitaTaulukko(boolean saakoLiikkua, ArrayList<Piste> pisteet, Liikkumistyyppi liikkumistyyppi, int lisaaX, int lisaaY) {
		
		if(saakoLiikkua) {
			for(Piste point : pisteet) {
				updateLiikkuvaKoordinaatti(point.annaX(),point.annaY(),0);
				
			}
			for(Piste point : pisteet) {
				updateLiikkuvaKoordinaatti(point.annaX()+lisaaX,point.annaY()+lisaaY,1);
			}
			
		} else if(liikkumistyyppi == Liikkumistyyppi.ALAS) {
	
			for(int i=0; i<pisteet.size(); i++) {
				
				int xKoordinaatti = pisteet.get(i).annaX();
				int yKoordinaatti = pisteet.get(i).annaY();
				updateStaattinenKoordinaatti(xKoordinaatti, yKoordinaatti, nykyinenMuoto.annaVariArvo());
			}
			
			nollaaTaulukko(pelilauta.annaLiikkuvaTaulukko());
			pisteet.clear();
			
			}
	}
	
	public void nollaaTaulukko(int[][] taulukko) {
		
		for (int[] row: taulukko)
			Arrays.fill(row, 0);
	}

	private boolean onkointattuStaattinen(int i, int j, int onkointattu) {
		return pelilauta.annaStaattinenTaulukko()[i][j] == onkointattu;
	}
	
	private boolean onkointattuLiikkuva(int i, int j, int onkointattu) {
		return pelilauta.annaLiikkuvaTaulukko()[i][j] == onkointattu;
	}
	
	public void updateLiikkuvaKoordinaatti(int i, int j, int uusiArvo) {
		pelilauta.annaLiikkuvaTaulukko()[i][j] = uusiArvo;
	}
	
	public void updateStaattinenKoordinaatti(int i, int j, int uusiArvo) {
		pelilauta.annaStaattinenTaulukko()[i][j] = uusiArvo;
	}
	

	public void luoPalikka() {
		int muotojenMaara = 4;
		Random r = new Random();
		int arvo = r.nextInt(muotojenMaara)+1;
		
		nykyinenMuoto = pelilauta.alustaMuoto(arvo, pelilauta);
	
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1, 1, 250, 592);
		pelilauta.luoLauta((Graphics2D)g, nykyinenMuoto, Main.score);
		g.fillRect(0, 500, 20, 20);
		g.setColor(Color.RED);	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		luoUusiMuoto();
		timer.start();
		repaint();
		staattinenTaulukko = pelilauta.annaStaattinenTaulukko();
		liikutaAlas();
		
	}
	
	public int[][] annaStaattinenTaulukko() {
		return staattinenTaulukko;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()) {
			case(KeyEvent.VK_ENTER):
				luoPalikka();
				break;
			case(KeyEvent.VK_RIGHT):
				liikuSivulle(1);
				break;
			case(KeyEvent.VK_LEFT):
				liikuSivulle(-1);
				break;
			case(KeyEvent.VK_SPACE):
				kaannaMuoto();
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