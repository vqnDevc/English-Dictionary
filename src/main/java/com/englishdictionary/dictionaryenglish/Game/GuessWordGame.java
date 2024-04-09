package com.englishdictionary.dictionaryenglish.Game;

import com.englishdictionary.dictionaryenglish.Entities.Word;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GuessWordGame {
    private String correctAnswer;
    public static void startGame(List<Word> wordList, Scanner scanner) {
        GuessWordGame guessWordGame = new GuessWordGame();
        Random random = new Random();
        int index =random.nextInt(wordList.size());

        guessWordGame.correctAnswer = wordList.get(index).getWord_target();
        char prefix = guessWordGame.correctAnswer.charAt(0);
        int lengthCorrectAnswer = guessWordGame.correctAnswer.length();
        String explain = wordList.get(index).getWord_explain();

        System.out.println("\033[3m" + "\nThis word has " + lengthCorrectAnswer + " characters. Start with '" + prefix +"'" + "\033[0m");
        System.out.println("Word meaning: " + explain);
        System.out.print("Your answer: ");
        scanner.nextLine();

        int times = 5;
        String answer = scanner.nextLine().toLowerCase();
        while (times > 0) {
            if (answer.length() != lengthCorrectAnswer) {
                times--;
                if (times > 0) {
                    System.out.println("\u001B[31m" + "The length of the string is invalid !" + "\u001B[0m");
                    System.out.print("You have " + times + " turns left. ");
                    System.out.print("Your answer: ");
                    answer = scanner.nextLine().toLowerCase();
                } else {
                    System.out.println("Incorrect! Your turn is over.");
                }

            } else if (answer.equals(guessWordGame.correctAnswer)) {
                System.out.println("Correct!");
                break;
            } else {
                printErrorCharacter(guessWordGame.correctAnswer, answer);
                --times;
                if (times > 0) {
                    System.out.println("Incorrect! Try again, you have " + times + " turns left.");
                    System.out.print("Your answer: ");
                    answer = scanner.nextLine().toLowerCase();
                } else {
                    System.out.println("Incorrect! Your turn is over.");
                }
            }

            if (times == 0) {
                System.out.println("\nThe correct word is '" + "\u001B[36m"
                        + guessWordGame.correctAnswer + "\u001B[0m" + "'");
                System.out.println("You have failed!");
            }
        }
    }

    private static void printErrorCharacter(String correctAnswer, String answer) {
        for (int i = 0; i < correctAnswer.length(); i++) {
            if (answer.charAt(i) != correctAnswer.charAt(i)) {
                System.out.print("\u001B[31m" + answer.charAt(i) + "\u001B[0m");
            } else {
                System.out.print(answer.charAt(i));
            }
        }
        System.out.println();
    }

}
