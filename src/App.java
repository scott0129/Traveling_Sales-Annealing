import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class App 
{
	public static void main(String[] args)
	{
		//System.out.println("Hello");
		
		JFrame frame = new JFrame();
        PathComponent d = new PathComponent();

        frame.add(d);

        frame.setVisible(true);

        Pathfinder pf = new Pathfinder(100, 7, 7, frame, d);
        pf.getPath();

		frame.setSize(900, 900);
		frame.setTitle("Traveling Salesman Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label = new JLabel("Completion: 0%");
		label.setFont(new Font("Consolas", Font.PLAIN, 50));
		label.setVerticalAlignment((int) JFrame.TOP_ALIGNMENT);

		d.addTown(pf.getMap());

		List<Integer> temp = new ArrayList<Integer>();

		for(int i = 0; i < pf.getPath().length; i++)
		{
			temp.add(pf.getPath()[i]);
		}

		pf.run();

		d.setPermutation(pf.getPath());
		


	}
}
