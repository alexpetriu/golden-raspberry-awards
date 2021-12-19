# Bem vindo ao Golden Raspberry Awards

Esse projeto tem o intuito de apresentação ao teste seletivo da Texo IT.
- O projeto foi feito utilizando Spring boot versão 2.5.7
- A IDE de trabalho utilizada foi o Eclipse IDE for Enterprise Java and Web Developers
- O Banco de dados utilizado é o H2 Database
- Documentação da api: swagger-ui 2.7.0

**Execução do Projeto**

 - Executar o arquivo: ApiRestAplication como Java Aplication (Já irá subir automaticamente o Apache Tom Cat
 - Ou, Utilizar um servidor de aplicação de sua preferência
 
**Acesso ao Banco de Dados**

    http://localhost:8080/h2-console/

    Driver class: org.h2.Driver
    JDBC URL: jdbc:h2:file:./data/goldenraspberryawards
    User name: adam
    Password: sandler

**Chamada aos endpoints**
- Após subir a aplicação, a interação com os endpoints pode ser feita através do link: http://localhost:8080/swagger-ui.html

**Endpoints**

Os endpoints disponível na aplicação são:

- POST: http://localhost:8080/api/importar-arquivo-file
- body: 
	- form-data: 
		- file: arquivo.csv
- response: Mensagem de sucesso ou erro 200 ou 400
- *Responsável por ler um arquivo.csv selecionado e armazenar no banco de dados. A cada requisição os dados são substituídos*
---
- POST: http://localhost:8080/api/importar-arquivo-path
- body
	- aplication/json
		`{"pathFile": "src/main/resources/csv-files/movielist1.csv"}`
- *Responsável por ler um arquivo.csv indicando o path de localizaçaõ e armazenar no banco de dados. A cada requisição os dados são substituídos. Os arquivos deverão estar na pasta: src/main/resources/csv-files*
---
- GET: http://localhost:8080/api/intervalo-premios
- *Responsável por apresentar os produtores que tiveram o menor tempo de premiação consecutivas, e também os produtores com o maior tempo de premiação entre um ano e outro*
---
- GET: http://localhost:8080/api/nomeacoes/{year}
- *Responsável por apresentar todos os filmes nomeados de um determinado ano*
---
- GET: http://localhost:8080/api/vencedores
- *Responsável por listar todos os vencedores de todos os anos*
---
- GET: http://localhost:8080/api/vencedores/{year}
- *Responsável por listar todos os vencedores de um determinado ano*
---
**Testes de Integração**
- Os Testes de integração estão disponíveis em:
-  src/test/java
- package: com.goldenraspberryawards.apirest
- Arquivo: MovieAwardsTest.java

- Para executar os testes de integração, basta executar o arquivo MovieAwardsTest.java como: JUnit Test
---

> Dúvidas, estarei a disposição através do email: alexpetriu2009@gmail.com e whatsapp: (45)99995-3343
> Obrigado

