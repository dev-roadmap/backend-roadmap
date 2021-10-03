package io.vepo.backend.roadmap.tickets;

import static io.restassured.RestAssured.given;
import static io.vepo.backend.roadmap.dsl.Dado.dadoTicket;
import static io.vepo.backend.roadmap.dsl.Dado.dadoUsuarioAleatorio;
import static io.vepo.backend.roadmap.dsl.Dado.adminAuthentication;
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
@DisplayName("Tickets/Usuário")
@QuarkusTestResource(MongoResource.class)
class TicketUsuarioEndpointTest {

    @Nested
    @DisplayName("XML")
    class XmlTest {

        @Test
        @DisplayName("Listar")
        void listarTicketsTest() {
            var reporterId = dadoUsuarioAleatorio().getId();
            var assigneeId = dadoUsuarioAleatorio().getId();

            var ticket = dadoTicket("TCKT-1", "Ticket 1", reporterId, assigneeId);

            given().accept(ContentType.XML)
                   .contentType(ContentType.XML)
                   .header(adminAuthentication())
                   .get("/usuario/" + reporterId + "/ticket")
                   .then()
                   .statusCode(200)
                   .body(hasXPath("count(/tickets/ticket)", is("1")))
                   .body(hasXPath("/tickets/ticket/id", is(ticket.getId())));

            given().accept(ContentType.XML)
                   .contentType(ContentType.XML)
                   .header(adminAuthentication())
                   .get("/usuario/" + assigneeId + "/ticket")
                   .then()
                   .statusCode(200)
                   .body(hasXPath("count(/tickets/ticket)", is("1")))
                   .body(hasXPath("/tickets/ticket/id", is(ticket.getId())));
        }
    }

}
