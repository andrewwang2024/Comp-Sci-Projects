import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.util.Scanner;

public class Main extends JPanel {
	private static Player genericPlayer;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		genericPlayer.draw(g);

	}


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
		Scanner input = new Scanner(System.in);
		System.out.print("Please input size: ");
		int size = input.nextInt();
		System.out.print("Please input number of ships: ");
		int shipNum = input.nextInt();
		Player player = new Player(size, shipNum);
		genericPlayer = player;
		Player computer = new Player(player.getSize(), player.getShipNum());
		// 3JFrame window = new JFrame("Battleship");
        /* window.setBounds(100, 100, 300, 300);
			- Need the draw method square size to determine bounds
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel canvas = new Main();
        canvas.setBackground(Color.WHITE);
        window.getContentPane().add(canvas);
		*/
		
	    for ( ; ; ) {
			System.out.print("Please input x-coordinate of guess: ");
			int xGuess = input.nextInt();
			System.out.print("Please input y-coordinate of guess: ");
			int yGuess = input.nextInt();
			if (computer.beenGuessed(xGuess, yGuess)) {
				System.out.println("This space has already been guessed!");
				continue;
			}
			boolean isHit = computer.fireShip(xGuess, yGuess);
			if (isHit) {
				System.out.println("You hit a ship!");
			}
			else {
				System.out.println("You missed!");
			}
			if (computer.getShipNum() == 0) {
				System.out.println("You Win!");
				break;
			}
		}
    }
}
