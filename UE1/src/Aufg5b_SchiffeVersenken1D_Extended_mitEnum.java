import java.util.Scanner;


public class Aufg5b_SchiffeVersenken1D_Extended_mitEnum {

	
	/**
	 * Erzeugt eine textuelle Darstellung des Spielfeldes, 
	 * wobei Schiffsteile mit o bzw. X (getroffen) und Wasserfelder mit _ dargestellt werden.
	 * 
	 * @param feld - das Spielfeld
	 * @return textuelle Darstellung.
	 */
	String toString(Feld[] feld) {
		String ausgabe = "";
		for (int i = 0; i < feld.length; i++) {
			String c;
			if (feld[i] == Feld.SCHIFF)
				c = "o";	// Schiff
			else if (feld[i] == Feld.TREFFER)
				c = "X";	// Treffer
			else //if (feld[i] == Feld.WASSER)
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
		Aufg5b_SchiffeVersenken1D_Extended_mitEnum spiel = new Aufg5b_SchiffeVersenken1D_Extended_mitEnum();
		spiel.run();

	}
	
	void run() {
		Feld[] feld = setup();
		
		int anzahlVersuche = 0;
		int anzahlTreffer = 0;
		
		boolean lebt = true;	// Spiel noch "am Leben"?
		while (lebt == true) {
			int tipp = eingabeTipp();
			anzahlVersuche++;
			
			Feld ergebnis = pruefeTipp(feld, tipp);
			String ausgabe = "Vorbei";
			if (ergebnis == Feld.WASSER) {
				ausgabe = "Vorbei";
			} 
			else if (ergebnis == Feld.SCHIFF) {
				ausgabe = "Treffer";
				anzahlTreffer++;
				feld[tipp] = Feld.TREFFER;
				
				if (pruefeVersenkt(feld)) {
					ausgabe = "Versenkt";
					lebt = false;	// Ende
				}
			} else /*if (ergebnis == Feld.TREFFER)*/ {
				ausgabe = "Was soll das? Das Feld haben Sie doch schon getroffen.";
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
	Feld pruefeTipp(Feld[] feld, int tippPos) {
		// Test auf falsche Eingabe
		if (feld == null)
			return Feld.WASSER;
		if (tippPos < 0 || tippPos > feld.length)
			return Feld.WASSER;
		
		return feld[tippPos];
	}

	/**
	 * Prueft, ob der Spieler alle Schiffe versenkt hat.
	 * 
	 * @param feld
	 * @return
	 */
	boolean pruefeVersenkt(Feld[] feld) {
		// Test auf falsche Eingabe
		if (feld == null)
			return false;
		
		for (int i = 0; i < feld.length; i++) {
			if (feld[i] == Feld.SCHIFF)
				return false;	// es gibt noch nicht getroffene (Teile von) Schiffen
		}
		return true;	// alles WASSER oder VERSENKT
	}
	
	
	/*
	 * Erzeuge ein leeres Spielfeld OHNE Schiffe (false == Wasser)
	 * @param feldGroesse - Anzahl der Felder
	 * @return das leere Spielfeld
	 */
	Feld[] erzeugeSpielfeld(int feldGroesse) {
		Feld[] feld = new Feld[feldGroesse];
		for (int i = 0; i < feldGroesse; i++) {
			feld[i] = Feld.WASSER;	// kein Schiff
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
	boolean setSchiff(Feld spielFeld[], int pos, int schiffsGroesse) {
		// Teste, ob alles OK ist.
		if (spielFeld == null)
			return false;
		if ((pos < 0) || (schiffsGroesse <= 0) || (pos + schiffsGroesse > spielFeld.length))
			return false;	// Fehler
		
		
		// jetzt koennen wir das Schiff positionieren
		for (int i = pos; i < pos + schiffsGroesse; i++) {
			spielFeld[i] = Feld.SCHIFF;	// Schiff
		}
		return true;
	}
	
	/**
	 * Erzeuge ein Spielfeld der Groesse 10 mit einem Schiff der Groesse 3
	 * return: Das Spielfeld
	 */
	enum Feld {WASSER, SCHIFF, TREFFER}; 
	final int FELD_GROESSE = 10;
	final int SCHIFFS_GROESSE = 3;
	Feld[] setup() {
		Feld[] feld = erzeugeSpielfeld(FELD_GROESSE);
		
		// erzeuge Schiff (Groesse 3)
		int startPos = (int) (Math.random() * (FELD_GROESSE - SCHIFFS_GROESSE + 1));
		setSchiff(feld, startPos, SCHIFFS_GROESSE);
		
		//System.out.println("setup: startpos = " + startPos);		// Schummeln :-)
		
		return feld;
	}

}
