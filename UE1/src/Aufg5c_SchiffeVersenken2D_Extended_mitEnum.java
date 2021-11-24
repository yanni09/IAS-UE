import java.util.Scanner;


public class Aufg5c_SchiffeVersenken2D_Extended_mitEnum {

	
	/**
	 * Erzeugt eine textuelle Darstellung des Spielfeldes, 
	 * wobei Schiffsteile mit o bzw. X (getroffen) und Wasserfelder mit _ dargestellt werden.
	 * 
	 * Beispiel-Ausgabe:
	 *     +---------------------+
     *     | _ _ o o X X _ _ _ _ |0
     *     | _ _ _ _ _ _ _ _ o _ |1
     *     | _ _ _ _ o _ _ _ o _ |2
     *     | X _ _ _ _ _ _ _ o _ |3
     *     | X _ _ _ _ _ _ _ _ _ |4
     *     | _ _ _ _ _ o o o _ _ |5
     *     +---------------------+
     *       0 1 2 3 4 5 6 7 8 9 
	 * 
	 * @param feld - das Spielfeld
	 * @return textuelle Darstellung.
	 */
	String toString(Feld[][] feld) {
		String spaces = "    ";		// Abstand links vom Rand
		
		String linie_vertikal = spaces + "+-";	// Begrenzungslinie
		for (int spalte = 0; spalte < feld[0].length; spalte++) {
			linie_vertikal += "--";
		}
		linie_vertikal += "+\n";
		
		String ausgabe = "\n" + linie_vertikal;
		
		for (int zeile = 0; zeile < feld.length; zeile++) {
			ausgabe += spaces + "| ";
			for (int spalte = 0; spalte < feld[0].length; spalte++) {
				String c;
				if (feld[zeile][spalte] == Feld.SCHIFF)
					c = "o";	// Schiff
				else if (feld[zeile][spalte] == Feld.TREFFER)
					c = "X";	// Treffer
				else //if (feld[zeile][spalte] == Feld.WASSER)
					c = "_";	// Wasser
				ausgabe += c + ' ';
			}
			// Zeilenumbruch
			ausgabe += "|" + zeile + "\n";
		}
		
		ausgabe += linie_vertikal;
		
		ausgabe += spaces + "  ";
		for (int spalte = 0; spalte < feld[0].length; spalte++) {
			ausgabe += spalte + " ";
		}
		ausgabe += "\n";
		
		return ausgabe;
	}
	
	/**
	 * Liest einen Tipp (geratene Zeile & Spalte) vom Terminal ein.
	 * @return - der Tipp als Feld von 2 Zahlen [zeile, spalte]
	 * 			 (das ist sehr unsauber - besser: Rückgabe eines Paar-Objektes statt Array!)
	 */
	int[] eingabeTipp() {
		Scanner scanner = new Scanner( System.in );
		
		System.out.print("Geben Sie die Zeile ein: ");
		int zeile = scanner.nextInt();
		System.out.print("Geben Sie die Spalte ein: ");
		int spalte = scanner.nextInt();
		
		int[] ergebnis = new int[2];	// Array von 2 ganzen Zahlen
		ergebnis[0] = zeile;		// das ist sehr unsauber !
		ergebnis[1] = spalte;	
		return ergebnis;
	}
	

	
	/**
	 * Startet das Schiffeversenken-Programm
	 * @param args - übergebene Aufrufargumente an das Programm
	 */
	public static void main(String[] args) {
		Aufg5c_SchiffeVersenken2D_Extended_mitEnum spiel = new Aufg5c_SchiffeVersenken2D_Extended_mitEnum();
		spiel.run();

	}
	
