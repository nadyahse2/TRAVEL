# Travel Database

## Краткое описание

Приложение Travel Database — это приложение на Java, предназначенное для управления информацией о туристических поездках. Оно позволяет пользователям просматривать, добавлять, изменять и удалять данные о поездках, используя базу данных PostgreSQL в качестве хранилища данных.

## Основные функции

*   **Аутентификация пользователей:** Поддержка ролей администратора и гостя для разграничения прав доступа. Пользователи и их права заданы заранее.
*   **Управление базой данных (только для админа):**
    *   Создание и удаление базы данных `travel_db`.
    *   Создание таблицы `trips`.
*   **Управление данными о поездках(только для админа):**
    *   Добавление новых поездок.
    *   Редактирование информации о текущих поездках.
    *   Удаление поездок.
*   **Поиск поездок:** Поиск поездок по определенному месту назначения.
*   **Графический интерфейс:** Интуитивно понятный интерфейс Swing.
*   **Поддержка PostgreSQL:** Использование PostgreSQL в качестве надежного и масштабируемого хранилища данных.

## Технологии

*   Java
*   Swing (для графического интерфейса)
*   JDBC (для подключения к PostgreSQL)
*   PostgreSQL

## Предназначение

Приложение Travel Database предназначено для:

*   Организаций, занимающихся планированием и организацией туристических поездок.
*   Людей, желающих хранить и управлять информацией о своих поездках.

## Инструкция по установке

1.  Установите PostgreSQL
2.  В базе данных postgres от имени супер пользователя выполните sql запросы из файлов sql(прилагаются)
3.  Клонируйте репозиторий с кодом приложения.
4.  Импортируйте проект в вашу IDE (например, IntelliJ IDEA или Eclipse).
5.  Добавьте JDBC-драйвер PostgreSQL в classpath вашего проекта.
6.  Измените параметры подключения к базе данных в классе `DbManager` (URL, имя пользователя, пароль).
7.  Запустите класс `MainFrame`.

## Скриншоты
Пример работы гостя:

![1](https://github.com/user-attachments/assets/3838d0c0-09b6-4dde-b51b-80e4b51143ca)
![2](https://github.com/user-attachments/assets/622435ac-8366-4756-8ed0-9d0e64847899)
![3](https://github.com/user-attachments/assets/191f3c77-84bd-4efd-863c-8f84669b9375)
![4](https://github.com/user-attachments/assets/35a74484-ae75-4488-8543-ab5a659e997a)

Пример для админа:

![image](https://github.com/user-attachments/assets/bacd495d-54a2-47d0-aad5-e46c445d0b45)
![image](https://github.com/user-attachments/assets/4fc0232e-a5bc-4113-9baf-48155f456748)
![image](https://github.com/user-attachments/assets/52e2aba8-6a2f-4a89-b6be-e4a44b473efd)
![image](https://github.com/user-attachments/assets/bda9d595-3aa3-4a70-97f4-e9cada6f5f4f)
![image](https://github.com/user-attachments/assets/6ae07e95-56ab-4aae-99e6-9db661c1d21b)
![image](https://github.com/user-attachments/assets/06f9e199-bb3f-479e-a81a-df26aaaeecb7)
