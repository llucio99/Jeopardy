
public class Main {				
	static Game game;			
	
	static String[][] questions = {
			{ "Movie that won the 2017 Academy Award for Best Picture",
			  "Highest grossing film before Avengers: Endgame",
			  "Jordan Peele's first directed film, released in 2017" },

			{ "Played the character Brian Mills in the Taken Trilogy",
			  "Portrayed the character of Jack in the film Titanic",
			  "Played Forrest Gump in the movie of the same title" },

			{ "Movie director known for his exaggerated use of explosions",
			  "Directed various award winning films such as Inception and Interstellar",
			  "Mexican movie director that is known for his imaginative monsters" }
			};

	static String[] answers = {
			"Moonlight",
			"Avatar",
			"Get Out",
			"Liam Neeson",
			"Leonardo DiCaprio",
			"Tom Hanks",
			"Michael Bay",
			"Christopher Nolan",
			"Guillermo del Toro"
			};

	public static void main(String[] args) {
		String replay;

		do{								
			game = new Game();			
			
			int numPlayers = game.askForInt("How many players? ", 1, 3);

			for (int i = 0; i < numPlayers; i++) {
				String name = game.askForText("What is player " + i + " name?");
				game.addPlayer(name);				
			}
			
			shuffleQuestions();
			
			int maxRounds = answers.length / numPlayers;
			int numRounds = game.askForInt("How many rounds: ", 1, maxRounds);

			int[] categoryCount = new int[questions.length];

			for (int i = 0; i < numRounds; i++)

				for (int j = 0; j < numPlayers; j++) {
					game.setCurrentPlayer(j);
					int categoryNum = game.askForInt("Choose a category: 1 = Movies   2 = Actors   3 = Directors ", 1, 3) - 1;

					while (categoryCount[categoryNum] == questions[categoryNum].length) {
						int memory = categoryNum;

						while (categoryNum == memory)
							categoryNum = game.askForInt("Choose another category: 1 = Movies   2 = Actors   3 = Directors ", 1, 3) - 1;
					}

					String answer = game.askForText(questions[categoryNum][categoryCount[categoryNum]]);

					if (answers[categoryCount[categoryNum] + (categoryNum * questions.length)].equals(answer)) {
						game.correct();
						categoryCount[categoryNum]++;
					} else
						game.incorrect();

				}

			replay = game.askForText("Play again? (Y/N)"); 

			while(replay != null && !replay.equalsIgnoreCase("Y") && !replay.equalsIgnoreCase("N"))
				replay = game.askForText("Invalid input. Play again? (Y/N)");

		} while(replay.toUpperCase().equals("Y"));

		System.exit(1);
		
	}

	public static void shuffleQuestions() {

		for (int r = 0; r < questions.length; r++) {

			int[] memory = new int[questions[r].length];

			for (int i = 0; i < memory.length; i++)
				memory[i] = -1;


			for (int c = 0; c < questions[r].length; c++) {
				int random = (int) (Math.random() * questions.length);

				if (isInArray(c, memory))
					continue;

				memory[c] = random;
				
				String qTemp = questions[r][c];
				questions[r][c] = questions[r][random];
				questions[r][random] = qTemp;
				
				String aTemp = answers[c + (r * questions.length)];
				answers[c + (r * questions.length)] = answers[random + (r * questions.length)];
				answers[random + (r * questions.length)] = aTemp;
			}

		}

	}

	public static boolean isInArray(int num, int[] arr) {

		for (int i = 0; i < arr.length; i++) {

			if (num == arr[i])
				return true;

		}

		return false;
	}

}
