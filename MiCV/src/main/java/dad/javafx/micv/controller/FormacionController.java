package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.dialogs.FormacionDialog;
import dad.javafx.micv.model.CV;
import dad.javafx.micv.model.Titulo;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.LocalDateStringConverter;

public class FormacionController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private TableView<Titulo> tableView;

	@FXML
	private TableColumn<Titulo, LocalDate> desdeColumn;

	@FXML
	private TableColumn<Titulo, LocalDate> hastaColumn;

	@FXML
	private TableColumn<Titulo, String> denomColumn;

	@FXML
	private TableColumn<Titulo, String> orgColumn;

	@FXML
	private Button anadirButton;

	@FXML
	private Button quitarButton;

	// model

	CV model = new CV();
	
	public CV getModel() {
		return model;
	}

	public AnchorPane getView() {
		return root;
	}

	public FormacionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindeos
		tableView.itemsProperty().bindBidirectional(model.formacionProperty());

		quitarButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

		// values factories
		desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v -> v.getValue().hastaProperty());
		denomColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		orgColumn.setCellValueFactory(v -> v.getValue().organizadorProperty());

		// cell factories
		desdeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		orgColumn.setCellFactory(TextFieldTableCell.forTableColumn());


	}

	@FXML
	void onAnadirAction(ActionEvent event) {
		FormacionDialog dialog = new FormacionDialog();
		Optional<Titulo> result = dialog.showAndWait();
		try {
			model.getFormacion().add(result.get());
		} catch (NoSuchElementException e) {
		}	
	}

	@FXML
	void onQuitarAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar formación");
		alert.setContentText("¿Desea borrar esta formación?");
		Optional<ButtonType> a = alert.showAndWait();
		if (a.get().getText().equals("Aceptar")) {
			model.getFormacion().remove(tableView.getSelectionModel().getSelectedIndex());
		}
	}

}
