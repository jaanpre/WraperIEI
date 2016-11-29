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
	private TableColumn<Movil, String> modelo;
	@FXML
	private TableColumn<Movil, String> precioPC;
	@FXML
	private TableColumn<Movil, String> precioFnac;

	@FXML
	private Button cerrar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("LISTADO DE MOVILES");
		cerrar.setOnAction(event -> stage.close());
		modelo.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getModelo()));
		precioPC.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrecio()));
		precioFnac.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrecio2()));
		
		this.moviles.getItems().addAll(WrapperIEI.getInstance().resultadosTotales(WrapperIEI.getInstance().getMarca()));
	}

}