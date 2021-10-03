package io.vepo.backend.roadmap.dsl;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.vepo.backend.roadmap.tickets.CriarTicketRequest;
import io.vepo.backend.roadmap.tickets.TicketResponse;
import io.vepo.backend.roadmap.usuarios.CriarUsuarioRequest;
import io.vepo.backend.roadmap.usuarios.UsuarioResponse;

import java.util.Base64;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;

public class Dado {

    public static UsuarioResponse dadoUsuarioAleatorio() {
        return given().accept(ContentType.JSON)
                      .contentType(ContentType.JSON)
                      .header(adminAuthentication())
                      .body(new CriarUsuarioRequest("john", "john.doe@dev-roadmap.com.br", "123456", Set.of("ADMIN")))
                      .when()
                      .put("/usuario")
                      .as(UsuarioResponse.class);
    }

    public static UsuarioResponse dadoUsuario(String username, String email) {
        return given().accept(ContentType.JSON)
                      .contentType(ContentType.JSON)
                      .header(adminAuthentication())
                      .body(new CriarUsuarioRequest(username, email, "123456", Set.of("ADMIN")))
                      .when()
                      .put("/usuario")
                      .as(UsuarioResponse.class);
    }

    public static TicketResponse dadoTicket(String titulo, String descricao, String reporterId, String assigneeId) {
        return given().accept(ContentType.XML)
                      .contentType(ContentType.XML)
                      .header(adminAuthentication())
                      .body(new CriarTicketRequest(titulo, descricao, reporterId, assigneeId))
                      .put("/ticket")
                      .as(TicketResponse.class);
    }

    public static Header adminAuthentication() {
        return new Header(HttpHeaders.AUTHORIZATION, "BASIC " + Base64.getEncoder().encodeToString("admin:admin".getBytes()));
    }
}
