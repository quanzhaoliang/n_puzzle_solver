package fifteenpuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AStarNode implements Comparable<AStarNode>{
    private byte[][] state;
    private int h;
    private int f;
    private static int N;

    private AStarNode parent;
    private ArrayList lastMove;


    public AStarNode(byte[][] state,AStarNode parent, int N, ArrayList lastMove){
        this.state = state;
        this.N = N;
        this.parent = parent;
        this.lastMove = lastMove;
        if (N > 6){
            this.f = heuristic(state)/2 + calculateTotalEuclideanDistance(state)/2 + hammingDistance(state)+ linearConflict(state);
        }
        else{
            this.f = heuristic(state);
        }


    }
    public int compareTo(AStarNode other){
        return Integer.compare(this.f, other.f);
    }



    //calculate the heuristic using Manhattan Distance
    public static int heuristic(byte[][] board){
        int dist = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                int val = board[i][j];
                if (val != 0){
                    int rowVal = (val-1) / N;
                    int colVal = (val-1) % N;
                    dist += Math.abs(i-rowVal) + Math.abs(j-colVal);
                }
            }
        }
        return dist;
    }

    private static int hammingDistance(byte[][] board) {
        int n = board.length;
        int misplacedTiles = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = board[i][j];

                // Skip the blank spot (0) and tiles in their correct position
                if (tile != 0 && tile != (i * n) + j + 1) {
                    misplacedTiles++;
                }
            }
        }

        return misplacedTiles;
    }


    private static int calculateTotalEuclideanDistance(byte[][] board) {

        int n = board.length;
        int totalEuclideanDistance = 0;

        for (int tile = 1; tile <= n; tile++) {
            int currentRow = -1;
            int currentColumn = -1;
            int goalRow = -1;
            int goalColumn = -1;

            // Iterate through the board to find the current position of the tile
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == tile) {
                        currentRow = i;
                        currentColumn = j;
                    }

                    if (tile == (i * n) + j + 1) {
                        goalRow = i;
                        goalColumn = j;
                    }
                }
            }

            if (currentRow == -1 || currentColumn == -1 || goalRow == -1 || goalColumn == -1) {
                System.out.println("Invalid tile or position.");
                return -1;
            }

            // Calculate the Euclidean distance for the current tile
            int deltaX = currentRow - goalRow;
            int deltaY = currentColumn - goalColumn;
            double euclideanDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            // Add the Euclidean distance to the total sum
            totalEuclideanDistance += euclideanDistance;
        }

        return totalEuclideanDistance;
    }



