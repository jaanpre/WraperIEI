package presentacion;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logica.Movil;
import logica.WrapperIEI;

public class ControladorListarMoviles extends ControladorCU { 
	@FXML 
	private TableView<Movil> moviles; 
	@FXML 
	private TableColumn<Movil, String> vendedor; 
	@FXML 
	private TableColumn<Movil, String> modelo; 
	@FXML 
	private TableColumn<Movil, String> precio; 
	@FXML
	private TableColumn<Movil, String> disponibilidad;
	@FXML 
	private Button cerrar; 
	
	@Override 
	public void initialize(URL location, ResourceBundle resources) { 
		stage = new Stage(StageStyle.DECORATED); 
		stage.setTitle("LISTADO DE MOVILES"); 
		cerrar.setOnAction(event -> stage.close()); 
		vendedor.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getVendedor())); 
		modelo.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getModelo())); 
		precio.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrecio())); 
		disponibilidad.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getDisponibilidad())); 
		//this.sucursales.getItems().addAll(AlquilerVehiculos.getAlquilerVehiculos().listarSucursales()); 
		this.moviles.getItems().addAll((new WrapperIEI()).resultadosPCComp(WrapperIEI.getInstance().getMarca()));
		} 	
	}
