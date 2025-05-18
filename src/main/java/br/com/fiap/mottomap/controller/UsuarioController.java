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

import br.com.fiap.mottomap.dto.UsuarioRequestDto;
import br.com.fiap.mottomap.filter.UsuarioFilter;
import br.com.fiap.mottomap.model.Filial;
import br.com.fiap.mottomap.model.Usuario;
import br.com.fiap.mottomap.repository.FilialRepository;
import br.com.fiap.mottomap.repository.UsuarioRepository;
import br.com.fiap.mottomap.specification.UsuarioSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
@Tag(name = "Usuario", description = "API para gerenciamento de usuarios no sistema da Mottu")
public class UsuarioController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private FilialRepository filialRepository;

    //----- Documentação Swagger -----
    @Operation(
        summary = "Listar todos os usuários",
        description = "Retorna uma lista com todos os usuários cadastrados no sistema",
        responses = {
                @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
        }
    )
    //----- Documentação Swagger -----
    @GetMapping
    public Page<Usuario> index(UsuarioFilter filter, @PageableDefault(size = 5) Pageable pageable){

        var specification = UsuarioSpecification.withFilter(filter);
        return repository.findAll(specification, pageable);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Cadastrar Usuário",
        description = "Coleta os dados para adicionar um usuário no sistema",
        responses = {
                @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    //----- Documentação Swagger -----
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody @Valid UsuarioRequestDto dto){
        log.info("Cadastrando Usuario");

        Filial filial = filialRepository.findById(dto.getFilial())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filial não encontrada"));

        Usuario usuario = Usuario.builder()
            .nome(dto.getNome())
            .email(dto.getEmail())
            .senha(dto.getSenha())
            .cargoUsuario(dto.getCargoUsuario())
            .filial(filial)
            .build();

        repository.save(usuario);
        return ResponseEntity.status(201).body(usuario);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Buscar Usuário por ID",
        description = "Retorna os dados de um usuário com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    //----- Documentação Swagger -----
    @GetMapping({"/{id}"})
    public Usuario get(@PathVariable Long id){
        log.info("Buscando usuário por ID: " + id);
        return getUsuario(id);
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Atualizar Usuário",
        description = "Atualiza os dados de um usuário existente com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    //----- Documentação Swagger -----
    @PutMapping({"/{id}"})
    public Usuario update(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDto dto){
        log.info("Atualizando usuário " + dto.toString());

        Filial filial = filialRepository.findById(dto.getFilial())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filial não encontrada"));

        Usuario usuario = Usuario.builder()
            .nome(dto.getNome())
            .email(dto.getEmail())
            .senha(dto.getSenha())
            .cargoUsuario(dto.getCargoUsuario())
            .filial(filial)
            .build();

        getUsuario(id);
        usuario.setId(id);
        repository.save(usuario);

        return usuario;
    }

    //----- Documentação Swagger -----
    @Operation(
        summary = "Deletar Usuário",
        description = "Remove um usuário existente com base no ID fornecido",
        responses = {
                @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
                @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    //----- Documentação Swagger -----
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Apagando Usuário ID: " + id);
        repository.delete(getUsuario(id));
    }
    
    private Usuario getUsuario(Long id) {
        return repository
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }    
}
