package presentacion;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import logica.WrapperIEI;

public class ControladorMenuPrincipal {
	@FXML
	private ComboBox<String> marcas;
	@FXML
	private CheckBox pccomp;
	@FXML
	private CheckBox otro;
	private static final String LISTAR_MOVILES = "Resultados.fxml";
	private Stage primaryStage; 
	private WrapperIEI w = WrapperIEI.getInstance();
	
	private List<String> listaMoviles = Arrays.asList("Alcatel","Acer","LG","Apple","Asus","BQ","HTC","Huawei","Lenovo","Meizu","Motorola","Nokia","Oneplus","Oppo","Samsung","Sony","Wiko","Xiaomi","ZTE","Honor");
	
	@FXML
	void buscar(ActionEvent event){ 
		if(pccomp.isSelected()){
			w.setMarca(marcas.getValue());
			initCU(LISTAR_MOVILES, ControladorListarMoviles.class).show();
		}
		else if(otro.isSelected())initCU(LISTAR_MOVILES, ControladorListarMoviles.class).show();
		else initCU(LISTAR_MOVILES, ControladorListarMoviles.class).show();
	}
	
	public void initialize(){
		marcas.setItems(FXCollections.observableArrayList(listaMoviles));
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage; 
	}
	private <T extends ControladorCU> T initCU(String urlVista, Class<T> controlClass) {
		return ControladorCU.initCU(urlVista, controlClass, primaryStage, ControladorMenuPrincipal.this); 
		} 
}
