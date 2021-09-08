# Decidindo o recurso

* [Principal](./README.md)
* [Anterior: Configurando um projeto Quarkus.io](./caso-de-uso-1-decidindo-o-recurso.md)
* [Próximo: Decidindo a ação](./caso-de-uso-2-decidindo-a-acao.md)

Quarkus usa o JAX-RS para implementar serviços REST. JAX-RS é uma especificação [Jakarta EE](https://eclipse-ee4j.github.io/jaxrs-api/apidocs/3.0.0/), sua principal caracteristica é que essa especificação é POJO Centric, isso significa que podemos definir classes que retornam objetos e essas classes definirão a API Rest.

Um padrao comum no JAX-RS, é criar classes para Endpoints. Cada vai responder por determinado conjunto de recursos e os métodos responderão por recursos mais específicos. No nosso caso, estamos criando um serviço de SM, assim temos dois recursos principais o `ticket` e o `usuario`. Na API poderemos também listar to