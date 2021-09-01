package io.vepo.backend.roadmap.usuarios;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@DisplayName("Usuários")
public class UsuarioEndpointTest {

    @Inject
    UsuarioService usuarioService;

    @BeforeEach
    void cleanup() {
        usuarioService.cleanup();
    }

    @Test
    @DisplayName("Listar - Sem Accept")
    void listarUsuarios() {
        given().when()
               .get("/usuario")
               .then()
               .header("Content-Type", anyOf(containsString("application/json"), containsString("application/xml")))
               .statusCode(200);
    }

    @Test
    @DisplayName("Listar - Aceita XML")
    void listarUsuariosAceitaXml() {
        given().accept(ContentType.XML)
               .when()
               .get("/usuario")
               .then()
               .header("Content-Type", containsString("application/xml"))
               .statusCode(200)
               .body(hasXPath("count(/usuarios/usuario)", is("0")));
    }

    @Test
    @DisplayName("Listar - Aceita JSON")
    void listarUsuariosAceitaJson() {
        given().accept(ContentType.JSON)
               .when()
               .get("/usuario")
               .then()
               .header("Content-Type", containsString("application/json"))
               .statusCode(200)
               .body("$", hasSize(0));
    }

    @Test
    @DisplayName("Criar Usuário - JSON")
    void criarUsuarioAceitaJson() {
        given().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .body(new CriarUsuarioRequest("john", "john.doe@dev-roadmap.com.br"))
               .when()
               .put("/usuario")
               .then()
               .statusCode(201);

        given().header("Accept", "application/json")
               .when()
               .get("/usuario")
               .then()
               .header("Content-Type", containsString("application/json"))
               .statusCode(200)
               .body("$", hasSize(1));

        given().header("Accept", "application/json")
               .when()
               .get("/usuario/{id}", 1)
               .then()
               .header("Content-Type", containsString("application/json"))
               .statusCode(200)
               .body("email", equalTo("john.doe@dev-roadmap.com.br"))
               .body("username", equalTo("john"));
    }
}
