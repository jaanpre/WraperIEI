package presentacion;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class ControladorCU implements Initializable { 
	protected Stage stage; 
	protected ControladorMenuPrincipal controladorMP; 
	public void setControladorPrincipal(ControladorMenuPrincipal controladorMP) { 
		this.controladorMP = controladorMP; 
		}
	public Stage getStage() { 
		return stage; 
		}
	public void show() { 
		stage.show(); 
		}
	public static <T extends ControladorCU> T initCU(String urlVista, Class<T> controlClass, Stage owner, 
			ControladorMenuPrincipal controladorMP) { 
		FXMLLoader fxmlLoader = new FXMLLoader(ControladorCU.class.getResource(urlVista));
		T controlador = null; 
		try {
			Parent parent = fxmlLoader.load(); 
			controlador = fxmlLoader.getController(); 
			Scene scene = new Scene(parent);
			controlador.stage.setScene(scene); 
			controlador.stage.initOwner(owner); 
			controlador.setControladorPrincipal(controladorMP); 
			} catch (NullPointerException | IOException e) { 
				System.out.println("Hola");
				e.printStackTrace(); 
				}
		return controlador; 
		} 
	}
