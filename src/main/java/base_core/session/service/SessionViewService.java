package base_core.session.service;

import base_core.session.dao.MessageDAO;
import base_core.session.model.Message;
import base_core.session.model.Session;
import base_core.session.view.SessionView;
import base_core.user.dao.UserDAO;
import base_core.user.model.User;
import base_core.user.service.UserViewService;
import base_core.user.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * created by ewang on 2018/5/15.
 */
@Service
public class SessionViewService {

    @Autowired
    private UserViewService userViewService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MessageDAO messageDAO;

    public List<SessionView> buildView(Collection<Session> sessionList) {
        List<SessionView> sessionViewList = new ArrayList<>();
        for (Session s : sessionList) {
            User toUser = userDAO.getById(s.getToUserId());
            UserView toUserView = userViewService.buildView(Collections.singletonList(toUser)).get(0);
            Message latestMessage = messageDAO.getLatestBySession(s.getId());
            SessionView sessionView = new SessionView(toUserView, latestMessage.getContent(), s.getUpdateTime(), s.getUnread());
            sessionViewList.add(sessionView);
        }
        return sessionViewList;
    }
}
