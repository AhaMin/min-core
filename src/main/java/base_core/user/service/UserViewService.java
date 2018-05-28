package base_core.user.service;

import base_core.image.dao.ImageDAO;
import base_core.image.service.ImageViewService;
import base_core.image.view.ImageView;
import base_core.user.dao.UserDAO;
import base_core.user.model.User;
import base_core.user.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created by ewang on 2018/5/15.
 */
@Service
public class UserViewService {

    @Autowired
    private ImageViewService imageViewService;

    @Autowired
    private UserDAO userDAO;

    public List<UserView> buildView(List<User> userList) {
        List<UserView> userViewList = new ArrayList<>();
        for (User u : userList) {
            List<ImageView> imageViewList = imageViewService.buildViewById(Collections.singletonList(u.getAvatarId()));
            userViewList.add(new UserView(u.getId(),
                    imageViewList.isEmpty() ? null : imageViewList.get(0),
                    u.getUsername(), u.getAccount()));
        }
        return userViewList;
    }

    public List<UserView> buildViewById(List<Long> userIds) {
        List<UserView> userViewList = new ArrayList<>();
        for (Long id : userIds) {
            if (id == null) {
                continue;
            }
            User u = userDAO.getById(id);
            List<ImageView> imageViewList = imageViewService.buildViewById(Collections.singletonList(u.getAvatarId()));
            userViewList.add(new UserView(u.getId(),
                    imageViewList.isEmpty() ? null : imageViewList.get(0),
                    u.getUsername(), u.getAccount()));
        }
        return userViewList;
    }
}
