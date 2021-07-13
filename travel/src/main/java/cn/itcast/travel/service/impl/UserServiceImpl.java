package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User login(User user) {
        return userDao.FindByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    /**
     * 激活用户
     *
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        User user = userDao.FindByCode(code);
        if (user != null) {
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //     根据用户名查询用户对象
        User u1 = userDao.FindByUsername(user.getUsername());
        if (u1 != null) {
            //            用户名存在，注册失败
            return false;
        } else {
            //            保存用户信息
            //         设置激活码，唯一字符串
            user.setCode(UuidUtil.getUuid());
            //            设置激活状态
            user.setStatus("N");
            userDao.save(user);
            //            激活邮件，发送正文
            String content =
                    "<a href= 'http://localhost:8080/travel/user/active?code="
                            + user.getCode()
                            + "'>点击激活【黑马旅游网】</a>";
            MailUtils.sendMail(user.getEmail(), content, "激活邮件");
            return true;
        }
    }
}
