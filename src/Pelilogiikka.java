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

	private Timer timer;
	private Muoto currentPalikka;
	private Pelilauta pelilauta;
	
	private int delay = 150;
	private int rivi, sarake;
	
	private boolean initialized;
	
	
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
	
	private class IteroituTaulukko{
		
		public boolean saakoLiikkua;
		public ArrayList<Piste> pisteet;
		private int lisaaX, lisaaY;
		
		public IteroituTaulukko(int lisaaX, int lisaaY){
			
			pisteet = new ArrayList<Piste>();
			saakoLiikkua = true;
			this.lisaaX = lisaaX;
			this.lisaaY = lisaaY;
		}
		
		public IteroituTaulukko() {
			pisteet = new ArrayList<Piste>();
			saakoLiikkua = true;
		}
		
		public boolean saakoLiikkua() {
			return saakoLiikkua;
		}
		public ArrayList<Piste> pisteet(){
			return pisteet;
		}
		public void iteroiTaulukko() {

			for(int i=rivi-1; i>=0; i--) {
				for(int j=sarake-1; j>=0; j--) {
					if(onkoVarattuLiikkuva(i,j,1)) {
						
						try {
							if(!onkoVarattuStaattinen(i+lisaaX,j+lisaaY,0)) {
								saakoLiikkua = false;
							}
								pisteet.add(new Piste(i,j));
								
						} catch(ArrayIndexOutOfBoundsException e) {
							pisteet.add(new Piste(i,j));
							saakoLiikkua = false;
						}

					}
				}
			}
		}
		
		public void iteroiTaulukkoRotaatio() {
			int suurinX = 1000;
			int suurinY = 1000;
			for(int i=rivi-1; i>=0; i--) {
				for(int j=sarake-1; j>=0; j--) {
					if(onkoVarattuLiikkuva(i,j,1)) {
						pisteet.add(new Piste(i,j));
						if(suurinX > i) {
							suurinX = i;
						}
						if(suurinY > j) {
							suurinY = j;
						}
					}
				}
			}
			for(int i=0; i<pisteet.size(); i++) {
				int uusiX = pisteet.get(i).annaX()-suurinX;
				int uusiY = pisteet.get(i).annaY()-suurinY;
				pisteet.set(i, new Piste(uusiX, uusiY));
			}
			
			for(Piste piste : pisteet) {
				System.out.println(piste.annaX() +  ", " +  piste.annaY());
				int x_new = piste.annaY();
				int y_new = 1 - (piste.annaX() - (1));
				// TODO ETSI MUODON KESKIKOHTA SEN PALIKOIDEN MÄÄRÄN PERUSTEELLA
				// TODO TEE TÄMÄ METODI MUOTO CLASSIIN!
				
				//updateLiikkuvaKoordinaatti(piste.annaX()+suurinX, piste.annaY()+ suurinX,0);
				//updateLiikkuvaKoordinaatti(x_new + suurinX, y_new+ suurinY, 1);
			}
			
		}
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
				updateStaattinenKoordinaatti(xKoordinaatti, yKoordinaatti, currentPalikka.annaVariArvo());
			}
			
			nollaaTaulukot(pisteet);
			
			}
	}
	
	public void nollaaTaulukot(ArrayList<Piste> pisteet) {
		
		pisteet.clear();
		
		for (int[] row: pelilauta.annaLiikkuvaTaulukko())
		    Arrays.fill(row, 0);
	}

	private boolean onkoVarattuStaattinen(int i, int j, int onkoVarattu) {
		return pelilauta.annaStaattinenTaulukko()[i][j] == onkoVarattu;
	}
	
	private boolean onkoVarattuLiikkuva(int i, int j, int onkoVarattu) {
		return pelilauta.annaLiikkuvaTaulukko()[i][j] == onkoVarattu;
	}
	
	public void updateLiikkuvaKoordinaatti(int i, int j, int uusiArvo) {
		pelilauta.annaLiikkuvaTaulukko()[i][j] = uusiArvo;
	}
	
	public void updateStaattinenKoordinaatti(int i, int j, int uusiArvo) {
		pelilauta.annaStaattinenTaulukko()[i][j] = uusiArvo;
	}
	
	// HYVIN KESKENERÄINEN !!!!!!!!!--------------------
	// VAIN TESTEJÄ VARTEN
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
		
		currentPalikka = new Muoto(muodot.get(arvo), arvo2, pelilauta);
	
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		pelilauta.luoLauta((Graphics2D)g, currentPalikka);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!initialized) {
			luoPalikka();
			initialized = true;
		}
		timer.start();
		repaint();
		if(initialized) {
			liikutaAlas();
		}
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
				IteroituTaulukko iteroituTaulukko = new IteroituTaulukko();
				iteroituTaulukko.iteroiTaulukkoRotaatio();
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
