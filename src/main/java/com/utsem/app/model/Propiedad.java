package com.utsem.app.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "propiedades")
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String estado;
    private String ubicacion;
    private Double precio;
    private Double promedio;

    @ElementCollection
    @CollectionTable(name = "propiedad_imagenes", joinColumns = @JoinColumn(name = "propiedad_id"))
    @Column(name = "imagen_url")
    private List<String> imagenes;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User propietario;

    public Propiedad() {}

    public Propiedad(String titulo, String descripcion, String tipo, String ubicacion, Double precio, List<String> imagenes, User propietario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.imagenes = imagenes;
        this.propietario = propietario;
        this.estado = "disponible";
    }
    
    public List<String> getImagenes() { return imagenes; }
    public void setImagenes(List<String> imagenes) { this.imagenes = imagenes; }
    
	public Double getPromedio() { return promedio; }
	public void setPromedio(Double promedio) { this.promedio = promedio; }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public User getPropietario() {
		return propietario;
	}

	public void setPropietario(User propietario) {
		this.propietario = propietario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
