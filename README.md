# Task Manager API

REST API для управления задачами с JWT-авторизацией.

## Стек технологий

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- JUnit 5
- Testcontainers

## Основные возможности

- Регистрация и аутентификация пользователей
- Создание, чтение, обновление, удаление задач (CRUD)
- Задачи привязаны к конкретному пользователю
- Защита эндпоинтов через JWT токен

## API эндпоинты

### Auth (публичные)

| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| POST | `/auth/register` | Регистрация пользователя |
| POST | `/auth/login` | Логин, возвращает JWT токен |

### Tasks (требуют Bearer токен)

| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/tasks` | Получить все задачи текущего пользователя |
| GET | `/tasks/{id}` | Получить задачу по ID |
| POST | `/tasks` | Создать новую задачу |
| PUT | `/tasks/{id}` | Обновить задачу |
| DELETE | `/tasks/{id}` | Удалить задачу |

## Запуск проекта

### 1. Клонировать репозиторий

```bash
git clone https://github.com/YSSkulkina/task-manager-api.git
cd task-manager-api
```

### 2. Создать базу данных PostgreSQL
```bash
sql
CREATE DATABASE taskmanager;
```
### 3. Настроить подключение к БД
Отредактируйте файл src/main/resources/application.yml:
```bash
yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskmanager
    username: postgres
    password: ваш_пароль
```

### 4. Запустить приложение
```bash
./mvnw spring-boot:run
```

## Примеры запросов (Postman)

### Регистрация
```bash
POST http://localhost:8080/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "12345"
}
```

### Логин (получить токен)
```bash
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "12345"
}
```

### Ответ:
```bash
json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
### Создать задачу
```bash
POST http://localhost:8080/tasks
Authorization: Bearer <ваш_токен>
Content-Type: application/json

{
  "title": "Купить молоко",
  "description": "Зайти в магазин после работы",
  "status": "PENDING"
}
```

### Получить все задачи

```bash
GET http://localhost:8080/tasks
Authorization: Bearer <ваш_токен>
```

### Обновить задачу

```bash
PUT http://localhost:8080/tasks/1
Authorization: Bearer <ваш_токен>
Content-Type: application/json

{
  "title": "Купить молоко и хлеб",
  "description": "Обновлено",
  "status": "DONE"
}
```

### Удалить задачу
```bash
DELETE http://localhost:8080/tasks/1
Authorization: Bearer <ваш_токен>
```

### Запуск тестов
```bash
./mvnw test
```

Автор
Юлия Скулкина

GitHub: YSSkulkina

Ссылка на проект
https://github.com/YSSkulkina/task-manager-api