	void run() {
		Feld[][] feld = setup();
		
		int anzahlVersuche = 0;
		int anzahlTreffer = 0;
		
		boolean lebt = true;	// Spiel noch "am Leben"?
		while (lebt == true) {
			System.out.println("Spielfeld: " + toString(feld));	// Schummeln :-)

			int[] tipp = eingabeTipp();
			anzahlVersuche++;
			
			Feld ergebnis = pruefeTipp(feld, tipp);
			String ausgabe = "Vorbei";
			if (ergebnis == Feld.WASSER) {
				ausgabe = "Vorbei";
			} 
			else if (ergebnis == Feld.SCHIFF) {
				ausgabe = "Treffer";
				anzahlTreffer++;
				
				int tippZeile = tipp[0];
				int tippSpalte = tipp[1];
				feld[tippZeile][tippSpalte] = Feld.TREFFER;
				
				if (pruefeVersenkt(feld)) {
					ausgabe = "Versenkt";
					lebt = false;	// Ende
				}
			} else /*if (ergebnis == Feld.TREFFER)*/ {
				ausgabe = "Was soll das? Das Feld haben Sie doch schon getroffen.";
			}
			
			System.out.println(ausgabe);
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
	Feld pruefeTipp(Feld[][] feld, int[] tippPos) {
		// Test auf falsche Eingabe
		if (feld == null)
			return Feld.WASSER;
		if (tippPos == null || tippPos.length != 2)
			return Feld.WASSER;
		int zeile = tippPos[0];		// unsauber !
		int spalte = tippPos[1];
		if (zeile < 0 || zeile >= feld.length)	// Zeilen-Index korrekt
			return Feld.WASSER;
		if (spalte < 0 || spalte >= feld[0].length)	// Spalten-Index korrekt
			return Feld.WASSER;
		
		return feld[zeile][spalte];
	}

	/**
	 * Prueft, ob der Spieler alle Schiffe versenkt hat.
	 * 
	 * @param feld
	 * @return
	 */
	boolean pruefeVersenkt(Feld[][] feld) {
		// Test auf falsche Eingabe
		if (feld == null)
			return false;
		
		for (int zeile = 0; zeile < feld.length; zeile++) {
			for (int spalte = 0; spalte < feld[0].length; spalte++) {
			if (feld[zeile][spalte] == Feld.SCHIFF)
				return false;	// es gibt noch nicht getroffene (Teile von) Schiffen
			}
		}
		return true;	// alles WASSER oder VERSENKT
	}
	
	
	/*
	 * Erzeuge ein leeres Spielfeld OHNE Schiffe (false == Wasser)
	 * @param anzahlZeilen - Anzahl der Zeilen (Größe des Spielfeldes)
	 * @param anzahlSpalten - Anzahl der Spalten
	 * @return das leere Spielfeld (überall ist Wasser)
	 */
	Feld[][] erzeugeSpielfeld(int anzahlZeilen, int anzahlSpalten) {
		Feld[][] feld = new Feld[anzahlZeilen][anzahlSpalten];
		for (int zeile = 0; zeile < anzahlZeilen; zeile++) {
			for (int spalte = 0; spalte < anzahlSpalten; spalte++) {
				// Wichtig: alle Spielfelder explizit initialisieren
				// (anderenfalls haben sie den Default-Wert null!)
				feld[zeile][spalte] = Feld.WASSER;	// kein Schiff
			}
		}
		return feld;
	}
	
	/**
	 * Positioniere ein Schiff in das Spielfeld, sofern moeglich
	 * 
	 * @param spielFeld - Das Spielfeld
	 * @param posX	- Die Startposition des Schiffes (Spalte)
	 * @param posY	- Die Startposition des Schiffes (Zeile)
	 * @param schiffsBreite - Die Breite des Schiffes (Anzahl belegter Felder in y-Richtung)
	 * @param schiffsHoehe - Die Hoehe des Schiffes (Anzahl belegter Felder in x-Richtung)
	 * @return - true, falls das Schiff positioniert werden kann.
	 */
	boolean setSchiff(Feld spielFeld[][], int posX, int posY, int schiffsBreite, int schiffsHoehe) {
		// Teste, ob alles OK ist.
		if (spielFeld == null)
			return false;
		if ((posY < 0) || (schiffsHoehe <= 0) || (posY + schiffsHoehe > spielFeld.length))
			return false;	// Fehler
		if ((posX < 0) || (schiffsBreite <= 0) || (posX + schiffsBreite > spielFeld[0].length))
			return false;	// Fehler
		
		// Prüfe, ob Felder oder Nachbarn bereits belegt sind
		for (int zeile = Math.max(posY - 1, 0); 
			 zeile < Math.min(posY + schiffsHoehe + 1, spielFeld.length-1); 
			 zeile++) {
			for (int spalte = Math.max(posX - 1, 0); 
				 spalte < Math.min(posX + schiffsBreite + 1, spielFeld[0].length); 
				 spalte++) {
				if (spielFeld[zeile][spalte] == Feld.SCHIFF)
					return false;	// bereits belegt
			}
		}
		
		// jetzt koennen wir das Schiff positionieren
		for (int zeile = posY; zeile < posY + schiffsHoehe; zeile++) {
			for (int spalte = posX; spalte < posX + schiffsBreite; spalte++) {
				spielFeld[zeile][spalte] = Feld.SCHIFF;	// Schiff
			}
		}
		return true;
	}
	
	
	enum Feld {WASSER, SCHIFF, TREFFER}; 
	final int FELD_HOEHE = 6;
	final int FELD_BREITE = 10;
	final int[] SCHIFFS_GROESSEN = {4, 3, 3, 2, 1};
	/**
	 * Erzeuge ein Spielfeld der Groesse 10x6 mit mehreren Schiffen unterschiedlicher Groesse
	 * return: Das Spielfeld
	 */
	Feld[][] setup() {
		Feld[][] feld = erzeugeSpielfeld(FELD_HOEHE, FELD_BREITE);
		
		// erzeuge Schiffe (der jeweiligen Groesse)
		for (int i = 0; i < SCHIFFS_GROESSEN.length; i++) {
			int schiffsBreite, schiffsHoehe;
			if (Math.random() < 0.7) {
				// 70 % Schiffs-Ausrichtung in x-Richtung
				schiffsBreite = SCHIFFS_GROESSEN[i];
				schiffsHoehe = 1;
			} else {
				// Schiffs-Ausrichtung in y-Richtung
				schiffsBreite = 1;
				schiffsHoehe = SCHIFFS_GROESSEN[i];				
			}
			
			int anzahlVersuche = 0;
			boolean erfolgreich;
			do {
				int startPosX = (int) (Math.random() * (FELD_BREITE - schiffsBreite + 1));
				int startPosY = (int) (Math.random() * (FELD_HOEHE - schiffsHoehe + 1));
				anzahlVersuche++;
				erfolgreich = setSchiff(feld, startPosX, startPosY, schiffsBreite, schiffsHoehe);
				
//				System.out.println("Erzeuge Schiff Nr. " + i + ": schiffsBreite = " + schiffsBreite + ", schiffsHoehe = " + schiffsHoehe
//						+ ", startPosX = " + startPosX + ", startPosY = " + startPosY
//						+ ", erfolgreich = " + erfolgreich + ", anzahlVersuche = " + anzahlVersuche);
			} while ((! erfolgreich) && (anzahlVersuche < 100));			
		}
		
		return feld;
	}

}
