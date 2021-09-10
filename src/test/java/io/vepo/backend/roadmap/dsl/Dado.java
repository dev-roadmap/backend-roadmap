package io.vepo.backend.roadmap.dsl;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.vepo.backend.roadmap.tickets.CriarTicketRequest;
import io.vepo.backend.roadmap.tickets.TicketResponse;
import io.vepo.backend.roadmap.usuarios.CriarUsuarioRequest;
import io.vepo.backend.roadmap.usuarios.UsuarioResponse;

public class Dado {

    public static UsuarioResponse dadoUsuarioAleatorio() {
        return given().accept(ContentType.JSON)
                      .contentType(ContentType.JSON)
                      .body(new CriarUsuarioRequest("john", "john.doe@dev-roadmap.com.br"))
                      .when()
                      .put("/usuario")
                      .as(UsuarioResponse.class);
    }

    public static UsuarioResponse dadoUsuario(String username, String email) {
        return given().accept(ContentType.JSON)
                      .contentType(ContentType.JSON)
                      .body(new CriarUsuarioRequest(username, email))
                      .when()
                      .put("/usuario")
                      .as(UsuarioResponse.class);
    }

    public static TicketResponse dadoTicket(String titulo, String descricao, String reporterId, String assigneeId) {
        return given().accept(ContentType.XML)
                      .contentType(ContentType.XML)
                      .body(new CriarTicketRequest(titulo, descricao, reporterId, assigneeId))
                      .put("/ticket")
                      .as(TicketResponse.class);
    }
}
