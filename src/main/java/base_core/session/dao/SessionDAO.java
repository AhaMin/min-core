package base_core.session.dao;


import base_core.session.model.Session;

import java.util.Date;
import java.util.List;

/**
 * created by ewang on 2018/4/18.
 */
public interface SessionDAO {

    Session getById(long id);

    Session getByUserAndToUser(long userId, long toUserId);

    long insert(long userId, long toUserId);

    int updateUnread(long sessionId, int oldData, int newData);

    int updateTime(long sessionId, Date updateTime);

    List<Session> findLatestSessionByUser(long userId, int limit);
}
