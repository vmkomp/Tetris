import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		luoIkkuna();
	}
	
	
	public static void luoIkkuna() {
		Pelilogiikka peliLogiikka = new Pelilogiikka();
		JFrame gameWindow = new JFrame();
		gameWindow.setBounds(10,10,700,600);
		gameWindow.setTitle("Kikkelitetris");
		gameWindow.setResizable(false);
		gameWindow.setVisible(true);
		gameWindow.add(peliLogiikka);
		System.out.println("Ikkuna luotu");
	}

	
}
