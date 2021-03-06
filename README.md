# Backend roadmap

Tutorial Quarkus baseado em casos de usos. 

## Para que serve esse repositório?

Esse repositório contém alguns tutoriais básicos que serve de demonstração para exemplificar como implementar várias tecnologias úteis para desenvolvimento back-end usando [Quarkus.io](https://quarkus.io/). 

Não considere esse repositório como conteúdo principal, ele serve como material de apoio para alguns projetos pessoais. Logo qualquer contribuição, correção ou atualização é bem vinda, desde que sirva para aperfeiçoar esse material. Ele conterá um aplicativo funcional.

## Proposta de funcionalidade

Como não desejo que esse material seja um mero tutorial, vou propor um produto fictício: um gerenciador de requisições de mudança. Para quem não entendeu o pra que serve tal gerenciador, é o mesmo que faz o Jira.

Cada passo vai definir parte dos requisitos do produto enquanto exemplifica como implementar casos de usos para demonstração.

## Configuração Quarkus

0. [Configurando um projeto Quarkus.io](./caso-de-uso-00-configurando-um-projeto-quarkus.md)

## Base de dados

Esse tutorial vai usar uma base MongoDB, por isso antes de executar inicialize a base localmente usando Docker.

```bash
docker run --name mongo-db -e MONGO_INITDB_ROOT_USERNAME=tickets -e MONGO_INITDB_ROOT_PASSWORD=tickets -p 27017:27017 -d mongo:5
```

## Protocolo HTTP

Nesse tutorial vamos demonstrar como usar o protocolo HTTP usando alguns casos de usos do protocolo. Quando me refiro a casos de uso do protocolo, não estou tentando construir um produto, mas demonstrar como podemos usar o protocolo para construir uma API.

Os casos de uso em questão são:

1. [Decidindo o recurso](./caso-de-uso-01-decidindo-o-recurso.md)
2. [Decidindo a ação](./caso-de-uso-02-decidindo-a-acao.md)
3. [Decidindo o formato](./caso-de-uso-03-decidindo-o-formato.md)
4. [Apresentando as credenciais](./caso-de-uso-04-apresentando-as-credenciais.md)
5. [Colocando valores na requisição](./caso-de-uso-05-colocando-valores-na-requisicao.md)
6. [Adicionando parâmetros de busca](./caso-de-uso-06-adicionando-parametros-de-busca.md)
7. [Escrevendo a resposta](./caso-de-uso-07-escrevendo-a-resposta.md)
8. [Informando o tipo de resposta](./caso-de-uso-08-informando-o-tipo-de-resposta.md)
9. [Tratando erros](./caso-de-uso-09-tratando-erros.md)
10. [Usando o cache](./caso-de-uso-10-usando-o-cache.md)
11. [Criando um canal _full duplex_ de comunicação](./caso-de-uso-11-criando-um-canal-full-duplex-de-comunicacao.md)

