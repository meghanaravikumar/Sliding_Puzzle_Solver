
import java.util.*;
/**
 *
 * @author ebonyrosemilbury
 */
public class TrayNode {
	private final Tray config;
	private final ArrayList<Integer[]> moves;
	private final ArrayList<Integer[]> movesMade;
	public final static HashSet<Tray> seenBoards = new HashSet<>();
	public TrayNode(TrayNode a) {
		this.config = new Tray(a.config);
		this.moves = new ArrayList<>();
		movesMade = new ArrayList<>();
		movesMade.addAll(a.movesMade);
	}
	public TrayNode(Tray t){
		seenBoards.add(t);
		this.config = t;
		this.moves = new ArrayList<>();
		movesMade = new ArrayList<>();
	}
	public void possibleMoves(){
		//for every rectangle in config
		//we get the four single spots around it - checking for out-of-bounds-ness
		//by doing y + y dimensions and x - x dimensions, whichever is applicable
		for (Rectangle r : config.getContainer().values()){
			Integer[] move = new Integer[4];
			move[0] = r.topLeft.x;
			move[1] = r.topLeft.y;
			if (r.topLeft.x + r.dimensions.x < config.dimensions.x){
				move [2] = r.topLeft.x + 1;
				move[3] = move[1];
				//System.out.println(move[0] + " " + move[1] + " " + move[2] + " " + move[3] + " moving right one");
				this.moves.add(move);
			}
			Integer[] move1 = new Integer[4];
			move1[0] = r.topLeft.x;
			move1[1] = r.topLeft.y;
			if (r.topLeft.y + r.dimensions.y  < config.dimensions.y){
				move1[2] = move[0];
				move1[3] = r.topLeft.y + 1;
				//System.out.println(move1[0] + " " + move1[1] + " " + move1[2] + " " + move1[3] + " moving down one");
				this.moves.add(move1);
			}
			Integer[] move2 = new Integer[4];
			move2[0] = r.topLeft.x;
			move2[1] = r.topLeft.y;
			if ((r.topLeft.x - 1) >= 0){
				move2 [2] = r.topLeft.x - 1;
				move2 [3] = move[1];
				//System.out.println(move2[0] + " " + move2[1] + " " + move2[2] + " " + move2[3] + " moving left one");
				this.moves.add(move2);
			}
			Integer[] move3 = new Integer[4];
			move3[0] = r.topLeft.x;
			move3[1] = r.topLeft.y;
			if ((r.topLeft.y - 1) >= 0){
				move3[2] = move[0];
				move3[3] = r.topLeft.y - 1;
				//System.out.println(move3[0] + " " + move3[1] + " " + move3[2] + " " + move3[3] + " moving up one");
				this.moves.add(move3);
			}
		}
		for (Integer[] i : this.moves){
			//System.out.println(i[0] + " " + i[1] + " " + i[2] + " " + i[3] + " houston, we've had a problem");
		}
	}
	public static TrayNode copyNode(Corner org, Corner next, TrayNode toCopy) { //will create new Tray without having to re-add unmoved pieces
		TrayNode tre = new TrayNode(toCopy);
		//System.out.println ("Tre is " + tre.config.toString() + "at beginning");//
		Rectangle r = tre.config.getContainer().get(org); //gets the current rectangle that we want to remove
		if (r == null) {
			System.out.println("Illegal move");
			System.exit(6);
		}
		//System.out.println("org is " + org);
		//System.out.println("next is " + next);
		Corner dim = r.dimensions; //gets that rectangle's dimensions
		tre.config.removeElement(r); // remove old rectangle from old board
		tre.config.getContainer().remove(org); //removes the old rectangle from the hashmap of rectangles
		Rectangle moved = new Rectangle(next, dim); //make a new rectangle with the dimensions of the old one at given point
		tre.config.getContainer().put(next, moved); //put in the new pairing
		Integer[] m = new Integer[4];
		m[0] = org.x;
		m[1] = org.y;
		m[2] = next.x;
		m[3] = next.y;
		tre.movesMade.add(m);
		if (!tre.config.addToBoardS(moved)){ //update the board
			return null;
		}
		if (seenBoards.contains(tre.config)){
			return null;
		}
		//System.out.println ("toCopy is " + toCopy.config.toString() + "at end");
		return tre;
	}
	public ArrayList<Integer[]> getMovesMade(){
		return this.movesMade;
	}
	public void movePrint() {
		for (int i = 0; i < this.movesMade.size(); i++){
			Integer[] k = movesMade.get(i);
			System.out.println(k[0] + " " + k[1] + " " + k[2] + " " + k[3]);
		}
		System.exit(0);
	}
	public Tray getConfig() {
		Tray ret = new Tray(config);
		return ret;
	}
	public ArrayList<Integer[]> getMoves() {
		ArrayList<Integer[]> ret =new ArrayList<>();
		ret.addAll(moves);
		return ret;
	}
}
