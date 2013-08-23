package net.canang.corernd.core.dao.impl;

import net.canang.corernd.core.dao.RndActorDao;
import net.canang.corernd.core.dao.RndInstructorDao;
import net.canang.corernd.core.dao.DaoSupport;
import net.canang.corernd.core.model.RndMetaState;
import net.canang.corernd.core.model.RndInstructor;
import net.canang.corernd.core.model.impl.RndInstructorImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rafizan.baharum
 * @since 7/20/13
 */
@Repository("instructorDao")
public class RndInstructorDaoImpl extends DaoSupport<Long, RndInstructor, RndInstructorImpl> implements RndInstructorDao {

    @Autowired
    private RndActorDao actorDao;

    @Override
    public RndInstructor findById(Long id) {
        return (RndInstructor) actorDao.findById(id);
    }

    @Override
    public RndInstructor findByMatrixNo(String matrixNo) {
        return (RndInstructor) actorDao.findByIdentityNo(matrixNo);
    }

    @Override
    public List<RndInstructor> find(Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndInstructor a where " +
                "a.metadata.state = :state");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    @Override
    public List<RndInstructor> find(String filter, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndInstructor a where " +
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
    public Integer count() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(a) from RndInstructor a where " +
                "a.metadata.state = :state");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Integer count(String filter) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(a) from RndInstructor a where " +
                "(a.name like upper(:filter) " +
                "or upper(a.identityNo) like upper(:filter)) " +
                "and a.metadata.state = :state");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }
}
