package com.senchenko.aliens.dao;

import com.senchenko.aliens.entity.*;
import com.senchenko.aliens.util.SqlQueries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao extends AliensDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_ALL_COMMENTS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Comment comment = new Comment(
                        resultSet.getInt(SqlQueries.ID_COMMENT),
                        resultSet.getDate(SqlQueries.DATE),
                        new Monster(
                                resultSet.getInt(SqlQueries.ID_MONSTER),
                                resultSet.getString(SqlQueries.NAME),
                                new Race(
                                        resultSet.getInt(SqlQueries.ID_RACE),
                                        resultSet.getString(SqlQueries.RACE)),
                                resultSet.getString(SqlQueries.DESCRIPTION),
                                resultSet.getDouble(SqlQueries.AVERAGE_RATING),
                                resultSet.getString(SqlQueries.PICTURE_ADDRESS)
                        ),
                        resultSet.getInt(SqlQueries.MARK),
                        resultSet.getString(SqlQueries.COMMENT),
                        new User(
                                resultSet.getInt(SqlQueries.ID_USER),
                                new Role(
                                        resultSet.getInt(SqlQueries.ID_ROLE),
                                        resultSet.getString(SqlQueries.ROLE)),
                                resultSet.getInt(SqlQueries.RATING),
                                resultSet.getString(SqlQueries.LOGIN),
                                resultSet.getString(SqlQueries.PASSWORD),
                                resultSet.getString(SqlQueries.EMAIL)
                        )
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return comments;
    }

    public List<Comment> findAllByMonsterId(int monsterId) {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_ALL_COMMENTS_BY_MONSTER_ID);
            statement.setInt(1, monsterId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Comment comment = new Comment(
                        resultSet.getInt(SqlQueries.ID_COMMENT),
                        resultSet.getDate(SqlQueries.DATE),
                        new Monster(
                                resultSet.getInt(SqlQueries.ID_MONSTER),
                                resultSet.getString(SqlQueries.NAME),
                                new Race(
                                        resultSet.getInt(SqlQueries.ID_RACE),
                                        resultSet.getString(SqlQueries.RACE)),
                                resultSet.getString(SqlQueries.DESCRIPTION),
                                resultSet.getDouble(SqlQueries.AVERAGE_RATING),
                                resultSet.getString(SqlQueries.PICTURE_ADDRESS)
                        ),
                        resultSet.getInt(SqlQueries.MARK),
                        resultSet.getString(SqlQueries.COMMENT),
                        new User(
                                resultSet.getInt(SqlQueries.ID_USER),
                                new Role(
                                        resultSet.getInt(SqlQueries.ID_ROLE),
                                        resultSet.getString(SqlQueries.ROLE)),
                                resultSet.getInt(SqlQueries.RATING),
                                resultSet.getString(SqlQueries.LOGIN),
                                resultSet.getString(SqlQueries.PASSWORD),
                                resultSet.getString(SqlQueries.EMAIL)
                        )
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return comments;
    }

    @Override
    public Comment findById(int id) {
        Comment comment = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_COMMENT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                comment = new Comment(
                        resultSet.getInt(SqlQueries.ID_COMMENT),
                        resultSet.getDate(SqlQueries.DATE),
                        new Monster(
                                resultSet.getInt(SqlQueries.ID_MONSTER),
                                resultSet.getString(SqlQueries.NAME),
                                new Race(
                                        resultSet.getInt(SqlQueries.ID_RACE),
                                        resultSet.getString(SqlQueries.RACE)),
                                resultSet.getString(SqlQueries.DESCRIPTION),
                                resultSet.getDouble(SqlQueries.AVERAGE_RATING),
                                resultSet.getString(SqlQueries.PICTURE_ADDRESS)
                        ),
                        resultSet.getInt(SqlQueries.MARK),
                        resultSet.getString(SqlQueries.COMMENT),
                        new User(
                                resultSet.getInt(SqlQueries.ID_USER),
                                new Role(
                                        resultSet.getInt(SqlQueries.ID_ROLE),
                                        resultSet.getString(SqlQueries.ROLE)),
                                resultSet.getInt(SqlQueries.RATING),
                                resultSet.getString(SqlQueries.LOGIN),
                                resultSet.getString(SqlQueries.PASSWORD),
                                resultSet.getString(SqlQueries.EMAIL)
                        )
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return comment;
    }

    @Override
    public boolean deleteById(int id) {
        boolean flag = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_DELETE_COMMENT_BY_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return flag;
    }

    @Override
    public boolean delete(Entity entity) {
        int id = entity.getId();
        return deleteById(id);
    }

    @Override
    public boolean create(Entity entity) {
        boolean flag = false;
        PreparedStatement statement = null;
        Comment comment = (Comment) entity;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_INSERT_COMMENT);
            statement.setDate(1, comment.getDate());
            statement.setInt(2, comment.getMonster().getMonsterId());
            statement.setInt(3, comment.getMark());
            statement.setString(4, comment.getComment());
            statement.setInt(5, comment.getUser().getUserId());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return flag;
    }

    @Override
    public Entity update(Entity entity) {
        PreparedStatement statement = null;
        Comment comment = (Comment) entity;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_COMMENT);
            statement.setDate(1, comment.getDate());
            statement.setInt(2, comment.getMonster().getMonsterId());
            statement.setInt(3, comment.getMark());
            statement.setString(4, comment.getComment());
            statement.setInt(5, comment.getUser().getUserId());
            statement.setInt(6, comment.getCommentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return comment;
    }
}
