package com.vehiculos.unow.application.serviceimpl;

import com.vehiculos.unow.application.port.out.db.VehiculoPortService;
import com.vehiculos.unow.domain.dto.VehiculoDTO;
import com.vehiculos.unow.infrastructure.adapter.out.db.model.VehiculoEntity;
import com.vehiculos.unow.infrastructure.adapter.out.db.persistence.VehiculoRepository;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleException;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    void testFindAll() {
        // Arrange
        VehiculoEntity vehiculo1 = new VehiculoEntity();
        vehiculo1.setIdVehiculo(1L);
        vehiculo1.setMarca("Chevrolet");
        vehiculo1.setModelo((short) 2023);
        vehiculo1.setMatricula("0000GXP");
        vehiculo1.setColor("Azul perlado");
        vehiculo1.setFecha(LocalDate.of(1990, 3, 12));

        VehiculoEntity vehiculo2 = new VehiculoEntity();
        vehiculo2.setIdVehiculo(2L);
        vehiculo2.setMarca("Volvo");
        vehiculo2.setModelo((short) 2021);
        vehiculo2.setMatricula("1234XYZ");
        vehiculo2.setColor("Gris metalizado");
        vehiculo2.setFecha(LocalDate.of(2018, 5, 15));

        List<VehiculoEntity> vehiculos = Arrays.asList(vehiculo1, vehiculo2);

        Mockito.when(vehiculoRepository.findAll()).thenReturn(vehiculos);

        // Act
        List<VehiculoEntity> result = vehiculoService.findAll();

        // Assert
        assertEquals(2, result.size()); // Verifica que se devuelvan dos entidades de vehículos
        assertEquals("Chevrolet", result.get(0).getMarca()); // Verifica la primera entidad
        assertEquals("Volvo", result.get(1).getMarca()); // Verifica la segunda entidad

        // Verifica que se llamó al método findAll del repositorio una vez
        Mockito.verify(vehiculoRepository, Mockito.times(1)).findAll();
    }

    @Test
    void saveValidVehiculoDTOReturnsSavedEntity() throws BussinesRuleValidationException {
        // Arrange
        VehiculoDTO vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setMarca("Chevrolet");
        vehiculoDTO.setModelo("2023");
        vehiculoDTO.setMatricula("0000GXP");
        vehiculoDTO.setColor("Azul perlado");
        vehiculoDTO.setFecha("1990-03-12");

        BindingResult result = Mockito.mock(BindingResult.class);
        Mockito.when(result.hasErrors()).thenReturn(false);

        LocalDate fechaEntrada = LocalDate.parse(vehiculoDTO.getFecha(), DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        Short modelo = Short.valueOf(vehiculoDTO.getModelo());

        VehiculoEntity entityToSave = new VehiculoEntity();
        entityToSave.setMatricula(vehiculoDTO.getMatricula());
        entityToSave.setMarca(vehiculoDTO.getMarca());
        entityToSave.setColor(vehiculoDTO.getColor());
        entityToSave.setModelo(modelo);
        entityToSave.setFecha(fechaEntrada);

        Mockito.when(vehiculoRepository.save(Mockito.any(VehiculoEntity.class))).thenReturn(entityToSave);

        // Act
        VehiculoEntity savedEntity = vehiculoService.save(vehiculoDTO, result);

        // Assert
        assertNotNull(savedEntity);
        assertEquals(vehiculoDTO.getMatricula(), savedEntity.getMatricula());
        assertEquals(vehiculoDTO.getMarca(), savedEntity.getMarca());
        assertEquals(vehiculoDTO.getColor(), savedEntity.getColor());
        assertEquals(modelo, savedEntity.getModelo());
        assertEquals(fechaEntrada, savedEntity.getFecha());

        Mockito.verify(vehiculoRepository, Mockito.times(1)).save(Mockito.any(VehiculoEntity.class));
    }

    @Test
    void save_InvalidVehiculoDTO_ThrowsException() {
        // Arrange
        VehiculoDTO vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setMarca("Chevrolet");
        vehiculoDTO.setModelo("2023");
        vehiculoDTO.setMatricula("0000GXP");
        vehiculoDTO.setColor("Azul perlado");
        vehiculoDTO.setFecha("1990-03-12");

        BindingResult result = Mockito.mock(BindingResult.class);
        Mockito.when(result.hasErrors()).thenReturn(true);

        // Act & Assert
        assertThrows(BussinesRuleValidationException.class, () -> {
            vehiculoService.save(vehiculoDTO, result);
        });

        Mockito.verify(vehiculoRepository, Mockito.never()).save(Mockito.any(VehiculoEntity.class));
    }

    @Test
    void testPutExistingVehiculo() throws BussinesRuleException, BussinesRuleValidationException {
        // Arrange
        Long id = 1L;
        VehiculoDTO vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setMatricula("1234XYZ");
        vehiculoDTO.setMarca("Volvo");
        vehiculoDTO.setColor("Gris metalizado");
        vehiculoDTO.setModelo("2021");
        vehiculoDTO.setFecha("2018-05-15");

        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);

        LocalDate fechaEntrada = LocalDate.parse(vehiculoDTO.getFecha(), DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        Short modelo = Short.valueOf(vehiculoDTO.getModelo());

        VehiculoEntity entityToSave = new VehiculoEntity();
        entityToSave.setMatricula(vehiculoDTO.getMatricula());
        entityToSave.setMarca(vehiculoDTO.getMarca());
        entityToSave.setColor(vehiculoDTO.getColor());
        entityToSave.setModelo(modelo);
        entityToSave.setFecha(fechaEntrada);

        Optional<VehiculoEntity> mockOptional = Optional.of(entityToSave); // Mock de entidad encontrada en el repositorio
        Mockito.when(vehiculoRepository.findById(id)).thenReturn(mockOptional);

        // Act
        vehiculoService.put(vehiculoDTO, mockBindingResult, id);

        // Assert
        Mockito.verify(vehiculoRepository, Mockito.times(1)).findById(id); // Verifica que se llamó a findById una vez con el id especificado
        Mockito.verify(vehiculoRepository, Mockito.times(1)).save(Mockito.any(VehiculoEntity.class)); // Verifica que se llamó a save una vez con cualquier VehiculoEntity
    }

    @Test
    void testPutNonExistingVehiculo() throws BussinesRuleValidationException, BussinesRuleException {
        // Arrange
        Long id = 1L;
        VehiculoDTO vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setMatricula("1234XYZ");
        vehiculoDTO.setMarca("Volvo");
        vehiculoDTO.setColor("Gris metalizado");
        vehiculoDTO.setModelo("2021");
        vehiculoDTO.setFecha("2018-05-15");

        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);

        // Simular que no se encuentra el vehículo
        Mockito.when(vehiculoRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BussinesRuleException.class, () -> vehiculoService.put(vehiculoDTO, mockBindingResult, id));

        // Verificar que se llamó a findById una vez con el id especificado
        Mockito.verify(vehiculoRepository, Mockito.times(1)).findById(id);

        // Verificar que no se llamó a save en el repositorio
        Mockito.verify(vehiculoRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteExistingVehiculo() throws BussinesRuleException {
        // Arrange
        Long id = 1L;
        VehiculoEntity vehiculoEntity = new VehiculoEntity();
        vehiculoEntity.setIdVehiculo(id);

        // Simular que el vehículo existe en la base de datos
        Mockito.when(vehiculoRepository.findById(id)).thenReturn(Optional.of(vehiculoEntity));

        // Act
        vehiculoService.delete(id);

        // Assert
        Mockito.verify(vehiculoRepository, Mockito.times(1)).findById(id);
        Mockito.verify(vehiculoRepository, Mockito.times(1)).delete(vehiculoEntity);
    }

    @Test
    void testDeleteNonExistingVehiculo() {
        // Arrange
        Long id = 1L;

        // Simular que el vehículo no existe en la base de datos
        Mockito.when(vehiculoRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BussinesRuleException.class, () -> vehiculoService.delete(id));

        // Verificar que se llamó a findById una vez con el ID especificado
        Mockito.verify(vehiculoRepository, Mockito.times(1)).findById(id);
    }
}