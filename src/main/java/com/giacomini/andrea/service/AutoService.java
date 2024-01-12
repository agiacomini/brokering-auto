package com.giacomini.andrea.service;

import com.giacomini.andrea.entity.Auto;
import com.giacomini.andrea.repository.IAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoService implements IAutoService{

    @Autowired
    IAutoRepository autoRepository;
    @Override
    public List<Auto> selectAllAuto() {
        return autoRepository.findAll();
    }

    @Override
    public long countAuto() {
        return autoRepository.count();
    }

    @Override
    public List<Auto> selectAutoWithAnzianitaAndCambioAutomatico(Integer anzianita, boolean cambioAutomatico) {
//        List<Auto> autoSelected = autoRepository.selByAnzianitaAndAlimentazione(anzianita, cambioAutomatico);
        List<Auto> autoSelected = autoRepository.findAutosByAnzianitaEqualsAndCambioAutomaticoEquals(anzianita, cambioAutomatico);
        return autoSelected;
    }

    @Override
    public List<Auto> selectAutoWithAnzianitaAndAlimentazione(Integer anzianita, String alimentazione) {
        List<Auto> autoSelected = autoRepository.findAutosByAnzianitaAndAlimentazione(anzianita, alimentazione);
        return autoSelected;
    }

    @Override
    public List<Auto> selectAutoWithAnzianitaAndCambioAutomaticoAndAlimentazioner(Integer anzianita,
                                                                                  boolean cambioAutomatico,
                                                                                  String alimentazione) {
        List<Auto> autoSelected =
                autoRepository.selectAutoByAnzianitaAndCambioAutomaticoAndAlimentazione(anzianita,
                                                                                        cambioAutomatico,
                                                                                        alimentazione);
        return autoSelected;
    }

    @Override
    public List<Auto> selectByAnzianitaAndCambioAutomaticoAndFasciaDiPrezzo(Integer anzianita,
                                                                            boolean cambioAutomatico,
                                                                            String fasciaDiPrezzo) {
        List<Auto> autoSelected =
                autoRepository.selectAutoByAnzianitaAndCambioAutomaticoAndFasciaPrezzo(anzianita,
                                                                                       cambioAutomatico,
                                                                                       fasciaDiPrezzo);
        return autoSelected;
    }

    @Override
    public Optional<Auto> selectAutoById(Long id) {
        return autoRepository.findById(id);
    }
    @Override
    public void insertAuto(Auto auto) {
        autoRepository.save(auto);
    }
    @Override
    public Auto updateAuto(Auto auto) {
        return autoRepository.save(auto);
    }
    @Override
    public void deleteAuto(Auto auto) { autoRepository.delete(auto); }
}