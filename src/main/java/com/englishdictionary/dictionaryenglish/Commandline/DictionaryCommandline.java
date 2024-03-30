package com.englishdictionary.dictionaryenglish.Commandline;

import com.englishdictionary.dictionaryenglish.Entities.Word;
import com.englishdictionary.dictionaryenglish.Management.DictionaryManagement;

import java.util.InputMismatchException;

public class DictionaryCommandline extends DictionaryManagement {

    public DictionaryCommandline() {
        super();
    }

    public void showAllWords() {
        System.out.println();
        System.out.printf("%-4s | %-18s | %-20s%n", "No", "English", "Vietnamese");

        for (Word word : dictionary.getWordList()) {
            System.out.printf(
                    "%-4s | %-18s | %-20s%n",
                    dictionary.getIndex(word) + 1,
                    word.getWord_target(),
                    word.getWord_explain());
        }
    }

    public void interfaceMenu() {
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

            String wordTarget = "";
            String wordExplain = "";
            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                case 1:
                    System.out.println("ADD: Enter new word target: ");
                    wordTarget = scanner.nextLine();

                    if (wordExit(wordTarget)) {
                        System.out.println("This word is already exited!");
                    } else {
                        System.out.println("ADD: Enter word explain: ");
                        wordExplain = scanner.nextLine();
                        addToDictionary(wordTarget, wordExplain);
                        System.out.println("This word was added to Dictionary.\n");
                    }
                    break;
                case 2:
                    System.out.println("REMOVE: Enter word target to remove: ");
                     wordTarget = scanner.nextLine();

                     if (wordExit(wordTarget)) {
                         removeFromDictionary(wordTarget);
                         System.out.println("This word are completely removed.\n");
                     } else {
                         System.out.println("This word is not exit.\n");
                     }
                    break;
                case 3:
                    System.out.println("UPDATE: Enter word target to update: ");
                    wordTarget = scanner.nextLine();

                    if (wordExit(wordTarget)) {
                        System.out.println("UPDATE: Enter new word explain of this word: ");
                        wordExplain = scanner.nextLine();
                        updateWordInDictionary(wordTarget, wordExplain);
                        System.out.println("Update completed.\n");
                    }
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    System.out.println("LOOKUP: Enter your word target: ");
                    wordTarget = scanner.nextLine();
                    dictionaryLookup(wordTarget);
                    break;
                case 6:
                    System.out.println("Search functionality not implemented yet.");
                    break;
                case 7:
                    System.out.println("Game functionality not implemented yet.");
                    break;
                case 8:
                    System.out.println("Import from file functionality not implemented yet.");
                    break;
                case 9:
                    System.out.println("Export to file functionality not implemented yet.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
        scanner.close();
    }
}
