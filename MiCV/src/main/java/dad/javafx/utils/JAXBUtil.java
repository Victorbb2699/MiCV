package dad.javafx.utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtil {
	
	public static void save(Object rootObject, File target)  {
		try {
			JAXBContext context = JAXBContext.newInstance(rootObject.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(rootObject, target);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static <T> T load(Class<T> rootClass, File source) throws Exception {
		JAXBContext context =null;
		Unmarshaller unmarshaller=null;
		try {
			context = JAXBContext.newInstance(rootClass);
			unmarshaller = context.createUnmarshaller();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return rootClass.cast(unmarshaller.unmarshal(source));
	}		
	
}