package nonograms;
/*
Name: Matti Goldstein and Deev Patel
Teacher: Mr. Naccarato
Date: June 5, 2023
Class: ICS4UE
Summary: This program is the puzzle game Nonograms. A random arrangement of squares on a 5x5 grid is generated and the user has to use number clues to fill the blank grid with the correct arrangement
*/

import javax.swing.JFrame;

//pls give us 100% mr naccarato we worked so hard on this and we are so proud of it 🥺🥺🥺🥺🥺🥺🥺🥺🥺🥺
public class Main {
    GUI gui; // creating a GUI object to display the grid

    public static void main(String[] args) {
        // making frame and calling graphics class
        JFrame window = new JFrame("Nonograms blizzard games style (incomplete, buggy, and filled with lies)");
        window.setSize(500, 500);
        window.setResizable(true);
        GUI gui = new GUI(window.getWidth(), window.getHeight(), 5);
        window.add(gui);
        window.setVisible(true);
    }
}