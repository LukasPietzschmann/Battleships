import logic.Launcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class praesi {
	public static void main(String[] args) throws Exception{
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			System.out.println("Modi:");
			System.out.println("1: AI vs. AI");
			System.out.println("2: Spieler vs. AI");
			System.out.println("3: AI vs NW (AI ist der Client)");
			System.out.println("4: AI vs NW (AI ist der Host)");
			System.out.println("5: Spieler vs NW (Spieler ist der Client)");
			System.out.println("6: Spieler vs NW (Spieler ist der Host)");
			
			String[] elems = bf.readLine().split(" ");
			switch(elems[0]){
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				case "5":
					break;
				case "6":
					break;
				
			}
		}
	}
}
