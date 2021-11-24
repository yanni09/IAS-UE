import java.util.Scanner;


public class Aufg5a_SchiffeVersenken1D_Extended {

	
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
		Aufg5a_SchiffeVersenken1D_Extended spiel = new Aufg5a_SchiffeVersenken1D_Extended();
		spiel.run();

	}
	
	void run() {
		boolean[] feld = setup();
		
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
		
		// kurz: return feld[tippPos]
		if (feld[tippPos] == true) {
			return true;
		} else {
			return false;
		}
	}

	
	
	/**
	 * Erzeuge ein leeres Spielfeld OHNE Schiffe (false == Wasser)
	 * @param feldGroesse - Anzahl der Felder
	 * @return das leere Spielfeld
	 */
	boolean[] erzeugeSpielfeld(int feldGroesse) {
		boolean[] feld = new boolean[feldGroesse];
		for (int i = 0; i < feldGroesse; i++) {
			feld[i] = false;	// kein Schiff
		}
		return feld;
	}
	
	/**
	 * Positioniere ein Schiff in das Spielfeld, sofern moeglich
	 * 
	 * @param spielFeld - Das Spielfeld
	 * @param pos	- Die Startposition des Schiffes
	 * @param schiffsGroesse - Die Groesse des Schiffes (Anzahl belegter Felder)
	 * @return - true, falls das Schiff positioniert werden kann.
	 */
	boolean setSchiff(boolean spielFeld[], int pos, int schiffsGroesse) {
		// Teste, ob alles OK ist.
		if (spielFeld == null)
			return false;
		if ((pos < 0) || (schiffsGroesse <= 0) || (pos + schiffsGroesse > spielFeld.length))
			return false;	// Fehler
		
		
		// jetzt koennen wir das Schiff positionieren
		for (int i = pos; i < pos + schiffsGroesse; i++) {
			spielFeld[i] = true;	// Schiff
		}
		return true;
	}
	
	/**
	 * Erzeuge ein Spielfeld der Groesse 10 mit einem Schiff der Groesse 3
	 * return: Das Spielfeld
	 */
	final int FELD_GROESSE = 10;
	final int SCHIFFS_GROESSE = 3;
	boolean[] setup() {
		boolean[] feld = erzeugeSpielfeld(FELD_GROESSE);
		
		// erzeuge Schiff (Groesse 3)
		int startPos = (int) (Math.random() * (FELD_GROESSE - SCHIFFS_GROESSE + 1));
		setSchiff(feld, startPos, SCHIFFS_GROESSE);
		
		return feld;
	}

}
