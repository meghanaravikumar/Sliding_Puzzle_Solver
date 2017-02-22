CS 61bl Project 3 - Sliding Block Puzzle

*A work in progress*

Ebony Milbury (cs61bl-jy), Meghana Ravikumar (cs61bl-jr), and Tohanh Bui (cs61bl-fq)

Division of Labor   ------------------------------------------------------------------------ page 2
Design Decisions    -----------------------------------------------------------------------  pages 3-5
Experimental Results --------------------------------------------------------------------- pages 6-8
	Experiment One: HashCodes ------------------------------------------ page 6
	Experiment Two: Many-Square Moves vs One-Square Moves --- page 7
	Experiment Three: BFS vs DFS --------------------------------------- page8
Program Development ------------------------------------------------------------------- page 9
Improvements ----------------------------------------------------------------------------- page 10




































Division of Labor:
We have all been relatively evenly involved in the division of labor - In Checker, Tohanh's focus was originally on understanding and implementing the scanner and input streams, Meghana's on testing and pseudocode, and Ebony's on implementing the various storage data structures. Once Solver began, Ebony was in charge of potential moves, Tohanh in charge of the BFS implementation, and Meghana in charge of copyNode, though each part had its bugs that were handled collaboratively. In terms of actual, physical coding there hasn’t been a significant distinction in which parts each person codes, as we very heavily utilized partner coding in this project. Due to having fewer other obligations, Ebony worked more heavily on the readme than the other partners. Generally work was divided based on preference, with lighter loads going to group members with the most other obligations (other class finals, work, etcetera).




































Design Decisions:
Our Tray is represented by an immutable object comprised of a hashMap of Corners and
Rectangles (to be explained later, fig.1) and a boolean [][](fig.1, 2), wherein "true" represents a full square, and "false" represents a valid movment spot. This dual representation is for use in a hashSet of already visited trays, because when solving, due to the fact that rectangles are only meaningfully distinct by size and location, not actually entities in and of themselves, repeating exactly an already seen configuration would be wasteful and unproductive. We use the boolean double array to determine legal moves, and the hashMap of Corners to Rectangles (the "corner" keys are the corner object representing the top left corner of the rectangle value, for ease of reference when moving said rectangle in our "copy board" method) to check if a given rectangle even can be moved, as well as to check if a board is .equals to another board (fig 1). (Hashcode and equals methods were overwritten in all our classes) CopyBoard is the most important part of our Tray class - it creates a new tray object with the move applied to it, and returns that new Tray (fig. 2). Rectangle is an immutable object defined by its three corners - topLeft, bottomRight, and dimensions. Rectangles can be constructed from an array of digits, where 0 and 1 are topLeft and 2 and 3 are bottomRight, or from two corners (used in making moves), topLeft and dimensions. This, again, is to facilitate making moves. Corner is essentially an immutable Point. It has no methods other than a constructor and an overridden hashCode & equals. Our decision to make things immutable stems from the desire to use hashMaps as much as possible for time efficiency, and the fact that it's safer to hash immutable objects when you don't want keys and values to be messed around with. For checking the final board against the goal, we have an instance variable "currboard" because we can't say "does the "Seen Boards" contain the goal?" - Firstly, Checker should not exit until the end of the input, regardless of if the goal was achieved. Secondly, the goal may not have as many pieces as the board that represents a successful solution, simply because it may only care about one piece's position. Therefore, we created a method called goalAchieved, and that checks the pieces in the goal against the pieces in the currboard, and if it is good, system.exit(0). Otherwise, system.exit(1).

For Solver, we created a representation called a “TrayNode" (fig. 3)  which contains a Tray that represents that node's board state, a list of all moves made to get to that state, and a list of potential moves, to be used to create more TrayNodes to put in the search. For the solving, there was also the aforementioned HashSet of Trays (fig. 4) to ensure no configuration is visited and searched twice, as well as a queue for a BFS traversal. 

We elected to allow our Solver to move pieces only one square at a time to keep the number of neighbors under control - while our checker accounts for multi-square movement, for a single piece in a board, one-square movements versus many-square movements makes the difference between the following number of “neighbors”: (fig. 5)

(l = length of board, w = width of board)
 one-square:  				multi-square: 
corner - one possible move 		corner - l+w-1 
edge - two possible moves 		edge - 2l + w - 1 OR 2w + l - 1 
central - three possible		moves central - 2w + 2l - 1



