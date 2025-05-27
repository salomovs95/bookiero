
    create table authors (
        id integer not null auto_increment,
        full_name varchar(255) not null,
        profile_picture varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table books (
        author_id integer,
        id integer not null auto_increment,
        page_count integer not null,
        publish_year integer not null,
        in_stock_amount bigint not null,
        book_cover varchar(255),
        category varchar(255) not null,
        edition varchar(255) not null,
        editor varchar(255) not null,
        esbn varchar(255) not null,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table borrows (
        book_id integer not null,
        borrowed_at date not null,
        id integer not null auto_increment,
        returned_at date,
        user_id integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table users (
        id integer not null auto_increment,
        joined_at date not null,
        address varchar(255) not null,
        email varchar(255) not null,
        full_name varchar(255) not null,
        password varchar(255) not null,
        phone varchar(255) not null,
        role varchar(255),
        tax_id varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table users 
       add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email);

    alter table users 
       add constraint UKdu5v5sr43g5bfnji4vb8hg5s3 unique (phone);

    alter table users 
       add constraint UKtb5xl1ncpt9amut4mf6movgvk unique (tax_id);

    alter table users 
       add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);

    alter table books 
       add constraint FKfjixh2vym2cvfj3ufxj91jem7 
       foreign key (author_id) 
       references authors (id);

    alter table borrows 
       add constraint FK8789wjikihu9ocbhamiw789y9 
       foreign key (book_id) 
       references books (id);

    alter table borrows 
       add constraint FKd64ttskt7j96v1nqtpry3pp2a 
       foreign key (user_id) 
       references users (id);
