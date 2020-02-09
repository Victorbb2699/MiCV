package dad.javafx.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class Utils {

	public static List<String> cargarPaises(){
		List<String> aux = new ArrayList<String>();
		
		String fileName = "utils/paises.csv";
        ClassLoader classLoader = new Utils().getClass().getClassLoader();
 
        File file = new File(classLoader.getResource(fileName).getFile());
         		
		try {
			String a;
			BufferedReader br = new BufferedReader(new FileReader(file,Charset.forName("UTF-8")));
			while((a=br.readLine())!=null){
				aux.add(a);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aux;
		
	}
	
	public static List<String> cargarNacionalidades(){
		List<String> aux = new ArrayList<String>();
		
		String fileName = "utils/nacionalidades.csv";
        ClassLoader classLoader = new Utils().getClass().getClassLoader();
 
        File file = new File(classLoader.getResource(fileName).getFile());
         		
		try {
			String a;
			BufferedReader br = new BufferedReader(new FileReader(file,Charset.forName("UTF-8")));
			while((a=br.readLine())!=null){
				aux.add(a);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aux;
		
	}
	
}
