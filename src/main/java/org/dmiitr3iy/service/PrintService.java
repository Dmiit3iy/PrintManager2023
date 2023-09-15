package org.dmiitr3iy.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.dmiitr3iy.model.Document;

import java.util.concurrent.LinkedBlockingQueue;

public class PrintService {
    private LinkedBlockingQueue<Document> documents = new LinkedBlockingQueue<>();
    //TODO попробовать передавать не вьюху а обсрвал листт
    public ListView<Document> listViewPrintedDocuments;

   public ListView<Document> listViewDocuments;


    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    Document document = documents.take();
                    System.out.println("Получил из очереди докуменот");
                    Thread.sleep(document.getPrintTime()*1000);

                    //TODO так как добавлять в графические элементы может только JavaFX
                    // поток то тут я его и создаю чтобы добавить распечатанный жокумент
                    Platform.runLater(() -> {
                        ObservableList<Document> documents1 = FXCollections.observableArrayList(document);
                        System.out.println("В платформере "+documents1);

                    });

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    });


    public PrintService(ListView<Document> listViewPrintedDocuments, ListView<Document> listViewDocuments) {
        this.listViewPrintedDocuments = listViewPrintedDocuments;
        this.listViewDocuments = listViewDocuments;
        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();
    }

    public void addDocument(Document document) throws InterruptedException {
        documents.put(document);
    }
}
