package presentacion;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private CheckBox fnac;
	private static final String LISTAR_MOVILES = "Resultados.fxml";
	private Stage primaryStage;
	private WrapperIEI w = WrapperIEI.getInstance();

	private List<String> listaMoviles = Arrays.asList("Samsung", "Lg", "Sony", "Huawei", "Motorola", "Apple",
			"OnePlus", "Lenovo");

	@FXML
	void buscar(ActionEvent event) {
		if (marcas.getValue() != null) {
			w.setMarca(marcas.getValue());
			w.setBools(pccomp.isSelected(), fnac.isSelected());
			initCU(LISTAR_MOVILES, ControladorListarMoviles.class).show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, there was an error!");

			alert.showAndWait();
		}
	}

	public void initialize() {
		marcas.setItems(FXCollections.observableArrayList(listaMoviles));
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private <T extends ControladorCU> T initCU(String urlVista, Class<T> controlClass) {
		return ControladorCU.initCU(urlVista, controlClass, primaryStage, ControladorMenuPrincipal.this);
	}
}
