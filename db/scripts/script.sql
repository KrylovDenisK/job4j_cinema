create table if not exists halls (
      id serial,
      row integer,
      place integer,
      price integer,
      status boolean default true,
      primary key (id)
    );

create table if not exists accounts (
	id serial,
	fio varchar(100),
	phone varchar(100),
	idHall integer,
	primary key (id),
	foreign key (idHall) references halls (id)
);

insert into halls (row, place, price) values (1, 2, 500),
                                                 (1, 2, 500),
                                                 (1, 3, 500),
                                                 (2, 1, 1000),
                                                 (2, 2, 1000),
                                                 (2, 3, 1000),
                                                 (3, 1, 1500),
                                                 (3, 2, 1500),
                                                 (3, 3, 1500);