While this will obviously be incremented by the number and location of other pieces on the
board, the number of potential neighbors from moving one step at a time will eventually get to the same place, and as we were planning on using some form of BFS - this seemed optimal because it would allow us to theoretically encounter many collisions between blocks faster.
Our TrayNodes are effectively building a tree as they’re traversing - while there are still TrayNodes in the queue (which is initialized with just a TrayNode containing the tray to-be-solved, and no moves made or posible moves), the first element is polled and checked against the goal. Then, if it‘s not the goal, “possibleMoves()” is called on it. possibleMoves basically just gets the 2-4 squares surrounding the given piece, instantly eliminating all out-of-board moves, and bundles them into a possibleMoveList, implemented as ArrayList “moves” in TrayNode. Then, copyNode is called with those moves as an argument, which results in either a new TrayNode containing the board used to make this move and the move used to make it (appended to earlier “made moves”) or null. If it’s null - which will occur if the move results in a collision, or if the configuration created by the board is already in the hashSet - it is skipped over. Otherwise, it is added to the queue and the loop repeats. If the queue is ever emptied, and no solution has been found, it exits with code 1.


fig.1) An example of the “Tray” object created from the board input

 



























Experiments: 

Number One: HashCodes

Background: We overwrote HashCode in Corner, Rectangle, and Tray because we wanted trays to be .equals if they had rectangles at all the same positions. Rectangles are only meaningfully distinct from other Rectangles by two factors - topLeft and bottomRight (or topLeft and dimensions) - so we wanted that to be reflected in our hashCode. The greatest priority was minimal collisions, since the Hashcodes were all intimately related (tray used rectangle which used corner) and since, for illegal boards especially, speed of checking containment was just as important as speed of hashing, and we wanted to avoid a costly resizing of the HashSet.

 - HashCode Formula:  primeNumber * (givenValue + (primeNumber * (givenValue....)))
 - Alt HashCode Formula: primeNumber * givenValue + primeNumber * givenValue....

Summary: To compare timing on each of these things, we repeatedly ran something that created a large array, and then hashed a large number of corners, then Rectangles, then trays, and put them in the appropriate bucket, counting til we ran into a collision, with the hashCode functions we implemented, and compared that to a quicker, but less safe method used earlier in the project.



























Number Two: Many-square moves vs One-square moves

Background: Our implementation of Solver only checks moves one square at a time, forbidding repeat configurations. This was for simplification purposes.

Summary: To test this, we ran the timing comparatively on various “hard” tests for multi-square moves versus one-square moves. 










































Number Three: BFS vs DFS

Summary: To test this, we ran the timing comparatively on all hard tests with solver variantly using a queue and a stack (BFS and DFS)













































Program Development:
Our first priority was clearly setting up the board, so we prioritized the data structures “Rectangle” and “Tray,” which we used to represent the abstract board passed in. Subsequently, we updated “Tray” to also include the boolean[][] board - we had put this off because we didn’t particularly like the data structure, and determining which way in a double array is x and which is y seemed all-important and also a little imposing. The idea of updating said board seemed slow and unnecessary, but we ultimately did settle on this structure as ideal for pointing out edge collisions for rectangles, given the rectangles’ representation as just 3 corners - topLeft, bottomRight, and dimensions. After we implemented these data structures, we created the methods to actually enact moves on them, starting again with the move legality check and putting the “new” rectangle into the hashMap of a copied Tray, then moving on to dealing with changing the boolean values on the board. The last things we did in Checker were the things involving scanners, because none of us have significant experience with these, so we prioritized board construction and move-making, figuring that worst came to worst we could request assistance in lab. Luckily, that wasn’t a major issue, but it did take some doing.
We also have to admit that we put off solver as much as possible, because it seemed like a daunting task. The complexity of any such board game is, by the nature of the thing, exponential, and we actually discussed several “optimizations” first, in spite of the silliness of discussing optimizations for something that doesn’t yet even exist, and different data structures before finally settling on our TrayNode representation. Thereafter, we actually reversed the order given in Checker - since Solver’s main method is quite similar to Checker’s, we could simply copy over most of the method. The only complication was the minor matter of setting up the queue for the BFS traversal. In solver, we did prioritize the structures representing everything, since it doesn’t do much good to cause movements on something nonexistant. Then, we created almost-duplicate methods in Tray so that if we made an illegal move in solver, it wouldn’t System.exit. Finally, we made the method to select all legal (not-out-of-bounds - we weren’t checking for collisions yet) moves on a given board (2-4 per rectangle). After that, it was just a matter of running it and making tweaks until solver returned correctly for each board, and optimizations for speed.
















Improvements:











