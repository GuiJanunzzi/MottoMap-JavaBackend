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

import br.com.fiap.mottomap.model.Filial;
import br.com.fiap.mottomap.model.FilialFilter;
import br.com.fiap.mottomap.repository.FilialRepository;
import br.com.fiap.mottomap.specification.FilialSpecification;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/filial")
@CrossOrigin
public class FilialController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private FilialRepository repository;

    @GetMapping
    public Page<Filial> index(FilialFilter filter, @PageableDefault(size = 5) Pageable pageable){

        var specification = FilialSpecification.withFilter(filter);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    public ResponseEntity<Filial> create(@RequestBody @Valid Filial filial){
        log.info("Cadastrando Filial");
        repository.save(filial);
        return ResponseEntity.status(201).body(filial);
    }

    @GetMapping({"/{id}"})
    public Filial get(@PathVariable Long id){
        log.info("Buscando filial por ID: " + id);
        return getFilial(id);
    }

    @PutMapping({"/{id}"})
    public Filial update(@PathVariable Long id, @RequestBody @Valid Filial filial){
        log.info("Atualizando filial " + filial.toString());

        getFilial(id);
        filial.setId(id);
        repository.save(filial);

        return filial;
    }

    @DeleteMapping({"/{id}"})
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
