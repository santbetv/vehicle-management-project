package com.vehiculos.unow.infrastructure.adapter.out.db.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehiculos")
public class VehiculoEntity implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_vehiculo")
        private Long idVehiculo;
        private String marca;
        private Short modelo;
        private String matricula;
        private String color;
        private LocalDate fecha;
}
