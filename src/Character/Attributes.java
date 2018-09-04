package Character;

import java.util.Random;

public class Attributes {

	/* Attributes[0]:
	 * 0 - WW
	 * 1 - US
	 * 2 - K
	 * 3 - Odp
	 * 4 - Zr
	 * 5 - Int
	 * 6 - SW
	 * 7 - Ogl
	 */
	
	/* Attributes[1]: 
	 * 0 - A
	 * 1 - Zyw
	 * 2 - S
	 * 3 - Wyt
	 * 4 - Sz
	 * 5 - Mag
	 * 6 - PO 
	 * 7 - PP
	 */
	
	public int[][] begin = new int[2][8];
	public int[][] progress = new int[2][8];
	public int[][] current = new int[2][8];
	
	private Random r;
	
	public Attributes() {
		r = new Random();
		
		for(int a=0; a<2; a++) {
			for(int at=0; at<8; at++) {
				progress[a][at] = 0;
			}				
		}
	}
	
	public boolean WW(int mod) { return r.nextInt(100) <= current[0][0] + getMOD(mod); }
	public boolean US(int mod) { return r.nextInt(100) <= current[0][1] + getMOD(mod); }
	public boolean K(int mod) { return r.nextInt(100) <= current[0][2] + getMOD(mod); }
	public boolean Odp(int mod) { return r.nextInt(100) <= current[0][3] + getMOD(mod); }
	public boolean Zr(int mod) { return r.nextInt(100) <= current[0][4] + getMOD(mod); }
	public boolean Int(int mod) { return r.nextInt(100) <= current[0][5] + getMOD(mod); }
	public boolean SW(int mod) { return r.nextInt(100) <= current[0][6] + getMOD(mod); }
	public boolean Ogl(int mod) { return r.nextInt(100) <= current[0][7] + getMOD(mod); }
	
	public int getA() { return current[1][0]; }
	public int getZyw() { return current[1][1]; }
	public int getS() { return current[1][2]; }
	public int getWyt() { return current[1][3]; }
	public int getSz() { return current[1][4]; }
	public int getMag() { return current[1][5]; }
	public int getPO() { return current[1][6]; }
	public int getPP() { return current[1][7]; }

	public static String[] getShortcutOrder(int nr) {
		return nr == 0 ? new String[] { "WW", "US", "K", "Odp", "Zr", "Int", "SW", "Og³" }
		               : new String[] { "A", "¯yw", "S", "Wyt", "Sz", "Mag", "PO", "PP" };
	}

	public static String[] getNamesOrder(int nr) {
		return nr == 0 ? new String[] { "Walka Wrêcz", "Umiejêtnoœci Strzeleckie",
				"Krzepa", "Odpornoœæ",
				"Zrêcznoœæ", "Inteligencja",
				"Si³a Woli", "Og³ada" }

				: new String[] { "Ataki", "¯ywotnoœæ",
				"Si³a", "Wytrzyma³oœæ",
				"Szybkoœæ", "Magia",
				"Punkty Ob³êdu", "Punkty Przeznaczenia" };
	}

	private int getMOD(int mod) {
		return mod > 30 ? 30 : mod < -30 ? -30 : mod;
	}
}
