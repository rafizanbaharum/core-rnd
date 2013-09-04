insert into RND_PRINCIPAL
(ID, NAME, PRINCIPAL_TYPE, LOCKED, M_ST, C_ID)
values (1, 'root', 0, 1, 1, 0);

insert into RND_USER (ID, REALNAME, PASSWORD, EMAIL, ACTOR_ID)
values (1, 'System Root', '6367c48dd193d56ea7b0baad25b19455e529f5ee', 'rafizan.baharum@gmail.com', null);

insert into RND_PRINCIPAL_ROLE (ID, ROLE_TYPE, PRINCIPAL_ID, M_ST, C_ID)
values(1, 0, 1, 1, 1);
