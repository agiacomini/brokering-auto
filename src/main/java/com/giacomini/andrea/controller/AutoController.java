package com.giacomini.andrea.controller;

import com.giacomini.andrea.dto.InfoMsg;
import com.giacomini.andrea.entity.Auto;
import com.giacomini.andrea.exception.NotFoundException;
import com.giacomini.andrea.service.AutoService;
import com.giacomini.andrea.utils.Constants;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@RestController
@RequestMapping("/api/v1/auto")
public class AutoController {

    @Autowired
    private AutoService autoService;
    @GetMapping(value = "/select/all", produces = "application/json")
    public ResponseEntity<List<Auto>> getAllAuto(){
        List<Auto> autoList = autoService.selectAllAuto();
        return new ResponseEntity<List<Auto>>(autoList, HttpStatus.OK) ;
    }

    @GetMapping(value = "/count", produces = "application/json")
    public ResponseEntity<InfoMsg> countAuto(){
        long countAuto = autoService.countAuto();
        return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), String.format("Le Auto presenti nel sistema sono %s", countAuto)),
                                            HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(value = "/select/{id}", produces = "application/json")
    public ResponseEntity<Auto> getAutoById(@PathVariable Long id){
        Optional<Auto> auto = autoService.selectAutoById(id);

        if(auto.isEmpty()){
            String errorMsg = String.format("L'auto con id %s non e' stato trovato!", id);

            log.warning(errorMsg);

            throw new NotFoundException(errorMsg);
        }
        return new ResponseEntity<Auto>(auto.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/select", produces = "application/json")
    public ResponseEntity<List<Auto>> getAutosByFilter(@RequestParam("anzianita") String anzianita,
                                                       @RequestParam(value = "cambioAutomatico", required = false) String cambioAutomatico,
                                                       @RequestParam(value = "alimentazione", required = false) String alimentazione,
                                                       @RequestParam(value = "fasciaDiPrezzo", required = false) String fasciaDiPrezzo,
                                                       @RequestParam(required = false, defaultValue = "false") String quattroPerQuattro){

        Integer anz = Integer.valueOf(anzianita);
        boolean cAuto = Boolean.valueOf(cambioAutomatico);
        List<Auto> autoSelected = new ArrayList<>();
        if(alimentazione != null){
            autoSelected = autoService.selectAutoWithAnzianitaAndAlimentazione(anz, alimentazione);
        } else if (cambioAutomatico != null) {
            if(fasciaDiPrezzo != null)
                autoSelected = autoService.selectByAnzianitaAndCambioAutomaticoAndFasciaDiPrezzo(anz, cAuto, fasciaDiPrezzo);
            else
                autoSelected = autoService.selectAutoWithAnzianitaAndCambioAutomatico(anz, cAuto);
        }
        return new ResponseEntity<List<Auto>>(autoSelected, HttpStatus.OK);
    }

    @GetMapping(value = "select/filtro1", produces = "application/json")
    public ResponseEntity<List<Auto>> getAutosWithAnzianitaAndCambioAutomatico(){
        List<Auto> autoSelected = autoService.selectAutoWithAnzianitaAndCambioAutomatico(1, true);
        return new ResponseEntity<List<Auto>>(autoSelected, HttpStatus.OK);
    }

    @GetMapping(value = "select/filtro2", produces = "application/json")
    public ResponseEntity<List<Auto>> getAutosWithAnzianitaAndAlimentazione(){
        List<Auto> autoSelected = autoService.selectAutoWithAnzianitaAndAlimentazione(5, Constants.ALIMENTAZIONE_GASOLIO);
        return new ResponseEntity<List<Auto>>(autoSelected, HttpStatus.OK);
    }

    @GetMapping(value = "select/filtro3", produces = "application/json")
    public ResponseEntity<List<Auto>> getAutosWithAnzianitaAndFasciaDiPrezzoAndAlimentazione(){
        List<Auto> autoSelected =
                autoService.selectByAnzianitaAndCambioAutomaticoAndFasciaDiPrezzo(3, true, Constants.FASCIA_VALORE_5_10);
        return new ResponseEntity<List<Auto>>(autoSelected, HttpStatus.OK);
    }

    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<InfoMsg> createAuto(@Valid @RequestBody Auto auto){
        log.info("Inserimento nuova auto");
        autoService.insertAuto(auto);
        return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Inserimento Auto eseguito con successo"),
                                            HttpStatus.CREATED);
    }

    @PostMapping(value = "/insert/csv", produces = "application/json")
    public ResponseEntity<InfoMsg> createAutoByCSV(){

        try (BufferedReader br = new BufferedReader(new FileReader("auto.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(Constants.COMMA_DELIMITER);

                Auto autoToAdd = new Auto(Boolean.getBoolean(values[0]), values[1],
                        Integer.valueOf(values[2]), Boolean.valueOf(values[3]), values[4]);

                autoService.insertAuto(autoToAdd);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Inserimento Auto da file csv eseguito con successo"),
                HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<Auto> updateAutoById(@PathVariable Long id,
                                               @RequestBody Auto auto) throws NotFoundException {

        Optional<Auto> autoToUpdate = autoService.selectAutoById(id);
        if(autoToUpdate.isEmpty()){
            String errorMsg = String.format("L'auto con id %s non e' stato trovata!", id);

            log.warning(errorMsg);

            throw new NotFoundException(errorMsg);
        }

        String testValue = checkAutoEntityBeforeUpdate(auto);
        if(!testValue.isEmpty()){

            String errorMsg = testValue;

            log.warning(errorMsg);

            throw new NotFoundException(errorMsg);
        }


        autoService.insertAuto(updateAutoEntity(autoToUpdate.get(), auto));
        return new ResponseEntity<Auto>(autoToUpdate.get(), HttpStatus.OK);
    }

    @SneakyThrows
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<List<Auto>> deleteAutoById(@PathVariable Long id){

        Optional<Auto> autoToDelete = autoService.selectAutoById(id);
        if(autoToDelete.isEmpty()){
            String errorMsg = String.format("L'auto con id %s non e' stato trovata!", id);

            log.warning(errorMsg);

            throw new NotFoundException(errorMsg);
        }
        autoService.deleteAuto(autoToDelete.get());
        return new ResponseEntity<List<Auto>>(autoService.selectAllAuto(), HttpStatus.OK);
    }

    private Auto updateAutoEntity(Auto autoToUpdate, Auto newAuto){
        autoToUpdate.setAlimentazione(newAuto.getAlimentazione() != null && !newAuto.getAlimentazione().isEmpty() ? newAuto.getAlimentazione() : autoToUpdate.getAlimentazione());
        autoToUpdate.setAnzianita(newAuto.getAnzianita() != null ? newAuto.getAnzianita() : autoToUpdate.getAnzianita());
        autoToUpdate.setCambioAutomatico(newAuto.getCambioAutomatico() != null ? newAuto.getCambioAutomatico() : autoToUpdate.getCambioAutomatico());
        autoToUpdate.setFasciaDiPrezzo(newAuto.getFasciaDiPrezzo() != null && !newAuto.getFasciaDiPrezzo().isEmpty() ? newAuto.getFasciaDiPrezzo() : autoToUpdate.getFasciaDiPrezzo());
        autoToUpdate.setQuattroPerQuattro(newAuto.getQuattroPerQuattro() != null ? newAuto.getQuattroPerQuattro() : autoToUpdate.getQuattroPerQuattro());

        return autoToUpdate;
    }

    private String checkAutoEntityBeforeUpdate(Auto autoToUpdate){
        Pattern regexAlimentazione = Pattern.compile(Constants.REGEX_ALIMENTAZIONE);
        Pattern regexFasciaDiPrezzo = Pattern.compile(Constants.REGEX_FASCIA_DI_PREZZO);

        StringBuilder sb = new StringBuilder();

        Matcher testAlimentazione;
        Matcher testFasciaDiPrezzo;
        if(autoToUpdate.getAlimentazione() != null) {
            testAlimentazione = regexAlimentazione.matcher(autoToUpdate.getAlimentazione());
            if(!testAlimentazione.find()){

                sb.append("L'Alimentazione può essere solo 'benzina', 'gasolio' oppure 'elettrica'");
            }
        }
        if(autoToUpdate.getFasciaDiPrezzo() != null) {
            testFasciaDiPrezzo = regexFasciaDiPrezzo.matcher(autoToUpdate.getFasciaDiPrezzo());
            if(!testFasciaDiPrezzo.find()){
                sb.append("\\\\n");
                sb.append("La fascia di prezzo deve essere '0 a 5K', 'da 5K a 10K' oppure 'oltre i 10K'");
            }
        }

        return sb.toString();
    }
}