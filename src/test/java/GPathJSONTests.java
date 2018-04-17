package test.java;

import io.restassured.response.Response;
import org.junit.Test;
import test.java.config.GPathTestConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;

/**
 * Info: http://james-willett.com/2017/05/rest-assured-gpath-json/
 */
public class GPathJSONTests extends GPathTestConfig {

    // Can return a "node" from JSON array matching a search pattern
    @Test
    public void extractMapOfElementsWithFind() {

        Response response = get("competitions/426/teams");

        Map<String, ?> allTeamDataForSingleTeam = response.path(
                "teams.find { it.name == 'Leicester City FC' }"
        );

        System.out.print(allTeamDataForSingleTeam);
        // {squadMarketValue=null, crestUrl=http://..../Leicester02.png, name=Leicester City FC, id=338, shortName=Foxes}
    }

    @Test
    public void extractSingleValueWithFind() {

        Response response = get("teams/66/players");
        String certainPlayer = response.path("" +
                "players.find { it.jerseyNumber == 20 }.name "
        );

        System.out.print(certainPlayer);
        // Sergio Romero
    }

    @Test
    public void extractListOfValuesWithFindAll() {

        Response response = get("teams/66/players");
        List<String> playerNames = response.path("" +
                "players.findAll { it.jerseyNumber > 10 }.name "
        );

        System.out.print(playerNames);
        // [Sergio Romero, Chris Smalling, Daley Blind, Luke Shaw, Matteo Darmian, Antonio Valencia, Michael Carrick, Ander Herrera, Marouane Fellaini, Ashley Young, Jesse Lingard, Anthony Martial, Marcus Rashford, Nemanja Matic, Joel Pereira, Cameron Borthwick-Jackson, Scott McTominay]

    }


    @Test
    // find the player having the highest number on the jersey and print name
    public void extractSingleValueWithHighestNumbe() {

        Response response = get("teams/66/players");
        int jerseyNum = response.path("" +
                "players.max { it.jerseyNumber }.jerseyNumber "
        );

        System.out.print(jerseyNum);
        // 43
    }

    @Test
    // find collection of jersey numbers snd then sum them
    public void extractMultipleValuesAndSumThem() {

        Response response = get("teams/66/players");
        int sumOfJerseys = response.path("" +
                "players.collect { it.jerseyNumber }.sum() "
        );

        System.out.print(sumOfJerseys);
        // 43
    }

    @Test
    // write complex query to extract map of object with find and findall
    public void nestedSubMatches() {

        String position = "Centre-Back";
        String nationality = "Argentina";

        Response response = get("teams/66/players");
        Map<String, ?> playerOfCertainPosition = response.path("" +
                        "players.findAll { it.position == '%s' }.find { it.nationality == '%s'} "
                , position, nationality);

        System.out.print(playerOfCertainPosition);
        // {nationality=Argentina, name=Marcos Rojo, jerseyNumber=5, marketValue=null, dateOfBirth=1990-03-20, contractUntil=2019-06-30, id=443, position=Centre-Back}
    }

    @Test
    // write complex query to extract map of object with find and findall
    public void nestedSubMatchesMultiplePlayers() {

        String position = "Centre-Back";
        String nationality = "England";

        Response response = get("teams/66/players");
        ArrayList<Map<String, ?>> allPlayersFromCertainNation = response.path("" +
                        "players.findAll { it.position == '%s' }.findAll { it.nationality == '%s'} "
                , position, nationality);

        System.out.print(allPlayersFromCertainNation);
        /*
            [
                {nationality=England, name=Chris Smalling, jerseyNumber=12, marketValue=null, dateOfBirth=1989-11-22, contractUntil=2019-06-30, id=442, position=Centre-Back},
                {nationality=England, name=Phil Jones, jerseyNumber=4, marketValue=null, dateOfBirth=1992-02-21, contractUntil=2019-06-30, id=444, position=Centre-Back}
            ]
         */
    }
}
