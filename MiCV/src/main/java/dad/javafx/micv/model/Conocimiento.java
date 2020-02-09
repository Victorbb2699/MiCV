package dad.javafx.micv.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Conocimiento {
	
	private SimpleStringProperty denominacion = new SimpleStringProperty();
	private ObjectProperty<Nivel> nivel = new SimpleObjectProperty<Nivel>();
	private ObjectProperty<Idioma> idioma = new SimpleObjectProperty<Idioma>();
	
	public final SimpleStringProperty denominacionProperty() {
		return this.denominacion;
	}
	
	@XmlAttribute
	public final String getDenominacion() {
		return this.denominacionProperty().get();
	}
	
	public final void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}

	public final ObjectProperty<Nivel> nivelProperty() {
		return this.nivel;
	}
	
	@XmlAttribute
	public final Nivel getNivel() {
		return this.nivelProperty().get();
	}
	

	public final void setNivel(final Nivel nivel) {
		this.nivelProperty().set(nivel);
	}

	public final ObjectProperty<Idioma> idiomaProperty() {
		return this.idioma;
	}
	
	@XmlElement
	public final Idioma getIdioma() {
		return this.idiomaProperty().get();
	}
	

	public final void setIdioma(final Idioma idioma) {
		this.idiomaProperty().set(idioma);
	}
	
	
}
