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
      `music-catalog/src/main/scala/Main.scala` (Using.resource, ResultSet-итерация)
- [ ] Проект 1, милстоун 1: схема БД — ждём `db/schema.sql` + `db/seed.sql` от пользователя
- [ ] Проект 1: REST (CRUD, фильтры, пагинация, tsvector-поиск, похожие треки), тесты, README
- [ ] Проект 2: sea-battle
- [ ] Проект 3: crypto-tracker

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

Проверить `db/schema.sql` и `db/seed.sql` от пользователя, применить к базе,
показать результат, затем милстоун 2 (repository-слой на JDBC).
