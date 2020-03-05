
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

	public void main(String[] args) {
		try {
			annaTopLista();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void annaTopLista() throws ClassNotFoundException, SQLException {

		Tietokanta test = new Tietokanta();
		ResultSet rs;

		int laskuri = 1;
		try {
			rs = test.displayUsers();
			while (rs.next() && laskuri < 11) {
				System.out.println(laskuri + " " + rs.getString("name") + " " + rs.getString("score"));
				laskuri++;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Suurin id: " + test.haeSuurinId());

		// test.tallennaPeli("101010101010101", "456");

	}

	public String annaTopListaTekstina() throws ClassNotFoundException, SQLException {

		Tietokanta test = new Tietokanta();
		ResultSet rs;

		StringBuilder teksti = new StringBuilder();

		int laskuri = 1;
		try {
			rs = test.displayUsers();
			while (rs.next() && laskuri < 11) {
				teksti.append(laskuri + "." + " " + rs.getString("name") + " " + rs.getString("score"));
				teksti.append(System.getProperty("line.separator"));
				laskuri++;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return teksti.toString();

	}

}
