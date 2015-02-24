import java.awt.*;

import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

import javax.swing.*;

public class DrawGraph extends JComponent 
{
	// Part 1
	List <Road> road1 = new LinkedList <Road> ();
	
	// Part 2
	List <Road> road2 = new LinkedList <Road> ();
		
	// Part 3
	List <Road> road3 = new LinkedList <Road> ();
	
	public DrawGraph(SeparateChainingHashTable2 rds, List <Road> rds2, List <Road> rds3) // the frame constructor method
	{
		road1 = rds.toLinkedList();
		
		for (Road r : rds2)
		{
			road2.add(r);
		}
		
		for (Road r : rds3)
		{
			road3.add(r);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (Road rr : road1)
		{
			Shape shapes = new Line2D.Double(rr.x1, rr.y1, rr.x2, rr.y2); // coordinates (x1, y1, x2, y2)
			g2.draw(shapes);
		}
		
		g.setColor(Color.BLUE);
		
		for (Road rr : road2)
		{
			Shape shapes = new Line2D.Double(rr.x1, rr.y1, rr.x2, rr.y2); // coordinates (x1, y1, x2, y2)
			g2.draw(shapes);
		}
		
		g.setColor(Color.RED);
		
		for (Road rr : road3)
		{
			Shape shapes = new Line2D.Double(rr.x1, rr.y1, rr.x2, rr.y2); // coordinates (x1, y1, x2, y2)
			g2.draw(shapes);
		}
		
		
	}
	

}
