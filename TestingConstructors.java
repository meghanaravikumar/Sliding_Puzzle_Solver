/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author ebonyrosemilbury
 */
public class TestingConstructors {
    
    public static void main(String[] args){
        HashMap<Corner, Rectangle> mappy = new HashMap<Corner, Rectangle>();
       
        mappy.put(new Corner(0, 0), new Rectangle (new Corner (0,0), new Corner(1,1)));
        mappy.put(new Corner(1, 1), new Rectangle (new Corner (1,1), new Corner(2,2)));
        //System.out.println(mappy + "mappy"); 
        Tray tre = new Tray(3, 3, mappy);
        System.out.println(Arrays.deepToString(tre.getBoard()) );
        Checker chkr = new Checker(tre);
        //System.out.println(chkr.currBoard.container.get(new Corner (0, 0) + " qwerty"));
        int[] mew = new int[4];
        mew[0] = 0;
        mew[1] = 0;
        mew[2] = 0;
        mew [3] = 1;
        chkr.makeMove(mew);
        System.out.println(Arrays.deepToString(chkr.currBoard.getBoard()) + " Move one");
        int[] qew = new int[4];
        System.out.println(chkr.currBoard.getContainer().containsKey(new Corner(0,1))+ " mewl");
        qew[0] = 1;
        qew[1] = 1;
        qew [2] = 1;
        qew [3] = 0;
        chkr.makeMove(qew);
        System.out.println(Arrays.deepToString(chkr.currBoard.getBoard()) + " Move two");
        
        HashMap<Corner, Rectangle> goal = new HashMap<>();
        goal.put(new Corner(2,4), new Rectangle (new Corner (2,4), new Corner (2,2)));
        goal.put(new Corner(6,0), new Rectangle (new Corner (6,0), new Corner (1,3)));
        Tray tre2 = new Tray(7,7, goal); 
        
        HashMap<Corner, Rectangle> created = new HashMap<>();
        created.put(new Corner(0,0), new Rectangle (new Corner (0,0), new Corner (1,1)));
        created.put(new Corner(3,2), new Rectangle (new Corner (3,2), new Corner (1,3)));
        created.put(new Corner(4,4), new Rectangle (new Corner (4,4), new Corner (2,2)));
        Tray tre1 = new Tray(7,7, created);
        
        HashMap<Corner, Rectangle> creat = new HashMap<>();
        creat.put(new Corner(0,0), new Rectangle (new Corner (0,0), new Corner (1,1)));
        creat.put(new Corner(3,2), new Rectangle (new Corner (3,2), new Corner (1,3)));
        creat.put(new Corner(4,4), new Rectangle (new Corner (4,4), new Corner (2,2)));
        Tray tre3 = new Tray(7,7, creat);
        
        System.out.println(tre1.equals(tre3) + "check equals"); 
        Checker chr1 = new Checker(tre1); 
        chr1.goalBoard = tre2;
        int [] moves = new int[4];
        moves[0] = 3; 
        moves[1] = 2; 
        moves[2] = 3;
        moves[3] = 0;
        chr1.makeMove(moves);
        //System.out.println(Arrays.deepToString(chr1.currBoard.getBoard()));
        System.out.println("here"); 
       // chr1.goalAchieved();// should return false
        moves[0] = 3;
        moves[1] = 0;
        moves[2] = 6;
        moves[3] = 0; 
        chr1.makeMove(moves); 
       // System.out.println(Arrays.deepToString(chr1.currBoard.getBoard()));
        System.out.println("here2"); 
        //chr1.goalAchieved(); //should return false
        moves[0] = 4;
        moves[1] = 4;
        moves[2] = 2;
        moves[3] = 4;
        chr1.makeMove(moves);
       // System.out.println(Arrays.deepToString(chr1.currBoard.getBoard()));
        System.out.println(Arrays.deepToString(chr1.goalBoard.getBoard()) +"goal");
        System.out.println(tre1.equals(chr1.goalBoard.getBoard())); 
        chr1.goalAchieved(); //should return true 
    }
}
