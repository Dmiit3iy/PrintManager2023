package org.dmiitr3iy.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;
import org.dmiitr3iy.model.Document;
import org.dmiitr3iy.model.Size;
import org.dmiitr3iy.model.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PrintServiceTest {
    @SneakyThrows
    @org.junit.jupiter.api.Test
    void avgPrintTime() {
        ArrayList<Document> documentsArrayList = new ArrayList<>();
        ArrayList<Document> printedDocumentsArrayList = new ArrayList<>();
        Document document= new Document(Size.A0, Type.JPG,1);
        Document document2= new Document(Size.A0, Type.JPG,2);
        printedDocumentsArrayList.add(document);
        printedDocumentsArrayList.add(document2);
        ObservableList<Document> listViewDocumentsOL = FXCollections.observableArrayList(documentsArrayList);
        ObservableList<Document> listViewPrintedDocumentsOL = FXCollections.observableArrayList(printedDocumentsArrayList);
        PrintService printService = new PrintService(listViewDocumentsOL,listViewPrintedDocumentsOL);
        double expected = 1.5;
        double actual = printService.avgPrintTime();
        assertEquals(expected,actual);
    }

}