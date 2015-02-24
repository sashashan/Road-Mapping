import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;


public class Reader 
{
	//static DrawGraph map;
	static int numberOfIntersections;
	static int numberOfRoads;
	static SeparateChaningHashTable intersectionsHash;
	static SeparateChainingHashTable2 roadsHash;
	static Intersection [] arrayOfIntersections;
	static List <Road> listOfRoads2; // for part 2
	static List <Road> listOfRoads3; // for part 3
	static int index;
	
	static Graph graphy;
	
	public static void main (String [] args) throws IOException
	{
		// PART 1
		
		DrawMap("monroe-county.txt"); // Includes Part 1 and Part 2
		
		JFrame frame = new JFrame ("Monroe County");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(700, 700);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// PART 3
		// Given two intersections, the program should find the shortest distance between them. The result will be drawn
		// in the green color!
		FindShortestDistance("infile.txt");
		
		// DRAWING THE GRAPH
		System.out.println("Loading graph...");
		DrawGraph map = new DrawGraph(roadsHash, listOfRoads2, listOfRoads3);
		frame.add(map);
		System.out.println("Done");
	}

	// (PART 1) HELPER METHOD
	public static void DrawMap (String filename) throws IOException
	{	
		BufferedReader inputStream = null;
		
		try 
		{
	        inputStream = new BufferedReader(new FileReader(filename));
	        
	        // Counting the number of intersections and roads which we will need for initiating the hash tables
	        countFile (filename);
	        
	        System.out.println("Testing graph in file: " + filename + " ... ");
	        String currentLine;
	        currentLine = inputStream.readLine();
	        
	        intersectionsHash = new SeparateChaningHashTable (nextPrime(numberOfIntersections));
	        arrayOfIntersections = new Intersection [numberOfIntersections];
	        roadsHash = new SeparateChainingHashTable2 (nextPrime(numberOfRoads));
	        
	        Intersection i1 = new Intersection ("", 0, 0, 0);
	        Intersection i2 = new Intersection ("", 0, 0, 0);
	        index = 0;
	        
	        while (currentLine != null && isInterection(currentLine))
	        {
	        	String [] parts = currentLine.split("\t");
	        	// parts [0] = i/r, parts[1] = id, parts [2] = x-coordinate, parts [3] = y-coordinate
	        	
	        	Intersection i = new Intersection (parts[1], Double.parseDouble(parts [2]), Double.parseDouble(parts[3]), index);
	        	intersectionsHash.insert(i);
	        	
	        	// Filling in arrayOfIntersections based on the index. So, in the order that it was read. This will
	       		// be later used in Parts 2 and 3
	       		arrayOfIntersections[index] = i;
	       		
	       		index++;
	       		
	        	currentLine = inputStream.readLine();	        	        	
	        }
	                 
	        currentLine = inputStream.readLine();
    
	        // the while statements are split so that the id of the roads can be initialized from 0
	        // and so the a graph with the number of intersections can be made
	        graphy = new Graph (numberOfIntersections);
	        index = 0;
	        
	        // only roads will pass in this while loop
	        while (currentLine != null)
	        {
	        	String [] parts = currentLine.split("\t");
	        	// parts [0] = i/r, parts[1] = id, parts [2] = id of i1, parts [3] = id of i2
	        	
	        	i1 = intersectionsHash.findIntersection(parts[2]);
	        	i2 = intersectionsHash.findIntersection(parts[3]);
	        	        	
	        	Road r = new Road (parts[1], i1, i2, index);
	        	roadsHash.insert(r);
	        	graphy.insert(r);
	        	
	        	index++;
	        	currentLine = inputStream.readLine();
	        }	
	        
	        // Part 2 - Building the Spanning tree
	        graphy.prim();
	        //graphy.printeachV ();
	        StringBuilder sb = new StringBuilder ();
	        sb = graphy.printSpaningTree();
	        String [] parts2 = sb.toString().split(" ");
	        //System.out.println(sb);
	        
	        listOfRoads2 = new LinkedList <Road> ();
	        int currentIndexOfIntersection;
	        int nextIndexOfIntersection;
	        Intersection ia;
	        Intersection ib;
	        
	        for (int i = 0; i < parts2.length; i++)
	        {
	        	currentIndexOfIntersection = Integer.parseInt(parts2[i]);
	        	ia = arrayOfIntersections[currentIndexOfIntersection];
	        	nextIndexOfIntersection = Integer.parseInt(parts2[++i]);
	        	ib = arrayOfIntersections[nextIndexOfIntersection];
	        	listOfRoads2.add(roadsHash.findRoad(ia, ib));
	        	//System.out.println("Road 1 at " + currentIndexOfIntersection + " " + i1 + " and " + "Road 2 at " + nextIndexOfIntersection + " " + i2);
	        	
	        	//i++; // we are moving through the for loop in pairs       	
	        }
	        
	        //System.out.println(listOfRoads2.toString());
	           
		} finally 
		{
	        if (inputStream != null) 
	        {
	            inputStream.close();
	        }
	        
	    } 	        
	}
	
	
	// HELPER METHODS FOR HASH TABLE
	public static void countFile (String filename) throws IOException
	{	
		BufferedReader inputStream = null;
		
		try 
		{
	        inputStream = new BufferedReader(new FileReader(filename));
	        
	        String currentLine;
	        currentLine = inputStream.readLine();
	        
	        while (currentLine != null)
	        {
	        	String [] parts = currentLine.split("\t");
	        	if (parts[0].equals("i"))
	        		numberOfIntersections++;
	        	if (parts[0].equals("r"))
	        		numberOfRoads++;
	        	currentLine = inputStream.readLine();
	        }
	        
		} finally 
		{
	        if (inputStream != null) 
	        {
	            inputStream.close();
	        }
	        
	    } 	        
	}
	
