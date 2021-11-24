import java.util.Scanner;

public class Aufg4_SchiffeVersenken1D {

	
	/**
	 * Erzeugt eine textuelle Darstellung des Spielfeldes, 
	 * wobei Schiffsteile mit o und Wasserfelder mit _ dargestellt werden.
	 * 
	 * @param feld - das Spielfeld
	 * @return textuelle Darstellung.
	 */
	String toString(boolean[] feld) {
		String ausgabe = "";
		for (int i = 0; i < feld.length; i++) {
			String c;
			if (feld[i])
				c = "o";	// Schiff
			else
				c = "_";	// Wasser
			ausgabe += c + ' ';
		}
		return ausgabe;
	}
	
	/**
	 * Liest eine Zahl (geratener Tipp) vom Terminal ein.
	 * @return - der Tipp
	 */
	int eingabeTipp() {
		System.out.print("Geben Sie eine Zahl ein: ");
		Scanner scanner = new Scanner( System.in );
		int eingabe = scanner.nextInt();
		
		return eingabe;
	}
	

	/**
	 * Startet das Schiffeversenken-Programm
	 * @param args - übergebene Aufrufargumente an das Programm
	 */
	public static void main(String[] args) {
		Aufg4_SchiffeVersenken1D spiel = new Aufg4_SchiffeVersenken1D();
		spiel.run();

	}
	
	void run() {
		// Erzeuge ein Spielfeld der Groesse 10 mit einem Schiff der Groesse 3
		//		- false = Wasser
		//		- true	= SCHIFF
		// (Beachte: Hier ist das Schiff fest positioniert, eine Zufallswahl wird in
		//		     Methode setup() - siehe SchiffeVersenken1DExtendend - realisert.)
		final int SCHIFFS_GROESSE = 3;
		boolean[] feld = { false, false, true, true, true, false, false, false, false, false };
		
		int anzahlVersuche = 0;
		int anzahlTreffer = 0;
		
		boolean lebt = true;	// Spiel noch "am Leben"?
		while (lebt == true) {
			int tipp = eingabeTipp();
			anzahlVersuche++;
			
			boolean ergebnis = pruefeTipp(feld, tipp);
			String ausgabe = "Vorbei";
			if (ergebnis == true) {
				ausgabe = "Treffer";
				anzahlTreffer++;
				feld[tipp] = false;
				
				if (anzahlTreffer == SCHIFFS_GROESSE) {
					ausgabe = "Versenkt";
					lebt = false;	// Ende
				}
			}
			
			System.out.println(ausgabe);
			// System.out.println("Spielfeld: " + toString(feld));	// Schummeln :-)
		}
		
		System.out.println("Sie haben " + anzahlVersuche + " Versuche benötigt.");
		System.out.println("Spielfeld: " + toString(feld));
	}

	
	/**
	 * Prueft, ob der Spieler ein Schiff getroffen hat.
	 * 
	 * @param feld
	 * @param tippPos
	 * @return
	 */
	boolean pruefeTipp(boolean[] feld, int tippPos) {
		// Test auf falsche Eingabe
		if (feld == null)
			return false;
		if (tippPos < 0 || tippPos > feld.length)
			return false;
		
		// kürzer: return feld[tippPos]
		if (feld[tippPos] == true) {
			return true;
		} else {
			return false;
		}
	}

}
