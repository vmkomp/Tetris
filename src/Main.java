import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Main {
	
	static JFrame gameWindow;
	static JFrame valikkoIkkuna;
	static JFrame valikkoIkkuna2;
	static JPanel panel;
	static JPanel panel2;
	
	public static void main(String[] args) {
		luoIkkuna();
	}
	
	
	public static void luoIkkuna() {
		
		gameWindow = new JFrame();
		gameWindow.setBackground(Color.BLACK);
		gameWindow.setBounds(10,10,1000,500);
		// Java swing saa haistaa pitk‰n paskan en en‰‰ koskaan r‰pikˆi t‰n kaa
		//gameWindow.setTitle("Kikkelitetris");
		//gameWindow.setResizable(false);

		Paavalikko p = new Paavalikko();
		
		gameWindow.add(p.panel);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setTitle("P‰‰valikko");
		//gameWindow.pack();
		gameWindow.setVisible(true);
	}
	
	public static void luoIkkuna2() {
		
		 
		valikkoIkkuna = new JFrame();
		valikkoIkkuna.getContentPane().setBackground( Color.PINK );
		valikkoIkkuna.setSize(600,450);
		//gameWindow.setTitle("Kikkelitetris");

		 JPanel mainPanel = new JPanel();
		 mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		 mainPanel.setBackground(Color.BLACK);
		 
		 
		//valikkoIkkuna.setResizable(false);
		Pelilogiikka peliLogiikka = new Pelilogiikka();
	
		
		
		panel = new JPanel();
		

		
		panel.setBackground(Color.BLACK);
		
		panel2 = new JPanel();
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Ikkunan koko
		panel2.setBackground(Color.BLACK);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		ImageIcon originalImage = new ImageIcon("src/TetrisVelho.png");
		Image image = originalImage.getImage();
		Image newimg = image.getScaledInstance(originalImage.getIconWidth()/3, originalImage.getIconHeight()/3,  java.awt.Image.SCALE_SMOOTH);
		originalImage = new ImageIcon(newimg);
		JLabel imgLabel = new JLabel(originalImage, JLabel.CENTER);
		
		valikkoIkkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		valikkoIkkuna.setTitle("P‰‰valikko");

		valikkoIkkuna.setVisible(true);
		gameWindow.setVisible(false);
		
	
		imgLabel.setBounds(0,0,0,0);
		imgLabel.setSize(150,350);
		panel.add(imgLabel);
		
		
		JButton button1 = new JButton("Tallenna peli");
		JButton button2 = new JButton("Valikkoon");
		JLabel jlabel = new JLabel("0", JLabel.CENTER);
		jlabel.setForeground(Color.WHITE);
		jlabel.setFont(new Font("Serif", Font.PLAIN, 24));
		jlabel.setBounds(100,100,0,0);
		
		panel2.add(button1);
		panel2.add(button2);
		
		panel2.add(button1);
		panel2.add(jlabel);
		
		

		valikkoIkkuna.setContentPane(mainPanel);
		peliLogiikka.setLayout(null);
		panel.setLayout(null);
		mainPanel.add(peliLogiikka);

		mainPanel.add(panel);
		mainPanel.add(panel2);
		
		
	}

}
