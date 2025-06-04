package com.api_inventario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_inventario.dto.InventarioDTO;
import com.api_inventario.model.Inventario;
import com.api_inventario.repository.InventarioRepository;


@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    private InventarioDTO toDTO(Inventario inventario){
        return new InventarioDTO(
            inventario.getId(),
            inventario.getIdProducto(),
            inventario.getStockDisponible(),
            inventario.getUbicacionBodega()
        );
        
    }

    private Inventario toEntity(InventarioDTO dto) {
        Inventario inventario = new Inventario();
        inventario.setId(dto.getId());
        inventario.setIdProducto(dto.getIdProducto());
        inventario.setStockDisponible(dto.getStockDisponible());
        inventario.setUbicacionBodega(dto.getUbicacionBodega());
        return inventario;
    }

    public InventarioDTO crear(InventarioDTO dto) {
        Inventario inventario = toEntity(dto);
        return toDTO(inventarioRepository.save(inventario));
    }

    public List<InventarioDTO> listar() {
        return inventarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InventarioDTO obtenerPorId(Integer id) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        return toDTO(inventario);
    }

    public InventarioDTO actualizar(Integer id, InventarioDTO dto) {
        Inventario existente = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        existente.setIdProducto(dto.getIdProducto());
        existente.setStockDisponible(dto.getStockDisponible());
        existente.setUbicacionBodega(dto.getUbicacionBodega());

        return toDTO(inventarioRepository.save(existente));
    }

    public void eliminar(Integer id) {
        inventarioRepository.deleteById(id);
    }






}
