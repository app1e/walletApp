# --- First database schema

# --- !Ups

create table app_profile (
  id                        BIGINT NOT NULL AUTO_INCREMENT,
  email                      VARCHAR(255),
  active            INT,
  CONSTRAINT pk_app_profile PRIMARY KEY (id))
;

CREATE SEQUENCE app_profile_seq START WITH 1;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS app_profile;


SET REFERENTIAL_INTEGRITY TRUE;

DROP SEQUENCE IF EXISTS app_profile_seq;