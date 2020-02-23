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

	private boolean play = false;
	private boolean palikkaLiikkeessa = true;
	private int score = 0;
	private Timer timer;
	private int delay = 150;
	private Pelilauta pelilauta;
	private int rivi, sarake;
	private boolean initialized;
	int[][] uusiArray;
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
	// 1 - sininen
	// 2 - punainen
	public void liikutaPalikkaa() {
		for(int i=rivi-1; i>=0; i--) {
			for(int j=sarake-1; j>=0; j--) {
				
				
				if(pelilauta.staattinenLauta[i][j] ==0 && pelilauta.palikkaKoordinaatit[i][j] != 0) {
					if(saakoLiikkua(i, j)) {
						int koordinaattiArvo = pelilauta.lauta2D[i][j];
						
						if(pelilauta.palikkaKoordinaatit[i][j] != 0 && palikkaLiikkeessa) {
							// P‰ivit‰ v‰rikartta
							pelilauta.lauta2D[i][j] = 0;
							pelilauta.lauta2D[i+1][j] = koordinaattiArvo;
							
							// P‰ivit‰ tippuvan palikan koordinaatit
							pelilauta.palikkaKoordinaatit[i][j] = 0;
							pelilauta.palikkaKoordinaatit[i+1][j] = 1;
						}
					} else {
						if(palikkaLiikkeessa) {
							updateStaattinenLauta(i,j);
							
						}
						//System.out.println("stop" + " " + i + " " + j);
						for (int[] row: pelilauta.palikkaKoordinaatit)
						    Arrays.fill(row, 0);
					}
				}
			}
		}
		//System.out.println("---------------");
		
	}
	
	public void liikuOikealle() {
		for(int i=rivi-1; i>=0; i--) {
			for(int j=sarake-1; j>=0; j--) {
				int koordinaattiArvo = pelilauta.lauta2D[i][j];
				if(pelilauta.staattinenLauta[i][j] ==0 && pelilauta.palikkaKoordinaatit[i][j] != 0 && j<=sarake-1) {
					// P‰ivit‰ v‰rikartta
					pelilauta.lauta2D[i][j] = 0;
					pelilauta.lauta2D[i][j+1] = koordinaattiArvo;
					
					// P‰ivit‰ tippuvan palikan koordinaatit
					pelilauta.palikkaKoordinaatit[i][j] = 0;
					pelilauta.palikkaKoordinaatit[i][j+1] = 1;
				}
			}
		}		
	}
	
	public void liikuVasemmalle() {
		for(int i=0; i<rivi; i++) {
			for(int j=0; j<sarake; j++) {
				int koordinaattiArvo = pelilauta.lauta2D[i][j];
				if(pelilauta.staattinenLauta[i][j] ==0 && pelilauta.palikkaKoordinaatit[i][j] != 0 && j<=sarake-1) {
					// P‰ivit‰ v‰rikartta
					pelilauta.lauta2D[i][j] = 0;
					pelilauta.lauta2D[i][j-1] = koordinaattiArvo;
					
					// P‰ivit‰ tippuvan palikan koordinaatit
					pelilauta.palikkaKoordinaatit[i][j] = 0;
					pelilauta.palikkaKoordinaatit[i][j-1] = 1;
				}
			}
		}		
	}
	
	public void updateStaattinenLauta(int x, int y) {
		System.out.println("("+ x + ", " + y);
		pelilauta.staattinenLauta[x][y] = 1;
		palikkaLiikkeessa = false;
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
		if(pelilauta.staattinenLauta[x+lisaysX][y+lisaysY] == 0 && pelilauta.palikkaKoordinaatit[x][y] == pelilauta.palikkaKoordinaatit[x+lisaysX][y+lisaysY]) {
			pelilauta.staattinenLauta[x+lisaysX][y+lisaysY]=currentPalikka.arvo;
			updateNaapuri(x+lisaysX, y + lisaysY);
		}
	}
	
	
	
	public boolean saakoLiikkua(int i, int j) {
		
		if(i==19) {
			i=18;
		}
		if((pelilauta.palikkaKoordinaatit[i+1][j] == 0 && pelilauta.staattinenLauta[i+1][j]==0)) {
				if(tarkistaViereiset(i,j)) {	
					return true;
				}
		}
		
		return false;
	}
	
	public boolean tarkistaViereiset(int x, int y) {
		try {
			for(int i=1; i<=currentPalikka.koordinaatit.length; i++) {
				
				if(pelilauta.palikkaKoordinaatit[x][y-i]==1 && pelilauta.staattinenLauta[x+1][y-i]!=0) {
					System.out.println("(" + x + ", " + y + ")");
					return false;
				}
			}
			// Helpompi ignoorata indexOutOfBoundsException kokonaan, sill‰ se ei vaikuta mihink‰‰n t‰ss‰ metodissas.
		} catch(Exception e) {}
		return true;
	}
	
	// T‰ss‰ metodissa p‰‰tet‰‰n palikan muoto ja v‰ri.
	// HYVIN KESKENERƒINEN !!!!!
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
		pelilauta.luoLauta((Graphics2D)g);
		if(!initialized) {
			luoPalikka();	
			initialized = true;
		}
		
	}
	
	
	// Ruudun p‰ivistys tapahtuu t‰‰ll‰
	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		repaint();
		liikutaPalikkaa();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Pressed key");
			
			luoPalikka();
			palikkaLiikkeessa=true;
		} else if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			liikuOikealle();
		} else if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			liikuVasemmalle();
		} else if(arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println(Arrays.deepToString(pelilauta.palikkaKoordinaatit).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
			
			pelilauta.palikkaKoordinaatit[0][5] = 0;
			pelilauta.palikkaKoordinaatit[0][4] = 0;
			System.out.println(Arrays.deepToString(pelilauta.palikkaKoordinaatit).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
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
