package org.jmc.dictionary.Game;

import org.jmc.dictionary.Entities.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordChainGame {
    private String computerWord;
    private String playerWord;
    private List<String> appearedWordList;
    private List<Word> searchWordList;

    public WordChainGame() {
        this.appearedWordList = new ArrayList<>();
        this.searchWordList = new ArrayList<>();
    }

    public static void startGame(List<Word> wordList, Scanner scanner) {
        WordChainGame wordChainGame = new WordChainGame();
        Random random = new Random();
        int index = random.nextInt(wordList.size());

        wordChainGame.setComputerWord(wordList.get(index).getWord_target());
        String computer = wordChainGame.getComputerWord();

        System.out.println("\nWord Chain Game: " + "\033[3m" +"Say words that start with the last letter of the previous word.");
        System.out.println("     Don't repeat or use invalid words. Keep the chain alive to win!" + "\033[0m");

        String player;
        scanner.nextLine();
        boolean running = true;

        while (running) {
            System.out.println("Computer word: " + "\u001B[31m" + computer + "\u001B[0m");
            System.out.print("Player word: ");
            player = scanner.nextLine().toLowerCase();

            while (wordChainGame.exitInAppearedWordList(player)) {
                System.out.println("\u001B[31m" + "This word has been used before, please enter new word" + "\u001B[0m");
                System.out.print("Player word: ");
                player = scanner.nextLine().toLowerCase();
            }

            if (!player.isEmpty()) {
                if (player.charAt(0) == computer.charAt(computer.length() - 1) && wordChainGame.exitWordList(wordList, player)){
                    wordChainGame.setPlayerWord(player);
                } else {
                    running = false;
                    System.out.println("Not correct, You lose.");
                }

                char prefix = player.charAt(player.length() - 1);
                wordChainGame.searchWordWithPrefix(wordList, prefix);
                if (wordChainGame.getSearchWordList().isEmpty()) {
                    System.out.println("There are no suitable words in Dictionary. You Win!");
                    running = false;
                } else {
                    int reply = random.nextInt(wordChainGame.getSearchWordList().size());
                    computer = wordChainGame.getSearchWordList().get(reply).getWord_target();

                    while (wordChainGame.exitInAppearedWordList(computer)) {
                        wordChainGame.removeFromSearchWord(computer);
                        reply = random.nextInt(wordChainGame.getSearchWordList().size());
                        computer = wordChainGame.getSearchWordList().get(reply).getWord_target();
                    }
                    wordChainGame.setComputerWord(computer);
                }
            } else {
                System.err.println("please ENTER a word!");
            }
        }
    }

    public void searchWordWithPrefix(List<Word> wordList, char c) {
        searchWordList.clear();
        String prefix = String.valueOf(c);
        for (Word word : wordList) {
            if (word.getWord_target().startsWith(prefix)) {
                addToSearchWord(word);
            }
        }
    }

    public boolean exitInAppearedWordList(String wordTarget) {
        for (String word : appearedWordList) {
            if (word.equals(wordTarget)) {
                return true;
            }
        }
        return false;
    }

    public boolean exitWordList(List<Word> wordList, String wordTarget) {
        for (Word word : wordList ) {
            if (word.getWord_target().equals(wordTarget)) {
                return true;
            }
        }
        return false;
    }

    public String getComputerWord() {
        return computerWord;
    }

    public void setComputerWord(String computerWord) {
        this.computerWord = computerWord;
        addToAppearedWord(computerWord);
    }

    public String getPlayerWord() {
        return playerWord;
    }

    public void setPlayerWord(String playerWord) {
        this.playerWord = playerWord;
        addToAppearedWord(playerWord);
    }

    public List<String> getAppearedWordList() {
        return appearedWordList;
    }

    public void setAppearedWordList(List<String> appearedWordList) {
        this.appearedWordList = appearedWordList;
    }

    public List<Word> getSearchWordList() {
        return searchWordList;
    }

    public void setSearchWordList(List<Word> searchWordList) {
        this.searchWordList = searchWordList;
    }

    public void addToAppearedWord(String appearedWord) {
        this.appearedWordList.add(appearedWord);
    }

    public void addToSearchWord(Word word) {
        this.searchWordList.add(word);
    }

    public void removeFromSearchWord(String removeWord) {
        this.searchWordList.removeIf(word -> word.getWord_target().equals(removeWord));
    }
}
