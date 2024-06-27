package com.vehiculos.unow.application.port.out.db;

import com.vehiculos.unow.domain.dto.VehiculoDTO;
import com.vehiculos.unow.infrastructure.adapter.out.db.model.VehiculoEntity;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleException;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleValidationException;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface VehiculoPortService {

    public List<VehiculoEntity> findAll();
    public List<VehiculoEntity> findByModel(Short modelo);
    public List<VehiculoEntity> findByBrand(String marca);
    public List<VehiculoEntity> findByLicensePlate(String matricula);

    public List<VehiculoEntity> getVehiculosByFilter(String marca, Short modelo, String matricula);

    public Page<VehiculoEntity> getVehiculosByFilterOrder(String marca, Short modelo, String matricula, int page, int size, String sortBy, String sortDirection);

    public VehiculoEntity save(VehiculoDTO vehiculoDTO, BindingResult result) throws BussinesRuleValidationException;

    public void put(VehiculoDTO clienteDTO, BindingResult result, Long id) throws BussinesRuleException, BussinesRuleValidationException;

    public void delete(Long id) throws BussinesRuleException;

}
