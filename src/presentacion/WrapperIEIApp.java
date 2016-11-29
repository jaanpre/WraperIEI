package presentacion;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class WrapperIEIApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	public static void main(String[] args) {launch(args);}
	
	public WrapperIEIApp (){}

	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Comparador móvil");
		this.primaryStage.getIcons().add(new Image("file:data/icono.png"));
		initRootLayout();
	}

	public void initRootLayout(){
		try { // Load root layout from fxml file.
			FXMLLoader loader =new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene); 
			primaryStage.setResizable(false);
			primaryStage.show();
			ControladorMenuPrincipal controlador = loader.getController();
			controlador.setPrimaryStage(primaryStage);
			}catch (IOException e){
				e.printStackTrace();
			}
	}

	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }
}
