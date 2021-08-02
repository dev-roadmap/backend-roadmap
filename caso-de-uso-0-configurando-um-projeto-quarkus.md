# Configurando um projeto Quarkus.io

* [Principal](./README.md)
* [Próximo: Decidindo o recurso](./caso-de-uso-1-decidindo-o-recurso.md)

O Quarkus é um framework fácil de configurar. Tudo que precisamos saber é usar o Maven ou o Gradle, no nosso caso vamos usar o Maven.

Caso você não tenha experiência com o Maven, você pode usar a opção [Start Coding](https://code.quarkus.io/) para gerar a estrutura do projeto com todas as configurações necessárias.

## Maven 101

Para que não conhece o Maven, ele é um sistema de build completo. Você não precisa baixar manualmente todas as bibliotecas e chamar o `javac` para compilar seu código, basta alterar o arquivo [`pom.xml`](./pom.xml). Nele são definidas todas as informações necessárias para build, assim como a dependências e a forma de distribuição do seu programa. 

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
