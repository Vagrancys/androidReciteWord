CREATE TABLE "user"(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER(10) NOT NULL,
    user_name varchar(50) NOT Null,
    token varchar(255),
    avatar varchar(500),
    last_login_at integer(10),
    recited_book integer(10));

CREATE TABLE "book" (
    _id       INTEGER NOT NULL PRIMARY KEY,
    book_id INTEGER(10) NOT NULL,
    book_user integer(10),
    book_name varchar(255),
    book_img varchar(255),
    book_url varchar(255),
    total_num integer(10),
    new_num integer(10),
    level_star integer(10),
    star integer(10),
    all_star integer(10),
    started_at integer(10),
    finished_at integer(10),
    last_finished_at integer(10),
    now_gate integer(10),
    finish_gate integer(10),
    finished integer(1),
    good integer(1),
    number integer(10),
    summary varchar(255));

CREATE TABLE "gate"(
    gate_id INTEGER NOT NULL PRIMARY KEY,
    gate_uid integer(10),
    gate_number integer(10),
    gate_star integer(1));
CREATE TABLE "word"(
    word_id INTEGER NOT NULL PRIMARY KEY,
    word_book integer(10),
    word_gate integer(10),
    word_name varchar(255),
    word_music varchar(255),
    word_music_url varchar(255),
    word_meaning varchar(255),
    word_sentence varchar(255),
    word_sentence_meaning varchar(255),
    word_sentence_url varchar(255),
    word_root varchar(255),
    word_error integer(1),
    word_error_text varchar(255),
    word_life integer(1),
    word_letter integer(1),
    word_study integer(1),
    word_time integer(10),
    word_last_time integer(10));


















