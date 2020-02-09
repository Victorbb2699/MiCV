package dad.javafx.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Conocimiento;
import dad.javafx.micv.model.Nivel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class ConocimientoDialog extends Dialog<Conocimiento> implements Initializable {

	private ButtonType CREAR_BUTTON_TYPE = new ButtonType("Crear", ButtonData.OK_DONE);

	// model

	private Conocimiento conocimiento = new Conocimiento();

	// view
	
	@FXML
	private TextField denomTextField;

	@FXML
	private ComboBox<Nivel> nivelComboBox;

	@FXML
	private Button resetButton;

	public ConocimientoDialog() {
		super();
		setTitle("Nuevo conocimiento");
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/icons/cv64x64.png").toString()));
		getDialogPane().getButtonTypes().addAll(CREAR_BUTTON_TYPE, // botón personalizado
				ButtonType.CANCEL);
		getDialogPane().setContent(loadContent("/fxml/dialogs/ConocimientoDialogView.fxml"));
		setResultConverter(dialogButton -> {
			if (dialogButton.getButtonData() == ButtonData.OK_DONE) {
				Conocimiento nuevoConocimiento = new Conocimiento();
				nuevoConocimiento.setDenominacion(conocimiento.getDenominacion());
				nuevoConocimiento.setNivel(conocimiento.getNivel());
				return nuevoConocimiento;
			}
			return null;
		});
	}

	private Node loadContent(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			loader.setController(this);
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nivelComboBox.getItems().addAll(Nivel.values());
		conocimiento.denominacionProperty().bind(denomTextField.textProperty());
		conocimiento.nivelProperty().bind(nivelComboBox.valueProperty());
		Node crearButton = getDialogPane().lookupButton(CREAR_BUTTON_TYPE);
		crearButton.disableProperty()
				.bind(conocimiento.denominacionProperty().isEmpty());
	}
	
	@FXML
	void onResetAction(ActionEvent event) {
		nivelComboBox.setValue(null);
	}

}
