package io.vepo.backend.roadmap.tickets;

import static io.restassured.RestAssured.given;
import static io.vepo.backend.roadmap.dsl.Dado.dadoUsuarioAleatorio;
import static io.vepo.backend.roadmap.dsl.Dado.adminAuthentication;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vepo.backend.roadmap.infra.MongoResource;

@QuarkusTest
@DisplayName("Tickets")
@QuarkusTestResource(MongoResource.class)
class TicketEndpointTest {
    @Nested
    @DisplayName("XML")
    class XmlTest {
        @Test
        @DisplayName("Listar")
        void listarTicketsTest() {
            given().accept(ContentType.XML)
                   .header(adminAuthentication())
                   .when()
                   .get("/ticket")
                   .then()
                   .statusCode(200)
                   .header("Content-Type", containsString("application/xml"))
                   .statusCode(200)
                   .body(hasXPath("count(/tickets/ticket)", is("0")));
        }

        @Test
        @DisplayName("Criar")
        void criarTicketTest() {
            var reporterId = dadoUsuarioAleatorio().getId();
            var assigneeId = dadoUsuarioAleatorio().getId();

            given().accept(ContentType.XML)
                   .contentType(ContentType.XML)
                   .header(adminAuthentication())
                   .body(new CriarTicketRequest("ticket 1", "Criar Ticket", reporterId, assigneeId))
                   .put("/ticket")
                   .then()
                   .statusCode(200)
                   .body(hasXPath("/ticket/titulo", is("ticket 1")))
                   .body(hasXPath("/ticket/descricao", is("Criar Ticket")))
                   .body(hasXPath("/ticket/reporterId", is(reporterId)))
                   .body(hasXPath("/ticket/assigneeId", is(assigneeId)));
        }

    }

    @Nested
    @DisplayName("JSON")
    class JsonTest {
        @Test
        @DisplayName("Listar")
        void listarTicketsTest() {
            given().accept(ContentType.JSON)
                   .header(adminAuthentication())
                   .when()
                   .get("/ticket")
                   .then()
                   .statusCode(200)
                   .header("Content-Type", containsString("application/json"))
                   .statusCode(200)
                   .body("$", hasSize(0));
        }
    }

}
