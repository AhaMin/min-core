package base_core.user.service;

import base_core.user.model.User;
import base_core.user.model.UserAddress;
import base_core.user.view.UserAddressView;
import base_core.user.view.UserView;

import java.util.List;

/**
 * created by ewang on 2018/6/14.
 */
public interface UserViewService {

    List<UserView> buildUserView(List<User> userList);

    List<UserView> buildUserViewById(List<Long> userIds);

    List<UserAddressView> buildAddressView(List<UserAddress> userAddressList);

    List<UserAddressView> buildAddressViewById(List<Long> addressIdList);
}
