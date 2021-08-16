insert into role(id, role_name)
values (1, 'DIRECTOR'),
       (2, 'WORKER');
insert into users(id, account_non_expired, account_non_locked, create_at, credentials_non_expired, email, email_code, enabled, first_name, last_name, password, update_at)
values ('05a0e28e-fbff-11eb-9a03-0242ac130003',true,true,'20-02-2021',true,'sattorovbekzodjon1997@gmail.com','sbek27061997dssd',true,'Bekzod','Sattorov','$2a$12$TOL6zKj0CkP9UiEyaSFayuniPN/x30GvQNMw0h/Fd.tDUgn1rYYiC','20-02-2021');
insert into users(id, account_non_expired, account_non_locked, create_at, credentials_non_expired, email, email_code, enabled, first_name, last_name, password, update_at)
values ('d8db9248-fbff-11eb-9a03-0242ac130003',true,true,'20-02-2021',true,'sattorovbekzodjondev97@gmail.com','sbek2706',true,'Bunyod','Sattorov','$2a$12$1fTF95yiJiBQZoXulNvaSOpE7CjN6ll8UHh1Uz0W3gt6qcbSyWih2','20-02-2021');
insert into users_role(users_id, role_id) values ('05a0e28e-fbff-11eb-9a03-0242ac130003',1);
insert into users_role(users_id, role_id) values ('d8db9248-fbff-11eb-9a03-0242ac130003',2);