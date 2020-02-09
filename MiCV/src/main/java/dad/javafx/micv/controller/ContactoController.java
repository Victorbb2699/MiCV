package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.model.CV;
import dad.javafx.micv.model.Contacto;
import dad.javafx.micv.model.Email;
import dad.javafx.micv.model.Telefono;
import dad.javafx.micv.model.TipoTelefono;
import dad.javafx.micv.model.Web;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ContactoController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private TableView<Telefono> tablaTelefonos;

	@FXML
	private TableColumn<Telefono, String> numeroColumn;

	@FXML
	private TableColumn<Telefono, TipoTelefono> tipoColumn;

	@FXML
	private Button anadirTelefono;

	@FXML
	private Button quitarTelefono;

	@FXML
	private TableView<Email> tablaCorreo;

	@FXML
	private TableColumn<Email, String> correoColumn;

	@FXML
	private Button anadirCorreo;

	@FXML
	private Button quitarCorreo;

	@FXML
	private TableView<Web> tablaWebs;

	@FXML
	private TableColumn<Web, String> urlColumn;

	@FXML
	private Button anadirWeb;

	@FXML
	private Button quitarWeb;

	public AnchorPane getView() {
		return root;
	}

	private CV model = new CV();
	
	public CV getModel() {
		return model;
	}

	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		model.setContacto(new Contacto());

		// bindeos
		tablaTelefonos.itemsProperty().bindBidirectional(model.getContacto().telefonosProperty());
		tablaCorreo.itemsProperty().bindBidirectional(model.getContacto().emailsProperty());
		tablaWebs.itemsProperty().bindBidirectional(model.getContacto().websProperty());

		quitarTelefono.disableProperty().bind(tablaTelefonos.getSelectionModel().selectedItemProperty().isNull());
		quitarWeb.disableProperty().bind(tablaWebs.getSelectionModel().selectedItemProperty().isNull());
		quitarCorreo.disableProperty().bind(tablaCorreo.getSelectionModel().selectedItemProperty().isNull());

		// values factories
		// telefono
		numeroColumn.setCellValueFactory(v -> v.getValue().telefonoProperty());
		tipoColumn.setCellValueFactory(v -> v.getValue().tipoProperty());
		// correo
		correoColumn.setCellValueFactory(v -> v.getValue().direccionProperty());
		// web
		urlColumn.setCellValueFactory(v -> v.getValue().urlProperty());

		// cell factories
		// telefono
		numeroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		tipoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));

		// correo
		correoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		urlColumn.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	@FXML
	void onAnadirCorreoAction(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Nuevo E-mail");
		dialog.setHeaderText("Crea una nueva dirección de correo");
		dialog.setContentText("E-mail:");

		Optional<String> result = dialog.showAndWait();
		try {
			if (!result.get().equals("")) {
				Email aux = new Email();
				aux.setDireccion(result.get());

				model.getContacto().getEmails().add(aux);

			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
	}

	@FXML
	void onAnadirTelefonoAction(ActionEvent event) {
		// Create the custom dialog.
		Dialog<Pair<String, TipoTelefono>> dialog = new Dialog<>();
		dialog.setTitle("Nuevo Teléfono");
		dialog.setHeaderText("Introduzca el nuevo número de teléfono.");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		// Add a custom icon.
		stage.getIcons().add(new Image(this.getClass().getResource("/icons/cv64x64.png").toString()));

		// Set the button types.
		ButtonType anadirButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(anadirButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField numero = new TextField();
		numero.setPromptText("Introduce un numero");
		ComboBox<TipoTelefono> tipo = new ComboBox<TipoTelefono>();
		tipo.getItems().addAll(TipoTelefono.values());
		tipo.setPromptText("Seleccione un  Tipo");

		grid.add(new Label("Número:"), 0, 0);
		grid.add(numero, 1, 0);
		grid.add(new Label("Tipo:"), 0, 1);
		grid.add(tipo, 1, 1);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			try {
				Integer.parseInt(numero.getText());
			} catch (NumberFormatException e) {
				return null;
			}
			if (tipo.getValue() == null)
				return null;
			return new Pair<>(numero.getText(), tipo.getValue());
		});

		// Request focus on the username field by default.

		Optional<Pair<String, TipoTelefono>> result = dialog.showAndWait();

		if (!result.isEmpty()) {
			Telefono aux = new Telefono();
			aux.setTelefono(result.get().getKey());
			aux.setTipo(result.get().getValue());
			model.getContacto().getTelefonos().add(aux);
		}

	}

	@FXML
	void onAnadirWebAction(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("http://");
		dialog.setTitle("Nueva web");
		dialog.setHeaderText("Crea una nueva dirección web.");
		dialog.setContentText("URL:");

		Optional<String> result = dialog.showAndWait();
		try {
			if (!result.get().equals("http://") && !result.get().equals("")) {
				Web aux = new Web();
				aux.setUrl(result.get());

				model.getContacto().getWebs().add(aux);

			}
		} catch (NoSuchElementException e) {
		}
	}

	@FXML
	void onQuitarCorreoAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar E-mail");
		alert.setContentText("¿Desea borrar este E-mail?");
		Optional<ButtonType> a = alert.showAndWait();
		if (a.get().getText().equals("Aceptar")) {
			model.getContacto().getEmails().remove(tablaCorreo.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	void onQuitarTelefonoAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar Teléfono");
		alert.setContentText("¿Desea borrar este teléfono?");
		Optional<ButtonType> a = alert.showAndWait();
		if (a.get().getText().equals("Aceptar")) {
			model.getContacto().getTelefonos().remove(tablaTelefonos.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	void onQuitarWebAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar Web");
		alert.setContentText("¿Desea borrar esta web?");
		Optional<ButtonType> a = alert.showAndWait();
		if (a.get().getText().equals("Aceptar")) {
			model.getContacto().getWebs().remove(tablaWebs.getSelectionModel().getSelectedIndex());
		}
	}

}
