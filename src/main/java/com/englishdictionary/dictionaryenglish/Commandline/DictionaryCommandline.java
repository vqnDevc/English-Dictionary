package com.englishdictionary.dictionaryenglish.Commandline;

import com.englishdictionary.dictionaryenglish.Entities.Word;
import com.englishdictionary.dictionaryenglish.Management.DictionaryManagement;

import java.io.IOException;
import java.util.InputMismatchException;

public class DictionaryCommandline extends DictionaryManagement {

    public DictionaryCommandline() {
        super();
    }

    private void showAllWords() {
        System.out.println();
        System.out.printf("%-4s | %-18s | %-20s%n", "No", "English", "Vietnamese");

        for (Word word : getDictionary().getWordList()) {
            System.out.printf(
                    "%-4s | %-18s | %-20s%n",
                    getDictionary().getIndex(word) + 1,
                    word.getWord_target(),
                    word.getWord_explain());
        }
    }


    private void interfaceMenu() {
        System.out.println("Welcome to My Application!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Update");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Game");
        System.out.println("[8] Import from file");
        System.out.println("[9] Export to file");

    }

    public void dictionaryAdvanced() {
        boolean running = true;
        interfaceMenu();

        while (running) {

            System.out.print("Your action: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.err.println("PLEASE ENTER AN INTEGER!");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                case 1:
                    insertFromCommandline();
                    break;
                case 2:
                    removeWord();
                    break;
                case 3:
                    updateWord();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    lookUpWord();
                    break;
                case 6:
                    searchWord();
                    break;
                case 7:
                    if (getDictionary().getWordList().isEmpty()) {
                        System.out.println("The Dictionary has no words, please add or import words to Dictionary.");
                    } else {
                        GameCommandline.startGame(getDictionary().getWordList(), scanner);
                        interfaceMenu();
                    }
                    break;
                case 8:
                    insertFromFile(DictionaryManagement.DICTIONARY_INPUT_FILE_PATH);
                    break;
                case 9:
                   exportFile();
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
        scanner.close();
    }
}
