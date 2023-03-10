create table cities
(
    id                serial
        primary key
        unique,
    name              varchar(50),
    status            varchar(25)  default 'ACTIVE',
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)
);


INSERT INTO cities (name)
VALUES ('Барановичи'),
       ('Иваново'),
       ('Петриков'),
       ('Барань'),
       ('Ивацевичи'),
       ('Пинск'),
       ('Белоозерск'),
       ('Ивье'),
       ('Полоцк'),
       ('Белыничи'),
       ('Поставы'),
       ('Берёза'),
       ('Калинковичи'),
       ('Пружаны'),
       ('Березино'),
       ('Каменец'),
       ('Березовка'),
       ('Кировск'),
       ('Речица'),
       ('Бобруйск'),
       ('Клецк'),
       ('Рогачёв'),
       ('Борисов'),
       ('Климовичи'),
       ('Браслав'),
       ('Кличев'),
       ('Светлогорск'),
       ('Брест'),
       ('Кобрин'),
       ('Свислочь'),
       ('Буда-Кошелево'),
       ('Копыль'),
       ('Сенно'),
       ('Быхов'),
       ('Коссово'),
       ('Скидель'),
       ('Костюковичи'),
       ('Славгород'),
       ('Василевичи'),
       ('Кричев'),
       ('Слоним'),
       ('Верхнедвинск'),
       ('Круглое'),
       ('Слуцк'),
       ('Ветка'),
       ('Крупки'),
       ('Смолевичи'),
       ('Вилейка'),
       ('Сморгонь'),
       ('Витебск'),
       ('Лепель'),
       ('Солигорск'),
       ('Волковыск'),
       ('Лида'),
       ('Старые Дороги'),
       ('Воложин'),
       ('Логойск'),
       ('Столбцы'),
       ('Высокое'),
       ('Лунинец'),
       ('Столин'),
       ('Любань'),
       ('Ганцевичи'),
       ('Ляховичи'),
       ('Толочин'),
       ('Глубокое'),
       ('Туров'),
       ('Гомель'),
       ('Малорита'),
       ('Горки'),
       ('Марьина Горка'),
       ('Узда'),
       ('Городок'),
       ('Микашевичи'),
       ('Гродно'),
       ('Минск'),
       ('Фаниполь'),
       ('Миоры'),
       ('Давид-Городок'),
       ('Могилёв'),
       ('Хойники'),
       ('Дзержинск'),
       ('Мозырь'),
       ('Дисна'),
       ('Молодечно'),
       ('Чаусы'),
       ('Добруш'),
       ('Мосты'),
       ('Чашники'),
       ('Докшицы'),
       ('Мстиславль'),
       ('Червень'),
       ('Дрогичин'),
       ('Мядель'),
       ('Чериков'),
       ('Дубровно'),
       ('Чечерск'),
       ('Дятлово'),
       ('Наровля'),
       ('Несвиж'),
       ('Шклов'),
       ('Ельск'),
       ('Новогрудок'),
       ('Новолукомль'),
       ('Щучин'),
       ('Жабинка'),
       ('Новополоцк'),
       ('Житковичи'),
       ('Жлобин'),
       ('Орша'),
       ('Жодино');
