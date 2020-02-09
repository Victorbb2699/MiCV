package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.dialogs.ConocimientoDialog;
import dad.javafx.micv.dialogs.IdiomaDialog;
import dad.javafx.micv.model.CV;
import dad.javafx.micv.model.Conocimiento;
import dad.javafx.micv.model.Idioma;
import dad.javafx.micv.model.Nivel;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

public class ConocimientosController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private TableView<Conocimiento> tableView;

	@FXML
	private TableColumn<Conocimiento, String> denomColumn;

	@FXML
	private TableColumn<Conocimiento, Nivel> nivelColumn;

	@FXML
	private Button anadirconoButton;

	@FXML
	private Button anadiridiomButton;

	@FXML
	private Button eliminarButton;
	
	//model
	
	CV model = new CV();
	
	public CV getModel() {
		return model;
	}

	public AnchorPane getView() {
		return root;
	}

	public ConocimientosController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindeos
		tableView.itemsProperty().bindBidirectional(model.habilidadesProperty());

		eliminarButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());

		// values factories
		denomColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivelColumn.setCellValueFactory(v -> v.getValue().nivelProperty());

		// cell factories
		denomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nivelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));
		
	}

	@FXML
	void onAnadirconoAction(ActionEvent event) {
		ConocimientoDialog dialog = new ConocimientoDialog();
		Optional<Conocimiento> result = dialog.showAndWait();
		try {
			model.getHabilidades().add(result.get());
		} catch (NoSuchElementException e) {
		}
		
	}

	@FXML
	void onAnadiridiomAction(ActionEvent event) {
		IdiomaDialog dialog = new IdiomaDialog();
		Optional<Idioma> result = dialog.showAndWait();
		try {
			model.getHabilidades().add(result.get());
		} catch (NoSuchElementException e) {
		}

	}

	@FXML
	void onEliminarAction(ActionEvent event) {
		model.getHabilidades().remove(tableView.getSelectionModel().getSelectedIndex());
	}

}
