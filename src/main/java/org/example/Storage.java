package org.example;

import java.util.ArrayList;

public class Storage {
    private ArrayList<String> quoteList;
    Storage()
    {
        quoteList = new ArrayList<>();
        quoteList.add("ауау иу");
        quoteList.add("опоп ау");
        quoteList.add("гопгоп вау");
    }

    String getRandQuote()
    {
        int randValue = (int)(Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}
