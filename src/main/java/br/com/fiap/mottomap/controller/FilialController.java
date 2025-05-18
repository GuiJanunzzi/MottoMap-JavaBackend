package br.com.fiap.mottomap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.fiap.mottomap.filter.FilialFilter;
import br.com.fiap.mottomap.model.Filial;
import br.com.fiap.mottomap.repository.FilialRepository;
import br.com.fiap.mottomap.specification.FilialSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/filial")
@CrossOrigin
@Tag(name = "Filial", description = "API para gerenciamento de filiais da Mottu")
public class FilialController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private FilialRepository repository;

    //----- Documentação Swagger -----
    @Operation(
        summary = "Listar todas as Filiais",
        description = "Retorna uma lista com todas as filiais cadastradas no sistema",
        responses = {
                @ApiResponse(responseCode = "200", description = "Lista de filiais retornada com sucesso")
        }
    )
    //----- Documentação Swagger -----
    @GetMapping
    @Cacheable("filial")
    public Page<Filial> index(FilialFilter filter, @PageableDefault(size = 5) Pageable pageable){

        var specification = FilialSpecification.withFilter(filter);
        return repository.findAll(specification, pageable);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Cadastrar Filial",
        description = "Coleta os dados para adicionar uma filial no sistema",
        responses = {
                @ApiResponse(responseCode = "201", description = "Filial cadastrada com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    //----- Documentação Swagger -----
    @PostMapping
    @CacheEvict(value = "filial", allEntries = true)
    public ResponseEntity<Filial> create(@RequestBody @Valid Filial filial){
        log.info("Cadastrando Filial");
        repository.save(filial);
        return ResponseEntity.status(201).body(filial);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Buscar Filial por ID",
        description = "Retorna os dados de uma filial com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "200", description = "Filial encontrada"),
                @ApiResponse(responseCode = "404", description = "Filial não encontrada")
        }
    )
    //----- Documentação Swagger -----
    @GetMapping({"/{id}"})
    public Filial get(@PathVariable Long id){
        log.info("Buscando filial por ID: " + id);
        return getFilial(id);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Atualizar Filial",
        description = "Atualiza os dados de uma filial existente com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "200", description = "Filial atualizada com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                @ApiResponse(responseCode = "404", description = "Filial não encontrada")
        }
    )
    //----- Documentação Swagger -----
    @PutMapping({"/{id}"})
    @CacheEvict(value = "filial", allEntries = true)
    public Filial update(@PathVariable Long id, @RequestBody @Valid Filial filial){
        log.info("Atualizando filial " + filial.toString());

        getFilial(id);
        filial.setId(id);
        repository.save(filial);

        return filial;
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Deletar Filial",
        description = "Remove uma Filial existente com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "204", description = "Filial removida com sucesso"),
                @ApiResponse(responseCode = "404", description = "Filial não encontrada")
        }
    )
    //----- Documentação Swagger -----
    @DeleteMapping({"/{id}"})
    @CacheEvict(value = "filial", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Apagando Filial ID: " + id);
        repository.delete(getFilial(id));
    }
    
    private Filial getFilial(Long id) {
        return repository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }
}
