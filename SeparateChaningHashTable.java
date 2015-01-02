import java.util.LinkedList;
import java.util.List;


public class SeparateChaningHashTable 
{
	public static final int DEFAULT_TABLE_SIZE = 160001;
	private List<Intersection> [] intersections;
	int size;
	
	public SeparateChaningHashTable ()
	{
		this (DEFAULT_TABLE_SIZE);
	}
	
	// Creating a linked list, and assigning each node in the linked list as another LinkedList
	public SeparateChaningHashTable (int size)
	{
		this.size = size;
		
		intersections = new LinkedList [size];
		for (int i = 0; i < intersections.length; i++)
			intersections[i] = new LinkedList<>();
	}
	
	public void insert (Intersection x)
	{
		List<Intersection> whichList = intersections [myhash(x)];
		if (! whichList.contains(x))
		{
			whichList.add(x);
		}
	}
	
	public boolean contains (Intersection x)
	{
		List<Intersection> whichList = intersections [myhash(x)];
		return whichList.contains(x);	
	}
	
	private int myhash (Intersection x)
	{
		long value = APHash(x.id);
		
		value %= intersections.length;
		if (value < 0)
			value += intersections.length;
		
		int i = (int) value;
		
		return i;
	}
	
	// overloading myhash for the findIntersection method
	private int myhash (String x)
	{
		long value = APHash(x);
		
		value %= intersections.length;
		if (value < 0)
			value += intersections.length;
		
		int i = (int) value;
		
		return i;
	}
	
	// borrowed from http://www.partow.net/programming/hashfunctions/
	private long APHash(String str)
	{
		long hash = 0xAAAAAAAA;

		for(int i = 0; i < str.length(); i++)
		{
			if ((i & 1) == 0)
			{
				hash ^= ((hash << 7) ^ str.charAt(i) * (hash >> 3));
			}
			else
			{
				hash ^= (~((hash << 11) + str.charAt(i) ^ (hash >> 5)));
			}
		}

		return hash;
	}
	
	public Intersection findIntersection(String idOfIntersction)
	{
		List<Intersection> whichList = intersections [myhash(idOfIntersction)];
		
		if (! whichList.contains(idOfIntersction))
		{
			for (int i = 0; i < whichList.size(); i++)
				if ((whichList.get(i).id).equals(idOfIntersction))
					return whichList.get(i);
		}
		
		// Hopefully it won't come to this
		return null;
	}
	
//	public List toLinkedList()
//	{
//		List <Road> ints = new LinkedList <Road> ();
//		
//		for (int i = 0; i < size; i++)
//		{
//			
//		}
//	}

}
