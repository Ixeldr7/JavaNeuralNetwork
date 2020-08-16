package dndAI;

public class aiCore {
	//training data for the neural network
	public  double    TrainingSet[][] = {
		//PersonalCon, AllyCon, EnemyCon, Attack, HealthPotion, Disengage, Spell
		{1,              0.7,      0.5,    0.9,    0.1,   			0.1,      0.1},
		{0.5,            0.3,      0.8,    0.1,    0.1,   			0.9,      0.9},
		{0.9,            0.4,      0.5,    0.9,    0.1,   			0.1,      0.9},
		{1,              1,        1,      0.9,    0.1,   			0.1,      0.9},
		{0.4,            0.7,      0.8,    0.1,    0.1,   			0.9,      0.9},
		{0.1,            0.4,      0.3,    0.1,    0.9,   			0.9,      0.9},
		{0.6,            0.9,      0.4,    0.9,    0.9,  			0.1,      0.9},
		{0.6,            1,        0.8,    0.9,    0.1,  			0.1,      0.1},
		{0.8,            0.6,      0.8,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.73,           0.3,      0.6,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.4,      0.2,    0.9,    0.1,   			0.1,      0.1},
		{0.5,            0.8,      0.8,    0.1,    0.9,   			0.6,      0.1},
		{0.2,            0.7,      0.7,    0.2,    0.9,   			0.9,      0.9},
		{1,              0.8,      0.7,    0.9,    0.1,   			0.1,      0.9},
		{0.8,            0.5,      0.2,    0.9,    0.6,   			0.1,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.5,      0.9,    0.1,    0.9,   			0.6,      0.9},
		{0.2,            0.8,      0.3,    0.9,    0.9,   			0.6,      0.9},
		{0.4,            0.8,      0.1,    0.9,    0.9,   			0.1,      0.9},
		{1,              0.2,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.1,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.0,      0.6,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.6,    0.9,    0.1,   			0.1,      0.9},
		{0.4,            0.0,      0.2,    0.6,    0.9,   			0.1,      0.9},
		{1,              0.4,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.4,      0.4,    0.1,    0.9,   			0.9,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.4,            0.8,      0.3,    0.1,    0.6,   			0.9,      0.9},
		{1,              0.8,      0.7,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.4,              1,      0.4,    0.1,    0.9,   			0.5,      0.9},
		{0.7,              1,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{0.7,            0.6,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.5,            0.8,      0.2,    0.9,    0.1,   			0.9,      0.9},
		{1,              0.4,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.6,            0.8,      0.8,    0.9,    0.1,   			0.9,      0.9},
		{1,              0.8,      0.8,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.6,      0.9,    0.1,    0.9,   			0.9,      0.9},
		{0.2,            0.4,      0.9,    0.1,    0.9,   			0.9,      0.9},
		{0.2,            0.8,      0.9,    0.1,    0.6,   			0.9,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.8,            0.8,      0.3,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.9,      0.3,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.7,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.6,      0.7,    0.1,    0.9,   			0.9,      0.9},
		{0.6,            0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.6,      0.4,    0.1,    0.9,   			0.6,      0.9},
		{0.3,            0.6,      0.6,    0.1,    0.6,   			0.9,      0.9},
		{0.2,            0.8,      0.8,    0.1,    0.6,   			0.9,      0.9},
		{1,              0.4,      0.9,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.3,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.8,            0.8,      0.5,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.5,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.7,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.4,            0.7,      0.4,    0.9,    0.6,   			0.9,      0.9},
		{0.6,            0.7,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{0.4,            0.7,      0.8,    0.1,    0.6,   			0.9,      0.9},
		{1,              0.8,      0.3,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.5,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.5,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.9,      0.5,    0.1,    0.9,   			0.9,      0.9},
		{0.2,            0.8,      0.9,    0.1,    0.6,   			0.9,      0.9},
		{1,              0.6,      0.1,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.7,            0.6,      0.3,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.6,            0.2,      0.8,    0.6,    0.9,   			0.1,      0.9},
		{0.7,            0.2,      0.3,    0.9,    0.1,   			0.1,      0.9},
		{0.6,            0.7,      0.7,    0.9,    0.9,   			0.1,      0.9},
		{0.7,            0.7,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.8,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.7,      0.7,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.4,      0.6,    0.9,    0.1,   			0.1,      0.9},
		{0.8,            0.4,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.5,      0.5,    0.1,    0.6,   			0.9,      0.9},
		{0.8,            0.4,      0.6,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.8,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.9,      0.6,    0.9,    0.5,   			0.5,      0.9},
		{1,              0.8,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.3,      0.3,    0.9,    0.1,   			0.1,      0.9},
		{0.7,            0.8,      0.7,    0.9,    0.5,   			0.1,      0.9},
		{0.5,            0.1,      0.2,    0.9,    0.5,   			0.1,      0.9},
		{1,              0.8,      0.7,    0.9,    0.1,   			0.1,      0.9},
		{0.5,            0.8,      0.6,    0.5,    0.5,   			0.1,      0.9},
		{0.7,            0.7,      0.4,    0.9,    0.6,   			0.1,      0.9},
		{1,              0.8,      0.9,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.2,      0.2,    0.5,    0.5,   			0.1,      0.9},
		{0.2,            0.6,      0.3,    0.1,    0.5,   			0.5,      0.9},
		{1,              1.0,      0.8,    0.9,    0.1,   			0.1,      0.9},
		{0.6,            0.9,      0.8,    0.5,    0.5,   			0.3,      0.9},
		{1,                1,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{0.2,            0.8,      0.6,    0.1,    0.5,   			0.5,      0.9},
		{0.7,            0.7,      0.5,    0.9,    0.5,   			0.1,      0.9},
		{0.6,            0.8,      0.8,    0.9,    0.5,   			0.1,      0.9},
		{1,              0.1,      0.8,    0.9,    0.1,   			0.1,      0.9},
		{0.5,            0.6,      0.6,    0.5,    0.5,   			0.1,      0.9},
		{1,              0.6,      0.2,    0.9,    0.1,   			0.1,      0.9},
		{1,              0.8,      0.4,    0.9,    0.1,   			0.1,      0.9},
		{0.7,            0.5,      0.5,    0.9,    0.5,   			0.1,      0.9},
		{0.2,            0.8,      0.3,    0.1,    0.5,   			0.5,      0.9},
	};	
	
	//public double currentSet[][] = {			
	//};
	
	public aiCore()
	{
	}
	
	public Network prepAI()
	{
		Network Core = new Network();
		Core.Initialize(3, 4, 4);
		Core.SetLearningRate(0.2);
		Core.SetMomentum(true, 0.9);
		TrainCore(Core);
	    TestCore(Core);
	    System.out.println("AI Network Online \n");
		return Core;
	}
	
	//trains the neural network with the training data set
	public void TrainCore(Network Core)
	{
		int		i;
		double	error = 1;
		int		c = 0;

		System.out.println("************Before training*******************");
		Core.DumpData();
		//refines the neural network to a small degree of error
		while((error > 0.05) && (c<50000))
		{
			error = 0;
			c++;
			for(i=0; i<100; i++)
			{
				Core.SetInput(0, TrainingSet[i][0]);
				Core.SetInput(1, TrainingSet[i][1]);
				Core.SetInput(2, TrainingSet[i][2]);

				Core.SetDesiredOutput(0, TrainingSet[i][3]);
				Core.SetDesiredOutput(1, TrainingSet[i][4]);
				Core.SetDesiredOutput(2, TrainingSet[i][5]);
				Core.SetDesiredOutput(3, TrainingSet[i][6]);


				Core.FeedForward();
				error += Core.CalculateError();
				Core.BackPropagate();

			}
			error = error / 100.0f;
		}

		System.out.println("************After training*******************");
		Core.DumpData();


	}

	//Tests the neural network by comparing its output against the training data
	public void TestCore(Network Core)
	{

		System.out.println("Output results");
		for (int i=0; i<100; i++)
		{

	    Core.SetInput(0, TrainingSet[i][0]);
	    Core.SetInput(1, TrainingSet[i][1]);
	    Core.SetInput(2, TrainingSet[i][2]);

	    Core.FeedForward();

	    System.out.println("\n");
		System.out.println("--------------------------------------------------------");
		System.out.print((i+1) + " ");

		double max = -1000.0;
		int index = -1000;
		for(int j=0; j<3; j++)
		{
			System.out.print(Core.GetOutput(j) + "; ");
			if (max < Core.GetOutput(j))
				{
				   max = Core.GetOutput(j);
				   index = j;
				}
		}

		if (index == 0)
			System.out.print(" attack : " + Core.GetOutput(index) + "; ");
		else if (index == 1)
		    System.out.print(" help : " + Core.GetOutput(index) + "; ");
		else if (index == 2)
			System.out.print(" disengage : " + Core.GetOutput(index) + "; ");
		else if (index == 3)
			System.out.print(" spell: " + Core.GetOutput(index) + "; ");
		}

		System.out.println("\n");
	}
	
	//takes game data and chooses a combat action
	public int useCore(Network Core, double personalCondition, double allyCondition, double enemyCondition)
	{
		int i = 0;
		
		System.out.println("AI RESULTS: \n");
		
		double currentSet[][] = {
				//PersonalCon, 			AllyCon, 	EnemyCon, 		Attack, HealthPotion, Disengage, 	Spell
				{personalCondition, allyCondition, enemyCondition, 	0.0, 		0.0, 		0.0,	 	 0.0}
		};
		//takes the processed data from the game and runs it through the neural network
		Core.SetInput(0, currentSet[0][0]);
		Core.SetInput(1, currentSet[0][1]);
		Core.SetInput(2, currentSet[0][2]);
		
		Core.FeedForward();
		
		System.out.println("\n");
		System.out.println("--------------------------------------------------------");
		System.out.print((i+1) + " ");
		
		//index is assinged the value of the most favourable neuron value
		double max = -1000.0;
		int index = -1000;
		for(int j=0; j<3; j++)
		{
			System.out.print(Core.GetOutput(j) + "; ");
			if (max < Core.GetOutput(j))
				{
				   max = Core.GetOutput(j);
				   index = j;
				}
		}

		//System.out.print("index : " + index);
		//returns an integer for the ai choice based on the produced index
		if (index == 0)
		{
			System.out.print(" attack : " + Core.GetOutput(index) + "; ");
			return 1;
		}
		else if (index == 1)
		{
		    System.out.print(" help : " + Core.GetOutput(index) + "; ");
		    return 2;
		}
		else if (index == 2)
		{
			System.out.print(" disengage : " + Core.GetOutput(index) + "; ");
			return 3;
		}
		else if (index == 3)
		{
			System.out.print(" spell: " + Core.GetOutput(index) + "; ");
			return 4;
		}
		
		return index;
	}	

}
	

