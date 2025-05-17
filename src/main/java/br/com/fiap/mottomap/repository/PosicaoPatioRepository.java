package br.com.fiap.mottomap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.mottomap.model.PosicaoPatio;

public interface PosicaoPatioRepository extends JpaRepository<PosicaoPatio, Long>, JpaSpecificationExecutor<PosicaoPatio>{
    
}
