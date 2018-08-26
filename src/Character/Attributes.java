package Character;

import java.util.Random;

public class Attributes {

	/* Attributes[0]:
	 * 0 - WW
	 * 1 - US
	 * 2 - K
	 * 3 - Zr
	 * 4 - Odp
	 * 5 - Int
	 * 6 - SW
	 * 7 - Ogl
	 */
	
	/* Attributes[1]: 
	 * 0 - Zyw
	 * 1 - A
	 * 2 - S
	 * 3 - Sz
	 * 4 - Wyt
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
	
	public boolean WW(int mod) { return r.nextInt(100) < current[0][0] + mod; }
	public boolean US(int mod) { return r.nextInt(100) < current[0][1] + mod; }
	public boolean K(int mod) { return r.nextInt(100) < current[0][2] + mod; }
	public boolean Zr(int mod) { return r.nextInt(100) < current[0][3] + mod; }
	public boolean Odp(int mod) { return r.nextInt(100) < current[0][4] + mod; }
	public boolean Int(int mod) { return r.nextInt(100) < current[0][5] + mod; }
	public boolean SW(int mod) { return r.nextInt(100) < current[0][6] + mod; }
	public boolean Ogl(int mod) { return r.nextInt(100) < current[0][7] + mod; }
	
	public int getSz() { return current[1][2]; }
}
