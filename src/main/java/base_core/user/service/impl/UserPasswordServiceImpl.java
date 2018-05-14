package base_core.user.service.impl;

import base_core.user.dao.UserDAO;
import base_core.user.dao.UserPasswordDAO;
import base_core.user.model.UserPassword;
import base_core.user.service.UserPasswordService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * created by ewang on 2018/5/14.
 */
@Service
public class UserPasswordServiceImpl implements UserPasswordService {

    @Autowired
    private UserPasswordDAO userPasswordDAO;

    @Override
    public boolean verifyPassword(long userId, String password) {
        UserPassword userPassword = userPasswordDAO.getByUser(userId);
        if (userPassword.getPassword().equals(encodePwd(password, userPassword.getUpdateTime()))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createUserPassword(long userId, String password) {
        long pwdUpdateTimeMills = System.currentTimeMillis();
        userPasswordDAO.insertOrUpdate(userId, encodePwd(password, pwdUpdateTimeMills), pwdUpdateTimeMills);
    }

    private String encodePwd(String pwd, long updateTime) {
        return DigestUtils.md5Hex(pwd + updateTime);
    }
}
