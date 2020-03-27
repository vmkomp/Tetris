import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Muoto {
	
	public int[][] koordinaatit;
	public int variArvo;
	
	// Palikan konstruktori. Alusta arrayt, variArvot ja palikan koordinaatit.
	public Muoto() {
		this.variArvo = alustaVariArvo();
	}
	
	public int alustaVariArvo() {
		Random r = new Random();
		return r.nextInt(4)+1;
	}
	
	public int annaVariArvo() {
		return variArvo;
	}
	
	public void sovitaPeliKenttaan(Pelilauta pelilauta) {
	// Palikan koordinaattien sovitus liikkuvaanKarttaan.
		int uusiX = 0;
		int uusiY = 0;
		
		for(int i=0; i< koordinaatit.length; i++) {
			for(int j=0; j<koordinaatit[i].length; j++) {
				if(koordinaatit[i][j] != 0) {
					uusiX = i;
					uusiY = j+3;
				}
				pelilauta.asetaLiikkuvaTaulukko(uusiX, uusiY, variArvo);
			}
		}
	}

	public int[][] annaKoordinaatit() {
		return koordinaatit;
	}
}

// S tarkoittaa suorakulmiota. Muut palikat nimetty ulkoasua vastaavaksi kirjaimeksi.

class S extends Muoto {
	
	int variArvo;
	
	public S() {
		
		this.variArvo = super.variArvo;
		int[][] suorakulmioKoordinaatit = {{variArvo, variArvo,variArvo}, {variArvo,variArvo,variArvo}};
		super.koordinaatit = suorakulmioKoordinaatit;
	}
}

class L extends Muoto {
	
	int variArvo;
	
	public L() {
		
		this.variArvo = super.variArvo;
		int[][] suorakulmioKoordinaatit = {{variArvo, 0,0}, {variArvo,variArvo,variArvo}};
		super.koordinaatit = suorakulmioKoordinaatit;
	}
}

class I extends Muoto {
	
	int variArvo;
	
	public I() {
		
		this.variArvo = super.variArvo;
		int[][] suorakulmioKoordinaatit = {{variArvo,variArvo,variArvo}};
		super.koordinaatit = suorakulmioKoordinaatit;
	}
}

class M extends Muoto {
	
	int variArvo;
	
	public M() {
		
		this.variArvo = super.variArvo;
		int[][] mKoordinaatit = {{variArvo, 0,variArvo}, {variArvo,variArvo,variArvo}};
		super.koordinaatit = mKoordinaatit;
	}
}