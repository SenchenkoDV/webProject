package com.senchenko.aliens.dao;

public enum SingletonDaoProvider {
    INSTANCE;
    private CommentDao commentDao;
    private MonsterDao monsterDao;
    private RaceDao raceDao;
    private RoleDao roleDao;
    private UserDao userDao;

    public CommentDao getCommentDao() {
        return commentDao != null ? commentDao : (commentDao = new CommentDao());
    }

    public MonsterDao getMonsterDao() {
        return monsterDao != null ? monsterDao : (monsterDao = new MonsterDao());
    }

    public RaceDao getRaceDao() {
        return raceDao != null ? raceDao : (raceDao = new RaceDao());
    }

    public RoleDao getRoleDao() {
        return roleDao != null ? roleDao : (roleDao = new RoleDao());
    }

    public UserDao getUserDao() {
        return userDao != null ? userDao : (userDao = new UserDao());
    }
}
