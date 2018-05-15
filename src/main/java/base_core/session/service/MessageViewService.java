package base_core.session.service;

import base_core.session.model.Message;
import base_core.session.view.MessageView;
import base_core.user.dao.UserDAO;
import base_core.user.model.User;
import base_core.user.service.UserViewService;
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
public class MessageViewService {

    @Autowired
    private UserViewService userViewService;

    @Autowired
    private UserDAO userDAO;

    public List<MessageView> buildView(List<Message> messageList) {
        List<MessageView> messageViewList = new ArrayList<>();
        for (Message m : messageList) {
            User user = userDAO.getById(m.getUserId());
            User toUser = userDAO.getById(m.getToUserId());
            UserView userView = userViewService.buildView(Collections.singletonList(user)).get(0);
            UserView toUserView = userViewService.buildView(Collections.singletonList(toUser)).get(0);
            messageViewList.add(new MessageView(userView, toUserView, m.getContent(), m.getCreateTime()));
        }
        return messageViewList;
    }

}
