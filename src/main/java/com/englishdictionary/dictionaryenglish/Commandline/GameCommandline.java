package com.englishdictionary.dictionaryenglish.Commandline;

import com.englishdictionary.dictionaryenglish.Entities.Word;
import com.englishdictionary.dictionaryenglish.Game.GuessWordGame;
import com.englishdictionary.dictionaryenglish.Game.ShuffleCharactersGame;
import com.englishdictionary.dictionaryenglish.Game.WordChainGame;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameCommandline {
    public static void menuGame() {
        System.out.println("\nWelcome to Dictionary Game CommandLine!");
        System.out.println("[1] Shuffle Characters Game");
        System.out.println("[2] Guess Word Game");
        System.out.println("[3] Word Chain Game");
        System.out.println("[0] Exit");
    }

    public static void startGame(List<Word> wordList, Scanner scanner) {
        boolean running = true;
        menuGame();

        while (running) {
            System.out.print("Your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("PLEASE ENTER AN INTEGER!");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting Game...\n");
                    running = false;
                    break;
                case 1:
                    ShuffleCharactersGame.startGame(wordList, scanner);
                    menuGame();
                    break;
                case 2:
                    GuessWordGame.startGame(wordList, scanner);
                    menuGame();
                    break;
                case 3:
                    WordChainGame.startGame(wordList, scanner);
                    menuGame();
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
    }
}
