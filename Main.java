public class Main {

	// TODO: Test method isShip (Will need to rename method)

	/*
	 *	Ideas for game board.
	 *		- Make two boards on each side.
	 *		- The first board (for the left side) is a never changing grid showing your ship
	 * 		- The second board (for the left side) actually renders over the first board
	 * 		- Rinse and repeat for the enemy board, where the static board is all white
	 * 		- Alternatively, make the white board rendered over and slowly make the squares transparent,
	 * 		  with the static board being the guess and ships.
	 * 		- Side note, maybe make the ends of the ship more distinct with different colors?
	 */

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
