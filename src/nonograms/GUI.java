package nonograms;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.Arrays;

public class GUI extends JPanel implements MouseListener
{
    //creating variables
    Dimension gridSize;
    Grid grid;
    int size;
    int frameWidth;
    int frameHeight;
    int gridWidth;
    int gridHeight;
    int squareSideLength;
    boolean[][] gridArray; //array that stores modified state of the grid
    int [][] clickedArray; //array that stores the state of each square
    boolean leftClick;
    boolean action; //boolean to let the program know if the user took an action or not (starts at false and will always be true after first action until the puzzle is done)
    int column; //column of clicked square
    int row; //row of clicked square

    public GUI(int width, int height, int size)
    {  
        super();
        this.size = size;
        this.grid = new Grid(size); //creating the arrangement of the puzzle
        this.frameWidth = width; //setting up dimensions of the frame and the grid
        this.frameHeight = height;
        this.gridWidth = width-100;
        this.gridHeight = height-100;
        this.squareSideLength  = 40;
        this.gridSize = new Dimension(gridWidth, gridHeight);
        this.gridArray = new boolean [size][size];
        this.clickedArray = new int [size][size];
        addMouseListener(this);
        initial(); //calling method to set up the initial graphics
    }

    //method to set up initial graphics (buttons)
    public void initial()
    {
        setLayout(null); //setting the layout of the JPanel to null so that we can use coordinates

        //setting up buttons and adding their ActionListeners
        Button butt = new Button("Check Win");
        butt.setFont(new Font("COMIC SANS MS", Font.PLAIN, 20));
        butt.setBounds(40, 370, 150, 40);
        butt.addActionListener(e -> checkWin());
        Button butt2 = new Button("New Puzzle");
        butt2.setFont(new Font("COMIC SANS MS", Font.PLAIN, 20));
        butt2.setBounds(300, 370, 150, 40);
        butt2.addActionListener(e -> newPuzzle());
        this.add(butt);
        this.add(butt2);    
        Button butt3 = new Button("Reveal Answer");
        butt3.setFont(new Font("COMIC SANS MS", Font.PLAIN, 20));
        butt3.setBounds(40, 420, 150, 40);
        butt3.addActionListener(e -> revealAnswer());
        this.add(butt3);
        Button butt4 = new Button("Clear");
        butt4.setFont(new Font("COMIC SANS MS", Font.PLAIN, 20));
        butt4.setBounds(300, 420, 150, 40);
        butt4.addActionListener(e -> clear());
        this.add(butt4);
    }

