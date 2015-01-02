// Road class is equivalent to Vertices class

public class Road 
{
	String id1; 
	double x1, y1, x2, y2, weight;
	Intersection i1, i2;
	int index;
	
	Road (String id2, Intersection i1, Intersection i2, int index)
	{
		this.id1 = id2;
		this.i1 = i1;
		this.i2 = i2;
		this.x1 = i1.x;
		this.y1 = i1.y;
		this.x2 = i2.x;
		this.y2 = i2.y;
		this.index = index;
		this.weight = calculateWeight();
	}
	
	//calculating the weight of the road with is the distance from one intersection to another
	//pythagoras theorem was used
	private double calculateWeight()
	{
		double answer = Math.sqrt( Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2) ); 
		return answer;
	}
}
