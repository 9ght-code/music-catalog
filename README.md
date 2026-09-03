# Music Catalog API

Backend API для каталога музыки на **Scala 3** + **cask** + **PostgreSQL**.

## Технологии

- **Scala 3** — язык, синтаксис отступов
- **cask** — HTTP-фреймворк (от lihaoyi)
- **PostgreSQL** — реляционная БД
- **JDBC** — доступ к данным
- **sbt** — сборка
- **munit** — тестирование
- **Python / customtkinter** — десктопный GUI

## Архитектура

```
api → service → repository → model
 ↑
Main (bootstrap)
```

## Структура проекта

```
music-catalog/
├── README.md
├── build.sbt
├── gui.py                          # Десктопный GUI (Python)
├── db/
│   ├── schema.sql                  # Схема БД
│   └── seed.sql                    # Тестовые данные
└── src/
    ├── main/scala/
    │   ├── Main.scala              # Bootstrap: соединение + wiring
    │   ├── model/
    │   │   └── Models.scala        # Artist, Album, Track, Genre, TrackWithGenres
    │   ├── repository/
    │   │   ├── ArtistRepositoryTrait.scala  # Интерфейс для DI
    │   │   ├── ArtistRepository.scala       # JDBC-доступ к artists
    │   │   ├── TrackRepositoryTrait.scala
    │   │   └── TrackRepository.scala        # JDBC-доступ к tracks
    │   ├── service/
    │   │   ├── ArtistService.scala  # Валидация + бизнес-логика
    │   │   └── TrackService.scala
    │   ├── api/
    │   │   ├── ArtistRoutes.scala   # REST: CRUD артистов
    │   │   └── TrackRoutes.scala    # REST: треки
    │   └── mappers/
    │       └── mappers.scala        # Extension methods → JSON
    └── test/scala/
        ├── ArtistServiceSuite.scala  # Тесты сервиса (munit)
        └── mock/
            └── MockArtistRepository.scala
```

- **model/** — case class'ы (Artist, Album, Track, Genre, TrackWithGenres)
- **repository/** — JDBC-доступ к данным (SQL-запросы, маппинг ResultSet)
- **service/** — бизнес-логика, валидация (Either[String, Artist])
- **api/** — HTTP-эндпоинты (cask.Routes)
- **mappers/** — конвертация моделей в JSON (extension methods)
- **Main.scala** — bootstrap: создание зависимостей, wiring

## Эндпоинты

| Метод | Путь | Описание |
|---|---|---|
| GET | `/artists` | Список всех артистов |
| GET | `/artists/:id` | Артист по id (404 если не найден) |
| POST | `/artists` | Создать артиста `{"name":"..."}` |
| PUT | `/artists/:id` | Обновить артиста `{"name":"..."}` |
| DELETE | `/artists/:id` | Удалить артиста |
| GET | `/tracks` | Все треки с жанрами |
| GET | `/tracks/:id` | Трек по id |

## Запуск

```bash
# 1. Создать БД
createdb music_catalog

# 2. Применить схему и seed
psql -d music_catalog -f schema.sql
psql -d music_catalog -f seed.sql

# 3. Запустить сервер
sbt run

# 4. Запустить GUI
pip install customtkinter requests
python gui.py
```

## Тесты

```bash
sbt test
```

Тесты используют моки (MockArtistRepository) — реальная БД не нужна.
