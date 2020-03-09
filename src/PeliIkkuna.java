import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PeliIkkuna implements ActionListener {
	
	JFrame peliIkkuna;
	JPanel panelKuva;
	JPanel panelToiminnot;
	JFrame valikkoIkkuna;
	JButton buttonTallenna;
	JButton buttonValikko;
	Tietokanta tietokanta;
	Pelilogiikka pelilogiikka;
	
	
	//tämä kutsutaan jos aloitetaan uusi peli
	public PeliIkkuna(Tietokanta tietokanta) {
		
		this.tietokanta = tietokanta;
		pelilogiikka = new Pelilogiikka();
		luoGrafiikat();
		
	}
	
	// Tämä kutsutaan jos ladataan peli tietokannasta
	public PeliIkkuna(Tietokanta tietokanta, int[][] ladattuPeli, int ladattuTulos) {
		
		this.tietokanta = tietokanta;
		pelilogiikka = new Pelilogiikka(ladattuPeli, ladattuTulos);
		luoGrafiikat();
	}
	
	public void luoGrafiikat() {
		
		JPanel mainPanel = new JPanel();
		
		
		peliIkkuna = new JFrame();
		peliIkkuna.getContentPane().setBackground( Color.PINK );
		peliIkkuna.setSize(650,490);
		peliIkkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		peliIkkuna.setTitle("Peli");
		peliIkkuna.setVisible(true);
		
		 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setBackground(Color.BLACK); 

		panelKuva = new JPanel();
		panelKuva.setBackground(Color.BLACK);
		
		panelToiminnot = new JPanel();
		panelToiminnot.setBackground(Color.BLACK);
		panelToiminnot.setLayout(new BoxLayout(panelToiminnot, BoxLayout.Y_AXIS));

		ImageIcon originalImage = new ImageIcon("src/TetrisVelho.png");
		panelKuva.add(alustaKuva(originalImage));
		

		buttonTallenna = new JButton("Tallenna peli");
		buttonTallenna.addActionListener(this);
		
		buttonValikko = new JButton("Valikkoon");
		buttonValikko.addActionListener(this);
		
		panelKuva.add(buttonTallenna);
		panelToiminnot.add(buttonValikko);
		
		panelToiminnot.add(buttonTallenna);
		
		peliIkkuna.setContentPane(mainPanel);
		mainPanel.add(pelilogiikka);
		mainPanel.add(panelKuva);
		mainPanel.add(panelToiminnot);
	}
	
	public void piilotaPeliIkkuna() {
		peliIkkuna.setVisible(false);
	}
	
	public void naytaPeliIkkuna() {
		peliIkkuna.setVisible(true);
	}
	
	public JFrame annaValikkoIkkuna() {
		return valikkoIkkuna;
	}
	
	public static JLabel alustaKuva(ImageIcon originalImage) {
		
		Image image = originalImage.getImage();
		Image newimg = image.getScaledInstance(originalImage.getIconWidth()/3, originalImage.getIconHeight()/3,  java.awt.Image.SCALE_SMOOTH);
		originalImage = new ImageIcon(newimg);
		JLabel imgLabel = new JLabel(originalImage, JLabel.CENTER);
		imgLabel.setSize(150,350);
		return imgLabel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == buttonValikko) {
			System.out.println("Nappi yksi");
			Main.luoValikkoIkkuna(tietokanta);
			peliIkkuna.setVisible(false);
		}
		
		if (e.getSource() == buttonTallenna) {
			String peliKentta = tietokanta.muutaMerkkijonoksi(pelilogiikka.annaStaattinenTaulukko());
			System.out.println(peliKentta);
			try {
				tietokanta.tallennaPeli(peliKentta, Main.score);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println("Peli tallennettu(?)");
		}
	}
}
