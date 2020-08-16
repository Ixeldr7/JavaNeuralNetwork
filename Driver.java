package dndAI;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Driver {
	
	//initialise variables
	static ArrayList<Monster> monsters= new ArrayList<Monster>();
	int partySize, monsterSize;
	static int input;
	static int linesToRead;
	int turn, num;
	static String[] encounterFileData = new String[6];
	static String[] monsterData = new String [19];
	static String fileName;
	static Random rand = new Random();
	String encounter;
	
	//variables for data processing
	static double personalCondition;
	static double allyCondition;
	static double enemyCondition;
	
	static boolean game = true;
	
	static Scanner scanner = new Scanner(System.in);
	File encounterFile = new File("");
	
	
	public static void main(String args[]) throws FileNotFoundException
	{
		//displays the user interface
		displayGUI();
		//initialises the neural network
		aiCore computer = new aiCore();
		Network Core = computer.prepAI();
		//the player creates their character
		Character player = new Character();
		//the player chooses an encounter
		chooseEncounter();
		//creates a loop for combat
		while(game)
		{
			//player chooses an action
			combat(player);
			//process data for the neural network
			//takes a turn for each npc under the control of the ai
			int enemies = monsters.size() - 1;
			int x = 0;
			while( x <= enemies)
			{
				Monster monster =  monsters.get(x);
				combat(Core, player, monster, computer, monsters);
				x++;
			}
		//checks to see if the game has ended
		resolveRound(player, monsters);
		}
		//ends the game and exits the program
		endGame();
	}
	
	public static void displayGUI()
	{
		OutputCapture ui = new OutputCapture();
        JFrame frame = new JFrame();
        
        //creates the ui frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(ui);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //sets the system out to print to the gui as well as the console
        PrintStream printStream = System.out;
        System.setOut(new PrintStream(new TextOutputStream("", ui, printStream)));
	}	
	
	public static void chooseEncounter() throws FileNotFoundException
	{	
		//the user is prompted to choose one of five encounters
		System.out.println("Choose an Encounter: ");
		System.out.println("1. Goblin Skirmishers");
		System.out.println("2. Bugbear");
		System.out.println("3. Goblin Camp");
		System.out.println("4. Camp Chieftain");
		System.out.println("5. Orc Invaders \n");
		input = scanner.nextInt();
		//if the user has entered a value out of bounds it is set to five
		if(input > 5)
		{
			input = 5;
		}
		//the appropriate filename is used depending on the choice
		String fileName = selectFile(input);
		createEncounter(fileName);
	}
	
	private static  String selectFile(int input)
	{
		fileName = " ";
		linesToRead = 0;
		//assigns appropriate filename
		if(input == 1)
		{
			fileName = ("src/dndAI/Encounter 1.txt");
			linesToRead = 2;
		}
		else if(input == 2)
		{
			fileName = ("src/dndAI/Encounter 2.txt");
			linesToRead = 1;
		}
		else if(input == 3)
		{
			fileName = ("src/dndAI/Encounter 3.txt");
			linesToRead = 6;
		}
		else if(input == 4)
		{
			fileName = ("src/dndAI/Encounter 4.txt");
			linesToRead = 3;
		}
		else if(input == 5)
		{
			fileName = ("src/dndAI/Encounter 5.txt");
			linesToRead = 5;
		}
		
		return fileName;
	}
	
	private static void createEncounter(String fileName) throws FileNotFoundException
	{
		int x;
		File encounter = new File(fileName);
		Scanner reader = new Scanner(encounter).useDelimiter(":");
	
		//loads encounter data from the selected file
		for( x = 0; x < linesToRead; x++)
		{			
			encounterFileData[x] = reader.nextLine();
			//System.out.println(encounterFileData[x]);
		}
		stringDeconstructor();
	}
	
	private static void stringDeconstructor() throws FileNotFoundException
	{
		int x,z;
		String currentMonster = " ";
		int numberOfMonsters = linesToRead;
		//loops through the array of monster stats
		for(x = 0; x < numberOfMonsters; x++)
		{
			//selects the next monster from the array
			currentMonster = encounterFileData[x];
			//populates an array with a monsters data
			for(z = 0; z < 16; z++)
			{
				monsterData = currentMonster.split(":");
				//System.out.println(monsterData[z]);				
			}
			//creates a new monster object with the data and adds it to the monsters array list
			monsters.add(new Monster((monsterData)));
			System.out.println(monsters.get(x).toString());
		}
	}
	
	public static void combat(Character player)
	{
		//The player chooses which combat action they want to take
		System.out.print("You have " + player.getCurrentHp() + " hitpoints remaining out of " + player.getMaxHp());
		System.out.print("\nYou have " + player.getSpellSlots() + " spell slots remaining.\n");
		System.out.println("Choose an Action: ");
		System.out.println("1. Attack");
		System.out.println("2. Health Potion");
		System.out.println("3. Disengage");
		System.out.println("4. Cast a spell \n");
		input = scanner.nextInt();

		if(input == 1)
		{
			attack(player);
		}
		if(input == 2)
		{
			healthPotion(player);
		}
		if(input == 3)
		{
			disengage(player);
		}
		if(input == 4)
		{
			castSpell(player);
		}	
	}
	
	//the computer decides on a combat action to take
	private static void combat(Network Core, Character player, Monster monster, aiCore computer, ArrayList<Monster> monsters)
	{
		//processes data for the neural network
		dataProcessing(player, monster, monsters);
		double choice = computer.useCore(Core, personalCondition, allyCondition, enemyCondition);
		System.out.print("AI CHOICE: " + choice);
		
		//fuzzy logic emotional processing
		choice = fuzzyEmotion(monster, personalCondition, allyCondition, enemyCondition, choice);
		
		//an action is taken based on the ai decision making
		if(choice == 1)
		{
			attack(monster, player);
		}
		if(choice == 2)
		{
			healthPotion(monster);
		}
		if(choice == 3)
		{
			disengage(monster);
		}
		if(choice == 4)
		{
			castSpell(monster);
		}	
		
	}
	
	public static double fuzzyEmotion(Monster monster, double personalCon, double allyCon, double enemyCon, double choice)
	{
		//if the monster has a trait it influences the action that it takes
		//intended to override the more calculated decision made by the neural network
		if(monster.getTrait().equals("None"))
		{
			return choice;
		}
		//monster gets angry when hurt and attacks
		else if(monster.getTrait().equals("Angry") && personalCon < 0.5)
		{
			choice = 1;
		}
		//monster is cowardly and will disengage if losing
		else if(monster.getTrait().equals("Coward") && (allyCon < 0.5 || personalCon < 0.5))
		{
			choice = 3;
		}
		//monster will heal a lot if cautious 
		else if(monster.getTrait().equals("Cautious") && personalCon < 0.75)
		{
			choice = 2;
		}
		return choice;
	}
	
	//Process data into an appropriate format for the neural network
	public static void dataProcessing(Character player, Monster monster, ArrayList<Monster> monsters)
	{
		//resets condition values
		personalCondition = 0.0;
		allyCondition = 0.0;
		enemyCondition = 0.0;
		
		//processes the current monsters condition
		personalCondition = processMonster(monster);
		//processes the condition of each monster to form an average team condition
		double monsterCount = monsters.size() - 1;
		int x = 0;
		while( x <= monsterCount)
		{
			Monster currentMonster = monsters.get(x);
			allyCondition = allyCondition + processMonster(currentMonster);
			x++;
		}
		//averages the ally condition
		allyCondition = allyCondition / monsters.size();
		System.out.println("Monster Team Condition: " + allyCondition);
		enemyCondition = processPlayer(player);
	}
	
	public static double processMonster(Monster monster)
	{
		double rx = 0.0;
		double currentHP = Integer.parseInt(monster.getCurrentHp());
		double maxHP = Integer.parseInt(monster.getMaxHp());
		double ac = Integer.parseInt(monster.getArmourClass());
		double healthPots = Integer.parseInt(monster.getHealthPotions());
		double conditions = 0.0;
		
		//changes the value of conditions based on how many the monster has
		if(Integer.parseInt(monster.getConditions()) == 0)
		{
			conditions = 1.0;
		}
		else 
		{
			conditions = 0.5;
		}
		
		//performs some math to create a number between 0 and 1 for then neural network
		rx = (currentHP / maxHP) + (ac / 20.0) + (healthPots/5.0) + conditions;
		rx = rx / 4.0;
		System.out.println("Monster Condition: " + rx);
		return rx;
	}
	
	public static double processPlayer(Character player)
	{
		double rx = 0.0;
		double currentHP = Integer.parseInt(player.getCurrentHp());
		double maxHP = Integer.parseInt(player.getMaxHp());
		double ac = Integer.parseInt(player.getArmourClass());
		double healthPots = Integer.parseInt(player.getHealthPotions());
		double spellSlots = Integer.parseInt(player.getSpellSlots());
		double conditions = 0.0;
		
		//changes the value of conditions based on how many the monster has
		if(Integer.parseInt(player.getConditions()) == 0)
		{
			conditions = 1.0;
		}
		else 
		{
			conditions = 0.5;
		}
		
		//performs some math to create a number between 0 and 1 for then neural network
		rx = (currentHP / maxHP) + (ac / 20.0) + (healthPots/5.0) + (spellSlots / 6.0) + conditions;
		rx = rx / 4.0;
		System.out.println("Player Condition: " + rx);
		return rx;
	}
	
	//The player attacks a monster
	private static void attack(Character player) 
	{
		//The player chooses a target from the existing group of enemies
		System.out.print("Choose an Enemy to Attack: \n");
		int enemies = monsters.size() - 1;
		int x = 0;
		while( x <= enemies)
		{
			int z = x + 1;
			System.out.println(z + ": " + monsters.get(x).toString());
			x++;
		}
		int input = scanner.nextInt();
		input = input - 1;
		Monster target = monsters.get(input);
		
		//checks to see if the creature has disengaged
		if(Integer.parseInt(target.getDisengaged()) == 1)
		{
			System.out.println(target.getName() + " has disengaged. Your attack misses.\n");
			target.setDisengaged("0");
		}
		else		
		{
			//An attack roll is made
			int attackRoll = roll(20,1) + 5;
			System.out.println("You rolled " + attackRoll);
		
			//If the attack roll is higher than the targets armour
			if(attackRoll > Integer.parseInt(target.getArmourClass()))
			{
				//the attack is successful and some damage is dealt to the target
				int damage = roll(Integer.parseInt(player.getWeaponDamage()),1);
				System.out.println("You hit " + target.toString() + " for " + damage + " damage.\n");
				resolveDamage(target, damage);
			}
			else
			{
				//Otherwise the attack misses
				System.out.println("Your attack missed. \n");
			}
		}
	}
	
	//The monster attacks the player
	private static void attack(Monster monster, Character player) 
	{	
		//checks to see if the player has disengaged
		if(Integer.parseInt(player.getDisengaged()) == 1)
		{
			System.out.println(player.getName() + " has disengaged." + monster.getName() + " misses their attack.\n");
			player.setDisengaged("0");
		}
		else		
		{
			//An attack roll is made
			int attackRoll = roll(20,1) + 3;
			System.out.println(attackRoll);
		
			//If the attack roll is higher than the players armour
			if(attackRoll > Integer.parseInt(player.getArmourClass()))
			{
				//the attack is successful and some damage is dealt to the player
				int damage = roll(Integer.parseInt(monster.getWeaponDamage()),1);
				System.out.println(monster.getName() +  " hit " + player.toString() + " for " + damage + " damage.\n");
				resolveDamage(player, damage);
			}
			else
			{
				//Otherwise the attack misses
				System.out.println(monster.getName() + " misses their attack.\n");
			}
		}
	}
	
	//Used for calculating dice rolls
	public static int roll(int dice, int multiplier)
	{
		int roll = (rand.nextInt(dice) * multiplier) + 1;
		return roll;
	}
	
	//resolves any combat damage dealt by the player
	public static void resolveDamage(Monster target, int damage)
	{
		int currentHp = Integer.parseInt(target.getCurrentHp());
		
		//If the damage is greater than the current hp of the target
		if(damage >= currentHp)
		{
			//the target is slain and removed from play
			System.out.println(target.toString() + " was slain!\n");
			//-----------------------------------------------------------------------------------------------
			//-----------------------------------------------------------------------------------------------
			//when removing from array list, it may need reformating so targetting works correctly
			monsters.remove(monsters.indexOf(target));
			//-----------------------------------------------------------------------------------------------
			//-----------------------------------------------------------------------------------------------
		}
		else
		{
			//otherwise the target simply loses health
			currentHp = currentHp - damage;
			target.setCurrentHp(Integer.toString(currentHp));
		}
	}
	
	//resolves combat damage dealt by a monster
	public static void resolveDamage(Character player, int damage)
	{
		int currentHp = Integer.parseInt(player.getCurrentHp());
		
		//If the damage is greater than the current hp of the player
		if(damage >= currentHp)
		{
			System.out.println("You were defeated by the monsters!\n");
			currentHp = currentHp - damage;
			player.setCurrentHp(Integer.toString(currentHp));
		}
		else
		{
			//otherwise the player simply loses health
			currentHp = currentHp - damage;
			player.setCurrentHp(Integer.toString(currentHp));
		}
	}
	
	//the player drinks a health potion
	private static void healthPotion(Character player) 
	{
		int remainingPotions = Integer.parseInt(player.getHealthPotions());
		//checks that the player has health potions remaining
		if(remainingPotions > 0)
		{
			System.out.println("You drink a health potion.\n");
			int amountHealed = roll(6,3);
			int currentHealth = Integer.parseInt(player.getCurrentHp());
			currentHealth = currentHealth + amountHealed;
			//deducts a health potion from the player
			remainingPotions--;
			player.setHealthPotions(Integer.toString(remainingPotions));
			//if the player would be healed to an amount of health greater than their maximum
			if(currentHealth > Integer.parseInt(player.getMaxHp()))
			{
				//they are healed to full health
				currentHealth = Integer.parseInt(player.getMaxHp());
				player.setCurrentHp(Integer.toString(currentHealth));
			}
			else
			{
				//otherwise they are healed for the amount rolled
				player.setCurrentHp(Integer.toString(currentHealth));
			}
		}
		else
		{
			System.out.println("You have no health potions left.\n");
		}
		
	}
	
	//the monster drinks a health potion
	private static void healthPotion(Monster monster) 
	{
		int remainingPotions = Integer.parseInt(monster.getHealthPotions());
		//checks that the monster has health potions remaining
		if(remainingPotions > 0)
		{
			System.out.println("You drink a health potion.\n");
			int amountHealed = roll(6,3);
			int currentHealth = Integer.parseInt(monster.getCurrentHp());
			currentHealth = currentHealth + amountHealed;
			remainingPotions--;
			monster.setHealthPotions(Integer.toString(remainingPotions));
			//if the monster would be healed to an amount of health greater than their maximum
			if(currentHealth > Integer.parseInt(monster.getMaxHp()))
			{
				//they are healed to full health
				currentHealth = Integer.parseInt(monster.getMaxHp());
				monster.setCurrentHp(Integer.toString(currentHealth));
			}
			else
			{
				//otherwise they are healed for the amount rolled
				monster.setCurrentHp(Integer.toString(currentHealth));
			}
		}
		else
		{
			System.out.println("You have no health potions left.\n");
		}
	}
	
	//the player attempts to disengage from combat
	private static void disengage(Character player) 
	{
		//the player must win a dice roll to successfully disengage
		int playerRoll = roll(20,1);
		int monsterRoll = roll(20,1);
		if(playerRoll > monsterRoll)
		{
			System.out.println("You successfully disengaged. \n");
			player.setDisengaged("1");
		}
		else
		{
			System.out.println("Disengage failed. \n");
		}
	}
	
	//the monster attempts to disengage from combat
	private static void disengage(Monster monster) 
	{
		//the player must win a dice roll to successfully disengage
		int playerRoll = roll(20,1);
		int monsterRoll = roll(20,1);
		if(playerRoll > monsterRoll)
		{
			System.out.println(monster.getName() + " successfully disengaged. \n");
			monster.setDisengaged("1");
		}
		else
		{
			System.out.println(monster.getName() + " Failed to disengage. \n");
		}
	}
	
	//the player casts a spell
	private static void castSpell(Character player) 
	{
		int remainingSpellSlots = Integer.parseInt(player.getSpellSlots());
		//checks if the player can cast spells
		if(remainingSpellSlots > 0)
		{
			//a spell slot is deducted
			remainingSpellSlots--;
			player.setSpellSlots(Integer.toString(remainingSpellSlots));
			//if so they are presented with a spell list for their class
			if(Integer.parseInt(player.getCls()) == 1)
			{
				//cleric spells
				System.out.println("Choose a Spell to Cast: ");
				System.out.println("1. Smite");
				
				input = scanner.nextInt();
				if(input == 1)
					smite(player);
			}
			if(Integer.parseInt(player.getCls()) == 2)
			{
				//fighter spells
				System.out.println("Choose a Spell to Cast: ");
				System.out.println("1. Whirlwind");
				
				input = scanner.nextInt();
				if(input == 1)
					whirlwind(player);
			}
			if(Integer.parseInt(player.getCls()) == 3)
			{
				//rogue spells
				System.out.println("Choose a Spell to Cast: ");
				System.out.println("1. Smoke Bomb");
				
				input = scanner.nextInt();
				
				if(input == 1)
					smokeBomb(player);
			}
			if(Integer.parseInt(player.getCls()) == 4)
			{
				//wizard spells
				System.out.println("Choose a Spell to Cast: ");
				System.out.println("1. Burning Hands");
				System.out.println("2. Magic Missile");
				System.out.println("3. Mage Armour");
				
				input = scanner.nextInt();
				
				if(input == 1)
				{
					burningHands(player);
				}
				else if (input == 2)
				{
					magicMissile(player);
				}
				else if (input == 3)
				{
					mageArmour(player);
				}
				
			}
		}
		else
		{
			//otherwise they are informed that they have no spells available
			System.out.println("You have no spell slots remaining\n");
		}
	}	
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Player Spells
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static void smite(Character player)
	{
		//The player chooses a target from the existing group of enemies
		System.out.print("Choose an Enemy to Attack: \n");
		int enemies = monsters.size() - 1;
		int x = 0;
				
		while( x <= enemies)
		{
			int z = x + 1;
			System.out.println(z + ": " + monsters.get(x).toString());
			x++;
		}
		
		int input = scanner.nextInt();
		input = input - 1;
		Monster target = monsters.get(input);
						
		//checks to see if the creature has disengaged
		if(Integer.parseInt(target.getDisengaged()) == 1)
		{
			System.out.println(target.getName() + "has disengaged. Your attack misses.\n");
			target.setDisengaged("0");
		}
		else		
		{
			//The target is hit by the smite
			int damage = roll(8,2);
			System.out.println("Your smite hit " + target.toString() + " for " + damage + " damage.\n");
			resolveDamage(target, damage);
			//The player is then healed for the damage dealt
			//if the player would be healed to an amount of health greater than their maximum
			if(damage > Integer.parseInt(player.getMaxHp()))
			{
				//they are healed to full health
				damage = Integer.parseInt(player.getMaxHp());
				player.setCurrentHp(Integer.toString(damage));
			}
			else
			{
				//otherwise they are healed for the amount rolled
				player.setCurrentHp(Integer.toString(damage));
			}
			
		System.out.println("You were healed for " + damage + ".\n");
		}
	}
	
	public static void whirlwind(Character player)
	{
		//makes an attack against each monster
		int enemies = monsters.size() - 1;
		int x = 0;
		while( x <= enemies)
		{
			//monsters have a chance to dodge the attack
			Monster target = monsters.get(x);
			int monsterRoll = roll(20,1);
			if(monsterRoll < 15)
			{
				//otherwise they are dealt some damage
				int damage = roll(8,2);
				System.out.println("Your whirlwind hit " + target.getName() + " for " + damage + " damage.\n");
				resolveDamage(target,damage);
				enemies--;
				x--;
			}
			else
			{
				System.out.println(target.getName() + " dodged your whirlwind!\n");
			}
			x++;
		}
	}
	
	public static void smokeBomb(Character player)
	{
		//the smoke bomb acts as taking the disengage action, the attack action and provides a tiny boost to armour class
		int ac = Integer.parseInt(player.getArmourClass());
		System.out.println("You throw a smoke bomb to the floor! " + ac);
		attack(player);
		player.setDisengaged("1");
		System.out.print("You successfully disengage!");
		ac = ac + 1;
		System.out.println("Your armour class has increased by 1.\n");
		player.setArmourClass(Integer.toString(ac));
	}
	
	public static void burningHands(Character player)
	{
		//makes an attack against each monster
		int enemies = monsters.size() - 1;
		int x = 0;
		while( x <= enemies)
		{
			//monsters have a chance to dodge the attack
			Monster target = monsters.get(x);
			int monsterRoll = roll(20,1);
			if(monsterRoll < 15)
			{
				//or else they are dealt some damage
				int damage = roll(6,3);
				System.out.println("Burning hands hit " + target.getName() + " for " + damage + " damage.\n");
				resolveDamage(target,damage);
				enemies--;
				x--;
			}
			else
			{
				System.out.println(target.getName() + " dodged your burning hands!\n");
			}
			x++;
		}
	}
	
	public static void magicMissile(Character player)
	{
		//The player chooses a target from the existing group of enemies
		System.out.print("Choose an Enemy to Attack: \n");
		int enemies = monsters.size() - 1;
		int x = 0;
		while( x <= enemies)
		{
			int z = x + 1;
			System.out.println(z + ": " + monsters.get(x).toString());
			x++;
		}
			int input = scanner.nextInt();
			input = input - 1;
			Monster target = monsters.get(input);
				
			//checks to see if the creature has disengaged
			if(Integer.parseInt(target.getDisengaged()) == 1)
			{
				System.out.println(target.getName() + "has disengaged. Your attack misses.\n");
				target.setDisengaged("0");
			}
			else		
			{
				//The target is hit by the magic missile
				int damage = roll(10,3);
				System.out.println("Your magic missile hit " + target.toString() + " for " + damage + " damage.\n");
				resolveDamage(target, damage);
			}
	}
	
	public static void mageArmour(Character player)
	{
		int ac = Integer.parseInt(player.getArmourClass());
		System.out.println("You cast mage armour, increasing your armour class from " + ac);
		ac = ac + 3;
		System.out.println("To " + ac + ".\n");
		player.setArmourClass(Integer.toString(ac));
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//the monster casts a spell
	private static void castSpell(Monster monster) 
	{
		//checks if the monster can cast spells
		//if so they are presented with a spell list for their class
			
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Monster Spells
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//resolves the round after everyone has taken a turn
	private static void resolveRound(Character player, ArrayList<Monster> monsters)
	{
		//if the player has died the game ends
		if(Integer.parseInt(player.getCurrentHp()) <= 0)
		{
			game = false;
		}
		//if all monsters have died the game ends
		if(monsters.size() <= 0 )
		{
			System.out.println("You have defeated the monsters!");
			game = false;
		}
		//otherwise the game continues
	}
	
	private static void endGame()
	{
		System.out.println("Game Over");
		System.exit(0);
	}

}
