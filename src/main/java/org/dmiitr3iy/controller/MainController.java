package org.dmiitr3iy.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import org.dmiitr3iy.model.Document;
import org.dmiitr3iy.model.Size;
import org.dmiitr3iy.model.Type;
import org.dmiitr3iy.service.PrintService;

import java.util.ArrayList;
import java.util.Collections;

public class MainController {
    @FXML
    public ListView<Document> listViewDocuments = new ListView<>(FXCollections.emptyObservableList());
    @FXML
    public ListView<Document> listViewPrintedDocuments = new ListView<>(FXCollections.emptyObservableList());;
    @FXML
    public ComboBox<Size> sizeDocumentComboBox;
    @FXML
    public ComboBox<Type> typeDocumentComboBox;
    @FXML
    public TextField printTimeTextArea;
    private PrintService printService = new PrintService(listViewPrintedDocuments, listViewDocuments);

    @FXML
    void initialize() {
        ObservableList<Size> sizeObservableList = FXCollections.observableArrayList(Size.A0, Size.A1, Size.A2, Size.A3);
        this.sizeDocumentComboBox.setItems(sizeObservableList);
        ObservableList<Type> typeObservableList = FXCollections.observableArrayList(Type.JPG, Type.PDF, Type.TXT);
        this.typeDocumentComboBox.setItems(typeObservableList);

    }


    public void printButton(ActionEvent actionEvent) {
        long time = Long.parseLong(this.printTimeTextArea.getText());
        Size size = this.sizeDocumentComboBox.getSelectionModel().getSelectedItem();
        Type type = this.typeDocumentComboBox.getSelectionModel().getSelectedItem();
        Document document = new Document(size, type, time);
        try {
            printService.addDocument(document);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> printService.stop();

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }




}
