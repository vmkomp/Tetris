import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// N‰ytt‰‰ top 10 parasta tulosta

public class TopLista extends JFrame {

	App lista = new App();
	App top = new App();
	JPanel paneeli = new JPanel();
	JTextArea tekstialue = new JTextArea(10, 20);

	public void topLista() {
		setTitle("Tutorial");
		setVisible(true);
		setSize(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		paneeli.add(tekstialue);
		add(paneeli);
		try {
			tekstialue.setText(top.annaTopListaTekstina());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void toplista() {
		topLista();
	}

}