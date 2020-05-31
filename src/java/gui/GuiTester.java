package gui;

public class GuiTester {
	
	public static int gridSize = 10;
	public static String theme = "Battleships";
	public static String themeIdentifierPlural = "Schiffe";
	public static String themeIdentifierSingular = "Schiff";
	public static boolean soundPlaying = false;
	
	public static int fiveFieldElementCount = 1;
	public static int fourFieldElementCount = 2;
	public static int threeFieldElementCount = 3;
	public static int twoFieldElementCount = 4;
	
	public static int fiveFieldElementMaxCount = 10;
	public static int fourFieldElementMaxCount = 10;
	public static int threeFieldElementMaxCount = 10;
	public static int twoFieldElementMaxCount = 10;
	
	public static String fiveFieldElementName;
	public static String fourFieldElementName;
	public static String threeFieldElementName;
	public static String twoFieldElementName;
	
	public static void main(String[] args) {
		MainWindow main = new MainWindow();
		main.setUpMainWindow();
	}
}	
