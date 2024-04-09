package com.englishdictionary.dictionaryenglish.Game;

import com.englishdictionary.dictionaryenglish.Entities.Word;

import java.util.*;

public class ShuffleCharactersGame {
    private String correctAnswer;
    public static void startGame(List<Word> wordList, Scanner scanner) {
        ShuffleCharactersGame shuffleGame = new ShuffleCharactersGame();
        Random random = new Random();
        int index = random.nextInt(wordList.size());

        shuffleGame.correctAnswer = wordList.get(index).getWord_target();
        String explain = wordList.get(index).getWord_explain();

        printCharacters(shuffleGame.shuffleString(shuffleGame.correctAnswer));
        System.out.print("Your answer: ");
        scanner.nextLine();

        int times = 5;

        while (times > 0) {
            String answer = scanner.nextLine().toLowerCase();

            if (answer.equals(shuffleGame.correctAnswer)) {
                System.out.println("Correct!");
                break;
            } else {
                --times;
                if (times > 0)
                    System.err.print("Incorrect! Try again: ");

                if (times == 0) {
                    System.out.println("\nThe correct word is '" + "\u001B[36m"
                            + shuffleGame.correctAnswer + "\u001B[0m" + "'");
                    System.out.println("You have failed!");
                }
            }
        }

    }

    private String shuffleString(String wordTarget) {
        List<Character> characters = new ArrayList<>();

        for (char c : wordTarget.toCharArray()) {
            characters.add(c);
        }

        Collections.shuffle(characters);
        StringBuilder shuffledString = new StringBuilder();

        for (char c : characters) {
            shuffledString.append(c);
        }

        return shuffledString.toString();
    }

    private static void printCharacters(String word) {
        StringBuilder shuffle = new StringBuilder();
        shuffle.append("\n");
        shuffle.append("\033[3m" + "Rearrange characters into complete words" + "\033[0m");
        shuffle.append("\n");
        for (char c : word.toCharArray()) {
            shuffle.append(c).append("/");
        }
        shuffle.deleteCharAt(shuffle.length() - 1);
        System.out.println(shuffle.toString());
    }
}
