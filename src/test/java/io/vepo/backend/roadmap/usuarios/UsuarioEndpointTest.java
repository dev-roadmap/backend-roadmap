package io.vepo.backend.roadmap.usuarios;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@DisplayName("Usuários")
@QuarkusTestResource(MongoResource.class)
public class UsuarioEndpointTest {

    @Inject
    UsuarioService usuarioService;

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
    @DisplayName("Encontrar por username - Aceita XML")
    void encontraUsuariosPorUsernameAceitaXml() {
        given().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .body(new CriarUsuarioRequest("john", "john.doe@dev-roadmap.com.br"))
               .when()
               .put("/usuario")
               .then()
               .statusCode(201);

        given().accept(ContentType.XML)
               .when()
               .get("/usuario/username/john")
               .then()
               .header("Content-Type", containsString("application/xml"))
               .statusCode(200)
               .body(hasXPath("username", is("john")));
    }

    @Test
    @DisplayName("Encontrar por username - Aceita JSON")
    void encontraUsuariosPorUsernameAceitaJson() {
        given().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .body(new CriarUsuarioRequest("john", "john.doe@dev-roadmap.com.br"))
               .when()
               .put("/usuario")
               .then()
               .statusCode(201);

        given().accept(ContentType.JSON)
               .when()
               .get("/usuario/username/john")
               .then()
               .header("Content-Type", containsString("application/json"))
               .statusCode(200)
               .body("username", is("john"));
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

        String userId = given().header("Accept", "application/json")
                               .when()
                               .get("/usuario")
                               .then()
                               .header("Content-Type", containsString("application/json"))
                               .statusCode(200)
                               .body("$", hasSize(1))
                               .extract()
                               .path("[0].id");

        given().header("Accept", "application/json")
               .when()
               .get("/usuario/{id}", userId)
               .then()
               .header("Content-Type", containsString("application/json"))
               .statusCode(200)
               .body("email", equalTo("john.doe@dev-roadmap.com.br"))
               .body("username", equalTo("john"));
    }
}
