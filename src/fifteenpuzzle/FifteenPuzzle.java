package fifteenpuzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FifteenPuzzle {
    public static int SIZE;

    public static int board[][];

//	private void checkBoard() throws BadBoardException {
//		int[] vals = new int[SIZE * SIZE];
//
//		// check that the board contains all number 0...15
//		for (int i = 0; i < SIZE; i++) {
//			for (int j = 0; j < SIZE; j++) {
//				if (board[i][j]<0 || board[i][j]>=SIZE*SIZE)
//					throw new BadBoardException("found tile " + board[i][j]);
//				vals[board[i][j]] += 1;
//			}
//		}
//
//		for (int i = 0; i < vals.length; i++)
//			if (vals[i] != 1)
//				throw new BadBoardException("tile " + i +
//											" appears " + vals[i] + "");
//
//	}

    /**
     * @param fileName
     * @throws FileNotFoundException if file not found
     */
    public FifteenPuzzle(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        SIZE = br.read() - '0';
        br.read();
        board = new int[SIZE][SIZE];
        int c1, c2, s;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                c1 = br.read();
                c2 = br.read();
                s = br.read(); // skip the space
                if (c1 == ' ')
                    c1 = '0';
                if (c2 == ' ')
                    c2 = '0';
                board[i][j] = 10 * (c1 - '0') + (c2 - '0');
            }
        }
        //checkBoard();
        br.close();


    }

    public static int[][] getBoard(){
        return board;
    }

//	private class Pair {
//		int i, j;
//
//		Pair(int i, int j) {
//			this.i = i;
//			this.j = j;
//		}
//	}
//
//	private Pair findCoord(int tile) {
//		int i = 0, j = 0;
//		for (i = 0; i < SIZE; i++)
//			for (j = 0; j < SIZE; j++)
//				if (board[i][j] == tile)
//					return new Pair(i, j);
//		return null;
//	}
//
//	/**
//	 * Get the number of the tile, and moves it to the specified direction
//	 *
//	 */
//	public void makeMove(int tile, int direction) {
//		Pair p = findCoord(tile);
//		int i = p.i;
//		int j = p.j;
//
//		// the tile is in position [i][j]
//		switch (direction) {
//		case UP: {
//			if (i > 0 && board[i - 1][j] == 0) {
//				board[i - 1][j] = tile;
//				board[i][j] = 0;
//				break;
//			}
//
//		}
//		case DOWN: {
//			if (i < SIZE - 1 && board[i + 1][j] == 0) {
//				board[i + 1][j] = tile;
//				board[i][j] = 0;
//				break;
//			}
//		}
//		case RIGHT: {
//			if (j < SIZE - 1 && board[i][j + 1] == 0) {
//				board[i][j + 1] = tile;
//				board[i][j] = 0;
//				break;
//			}
//		}
//		case LEFT: {
//			if (j > 0 && board[i][j - 1] == 0) {
//				board[i][j - 1] = tile;
//				board[i][j] = 0;
//				break;
//			}
//		}
//		}
//
//	}

    /**
     * @return true if and only if the board is solved, i.e., the board has all
     *         tiles in their correct positions
     */
//	public boolean isSolved() {
//		for (int i = 0; i < SIZE; i++)
//			for (int j = 0; j < SIZE; j++)
//				if (board[i][j] != (4 * i + j + 1) % 16)
//					return false;
//		return true;
//	}

    private String num2str(int i) {
        if (i == 0)
            return "  ";
        else if (i < 10)
            return " " + Integer.toString(i);
        else
            return Integer.toString(i);
    }

    public String toString() {
        String ans = "";
        for (int i = 0; i < SIZE; i++) {
            ans += num2str(board[i][0]);
            for (int j = 1; j < SIZE; j++)
                ans += " " + num2str(board[i][j]);
            ans += "\n";
        }
        return ans;
    }
}

