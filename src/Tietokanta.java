
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Hoitaa yhteyden tietokantaan, alustuksen ja muokkauksen
 * 
 */

public class Tietokanta {

	private static Connection con;
	private static boolean hasData = false;

	public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}

		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT id, name, score FROM highScore order by score desc");
		return res;

	}

	// Luo yhteyden tietokantaan

	public void getConnection() throws ClassNotFoundException, SQLException {
		System.out.println("Yhdistetty tietokantaan");
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:TETRIS_tietokanta.db");
		initialise();

	}

	// Luo uuden tietokannan jos sitä ei ole olemassa
	// Syöttää hieman dataa tietokantaan testiksi
	private void initialise() throws SQLException {
		if (!hasData) {
			hasData = true;

			Statement state = con.createStatement();
			ResultSet res = state
					.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='highScore'");

			// ResultSet res2 = state
			// .executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND
			// name='tallenne'");

			if (!res.next()) {
				System.out.println("Luodaan uusi tietokanta default arvoilla");
				// need to build the table

				// Luo tilastotaulun
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE highScore (id integer," + "name varchar(60)," + "score integer(30),"
						+ "primary key(id));");
				// Luo tallennetaulun
				Statement state3 = con.createStatement();
				state3.execute("CREATE TABLE tallenne (id integer AUTO_INCREMENT," + "tallenne varchar(200),"
						+ "highscore integer(5));");

				// Syöttää 10 default-tulosta highscore tauluun
				for (int i = 1; i < 11; i++) {

					PreparedStatement prep = con.prepareStatement("INSERT INTO highScore values(?,?,?);");
					prep.setInt(1, i);
					prep.setString(2, "Default_player");
					prep.setInt(3, i);
					prep.execute();
				}

				// Syöttää dataa tallennetauluun testiksi
				PreparedStatement pre2 = con.prepareStatement("INSERT INTO tallenne values(?,?,?);");
				pre2.setInt(1, 1);
				pre2.setString(2, "default");
				pre2.setInt(3, 0);
				pre2.execute();

			}

		}

	}

	// Lisää tuloksen high score listalle
	// TODO: ehkä turha?
	public void addUser(Integer id, String name, String score) throws ClassNotFoundException, SQLException {

		if (con == null) {
			getConnection();
		}

		PreparedStatement prep = con.prepareStatement("INSERT INTO highScore values(?,?,?);");
		prep.setInt(1, id);
		prep.setString(2, name);
		prep.setString(3, score);
		prep.execute();

	}

	// Lisää tulos tietokantaan
	public void lisaaTulos(String nimi, Integer score) throws SQLException {

		PreparedStatement prep = con.prepareStatement("INSERT INTO highScore values(?,?,?);");
		prep.setInt(1, haeSuurinId() + 1);
		prep.setString(2, nimi);
		prep.setInt(3, score);
		prep.execute();

	}

	// Palauttaa suurimman id:n highScore taulusta
	// ts. kertoo montako tulosta tietokannassa on
	public Integer haeSuurinId() {

		ResultSet rs;
		ArrayList<Integer> idLista = new ArrayList<Integer>();
		idLista.add(0);

		try {
			rs = displayUsers();
			while (rs.next()) {

				idLista.add(Integer.parseInt(rs.getString("id")));

			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		int suurin = 0;
		for (int i = 0; i < idLista.size(); i++) {
			if (suurin < idLista.get(i)) {
				suurin = idLista.get(i);
			}
		}
		return suurin;
	}

	// Luo taulun pelitallenteelle
	// taulussa vain yksi paikka tallenteelle
	public void tallennaPeli(String tallenne, int peliPisteet) throws SQLException, ClassNotFoundException {

		ArrayList<Integer> idLista = new ArrayList<Integer>();
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery("SELECT id FROM tallenne");
		idLista.add(0);

		try {
			while (rs.next()) {

				idLista.add(Integer.parseInt(rs.getString("id")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int suurin = 0;
		for (int i = 0; i < idLista.size(); i++) {
			if (suurin < idLista.get(i)) {
				suurin = idLista.get(i);
			}
		}

		PreparedStatement prep = con.prepareStatement("INSERT INTO tallenne values(?,?,?);");
		prep.setInt(1, suurin + 1);
		prep.setString(2, tallenne);
		prep.setInt(3, peliPisteet);
		prep.execute();
	}

	// Muuttaa tallenteen matriisin merkkijonoksi tietokantaan tallentamista varten
	public String muutaMerkkijonoksi(int[][] staattinenTaulukko) {
		StringBuilder sb = new StringBuilder();
		for (int[] s1 : staattinenTaulukko) {
			for (int s2 : s1) {
				sb.append(s2);
			}
		}
		String table = sb.toString();
		return table;
	}

	// Lataa tallenteen tietokannasta matriisiksi muutettuna ja pisteet
	public ArrayList<Object> lataaPeli() throws ClassNotFoundException, SQLException, StringIndexOutOfBoundsException {
		
		ArrayList<Object> matriisiJaTulos = new ArrayList<Object>();
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT tallenne, highscore, id FROM tallenne order by id desc");
		
		System.out.println("Pelin lataaminen aloitettu");
		System.out.println(res.getString("id") + "," + res.getString("tallenne") + ", " + res.getString("highscore"));

		String tuodutPisteet = res.getString("highscore");
		StringBuilder teksti = new StringBuilder();

		try {
			
			teksti.append(res.getString("tallenne"));
		
		} catch (SQLException|StringIndexOutOfBoundsException e ) {
			e.printStackTrace();
		}
			
		int[][] tallenneMatriisi = new int[20][10];
		int laskuri = 0;
		
		// Jos tallennetta ei löydy, annetaan tyhjä matriisi ja tulos
		try {
			for (int rivi=0;rivi<20; rivi++) {
				for (int sarake=0; sarake<10; sarake++) {
					tallenneMatriisi[rivi][sarake] = Character.getNumericValue(teksti.charAt(laskuri));
					laskuri++;
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("Virhe pelin lataamisessa. Tallennetta ei ole olemassa!");
			tallenneMatriisi = new int[20][10];
			tuodutPisteet = "0";
			//e.printStackTrace();
		}
		
		matriisiJaTulos.add(tallenneMatriisi);
		matriisiJaTulos.add(Integer.parseInt(tuodutPisteet));
			
		return matriisiJaTulos;
	}
}
