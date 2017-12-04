import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;




public class PathComponent extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<List<Double>> towns;
	
	List<List<Integer>> permutations;
	
	List<Integer> input;
	
	int iteration = 0;
	
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(5));
		
		for(int i = 0; i < input.size() - 1; i++)
		{
			/*
			try 
			{
				TimeUnit.NANOSECONDS.sleep(1);
			} catch (InterruptedException e) 
			{}
			//*/
			
			//gets 
			Line2D.Double path = new Line2D.Double(50 + towns.get(input.get(i)).get(0)*100, 50 + 100*towns.get(input.get(i)).get(1), 50 + 100*towns.get(input.get(i+1)).get(0), 50 + 100*towns.get(input.get(i+1)).get(1));
			g2.draw(path);

		}
		
		//draws final line for "full circle"
		Line2D.Double finalPath = new Line2D.Double(50 + towns.get(input.get(input.size() -1)).get(0)*100, 50 + 100*towns.get(input.get(input.size() -1)).get(1), 50 + 100*towns.get(input.get(0)).get(0), 50 + 100*towns.get(input.get(0)).get(1));
		g2.draw(finalPath);
		//*/
		
		
		Graphics2D g1 = (Graphics2D)g;
		for(List<Double> town : towns)
		{
			Ellipse2D.Double Town = new Ellipse2D.Double(50 - 10 + (town.get(0)*100), (50 - 10 + town.get(1)*100), 20, 20);
			g1.fill(Town);
			g1.draw(Town);
		}
		
	}
	
	public void addTown(List<List<Double>> x)
	{
		towns = x;
	}
	
	public void addPermutation(List<List<Integer>> x)
	{
		permutations = x;
	}
	
    public static double pythagor(double x , double y)
    {
        double c = Math.sqrt((x*x)+(y*y));
        return c;
    }
    
    public void setPermutation(List<Integer> i)
    {
    	input = i;
    }

    public void setPermutation(int[] i)
	{
		ArrayList<Integer> temp = new ArrayList<Integer>(i.length);
		for(int j = 0; j < i.length; j++)
		{
			temp.add(i[j]);
		}

		input = temp;
	}
    
    public int getIteration()
    {
    	return iteration;
    }
    
    public void setIteration(int i)
    {
    	iteration = i;
    }
	
    /*
    //experimental getDistance
	public double getDistance()
	{
		double sum = 0;
		for(int i = 0; i < input.size() - 1; i++)
		{
			sum += pythagor(towns.get(input.get(i)).get(0) - towns.get(input.get(i + 1)).get(0), towns.get(input.get(i)).get(1) - towns.get(input.get(i + 1)).get(1));
		}

		return sum;
	}
    //*/
	
    //gets distance with full circle
	public double getDistance()
	{
		double sum = 0;
		for(int i = 0; i < input.size(); i++)
		{
			if( i != input.size() - 1)
			{
				sum += pythagor(towns.get(input.get(i)).get(0) - towns.get(input.get(i + 1)).get(0), towns.get(input.get(i)).get(1) - towns.get(input.get(i + 1)).get(1));
			}
			else
			{
				sum += pythagor(towns.get(input.get(i)).get(0) - towns.get(input.get(0)).get(0), towns.get(input.get(i)).get(1) - towns.get(input.get(0)).get(1));
			}
		}

		return sum;
	}
	//*/
	
	/*
	//gets distance without full circle
	public double getDistance()
	{
		double sum = 0;
		for(int i = 0; i < input.size() - 1; i++)
		{
			sum += pythagor(towns.get(input.get(i)).get(0) - towns.get(input.get(i + 1)).get(0), towns.get(input.get(i)).get(1) - towns.get(input.get(i + 1)).get(1));
		}

		return sum;
	}
	//*/
	
	public List<Integer> getCurrentPermutation()
	{
		return input;
	}
	
	public void addNewPermutation(List<Integer> x)
	{
		input = x;
	}
	

}
	