//    public static int heuristicWithoutR(byte[][] board){
//        Set<Byte> rowE = rowElement();
//        int dist = 0;
//        for (int i = 0; i < N; i++){
//            for (int j = 0; j < N; j++){
//                int val = board[i][j];
//                if (val != 0 && !rowE.contains(val)){
//                    int rowVal = (val-1) / N;
//                    int colVal = (val-1) % N;
//                    dist += Math.abs(i-rowVal) + Math.abs(j-colVal);
//                }
//            }
//        }
//        return dist;
//    }
//
//    public static int heuristicWithoutE(byte[][] board){
//        Set<Byte> colE = colElement();
//        int n = board[0].length;
//        byte[][] fullBoard = fullBoard(board);
//        int dist = 0;
//        for (int i = 0; i < n; i++){
//            for (int j = 1; j < n; j++){
//                int val = fullBoard[i][j];
//                if (val != 0 && !colE.contains(val)){
//                    int rowVal = (val-1) / n;
//                    int colVal = (val-1) % n;
//                    dist += Math.abs(i-rowVal) + Math.abs(j-colVal);
//                }
//            }
//        }
//        return dist;
//    }
//
//
//    public static Set<Byte >SurroundingElements(byte[][] matrix) {
//        Set<Byte> resultSet = new HashSet<>();
//        int rows = matrix.length;
//        int columns = matrix[0].length;
//
//        int targetRow = -1;
//        int targetColumn = -1;
//
//        // Find the position of the given value
//        outerLoop:
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                if (matrix[i][j] == 0) {
//                    targetRow = i;
//                    targetColumn = j;
//                    break outerLoop;
//                }
//            }
//        }
//
//        // Add surrounding elements to the resultSet
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
//                int newRow = targetRow + i;
//                int newColumn = targetColumn + j;
//
//                // Skip the target value itself and check if the position is valid
//                if (!((i == 0 && j == 0) || newRow < 0 || newRow >= rows || newColumn < 0 || newColumn >= columns)) {
//                    resultSet.add(matrix[newRow][newColumn]);
//                }
//            }
//        }
//        return resultSet;
//    }
//
//    private static Set<Byte> rowElement(){
//        byte n = (byte) N;
//        Set<Byte> rowElement = new HashSet<>();
//        for (byte i=1; i <= n; i++){
//            rowElement.add(i);
//        }
//        return rowElement;
//    }
//
//    private static Set<Byte> colElement(){
//        Set<Byte> colElement = new HashSet<>();
//        int k = 1;
//        for (int i=1; i < N; i++){
//            k = k + N;
//            colElement.add((byte)k);
//        }
//        return colElement;
//    }
//
//    private static byte[][] fullBoard(byte[][] board){
//        int x = board.length;
//        int y = board[0].length;
//        byte[][] fullBoard = new byte[y][y];
//        byte c = 1;
//        for (int i = 0; i < y; i++){
//            fullBoard[0][i] = c;
//            c++;
//        }
//        int a = 0;
//        for (int i = 1; i < y; i++){
//            for (int j = 0; j < y; j++){
//                fullBoard[i][j] = board[a][j];
//            }
//            a++;
//        }
//        return fullBoard;
//    }
//
//    public static int colHeuristic(byte[][] board){
//        int x = board.length;
//        int y = board[0].length;
//        byte[][] fullBoard = fullBoard(board);
//        Set<Byte> colE = colElement();
//        int dist = 0;
//        for (int i = 0; i < y; i++){
//            for (int j = 0; j < y; j++){
//                byte val = fullBoard[i][j];
//                if (val != 0 && colE.contains(val)){
//                    int rowVal = (val-1) / y;
//                    int colVal = (val-1) % y;
//                    dist += Math.abs(i-rowVal) + Math.abs(j-colVal);
//                }
//            }
//        }
//
//        Set<Byte> surroundE = SurroundingElements(board);
//        int k = 1;
//        for (int i = 1; i< x; i++){
//            if (surroundE.contains((byte)(k+=y))){
//                dist = dist -1;
//                break;
//            }
//        }
//        return dist;
//    }
//
//    public static int rowHeuristic(byte[][] board){
//        Set<Byte> rowE = rowElement();
//        int dist = 0;
//        for (int i = 0; i < N; i++){
//            for (int j = 0; j < N; j++){
//                byte val = board[i][j];
//                if (val != 0 && rowE.contains(val)){
//                    int rowVal = (val-1) / N;
//                    int colVal = (val-1) % N;
//                    dist += Math.abs(i-rowVal) + Math.abs(j-colVal);
//                }
//            }
//        }
//
//        Set<Byte> surroundE = SurroundingElements(board);
//        for (int i = 1; i<=N; i++){
//            if (surroundE.contains((byte)i)){
//                dist = dist -1;
//                break;
//            }
//        }
//        return dist;
//    }


    public int getF(){
        return f;
    }

    public byte[][] getState(){
        return state;
    }

    public AStarNode getParent() {
        return parent;
    }

    public ArrayList getLastMove() {
        return lastMove;
    }

    private static int linearConflict(byte[][] board) {
        int n = board.length;
        int linearConflictCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = board[i][j];

                // Skip the blank spot (0)
                if (tile == 0) {
                    continue;
                }

                // Check for horizontal conflicts
                if (j < n - 1) {
                    int nextHorizontalTile = board[i][j + 1];
                    if (nextHorizontalTile != 0 && (tile - 1) / n == (nextHorizontalTile - 1) / n && tile > nextHorizontalTile) {
                        linearConflictCount++;
                    }
                }

                // Check for vertical conflicts
                if (i < n - 1) {
                    int nextVerticalTile = board[i + 1][j];
                    if (nextVerticalTile != 0 && (tile - 1) % n == (nextVerticalTile - 1) % n && tile > nextVerticalTile) {
                        linearConflictCount++;
                    }
                }
            }
        }

        return linearConflictCount;
    }



    //    public int heuristic(int[][] board) {
//        int sum = 0;
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (board[i][j] == 0) {
//                    continue;
//                }
//                int[] goalPos = getGoalPosition(board[i][j]);
//                int goalRow = goalPos[0];
//                int goalCol = goalPos[1];
//                int dist = Math.abs(i - goalRow) + Math.abs(j - goalCol);
//                sum += dist;
//            }
//        }
//        return sum;
//    }
//
//    private int[] getGoalPosition(int num) {
//        if (num == 0) {
//            return new int[] {N - 1, N - 1};
//        }
//        int row = (num - 1) / N;
//        int col = (num - 1) % N;
//        return new int[] {row, col};
//    }



}
