package com.utsem.app.controller;

import com.utsem.app.dto.PropiedadDTO;
import com.utsem.app.model.Propiedad;
import com.utsem.app.service.PropiedadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping("/listar-json")
    public List<Propiedad> listar() {
        return propiedadService.listar();
    }
    
    @GetMapping("/listar-por-propietario/{propietarioId}")
    public List<Propiedad> listarPorPropietario(@PathVariable Long propietarioId) {
        return propiedadService.listarPorPropietario(propietarioId);
    }

    @GetMapping("/{id}")
    public Propiedad obtener(@PathVariable Long id) {
        return propiedadService.obtener(id);
    }

    @PostMapping("/crear")
    public Propiedad crear(@RequestBody PropiedadDTO dto) {
        return propiedadService.crear(dto);
    }

    @PutMapping("/editar/{id}")
    public Propiedad editar(@PathVariable Long id, @RequestBody PropiedadDTO dto) {
        return propiedadService.editar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        propiedadService.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<Propiedad> filtrar(
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax
    ) {
        return propiedadService.filtrar(ubicacion, tipo, precioMin, precioMax);
    }
    
    @PatchMapping("/status/{id}")
    public Propiedad cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String estado = body.get("estado");
        return propiedadService.cambiarEstado(id, estado);
    }
}
