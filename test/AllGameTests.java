import org.junit.runner.RunWith; 
import org.junit.runners.Suite; 

import command.TestAttackCommandEast;
import command.TestAttackCommandNorth;
import command.TestAttackCommandSouth;
import command.TestAttackCommandWest;
import command.TestDropAndAcquireCommand;
import command.TestMoveCommandEast;
import command.TestMoveCommandNorth;
import command.TestMoveCommandSouth;
import command.TestMoveCommandWest;
import command.TestReloadCommand;
import command.TestTurnEastCommand;
import command.TestTurnNorthCommand;
import command.TestTurnSouthCommand;
import command.TestTurnWestCommand;
import recovery.TestRecovery;
import weapon.TestPowerBooster;
import weapon.TestScope;
import weapon.TestStabilizer;
import weapon.TestWeapons;
import environment.TestEnvironment;
import environment.TestCell;
import gameplay.TestSimpleTimer;
import graphics.testInvoker;
import graphics.testMap;
import lifeform.TestAlien;
import lifeform.TestLifeForm;
import lifeform.TestHuman; 

/** 
 * Runs all of the tests in this project 
 * @author Dr. Dudley Girard -- first author
 * @author Dr. Alice Armstrong -- revisions
 */ 
@RunWith(Suite.class) 
@Suite.SuiteClasses( 
{  
	TestAttackCommandEast.class, TestAttackCommandNorth.class, TestAttackCommandSouth.class, TestAttackCommandWest.class,
	TestMoveCommandEast.class, TestMoveCommandNorth.class, TestMoveCommandSouth.class, TestMoveCommandWest.class,
	TestTurnEastCommand.class, TestTurnNorthCommand.class, TestTurnSouthCommand.class, TestTurnWestCommand.class,
	TestDropAndAcquireCommand.class, TestReloadCommand.class,
	TestCell.class, TestEnvironment.class,
	TestSimpleTimer.class,
	testInvoker.class, testMap.class,
	TestLifeForm.class, TestHuman.class, TestAlien.class,
	TestRecovery.class,
	TestStabilizer.class, TestPowerBooster.class, TestScope.class, TestWeapons.class
}) 
 
public class AllGameTests 
{ 
} 

