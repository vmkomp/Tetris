import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Pelilogiikka extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	private Timer timer;
	private int delay = 1000;
	private Pelilauta pelilauta;
	private int rivi, sarake;
	
	public Pelilogiikka() {
		
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
		for(int i=rivi-1; i>=0; i--) {
			for(int j=sarake-1; j>=0; j--) {
				if(pelilauta.lauta2D[i][j] == 1) {
					System.out.println(j);
					pelilauta.lauta2D[i][j] = 0;
					pelilauta.lauta2D[i+1][j] = 1;
				}
			}
		}
	}
	
	// T‰ss‰ metodissa p‰‰tet‰‰n palikan muoto ja v‰ri.
	public void luoPalikka(Graphics g) {
		int[][] pArray = {{1,1,1 },{1, 1,1}};
		
		Palikat palikka = new Palikat(pArray, Color.blue, pelilauta, (Graphics2D)g);
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
		luoPalikka(g);
	}
	
	
	// Ruudun p‰ivistys tapahtuu t‰‰ll‰
	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		//liikutaPalikkaa();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
