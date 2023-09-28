package fifteenpuzzle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class Solver {


	static  int N;
	public static void main(String[] args) {
		System.out.println("number of arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}


		FifteenPuzzle game = game(args[0]);
		N = game.SIZE;
		int[][] gameBoard = game.getBoard();

		byte[][] initialState = boardToByte(gameBoard);
		List<ArrayList> path = solver(initialState);

		if (path == null){
			System.out.println("Board is unsolvable");
		}
		else{
			File output = new File(args[1]);

			try{
				FileWriter wOutput = new FileWriter(output);
				for (int i = 1; i < path.size(); i++){
					if (path.get(i).get(1).equals((byte)1)){
						wOutput.write(path.get(i).get(0) + " " +"L" +"\n");
					}
					else if (path.get(i).get(1).equals((byte)2)){
						wOutput.write(path.get(i).get(0) + " " + "R" +"\n");
					}
					else if (path.get(i).get(1).equals((byte)3)){
						wOutput.write(path.get(i).get(0) + " " + "U" +"\n");
					}
					else{
						wOutput.write(path.get(i).get(0) + " " + "D" +"\n");
					}

				}
				wOutput.close();
			}
			catch (IOException e){
				System.out.println(e);
			}
		}
	}

	private static FifteenPuzzle game(String fileName){
		try {
			FifteenPuzzle game = new FifteenPuzzle(fileName);
			return game;
		}
		catch(IOException e){
			System.out.println(e);
			return null;
		}
	}

	private static byte[][] boardToByte(int[][] state){
		byte[][] board = new byte[N][N];
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				board[i][j] = (byte)state[i][j];
			}
		}
		return board;
	}
	public static byte[][] goal(int size){
		byte[][] g = new byte[size][size];
		byte n = 1;
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				g[i][j] = n;
				n++;
			}
		}
		g[size-1][size-1] = 0;
		return g;
	}

	private static byte[][] copyState(byte[][] state){
		int x =state.length;
		int y = state[0].length;
		byte [][] newState = new byte[x][y];
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				newState[i][j] = state[i][j];
			}
		}
		return newState;
	}
	private static List<AStarNode> expend(AStarNode node){
		List<AStarNode> nodes = new ArrayList<>();
		byte[][] state = node.getState();

		//find the empty tile in the board
		int blankX = -1;
		int blankY = -1;
		outerloop:
		for (int i = 0; i < state.length; i++){
			for (int j = 0; j < state[0].length; j++){
				if (state[i][j] == 0){
					blankX = i;
					blankY = j;
					break outerloop;
				}
			}
		}

		if (blankY > 0){
			byte[][] newState = copyState(state);
			newState[blankX][blankY] = newState[blankX][blankY - 1];
			newState[blankX][blankY -1] = 0;
			ArrayList<Byte> lastMove = new ArrayList<>();
			lastMove.add(newState[blankX][blankY]);
			lastMove.add((byte)2);
			nodes.add(new AStarNode(newState, node, N, lastMove));
		}

		if (blankY < state[0].length-1){
			byte[][] newState = copyState(state);
			newState[blankX][blankY] = newState[blankX][blankY + 1];
			newState[blankX][blankY + 1] = 0;
			ArrayList<Byte> lastMove = new ArrayList<>();
			lastMove.add((newState[blankX][blankY]));
			lastMove.add((byte)1);
			nodes.add(new AStarNode(newState,  node, N, lastMove));
		}

		if (blankX > 0){
			byte[][] newState = copyState(state);
			newState[blankX][blankY] = newState[blankX - 1][blankY];
			newState[blankX - 1][blankY ] = 0;
			ArrayList<Byte> lastMove = new ArrayList<>();
			lastMove.add(newState[blankX][blankY]);
			lastMove.add((byte)4);
			nodes.add(new AStarNode(newState, node, N, lastMove));
		}

		if (blankX < state.length-1){
			byte[][] newState = copyState(state);
			newState[blankX][blankY] = newState[blankX + 1][blankY];
			newState[blankX + 1][blankY ] = 0;
			ArrayList<Byte> lastMove = new ArrayList<>();
			lastMove.add(newState[blankX][blankY]);
			lastMove.add((byte)3);
			nodes.add(new AStarNode(newState, node, N, lastMove));
		}
		return nodes;
	}

	private static boolean findTarget(byte[][] state){
		int n = state.length;
		byte[][] goal = goal(n);
		return Arrays.deepEquals(state, goal);
	}

	public static List<ArrayList> solver(byte[][] initialState){
		AStarNode initialNode = new AStarNode(initialState, null, N, null);
		minHeapPriorityQ<AStarNode> openList = new minHeapPriorityQ<>();
		Set<IntMatrixWrapper> closeList = new HashSet<>();
		openList.addElement(initialNode);
		closeList.add(new IntMatrixWrapper(initialNode.getState()));

		while (!openList.isEmpty()){
			AStarNode node = openList.removeMin();
			closeList.add(new IntMatrixWrapper(node.getState()));
			if (findTarget(node.getState())){
				List<ArrayList> path = new ArrayList<>();
				while (node != null){
					path.add(0, node.getLastMove());
					node = node.getParent();
				}
				return path;
			}
			for (AStarNode neighbor : expend(node)){
				if (!closeList.contains(new IntMatrixWrapper(neighbor.getState()))){
					openList.addElement(neighbor);
				}
			}
		}
		return null;
	}
}


