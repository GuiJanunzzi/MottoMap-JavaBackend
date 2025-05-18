package br.com.fiap.mottomap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.mottomap.dto.MotoRequestDto;
import br.com.fiap.mottomap.model.Filial;
import br.com.fiap.mottomap.model.Moto;
import br.com.fiap.mottomap.model.MotoFilter;
import br.com.fiap.mottomap.repository.FilialRepository;
import br.com.fiap.mottomap.repository.MotoRepository;
import br.com.fiap.mottomap.specification.MotoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/moto")
@CrossOrigin
@Tag(name = "Moto", description = "API para gerenciamento de motos no sistema da Mottu")
public class MotoController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MotoRepository repository;

    @Autowired
    private FilialRepository filialRepository;

    //----- Documentação Swagger -----
    @Operation(
        summary = "Listar todos as Motos",
        description = "Retorna uma lista com todas as motos cadastradas no sistema",
        responses = {
                @ApiResponse(responseCode = "200", description = "Lista de motos retornada com sucesso")
        }
    )
    //----- Documentação Swagger -----
    @GetMapping
    public Page<Moto> index(MotoFilter filter, @PageableDefault(size = 5) Pageable pageable){

        var specification = MotoSpecification.withFilter(filter);
        return repository.findAll(specification, pageable);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Cadastrar Moto",
        description = "Coleta os dados para adicionar uma moto no sistema",
        responses = {
                @ApiResponse(responseCode = "201", description = "Moto cadastrada com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    //----- Documentação Swagger -----
    @PostMapping
    public ResponseEntity<Moto> create(@RequestBody @Valid MotoRequestDto dto){
        log.info("Cadastrando Moto");

        Filial filial = filialRepository.findById(dto.getFilial())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filial não encontrada"));

        Moto moto = Moto.builder()
            .placa(dto.getPlaca())
            .placa(dto.getPlaca())
            .chassi(dto.getChassi())
            .modeloMoto(dto.getModeloMoto())
            .ano(dto.getAno())
            .statusMoto(dto.getStatusMoto())
            .filial(filial)
            .build();

        repository.save(moto);
        return ResponseEntity.status(201).body(moto);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Buscar Moto por ID",
        description = "Retorna os dados de uma moto com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "200", description = "Moto encontrada"),
                @ApiResponse(responseCode = "404", description = "Moto não encontrada")
        }
    )
    //----- Documentação Swagger -----
    @GetMapping({"/{id}"})
    public Moto get(@PathVariable Long id){
        log.info("Buscando moto por ID: " + id);
        return getMoto(id);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Atualizar Moto",
        description = "Atualiza os dados de uma moto existente com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "200", description = "Moto atualizada com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                @ApiResponse(responseCode = "404", description = "Moto não encontrada")
        }
    )
    //----- Documentação Swagger -----
    @PutMapping({"/{id}"})
    public Moto update(@PathVariable Long id, @RequestBody @Valid MotoRequestDto dto){
        log.info("Atualizando usuário " + dto.toString());

        Filial filial = filialRepository.findById(dto.getFilial())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filial não encontrada"));

        Moto moto = Moto.builder()
            .placa(dto.getPlaca())
            .placa(dto.getPlaca())
            .chassi(dto.getChassi())
            .modeloMoto(dto.getModeloMoto())
            .ano(dto.getAno())
            .statusMoto(dto.getStatusMoto())
            .filial(filial)
            .build();

        getMoto(id);
        moto.setId(id);
        repository.save(moto);

        return moto;
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Deletar Moto",
        description = "Remove uma moto existente com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "204", description = "Moto removida com sucesso"),
                @ApiResponse(responseCode = "404", description = "Moto não encontrada")
        }
    )
    //----- Documentação Swagger -----
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Apagando moto ID: " + id);
        repository.delete(getMoto(id));
    }
    
    private Moto getMoto(Long id) {
        return repository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }    
}
