JavaEExample

Для запуска примера требуется:  
Склонировать проект к себе на компьютер: git clone https://github.com/krutovsky/JavaEExample.git  
В конфигурационном файле */JavaEExample/JavaWebExample/src/main/resources/config.yml надо настроить пути к БД, файлам и к рабочим папкам:
```
dbConfig:
  databaseUrl: jdbc:mysql://localhost:3306/test
  password: '1'
  user: root
jsondataPath: /home/leon/JEExample/jsondata.txt
logFilePath: /home/leon/JEExample/log.log
mainDirPath: /home/leon/JEExample
```
В папке mainDirPath будут созданы папки prepare и work.  
В качестве БД я использовал mysql. Если вы используете другую базу, вам надо добавить в pom.xml файл зависимость для коннектора вашей БД, например для postgresql:  
```
<!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.1.4</version>
</dependency>

```

Что бы собрать проект нужно установить maven.
В папке с файлом pom.xml надо набрать mvn package. После установки всех зависмостей и сбора проекта в папке target можно будет увидеть файл JavaWebExample-1.0.war.   
Деплой производить стандартными средставми вашего контейнера сервлетов. На tomcat можно зайти в /manager и задеплоить из стандартного меню. 

Реализовано:  
Веб страница просмотра и редактирования справочника Места работы, а так же просмотра справочника Телефонная книга.   
Поиск по полям справочника места работы.   
Форма заполнения справочника телефонная книга.   
Валидация данных формы(HTML + JS).  
Кнопка cleaer удаляет все файлы из папки prepare.   
Кнопка send читает все файлы из папки prepare, удаляет их, записывает содержимое в файл в папку work и создает пустой файл флаг.  

