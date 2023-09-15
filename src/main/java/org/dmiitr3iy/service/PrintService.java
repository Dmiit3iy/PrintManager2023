package org.dmiitr3iy.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.dmiitr3iy.App;
import org.dmiitr3iy.controller.MainController;
import org.dmiitr3iy.model.Document;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class PrintService {
    private LinkedBlockingQueue<Document> documents = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<Document> getDocuments() {
        return documents;
    }

    public ListView<Document> listViewPrintedDocuments;

    ObservableList<Document> observableList = FXCollections.observableArrayList();
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    Document document = documents.take();
                    Thread.sleep(document.getPrintTime());
                    //observableList = listViewPrintedDocuments.getItems();

                    observableList.add(document);
                    System.out.println(observableList);
                    ///
                    listViewPrintedDocuments.setItems(observableList);
                    System.out.println(listViewPrintedDocuments);
                    listViewPrintedDocuments.refresh();

                    //TODO так как добавлять в графические элементы может только JavaFX
                    // поток то тут я его и создаю чтобы добавить распечатанный жокумент
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            listViewPrintedDocuments.setItems(observableList);
                            System.out.println(listViewPrintedDocuments);
                            //listViewPrintedDocuments.refresh();

                        }
                    });

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    });


    public PrintService(ListView<Document> listViewPrintedDocuments) {
        this.listViewPrintedDocuments = listViewPrintedDocuments;
        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();
    }

    public void add(Document document) throws InterruptedException {
        documents.put(document);
    }
}
