package com.cesde.proyecto_integrador.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cesde.proyecto_integrador.model.Resultado;
import com.cesde.proyecto_integrador.model.User;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    List<Resultado> findByUsuario(User usuario);
}
