package org.dmiitr3iy.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.dmiitr3iy.App;
import org.dmiitr3iy.model.Document;

import java.util.concurrent.LinkedBlockingQueue;

public class PrintService {
    private Object lock = new Object();
    private LinkedBlockingQueue<Document> documents = new LinkedBlockingQueue<>();
    private ObservableList<Document> listViewDocumentsOL;
    private ObservableList<Document> listViewPrintedDocumentsOL;


    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    Document document = documents.take();
                    synchronized (lock) {
                        long t1 = System.currentTimeMillis();
                        lock.wait(document.getPrintTime() * 1000);
                        long t2 = System.currentTimeMillis() - t1;
                        if (t2 < (document.getPrintTime() * 1000)) {
                            Platform.runLater(() -> {
                                listViewDocumentsOL.remove(document);
                                App.showMessage("отмена печати", "Документ: " + document + " снят с печати", Alert.AlertType.INFORMATION);
                            });

                        } else {
                            Platform.runLater(() -> {
                                listViewDocumentsOL.remove(document);
                                listViewPrintedDocumentsOL.add(document);
                            });
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });


    public PrintService(ObservableList<Document> listViewDocumentsOL, ObservableList<Document> listViewPrintedDocumentsOL) {
        this.listViewDocumentsOL = listViewDocumentsOL;
        this.listViewPrintedDocumentsOL = listViewPrintedDocumentsOL;
        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();
    }

    public void addDocument(Document document) throws InterruptedException {
        documents.put(document);
    }

    public void cancelPrinting() {
        synchronized (lock) {
            lock.notify();
        }
    }
}
