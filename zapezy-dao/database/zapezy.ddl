create table users
(
  uid serial primary key,
  email_address varchar(255) not null,
  password varchar(255) not null,
  created_at timestamp not null,
  activation_token varchar(100),
  activated_at timestamp,
  role varchar(20) not null
);
create unique index uix_users_email_address on users (email_address);