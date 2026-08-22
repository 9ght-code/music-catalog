# Статус подготовки к стажировке (junior backend Scala)

> Этот файл — «точка восстановления». Если сессия чата потеряется, новый чат
> должен начать с чтения этого файла и продолжить работу.

## Цель

Три портфолио-проекта на Scala 3 + PostgreSQL для отбора на стажировку.
Формат — 3 отдельных репозитория.

## Состав проектов

1. **music-catalog** (в работе) — музыкальный каталог: REST + PostgreSQL
   (полнотекстовый поиск tsvector, джойны, пагинация, «похожие треки»)
2. **sea-battle** — морской бой по сети: сокеты, конкурентность, история партий
   и рейтинг (Эло) в PostgreSQL
3. **crypto-tracker** — крипто-трекер с алертами в Telegram: внешний API,
   фоновые задачи, time-series в PostgreSQL, REST

## Окружение (Windows)

| Инструмент | Версия | Заметки |
|---|---|---|
| Java | 26.0.1 | PATH есть |
| sbt | 2.0.6 | Только в PowerShell (`sbt.bat`), в bash не виден |
| scala-cli | 1.16.0 | Для быстрых экспериментов, в PowerShell |
| git | 2.53.0 | Есть |
| PostgreSQL | 18.6 | `C:\Program Files\PostgreSQL\18\bin` — НЕ в PATH, вызывать полным путём |
| IDE | VS Code | Расширение Scala (Metals) — установить/проверить |

**PostgreSQL:** user `postgres`, пароль `123456`, порт 5432.
База создана: `music_catalog`.
psql: `& 'C:\Program Files\PostgreSQL\18\bin\psql.exe' -U postgres -h localhost -p 5432`

## Прогресс

- [x] Сетап окружения (Java, sbt, scala-cli, git, PostgreSQL 18.6, база `music_catalog`)
- [x] Основы Scala (день 1): val/var, выражения, case class, pattern matching,
      Option, collections (map/filter/groupBy/sortBy/take/foldLeft) — задачи в `playground/tasks1.scala`
- [x] Проект 1, милстоун 0: подключение к PostgreSQL из Scala через JDBC —
      `Using.resource`, `ResultSet`-итерация
- [x] Проект 1, милстоун 1: схема БД + сид — `music-catalog/db/schema.sql` и `db/seed.sql`
      (5 таблиц, M2M `track_genres` с составным PK, каскады, применено к базе)
- [x] Проект 1, милстоун 2: модели + repository-слой на JDBC
      (`ArtistRepository`, `TrackRepository`)
- [x] Проект 1, милстоун 3: REST API на cask — `GET /artists`, `GET /artists/:id` (404),
      `GET /tracks` (жанры через string_agg). Проверено curl'ом.
- [x] Проект 1, архитектура: слои model / repository / service / api, Main — bootstrap
- [x] Проект 1, CRUD: POST/PUT/DELETE для артистов (валидация через Either)
- [x] Проект 1, маппинг: extension methods в mappers/
- [x] Проект 1, DI: trait ArtistRepositoryInterface + MockArtistRepository для тестов
- [x] Проект 1, GUI: десктопное приложение (customtkinter + requests)
- [ ] Проект 1: фильтры, пагинация, tsvector-поиск, похожие треки, README
- [ ] Проект 2: sea-battle
- [ ] Проект 3: crypto-tracker

## Архитектура проекта 1 (music-catalog)

Классическая слоёная архитектура, зависимости смотрят вниз:
`api → service → repository → model`

```
src/main/scala/
├── Main.scala          bootstrap: соединение + wiring, extends cask.Main,
│                       allRoutes = Seq(ArtistRoutes(...), TrackRoutes(...))
├── model/              case class'ы (домен): Artist, Album, Track, Genre, TrackWithGenres
├── repository/         JDBC-доступ: ArtistRepository, TrackRepository + Trait для DI
├── service/            бизнес-логика: ArtistService, TrackService (валидация, Either)
├── api/                HTTP: ArtistRoutes, TrackRoutes (классы cask.Routes)
└── mappers/            extension methods: model → ujson.Obj
src/test/scala/
├── ArtistServiceSuite.scala    тесты сервиса (munit + mock)
└── mock/MockArtistRepository.scala
gui.py                  десктопный GUI (customtkinter)
```

- DI — просто передача зависимостей в конструктор (Main собирает цепочку)
- Роуты — классы `cask.Routes` с `(implicit cc: castor.Context, log: cask.Logger)`
- JSON: ручная сборка `ujson.Obj` в api-слое (позже можно upickle-деривация)

## Роли и правила

- Пользователь пишет код, ассистент направляет, проверяет, объясняет (режим «учитель»)
- Ассистент: ревью кода, проверка запуском, вопросы как на собеседовании
- Аналогии для пользователя: C#/.NET → Scala (case class ≈ DTO, Option ≈ nullable, Either ≈ Result)

## Спецификация схемы (милстоун 1, ждёт реализации)

- `artists(id serial PK, name text not null unique)`
- `albums(id serial PK, title text not null, year int, artist_id FK artists)`
- `tracks(id serial PK, title text not null, duration_seconds int, album_id FK albums)`
- `genres(id serial PK, name text not null unique)`
- `track_genres(track_id FK, genre_id FK, PK(track_id, genre_id))`
- Вопросы для пользователя: ON DELETE поведение, индексы, зачем junction-таблица
- Данные: Queen, Metallica, Daft Punk, Radiohead, ABBA + реальные альбомы/треки/жанры

## Следующий шаг

Фильтры/пагинация и tsvector-поиск (`/search?q=...`), похожие треки,
тесты (munit + mock), README → затем проект 2 (sea-battle).

## Запуск

```powershell
cd music-catalog
sbt run          # сервер на http://localhost:8080
```

Проверка: `curl http://localhost:8080/artists` и `curl http://localhost:8080/tracks`.
