public class Main {

	// TODO: Test method isShip (Will need to rename method)

    public static void main(String[] args) {
	    Player test = new Player(9, 100);
	    for (int i = 0; i < test.getSize(); i++) {
	        for (int k = 0; k < test.getSize(); k++) {
	            System.out.print("" + test.ships[i][k] + " ");
            }
	        System.out.print("\n");
        }
    }
}
