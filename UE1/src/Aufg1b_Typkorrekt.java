public class Aufg1b_Typkorrekt {
	public static void main(String[] args) {
		short s = 7;
		int i = -12;
//		unsigned int u = +12; 	// unsigned unbekannt
		long l = 123456789;
		float f = 3.1415F;	//3.1415;	// Typfehler: double statt float!
		double d = 3.1415;
		
		s = (short) i;		//i;		// Typfehler: int -> short
		System.out.println("s = " + s);		// --> -12
		i = s;
		s = (short) 123456789; //123456789; // Typfehler: int statt short
		System.out.println("s = " + s);		// --> -13035
		int i2 = (int) l;
		System.out.println("i2 = " + i2);	// --> 123456789
		i = (int) 12.4;		//12.4;		// Typfehler: double statt int
		System.out.println("i = " + i);		// --> 12
		i = (int) 12L;		//12L;		// Typfehler: long statt int
		System.out.println("i = " + i);		// --> 12
		i = (int) 12L;

		String str = "Hallo" + "Welt";
	//	str = args[-3];		//args[-3];	// Laufzeitfehler: ArrayIndexOutOfBoundsException
		str = "i = " + i;
		System.out.println("str = " + str);	// --> i = 12
		str = str + i;		//str - i;	// Operator - undefiniert für String x int
		System.out.println("str = " + str);	// --> i = 1212
		
		boolean b = false;
		b = 12L == 12;
		System.out.println("b = " + b);		// --> true
	}
}
