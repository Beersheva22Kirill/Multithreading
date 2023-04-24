package telran.game;


import java.util.LinkedList;

import telran.view.*;

public class RaceApp {

	public static void main(String[] args) {

		StandartInputOutput inputOutput = new StandartInputOutput();

		Menu menu = new Menu("Race menu", Item.of("Start", is -> {
			int place = 1;
			Race race = createRace(is);
			try {
				race.startRace();
				for (Runner runner : race.runners) {
					runner.join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			is.writeLine("	Winner " + race.getWinner() + "\n");
			for (String string : race.getWinners()) {
				is.writeLine("Place: " + place++ + string);
			}			
		}), Item.exit());

		menu.perform(inputOutput);

		System.out.println("Application closed");

	}

	private static Race createRace(InputOutput is) {
		Race race = new Race();
		int countRunner = is.readInt("Enter count runner", "Not int");
		int distance = is.readInt("Enter distance", "Not int");
		race.createRunners(countRunner, distance, race);
		return race;
	}

}
