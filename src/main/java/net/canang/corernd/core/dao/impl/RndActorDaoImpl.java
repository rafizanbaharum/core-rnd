package net.canang.corernd.core.dao.impl;

/**
 * @author rafizan.baharum
 * @since 7/13/13
 */

import net.canang.corernd.core.dao.RndActorDao;
import net.canang.corernd.core.dao.DaoSupport;
import net.canang.corernd.core.model.*;
import net.canang.corernd.core.model.impl.RndActorImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository("actorDao")
public class RndActorDaoImpl extends DaoSupport<Long, RndActor, RndActorImpl> implements RndActorDao {

    // =============================================================================
    // FINDER METHODS
    // =============================================================================

    @Override
    public RndActor findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (RndActor) session.get(RndActorImpl.class, id);
    }

    @Override
    public RndActor findByIdentityNo(String identityNo) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndActor a where " +
                "a.identityNo = :identityNo " +
                "and a.metadata.state = :state ");
        query.setString("identityNo", identityNo);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return (RndActor) query.uniqueResult();
    }

    @Override
    public RndActor findByNricNo(String nricNo) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndActor a where " +
                "a.nricNo = :nricNo " +
                "and a.metadata.state = :state ");
        query.setString("nricNo", nricNo);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return (RndActor) query.uniqueResult();
    }

    @Override
    public List<RndActor> find(Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndActor a where " +
                "a.metadata.state = :state " +
                "order by a.code");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    @Override
    public List<RndActor> find(String filter, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndActor a where " +
                "(a.code like upper(:filter) " +
                "or upper(a.description) like upper(:filter)) " +
                "and a.metadata.state = :state");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    @Override
    public List<RndActor> find(RndActorType type, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndActor a where " +
                "a.actorType = :actorType " +
                "and a.metadata.state = :state");
        query.setInteger("actorType", type.ordinal());
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    @Override
    public List<RndActor> find(RndActorType type, String filter, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndActor a where " +
                "(a.identityNo like upper(:filter) " +
                "or upper(a.name) like upper(:filter)) " +
                "and a.actorType = :actorType " +
                "and a.metadata.state = :state");
        query.setInteger("actorType", type.ordinal());
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    @Override
    public Integer count() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(a) from RndActor a where " +
                "a.metadata.state = :state");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Integer count(String filter) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(a) from RndActor a where " +
                "(a.name like upper(:filter) " +
                "or upper(a.identityNo) like upper(:filter)) " +
                "and a.metadata.state = :state");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Integer count(RndActorType type) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(a) from RndActor a where " +
                "a.actorType = :actorType " +
                "and a.metadata.state = :state");
        query.setInteger("actorType", type.ordinal());
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }

    // =============================================================================
    // CRUD METHODS
    // =============================================================================

}
