package com.utsem.app.service;

import com.utsem.app.dto.PropiedadDTO;
import com.utsem.app.model.Propiedad;
import com.utsem.app.model.User;
import com.utsem.app.repository.ComentarioRepository;
import com.utsem.app.repository.PropiedadRepository;
import com.utsem.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private ComentarioRepository comentarioRepo;

    public List<Propiedad> listar() {
        List<Propiedad> lista = propiedadRepository.findAll()
                .stream()
                .filter(p -> "disponible".equals(p.getEstado()))
                .toList();

        lista.forEach(p -> {
            Double promedio = comentarioRepo.promedioPorPropiedad(p.getId());
            p.setPromedio(promedio != null ? promedio : 0.0);
        });

        return lista;
    }
    
    public List<Propiedad> listarPorPropietario(Long propietarioId) {
        List<Propiedad> lista = propiedadRepository.findByPropietarioId(propietarioId);

        lista.forEach(p -> {
            Double promedio = comentarioRepo.promedioPorPropiedad(p.getId());
            p.setPromedio(promedio != null ? promedio : 0.0);
        });

        return lista;
    }

    public Propiedad obtener(Long id) {
        Optional<Propiedad> propiedad = propiedadRepository.findById(id);
        return propiedad.orElse(null);
    }

    public Propiedad crear(PropiedadDTO dto) {
        User propietario = userRepository.findById(dto.getPropietarioId()).orElse(null);

        Propiedad propiedad = new Propiedad(
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getTipo(),
                dto.getUbicacion(),
                dto.getPrecio(),
                dto.getImagenes(),
                propietario
        );
        propiedad.setEstado("disponible");

        return propiedadRepository.save(propiedad);
    }

    public Propiedad editar(Long id, PropiedadDTO dto) {
        Optional<Propiedad> opt = propiedadRepository.findById(id);
        if (!opt.isPresent()) return null;

        Propiedad propiedad = opt.get();
        propiedad.setTitulo(dto.getTitulo());
        propiedad.setDescripcion(dto.getDescripcion());
        propiedad.setTipo(dto.getTipo());
        propiedad.setUbicacion(dto.getUbicacion());
        propiedad.setPrecio(dto.getPrecio());
        propiedad.setImagenes(dto.getImagenes());
        propiedad.setEstado(dto.getEstado());

        return propiedadRepository.save(propiedad);
    }

    public void eliminar(Long id) {
        propiedadRepository.deleteById(id);
    }

    public List<Propiedad> filtrar(String ubicacion, String tipo, Double precioMin, Double precioMax) {
        return propiedadRepository.buscarFiltrado(ubicacion, tipo, precioMin, precioMax);
    }
    
    public Propiedad cambiarEstado(Long id, String estado) {
        Optional<Propiedad> opt = propiedadRepository.findById(id);
        if (!opt.isPresent()) return null;

        Propiedad propiedad = opt.get();
        if ("disponible".equals(estado) || "alquilada".equals(estado)) {
            propiedad.setEstado(estado);
        }
        return propiedadRepository.save(propiedad);
    }

}
