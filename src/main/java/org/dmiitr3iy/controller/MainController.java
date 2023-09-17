package org.dmiitr3iy.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import lombok.NonNull;
import org.dmiitr3iy.App;
import org.dmiitr3iy.model.Document;
import org.dmiitr3iy.model.Size;
import org.dmiitr3iy.model.Type;
import org.dmiitr3iy.service.PrintService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML
    public TextField avgTimeTextField;
    private ArrayList<Document> documentsArrayList = new ArrayList<>();
    private ArrayList<Document> printedDocumentsArrayList = new ArrayList<>();
    private ObservableList<Document> listViewDocumentsOL;
    private ObservableList<Document> listViewPrintedDocumentsOL;
    @FXML
    public ListView<Document> listViewDocuments;
    @FXML
    public ListView<Document> listViewPrintedDocuments;
    @FXML
    public ComboBox<Size> sizeDocumentComboBox;
    @FXML
    public ComboBox<Type> typeDocumentComboBox;
    @FXML
    public TextField printTimeTextArea;
    private PrintService printService;

    @FXML
    void initialize() {
        ObservableList<Size> sizeObservableList = FXCollections.observableArrayList(Size.A0, Size.A1, Size.A2, Size.A3);
        this.sizeDocumentComboBox.setItems(sizeObservableList);
        this.sizeDocumentComboBox.getSelectionModel().selectFirst();
        ObservableList<Type> typeObservableList = FXCollections.observableArrayList(Type.JPG, Type.PDF, Type.TXT);
        this.typeDocumentComboBox.setItems(typeObservableList);
        this.typeDocumentComboBox.getSelectionModel().selectFirst();

        listViewDocumentsOL = FXCollections.observableArrayList(documentsArrayList);
        listViewPrintedDocumentsOL = FXCollections.observableArrayList(printedDocumentsArrayList);

        this.listViewDocuments.setItems(listViewDocumentsOL);
        printService = new PrintService(listViewDocumentsOL, listViewPrintedDocumentsOL);
    }


    public void printButton(ActionEvent actionEvent) {
        String time = printTimeTextArea.getText();
        if (time.matches("\\d")) {

            Size size = this.sizeDocumentComboBox.getSelectionModel().getSelectedItem();
            Type type = this.typeDocumentComboBox.getSelectionModel().getSelectedItem();

            Document document = new Document(size, type, Long.parseLong(time));


            listViewDocumentsOL.add(document);

            try {
                printService.addDocument(document);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else App.showMessage("Ошибка", "Введите время печати в цифрах", Alert.AlertType.ERROR);
        printTimeTextArea.clear();

    }

    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        try {
            printService.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }


    public void cancelPrintButton(ActionEvent actionEvent) {
        printService.cancelPrinting();
    }


    public void avgTimeButton(ActionEvent actionEvent) {
        String msg = String.valueOf(printService.avgPrintTime()) + " секунд(ы)";
        App.showMessage("Среднее время печати", msg, Alert.AlertType.INFORMATION);
    }

    public void sortByTypeButton(ActionEvent actionEvent) throws IOException {
        ObservableList<Document> sortedList = FXCollections.observableArrayList(listViewPrintedDocumentsOL);
        Collections.sort(sortedList, new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                return o1.getType().toString().compareTo(o2.getType().toString());
            }
        });
        loadNewScene(sortedList);
    }

    public void sortByTimeButton(ActionEvent actionEvent) throws IOException {
        ObservableList<Document> sortedList = FXCollections.observableArrayList(listViewPrintedDocumentsOL);
        Collections.sort(sortedList, new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                return Long.compare(o1.getPrintTime(), o2.getPrintTime());
            }
        });

        loadNewScene(sortedList);
    }

    public void sortBySizeButton(ActionEvent actionEvent) throws IOException {
        ObservableList<Document> sortedList = FXCollections.observableArrayList(listViewPrintedDocumentsOL);
        Collections.sort(sortedList, new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                return o1.getSize().compareTo(o2.getSize());
            }
        });
        loadNewScene(sortedList);
    }

    public void noSortButton(ActionEvent actionEvent) throws IOException {
        loadNewScene(listViewPrintedDocumentsOL);
    }

    public void loadNewScene(ObservableList<Document> sortedList) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("second.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        SecondController controller = loader.getController();
        controller.initData(sortedList);
        stage.show();
    }
}
