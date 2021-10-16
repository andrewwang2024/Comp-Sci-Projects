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
        // Random number generator
        Random rand = new Random();

        // Number that determines orientation
        int orientationNum;

        // Ship upper left corner coordinate
        int shipX;
        int shipY;

        // Array that tracks whether the ship crosses another ship
        boolean[] isOccupiedArray = {true, true, true};

        // Result of above array
        boolean isOccupied;

        // Orientation is determined by true or false
        // true: horizontal; false: vertical
        boolean orientation;

        //Checks for whether it has become impossible to place a ship
        int impossibleVerticalIncrement = 0;
        int impossibleHorizontalIncrement = 0;
        boolean impossibleHorizontal = false;
        boolean impossibleVertical = false;

        // Initialization of board arrays
        ships = new boolean[size][size];
        hits = new boolean[size][size];

        for (int i = 1; i <= shipNum; i++) {
            // Checks number of ships and adjusts accordingly
            if (shipNum * 3 >= size * size) {
                shipNum = size * size / 3;
                if (shipNum * 3 == size * size) {
                    shipNum--;
                }
                System.out.println("Not enought space! Number of ships is now " + shipNum + "!");
            }

            // Initialization of ship hits
            shipHitsLeft = shipNum * 3;

            // Code block for orientation determination
            orientationNum = rand.nextInt(2);
            if (orientationNum == 1) {
                orientation = true;
            }
            else {
                orientation = false;
            }
            
            // Checks for impossible orientations when spaces are limited
            if (impossibleVertical) {
                orientation = true;
            }
            else if (impossibleHorizontal) {
                orientation = false;
            }


            // Code block for ship positioning
            if (orientation) {
                // Checks Horizonal Positioning
                randomHorizontalCheck:
                for (int k = 1; k <= shipNum; k++) {
                    // Checks if horizontal positioning is impossible
                    if (impossibleHorizontalIncrement > shipNum * 100) {
                        break randomHorizontalCheck;
                    }

                    // Upper left x-coord is move to the left by 3
                    shipX = rand.nextInt(size - 3);
                    shipY = rand.nextInt(size);
                    isOccupied = false;

                    // Checks for occupied spaces where the ship is going to be placed
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
                        // Tells computer to find a new location if space is occupied
                        k--;
                        impossibleHorizontalIncrement++;
                        if (impossibleHorizontalIncrement > shipNum * 100) {
                            impossibleHorizontal = true;
                            System.out.println("Can only place " + i + " ships!");
                            break randomHorizontalCheck;
                        }
                        continue randomHorizontalCheck;
                    }
                }
            }
            else {
                // Checks Vertical Position
                randomVerticalCheck:
                for (int k = 1; k <= shipNum; k++) {
                    // Checks if veritical positioning is impossible
                    if (impossibleVerticalIncrement > shipNum * 100) {
                        break randomVerticalCheck;
                    }
                    isOccupied = false;
                    shipX = rand.nextInt(size);
                    // Upper left y-coord is moved 3 units up
                    shipY = rand.nextInt(size - 3);

                    // Checks for occupied spaces where the ship is going to be placed
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
                        // Tells computer to find a new location if space is occupied
                        k--;
                        impossibleVerticalIncrement++;
                        if (impossibleVerticalIncrement > shipNum * 100) {
                            impossibleVertical = true;
                            System.out.println("Can only place " + i + " ships!");
                            break randomVerticalCheck;
                        }
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
