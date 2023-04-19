package telran.game;

import java.util.Iterator;

import telran.view.Item;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class RaceApp {

	public static void main(String[] args) {
	 	
		StandartInputOutput inputOutput = new StandartInputOutput();
		
		Menu menu = new Menu("Race menu", Item.of("Start", is -> {
			 Race race = new Race();
			 int countRunner = is.readInt("Enter count runner", "Not int");
			 int distance = is.readInt("Enter distance", "Not int");	
			 race.createRunners(countRunner, distance,race);
			 try {
				race.startRace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 is.writeString("Winner " + race.getWinner() + "\n\n");
			 int place = 1;
			 for (String string : race.getWinners()) {
				is.writeLine( "Place " + place++ + string);
			}
		}), Item.exit());
		
		menu.perform(inputOutput);
		
		
		System.out.println("Application closed");
	
	}

}
