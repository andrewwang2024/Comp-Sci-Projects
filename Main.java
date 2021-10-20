import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class Main extends JPanel {
	private static Player genericPlayer;
	private static Player computerPlayer;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		genericPlayer.draw(g);
		computerPlayer.draw(g);

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
		Random rand = new Random();
		Scanner input = new Scanner(System.in);
		System.out.print("Please input size: ");
		int size = input.nextInt();
		System.out.print("Please input number of ships: ");
		int shipNum = input.nextInt();
		Player player = new Player(size, shipNum);
		genericPlayer = player;
		Player computer = new Player(player.getSize(), player.getShipNum());
		int testNum;
		JFrame window = new JFrame("Battleship");
        window.setBounds(100, 100, genericPlayer.getSize() * 60 + 100, genericPlayer.getSize() * 60 + 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel canvas = new Main();
        canvas.setBackground(Color.WHITE);
        window.getContentPane().add(canvas);
		window.setVisible(true);
		for (int i = 0; i < player.getSize(); i++) {
			for (int k = 0; k < player.getSize(); k++) {
				if (player.ships[i][k]) {
					testNum = 1;
				}
				else {
					testNum = 0;
				}
				System.out.print("" + testNum + " ");

			}
			System.out.print("\n");
		}
		for ( ; ; ) {
			System.out.print("Please input x-coordinate of guess: ");
			int xGuess = input.nextInt() - 1;
			System.out.print("Please input y-coordinate of guess: ");
			int yGuess = input.nextInt() - 1;
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
			canvas.repaint();
			if (computer.hitsLeft() == 0) {
				System.out.println("You Win!");
				break;
			}
			int compXPos = rand.nextInt(player.getSize());
			int compYPos = rand.nextInt(player.getSize());
			if (player.beenGuessed(compXPos, compYPos)) {
				compXPos = rand.nextInt(player.getSize());
				compYPos = rand.nextInt(player.getSize());
			}
			boolean compGuessHit = player.fireShip(compXPos, compYPos);
			System.out.println("Computer guessed at: (" + (compXPos + 1) + "," + (compYPos + 1) + ")");
			if (compGuessHit) {
				System.out.println("Computer hit a ship!");
			}
			else {
				System.out.println("Computer Missed!");
			}
			canvas.repaint();
			if (player.hitsLeft() == 0) {
				System.out.println("Computer Won!");
			}
		}
	}
}