	public static int nextPrime(int number)
	{
		int np = number; 
		
		while (!isPrime (np++));
			//np = number++;
		
		return np;
	}
	
	public static boolean isPrime ( int num )
	{
		boolean prime = true;
		int limit = (int) Math.sqrt ( num );  

		for ( int i = 2; i <= limit; i++ )
		{
			if ( num % i == 0 )
			{
				prime = false;
				break;
			}
		}

		return prime;
	}
	
	// (PART 3) HELPER METHOD
	// This method reads in infile.txt containing two intersections. It uses the Graph.class
	// and dijkstra method to calculate the shortest paths to all the other intersections. 
	// Next, a second method is used to directle output the roads connecting the two intersections. 
	public static void FindShortestDistance (String filename) throws IOException
	{	
		BufferedReader inputStream = null;
		
		try 
		{
	        inputStream = new BufferedReader(new FileReader(filename));
	        // outputStream = new PrintWriter(new FileWriter("outfile.txt"));
	        
	        String currentLine1;
	        String currentLine2; 
	        currentLine1 = inputStream.readLine(); // This is intersections number 1 (starting intersection)
	        
	        String [] parts = currentLine1.split("\t"); // This will split the intersection into different components
	        // parts [0] = i/r, parts[1] = id, parts [2] = x-coordinate, parts [3] = y-coordinate
      
	        Intersection sI = intersectionsHash.findIntersection(parts[1]);
	        //System.out.println("starting index" + sI.index);
	        Vertex sV = new Vertex();
	        sV.index = sI.index;
	        //graphy.print();
	        graphy.dijkstra(sV);
	        
	        currentLine2 = inputStream.readLine(); // This is intersections number 2 (final intersection)
	        
	        String [] parts2 = currentLine2.split("\t"); // This will split the intersection into different components       
	        // parts [0] = i/r, parts[1] = id, parts [2] = x-coordinate, parts [3] = y-coordinate
	        // Now we need to find the intersection in our Intersection linked list so we can identify its index
	        
	        Intersection sE = intersectionsHash.findIntersection(parts2[1]);
	        Vertex eV = new Vertex();
	        eV.index = sE.index;
	        //System.out.println("ending vertex" + sE.index);
	        StringBuilder sb = graphy.printPath(eV);
	        //System.out.println(sb);
	        //System.out.println();
	        
	        //graphy.printeachV (); // For testing
	        
	        // Printing the connecting roads
	        //System.out.println("The roads connecting " + currentLine1 + " and " + currentLine2 + " are:");
	        String [] parts3 = sb.toString().split(" ");
	        listOfRoads3 = new LinkedList <Road> ();
	        int currentIndexOfIntersection;
	        int nextIndexOfIntersection;
	        Intersection i1;
	        Intersection i2;
	        
	        for (int i = 0; i < parts3.length; i++)
	        {
	        	currentIndexOfIntersection = Integer.parseInt(parts3[i]);
	        	i1 = arrayOfIntersections[currentIndexOfIntersection];
	        	nextIndexOfIntersection = Integer.parseInt(parts3[++i]);
	        	i2 = arrayOfIntersections[nextIndexOfIntersection];
	        	listOfRoads3.add(roadsHash.findRoad(i1, i2));
	        	//System.out.println("Road 1 at " + currentIndexOfIntersection + " " + i1 + " and " + "Road 2 at " + nextIndexOfIntersection + " " + i2);
	        	
	        	//i++; // we are moving through the for loop in pairs       	
	        }
	        
	        //This will print the road path
	        String [] array = new String [listOfRoads3.size()];
	        StringComparator strcomp = new StringComparator();
	        
	        for (int i = 0; i < listOfRoads3.size(); i++)
	        {
	        	array[i] = (listOfRoads3.get(i)).id1;
	        }
	        
	        Arrays.sort(array, strcomp);
//	        for (String id: array)
//	        {
//	           System.out.println(id);
//	        }
	        //System.out.println(listOfRoads3.toString());
	        
	        //System.out.print
	        
	        
	        
		} finally 
		{
	        if (inputStream != null) 
	        {
	            inputStream.close();
	        }
	    }
		
		System.out.println("Part 3: done");
	}
	
	// method checking that the inserted currentLine is an intersection 
	private static boolean isInterection(String line)
	{
		String [] parts = line.split("\t");
		
		return parts[0].equals("i");
	}
	
//	private static int maxX()
//	{
//		double max = 0;
//		
//		for (Intersection i : intersections)
//		{
//			if (i.x > max) max = i.x;
//		}
//		return (int)Math.ceil(max);
//	}
//	
//	private static int maxY()
//	{
//		double max = 0;
//		
//		for (Intersection i : intersections)
//		{
//			if (i.y > max) max = i.y;
//		}
//		return (int)Math.ceil(max);
//	}
	
	
}

	class StringComparator implements Comparator<String>
{
		public int compare(String a, String b)
		{
			return a.compareTo(b);
		}
}
    
    
    