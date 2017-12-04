import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pathfinder 
{
	
	//private static int numTowns = 10;
	//private static int townSize = 10;

	//private static double avgDistance;

	private static double timeSeconds = 30;
	private static long scheduleTime = (long)(1000 * timeSeconds);
	
	//private double measure;
	
	//private int temperature;
	private List<List<Double>> map;
	private double[][] distanceTable;
	//private int cycle;
	
	private int[] currentPath;
	private int[] potentPath;
	
	private long startTime;
	
	private double currentDistance;
	private double potentDistance;

	private JFrame frame;
	private PathComponent pathComp;
	
	public Pathfinder(int towns, int xSize, int ySize, JFrame theFrame, PathComponent path)
	{
		frame = theFrame;
		pathComp = path;

		//cycle = 0;
		
		startTime = System.currentTimeMillis();
		
		map = makeMap(towns, xSize, ySize);
		
		distanceTable = makeDistanceTable(map);
				
		currentPath = new int[distanceTable.length];
		
		potentPath = new int[distanceTable.length];
		
		//a measure for how far an "average" path is
		//measure = townSize * 0.52 * (numTowns - 1);
		
		for(int i = 0; i < distanceTable.length; i++)
		{
			currentPath[i] = i;
		}

		System.out.println(Arrays.toString(currentPath));
		
		//Calculate the initial distance
		currentDistance = 0;
		for(int i = 0; i < currentPath.length; i++)
		{
			currentDistance += distanceTable[currentPath[i]][currentPath[(i+1)%currentPath.length]];
		}

		
		//System.out.println(Arrays.toString(currentPath));
		
	}

	public void run()
	{
		startTime = System.currentTimeMillis();

		while(System.currentTimeMillis() - startTime < scheduleTime)
		{
			/*
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//*/

			iterate();
			pathComp.setPermutation(currentPath);
			frame.repaint();
		}
		finish();
	}
	
	public int[] getPath()
	{
		return currentPath;
	}
	
	public List<List<Double>> getMap()
	{
		return map;
	}
	
	public void iterate()
	{
		//start can be any int, except last in currentPath
		//System.out.println(tempFunction2());
		int start = (int)(Math.random() * (currentPath.length - 1));
		
		//end is after the start
		int end = (int)(Math.random() * (currentPath.length - start - 1)) + start + 1;
		
		/*
		System.out.println("Start is: " + start);
		System.out.println("End is: " + end);
		
		System.out.println("Current path is: " + Arrays.toString(currentPath));
		//*/
		
		//copy it until "start"
		for(int i = 0; i < start; i++)
		{
			potentPath[i] = currentPath[i];
			
			//System.out.println("started: " + currentPath[i] + " at " + i);

		}
		
		//System.out.println("Current path after first run is: " + Arrays.toString(currentPath));

		
		//then flip it
		for(int i = start; i <= end; i++)
		{
			potentPath[end - (i - start)] = currentPath[i];
			
			//System.out.println("flipped: " + currentPath[i] + " from " + i + " to " + (end-(i-start)));
		}
		
		//then copy the rest
		for(int i = end + 1; i < currentPath.length; i++)
		{
			potentPath[i] = currentPath[i];
			
			//System.out.println("finished: " + currentPath[i] + " at " + i);
		}
		
		//then calculates potentdistance
		
		potentDistance = 0;
		for(int i = 0; i < potentPath.length; i++)
		{
			potentDistance += distanceTable[potentPath[i]][potentPath[(i+1)%potentPath.length]];
		}
		
		//System.out.println(Arrays.toString(potentPath));
		
		if(potentDistance < currentDistance)
		{
			currentPath = potentPath.clone();
			currentDistance = potentDistance;
			//System.out.println("replaced!");
		}
		else
		{
			double x = tempFunction2() * Math.pow(currentDistance/potentDistance, 2);
			System.out.println(x);
			if(Math.random() < (x))
			{
				currentPath = potentPath.clone();
				currentDistance = potentDistance;
				//System.out.println("I got lucky");
			}
			else
			{
				//System.out.println("I've returned");
				return;
			}
		}
		
	}
	
	public void finish()
	{
		for(int first = 0; first < currentPath.length - 1; first++)
		{
			for(int second = first + 1; second < currentPath.length; second++)
			{
				for(int i = 0; i < first; i++)
				{
					potentPath[i] = currentPath[i];
				}
				
				for(int i = first; i <= second; i++)
				{
					potentPath[second - (i - first)] = currentPath[i];
				}
				
				for(int i = second + 1; i < currentPath.length; i++)
				{
					potentPath[i] = currentPath[i];
				}
				
				potentDistance = 0;
				for(int i = 0; i < potentPath.length; i++)
				{
					potentDistance += distanceTable[potentPath[i]][potentPath[(i + 1) % potentPath.length]];
				}

				if(potentDistance < currentDistance)
				{
					currentPath = potentPath.clone();
					currentDistance = potentDistance;
					System.out.println("Trying to finalize!");
					first = 0;
					second = 1;
					pathComp.setPermutation(currentPath);
					frame.repaint();
				}
			}
		}
	}
	
	public double tempFunction()
	{
		return 0.8 - (((System.currentTimeMillis() - startTime)/scheduleTime) * 0.8 * 2);
	}
	
	public double tempFunction2()
	{
		return (34*Math.exp(-5*(((System.currentTimeMillis() - startTime)/(double)scheduleTime) + 0.7)))/1000;
	}
	
	public static double[][] makeDistanceTable(List<List<Double>> source)
	{
		double[][] table = new double[source.size()][source.size()];
		for(int i = 0; i < source.size(); i++)
		{
			for(int j = 0; j < source.size(); j++)
			{
				table[i][j] = pythagor(source.get(i).get(0) - source.get(j).get(0), source.get(i).get(1) - source.get(j).get(1));
			}
		}
		return table;
	}
	
    public static double pythagor(double x , double y)
    {
        double c = Math.sqrt((x*x)+(y*y));
        return c;
    }
	
	public static List<List<Double>> makeMap(int numTowns, int x, int y)
	{
		List<List<Double>> table = new ArrayList<List<Double>>();
		
		for(int i = 0; i < numTowns; i++)
		{
			List<Double> town = new ArrayList<>(Arrays.asList(Double.parseDouble(new DecimalFormat("##.##").format(Math.random() * x)), Double.parseDouble(new DecimalFormat("##.##").format(Math.random() * y))));
			//List town = new ArrayList<Double>(Arrays.asList(Math.random() * x , Math.random() * y));
			table.add((ArrayList<Double>) town);
		}
		return table;
	}
}
