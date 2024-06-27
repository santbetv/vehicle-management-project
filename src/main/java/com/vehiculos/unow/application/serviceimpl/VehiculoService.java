package com.vehiculos.unow.application.serviceimpl;

import com.vehiculos.unow.application.port.out.db.VehiculoPortService;
import com.vehiculos.unow.domain.dto.VehiculoDTO;
import com.vehiculos.unow.infrastructure.adapter.out.db.model.VehiculoEntity;
import com.vehiculos.unow.infrastructure.adapter.out.db.persistence.VehiculoRepository;
import com.vehiculos.unow.infrastructure.adapter.out.db.persistence.VehiculoSpecification;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleException;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService implements VehiculoPortService {

    private static final Logger LOG = LoggerFactory.getLogger(VehiculoService.class);
    private final String INFO_URL = "api/vehiculo";
    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    @Transactional(readOnly = true) //
    public List<VehiculoEntity> findAll() {
        List<VehiculoEntity> l = vehiculoRepository.findAll();
        return l;
    }

    @Override
    @Transactional(readOnly = true) //
    public List<VehiculoEntity> getVehiculosByFilter(String marca, Short modelo, String matricula) {
        Specification<VehiculoEntity> specification = VehiculoSpecification.filterBy(marca, modelo, matricula);
        return vehiculoRepository.findAll(specification);
    }

    @Override
    @Transactional(readOnly = true) //
    public Page<VehiculoEntity> getVehiculosByFilterOrder(String marca, Short modelo, String matricula, int page, int size, String sortBy, String sortDirection) {
        Specification<VehiculoEntity> specification = VehiculoSpecification.filterBy(marca, modelo, matricula);

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return vehiculoRepository.findAllOrder(specification, pageable);
    }

    @Override
    @Transactional
    public VehiculoEntity save(VehiculoDTO vehiculoDTO, BindingResult result) throws BussinesRuleValidationException {
        if (result.hasErrors()) {
            BussinesRuleValidationException exception = new BussinesRuleValidationException(INFO_URL, result);
            throw exception;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate fechaEntrada = LocalDate.parse(vehiculoDTO.getFecha(), formatter);
            Short modelo = Short.valueOf(vehiculoDTO.getModelo());
            VehiculoEntity c = new VehiculoEntity();
            c.setMatricula(vehiculoDTO.getMatricula());
            c.setMarca(vehiculoDTO.getMarca());
            c.setColor(vehiculoDTO.getColor());
            c.setModelo(modelo);
            c.setFecha(fechaEntrada);
            VehiculoEntity save = vehiculoRepository.save(c);
            return save;
        }
    }

    @Override
    @Transactional//
    public void put(VehiculoDTO vehiculoDTO, BindingResult result, Long id) throws BussinesRuleException, BussinesRuleValidationException {
        Optional<VehiculoEntity> find = vehiculoRepository.findById(id);

        if (!find.isEmpty()) {
            if (result.hasErrors()) {
                BussinesRuleValidationException exception = new BussinesRuleValidationException(INFO_URL, result);
                throw exception;
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                LocalDate fechaEntrada = LocalDate.parse(vehiculoDTO.getFecha(), formatter);
                Short modelo = Short.valueOf(vehiculoDTO.getModelo());
                VehiculoEntity c = new VehiculoEntity();
                find.get().setMatricula(vehiculoDTO.getMatricula());
                find.get().setMarca(vehiculoDTO.getMarca());
                find.get().setColor(vehiculoDTO.getColor());
                find.get().setModelo(modelo);
                find.get().setFecha(fechaEntrada);
                VehiculoEntity save = vehiculoRepository.save(find.get());
                vehiculoRepository.save(save);
            }
        } else {
            BussinesRuleException exception = new BussinesRuleException(INFO_URL);
            throw exception;
        }
    }

    @Override
    @Transactional //
    public void delete(Long id) throws BussinesRuleException {
        Optional<VehiculoEntity> cuenta = vehiculoRepository.findById(id);
        if (!cuenta.isEmpty()) {
            vehiculoRepository.delete(cuenta.get());
        } else {
            BussinesRuleException exception = new BussinesRuleException(INFO_URL);
            throw exception;
        }
    }

    @Override
    @Transactional(readOnly = true) //
    public List<VehiculoEntity> findByModel(Short modelo) {
        return vehiculoRepository.filterByModel(modelo);
    }

    @Override
    @Transactional(readOnly = true) //
    public List<VehiculoEntity> findByBrand(String marca) {
        return vehiculoRepository.filterByBrand(marca);
    }

    @Override
    @Transactional(readOnly = true) //
    public List<VehiculoEntity> findByLicensePlate(String matricula) {
        return vehiculoRepository.filterByLicensePlate(matricula);
    }
}
