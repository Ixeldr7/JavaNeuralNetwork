package dndAI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Character {
	//initialise variables
	String name;
	int input;
	String strength, dexterity, constitution, intelligence, wisdom, charisma, cls, proficiencyBonus, armourClass, speed,
	maxHp, currentHp, spellCastingAbility, spellSaveDc, spellAttackBonus, weaponRange, weaponDamage, spellSlots, 
	healthPotions, conditions, disengaged;
			
	String[] classDetails = new String[18];
	String[] classFile = new String[4];
			
	Scanner scanner = new Scanner(System.in);
	File root = new File("");
	
	public Character() throws FileNotFoundException
	{
		name = enterName();
		cls = enterClass();
		createCharacter();
	}
	
	private String enterName ()
	{
		//user enters a player name
		System.out.println("Enter your player name: ");
		name = scanner.nextLine();
		//OutputCapture.textArea.getText();
		//System.out.println(name);
		return name;
	}

	private String enterClass()
	{
		//user is prompted to choose a class
		System.out.println("What class are you?");
	    System.out.println("1. Cleric");
	    System.out.println("2. Fighter");
	    System.out.println("3. Rogue");
	    System.out.println("4. Wizard");
	    
	    input = scanner.nextInt();
	    String choice = Integer.toString(input);
	    System.out.println(choice);
	    //adjustment for file reading
	    input = input - 1;
	    
	    return choice;
		}
	
	private void createCharacter() throws FileNotFoundException{
		int x;
		
		//data is loaded from the classes file
		File classes = new File ("src/dndAI/Classes.txt");
		Scanner reader = new Scanner(classes).useDelimiter(":");
		
		//loops through all file lines
		for(x = 0; x < 4; x++)
		{
			classFile[x] = reader.nextLine();
			//System.out.println(classFile[x]);
		}
		reader.close();
		stringDeconstructor();		
	}
	
	private void stringDeconstructor()
	{
		int x;
		//finds the correct line of the array from
		String chosenClass = classFile[input];
		//populates the array class details with the appropriate data
		for(x = 0; x < 18; x++)
		{
			classDetails = chosenClass.split(":");
			//System.out.println(classDetails[x]);
			//System.out.println(x);
		}
		attributeAssigner();
	}
	
	private void attributeAssigner()
	{
		//assigns attributes
		this.strength = classDetails[2];
		this.dexterity = classDetails[3];
		this.constitution = classDetails[4];
		this.intelligence = classDetails[5];
		this.wisdom = classDetails[6];
		this.charisma = classDetails[7];
		this.proficiencyBonus = classDetails[8];
		this.armourClass = classDetails[9];
		this.speed = classDetails[10];
		this.maxHp = classDetails[11];
		this.currentHp = maxHp;
		this.spellCastingAbility = classDetails[12];
		this.spellSaveDc = classDetails[13];
		this.spellAttackBonus = classDetails[14];
		this.weaponRange = classDetails[15];
		this.weaponDamage = classDetails[16];
		this.spellSlots = classDetails[17];
		this.healthPotions = classDetails[18];
		this.conditions = "0";
		this.disengaged = "0";
		printDetails();
	}
	
	private void printDetails()
	{
		System.out.println("Character Details: " + "\n");
		System.out.println("Name: " + name + "\n");
		
		System.out.println("Strength: " + strength + "\n");
		System.out.println("Dexterity: " + dexterity + "\n");
		System.out.println("Consitution: " + constitution + "\n");
		System.out.println("Intelligence: " + intelligence + "\n");
		System.out.println("Wisdom: " + wisdom + "\n");
		System.out.println("Charisma: " + charisma + "\n");
		
		System.out.println("Proficiency Bonus: " + proficiencyBonus + "\n");
		System.out.println("Armour Class: " + armourClass + "\n");
		System.out.println("Speed: " + speed + "\n");
		System.out.println("Maximum Hitpoints: " + maxHp + "\n");
		System.out.println("Spell Casting Ability: " + spellCastingAbility + "\n");
		System.out.println("Spell Save DC: " + spellSaveDc + "\n");
		System.out.println("Spell Attack Bonus: " + spellAttackBonus + "\n");
		System.out.println("Weapon Range: " + weaponRange + "\n");
		System.out.println("Weapon Damage: " + weaponDamage + "\n");
		System.out.println("Spell Slots: " + spellSlots + "\n");
		System.out.println("Health Potions: " + healthPotions + "\n");
		System.out.println("Conditions: " + conditions + "\n");
		
	}
	
	public String toString()
	{
		return name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getDexterity() {
		return dexterity;
	}

	public void setDexterity(String dexterity) {
		this.dexterity = dexterity;
	}

	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public String getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(String intelligence) {
		this.intelligence = intelligence;
	}

	public String getWisdom() {
		return wisdom;
	}

	public void setWisdom(String wisdom) {
		this.wisdom = wisdom;
	}

	public String getCharisma() {
		return charisma;
	}

	public void setCharisma(String charisma) {
		this.charisma = charisma;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getProficiencyBonus() {
		return proficiencyBonus;
	}

	public void setProficiencyBonus(String proficiencyBonus) {
		this.proficiencyBonus = proficiencyBonus;
	}

	public String getArmourClass() {
		return armourClass;
	}

	public void setArmourClass(String armourClass) {
		this.armourClass = armourClass;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(String maxHp) {
		this.maxHp = maxHp;
	}

	public String getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(String currentHp) {
		this.currentHp = currentHp;
	}

	public String getSpellCastingAbility() {
		return spellCastingAbility;
	}

	public void setSpellCastingAbility(String spellCastingAbility) {
		this.spellCastingAbility = spellCastingAbility;
	}

	public String getSpellSaveDc() {
		return spellSaveDc;
	}

	public void setSpellSaveDc(String spellSaveDc) {
		this.spellSaveDc = spellSaveDc;
	}

	public String getSpellAttackBonus() {
		return spellAttackBonus;
	}

	public void setSpellAttackBonus(String spellAttackBonus) {
		this.spellAttackBonus = spellAttackBonus;
	}

	public String getWeaponRange() {
		return weaponRange;
	}

	public void setWeaponRange(String weaponRange) {
		this.weaponRange = weaponRange;
	}

	public String getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(String weaponDamage) {
		this.weaponDamage = weaponDamage;
	}
	
	public String getSpellSlots(){
		return spellSlots;
	}
	
	public void setSpellSlots(String spellSlots){
		this.spellSlots = spellSlots;
	}
	
	public String getHealthPotions(){
		return healthPotions;
	}
	
	public void setHealthPotions(String healthPotions){
		this.healthPotions = healthPotions;
	}
	
	public String getConditions(){
		return conditions;
	}
	
	public void setConditions(String conditions){
		this.conditions = conditions;
	}
	
	public String getDisengaged(){
		return disengaged;
	}
	
	public void setDisengaged(String disengaged){
		this.disengaged = disengaged;
	}
}
