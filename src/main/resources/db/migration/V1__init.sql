CREATE TABLE IF NOT EXISTS post
(
    id               bigserial,
    name             varchar(100) NOT NULL,
    description      varchar(255) NOT NULL,
    create_date_time timestamp    NOT NULL,
    update_date_time timestamp,
    constraint post_pk primary key (id)
);

comment on table post is 'Таблица постов';
comment on column post.id is 'Идентификатор поста';
comment on column post.name is 'Название поста';
comment on column post.description is 'Описание поста';
comment on column post.create_date_time is 'Дата создания поста';
comment on column post.update_date_time is 'Дата обновления поста';

CREATE TABLE IF NOT EXISTS comment
(
    id               bigserial,
    value            varchar(255) NOT NULL,
    create_date_time timestamp    NOT NULL,
    update_date_time timestamp,
    post_id          bigint,
    constraint comment_pk primary key (id),
    constraint comment_post_id_fk foreign key (post_id) references post (id)
);

comment on table comment is 'Таблица комментариев';
comment on column comment.id is 'Идентификатор комментария';
comment on column comment.value is 'Значение комментария';
comment on column comment.create_date_time is 'Дата создания комментария';
comment on column comment.update_date_time is 'Дата обновления комментария';
