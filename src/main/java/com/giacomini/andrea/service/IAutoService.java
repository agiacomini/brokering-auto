package com.giacomini.andrea.service;

import com.giacomini.andrea.entity.Auto;

import java.util.List;
import java.util.Optional;

public interface IAutoService {
    public List<Auto> selectAllAuto();
    public long countAuto();
    // ANZIANITA + CAMBIO AUTOMATICO
    public List<Auto> selectAutoWithAnzianitaAndCambioAutomatico(Integer anzianita, boolean cambioAutomatico);
    // ANZIANITA + ALIMENTAZIONE
    public List<Auto> selectAutoWithAnzianitaAndAlimentazione(Integer anzianita, String alimentazione);
    // ANZIANITA + CAMBIO AUTOMATICO + ALIMENTAZIONE
    public List<Auto> selectAutoWithAnzianitaAndCambioAutomaticoAndAlimentazioner(Integer anzianita,
                                                                                  boolean cambioAutomatico,
                                                                                  String alimentazione);
    // ANZIANITA + CAMBIO AUTOMATICO + FASCIA DI PREZZO
    public List<Auto> selectByAnzianitaAndCambioAutomaticoAndFasciaDiPrezzo(Integer anzianita,
                                                                         boolean cambioAutomatico,
                                                                         String fasciaDiPrezzo);

    public Optional<Auto> selectAutoById(Long id);
    public void insertAuto(Auto auto);
    public Auto updateAuto(Auto auto);
    public void deleteAuto(Auto auto);
}