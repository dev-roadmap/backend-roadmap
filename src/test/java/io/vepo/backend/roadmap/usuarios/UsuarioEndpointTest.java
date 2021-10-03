package io.vepo.backend.roadmap.usuarios;

import static io.restassured.RestAssured.given;
import static io.vepo.backend.roadmap.dsl.Dado.dadoUsuario;
import static io.vepo.backend.roadmap.dsl.Dado.adminAuthentication;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
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

import java.util.Set;

@QuarkusTest
@DisplayName("Usuários")
@QuarkusTestResource(MongoResource.class)
class UsuarioEndpointTest {

    @Nested
    @DisplayName("JSON")
    class JsonTest {
        @Test
        @DisplayName("Criar")
        void criarUsuario() {
            var userId = given().accept(ContentType.JSON)
                                .contentType(ContentType.JSON)
                                .header(adminAuthentication())
                                .body(new CriarUsuarioRequest("john", "john.doe@dev-roadmap.com.br", "123456", Set.of("ADMIN")))
                                .when()
                                .put("/usuario")
                                .then()
                                .statusCode(201)
                                .extract()
                                .as(UsuarioResponse.class)
                                .getId();

            given().accept(ContentType.JSON)
                   .header(adminAuthentication())
                   .when()
                   .get("/usuario/{id}", userId)
                   .then()
                   .header("Content-Type", containsString("application/json"))
                   .statusCode(200)
                   .body("email", equalTo("john.doe@dev-roadmap.com.br"))
                   .body("username", equalTo("john"));
        }

        @Test
        @DisplayName("Encontrar por username")
        void encontraUsuariosPorUsername() {
            dadoUsuario("john", "john.doe@dev-roadmap.com.br");

            given().accept(ContentType.JSON)
                   .header(adminAuthentication())
                   .when()
                   .get("/usuario/username/john")
                   .then()
                   .header("Content-Type", containsString("application/json"))
                   .statusCode(200)
                   .body("username", is("john"));
        }

        @Test
        @DisplayName("Listar")
        void listarUsuarios() {
            given().accept(ContentType.JSON)
                   .header(adminAuthentication())
                   .when()
                   .get("/usuario")
                   .then()
                   .header("Content-Type", containsString("application/json"))
                   .statusCode(200)
                   .body("$", hasSize(1));
        }
    }

    @Nested
    @DisplayName("XML")
    class XmlTest {
        @Test
        @DisplayName("Encontrar por username")
        void encontraUsuariosPorUsername() {
            dadoUsuario("john", "john.doe@dev-roadmap.com.br");

            given().accept(ContentType.XML)
                   .header(adminAuthentication())
                   .when()
                   .get("/usuario/username/john")
                   .then()
                   .header("Content-Type", containsString("application/xml"))
                   .statusCode(200)
                   .body(hasXPath("username", is("john")));
        }

        @Test
        @DisplayName("Listar")
        void listarUsuarios() {
            given().accept(ContentType.XML)
                   .header(adminAuthentication())
                   .when()
                   .get("/usuario")
                   .then()
                   .header("Content-Type", containsString("application/xml"))
                   .statusCode(200)
                   .body(hasXPath("count(/usuarios/usuario)", is("1")));
        }

        @Test
        @DisplayName("Criar")
        void criarUsuario() {
            var userId = given().accept(ContentType.XML)
                                .header(adminAuthentication())
                                .contentType(ContentType.XML)
                                .body(new CriarUsuarioRequest("john", "john.doe@dev-roadmap.com.br", "123456", Set.of("ADMIN")))
                                .when()
                                .put("/usuario")
                                .then()
                                .statusCode(201)
                                .extract()
                                .as(UsuarioResponse.class)
                                .getId();

            given().accept(ContentType.XML)
                   .header(adminAuthentication())
                   .when()
                   .get("/usuario/{id}", userId)
                   .then()
                   .header("Content-Type", containsString("application/xml"))
                   .statusCode(200)
                   .body(hasXPath("/usuario/email", equalTo("john.doe@dev-roadmap.com.br")))
                   .body(hasXPath("/usuario/username", equalTo("john")));
        }

    }
}
