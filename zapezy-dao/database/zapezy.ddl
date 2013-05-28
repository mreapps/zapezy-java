create table users
(
  uid serial primary key,
  email_address varchar(255) not null,
  password varchar(255) not null,
  created_at timestamp not null,
  activation_token varchar(100),
  activated_at timestamp
);
create unique index uix_users_email_address on users (email_address);

create table user_role
(
  uid serial primary key,
  user_uid integer not null,
  role varchar(50) not null
);
alter table user_role add constraint fk_user_role_user foreign key (user_uid) references users (uid);