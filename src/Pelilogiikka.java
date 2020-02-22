import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

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
	
	// KESKENERÄINEN!! Keksikää lisää logiikkaa palikoille ja luokaa uusia metodeja.
	// 1 - sininen
	// 2 - punainen
	public void liikutaPalikkaa() {
		for(int i=rivi-1; i>=0; i--) {
			for(int j=sarake-1; j>=0; j--) {
				
				if(pelilauta.staattinenLauta[i][j] ==0 && pelilauta.palikkaKoordinaatit[i][j] == 1) {
					if(saakoLiikkua(i, j)) {
						int koordinaattiArvo = pelilauta.lauta2D[i][j];
						
						if(pelilauta.palikkaKoordinaatit[i][j] != 0 && palikkaLiikkeessa) {
							pelilauta.lauta2D[i][j] = 0;
							pelilauta.lauta2D[i+1][j] = koordinaattiArvo;
							pelilauta.palikkaKoordinaatit[i][j] = 0;
							pelilauta.palikkaKoordinaatit[i+1][j] = 1;
						}
					} else {
						if(palikkaLiikkeessa) {
							updateStaattinenLauta(i,j);
						}
						System.out.println("stop" + " " + i + " " + j);
						for (int[] row: pelilauta.palikkaKoordinaatit)
						    Arrays.fill(row, 0);
						
						System.out.println(Arrays.deepToString(pelilauta.palikkaKoordinaatit).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
						break;
						
					}
				}
			}
		}
		//System.out.println("---------------");
		
	}
	
	public void liikuSivulle() {
		for(int i=rivi-1; i>=0; i--) {
			for(int j=sarake-1; j>=0; j--) {
				
			}
		}		
	}
	
	public void updateStaattinenLauta(int x, int y) {
		pelilauta.staattinenLauta[x][y] = 1;
		palikkaLiikkeessa = false;
		tarkistaNaapuri(x,y);
	}
	
	public void tarkistaNaapuri(int x, int y) {
		if(y< sarake-1) {
			if(pelilauta.staattinenLauta[x][y+1] == 0 && pelilauta.lauta2D[x][y] == pelilauta.lauta2D[x][y+1]) {
				pelilauta.staattinenLauta[x][y+1]=1;
				tarkistaNaapuri(x, y+1);
			}
		}
		
		if(pelilauta.staattinenLauta[x][y-1] == 0 && pelilauta.lauta2D[x][y] == pelilauta.lauta2D[x][y-1]) {
			pelilauta.staattinenLauta[x][y-1]=1;
			tarkistaNaapuri(x, y-1);
		}
		
		if(x<rivi-1) {
			if(pelilauta.staattinenLauta[x+1][y] == 0 && pelilauta.lauta2D[x][y] == pelilauta.lauta2D[x+1][y]) {
				System.out.println(x);
				pelilauta.staattinenLauta[x+1][y]=1;
				tarkistaNaapuri(x+1, y);
			}
		}

		if(pelilauta.staattinenLauta[x-1][y] == 0 && pelilauta.lauta2D[x][y] == pelilauta.lauta2D[x-1][y] && x>0) {
			pelilauta.staattinenLauta[x-1][y]=1;
			tarkistaNaapuri(x-1, y);
		}
		//System.out.println(Arrays.deepToString(pelilauta.staattinenLauta).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}
	
	
	
	public boolean saakoLiikkua(int i, int j) {
		if(i<=19) {
			if(i==19) {
				i=18;
			}
			if((pelilauta.lauta2D[i+1][j] == 0 && pelilauta.staattinenLauta[i+1][j]==0)) {
				return true;
			}
		}
		return false;
	}
	
	// Tässä metodissa päätetään palikan muoto ja väri.
	public void luoPalikka() {
		//System.out.println(Arrays.deepToString(pelilauta.).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		int arvo = 2;
		int[][] pArray = {{arvo, 0,arvo,0},{arvo,arvo,arvo}};
		
		
		currentPalikka = new Palikat(pArray, arvo, pelilauta);
		System.out.println(Arrays.deepToString(pelilauta.palikkaKoordinaatit).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}
	
	/**
	 * Piirtää ikkunan, pelilaudan ja kaikki muu grafiikkaan kuuluvan. 
	 * Päivitys tapahtuu delayn(1000ms) välein.
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
	
	
	// Ruudun päivistys tapahtuu täällä
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
			System.out.println("Presse key");
			
			luoPalikka();
			palikkaLiikkeessa=true;
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
