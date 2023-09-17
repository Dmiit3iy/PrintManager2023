package org.dmiitr3iy.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.dmiitr3iy.model.Document;

public class SecondController {
    public ListView printedDocumentsListView;
    public void initData(ObservableList<Document> listViewPrintedDocumentsOL) {
        this.printedDocumentsListView.setItems(listViewPrintedDocumentsOL);
    }
}
