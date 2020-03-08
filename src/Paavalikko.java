import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// M‰‰ritt‰‰ pelin p‰‰valikon
public class Paavalikko extends JPanel implements ActionListener {

	int count = 0;
	JLabel label;
	public JPanel panel;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JFrame valikkoIkkuna;
	Tietokanta tietokanta;
	
	App lista = new App();
	TopLista l = new TopLista();

	public Paavalikko(Tietokanta tietokanta) {
		
		this.tietokanta = tietokanta;
		
		valikkoIkkuna = new JFrame();
		valikkoIkkuna.setBackground(Color.BLACK);
		valikkoIkkuna.setBounds(10,10,1000,500);
		valikkoIkkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		valikkoIkkuna.setTitle("P‰‰valikko");
		

		// Nappula1
		button1 = new JButton("Uusi peli");
		

		button1.addActionListener(this);

		// Nappula2
		button2 = new JButton("N‰yt‰ top 10");
		button2.addActionListener(this);

		// Nappula3
		button3 = new JButton("Tallenna peli");
		button3.addActionListener(this);

		// Nappula4
		button4 = new JButton("Lataa peli");
		button4.addActionListener(this);

		label = new JLabel();

		panel = new JPanel();
		
		panel.setBorder(BorderFactory.createEmptyBorder(40, 200, 100, 200)); // Ikkunan koko
		
		//panel.setLayout(new GridLayout(0, 1));

		// Lis‰‰ kuvan p‰‰valikkoon
		JLabel imgLabel = new JLabel(new ImageIcon("src/TetrisLogo.png"));
		
		imgLabel.setBounds(10,0,0,0);
		panel.add(imgLabel);

		panel.add(button1);
		panel.add(button2);
		//panel.add(button3);
		
		panel.add(button4);
		panel.add(label);
		panel.setBackground(Color.BLACK);
		
		valikkoIkkuna.add(panel);
		valikkoIkkuna.setVisible(true);
		

	}
	
	public void piilotaValikko() {
		valikkoIkkuna.setVisible(false);
	}
	
	public void naytaValikko() {
		valikkoIkkuna.setVisible(true);
	}
	

	// M‰‰ritt‰‰ mit‰ napit tekee
	@Override
	public void actionPerformed(ActionEvent e) {

		
		if (e.getSource() == button1) {
			System.out.println("Nappi yksi");
			Main.luoPeliIkkuna(tietokanta);
			valikkoIkkuna.setVisible(false);
		}

		if (e.getSource() == button2) {
			System.out.println("Nappi kaksi");
			l.toplista();

		}
		if (e.getSource() == button3) {
			System.out.println("Tallenna peli");

		}
		if (e.getSource() == button4) {
			int[][] testiKentta = new int[2][2];
			int testiTulos = 3;
			
			
			Main.luoLadattuPeliIkkuna(tietokanta, testiKentta, testiTulos);
			try {
				
				String ladattuPeli = tietokanta.lataaPeli();
				System.out.println("*" + tietokanta.lataaPeli() );
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println("Peli ladattu");

		}
	}

}
