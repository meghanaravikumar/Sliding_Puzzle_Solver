import java.util.*;
	public class Tray implements Iterable<Rectangle> {
	private final HashMap<Corner, Rectangle> container; // = new HashMap<>();
	private final boolean[][] Board;
	public final Corner dimensions;
	
	public Tray(int x, int y) { //creates an empty board with all false
		Board = new boolean[x][y]; //will set everything equal to false
		dimensions = new Corner(x, y);
		container = new HashMap<>();
	}
	
	public Tray(Tray old){
		Board = new boolean[old.dimensions.x][old.dimensions.y];
		this.container = new HashMap<>();
		container.putAll(old.container);
		this.updateBoard(container);
		this.dimensions = old.dimensions.copy();
	}
	public Tray(int x, int y, HashMap<Corner, Rectangle> nodes) { //takes in arraylist with Corners of rectangles
		/*for (Entry<Corner, Rectangle> en : nodes.entrySet()) {
container.put(en.getKey(), en.getValue());
}*/
		container = nodes; //should work, if not we need to initialize container up top
		Board = new boolean[x][y];
		this.updateBoard(nodes);
		dimensions = new Corner(x, y);
	}
	
	public boolean containsRectangle(Rectangle r) {
		return container.containsValue(r);
	}
	public boolean[][] getBoard(){
		return Board;
	}
	public HashMap<Corner, Rectangle> getContainer(){
		return container;
	}
	public void removeElement(Rectangle r){
		this.removeFromBoard(r);
	}
	public void addElement(Rectangle r){
		this.addToBoard(r);
	}
	private void updateBoard(HashMap<Corner, Rectangle> nodes) {// initial tray construction
		for (Rectangle r : nodes.values()) {
			Corner btRight = r.bottomRight;
			int collnt = r.dimensions.y;
			int rowlnt = r.dimensions.x;
			int bRX = btRight.x;
			int bRY = btRight.y;
			while (rowlnt > 0) {
				while (collnt > 0) {
					if (Board[bRX][bRY] == false) {
						Board[bRX][bRY] = true;
						bRY = bRY - 1;
					} else if (Board[bRX][bRY] == true) {
						System.out.println("This is an invalid board.");
						System.exit(5);
					}
					collnt = collnt - 1;
				}
				collnt = r.dimensions.y;
				bRX = bRX - 1;
				bRY = btRight.y;
				rowlnt = rowlnt - 1;
			}
		}
	}
	//will use in constructors
	//if overlap, error/System.exit whatever (do not worry about solver, this is just for initial board)
	private void addToBoard(Rectangle oneNew) {// updates board with one new rectangle
		//this is an overloaded method - it updates the board with just one rectangle, rather than many.
		//will use in copyBoard
		//if the new rectangle update overlaps with another rectangle, error!
		//or find a way to return null so doesn't break solver
		//container.put(oneNew.topLeft, oneNew);
		Corner btRight = oneNew.bottomRight;
		int collnt = oneNew.dimensions.y;
		int rowlnt = oneNew.dimensions.x;
		int bRX = btRight.x;
		int bRY = btRight.y;
		while (rowlnt > 0) {
			while (collnt > 0) {
				if (Board[bRX][bRY] == false) {
					Board[bRX][bRY] = true;
					bRY = bRY - 1;
				} else if (Board[bRX][bRY] == true) {
					System.out.println("This is an invalid board.");
					System.exit(5);
				}
				collnt = collnt - 1;
			}
			collnt = oneNew.dimensions.y;
			bRX = bRX - 1;
			bRY = btRight.y;
			rowlnt = rowlnt - 1;
		}
	}
	private void removeFromBoard(Rectangle oldOne) {
		Corner btRight = oldOne.bottomRight;
		int collnt = oldOne.dimensions.y;
		int rowlnt = oldOne.dimensions.x;
		int bRX = btRight.x;
		int bRY = btRight.y;
		while (rowlnt > 0) {
			while (collnt > 0) {
				if (Board[bRX][bRY] == true) {
					Board[bRX][bRY] = false;
					bRY = bRY - 1;
				} else if (Board[bRX][bRY] == false) {
					System.out.println("This is an invalid board.");
					System.exit(5);
				}
				collnt = collnt - 1;
			}
			collnt = oldOne.dimensions.y;
			bRX = bRX - 1;
			bRY = btRight.y;
			rowlnt = rowlnt - 1;
		}
	}
	public Tray copyBoard(Corner org, Corner next) { //will create new Tray without having to re-add unmoved pieces
		Tray tre = this;
		Rectangle r = tre.container.get(org); //gets the current rectangle that we want to remove
		if (r == null) {
			System.out.println("Illegal move");
			System.exit(4);
		}
		Corner dim = r.dimensions; //gets that rectangle's dimensions
		tre.removeFromBoard(r); // remove old rectangle from old board
		tre.container.remove(org); //removes the old rectangle from the hashmap of rectangles
		Rectangle moved = new Rectangle(next, dim); //make a new rectangle with the dimensions of the old one at given point
		tre.container.put(next, moved); //put in the new pairing
		tre.addToBoard(moved); //update the board
		return tre; //call whenever we make a move
	}
	@Override
	public int hashCode() {
		int sum = Board.length + Board[0].length;
		for (Rectangle r : this) {
			sum += (17 * Objects.hashCode(r));
		}
		return sum;
	}
	@Override
	public boolean equals(Object tre) { //checks to see if board it's called on is the board it's given
		if (tre == null){
			return false;
		}
		if (!tre.getClass().equals(this.getClass())) {
			return false;
		}
		//return (((Tray) tre).container.equals(this.container));
		for (Rectangle r : ((Tray) tre).getContainer().values()){
			if (!this.containsRectangle(r)){
				return false;
			}
		}
		return true;
	}
	
	
	protected boolean addToBoardS(Rectangle oneNew) {// updates board with one new rectangle
		//this is an overloaded method - it updates the board with just one rectangle, rather than many.
		//will use in copyBoard
		//if the new rectangle update overlaps with another rectangle, error!
		//or find a way to return null so doesn't break solver
		//container.put(oneNew.topLeft, oneNew);
		Corner btRight = oneNew.bottomRight;
		int collnt = oneNew.dimensions.y;
		int rowlnt = oneNew.dimensions.x;
		int bRX = btRight.x;
		int bRY = btRight.y;
		while (rowlnt > 0) {
			while (collnt > 0) {
				if (Board[bRX][bRY] == false) {
					Board[bRX][bRY] = true;
					bRY = bRY - 1;
				} else if (Board[bRX][bRY] == true) {
					return false;
				}
				collnt = collnt - 1;
			}
			collnt = oneNew.dimensions.y;
			bRX = bRX - 1;
			bRY = btRight.y;
			rowlnt = rowlnt - 1;
		}
		return true;
	}
	protected boolean removeFromBoardS(Rectangle oldOne) {
		Corner btRight = oldOne.bottomRight;
		int collnt = oldOne.dimensions.y;
		int rowlnt = oldOne.dimensions.x;
		int bRX = btRight.x;
		int bRY = btRight.y;
		while (rowlnt > 0) {
			while (collnt > 0) {
				if (Board[bRX][bRY] == true) {
					Board[bRX][bRY] = false;
					bRY = bRY - 1;
				} else if (Board[bRX][bRY] == false) {
					return false;
				}
				collnt = collnt - 1;
			}
			collnt = oldOne.dimensions.y;
			bRX = bRX - 1;
			bRY = btRight.y;
			rowlnt = rowlnt - 1;
		}
		return true;
	}
	@Override
	public Iterator<Rectangle> iterator() {
		return container.values().iterator();
	}
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		for (Rectangle r :container.values()){
			str.append(r.toString() + " ");
		}
		return str.toString();
	}
	
}
