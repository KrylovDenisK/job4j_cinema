--Ряд - место
CREATE TABLE IF NOT EXISTS halls (
      id      SERIAL PRIMARY KEY,
      row     INTEGER NOT NULL,
      place   INTEGER NOT NULL,
      price   INTEGER NOT NULL,
      status  BOOLEAN DEFAULT TRUE
    );

--Пользователи
CREATE TABLE IF NOT EXISTS accounts (
	id      SERIAL PRIMARY KEY,
	fio     VARCHAR (100) NOT NULL,
	phone   VARCHAR (100) UNIQUE NOT NULL,
	idHall  INTEGER NOT NULL,
	FOREIGN KEY (idHall) REFERENCES halls (id)
);

--Загрузка мест и цен
INSERT INTO halls (row, place, price) VALUES (1, 2, 500),
                                                 (1, 2, 500),
                                                 (1, 3, 500),
                                                 (2, 1, 1000),
                                                 (2, 2, 1000),
                                                 (2, 3, 1000),
                                                 (3, 1, 1500),
                                                 (3, 2, 1500),
                                                 (3, 3, 1500);

