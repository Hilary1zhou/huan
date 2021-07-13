package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 注册用户
     *
     * @param username
     * @return
     */
    public User FindByUsername(String username);

    /**
     * 保存用户信息
     *
     * @param user
     */
    public void save(User user);

    public User FindByCode(String code);

    void updateStatus(User user);

    User FindByUsernameAndPassword(String username, String password);
}
