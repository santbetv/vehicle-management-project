package com.vehiculos.unow.application.port.in.web;

import com.vehiculos.unow.domain.dto.VehiculoDTO;
import com.vehiculos.unow.infrastructure.adapter.out.db.model.VehiculoEntity;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleException;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleValidationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface WebPort {

    @GetMapping("/vehiculos")
    public List<VehiculoEntity> list();

    @GetMapping("/vehiculos/search")
    public List<VehiculoEntity> getVehiculosByFilter(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Short modelo,
            @RequestParam(required = false) String matricula);

    @GetMapping("/vehiculos/order")
    public Page<VehiculoEntity> getVehiculosByFilter(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Short modelo,
            @RequestParam(required = false) String matricula,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idVehiculo") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection);
    @GetMapping("/vehiculos/{modelo}")
    public List<VehiculoEntity> getModelo(@PathVariable Short modelo);

    @PostMapping("/vehiculos")
    public ResponseEntity<?> post(@Valid @RequestBody VehiculoDTO input, BindingResult result) throws BussinesRuleValidationException;


    @DeleteMapping("/vehiculos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws BussinesRuleException;

    @PutMapping("/vehiculos/{id}")
    public ResponseEntity<?> put(@Valid
                                 @RequestBody VehiculoDTO input, BindingResult result,
                                 @PathVariable Long id) throws BussinesRuleException, BussinesRuleValidationException;
}
