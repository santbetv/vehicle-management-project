package com.vehiculos.unow.infrastructure.adapter.in.web;

import com.vehiculos.unow.application.port.in.web.WebPort;
import com.vehiculos.unow.application.port.out.db.VehiculoPortService;
import com.vehiculos.unow.domain.dto.VehiculoDTO;
import com.vehiculos.unow.infrastructure.adapter.out.db.model.VehiculoEntity;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleException;
import com.vehiculos.unow.infrastructure.exception.BussinesRuleValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Vehiculos", description = "Operaciones relacionadas con vehiculos")
public class VehiculoRestController implements WebPort {
    private static final Logger LOG = LoggerFactory.getLogger(VehiculoRestController.class);

    private VehiculoPortService vehiculoPortService;

    public VehiculoRestController(VehiculoPortService vehiculoPortService) {
        this.vehiculoPortService = vehiculoPortService;
    }
    @Override
    @GetMapping("/vehiculos")
    @Operation(
            summary = "Listar todos los vehiculos",
            description = "Endpoint para listar todos los vehiculos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de vehiculos", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehiculoEntity.class))))
            }
    )
    public List<VehiculoEntity> list() {
        return vehiculoPortService.findAll();
    }

    @Override
    @GetMapping("/vehiculos/search")
    @Operation(
            summary = "Buscar vehiculos por filtros",
            description = "Endpoint para buscar vehiculos por marca, modelo o matricula",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de vehiculos filtrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehiculoEntity.class))))
            }
    )
    public List<VehiculoEntity> getVehiculosByFilter(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Short modelo,
            @RequestParam(required = false) String matricula) {
        return vehiculoPortService.getVehiculosByFilter(marca, modelo, matricula);
    }

    @Override
    @GetMapping("/vehiculos/order")
    @Operation(
            summary = "Buscar vehiculos con paginación y ordenamiento",
            description = "Endpoint para buscar vehiculos por filtros con paginación y ordenamiento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de vehiculos filtrados con paginación y ordenamiento", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehiculoEntity.class))))
            }
    )
    public Page<VehiculoEntity> getVehiculosByFilter(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Short modelo,
            @RequestParam(required = false) String matricula,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idVehiculo") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return vehiculoPortService.getVehiculosByFilterOrder(marca, modelo, matricula, page, size, sortBy, sortDirection);
    }

    @Override
    @GetMapping("/vehiculos/{modelo}")
    @Operation(
            summary = "Buscar vehiculos por modelo",
            description = "Endpoint para buscar vehiculos por modelo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de vehiculos filtrados por modelo", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehiculoEntity.class))))
            }
    )
    public List<VehiculoEntity> getModelo(@PathVariable Short modelo) {
        return vehiculoPortService.findByModel(modelo);
    }

    @Override
    @PostMapping("/vehiculos")
    @Operation(
            summary = "Crear un nuevo vehiculo",
            description = "Endpoint para crear un nuevo vehiculo en el sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del vehiculo a crear",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VehiculoDTO.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de vehiculo",
                                    value = "{ \"marca\": \"Volvo\", \"modelo\": \"2021\", \"matricula\": \"1234XYZ\", \"color\": \"Gris metalizado\", \"fecha\": \"2018-05-15\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehiculo creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos del vehiculo invalidos")
            }
    )
    public ResponseEntity<?> post(@Valid @RequestBody VehiculoDTO input, BindingResult result) throws BussinesRuleValidationException {
        VehiculoEntity save = vehiculoPortService.save(input, result);
        return ResponseEntity.ok(save);
    }


    @Override
    @DeleteMapping("/vehiculos/{id}")
    @Operation(
            summary = "Eliminar un vehiculo",
            description = "Endpoint para eliminar un vehiculo por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehiculo eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
            }
    )
    public ResponseEntity<?> delete(@PathVariable Long id) throws BussinesRuleException {
        vehiculoPortService.delete(id);
        return new ResponseEntity(HttpStatus.OK);

    }

    @Override
    @PutMapping("/vehiculos/{id}")
    @Operation(
            summary = "Actualizar un vehiculo",
            description = "Endpoint para actualizar un vehiculo por su ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del vehiculo a actualizar",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = VehiculoDTO.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de vehiculo",
                                    value = "{ \"marca\": \"Volvo\", \"modelo\": \"2021\", \"matricula\": \"1234XYZ\", \"color\": \"Gris metalizado\", \"fecha\": \"2018-05-15\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehiculo actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos del vehiculo invalidos"),
                    @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
            }
    )
    public ResponseEntity<?> put(@Valid
                                 @RequestBody VehiculoDTO input, BindingResult result,
                                 @PathVariable Long id) throws BussinesRuleException, BussinesRuleValidationException {
        vehiculoPortService.put(input, result, id);
        return new ResponseEntity(HttpStatus.OK);
    }
}


