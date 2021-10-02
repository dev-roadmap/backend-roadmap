# Decidindo o recurso

* [Principal](./README.md)
* [Anterior: Configurando um projeto Quarkus.io](./caso-de-uso-00-configurando-um-projeto-quarkus.md)
* [Próximo: Decidindo a ação](./caso-de-uso-02-decidindo-a-acao.md)

Quarkus usa o JAX-RS para implementar serviços REST. JAX-RS é uma especificação [Jakarta EE](https://eclipse-ee4j.github.io/jaxrs-api/apidocs/3.0.0/), sua principal caracteristica é que essa especificação é POJO Centric, isso significa que podemos definir classes que retornam objetos e essas classes definirão a API Rest.

Um padrao comum no JAX-RS, é criar classes para Endpoints. Cada vai responder por determinado conjunto de recursos e os métodos responderão por recursos mais específicos. No nosso caso, estamos criando um serviço de SM, assim temos dois recursos principais o `ticket` e o `usuario`. Na API poderemos também listar todos os tickets associados a um único usuário, para isso o Endpoint relativo aos usuário será usado como filtros.

```java
@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<UsuarioResponse>> listarUsuariosComoJson() {
        // código
    }
}
```