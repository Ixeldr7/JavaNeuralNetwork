package dndAI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Monster {
	//initialise variables
	int input, linesToRead, num;
	String[] encounterFileData = new String[6];
	String[] monsterData = new String [18];
	String fileName, encounter;
	
	String name, strength, dexterity, constitution, intelligence, wisdom, charisma, proficiencyBonus, armourClass, speed,
	maxHp, currentHp, spellCastingAbility, spellSaveDc, spellAttackBonus, weaponRange, weaponDamage, spellSlots,
	healthPotions, conditions, trait, disengaged;
	
	Scanner scanner = new Scanner(System.in);
	File encounterFile = new File("");
	
	public Monster(String[] monsterData) throws FileNotFoundException
	{	
		//passes the monster data to attribute assigner
		attributeAssigner(monsterData);
		//
	}	

		
	private void attributeAssigner(String[] monsterData2) 
	{
		//assigns monster stats
		this.name = monsterData2[1];
		this.strength = monsterData2[2];
		this.dexterity = monsterData2[3];
		this.constitution = monsterData2[4];
		this.intelligence = monsterData2[5];
		this.wisdom = monsterData2[6];
		this.charisma = monsterData2[7];
		this.proficiencyBonus = monsterData2[8];
		this.armourClass = monsterData2[9];
		this.speed = monsterData2[10];
		this.maxHp = monsterData2[11];
		this.currentHp = maxHp;
		this.spellCastingAbility = monsterData2[12];
		this.spellSaveDc = monsterData2[13];
		this.spellAttackBonus = monsterData2[14];
		this.weaponRange = monsterData2[15];
		this.weaponDamage = monsterData2[16];
		this.spellSlots = monsterData2[17];
		this.healthPotions = monsterData2[18];
		this.trait = monsterData2[19];
		this.conditions = "0";
		this.disengaged = "0";
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
		System.out.println("Trait: " + trait + "\n");
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
	
	public void setTrait(String trait){
		this.trait = trait;
	}
	
	public String getTrait(){
		return trait;
	}
	
	public String getDisengaged(){
		return disengaged;
	}
	
	public void setDisengaged(String disengaged){
		this.disengaged = disengaged;
	}
}
