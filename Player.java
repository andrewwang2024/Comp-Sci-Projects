import java.awt.*;
import java.util.Random;

public class Player {
    // Stores ship positions
    public boolean[][] ships;

    // Stores previous guess
    public boolean previousGuess = false;
    int prevXGuess = 0;
    int prevYGuess = 0;

    // Stores guesses of where opponent guessed
    public boolean[][] hits;

    // Total number of ship slots not hit
    private int shipHitsLeft = 0;

    // Tracks number of ships
    private int shipNum = 0;

    // Make sure there is a way to track how many ships of sizes there are
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
        // Need to make this dynamic (for differing ship length)
        // Need to put into for loop
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
            // Will need to get rid of
            if (shipNum * 3 >= size * size) {
                shipNum = size * size / 3;
                if (shipNum * 3 == size * size) {
                    shipNum--;
                }
            }

            // Initialization of ship hits
            if (shipHitsLeft == 0) {
                shipHitsLeft = shipNum * 3;
            }

            if (this.shipNum == 0) {
                this.shipNum = shipNum;
            }

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
                    if (impossibleHorizontalIncrement > shipNum * 1000) {
                        break randomHorizontalCheck;
                    }

                    // Upper left x-coord is move to the left by 3
                    try {
                        shipX = rand.nextInt(size - 3);
                    }
                    catch (Exception e) {
                        shipX = 0;
                    }

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
                    // Places ships
                    if (!isOccupied) {
                        for (int j = 0; j < 3; j++) {
                            ships[shipX + j][shipY] = true;
                        }
                        shipHitsLeft = i * 3;
                        this.shipNum = i;
                        break;
                    }
                    else {
                        // Tells computer to find a new location if space is occupied
                        k--;
                        impossibleHorizontalIncrement++;
                        if (impossibleHorizontalIncrement > shipNum * 1000) {
                            impossibleHorizontal = true;
                            i--;
                            shipHitsLeft = i * 3;
                            this.shipNum = i;
                            break randomHorizontalCheck;
                        }
                        continue randomHorizontalCheck;
                    }
                }
            }
            else {
                // Checks Vertical Position
                // Add random length
                randomVerticalCheck:
                for (int k = 1; k <= shipNum; k++) {
                    // Checks if veritical positioning is impossible
                    if (impossibleVerticalIncrement > shipNum * 1000) {
                        break randomVerticalCheck;
                    }
                    isOccupied = false;
                    shipX = rand.nextInt(size);
                    // Upper left y-coord is moved 3 units up
                    try {
                        shipY = rand.nextInt(size - 3);
                    }
                    catch (Exception e) {
                        shipY = 0;
                    }

                    // Checks for occupied spaces where the ship is going to be placed
                    for (int j = 0; j < 3; j++) {
                        isOccupiedArray[j] = ships[shipX][shipY + j];
                        if (isOccupiedArray[j]) {
                            isOccupied = true;
                            break;
                        }
                    }
                    // Places ships
                    if (!isOccupied) {
                        for (int j = 0; j < 3; j++) {
                            ships[shipX][shipY + j] = true;
                        }
                        shipHitsLeft = i * 3;
                        this.shipNum = i;
                        break;
                    }
                    else {
                        // Tells computer to find a new location if space is occupied
                        k--;
                        impossibleVerticalIncrement++;
                        if (impossibleVerticalIncrement > shipNum * 1000) {
                            impossibleVertical = true;
                            i--;
                            // Fix these for the upcoming update
                            shipHitsLeft = i * 3;
                            this.shipNum = i;
                            break randomVerticalCheck;
                        }
                        continue randomVerticalCheck;
                    }
                }
            }
            // Checks for any un-chosen available spaces
            // Iterates through all possible spaces
            if (impossibleHorizontal && impossibleVertical) {
                for (int k = 0; k < ships.length; k++) {
                    for (int j = 0; j <= ships.length - 3; j++) {
                        isOccupied = false;
                        for (int l = 0; l < 3; l++) {
                            isOccupiedArray[l] = ships[k][j + l];
                            if (isOccupiedArray[l]) {
                                isOccupied = true;
                                break;
                            }
                        }
                        if (!isOccupied) {
                            for (int l = 0; l < 3; l++) {
                                ships[k][j + l] = true;
                            }
                            shipHitsLeft = i * 3;
                            this.shipNum = i;
                            break;
                        }
                    }
                }
                for (int k = 0; k <= ships.length - 3; k++) {
                    for (int j = 0; j < ships.length; j++) {
                        isOccupied = false;
                        for (int l = 0; l < 3; l++) {
                            isOccupiedArray[l] = ships[k + l][j];
                            if (isOccupiedArray[l]) {
                                isOccupied = true;
                                break;
                            }
                        }
                        if (!isOccupied) {
                            for (int l = 0; l < 3; l++) {
                                ships[k + l][j] = true;
                            }
                            shipHitsLeft = i * 3;
                            this.shipNum = i;
                            break;
                        }
                    }
                }
            }
        }
    }

    // Renamed method 'fire' to 'fireShip' for readability
    // Renamed parameters 'x' and 'y' to 'xPos' and 'yPos' for readability

    public boolean fireShip(int xPos, int yPos) {
        boolean hit;
        // Checks if there is a ship at the position fired
        if (ships[yPos][xPos] == true) {
            hit = true;
            previousGuess = true;
            shipHitsLeft--;
        }
        else {
            previousGuess = false;
            hit = false;
        }
        hits[yPos][xPos] = true;
        return hit;
    }

    public int hitsLeft() {
        return shipHitsLeft;
    }

    // Renamed parameters 'x' and 'y' to 'xPos' and 'yPos' for readability
    public boolean beenGuessed(int xPos, int yPos) {
        boolean verify;
        if (hits[yPos][xPos] == true) {
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

    public int getShipNum() {
        return shipNum;
    }

    public boolean semiSmartGuess(int xPos, int yPos) {
        Random rand = new Random();
        int guessNum = rand.nextInt(4) + 1;
        boolean[] options = {true, true, true, true};
        int impossibleCounter = 0;
        if (previousGuess) {
            optionLoop:
            for ( ; ; ) {
                // Tells computer to go to another random space if options are all unavailable
                if (!options[0] && !options[1] && !options[2] && !options[3]) {
                    prevXGuess = rand.nextInt(getSize());
                    prevYGuess = rand.nextInt(getSize());
                    while (beenGuessed(prevXGuess, prevYGuess)) {
                        prevXGuess = rand.nextInt(getSize());
                        prevYGuess = rand.nextInt(getSize());
                    }
                    return fireShip(prevXGuess, prevYGuess);
                }
                if (impossibleCounter > 1000) {
                    prevXGuess = rand.nextInt(getSize());
                    prevYGuess = rand.nextInt(getSize());
                    while (beenGuessed(prevXGuess, prevYGuess)) {
                        prevXGuess = rand.nextInt(getSize());
                        prevYGuess = rand.nextInt(getSize());
                    }
                    return fireShip(prevXGuess, prevYGuess);
                }
                switch (guessNum) {
                    // Chooses the space to the right
                    case 1:
                        // Terminates if this option isn't available
                        if (!options[0]) {
                            guessNum = rand.nextInt(3) + 2;
                            impossibleCounter++;
                            break;
                        }
                        // Checks for ships that have been hit and goes to the correct case
                        try {
                            if (beenGuessed(xPos, yPos - 1)) {
                                if (ships[yPos - 1][xPos]) {
                                    guessNum = 3;
                                    impossibleCounter++;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[3] = false;
                        }
                        try {
                            if (beenGuessed(xPos, yPos + 1)) {
                                if (ships[yPos + 1][xPos]) {
                                    guessNum = 4;
                                    impossibleCounter++;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[2] = false;
                        }
                        // Processing to see whether the case is valid or not
                        try {
                            boolean buffer = beenGuessed(xPos + 1, yPos);
                        }
                        catch (Exception e) {
                            options[0] = false;
                            guessNum = rand.nextInt(3) + 2;
                            impossibleCounter++;
                            break;
                        }
                        if (beenGuessed(xPos + 1, yPos)) {
                            options[0] = false;
                            if (!options[1]) {
                                guessNum = rand.nextInt(2) + 3;
                                impossibleCounter++;
                                break;
                            }
                            if (ships[yPos][xPos + 1]) {
                                guessNum = 2;
                                impossibleCounter++;
                                break;
                            }
                            guessNum = rand.nextInt(3) + 2;
                            impossibleCounter++;
                            break;
                        }

                        prevXGuess = xPos + 1;
                        prevYGuess = yPos;
                        return fireShip(xPos + 1, yPos);
                    // Chooses the space to the left
                    case 2:
                        // Terminates if this option isn't available
                        if (!options[1]) {
                            guessNum = rand.nextInt(3);
                            impossibleCounter++;
                            if (guessNum == 0) {
                                guessNum = 1;
                            }
                            else if (guessNum == 1) {
                                guessNum = 3;
                            }
                            else if (guessNum == 2) {
                                guessNum = 4;
                            }
                            break;
                        }
                        // Checks for ships that have been hit and goes to the correct case
                        try {
                            if (beenGuessed(xPos, yPos - 1)) {
                                if (ships[yPos - 1][xPos]) {
                                    guessNum = 3;
                                    impossibleCounter++;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[3] = false;
                        }
                        try {
                            if (beenGuessed(xPos, yPos + 1)) {
                                if (ships[yPos + 1][xPos]) {
                                    guessNum = 4;
                                    impossibleCounter++;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[2] = false;
                        }
                        try {
                            boolean buffer = beenGuessed(xPos - 1, yPos);
                        }
                        catch (Exception e) {
                            options[1] = false;
                            guessNum = rand.nextInt(3);
                            impossibleCounter++;
                            if (guessNum == 0) {
                                guessNum = 1;
                            }
                            else if (guessNum == 1) {
                                guessNum = 3;
                            }
                            else if (guessNum == 2) {
                                guessNum = 4;
                            }
                            break;
                        }
                        // Processing to see whether the case is valid or not
                        if (beenGuessed(xPos - 1, yPos)) {
                            options[1] = false;
                            if (!options[0]) {
                                guessNum = rand.nextInt(2) + 3;
                                impossibleCounter++;
                                break;
                            }
                            if (ships[yPos][xPos - 1]) {
                                guessNum = 1;
                                impossibleCounter++;
                                break;
                            }
                            guessNum = rand.nextInt(3);
                            impossibleCounter++;
                            if (guessNum == 0) {
                                guessNum = 1;
                            }
                            else if (guessNum == 1) {
                                guessNum = 3;
                            }
                            else if (guessNum == 2) {
                                guessNum = 4;
                            }
                            break;
                        }
                        prevXGuess = xPos - 1;
                        prevYGuess = yPos;
                        return fireShip(xPos - 1, yPos);
                    // Chooses the space to the bottom
                    case 3:
                        // Terminates if this option isn't available
                        if (!options[2]) {
                            guessNum = rand.nextInt(3);
                            impossibleCounter++;
                            if (guessNum == 0) {
                                guessNum = 1;
                            }
                            else if (guessNum == 1) {
                                guessNum = 2;
                            }
                            else if (guessNum == 2) {
                                guessNum = 4;
                            }
                            break;
                        }
                        // Checks for ships that have been hit and goes to the correct case
                        try {
                            if (beenGuessed(xPos + 1, yPos)) {
                                if (ships[yPos][xPos + 1]) {
                                    guessNum = 2;
                                    impossibleCounter++;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[0] = false;
                        }
                        try {
                            if (beenGuessed(xPos - 1, yPos)) {
                                if (ships[yPos][xPos - 1]) {
                                    guessNum = 1;
                                    impossibleCounter++;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[1] = false;
                        }
                        // Processing to see whether the case is valid or not
                        try {
                            boolean buffer = beenGuessed(xPos, yPos + 1);
                        }
                        catch (Exception e) {
                            options[2] = false;
                            guessNum = rand.nextInt(3);
                            impossibleCounter++;
                            if (guessNum == 0) {
                                guessNum = 1;
                            }
                            else if (guessNum == 1) {
                                guessNum = 2;
                            }
                            else if (guessNum == 2) {
                                guessNum = 4;
                            }
                            break;
                        }
                        if (beenGuessed(xPos, yPos + 1)) {
                            options[2] = false;
                            if (!options[3]) {
                                guessNum = rand.nextInt(2) + 1;
                                impossibleCounter++;
                                break;
                            }
                            if (ships[yPos + 1][xPos]) {
                                guessNum = 4;
                                impossibleCounter++;
                                break;
                            }
                            guessNum = rand.nextInt(3);
                            impossibleCounter++;
                            if (guessNum == 0) {
                                guessNum = 1;
                            }
                            else if (guessNum == 1) {
                                guessNum = 2;
                            }
                            else if (guessNum == 2) {
                                guessNum = 4;
                            }
                            break;
                        }
                        prevXGuess = xPos;
                        prevYGuess = yPos + 1;
                        return fireShip(xPos, yPos + 1);
                    // Chooses the space to the bottom
                    case 4:
                        // Terminates if this option isn't available
                        if (!options[3]) {
                            guessNum = rand.nextInt(3) + 1;
                            impossibleCounter++;
                            break;
                        }
                        // Checks for ships that have been hit and goes to the correct case
                        try {
                            if (beenGuessed(xPos + 1, yPos)) {
                                if (ships[yPos][xPos + 1]) {
                                    guessNum = 2;
                                    impossibleCounter++;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[0] = false;
                        }
                        try {
                            if (beenGuessed(xPos - 1, yPos)) {
                                if (ships[yPos][xPos - 1]) {
                                    impossibleCounter++;
                                    guessNum = 1;
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            options[1] = false;
                        }
                        // Processing to see whether the case is valid or not
                        try {
                            boolean buffer = beenGuessed(xPos, yPos - 1);
                        }
                        catch (Exception e) {
                            options[3] = false;
                            guessNum = rand.nextInt(3) + 1;
                            impossibleCounter++;
                            break;
                        }
                        if (beenGuessed(xPos, yPos - 1)) {
                            options[3] = false;
                            if (!options[2]) {
                                guessNum = rand.nextInt(2) + 1;
                                impossibleCounter++;
                                break;
                            }
                            if (ships[yPos - 1][xPos]) {
                                guessNum = 3;
                                impossibleCounter++;
                                break;
                            }
                            guessNum = rand.nextInt(3) + 1;
                            impossibleCounter++;
                            break;
                        }
                        prevXGuess = xPos;
                        prevYGuess = yPos - 1;
                        return fireShip(xPos, yPos - 1);
                }
            }
        }
        // Fires original position if the previous guess was unsuccessful
        prevXGuess = xPos;
        prevYGuess = yPos;
        return fireShip(xPos, yPos);
    }

    public void draw(Graphics g, boolean isComp){
        // Offset for computer grid
        int xOffset = getSize() * 60 + 90;
        // Draws player board
        if (!isComp) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            int stringXIndex = 0;
            // Labels player board and hits left
            if (getSize() % 2 == 1) {
                stringXIndex = getSize() / 2 + 1;
                g.drawString("My Board", stringXIndex * 60 - 20, 35);
                g.drawString("Hits Left: " + shipHitsLeft, stringXIndex * 60 - 30, getSize() * 60 + 90);
            } else {
                stringXIndex = getSize() / 2;
                g.drawString("My Board", stringXIndex * 60 + 10, 35);
                g.drawString("Hits Left: " + shipHitsLeft, stringXIndex * 60, getSize() * 60 + 90);
            }
            // Draws bottom layer, which shows missed hits and ships
            for (int i = 0; i < getSize(); i++) {
                for (int k = 0; k < getSize(); k++) {
                    if (ships[k][i]) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(new Color(0, 0, 0, 128));
                    }
                    g.fillRect(60 + i * 60, 60 + k * 60, 60, 60);
                    g.setColor(Color.black);
                    String xIndex = "" + (i + 1);
                    g.drawString(xIndex, i * 60 + 85, 60);
                    String yIndex = "" + (k + 1);
                    g.drawString(yIndex, 40, 100 + k * 60);
                }
            }
            // Draws top layer
            for (int i = 0; i < getSize(); i++) {
                for (int k = 0; k < getSize(); k++) {
                    // Turns top layer square transparent if it has been guessed
                    if (beenGuessed(i, k)) {
                        g.setColor(new Color(0, 0, 0, 0));
                    } else if (ships[k][i]) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(60 + i * 60, 60 + k * 60, 60, 60);
                }
            }
            // Draws grid
            g.setColor(Color.BLACK);
            for (int i = 0; i < getSize(); i++) {
                for (int k = 0; k < getSize(); k++) {
                    g.drawRect(60 + i * 60, 60 + k * 60, 60, 60);
                }
            }
        }
        else {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            int stringXIndex = 0;
            // Labels enemy board
            if (getSize() % 2 == 1) {
                stringXIndex = getSize() / 2 + 1;
                g.drawString("Enemy Board", stringXIndex * 60 - 20 + xOffset - 20, 35);
                g.drawString("Hits Left: " + shipHitsLeft, stringXIndex * 60 - 30 + xOffset, getSize() * 60 + 90);
            } else {
                stringXIndex = getSize() / 2;
                g.drawString("Enemy Board", stringXIndex * 60 + 10 + xOffset - 20, 35);
                g.drawString("Hits Left: " + shipHitsLeft, stringXIndex * 60 + xOffset, getSize() * 60 + 90);
            }
            // Draws bottom layer, which shows missed hits and ships
            for (int i = 0; i < getSize(); i++) {
                for (int k = 0; k < getSize(); k++) {
                    if (ships[k][i]) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(new Color(0, 0, 0, 128));
                    }
                    g.fillRect(60 + i * 60 + xOffset, 60 + k * 60, 60, 60);
                    g.setColor(Color.black);
                    String xIndex = "" + (i + 1);
                    g.drawString(xIndex, i * 60 + 85 + xOffset, 60);
                    String yIndex = "" + (k + 1);
                    g.drawString(yIndex, 40 + xOffset, 100 + k * 60);
                }
            }
            // Draws top layer
            for (int i = 0; i < getSize(); i++) {
                for (int k = 0; k < getSize(); k++) {
                    // Turns top layer square transparent if it has been guessed
                    if (beenGuessed(i, k)) {
                        g.setColor(new Color(0, 0, 0, 0));
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(60 + i * 60 + xOffset, 60 + k * 60, 60, 60);
                }
            }
            // Draws Grid
            g.setColor(Color.BLACK);
            for (int i = 0; i < getSize(); i++) {
                for (int k = 0; k < getSize(); k++) {
                    g.drawRect(60 + i * 60 + xOffset, 60 + k * 60, 60, 60);
                }
            }
        }
    }
}
