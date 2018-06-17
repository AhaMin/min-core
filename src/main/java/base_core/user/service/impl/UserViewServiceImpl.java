package base_core.user.service.impl;

import base_core.image.service.ImageViewService;
import base_core.image.view.ImageView;
import base_core.user.dao.UserAddressDAO;
import base_core.user.dao.UserDAO;
import base_core.user.model.User;
import base_core.user.model.UserAddress;
import base_core.user.service.UserViewService;
import base_core.user.view.UserAddressView;
import base_core.user.view.UserView;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created by ewang on 2018/5/15.
 */
@Service
public class UserViewServiceImpl implements UserViewService {

    @Autowired
    private ImageViewService imageViewService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAddressDAO userAddressDAO;

    public List<UserView> buildUserView(List<User> userList) {
        List<UserView> userViewList = new ArrayList<>();
        for (User u : userList) {
            List<ImageView> imageViewList = imageViewService.buildViewById(Collections.singletonList(u.getAvatarId()));
            userViewList.add(new UserView(u.getId(),
                    CollectionUtils.isEmpty(imageViewList) ? null : imageViewList.get(0),
                    u.getUsername(), u.getAccount()));
        }
        return userViewList;
    }

    public List<UserView> buildUserViewById(List<Long> userIds) {
        List<UserView> userViewList = new ArrayList<>();
        for (Long id : userIds) {
            if (id == null) {
                continue;
            }
            User u = userDAO.getById(id);
            if (u == null) {
                continue;
            }
            List<ImageView> imageViewList = imageViewService.buildViewById(Collections.singletonList(u.getAvatarId()));
            userViewList.add(new UserView(u.getId(),
                    CollectionUtils.isEmpty(imageViewList) ? null : imageViewList.get(0),
                    u.getUsername(), u.getAccount()));
        }
        return userViewList;
    }

    @Override
    public List<UserAddressView> buildAddressView(List<UserAddress> userAddressList) {
        List<UserAddressView> userAddressViewList = new ArrayList<>();
        for (UserAddress address : userAddressList) {
            List<UserView> userViewList = buildUserViewById(Collections.singletonList(address.getUserId()));
            userAddressViewList.add(new UserAddressView(address.getId(),
                    CollectionUtils.isEmpty(userViewList) ? null : userViewList.get(0), address.getProvince(), address.getCity(),
                    address.getCounty(), address.getStreet(), address.getReceiverName(), address.getReceiverPhone()));
        }
        return userAddressViewList;
    }

    @Override
    public List<UserAddressView> buildAddressViewById(List<Long> addressIdList) {
        List<UserAddressView> userAddressViewList = new ArrayList<>();
        for (Long id : addressIdList) {
            if (id == null) {
                continue;
            }
            UserAddress address = userAddressDAO.getById(id);
            if (address == null) {
                continue;
            }
            List<UserView> userViewList = buildUserViewById(Collections.singletonList(address.getUserId()));
            userAddressViewList.add(new UserAddressView(address.getId(),
                    CollectionUtils.isEmpty(userViewList) ? null : userViewList.get(0), address.getProvince(), address.getCity(),
                    address.getCounty(), address.getStreet(), address.getReceiverName(), address.getReceiverPhone()));
        }
        return userAddressViewList;
    }


}
