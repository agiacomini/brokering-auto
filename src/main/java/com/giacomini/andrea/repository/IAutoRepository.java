package com.giacomini.andrea.repository;

import com.giacomini.andrea.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAutoRepository extends JpaRepository<Auto, Long> {
    // Query Method
    List<Auto> findAutosByAnzianitaEqualsAndCambioAutomaticoEquals(Integer anzianita, boolean cambioAutomatico);
    List<Auto> findAllByAnzianitaAndCambioAutomatico(Integer anzianita, boolean cambioAutomatico);
    List<Auto> findAutosByAnzianitaAndAlimentazione(Integer anzianita, String alimentazione);

    // JPQL
    @Query(value = "SELECT a FROM Auto a WHERE a.id = :id")
    Auto selectAutoById(@Param("id") Long id);

    @Query(value = "SELECT a FROM Auto a WHERE a.anzianita = :anzianita AND a.cambioAutomatico = :cambioAutomatico")
    List<Auto> selByAnzianitaAndAlimentazione(@Param("anzianita") Integer anzianita, @Param("cambioAutomatico") boolean cambioAutomatico);

    // NATIVE QUERY
    @Query(value = "SELECT * FROM auto a WHERE a.anzianita = :anzianita AND a.cambio_automatico = :cambioAutomatico AND a.fascia_di_prezzo = :fasciaDiPrezzo", nativeQuery = true)
    List<Auto> selectAutoByAnzianitaAndCambioAutomaticoAndFasciaPrezzo(@Param("anzianita") Integer anzianita,
                                                                       @Param("cambioAutomatico") boolean cambioAutomatico,
                                                                       @Param("fasciaDiPrezzo") String fasciaDiPrezzo);

    @Query(value = "SELECT * FROM auto a WHERE a.anzianita = :anzianita AND a.cambio_automatico = :cambioAutomatico AND a.alimentazione = :alimentazione", nativeQuery = true)
    List<Auto> selectAutoByAnzianitaAndCambioAutomaticoAndAlimentazione(@Param("anzianita") Integer anzianita,
                                                                       @Param("cambioAutomatico") boolean cambioAutomatico,
                                                                       @Param("alimentazione") String alimentazione);

    @Query(value = "select max(id) from auto;", nativeQuery = true)
    public Long getMaxValueId();
}