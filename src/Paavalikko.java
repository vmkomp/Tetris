import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// M��ritt�� pelin p��valikon
public class Paavalikko implements ActionListener {

	int count = 0;
	JLabel label;
	JFrame frame;
	JPanel panel;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;

	Main a = new Main();
	App lista = new App();
	TopLista l = new TopLista();

	public Paavalikko() {

		frame = new JFrame();

		// Nappula1
		button1 = new JButton("Uusi peli");
		button1.addActionListener(this);

		// Nappula2
		button2 = new JButton("N�yt� top 10");
		button2.addActionListener(this);

		// Nappula3
		button3 = new JButton("Tallenna peli");
		button3.addActionListener(this);

		// Nappula4
		button4 = new JButton("Lataa peli");
		button4.addActionListener(this);

		label = new JLabel();

		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 100, 200)); // Ikkunan koko
		panel.setLayout(new GridLayout(0, 1));

		// Lis�� kuvan p��valikkoon
		JLabel background;
		ImageIcon img = new ImageIcon("TetrisLogo.png");
		background = new JLabel("", img, JLabel.CENTER);
		background.setBounds(0,0,10,10);
		panel.add(background);

		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(label);
		panel.setBackground(Color.BLACK);

		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("P��valikko");
		frame.pack();
		frame.setVisible(true);

	}

	// M��ritt�� mit� napit tekee
	@Override
	public void actionPerformed(ActionEvent e) {

		count++;

		if (e.getSource() == button1) {
			System.out.println("Nappi yksi");
			a.luoIkkuna();
		}

		if (e.getSource() == button2) {
			System.out.println("Nappi kaksi");
			l.toplista();

		}
		if (e.getSource() == button3) {
			System.out.println("Tallenna peli");

		}
		if (e.getSource() == button4) {
			System.out.println("Peli ladattu");

		}
	}

	public static void main(String[] args) {
		new Paavalikko();

	}

}
