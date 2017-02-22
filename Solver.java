import java.io.*;
import java.util.*;
/**
 *
 * @author ebonyrosemilbury
 */
public class Solver {
	public Tray goal;
	public Tray initBoard;
	public HashSet<Tray> seenBoards = new HashSet<>();
	public Queue<TrayNode> q = new LinkedList<TrayNode>();
	public Solver(File init, File goal){
		createPoints(init);
		createGoal(goal);
	}
	public boolean goalAchieved(Tray tr) {
		for (Rectangle r : goal.getContainer().values()) {
			if (!tr.containsRectangle(r)) {
				return false;
			}
		}
		return true;
	}
	public void createGoal(File file) {
		int x = initBoard.dimensions.x;
		int y = initBoard.dimensions.y;
		HashMap<Corner, Rectangle> contents = new HashMap<Corner, Rectangle>();
		//Scanner in = new Scanner(System.in); // this is for if you're using commandline
		Scanner in;
		try {
			in = new Scanner(new FileReader(file));
			while (in.hasNextInt()) {
				int[] oneShape = new int[4];
				for (int j = 0; j <= 3; j++) {
					oneShape[j] = in.nextInt();
				}
				contents.put(new Corner(oneShape[0], oneShape[1]), new Rectangle(oneShape));
			}
			in.close();
			goal = new Tray(x, y, contents);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(3);
		} //this is for if you're using a file.
	}
	public void createPoints(File file) {
		int x;
		int y;
		HashMap<Corner, Rectangle> contents = new HashMap<Corner, Rectangle>();
		//Scanner in = new Scanner(System.in); // this is for if you're using commandline
		Scanner in;
		try {
			in = new Scanner(new FileReader(file));
			x = in.nextInt();
			y = in.nextInt();
			while (in.hasNextInt()) {
				int[] oneShape = new int[4];
				for (int j = 0; j <= 3; j++) {
					oneShape[j] = in.nextInt();
				}
				contents.put(new Corner(oneShape[0], oneShape[1]), new Rectangle(oneShape));
			}
			in.close();
			initBoard = new Tray(x, y, contents);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(3);
		} //this is for if you're using a file.
	}
	public static void main(String[] args){
		if (args.length != 2) {
			System.exit(2);
		} else {
			File init = new File(args[0]);
			File goal = new File(args[1]);
			Solver sol = new Solver(init, goal);
			TrayNode initial = new TrayNode(sol.initBoard);
			sol.q.add(initial);
			while (sol.q.size() > 0){
				TrayNode curr = sol.q.poll();
				if (sol.goalAchieved(curr.getConfig())){
					//System.out.println("Board succesfully solved");
					curr.movePrint(); //implement in trayNode, not yet implemented
				}
				curr.possibleMoves();
				for (Integer[] m : curr.getMoves()){
					TrayNode nxt = TrayNode.copyNode(new Corner(m[0], m[1]), new Corner(m[2], m[3]), curr); // this will try the move, and if it is unsuccessful return null
					if (nxt != null){
						sol.q.add(nxt);
						sol.seenBoards.add(nxt.getConfig());
						TrayNode.seenBoards.add(nxt.getConfig());
					}
				}
			}
			//System.out.println(sol.seenBoards.size() + " is the number of boards seen");
			//System.out.println(sol.initBoard.dimensions + " is the dimensions of the original board, " + sol.initBoard.getContainer().size() + " is the number of rectangles");
			//System.out.println("Board could not be successfully solved");
			System.exit(1);
		}
	}
}
