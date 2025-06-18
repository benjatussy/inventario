package com.api_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.api_inventario.dto.InventarioDTO;
import com.api_inventario.model.Inventario;
import com.api_inventario.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<InventarioDTO> crear(@RequestBody InventarioDTO dto) {
        return ResponseEntity.ok(inventarioService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listar() {
        return ResponseEntity.ok(inventarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Integer id, @RequestBody InventarioDTO dto) {
        return ResponseEntity.ok(inventarioService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public InventarioDTO obtenerHATEOAS(@PathVariable Integer id) {
        InventarioDTO dto = inventarioService.obtenerPorId(id);
        
        dto.add(linkTo(methodOn(InventarioController.class).obtenerHATEOAS(id)).withSelfRel());
        dto.add(linkTo(methodOn(InventarioController.class).obtenerTodosHATEOAS()).withRel("todos"));
        dto.add(linkTo(methodOn(InventarioController.class).eliminar(id)).withRel("eliminar"));

        return dto;
    }

    //METODO HATEOAS para listar todos los productos utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<InventarioDTO> obtenerTodosHATEOAS() {
        List<InventarioDTO> lista = inventarioService.listar();

        for (InventarioDTO dto : lista) {
            dto.add(linkTo(methodOn(InventarioController.class).obtenerHATEOAS(dto.getId())).withSelfRel());
        }

        return lista;
    }

}
