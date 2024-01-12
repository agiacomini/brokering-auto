package com.giacomini.andrea.repository;

import com.giacomini.andrea.entity.Auto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@TestMethodOrder(OrderAnnotation.class)
public class AutoRepositoryTest {

    private Long newValueId;

    @Autowired
    private IAutoRepository autoRepository;

    @BeforeEach
    public void setup()  {
        newValueId = autoRepository.getMaxValueId();
    }

    @Test
    @Order(1)
    public void TestCreateAuto(){

        // CLASSE ENTITY AUTO
        Auto auto = new Auto(newValueId + 1L, false, "da 5K a 10K", 5, true, "benzina");

        autoRepository.save(auto);

        Auto autoSelected = autoRepository.selectAutoById(newValueId);

        assertThat(autoSelected).extracting(Auto::getFasciaDiPrezzo)
                .isEqualTo("da 5K a 10K");
    }

    @Test
    @Order(2)
    public void TestFindByAnzianitaAndCambioAutomatico(){
        Auto auto = autoRepository.selectAutoById(newValueId);
        assertThat(autoRepository.findAutosByAnzianitaEqualsAndCambioAutomaticoEquals(5, true))
                .contains(auto);
    }

    @Test
    @Order(3)
    public void TestFindByAnzianitaAndCambioAutomaticoAndFasciaPrezzo(){
        Auto auto = autoRepository.selectAutoById(newValueId);
        assertThat(autoRepository.selectAutoByAnzianitaAndCambioAutomaticoAndFasciaPrezzo(5, true, "da 5K a 10K"))
                .contains(auto);
    }

    @Test
    @Order(4)
    public void TestDeleteAuto(){
        autoRepository.delete(autoRepository.selectAutoById(newValueId));
        assertThat(autoRepository.selectAutoById(newValueId)).isNull();
    }
}