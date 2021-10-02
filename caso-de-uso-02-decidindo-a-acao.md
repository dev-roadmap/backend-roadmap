# Decidindo a ação

* [Principal](./README.md)
* [Anterior: Decidindo o recurso](./caso-de-uso-01-decidindo-o-recurso.md)
* [Próximo: Decidindo o formato](./caso-de-uso-03-decidindo-o-formato.md)

Dado que os recursos já estão definidos no caso de uso anteriores, como podemos adicionar ações neles? Você pode ter reparado que não descrevemos o uso da anotação `GET`. Essa anotação de define o método HTTP que estamos usando. O JAX-RS define as seguintes anotações `GET`, `HEAD`, `DELETE`, `OPTIONS`, `PATCH`, `POST` e `PUT`. Essas anotações devem ser usadas para marcar qual método HTTP o método da classe deve responder. O JAX-RS também define a anotação `HttpMethod` para que seja possível implementar outros métodos HTTP.

Para que possamos demonstrar como usar os métods, vamos adicionar duas novas operações no endpoint da sessão anterio. Vamos habilitar a criação de um novo usuário e a edição de um usuário de duas formas diferentes.

```java
@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {

    // [Código omitido]

    @PUT
    public UsuarioResponse novo(CriarUsuarioRequest request) {
        // implementação
    }

    @POST
    @Path("{id}")
    public UsuarioResponse editar(@PathParam("id") Long id, EditarUsuarioRequest request) {
        // implementação
    }

    @PATCH
    @Path("{id}")
    public UsuarioResponse aplicarEdicao(@PathParam("id") Long id, PatchUsuarioRequest request) {
        // implementação
    }
}
```