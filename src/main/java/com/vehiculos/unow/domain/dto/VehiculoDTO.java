package com.vehiculos.unow.domain.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoDTO {
        @NotEmpty(message = "no puede estar vacio")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "solo puede contener letras")
        private String marca;

        @NotEmpty(message = "no puede estar vacio")
        @Pattern(regexp = "^[0-9]+$", message = "Debe contener solo números")
        private String modelo;

        @NotEmpty(message = "no puede estar vacio")
        private String matricula;

        @NotEmpty(message = "no puede estar vacio")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "solo puede contener letras")
        private String color;

        @NotEmpty(message = "no puede estar vacio")
        private String fecha;
}
