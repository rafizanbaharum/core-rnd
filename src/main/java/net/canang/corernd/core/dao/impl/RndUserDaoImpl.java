package net.canang.corernd.core.dao.impl;

import net.canang.corernd.core.dao.RndPrincipalDao;
import net.canang.corernd.core.dao.RndUserDao;
import net.canang.corernd.core.dao.DaoSupport;
import net.canang.corernd.core.model.*;
import net.canang.corernd.core.model.RndUser;
import net.canang.corernd.core.model.RndGroup;
import net.canang.corernd.core.model.impl.RndUserImpl;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
@Repository("userDao")
public class RndUserDaoImpl extends DaoSupport<Long, RndUser, RndUserImpl> implements RndUserDao {

    private static final Logger log = Logger.getLogger(RndUserDaoImpl.class);

    @Autowired
    private RndPrincipalDao principalDao;


    // =============================================================================
    // FINDER METHODS
    // =============================================================================

    @Override
    public List<RndGroup> findUserGroups(RndUser user) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select r from RndGroup r inner join r.groupMembers m where m.principal = :user");
        query.setEntity("user", user);
        return (List<RndGroup>) query.list();
    }

    @Override
    public boolean isExists(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from RndUser u where " +
                "u.name = :username");
        query.setString("username", username);
        return 0 < ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public RndUser findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (RndUser) session.get(RndUserImpl.class, id);
    }

    @Override
    public RndUser findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select u from RndUser u where u.name = :username and u.metadata.state = :state");
        query.setString("username", username);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return (RndUser) query.uniqueResult();
    }

    @Override
    public RndUser findByActor(RndActor actor) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select u from RndUser u where u.actor  = :actor");
        query.setEntity("actor", actor);
        return (RndUser) query.uniqueResult();
    }

    public RndUser findByRealName(String realname) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select u from RndUser u where u.realname = :realname");
        query.setString("realname", realname);
        return (RndUser) query.uniqueResult();
    }

    @Override
    public List<RndUser> find(Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s from RndUser s");
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<RndUser>) query.list();
    }

    @Override
    public List<RndUser> find(String filter, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s from RndUser s where (s.name like :filter or s.realname like upper(:filter))");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<RndUser>) query.list();
    }

    @Override
    public Integer count() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(u) from RndUser u");
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Integer count(String filter) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(s) from RndUser s where s.name like :filter " +
                "or s.realname like upper(:filter)");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        return ((Long) query.uniqueResult()).intValue();
    }
}
