# Intervale Course
**Задание**

Реализовать клиент-серверное приложение с использованием spring boot, состоящее из единственного http-контроллера, которое вернёт ответ соответствующий запросу:

GET /hello - 200 Hello!

- GET /withParams?{singleParamName}={paramValue} - 202 paramValue

- GET /withPathVariable/{id} - 201 for {id}

- POST с заголовком Content-Type: applicatiom/json /echo - 200 json

- POST с заголовком Content-Type: applicatiom/xml /echo - 200 xml

- PUT /put - 200 OK

- GET /cookie при первом обращении к серверу - 200 OK и установить cookie с датой запроса

- GET /cookie при повторном обращении к серверу - 200 вывести дату предыдущего обращения и обновить cookie с датой запроса

*подключить сваггер (см. https://springdoc.org/#getting-started)