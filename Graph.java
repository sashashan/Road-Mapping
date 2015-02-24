//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


// HELLOOO
public class Graph 
{
	private int Cvnt, Ecnt; // number of vertices (intersections) and edges (roads)
	private LinkedList <Node> [] adjList;
	Vertex box [];
	double INFINITY = Integer.MAX_VALUE; 
	LinkedList <Node> list;
	StringBuilder sb2 = new StringBuilder ();
	StringBuilder sb3 = new StringBuilder ();
	
	public Graph (int numVerticies)
	{ 
		Cvnt = numVerticies;
		adjList = (LinkedList <Node> []) new LinkedList<?> [Cvnt];
		
		for (int i = 0; i < Cvnt; i++)
		{
			list = new LinkedList <Node> ();
			adjList[i] = list; // filling in each index with a new list
		}
	}
	
	// assuming no repeated roads
	public void insert (Road r) 
	{
		Ecnt++;
		Node node = new Node();
		node.index = (r.i2).index;
		node.weight = r.weight;
		(adjList[(r.i1).index]).add (node);
		
		// because it's an undirected graph we need to insert one more time
		node.index = (r.i1).index;
		(adjList[(r.i2).index]).add (node);
	}
	
	public void print ()
	{
		for (int i = 0; i < Cvnt; i++)
		{
			for (int j = 0; j < adjList[i].size(); j++)
			{
			System.out.print("at " + i + ": " + (adjList[i].get(j)).index + ", " + (adjList[i].get(j)).weight + ", ");
			}
			System.out.println();
		}
		
	}
	
	// Part 3 - Shortest Path
	public void dijkstra (Vertex s)
	{
		box = new Vertex [Cvnt];
		
		for (int i = 0; i < Cvnt; i++) // for each Vertex
		{
			Vertex u = new Vertex ();
			u.index = i;
			u.dist = INFINITY;
			u.known = false;
			box [i] = u;		
		}
		
		box[s.index].dist = 0; 
		
		while ( moreUnknownRemains() == true) // while there is an unknown distance vertex
		{
			Vertex v = findVertexWithShortestDistance ();

			box[v.index].known = true;
			
			for (int i = 0; i < Cvnt; i++) // for each Vertex 
			{
				if (edge(v.index, i)) // for each vertex w adjacent to v
				{
					if (box[i].known == false) // w is the adjacent vertex that will pass this check
					{
						double cvw = cost(v.index, i); // cost of edge from v to w
						
						if ((v.dist + cvw) < box[i].dist)
						{
							//System.out.println("Was here");
							box[i].dist = v.dist + cvw;
							box[i].path = v;
						}
					}
				}
				
			}
		}
	}
	
	// PART 2 - Minimum Spanning Tree
	public void prim ()
	{
		box = new Vertex [Cvnt];
		
		for (int i = 0; i < Cvnt; i++) // for each Vertex
		{
			Vertex u = new Vertex ();
			u.index = i;
			u.dist = INFINITY;
			u.known = false;
			box [i] = u;		
		}
		
		box[0].dist = 0; 
		
		while ( moreUnknownRemains() == true) // while there is an unknown distance vertex
		{
			Vertex v = findVertexWithShortestDistance ();

			box[v.index].known = true;
			
			for (int i = 0; i < Cvnt; i++) // for each Vertex 
			{
				if (edge(v.index, i)) // for each vertex w adjacent to v
				{
					if (box[i].known == false) // w is the adjacent vertex that will pass this check
					{
						double cvw = cost(v.index, i); // cost of edge from v to w
				
						//System.out.println("Was here");
						box[i].dist = Math.min(v.dist, cvw);
						box[i].path = v;
						//System.out.println("was here");
					}
				}
				
			}
		}
	}
	
	public void printeachV ()
	{
		for (int i = 0; i < Cvnt; i++)
			if (box[i].known == true)
		{
			System.out.print("V" + box[i].index);
			System.out.print(" K: " + box[i].known);
			System.out.print(" D: " + box[i].dist);
			System.out.print(" P: " + box[i].path.index);
			System.out.println();
		}
	}

		// Part 2
	public StringBuilder printSpaningTree ()
	{
		for (int i = 1; i < Cvnt; i++) // Starting with 1 because v0 is a parent of itself according to the code
			if (box[i].path != null)
		{
			sb2.append(box[i].index);
			sb2.append(" ");
			sb2.append(box[i].path.index);
			sb2.append(" ");
		}
		
		return sb2;
	}
	
	// Part 3
	public StringBuilder printPath(Vertex e)
	{
		printPath2(e);
		return sb3;
	}
	
	// finding the shortest path from s (in dijkstra method) to e
	public void printPath2(Vertex e) 
	{
		
		if (box[e.index].path != null)
		{
			printPath(box[e.index].path);
			//System.out.print(" to ");
			sb3.append(" ");
		}
		//System.out.print(box[e.index].index);
		sb3.append(e.index);
	}
	
	
	
	// HELPER METHODS
	
	public boolean moreUnknownRemains()
	{
		for (int i = 0; i < Cvnt; i++) // for each Vertex
		{
			if (box[i].known == false) return true;
		}
		
		return false;
	}
	
	public Vertex findVertexWithShortestDistance () // out of the remaining unknown vertices 
	{
		int min = 0;
		
		for (int i = 0; i < Cvnt; i++)
		{
			if (box[i].known == false) min = i;
		}
		
		for (int i = 0; i < Cvnt; i++) // for each Vertex
		{
			if (box[i].known == false && box[i].dist < box [min].dist)
			{
				min = i;
			}
		}
		
		return box[min];
	}
	
	public double cost (int id1, int id2)
	{
		double cost = 0;
		
		for (int i = 0; i < adjList[id1].size(); i++)
		{
			if ( ((adjList[id1]).get(i)).index == id2 )
			{
				cost = ((adjList[id1]).get(i)).weight;
				//System.out.println(cost);
			}
		}
		
		return cost;
	}
	
	// are they connected?
	public boolean edge (int id1, int id2) 
	{
		for (int i = 0; i < adjList[id1].size(); i++)
		{
			if ( ((adjList[id1]).get(i)).index == id2 )
			{
				//System.out.println("found edge");
				return true;
			}
		}
		
		return false;
	}

}
