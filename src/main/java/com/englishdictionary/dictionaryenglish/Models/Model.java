package com.englishdictionary.dictionaryenglish.Models;

import com.englishdictionary.dictionaryenglish.Views.ViewFactory;

public class Model {
    public static Model model;

    private final ViewFactory viewFactory;

    private Model() {
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
