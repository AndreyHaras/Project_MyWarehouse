## MyWarehouse

<img src=".\\ReadmeResources\\PrjectGif.gif" width="80%">

### Описание
Проект представляет собой программу, предназначенную для управления складом.
Позволяет:
+ производить учет накладных
+ осуществлять приемку товара
+ вести учет общего количества товара
+ вести учет контрагентов
### Технологии в проекте
Проект написан с применением: Java 8, Spring Boot, Spring Web, Spring Data, Spring Security, Hibernate, HTML, Thymeleaf, H2, Flywaydb.
### Техническое описание проекта
#### Запуск
Для запуска демонстрационной версии на H2:
+ Run/Debug Configuration => 
		Program arguments: введите строку без кавычек "--spring.profiles.active=develop"

Для запуска на PostgreSQL:
+ настройки для запуска ниже
+ "--spring.profiles.active=prod"
+ При запуске создается новая схема mywarehouse
#### Пароль при входе в приложение
+ Username = сотрудник, Password = user (Будут доступны только функции, предусмотренные для сотрудника)
+ Username = админ, Password = admin (Будут доступны все функции)
#### Порт
+ server.port=5056
#### Настройка при запуске на PostgreSQL
+ в файле application-prod.properties задать настройки postgresql 
+ указать spring.flyway url,user и password такие-же как и у postgresql
+ в pom.xml прописать db.url, db.user, db.password



