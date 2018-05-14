package base_core.user.service;

/**
 * created by ewang on 2018/5/14.
 */
public interface UserPasswordService {

    boolean verifyPassword(long userId, String password);

    void createUserPassword(long userId, String password);
}
