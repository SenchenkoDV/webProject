package com.senchenko.aliens.dao;

public class SqlQueries {
    public static final String ID_COMMENT = "id_comment";
    public static final String DATE = "date";
    public static final String ID_MONSTER = "id_monster";
    public static final String NAME = "name";
    public static final String ID_RACE = "id_race";
    public static final String RACE = "race";
    public static final String DESCRIPTION = "description";
    public static final String AVERAGE_RATING = "average_rating";
    public static final String PICTURE_ADDRESS = "picture_address";
    public static final String MARK = "mark";
    public static final String COMMENT = "comment";
    public static final String ID_USER = "id_user";
    public static final String ID_ROLE = "id_role";
    public static final String ROLE = "role";
    public static final String RATING = "rating";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";

    public static final String SQL_SELECT_ALL_COMMENTS =
            "SELECT * FROM comments " +
                    "JOIN monsters ON comments.id_monster = monsters.id_monster " +
                    "JOIN races ON monsters.id_race = races.id_race " +
                    "JOIN users ON comments.id_user = users.id_user " +
                    "JOIN roles ON users.id_role = roles.id_role";
    public static final String SQL_SELECT_ALL_COMMENTS_BY_MONSTER_ID =
            "SELECT * FROM comments " +
                    "JOIN monsters ON comments.id_monster = monsters.id_monster " +
                    "JOIN races ON monsters.id_race = races.id_race " +
                    "JOIN users ON comments.id_user = users.id_user " +
                    "JOIN roles ON users.id_role = roles.id_role " +
                    "WHERE comments.id_monster = ?";
    public static final String SQL_SELECT_COMMENT_BY_ID =
            "SELECT * FROM comments " +
                    "JOIN monsters ON comments.id_monster = monsters.id_monster " +
                    "JOIN races ON monsters.id_race = races.id_race " +
                    "JOIN users ON comments.id_user = users.id_user " +
                    "JOIN roles ON users.id_role = roles.id_role " +
                    "WHERE id_comment = ?";
    public static final String SQL_INSERT_COMMENT =
            "INSERT INTO comments(date, id_monster, mark, comment, id_user ) VALUES(?,?,?,?,?)";
    public static final String SQL_UPDATE_COMMENT =
            "UPDATE comments " +
                    "SET date = ?, id_monster = ?, mark = ?, comment = ?, id_user = ? " +
                    "WHERE id_comment = ?";
    public static final String SQL_DELETE_COMMENT_BY_ID =
            "DELETE FROM comments where id_comment = ?";
    public static final String SQL_SELECT_ALL_USERS =
            "SELECT * FROM users " +
                    "JOIN roles ON users.id_role = roles.id_role";
    public static final String SQL_SELECT_USER_BY_ID =
            "SELECT * FROM users " +
                    "JOIN roles ON users.id_role = roles.id_role " +
                    "WHERE id_user = ?";
    public static final String SQL_SELECT_USER_BY_LOGIN =
            "SELECT * FROM users " +
                    "JOIN roles ON users.id_role = roles.id_role " +
                    "WHERE login = ?";
    public static final String SQL_INSERT_USER =
            "INSERT INTO users(id_role, rating, login, password, email ) VALUES(?,?,?,?,?)";
    public static final String SQL_UPDATE_USER =
            "UPDATE users " +
                    "SET id_role = ?, rating = ?, login = ?, password = ?, email = ? " +
                    "WHERE id_user = ?";
    public static final String SQL_DELETE_USER_BY_ID =
            "DELETE FROM user where id_user = ?";
    public static final String SQL_SELECT_ALL_ROLES =
            "SELECT * FROM roles";
    public static final String SQL_SELECT_ROLE_BY_ID =
            "SELECT * FROM roles " +
                    "WHERE id_role = ?";
    public static final String SQL_INSERT_ROLE =
            "INSERT INTO roles(role) VALUES(?)";
    public static final String SQL_UPDATE_ROLE =
            "UPDATE races " +
                    "SET role = ? " +
                    "WHERE id_role = ?";
    public static final String SQL_DELETE_ROLE_BY_ID =
            "DELETE FROM roles where id_role = ?";
    public static final String SQL_SELECT_ALL_MONSTERS =
            "SELECT * FROM monsters " +
                    "JOIN races ON monsters.id_race = races.id_race";
    public static final String SQL_SELECT_MONSTER_BY_ID =
            "SELECT * FROM monsters " +
                    "JOIN races ON monsters.id_race = races.id_race " +
                    "WHERE id_monster = ?";
    public static final String SQL_SELECT_MONSTER_BY_NAME =
            "SELECT * FROM monsters " +
                    "JOIN races ON monsters.id_race = races.id_race " +
                    "WHERE name = ?";
    public static final String SQL_INSERT_MONSTER =
            "INSERT INTO monsters(name, id_race, description, average_rating, picture_address) VALUES(?,?,?,?,?)";
    public static final String SQL_UPDATE_MONSTER =
            "UPDATE monsters " +
                    "SET name = ?, id_race = ?, description = ?, average_rating = ?, picture_address = ? " +
                    "WHERE id_monster = ?";
    public static final String SQL_DELETE_MONSTER_BY_ID =
            "DELETE FROM monsters where id_monster = ?";
    public static final String SQL_SELECT_ALL_RACES =
            "SELECT * FROM races";
    public static final String SQL_SELECT_RACE_BY_ID =
            "SELECT * FROM races " +
                    "WHERE id_race = ?";
    public static final String SQL_SELECT_RACE_BY_RACE =
            "SELECT * FROM races " +
                    "WHERE race = ?";
    public static final String SQL_INSERT_RACE =
            "INSERT INTO races(race) VALUES(?)";
    public static final String SQL_UPDATE_RACE =
            "UPDATE races " +
                    "SET race = ? " +
                    "WHERE id_race = ?";
    public static final String SQL_DELETE_RACE_BY_ID =
            "DELETE FROM races where id_race = ?";
}
