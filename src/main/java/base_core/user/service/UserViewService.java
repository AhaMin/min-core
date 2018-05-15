package base_core.user.service;

import base_core.user.model.User;
import base_core.user.view.UserView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ewang on 2018/5/15.
 */
@Service
public class UserViewService {

    public List<UserView> buildView(List<User> userList) {
        List<UserView> userViewList = new ArrayList<>();
        for (User u : userList) {
            userViewList.add(new UserView(u.getId(), u.getUsername(), u.getAccount()));
        }
        return userViewList;
    }
}
