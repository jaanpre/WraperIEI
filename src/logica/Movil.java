package logica;

public class Movil {
	private String vendedor;
	private String modelo;
	private String precio1;
	private String precio2;

	private String disponibilidad;
	
	public Movil(String vendedor, String modelo, String precio, String disponibilidad) {
		this.vendedor = vendedor;
		this.modelo = modelo;
		this.precio1 = precio;
		this.disponibilidad = disponibilidad;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPrecio() {
		return precio1;
	}
	public void setPrecio(String precio) {
		this.precio1 = precio;
	}
	public String getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public String getPrecio2() {
		return precio2;
	}
	public void setPrecio2(String precio2) {
		this.precio2 = precio2;
	}
}
