package com.utsem.app.service;

import com.utsem.app.dto.PropiedadDTO;
import com.utsem.app.model.Propiedad;
import com.utsem.app.model.User;
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

    public List<Propiedad> listar() {
        return propiedadRepository.findAll();
    }
    
    public List<Propiedad> listarPorPropietario(Long propietarioId) {
        return propiedadRepository.findByPropietarioId(propietarioId);
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
                dto.getImagenUrl(),
                propietario
        );

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
        propiedad.setImagenUrl(dto.getImagenUrl());

        return propiedadRepository.save(propiedad);
    }

    public void eliminar(Long id) {
        propiedadRepository.deleteById(id);
    }

    public List<Propiedad> filtrar(String ubicacion, String tipo, Double precioMin, Double precioMax) {
        return propiedadRepository.buscarFiltrado(ubicacion, tipo, precioMin, precioMax);
    }
}
