package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.dialogs.ExperienciaDialog;
import dad.javafx.micv.model.CV;
import dad.javafx.micv.model.Experiencia;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.LocalDateStringConverter;

public class ExperienciaController implements Initializable {

	// view

	@FXML
	private AnchorPane root;

	@FXML
	private TableView<Experiencia> tableView;

	@FXML
	private TableColumn<Experiencia, LocalDate> desdeColumn;

	@FXML
	private TableColumn<Experiencia, LocalDate> hastaColumn;

	@FXML
	private TableColumn<Experiencia, String> denomColumn;

	@FXML
	private TableColumn<Experiencia, String> empleColumn;

	@FXML
	private Button anadirButton;

	@FXML
	private Button eliminarButton;

	// model

	CV model = new CV();
	
	public CV getModel() {
		return model;
	}

	public AnchorPane getView() {
		return root;
	}

	public ExperienciaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindeos

		tableView.itemsProperty().bindBidirectional(model.experienciasProperty());

		eliminarButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

		// values factories
		desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v -> v.getValue().hastaProperty());
		denomColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleColumn.setCellValueFactory(v -> v.getValue().empleadorProperty());

		// cell factories
		desdeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		empleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	@FXML
	void onAnadirAction(ActionEvent event) {
		ExperienciaDialog dialog = new ExperienciaDialog();
		Optional<Experiencia> result = dialog.showAndWait();
		try {
			model.getExperiencias().add(result.get());
		} catch (NoSuchElementException e) {
		}

	}

	@FXML
	void onEliminarAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar experiencia");
		alert.setContentText("¿Desea borrar esta experiencia?");
		Optional<ButtonType> a = alert.showAndWait();
		if (a.get().getText().equals("Aceptar")) {
			model.getExperiencias().remove(tableView.getSelectionModel().getSelectedIndex());
		}
	}
}
