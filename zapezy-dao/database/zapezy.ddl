begin;
  create table users
  (
    uid serial primary key not null,
    email_address varchar(255) not null,
    password varchar(255) not null,
    created_at timestamp not null,
    activation_token varchar(100),
    activated_at timestamp,
    reset_password_token varchar(100),
    reset_password_token_created_at timestamp,
    role varchar(20) not null,
    fb_id int8,
    fb_access_token varchar(256),
    fb_access_token_expires timestamp,
    firstname varchar(100),
    lastname varchar(100),
    birthday date,
    gender int2 not null,
    image_uid int4
  );
  create unique index uix_users_email_address on users (email_address);

  create table blob_file
  (
    uid serial primary key not null,
    content oid not null
  );

  alter table users add constraint fk_users_image foreign key (image_uid) references blob_file (uid);

  create table channel
  (
    uid serial primary key not null,
    channel_id varchar(255) not null,
    channel_name_short varchar(30) not null,
    channel_name_full varchar(255) not null,
    epg_base_url varchar(255),
    icon_url varchar (255),
    sort_index int2,
    webtv_url varchar(255)
  );
  create unique index uix_channel_channel_id on channel (channel_id);

  create table channel_icon
  (
    uid serial primary key not null,
    channel_uid integer not null,
    blob_file_uid integer not null,
    valid_from date not null
  );
  alter table channel_icon add constraint fk_channel_icon_channel foreign key (channel_uid) references channel (uid);
  alter table channel_icon add constraint fk_channel_icon_file foreign key (blob_file_uid) references blob_file (uid);
  create unique index uix_channel_icon on channel_icon (channel_uid, blob_file_uid, valid_from);

  create table programme
  (
    uid serial primary key not null,
    channel_uid integer not null,
    start timestamp not null,
    stop timestamp not null,
    title_nb varchar(100),
    sub_title_nb varchar(100),
    description_nb text,
    title_sv varchar(100),
    sub_title_sv varchar(100),
    description_sv text,
    title_en varchar(100),
    sub_title_en varchar(100),
    description_en text,
    title_dk varchar(100),
    sub_title_dk varchar(100),
    description_dk text,
    date_string varchar(8)
  );
  alter table programme add constraint fk_programme_channel foreign key (channel_uid) references channel (uid);
  create unique index uix_programme_start on programme (channel_uid, start);
  create index ix_programme_stop on programme (channel_uid, stop);

  create table person
  (
    uid serial primary key not null,
    name varchar(256) not null unique
  );

  create table programme_director
  (
    programme_uid integer not null,
    person_uid integer not null,
    primary key (programme_uid, person_uid)
  );
  alter table programme_director add constraint fk_programme_director_programme foreign key (programme_uid) references programme (uid);
  alter table programme_director add constraint fk_programme_director_person foreign key (person_uid) references person (uid);

  create table programme_actor
  (
    programme_uid integer not null,
    person_uid integer not null,
    primary key (programme_uid, person_uid)
  );
  alter table programme_actor add constraint fk_programme_actor_programme foreign key (programme_uid) references programme (uid);
  alter table programme_actor add constraint fk_programme_actor_person foreign key (person_uid) references person (uid);

  create table log_request
  (
    uid serial primary key not null,
    path varchar(256) not null,
    execution_time integer not null,
    remote_ip varchar(100) not null,
    user_agent varchar(256) not null,
    created_time timestamp not null default current_timestamp
  );
end;