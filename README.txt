// README

********** Instructions to run: ********
Copy the files on your computer.

In terminal:
cd to the source
javac -Xlint:unchacked Reader.java
java Reader

Note: when the program is finished running, a message will be displayed in the output. Part 2 takes the longest time, but it still should not exceed 1 minute.
Also, when the pop up window appears wait 3 seconds and you will need to drag the corners to resize it to see the content.

The road map was drawn using GUI. It's a simple class (DrawGraph.java) that takes in a hash table list (with original roads), and two 
lists of roads, one list containing the minimum spanning tree roads and the other containing the roads connecting two given intersections.
Black color represents the original map.
Blue color - the minimum spanning tree.
Red color - the shortest path from two intersections. 

A hash table is used to store the intersections and roads. SeparateChainingHashTable2 contains the roads while SeparateChaningHashTable
contains the intersections. General code was borrowed from Weiss's book Data Structures and Algorithms in Java. APHash function was used. 

For both Part 2 and Part 3 (found in Graph.Java) an adjecency list was created for the given roads. As the Buffer Reader reads the file, 
it will find the corresponding intersections using the hash table and build the list. 

****Part 2 ****
Prim's algorithm was used to calculate minimum spanning tree with the use of a matrix containing vertices. 

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
	
	Running time expected: worst case big-Oh of V.
	
****Part 3 ****
dijkstra method was used to calculate shortest path between two vertices with the use of a matrix containing vertices. 

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
	
	The final result (the connecting roads in ascending order) is printed in the Console. 
	Expected big oh is lon|V + E|.
