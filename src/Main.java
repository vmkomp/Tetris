import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Main {
	
	static int score = 0;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Tietokanta tietokanta = new Tietokanta();
		alustaPeli(tietokanta);
	}
	
	public static void alustaPeli(Tietokanta tietokanta) throws ClassNotFoundException, SQLException {
		tietokanta.getConnection();
		luoValikkoIkkuna(tietokanta);
	}
	
	
	public static void luoValikkoIkkuna(Tietokanta tietokanta) {
		Paavalikko paaValikko = new Paavalikko(tietokanta);
	}
	
	
	
	public static void luoPeliIkkuna(Tietokanta tietokanta) {
		 PeliIkkuna peliIkkuna = new PeliIkkuna(tietokanta);
	}
	
	public static void luoLadattuPeliIkkuna(Tietokanta tietokanta, int[][] ladattuKentta, int ladattuTulos) {
		PeliIkkuna peliIkkuna = new PeliIkkuna(tietokanta, ladattuKentta, ladattuTulos);
	}

}
