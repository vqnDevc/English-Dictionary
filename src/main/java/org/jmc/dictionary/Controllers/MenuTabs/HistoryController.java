package org.jmc.dictionary.Controllers.MenuTabs;

import org.jmc.dictionary.DictionaryApp;
import org.jmc.dictionary.Entities.Word;
import org.jmc.dictionary.Management.FavoriteWords;
import org.jmc.dictionary.Management.SearchHistory;

public class HistoryController extends SearcherController {
    @Override
    public void updateAutocompleteList() {
        autocompleteWordList.clear();
        autocompleteWordList.addAll(SearchHistory.getSearchHistory().reversed());
        autocompleteList.setItems(autocompleteWordList);
    }

    @Override
    public void onAutocompleteListClick() {
        String target = autocompleteList.getSelectionModel().getSelectedItem();
        if (target == null) {
            return;
        }

        if (FavoriteWords.inFavorites(target)) {
            favoriteButton.setImage(starFilledImage);
        } else {
            favoriteButton.setImage(starImage);
        }

        Word word = DictionaryApp.dictionary.dictionaryAppLookup(target);

        wordTargetLabel.setText(word.getWord_target());

        wordExplainView.getEngine().loadContent(word.getWord_explain(), "text/html");
    }
}
