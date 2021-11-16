CREATE TABLE IF NOT EXISTS gift_certificates
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    description text,
    price integer,
    duration integer,
    last_update_date timestamp with time zone,
                                   name character varying(20),
    create_date timestamp with time zone,
                                   CONSTRAINT gift_certificates_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS tags
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(20),
    CONSTRAINT tags_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS certificate_tag_maps
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    gift_certificate_id integer,
    tag_id integer,
    CONSTRAINT gift_certificate_id FOREIGN KEY (gift_certificate_id)
    REFERENCES gift_certificates (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT tag_id FOREIGN KEY (tag_id)
    REFERENCES tags (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

CREATE TABLE users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    forename character varying(15),
    surname character varying(15),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    purchase_timestamp timestamp with time zone,
    total_cost integer,
    user_id integer,
    certificate_id integer,
    CONSTRAINT certificate_id FOREIGN KEY (certificate_id)
        REFERENCES gift_certificates (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

