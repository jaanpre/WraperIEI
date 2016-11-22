package logica;

public class Movil {
	private String vendedor;
	private String modelo;
	private String precio;
	private String disponibilidad;
	
	public Movil(String vendedor, String modelo, String precio, String disponibilidad) {
		super();
		this.vendedor = vendedor;
		this.modelo = modelo;
		this.precio = precio;
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
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	
}
