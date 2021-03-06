package br.com.cattle_control.starter.service;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.cattle_control.starter.model.Place;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlaceServiceIntegrationTest  {
    
    @Autowired
    PlaceService placeService;

    @DisplayName("Testar a criação de um Local no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        Place place = Place.builder()
                                .cep("55555-000")
                                .city("Testolândia")
                                .deleted(false)
                                .build();

        placeService.create(place);

        assertThat(placeService.findByCep("55555-000").getCity()).isEqualTo("Testolândia");
    }


    @DisplayName("Testar a procura de um Local por Cep.")
	@Test
	@Transactional
	@Rollback
	void testFindByCep() throws Exception{

        Place place = Place.builder()
                                .cep("55555-000")
                                .city("Testolândia")
                                .deleted(false)
                                .build();

        placeService.create(place);

        
        assertThat(placeService.findByCep("55555-000")).isEqualTo(place);
    }

    @DisplayName("Testar o update de um Local no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdatePlace () throws Exception{

        Place place = Place.builder()
                                .cep("55555-000")
                                .city("Testolândia")
                                .deleted(false)
                                .build();

        placeService.create(place);

        Integer id = placeService.findByCep("55555-000").getId();

        place = Place.builder()
                            .id(id)
                            .cep("55555-000")
                            .city("Auriflama")
                            .deleted(false)
                            .build();

        placeService.update(place);

        
        assertThat(placeService.findByCep("55555-000").getCity()).isEqualTo("Auriflama");
    }


    @DisplayName("Testar a procura de um Local deletada por Cep.")
	@Test
	@Transactional
	@Rollback
	void testFindByCepDeleted() throws Exception {

        Place place = Place.builder()
                                .cep("55555-000")
                                .city("Testolândia")
                                .deleted(true)
                                .build();

        placeService.create(place);


        assertThrows(com.github.adminfaces.template.exception.BusinessException.class, () -> {
            placeService.findByCep("55555-000");
        });
    }


    @DisplayName("Testar a criação de um Local com CEP igual a quem já foi deletado.")
	@Test
	@Transactional
	@Rollback
	void testCreatePlaceCepEqualsDeleted() throws Exception{

        Place place = Place.builder()
                                .cep("55555-000")
                                .city("Testolândia")
                                .deleted(true)
                                .build();

        placeService.create(place);
        
        place = Place.builder()
                            .cep("55555-000")
                            .city("Auriflama")
                            .deleted(false)
                            .build();

        placeService.create(place);

        assertThat(placeService.readAll().get(0).getDeleted()).isEqualTo(false);
    }


    @DisplayName("Testar a procura por Ceps existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetCeps() throws Exception{

        Place place = Place.builder()
                                .cep("55555-000")
                                .city("Testolândia")
                                .deleted(false)
                                .build();

        placeService.create(place);

        assertThat(placeService.getCeps("55555").get(0)).isEqualTo("55555-000");
    }
}
