package com.api_inventario.model;

import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "inventario")
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_inventario")
    private Integer id;

    @Column(name="id_producto")
    private Integer idProducto;

    @Column(name="stock_disponible")
    private Integer stockDisponible;

    @Column(name="ubicacion_bodega")
    private String ubicacionBodega;

}
