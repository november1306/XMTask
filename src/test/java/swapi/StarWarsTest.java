package swapi;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;

class StarWarsTest {

    Logger log = LoggerFactory.getLogger(StarWarsTest.class);

    @Test
    @DisplayName("SWAPI check starships")
    void starshipsCheck() {

        log.info("1. Find film with a title ”A New Hope”");
        JsonPath films = RestAssured
                .get("https://swapi.dev/api/films")
                .jsonPath();
        Map<String, List<String>> newHopeFilm = films.getJsonObject("results.find {it.title == 'A New Hope'}");

        Assertions.assertNotNull(newHopeFilm, "New Hope film was not found");

        log.info("2. Using previous response (1) find person with name “Biggs Darklighter” among the characters that were part of that film.");
        List<String> characters = newHopeFilm.get("characters");
        JsonPath biggsDetails = characters.parallelStream()
                .map(s -> RestAssured.get(s).jsonPath())
                .filter(s -> s.getString("name").equals("Biggs Darklighter"))
                .findFirst().orElse(null);

        Assertions.assertNotNull(biggsDetails, "Biggs Darklighter not found in a New Hope film");


        log.info("3. Using previous response (2) find which starship he/she was flying on.");
        log.info("4. Using previous response (3) check next:");
        log.info("a. starship class is “Starfighter”");

        String biggsStarship = RestAssured
                .when()
                .get(
                        biggsDetails.getList("starships").get(0).toString()
                )
                .then()
                .assertThat()
                .body("starship_class", equalTo("Starfighter"))
                .and()
                .extract()
                .path("name");

        log.info("biggs Starship is " + biggsStarship);

        log.info("b. “Luke Skywalker” is among pilots that were also flying this kind of starship");
        List<String> lukeShips = RestAssured
                .get("https://swapi.dev/api/people/1")
                .path("starships");

        Set<String> lukeShipClass = lukeShips.parallelStream()
                .map(s -> RestAssured.get(s).path("starship_class").toString())
                .collect(Collectors.toSet());

        Assertions.assertTrue(lukeShipClass.contains("Starfighter"), "no Starfighter class ships among ones Luke piloted");
    }
}
