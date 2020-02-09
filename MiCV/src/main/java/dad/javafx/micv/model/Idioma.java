package dad.javafx.micv.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Idioma extends Conocimiento {
	
	private StringProperty certificacion = new SimpleStringProperty();
	
	public final StringProperty certificacionProperty() {
		return this.certificacion;
	}
	
	@XmlAttribute(name="certificación")
	public final String getCertificacion() {
		return this.certificacionProperty().get();
	}
	

	public final void setCertificacion(final String certificacion) {
		this.certificacionProperty().set(certificacion);
	}
	

}
