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
		genericPlayer.draw(g, false);
		computerPlayer.draw(g, true);

	}

	public static void main(String[] args) {
		// get inputs
		Random rand = new Random();
		Scanner input = new Scanner(System.in);
		System.out.print("Please input size: ");
		int size = input.nextInt();
		System.out.print("Please input number of ships: ");
		int shipNum = input.nextInt();
		Player player = new Player(size, shipNum);
		genericPlayer = player;
		Player computer = new Player(player.getSize(), player.getShipNum());
		computerPlayer = computer;
		for ( ; ; ) {
			if (computer.getShipNum() != player.getShipNum()) {
				computer = new Player(player.getSize(), player.getShipNum());
				computerPlayer = computer;
				continue;
			}
			else {
				break;
			}
		}
		if (player.getShipNum() != shipNum) {
			System.out.println("Can not place " + shipNum + " ships!");
		}
		System.out.println("Player Final Placement: " + player.getShipNum() + " ships!");
		System.out.println("Computer Final Placement: " + computer.getShipNum() + " ships!");
		int testNum;

		//Graphics
		JFrame window = new JFrame("Battleship");
		window.setBounds(100, 100, genericPlayer.getSize() * 60 * 2 + 200, genericPlayer.getSize() * 60 + 160);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel canvas = new Main();
		canvas.setBackground(Color.WHITE);
		window.getContentPane().add(canvas);
		window.setVisible(true);

		// Repeat until computer win or player win
		for ( ; ; ) {
			System.out.print("Please input x-coordinate of guess: ");
			int xGuess = input.nextInt() - 1;
			System.out.print("Please input y-coordinate of guess: ");
			int yGuess = input.nextInt() - 1;
			if (xGuess < 0 || xGuess >= player.getSize() || yGuess < 0 || yGuess >= player.getSize()) {
				System.out.println("This guess is not valid!");
				continue;
			}
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
			// make computer remember the previous guess
			int prevCompXPos;
			int prevCompYPos;
			while (player.beenGuessed(compXPos, compYPos)) {
				compXPos = rand.nextInt(player.getSize());
				compYPos = rand.nextInt(player.getSize());
			}
			if (!player.previousGuess) {
				prevCompXPos = compXPos;
				prevCompYPos = compYPos;
			}
			else {
				prevCompXPos = player.prevXGuess;
				prevCompYPos = player.prevYGuess;
			}
			/*
			 * https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
			 * Wait code
			 */
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
			//make the computer guess arround the square of previous guessed.
			boolean compGuessHit = player.semiSmartGuess(prevCompXPos, prevCompYPos);
			System.out.println("Computer guessed at: (" + (player.prevXGuess + 1) + "," + (player.prevYGuess + 1) + ")");
			if (compGuessHit) {
				System.out.println("Computer hit a ship!");
			}
			else {
				System.out.println("Computer Missed!");
			}
			canvas.repaint();
			if (player.hitsLeft() == 0) {
				System.out.println("Computer Won!");
				break;
			}
		}
		/*
		 * https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
		 * Wait code
		 */
		try
		{
			Thread.sleep(5000);
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
		input.close();
		System.exit(0);
	}
}
