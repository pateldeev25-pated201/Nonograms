package nonograms;

import java.util.Random;
import java.util.Arrays;

public class Grid
{
    //setting up variables
    Random rand = new Random();
    int size;
    boolean[][] grid; //array for the arrangement of the grid (filled in is true) where boolean[columnIndex][rowIndex]
    int [][] rowNumbers; //array for the row numbers [rowIndex][numbers]
    int [][] columnNumbers; //[columnIndex][numbers]
    
    public Grid(int size)
    {
        this.size = size;
        grid = new boolean[this.size][this.size];
        rowNumbers = new int [this.size][3];
        columnNumbers = new int [this.size][3];
        generate();
        rowNumbers();
        columnNumbers();
    }

    //method to randomly generate the grid
    public void generate()
    {
        //iterating through the grid and randomly setting each square's value to true or false (filled or empty)
        for (int column = 0; column < size; column++)
        {
            for (int row = 0; row < size; row++)
            {
                grid[column][row] = rand.nextBoolean();
            }
        }
    }

    //method to set the row numbers based on the filled in squares
    public void rowNumbers()
    {
        //iterating down the rows
        for (int row = 0; row < size; row++)
        {
            int count = 0; //count of consecutive filled in squares
            int i = 0; //index of the numbers for the current row
            for (int column = 0; column < size; column++) //iterating across the row
            {
                //if the current square is filled in, increase the count
                if (grid[column][row])
                {
                    count++;
                }
                else //once the loops reach an empty square, set the number
                {
                    if(count != 0) //if the count is not 0 set the count as a number to be displayed and reset the count
                    {
                        rowNumbers[row][i] = count;
                        i++;
                        count = 0;
                    }
                }
                if (column == 4) //if it's the last square in the row the count needs to be added as well since the count is normally only added once the loop reaches an empty square
                {
                    if(count != 0)
                    {
                        rowNumbers[row][i] = count;
                        i++;
                        count = 0;
                    }
                }            
            }
        }
    }

    //same method as rowNumbers() but for column numbers
    public void columnNumbers()
    {
        for (int column = 0; column < size; column++)
        {
            int count = 0;
            int i = 0;
            for (int row = 0; row < size; row++)
            {
                if (grid[column][row])
                {
                    count++;
                }
                else
                {
                    if(count != 0)
                    {
                        columnNumbers[column][i] = count;
                        i++;
                        count = 0;
                    }
                } 
                if (row == 4) 
                {
                    if(count != 0)
                    {
                        columnNumbers[column][i] = count;
                        i++;
                        count = 0;
                    }
                }
                 
            }
        }
    }
}