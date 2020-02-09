package dad.javafx.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Experiencia;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class ExperienciaDialog extends Dialog<Experiencia> implements Initializable {

	private ButtonType CREAR_BUTTON_TYPE = new ButtonType("Crear", ButtonData.OK_DONE);

	// model

	private Experiencia exp = new Experiencia();
	
	// view
	
	@FXML
    private TextField denomTextField;

    @FXML
    private TextField empleTextField;

    @FXML
    private DatePicker desdeDate;

    @FXML
    private DatePicker hastaDate;
    
    public ExperienciaDialog() {
    	super();
    	setTitle("Nueva experiencia");
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/icons/cv64x64.png").toString()));
		getDialogPane().getButtonTypes().addAll(
				CREAR_BUTTON_TYPE, // botón personalizado
				ButtonType.CANCEL
			);
		getDialogPane().setContent(loadContent("/fxml/dialogs/ExperienciaDialogView.fxml"));
		setResultConverter(dialogButton -> {
		    if (dialogButton.getButtonData() == ButtonData.OK_DONE) {
		    	Experiencia nuevaExperiencia = new Experiencia();
		    	nuevaExperiencia.setDenominacion(exp.getDenominacion());
		    	nuevaExperiencia.setEmpleador(exp.getEmpleador());
		    	nuevaExperiencia.setDesde(exp.getDesde());
		    	nuevaExperiencia.setHasta(exp.getHasta());
		    	return nuevaExperiencia;
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
		exp.denominacionProperty().bind(denomTextField.textProperty());
		exp.empleadorProperty().bind(empleTextField.textProperty());
		exp.desdeProperty().bind(desdeDate.valueProperty());
		exp.hastaProperty().bind(hastaDate.valueProperty());
		
		Node crearButton = getDialogPane().lookupButton(CREAR_BUTTON_TYPE);
		crearButton.disableProperty().bind(
				exp.denominacionProperty().isEmpty()
				.or(exp.empleadorProperty().isEmpty())
				.or(exp.desdeProperty().isNull())
				.or(exp.hastaProperty().isNull())
				);
		
	}

}
