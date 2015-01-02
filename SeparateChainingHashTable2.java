import java.util.LinkedList;
import java.util.List;


public class SeparateChainingHashTable2 
{
	public static final int DEFAULT_TABLE_SIZE = 160001;
	private List<Road> [] roads;
	int size;
	
	public SeparateChainingHashTable2 ()
	{
		this (DEFAULT_TABLE_SIZE);
	}
	
	// Creating a linked list, and assigning each node in the linked list as another LinkedList
	public SeparateChainingHashTable2 (int size)
	{
		this.size = size;
		
		roads = new LinkedList [size];
		for (int i = 0; i < roads.length; i++)
			roads[i] = new LinkedList<>();
	}
	
	public void insert (Road x)
	{
		List<Road> whichList = roads [myhash(x)];
		if (! whichList.contains(x))
		{
			whichList.add(x);
		}
	}
	
	public boolean contains (Road x)
	{
		List<Road> whichList = roads [myhash(x)];
		return whichList.contains(x);	
	}
	
	private int myhash (Road x)
	{
		long value = APHash(x.id1);
		
		value %= roads.length;
		if (value < 0)
			value += roads.length;
		
		int i = (int) value;
		
		return i;
	}
	
	// borrowed from http://www.partow.net/programming/hashfunctions/
	public long APHash(String str)
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
	
	public List toLinkedList ()
	{
		List <Road> rds = new LinkedList <Road> ();
		
		for (int i = 0; i < size; i++) // traversing through each slot
		{
			List<Road> whichList = roads [i];
			for (int j = 0; j < whichList.size(); j++)
			{
				rds.add(whichList.get(j));
			}
		}
		
		return rds;
	}
	
	public Road findRoad (Intersection ia, Intersection ib)
	{
		for (int i = 0; i < size; i++) // traversing through each slot
		{
			List<Road> whichList = roads [i];
			for (int j = 0; j < whichList.size(); j++)
			{
				if ( (whichList.get(j).i1.id).equals(ia.id) && (whichList.get(j).i2.id).equals(ib.id))
					return whichList.get(j);
				else if ( (whichList.get(j).i2.id).equals(ia.id) && (whichList.get(j).i1.id).equals(ib.id))
					return whichList.get(j); 
			}
		}
		return null;
	}

}
