create table public.persons(
	id serial primary key,
	name text,
	date_of_birth timestamp
);

create table public.children(
	id serial primary key,
	parent_id integer,
	name text,
	date_of_birth timestamp,
	foreign key (parent_id) references public.persons(id)
);

insert into public.persons(name, date_of_birth) values ('mama', now());
insert into public.persons(name, date_of_birth) values ('tata', now());

insert into public.children(parent_id, name, date_of_birth) values (1, 'mama', now());
insert into public.children(parent_id, name, date_of_birth) values (2, 'tata', now());