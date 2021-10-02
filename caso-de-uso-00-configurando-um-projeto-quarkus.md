# Configurando um projeto Quarkus.io

* [Principal](./README.md)
* [Próximo: Decidindo o recurso](./caso-de-uso-01-decidindo-o-recurso.md)

O Quarkus é um framework fácil de configurar. Tudo que precisamos saber é usar o Maven ou o Gradle. Nesse tutorial vamos usar o Maven.

Caso você não tenha experiência com o Maven, você pode usar o serviço [Start Coding](https://code.quarkus.io/) do Quarkus.io para gerar a estrutura do projeto com todas as configurações necessárias.

## Maven 101

Para que não conhece o Maven, ele é um sistema de build completo. Você não precisa baixar manualmente todas as bibliotecas ou chamar o `javac` para compilar seu código, basta alterar o arquivo [`pom.xml`](./pom.xml). Nele são definidas todas as informações necessárias para build, assim como a dependências e a forma de distribuição do seu programa. 

A instalação do Maven é simples. Faça o download da última versão em [maven.apache.org](https://maven.apache.org/) e adicione a pasta de executáveis na váriavel de ambiente PATH. Mais detalhes podem ser encontrado nos tutoriais especificos para [Windows](./tutoriais/01-como-instalar-maven-windows.md) e [Linux](./tutoriais/01-como-instalar-maven-linux.md).

O Maven funciona usando um padrão chamado configuração por convenção, ou seja, devemos seguir algumas convenções para configurações básicas. A primeira convenção que devemos aprender é que todo código deve ficar na pasta `/src`. Esta pasta deverá ter uma estrutura fixa para cada tipo de código. 

```
.
├── src                      ## Todo o código deve ser armazenado nessa pasta
│   ├── main                 ## Código de produção
|   |   ├── java             ## Código Java de produção
|   |   └── resources        ## Arquivos que não serão compilados, mas estarão disponíveis em tempo de execução
│   └── test                 ## Código usado para testes unitários
|       ├── java             ## Código Java para testes unitários
|       └── resources        ## Arquivos que não serão compilados, mas estarão disponíveis em tempo de execução
└── pom.xml                  ## Arquivo que define como será feita a build
```

Outra convenção de um projeto Maven é o ciclo de uma build. Para se executar uma build, deve-se prover quais passos devem ser executados, e alguns passos dependem de outros. Abaixo cito quais são os principais passos de uma build Maven

* `clean`: remove todos os arquivos gerados por builds anteriores
* `validate`: validar se o projeto está correto e todas as informações necessárias estão disponíveis
* `compile`: compilar o código fonte do projeto
* `test`: teste o código-fonte compilado usando uma estrutura de teste de unidade adequada. Esses testes não devem exigir que o código seja empacotado ou implantado
* `package`: depois do código compilado, empacote-o em seu formato distribuível, como um JAR.
* `verify`: execute todas as verificações nos resultados dos testes de integração para garantir que os critérios de qualidade sejam atendidos
* `install`: instale o pacote no repositório local, para uso como uma dependência em outros projetos localmente
* `deploy`: copia o pacote final para o repositório remoto para compartilhamento com outros desenvolvedores e projetos.


Esses passos podem ser alterados através de plugins e novos passos podem ser adicionados. Supondo que eu tenha um projeto Java que implemente o método main em uma classe `io.vepo.exemplo.Main`, para executar ela é preciso chamar os passos `clean`, `compile` e `exec:java`.

```bash
mvn clean compile exec:java -Dexec.mainClass="io.vepo.exemplo.Main"
```

## Coordenadas Maven

O Maven gerencia automaticamente as dependências do projeto. No `pom.xml` podemos definir uma sessão chamada `dependencies` onde todas as dependências podem ser definidas. Existem inúmeras bibliotecas disponíveis para serem usadas em projetos Java, muitas delas podem ser encontradas através do site [mvnrepository.com](https://mvnrepository.com/).

Para identificar um artefato especifico, o Maven define o conceito de Coordenada Maven. Uma coordenada é definda por todos elementos necessáios para identificar um artefato, as principais são `groupId`, `artifactId` e `version`. Mas também podem ser definidos os elementos `classifier` e `type`.
* **groupId**: Identificador de agroupamento, normalmente referindo-se a uma organização, uma empresa e pode incluir um tema básico para um ou mais projetos. 
* **artifactId**: Nome para o projeto. Entre os muitos projetos que existem no grupo, o artifactId pode identificar exclusivamente  artefato. 
* **version**: Um identificador que rastreia builds exclusivas de um artefato. Uma versão é uma string construída pela equipe de desenvolvimento do projeto para identificar builds, diferenciado novos artefatos e catalogando alterações.

Para adicionar uma dependência, procure ela no site e copie suas coordenadas na sessão `dependencies`.

## Dependências do Quarkus

Um dos pressupostos do Quarkus é facilitar o gerenciamento de dependências, logo a dependência mais importante é o BOM (Bill Of Materials) do quarkus. Um BOM define uma série de dependências e suas respectivas versões. Então para configurar o projeto Quarkus copie as coordenadas do BOM e adicione em `dependencyManagement`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-universe-bom</artifactId>
            <version>${quarkus.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Adicionado o BOM, não precisamos mais identificar qual versão estamos usando dos extensões Quarkus, nosso código vai depender apenas do groupId e artifactId dos extensões definidas. Para verificar quais extensões existem, leia a documentação, ela define detalhadamente quais extensões são necessárias para cada funcionalidade.

## Criando um executável

O próximo passo para termos nosso projeto configurado é definir o plugin de build do Quarkus no Maven. O código que vamos escrever não contém nenhum chamada ao framework, pois por definição um framework chama o nosso código e não o contrário. Nós só precisamos definir pontos de extensão e definir o plugin de build. Para fazer isso adicione o seguinte plugin:

```xml
<plugin>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-maven-plugin</artifactId>
    <version>${quarkus.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>generate-code</goal>
                <goal>generate-code-tests</goal>
                <goal>build</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Agora basta executar `mvn quarkus:dev` para ver seu programa em execução. Mesmo sem nenhum código o comando será executado com sucesso.