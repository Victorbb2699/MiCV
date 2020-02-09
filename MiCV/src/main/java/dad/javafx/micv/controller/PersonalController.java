package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.model.CV;
import dad.javafx.micv.model.Nacionalidad;
import dad.javafx.micv.model.Personal;
import dad.javafx.utils.Utils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class PersonalController implements Initializable {

	// vista

	@FXML
	private BorderPane root;

	@FXML
	private TextField dniTextFeld;

	@FXML
	private TextField nombreTextField;

	@FXML
	private TextField apellidosTextField;

	@FXML
	private TextField codpostalTextField;

	@FXML
	private TextField localidadTextField;

	@FXML
	private DatePicker fechanacDate;

	@FXML
	private TextArea direccionTextArea;

	@FXML
	private ComboBox<String> paisComboBox;

	@FXML
	private ListView<Nacionalidad> nacionalidadListView;

	@FXML
	private Button anadirButton;

	@FXML
	private Button quitarButton;

	// modelo

	CV model = new CV();
	
	public CV getModel() {
		return model;
	}

	public PersonalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public BorderPane getView() {
		return root;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model.setPersonal(new Personal());

		paisComboBox.getItems().addAll(Utils.cargarPaises());
		
		quitarButton.disableProperty().bind(nacionalidadListView.getSelectionModel().selectedItemProperty().isNull());


		model.getPersonal().identificacionProperty().bindBidirectional(dniTextFeld.textProperty());
		model.getPersonal().nombreProperty().bindBidirectional(nombreTextField.textProperty());
		model.getPersonal().apellidosProperty().bindBidirectional(apellidosTextField.textProperty());
		model.getPersonal().fechaNacimientoProperty().bindBidirectional(fechanacDate.valueProperty());
		model.getPersonal().direccionProperty().bindBidirectional(direccionTextArea.textProperty());
		model.getPersonal().codigoPostalProperty().bindBidirectional(codpostalTextField.textProperty());
		model.getPersonal().localidadProperty().bindBidirectional(localidadTextField.textProperty());
		model.getPersonal().paisProperty().bindBidirectional(paisComboBox.valueProperty());
		model.getPersonal().nacionalidadesProperty().bindBidirectional(nacionalidadListView.itemsProperty());

	}

	@FXML
	void onAnadirNacButton(ActionEvent event) {

		List<String> aux = new ArrayList<>();
		aux.addAll(Utils.cargarNacionalidades());
		
		ChoiceDialog<String> dialog = new ChoiceDialog<>(aux.get(0),aux);
		dialog.setTitle("MiCV");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Elige una nacionalidad");

		Optional<String> result = dialog.showAndWait();
		Nacionalidad aux2 = new Nacionalidad();
		aux2.setNacionalidad(result.get());

		if(!model.getPersonal().getNacionalidades().contains(aux2)) {
			model.getPersonal().getNacionalidades().add(aux2);
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("MiCV");
			alert.setContentText("Ya has añadido esa nacionalidad");
			alert.showAndWait();
		}
		
		
	}

	@FXML
	void onQuitarNacButton(ActionEvent event) {
		
		model.getPersonal().getNacionalidades().remove(nacionalidadListView.getSelectionModel().getSelectedItem());

	}
	
}
