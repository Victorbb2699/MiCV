package dad.javafx.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Titulo;
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

public class FormacionDialog extends Dialog<Titulo> implements Initializable {

	private ButtonType CREAR_BUTTON_TYPE = new ButtonType("Crear", ButtonData.OK_DONE);

	// model

	private Titulo titulo = new Titulo();
	
	// view
	
	@FXML
    private TextField denomTextField;

    @FXML
    private TextField orgTextField;

    @FXML
    private DatePicker desdeDate;

    @FXML
    private DatePicker hastaDate;
    
    public FormacionDialog() {
    	super();
    	setTitle("Nuevo título");
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/icons/cv64x64.png").toString()));
		getDialogPane().getButtonTypes().addAll(
				CREAR_BUTTON_TYPE, // botón personalizado
				ButtonType.CANCEL
			);
		getDialogPane().setContent(loadContent("/fxml/dialogs/FormacionDialogView.fxml"));
		setResultConverter(dialogButton -> {
		    if (dialogButton.getButtonData() == ButtonData.OK_DONE) {
		    	Titulo nuevoTitulo = new Titulo();
		    	nuevoTitulo.setDenominacion(titulo.getDenominacion());
		    	nuevoTitulo.setOrganizador(titulo.getOrganizador());
		    	nuevoTitulo.setDesde(titulo.getDesde());
		    	nuevoTitulo.setHasta(titulo.getHasta());
		    	return nuevoTitulo;
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
		titulo.denominacionProperty().bind(denomTextField.textProperty());
		titulo.organizadorProperty().bind(orgTextField.textProperty());
		titulo.desdeProperty().bind(desdeDate.valueProperty());
		titulo.hastaProperty().bind(hastaDate.valueProperty());
		
		Node crearButton = getDialogPane().lookupButton(CREAR_BUTTON_TYPE);
		crearButton.disableProperty().bind(
				titulo.denominacionProperty().isEmpty()
				.or(titulo.organizadorProperty().isEmpty())
				.or(titulo.desdeProperty().isNull())
				.or(titulo.hastaProperty().isNull())
				);
		
	}

}
