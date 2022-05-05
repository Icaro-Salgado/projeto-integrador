![image](https://user-images.githubusercontent.com/101267189/164761759-18d208fe-c31e-4307-ab75-212aadaa33ec.png)

# Welcome to mercadoFresh!

MercadoFresh é um projeto de fechamento do curso de programação Java da escola **Digital House** em parceria com o **Mercado Livre**. O projeto compreende uma API de gestão de estoque e armazenamento de mercadorias que são disponibilizadas por parceiros comerciais (leia-se vendedores) em local com temperatura controlada.

## Tecnologias utilizadas para desenvolvimento da API

-   Linguagem de desenvolvimento: JAVA v.11
-   Framework: Springboot v. 2.6.7
-   Autenticação: Java-JWT v.3.18.2
-   Banco de dados: Postgres (PostgreSQL) 13.6
-   Ambiente virtual: Docker v. 20.10.11

## Ferramentas necessárias

-   Docker v.3.1
-   JAVA v.11

Para subir a infraestrutura da aplicação, entre na pasta onde se encontra o projeto e rode o código abaixo no terminal da sua máquina.
``` sh
   docker-compose -f docker/docker-compose.yaml up -d
 ```

Para realizar testes de integração na aplicação entre na pasta do projeto rode o código abaixo no terminal da sua máquina.
``` sh
   docker-compose -f docker/docker-compose-test.yaml up -d
```

## Testando a API

As comunicações com a API são realizadas através de métodos HTTP e os endpoints e descrições de funcionamento encontram-se na documentação do [requisito 01](Doc/requisitos/Requisito%2001.md).

Para que as rotas respondam da maneira desejada é necessário ter um batch (lote) já cadastrado na base de dados e o usuário deve estar logado e ter permissão de acesso ao sistema de gestão do Warehouse.

E caso utilize o POSTMAN você pode importar a [collection](Doc/Projeto%20integrador.postman_collection.json) contida na pasta **doc**.

As definições das rotas também estão documentas no Swagger, que pode ser acessado pelo link:  
http://localhost:8080/swagger-ui

## Membros do grupo

| Evandro | Icaro | Klinton | Maran |Paulo| Pedro | Thainan |
| --- | --- | --- | --- | --- | --- | --- |
|[<img src="https://avatars.githubusercontent.com/u/39993682?v=4" width=115><br><sub></sub>](https://github.com/evandrosutil)|[<img src="https://avatars.githubusercontent.com/u/101267189?v=4" width=115><br><sub></sub>](https://github.com/Icaro-Salgado) |[<img src="https://avatars.githubusercontent.com/u/97066287?v=4" width=115><br><sub></sub>](https://github.com/MeliKlin) |[<img src="https://avatars.githubusercontent.com/u/80549051?v=4" width=115><br><sub></sub>](https://github.com/maranbrasil) |[<img src="https://avatars.githubusercontent.com/u/101268601?v=4" width=115><br><sub></sub>](https://github.com/Paulorlima) |[<img src="https://avatars.githubusercontent.com/u/73892750?v=4" width=115><br><sub></sub>](https://github.com/pedroLSoares) |[<img src="https://avatars.githubusercontent.com/u/101267217?v=4" width=115><br><sub></sub>](https://github.com/ThainanEsteves)

## Apoio técnico


[Michelle de Souza](https://www.linkedin.com/in/michelledsouza3?miniProfileUrn=urn%3Ali%3Afs_miniProfile%3AACoAABVxLgwB8sD4Rs6oS_JjqHbXnw__jC3g30E&lipi=urn%3Ali%3Apage%3Ad_flagship3_search_srp_all%3Bp1zwK5pVRrOjDzEpWTthog%3D%3D) e [Kenyo Faria](https://www.linkedin.com/in/kenyo-faria?miniProfileUrn=urn%3Ali%3Afs_miniProfile%3AACoAAATNPiYBMewl43f-CzfvdxywpQQHs282oxk&lipi=urn%3Ali%3Apage%3Ad_flagship3_search_srp_all%3Bw7XZjiPxRpqOSrPGvT3v%2FA%3D%3D)


**mercadoFresh** v. 0.1.0