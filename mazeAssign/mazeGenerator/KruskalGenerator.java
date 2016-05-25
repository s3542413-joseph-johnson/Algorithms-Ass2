package mazeGenerator;


import java.util.ArrayList;
import java.util.Random;

import maze.Cell;
import maze.Maze;

public class KruskalGenerator implements MazeGenerator {

	/*
	 * List of edges to be used to generate paths.
	 * 
	 * List of sets will be used to join cells together when a path exists
	 */
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<ArrayList<Tree>> sets = new ArrayList<ArrayList<Tree>>();
	
	@Override
	public void generateMaze(Maze maze) {
		/*
		 * Each if statement checks what type of maze is being generated and
		 * calls the appropriate method
		 */
		if (maze.type == Maze.NORMAL)
		{
			normalMaze(maze);
		}
		else if (maze.type == Maze.HEX)
		{
			hexMaze(maze);
		}
		else if (maze.type == Maze.TUNNEL)
		{
			tunnelMaze(maze);
		}

	} // end of generateMaze()

	private void normalMaze(Maze maze) {
		/*
		 * First add an edge for every pair of adjacent cells to a list.
		 * 
		 * Iterate through the whole maze, getting all adjacent pairs and add the 
		 * pair to the list.
		 * Each edge is represented as an adjacent pair of cells.
		 * 
		 * South and east edges are not checked because it would cause some
		 * edges to be added twice
		 */
		Edge pair = null;
		for(int i = 0; i < maze.sizeR; i++)
		{
			ArrayList<Tree> tempSet = new ArrayList<Tree>();
			for(int j = 0; j < maze.sizeC; j++)
			{
				Cell currentCell = maze.map[i][j];
				tempSet.add(new Tree());
				
				// Checking neighbour on north side of cell
				if (currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0
						&& currentCell.r < maze.sizeR - 1)
				{
					pair = new Edge(currentCell, currentCell.neigh[Maze.NORTH]);
					if(edges.contains(pair)==false)
					{
						edges.add(pair);						
					}
				}

				// Checking neighbour on west side of cell
				if (currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 
						&& currentCell.r <= maze.sizeR)
				{
					pair = new Edge(currentCell, currentCell.neigh[Maze.WEST]);
					if(edges.contains(pair)==false)
					{
						edges.add(pair);						
					}
				}
			}
			sets.add(tempSet);
		}
		/*
		 * Main body of algorithm
		 * 
		 * Continue to choose random edges until there are none left.
		 */
		while(!edges.isEmpty())
		{
			// Randomly select an edge
			Random rand = new Random();
			Edge edge = edges.get(rand.nextInt(edges.size()));
			Cell cellOne = edge.cellOne;
			Cell cellTwo = edge.cellTwo;
			
			// Get the corresponding sets for the two cells
			Tree set1 = (sets.get(cellOne.r)).get(cellOne.c);
			Tree set2 = (sets.get(cellTwo.r)).get(cellTwo.c);
			
			// If the sets are not connected, carve a path and connect them
			if(!set1.connected(set2))
			{
				
				if(cellOne.neigh[Maze.NORTH]==cellTwo)
				{
					cellOne.wall[Maze.NORTH].present = false;
					
				}
				else if(cellOne.neigh[Maze.EAST]==cellTwo)
				{
					cellOne.wall[Maze.EAST].present = false;
					
				}
				else if(cellOne.neigh[Maze.SOUTH]==cellTwo)
				{
					cellOne.wall[Maze.SOUTH].present = false;
					
				}
				else if(cellOne.neigh[Maze.WEST]==cellTwo)
				{
					cellOne.wall[Maze.WEST].present = false;
					
				}
				set1.connect(set2);
			}
			/*
			 * Always remove any edges that are checked, if they are rejected they
			 * should be removed from the list and not considered again.
			 */
			edges.remove(edge);
		}
		
	}// End of normal generator

	private void hexMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}// End of hex generator

	private void tunnelMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}// End of tunnel generator
	
	/*
	 * Tree data structure used to compare sets of cells
	 */
	class Tree {
		
		private Tree parent = null;
		
		public Tree() {
			
		}
		
		/*
		 * Traverse to root of tree to see if there is a parent.
		 * Return parent if exists, otherwise return this object instance.
		 */
		public Tree root() {
			return parent != null ? parent.root() : this;
		}
		
		/*
		 * Are we connected to this tree?
		 */
		public boolean connected(Tree tree) {
			return this.root() == tree.root();
		}
		
		//
		// Connect to the tree
		//
		public void connect(Tree tree) {
			tree.root().setParent(this);
		}
		
		//
		// Set the parent of the object instance
		public void setParent(Tree parent) {
			this.parent = parent;
		}
	}// End of Tree inner class
	
	/*
	 * Edge data structure to represent an edge between two cells.
	 * 
	 * Each edge contains the two cells that are adjacent to each other.
	 */
	class Edge{
		
		Cell cellOne;
		Cell cellTwo;
		
		Edge(Cell one, Cell two)
		{
			cellOne = one;
			cellTwo = two;
		}
	}// End of Edge inner class
	
} // end of class KruskalGenerator


