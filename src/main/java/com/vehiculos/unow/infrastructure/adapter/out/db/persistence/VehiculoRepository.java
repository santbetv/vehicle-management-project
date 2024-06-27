package com.vehiculos.unow.infrastructure.adapter.out.db.persistence;

import com.vehiculos.unow.infrastructure.adapter.out.db.model.VehiculoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long>, JpaSpecificationExecutor<VehiculoEntity> {


    default Page<VehiculoEntity> findAllOrder(Specification<VehiculoEntity> specification, Pageable pageable) {
        return findAll(specification, pageable);
    }
    @Query("SELECT m FROM VehiculoEntity m  WHERE m.marca = :marca")
    public List<VehiculoEntity> filterByBrand(@Param("marca") String marca);

    @Query("SELECT m FROM VehiculoEntity m  WHERE m.modelo = :modelo")
    public List<VehiculoEntity> filterByModel(@Param("modelo") Short modelo);

    @Query("SELECT m FROM VehiculoEntity m  WHERE m.matricula = :matricula")
    public List<VehiculoEntity> filterByLicensePlate(@Param("matricula") String matricula);
}