    //paint method to create the grid and filled in squares
    public void paint(Graphics g)
    {
        super.paint(g); //calling the method of the superclass 
        g.setColor(Color.GRAY); 
        createGrid(g); //calling method to create the empty grid and numbers

        //if the user has taken an action, the logic to change the squares is run
        if (action)
        {   
            //outer for loop representing column
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++) //inner for loop representing row
                {
                    //if the current square is set to empty (0 in clickedArray), clear it and fill it with a square the same colour as the background
                    if (clickedArray[i][j] == 0)
                    {
                        int x = (i+4)*squareSideLength; 
                        int y = (j+4)*squareSideLength;
                        g.clearRect(x+1, y+1, squareSideLength-1, squareSideLength-1);
                        g.setColor(Color.decode("#eeeeee"));
                        g.fillRect(x+1, y+1, squareSideLength-1, squareSideLength-1);
                    }
                    //if the current square is set to filled (1 in clickedArray), fill it with a black square
                    else if(clickedArray[i][j] == 1)
                    {
                        g.setColor(Color.BLACK);
                        int x = (i+4)*squareSideLength;
                        int y = (j+4)*squareSideLength;
                        g.clearRect(x, y, squareSideLength, squareSideLength);
                        g.fillRect(x, y, squareSideLength, squareSideLength);
                    }
                    //if the current square is set to an x (2 in clickedArray), fill it with two lines to form an x
                    else if(clickedArray[i][j] == 2)
                    {
                        int x1 = (i+4)*squareSideLength;
                        int x2 = (i+5)*squareSideLength;
                        int y1 = (j+4)*squareSideLength;
                        int y2 = (j+5)*squareSideLength;
                        g.clearRect(x1+1, y1+1, squareSideLength-1, squareSideLength-1);
                        g.setColor(Color.decode("#eeeeee"));
                        g.fillRect(x1+1, y1+1, squareSideLength-1, squareSideLength-1);
                        g.setColor(Color.RED);
                        g.drawLine(x1, y1, x2, y2);
                        g.drawLine(x1, y2, x2, y1);
                    }
                }
            }
        }
    }

    //method to check if the player has won, triggered by a button press
    public void checkWin()
    {
        //if the user grid is the same as the generated one
        if(Arrays.deepEquals(grid.grid, gridArray))
        {
            JOptionPane.showMessageDialog(null, "You won!");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You lost!");
        }
    }

    //method triggered by a button press to create a new puzzle 
    public void newPuzzle()
    {
        clear();
        grid = new Grid(size);
        repaint();
    }
    
    //method to reveal the answer by printing out the true/false array (in the console)
    public void revealAnswer()
    {
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                System.out.print(grid.grid[x][y] + " ");
            }
            System.out.print("\n");
        }
    }

    //method to clear the grid
    public void clear()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                //setting everything (both the boolean state of each square and the clicked state) to empty/false
                gridArray[i][j] = false;
                clickedArray[i][j] = 0;
            }
        }
        repaint();
    }

    //method to create the grid
    public void createGrid(Graphics g)  
    {
        //create a 5x5 grid at the center of the screen
        g.setColor(Color.black);
        int gridrows = size + 5; //amount of lines needed to create the grid/amount of spaces where a line could be
        int gridcolumns = size + 5;

        //drawing the grid
        for (int i = 1; i < gridrows; i++)
        {   
            //if i = 1 then the lines shouldn't extend all the way
            if (i == 1)
            {
                //the length of the lines are based on the side length of the squares multiplied by the current row/column
                g.drawLine(61 + frameWidth-gridWidth, i*squareSideLength, frameWidth - 140, i*squareSideLength);
                g.drawLine(60, 4*squareSideLength, 60, 9*squareSideLength);
            }
            //leaving spaces where the numbers are 
            else if (i == 2 || i == 3)
            {
                continue;
            }
            else //drawwing the lines of the grid, with i acting as a multiplier to increment their positions (y coordinate for horizontal lines and x for vertical)
            {
                g.drawLine(60, i*squareSideLength, frameWidth - 140,  i*squareSideLength);
                g.drawLine(i*squareSideLength, squareSideLength, i*squareSideLength, 9*squareSideLength);
            }
        }

        //draw the numbers
        g.setColor(Color.black);
        g.setFont(new Font("COMIC SANS MS", Font.PLAIN, 20));
        
        //outer for loop to increment through each row/column
        for (int i = 0; i < size; i++)
        {
            //inner for loop to increment through the 3 numbers for the current row/column
            for (int j = 0; j < 3; j++)
            {
                //TODO (if we have time): make numbers go at bottom when not showing zeroes

                //drawing the numbers with their positioning being based on the size of the squares and current value of the loop iterators
                if(grid.columnNumbers[i][j] != 0) //not drawing zeroes because they are just placeholders
                {
                    g.drawString(Integer.toString(grid.columnNumbers[i][j]), (i+4)*squareSideLength + 13, (j+1)*squareSideLength + 25);
                }
                if (grid.rowNumbers[i][j] != 0)
                {
                    g.drawString(Integer.toString(grid.rowNumbers[i][j]), (j+1)*squareSideLength + 26, (i+4)*squareSideLength + 25);
                }
            }
        }
    }

    //mouseListener method for when the user presses a mouse button to perform an action
    public void mousePressed(MouseEvent e)
    {
        //getting the coordinates of where the mouse was pressed
        int mouseX = e.getX();
        int mouseY = e.getY();

        //returning if the mouse press is outside of the grid (the grid boundaries are 4 and 9 times the side length of the square due to the rows and column of the numbers)
        if (mouseX < 4*squareSideLength || mouseX > 9*squareSideLength || mouseY < 4*squareSideLength || mouseY > 9*squareSideLength)
        {
            return;
        }

        //getting the row and column numbers (from 0 to 4) of the mouse press by checking if the coordinates are within two of the grid lines
        for (int i = 4; i < 9; i++)
        {
            if(mouseY >= i*squareSideLength && mouseY < (i+1)*squareSideLength)
            {
                row = i-4;
            }
        }
        for (int i = 4; i < 9; i++)
        {
            if (mouseX >= i*squareSideLength && mouseX < (i+1)*squareSideLength)
            {
                column = i-4;
            }
        }

        //checking if the button pressed was the left button or not
        if(e.getButton() == MouseEvent.BUTTON1) //BUTTON1 is left mouse button
        {
            leftClick = true;
        }
        else if (e.getButton() == MouseEvent.BUTTON3) //BUTTON3 is right mouse button
        {
            leftClick = false;
        }

        //if the user left clicked on a filled in or crossed out square or if the right clicked on a crossed out square then set that square to empty and its boolean value to the opposite of what it was (so true to false)
        if ((leftClick && (clickedArray[column][row] == 1 || clickedArray[column][row] == 2)) || (!leftClick && clickedArray[column][row] == 2)) 
        {
            clickedArray[column][row] = 0;
            gridArray[column][row] = false; 
        }
        //if the user left clicked on a square in any other conditions, fill it
        else if (leftClick)
        {
            clickedArray[column][row] = 1;
            gridArray[column][row] = true;
        }
        //if the user right clicked on a square in any other conditions, cross it out
        else if (!leftClick)
        {
            clickedArray[column][row] = 2;
            gridArray[column][row] = false; //This is here so that the program knows that the square is not filled in because if it was filled and then crossed, it would remain filled
        }

        action = true; //let the program know the user has taken an action
        repaint();
    }

    public void mouseReleased(MouseEvent e) 
    {
        //This needs to be here since we are implementing an abstract class :P
    }

    public void mouseEntered(MouseEvent e) 
    {
        //This needs to be here since we are implementing an abstract class :P
    }

    public void mouseExited(MouseEvent e) 
    {
        //This needs to be here since we are implementing an abstract class :P
    }

    public void mouseClicked(MouseEvent e) 
    {
        //This needs to be here since we are implementing an abstract class :P
    }
    
}

