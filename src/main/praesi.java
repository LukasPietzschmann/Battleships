import java.io.BufferedReader;
import java.io.InputStreamReader;

public class praesi {
	public static void main(String[] args) throws Exception {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println("Modi:");
			System.out.println("1: AI vs. AI");
			System.out.println("2: Spieler vs. AI");
			System.out.println("3: AI vs NW (AI ist der Client)");
			System.out.println("4: AI vs NW (AI ist der Host)");
			System.out.println("5: Spieler vs NW (Spieler ist der Client)");
			System.out.println("6: Spieler vs NW (Spieler ist der Host)");
			
			String mod = bf.readLine();
			
			System.out.println("2er Schiffe - 3er Schiffe - 4er Schiffe - 5er Schiffe - Größe");
			String[] elems = bf.readLine().split(" ");
			/*switch(mod) {
				case "1":
					Launcher.getInstance().startGame(Launcher.AI_AI,
									Integer.parseInt(elems[0]),
									Integer.parseInt(elems[1]),
									Integer.parseInt(elems[2]),
									Integer.parseInt(elems[3]),
									Integer.parseInt(elems[4])).startGame();
					break;
				case "2":
					Launcher.getInstance().startGame(Launcher.PL_AI,
									Integer.parseInt(elems[0]),
									Integer.parseInt(elems[1]),
									Integer.parseInt(elems[2]),
									Integer.parseInt(elems[3]),
									Integer.parseInt(elems[4])).startGame();
					break;
				case "3":
					Launcher.getInstance().startGame(Launcher.NW_CL_AI,
									Integer.parseInt(elems[0]),
									Integer.parseInt(elems[1]),
									Integer.parseInt(elems[2]),
									Integer.parseInt(elems[3]),
									Integer.parseInt(elems[4])).startGame();
					break;
				case "4":
					Launcher.getInstance().startGame(Launcher.NW_SV_AI,
									Integer.parseInt(elems[0]),
									Integer.parseInt(elems[1]),
									Integer.parseInt(elems[2]),
									Integer.parseInt(elems[3]),
									Integer.parseInt(elems[4])).startGame();
					break;
				case "5":
					Launcher.getInstance().startGame(Launcher.PL_NW_CL,
									Integer.parseInt(elems[0]),
									Integer.parseInt(elems[1]),
									Integer.parseInt(elems[2]),
									Integer.parseInt(elems[3]),
									Integer.parseInt(elems[4])).startGame();
					break;
				case "6":
					Launcher.getInstance().startGame(Launcher.PL_NW_SV,
									Integer.parseInt(elems[0]),
									Integer.parseInt(elems[1]),
									Integer.parseInt(elems[2]),
									Integer.parseInt(elems[3]),
									Integer.parseInt(elems[4])).startGame();
					break;
			}*/
		}
	}
}
