package com.senchenko.aliens.util;

public class SqlQueries {
    public static final String SQL_SELECT_ALL_COMMENTS =
            "SELECT * FROM comments " +
                    "JOIN monsters ON comments.id_monster = monsters.id_monster " +
                    "JOIN races ON monsters.id_race = races.id_race " +
                    "JOIN users ON comments.id_user = users.id_user " +
                    "JOIN roles ON users.id_role = roles.id_role";
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
    public static final String SQL_INSERT_MONSTER =
            "INSERT INTO monsters(name, id_race, description, average_rating) VALUES(?,?,?,?)";
    public static final String SQL_UPDATE_MONSTER =
            "UPDATE monsters " +
                    "SET name = ?, id_race = ?, description = ?, average_rating = ? " +
                    "WHERE id_monster = ?";
    public static final String SQL_DELETE_MONSTER_BY_ID =
            "DELETE FROM monsters where id_monster = ?";
    public static final String SQL_SELECT_ALL_RACES =
            "SELECT * FROM races";
    public static final String SQL_SELECT_RACE_BY_ID =
            "SELECT * FROM races " +
                    "WHERE id_race = ?";
    public static final String SQL_INSERT_RACE =
            "INSERT INTO races(race) VALUES(?)";
    public static final String SQL_UPDATE_RACE =
            "UPDATE races " +
                    "SET race = ? " +
                    "WHERE id_race = ?";
    public static final String SQL_DELETE_RACE_BY_ID =
            "DELETE FROM races where id_race = ?";

}
