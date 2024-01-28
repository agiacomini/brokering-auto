package com.giacomini.andrea.seeding;

import com.giacomini.andrea.entity.Auto;
import com.giacomini.andrea.repository.IAutoRepository;
import lombok.extern.java.Log;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log
@Component
public class DatabaseSeeder {

    private IAutoRepository autoRepository;
    public DatabaseSeeder(IAutoRepository autoRepository){
        this.autoRepository = autoRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent){
        seedAutoTable();
    }

    private void seedAutoTable(){

        Auto auto = autoRepository.selectTestAuto(244, false, "0 a 5K", 1, true, "benzina");
        if(auto == null){
            Auto autoTest = new Auto();
            autoTest.setId(244L);
            autoTest.setQuattroPerQuattro(false);
            autoTest.setFasciaDiPrezzo("0 a 5K");
            autoTest.setAnzianita(1);
            autoTest.setCambioAutomatico(true);
            autoTest.setAlimentazione("benzina");
            autoRepository.save(autoTest);
            log.info("Auto Seeded");
        } else {
            log.info("Auto Seeding Not required");
        }
    }
}