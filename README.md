# Create 26.3 для Fabric — неофициальный порт от BLauncher

Порт мода [Create](https://github.com/Creators-of-Create/Create) на **Minecraft 26.3** под **Fabric**.
Основа — [Create Fly](https://github.com/ZurrTum/Create-Fly) (ZurrTum, CC0), перенесённый на 26.3.

> ⚠️ **Бета и неофициальная сборка.** Это не выпуск команды Create. Ошибки порта — наши, пишите о них
> в [Issues](https://github.com/umuwi/create-26.3-fabric/issues), а не авторам Create.

Версия для NeoForge: [umuwi/create-26.3-neoforge](https://github.com/umuwi/create-26.3-neoforge).

## Как скачать

- **Проще всего — через [BLauncher](https://blauncher.ru):** вкладка «Моды» → найдите `create` → карточка
  «Create 26.3 · ЭКСКЛЮЗИВ» → «+» → выберите сборку 26.3 Fabric. Лаунчер скачает и проверит файл сам.
- **Вручную:** готовый jar — во вкладке [Releases](https://github.com/umuwi/create-26.3-fabric/releases).
  Положите его в папку `mods` сборки Minecraft 26.3 с Fabric Loader 0.20.1 или новее (Fabric API не обязателен).

Flywheel и Ponder уже встроены в jar — отдельно ставить их не нужно.

## Что проверено

Мир и механизмы, контрапции (подшипники, суперклей), Ponder, меню Create (кнопка-очки); с Fabric API
и без него. Совместимость: Sodium 0.9.3-beta.1 и Iris 1.11.7 с шейдерами (Complementary Reimagined).
Поезда, игра по сети и окна механизмов подробно проверены на NeoForge-версии — код у них общий.

## Известные ограничения

- Обмен предметами и жидкостями с другими модами работает только при установленном Fabric API;
  без него — только с ванильными сундуками и между блоками Create.
- Не проверены сигналы, склад и посылки, лифты, жидкости, долгая игра.
- Экрана настроек нет — «Настроить…» в меню Create открывает папку с файлами настроек.

## Сборка из исходников

JDK 25, затем `./gradlew jar` — jar появится в `build/libs/`. `./gradlew runClient` запускает игру для проверки.

## Авторы и лицензии

- **Create** — The Create Team / The Creators of Create. Код — MIT, ресурсы (`assets/create`) —
  All Rights Reserved, см. [`licenses/Create-LICENSE`](licenses/Create-LICENSE).
- **Create Fly** — ZurrTum, CC0 ([`LICENSE`](LICENSE)); исходный README — [`README-Create-Fly.md`](README-Create-Fly.md).
- **Flywheel**, **Ponder**, **NeoForge**, **Fabric API** — лицензии в папке [`licenses`](licenses).
- Перенос на 26.3 — BLauncher ([blauncher.ru](https://blauncher.ru)).

---

**English:** unofficial port of Create to Minecraft 26.3 (Fabric), based on Create Fly. Beta — please report
port bugs here, not to the Create team. Download the jar from Releases or install it via the BLauncher launcher.
