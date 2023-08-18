## Buysell


Приложение представляет собой интернет площадку для покупки и продажи товаров.
Объявления выставляемые на сайте можно настраивать, определяя им название, описание, цену и установив картинку. Помимо этого, зарегистрированные пользователи имеют возможность оставлять комментарии на карточке товара, а владелец объявления может их удалять, если на, то есть причина.
Так же пользователь может настраивать свой аккаунт и, на пример, менять аватар.

**Front-end** часть была предоставлена компанией: SkyPro  
**Back-end** часть была написана мной.

Проект построен на базе **Spring Boot 2.7.10** и использует следующие технологии:
* Java 11
* Spring Core | Spring Web MVC | Spring Data JPA | Spring Security | Spring TestContext | Spring AOP
* Hibernate | PostgreSQL | Liquibase
* JUnit | Mockito | H2
* Jackson
* Tomcat
* slf4 | log4j2
* Docker

Для запуска приложения нужно совершить следующие действия:
1) Выполнить команду:
```sh
docker run -p 3000:3000 --rm --name somename ghcr.io/bizinmitya/front-react-avito:v1.17
```
Это позволить запустить front-end часть приложения, **somename** нужно заменить на удобное имя для контейнера.  

2) В файле *application.properties* нужно указать данные для работы с базой данных.

Ввиду того, что в качестве основы приложения используется Spring Boot, оно не требует серьёзной настройки и может работать 
без переконфигурирования. Интеграционные тесты используют H2, в качестве встроенной базы данных, это позволяет проводить 
тестирование независимо он производственной базы данных. Конфигурация для запуска интеграционных тестов уже настроена.
Нужно учитывать, что liquibase миграция подразумевает использование PostgreSQL в качестве базы данных ввиду использования 
специфических параметров при создании схем.

-------------------------------------------

The application represents an online platform for buying and selling goods. 
Advertisements posted on the website can be customized by providing them with a title, description, price, and an image. In addition to this, registered users have the ability to leave comments on the product card, and the ad owner can delete them if necessary. Users can also customize their accounts, for instance, by changing their avatars.

**The front-end** part of the project was provided by the company SkyPro 
**The back-end** part was developed by me.

The project is built on **Spring Boot 2.7.10** and utilizes the following technologies:
* Java 11
* Spring Core | Spring Web MVC | Spring Data JPA | Spring Security | Spring TestContext | Spring AOP
* Hibernate | PostgreSQL | Liquibase
* JUnit | Mockito | H2
* Jackson
* Tomcat
* slf4 | log4j2
* Docker

To run the application, follow these steps:
1) Execute the command:
```sh
docker run -p 3000:3000 --rm --name somename ghcr.io/bizinmitya/front-react-avito:v1.17
```
This will start the front-end part of the application. Replace "somename" with a preferred container name. 

2) In the application.properties file, provide the necessary database connection details.

Since the application is built on Spring Boot, it does not require extensive configuration and can work without major adjustments. Integration tests use H2 as an embedded database, allowing testing independently of the production database. Configuration for running integration tests is already set up. Keep in mind that Liquibase migration assumes the use of PostgreSQL as the database due to specific schema creation parameters.











