import java.util.*;

public class Player {
    // Stores ship positions
    public boolean[][] ships;


    // Stores guesses of where opponent guessed
    public boolean[][] hits;

    // Total number of ship slots not hit
    private int shipHitsLeft;

    // Renamed parameters 'n' and 's' to 'size' and 'shipNum' for readability
    public Player(int size, int shipNum) {
        Random rand = new Random();
        int orientationNum;
        int shipX;
        int shipY;
        boolean[] isOccupiedArray = {true, true, true};
        boolean isOccupied;
        /*
         *  Orientation is a boolean that determines orientation
         *  Output:
         *  true - horizontal
         *  false - vertical
         */
        boolean orientation;
        ships = new boolean[size][size];
        hits = new boolean[size][size];
        shipHitsLeft = shipNum * 3;
        for (int i = 1; i <= shipNum; i++) {
            if (shipNum * 3 >= size * size) {
                if (shipNum * 3 == size * size) {
                    shipNum--;

                }
                else {
                    shipNum = size * size / 3;
                }
                System.out.println("Not enought space! Number of ships is now " + shipNum + "!");
            }
            // Code block for orientation determination
            orientationNum = rand.nextInt(2);
            if (orientationNum == 0) {
                orientation = true;
            }
            else {
                orientation = false;
            }
            // Code block for ship positioning
            if (orientation) {
                randomHorizontalCheck:
                for (int k = 1; k <= shipNum; k++) {
                    shipX = rand.nextInt(size - 3);
                    shipY = rand.nextInt(size);
                    isOccupied = false;
                    for (int j = 0; j < 3; j++) {
                        isOccupiedArray[j] = ships[shipX + j][shipY];
                        if (isOccupiedArray[j]) {
                            isOccupied = true;
                            break;
                        }
                    }
                    if (!isOccupied) {
                        for (int j = 0; j < 3; j++) {
                            ships[shipX + j][shipY] = true;
                        }
                        break;
                    }
                    else {
                        k--;
                        continue randomHorizontalCheck;
                    }
                }
            }
            else {
                randomVerticalCheck:
                for (int k = 1; k <= shipNum; k++) {
                    isOccupied = false;
                    shipX = rand.nextInt(size);
                    shipY = rand.nextInt(size - 3);
                    for (int j = 0; j < 3; j++) {
                        isOccupiedArray[j] = ships[shipX][shipY + j];
                        if (isOccupiedArray[j]) {
                            isOccupied = true;
                            break;
                        }
                    }
                    if (!isOccupied) {
                        for (int j = 0; j < 3; j++) {
                            ships[shipX][shipY + j] = true;
                        }
                        break;
                    }
                    else {
                        k--;
                        continue randomVerticalCheck;
                    }
                }
            }
        }
    }

    // Renamed method 'fire' to 'isShip' for readability
    // Renamed parameters 'x' and 'y' to 'xPos' and 'yPos' for readability

    // Will implement guess check in driver class;
    public boolean isShip(int xPos, int yPos) {
        boolean hit;
        if (ships[xPos][yPos] == true) {
            hit = true;
            shipHitsLeft--;
        }
        else {
            hit = false;
        }
        hits[xPos][yPos] = true;
        return hit;
    }

    public int hitsLeft() {
        return shipHitsLeft;
    }

    // Renamed parameters 'x' and 'y' to 'xPos' and 'yPos' for readability
    public boolean beenGuessed(int xPos, int yPos) {
        boolean verify;
        if (hits[xPos][yPos] == true) {
            verify = true;
        }
        else {
            verify = false;
        }
        return verify;
    }

    public int getSize() {
        return ships.length;
    }
}
