# Decidindo o formato

* [Principal](./README.md)
* [Anterior: Decidindo a ação](./caso-de-uso-02-decidindo-a-acao.md)
* [Próximo: Apresentando as credenciais](./caso-de-uso-04-apresentando-as-credenciais.md)

O próximo passo é definir quais formatos podemos expor na nossa API. Como estamos criando uma API back-end, vamos começar pelo uso do cabeçalho `Accept` e sua respectiva resposta o `Content-Type`.

A especificação JAX-RS define duas anotações que devem ser sempre utilizadas `Consumes` e `Produces`. A decisão de qual formato utiliza não deve ser tomada pelo desenvolvedor, mas pelo Quarkus, afinal isso não faz parte da lógica de negócios. O desenvolvedor deve apenas especificar o POJO a ser retornado e quais os possíveis formatos que aquele POJO será enviado. Em muito casos um mesmo código pode ser utilizado para fornecer dados de dois formatos diferentes. Mas em outros casos é preciso algumas configurações um pouco mais especificas, como veremos no caso do XML. 

Abaixo vamos redefinir a classe `UsuarioEndpoint` para observarmos o que devemos alterar para permitir que a API use tanto JSON quanto XML.

```java
@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsuarioResponse> listar() {
        // implementação
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public UsuariosResponse listarXml() {
        // implementação
    }

    @PUT
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public UsuarioResponse novo(CriarUsuarioRequest request) {
        // implementação
    }
}
```

No método `novo` foi usado mais de uma valor para cada anotação. Repare que `Consumes` e `Produces` aceita tanto um valor único quanto uma lista de valores. Ao definir o formato JSON, será necessário apenas adicionar a dependência `quarkus-resteasy-jsonb` do Quarkus ao projeto. Mas ao definir o formato XML é preciso, além de adicionar a dependência `quarkus-resteasy-jaxb`, adicionar algumas anotações aos POJOs retornados. A classe `UsuarioResponse` deve ser definida com a anotações da especificação JAXB.

```java
@XmlRootElement(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioResponse {
    // implementação
}
```

Essas anotações devem definir o modo como o XML deve ser criado. Elas devem ser defindas porque o formato XML requer uma entidade raiz, ou seja, se `UsuarioResponse` tiver um campo definido por uma outra classe, não é necessário usar a anotação `XmlRootElement`. 


```java
@XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuariosResponse {

    private List<UsuarioResponse> usuarios;
    // implementação com Getters e Setters
}
```

A especificação JAX-RS define a interface `MessageBodyWriter` que deve implementado para cada formato, para que o Quarkus encontre essa implementação e a associe ao corretor formato devemos definir usar as anotações `Produces` e `Provider`.

```java
@Provider
@Produces("application/pdf")
public class RelatorioUsuarioWriter implements MessageBodyWriter<UsuariosResponse> {
    // implementação
}
```

Ao criarmos a classe `RelatorioUsuarioWriter`, ela poderá ser usada nas classes endpoint apenas referenciando `application/pdf` nas anotações `Produces`.

```java
@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/pdf" })
    public UsuariosResponse listar() {
        // implementação
    }
}
```
_— E os cabeçalhos `Accept-Language`? Não dá pra usar com `Consumes` e `Produces`?_

Em pouquíssimos casos será necessário tratar o cabeçalho `Accept-Language` em uma API, na maioria das vezes ele será tratato automaticamente pelo framework usado para renderização de páginas pelo servidor. Mas no caso acima, estamos tratando de uma funcionalidade típica de uma API que deve tratar internacionalização. Mas não temos uma funcionalidade especifica tratando de internacionalização no JAX-RS, por isso devemos tratar como um cabeçalho. Isso significa que ele pode ser acessado usando a anotação `HeaderParam` do JAX-RS, como no exemplo abaixo, ou pode ser gerado pela implementação de `MessageBodyWriter` através do parâmetro `httpHeaders` do método `writeTo`.

```java
@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {
    @GET
    @Path("relatorio")
    @Produces("application/pdf")
    public UsuariosResponse listar(@HeaderParam("Accept-Language") String acceptLanguage) {
        // implementação
    }
}
```