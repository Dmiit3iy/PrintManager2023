package org.dmiitr3iy.service;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmiitr3iy.App;
import org.dmiitr3iy.controller.SecondController;
import org.dmiitr3iy.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PrintService {
    private static final Logger logger = LoggerFactory.getLogger(PrintService.class);
    private Object lock = new Object();
    private BlockingQueue<Document> documentsQueue = new LinkedBlockingQueue<>();
    private ObservableList<Document> listViewDocumentsOL;
    private ObservableList<Document> listViewPrintedDocumentsOL;


    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    Document document = documentsQueue.take();
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
            } catch (InterruptedException ignored) {
            }
        }
    });


    public PrintService(ObservableList<Document> listViewDocumentsOL, ObservableList<Document> listViewPrintedDocumentsOL) {
        this.listViewDocumentsOL = listViewDocumentsOL;
        this.listViewPrintedDocumentsOL = listViewPrintedDocumentsOL;
        this.thread.start();
    }

    /**
     * Остановка диспетчера. Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов.
     *
     * @throws IOException
     */
    public void stop() throws IOException {
        logger.info("Запуск  метода остановки диспетчера печати");
        this.thread.interrupt();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("second.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        SecondController controller = loader.getController();
        controller.initData(listViewPrintedDocumentsOL);
        stage.show();
    }

    /**
     * Метод для принятия документа на печать
     *
     * @param document
     * @throws InterruptedException
     */
    public void addDocument(Document document) throws InterruptedException {
        logger.info("Запуск  метода отправки на печать");
        documentsQueue.put(document);
    }

    /**
     * Метод для отмены печатающегося документа
     */
    public void cancelPrinting() {
        logger.info("Запуск  метода отмены печатающегося документа");
        synchronized (lock) {
            lock.notify();
        }
    }

    /**
     * Метод для расчета среднего времени печати
     * @return
     */
    public double avgPrintTime() {
        logger.info("Запуск  метода подсчета среднего времени печати");
        return listViewPrintedDocumentsOL.stream().mapToLong(e -> e.getPrintTime()).average().orElse(0);
    }
}
