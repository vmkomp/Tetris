import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Luo ikkunan, joka tallentaa pelituloksen tietokantaan

public class TulosIkkuna extends JFrame {

	JPanel jp = new JPanel();
	JLabel jl = new JLabel();
	JTextField tekstikentta = new JTextField(30);
	JButton nappula1 = new JButton("Valmis");
	Tietokanta tietokanta = new Tietokanta();

	public void avaaTulosIkkuna() {
		tulosIkkuna();

	}

	public void tulosIkkuna() {

		setTitle("Tallenna tulos");
		setVisible(true);
		setSize(400, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		jp.add(tekstikentta);

		tekstikentta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String input = tekstikentta.getText();
				jl.setText(input);

			}
		});

		jp.add(jl);
		nappula1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String input = tekstikentta.getText();
				jl.setText(input);

				if (e.getSource() == nappula1) {
					System.out.println("Annoit tekstin " + input);

					try {
						tietokanta.lisaaTulos(input, Main.score);
					} catch (SQLException e1) {
						System.out.println("Virhe tallennettaessa tulosta tietokantaan");
						e1.printStackTrace();
					}

					setVisible(false);

				}

			}
		});

		jp.add(nappula1);
		add(jp);

	}

}
