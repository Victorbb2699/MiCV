package dad.javafx.micv.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.app.MiCVApp;
import dad.javafx.micv.model.CV;
import dad.javafx.utils.JAXBUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	@FXML
	private BorderPane root;

	@FXML
	private MenuBar MenuBar;

	@FXML
	private Menu firstMenu;

	@FXML
	private Menu secondMenu;

	@FXML
	private TabPane TabPane;

	@FXML
	private Tab personalTab;

	@FXML
	private Tab contactoTab;

	@FXML
	private Tab formacionTab;

	@FXML
	private Tab experienciaTab;

	@FXML
	private Tab conocimientosTab;

	// model
	PersonalController personal = new PersonalController();
	ContactoController contacto = new ContactoController();
	FormacionController formacion = new FormacionController();
	ExperienciaController experiencia = new ExperienciaController();
	ConocimientosController conocimiento = new ConocimientosController();

	CV model = new CV();

	private File fichero;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public BorderPane getView() {
		return root;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		model.personalProperty().bindBidirectional(personal.getModel().personalProperty());
		model.contactoProperty().bindBidirectional(contacto.getModel().contactoProperty());
		model.formacionProperty().bindBidirectional(formacion.getModel().formacionProperty());
		model.experienciasProperty().bindBidirectional(experiencia.getModel().experienciasProperty());
		model.habilidadesProperty().bindBidirectional(conocimiento.getModel().habilidadesProperty());

		personalTab.setContent(personal.getView());
		contactoTab.setContent(contacto.getView());
		formacionTab.setContent(formacion.getView());
		experienciaTab.setContent(experiencia.getView());
		conocimientosTab.setContent(conocimiento.getView());

	}

	@FXML
	void onNuevoAction(ActionEvent event) {
		
		fichero= null;

		// personal
		model.getPersonal().setIdentificacion(new String());
		model.getPersonal().setNombre(new String());
		model.getPersonal().setApellidos(new String());
		model.getPersonal().setFechaNacimiento(null);
		model.getPersonal().setDireccion(new String());
		model.getPersonal().setCodigoPostal(new String());
		model.getPersonal().setLocalidad(new String());
		model.getPersonal().setPais(null);
		model.getPersonal().setNacionalidades(FXCollections.observableArrayList());

		// contacto

		model.getContacto().setTelefonos(FXCollections.observableArrayList());
		model.getContacto().setEmails(FXCollections.observableArrayList());
		model.getContacto().setWebs(FXCollections.observableArrayList());

		// formacion

		model.setFormacion(FXCollections.observableArrayList());

		// Experiencias

		model.setExperiencias(FXCollections.observableArrayList());

		// Conocimientos

		model.setHabilidades(FXCollections.observableArrayList());

	}

	@FXML
	void onAbrirAction(ActionEvent event) {
		CV aux=null;

		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("xml files", "*.cv"));

		chooser.setTitle("Abrir fichero CV");

		fichero = chooser.showOpenDialog(MiCVApp.getStage());
		try {
			aux = JAXBUtil.load(CV.class, fichero);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// personal
		model.getPersonal().setIdentificacion(aux.getPersonal().getIdentificacion());
		model.getPersonal().setNombre(aux.getPersonal().getNombre());
		model.getPersonal().setApellidos(aux.getPersonal().getApellidos());
		model.getPersonal().setFechaNacimiento(aux.getPersonal().getFechaNacimiento());
		model.getPersonal().setDireccion(aux.getPersonal().getDireccion());
		model.getPersonal().setCodigoPostal(aux.getPersonal().getCodigoPostal());
		model.getPersonal().setLocalidad(aux.getPersonal().getLocalidad());
		model.getPersonal().setPais(aux.getPersonal().getPais());
		model.getPersonal().setNacionalidades(aux.getPersonal().getNacionalidades());

		// contacto

		model.getContacto().setTelefonos(aux.getContacto().getTelefonos());
		model.getContacto().setEmails(aux.getContacto().getEmails());
		model.getContacto().setWebs(aux.getContacto().getWebs());

		// formacion

		model.setFormacion(aux.getFormacion());

		// Experiencias

		model.setExperiencias(aux.getExperiencias());

		// Conocimientos

		model.setHabilidades(aux.getHabilidades());
	}

	@FXML
	void onGuardarAction(ActionEvent event) {
		if (fichero != null) {
			JAXBUtil.save(model, fichero);
		} else {
			onGuardarComoAction(event);
		}
	}

	@FXML
	void onGuardarComoAction(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("xml files", "*.cv"));

		chooser.setTitle("Guardar fichero CV");

		fichero = chooser.showSaveDialog(MiCVApp.getStage());
		JAXBUtil.save(model, fichero);

	}

	@FXML
	void onSalirAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Salir");
		alert.setHeaderText("Va a salir del programa");
		alert.setContentText("¿Seguro que quiere salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(0);
		}
	}

}
