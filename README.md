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

## Основные возможности
- Регистрация и аутентификация пользователей
- Создание, чтение, обновление, удаление задач (CRUD)
- Задачи привязаны к конкретному пользователю
- Защита эндпоинтов через JWT токен

## API эндпоинты

### Auth
| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| POST | `/auth/register` | Регистрация пользователя |
| POST | `/auth/login` | Логин, возвращает JWT |

### Tasks (требуют Bearer токен)
| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/tasks` | Получить все задачи текущего пользователя |
| GET | `/tasks/{id}` | Получить задачу по ID |
| POST | `/tasks` | Создать новую задачу |
| PUT | `/tasks/{id}` | Обновить задачу |
| DELETE | `/tasks/{id}` | Удалить задачу |

## Запуск проекта

1. Клонировать репозиторий
```bash
git clone https://github.com/YSSkulkina/task-manager-api.git

## Настроить PostgreSQL (создать БД taskmanager)

## Обновить application.yml:

yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskmanager
    username: postgres
    password: ваш_пароль

## Запустить приложение:

bash
./mvnw spring-boot:run

## Пример запроса (регистрация)
json
POST /auth/register
{
  "email": "user@example.com",
  "password": "12345"
}
