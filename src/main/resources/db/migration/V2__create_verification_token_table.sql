/*CREATE TABLE verification_token (
    id bigint NOT NULL auto_increment PRIMARY KEY,
    user_id bigint,
    token VARCHAR(255),
    expiry_date TIMESTAMP,
    FOREIGN KEY (user_id) references my_db.social_media_user(id)
);*/