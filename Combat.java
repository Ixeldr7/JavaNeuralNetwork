package dndAI;

import java.util.Scanner;

public class Combat {

	Character party[] = new Character[4];
	Character monsters[] = new Character[6];
	
	int input, turn;
	
	Scanner scanner = new Scanner(System.in);
	
	public Combat()
	{
		initialise();
		playerTurn();
		aiTurns();
		resolveAndUpdate();
	}

	private void initialise() 
	{
		
		
	}

	private void playerTurn() 
	{
		System.out.println("Choose an Action: ");
	    System.out.println("1. Attack");
	    System.out.println("2. Help");
	    System.out.println("3. Disengage");
	    System.out.println("4. Cast a spell");
		input = scanner.nextInt();
		
		if(input == 1)
		{
			attack();
		}
		if(input == 1)
		{
			help();
		}
		if(input == 1)
		{
			disengage();
		}
		if(input == 1)
		{
			castSpell();
		}
	}
	
	private void aiTurns()
	{
		
	}
	
	private void resolveAndUpdate() 
	{
		
		
	}
	
	private void attack()
	{
		
	}
	
	private void help()
	{
		
	}
	
	private void disengage()
	{
		
	}
	
	private void castSpell()
	{
		
	}
	
}
