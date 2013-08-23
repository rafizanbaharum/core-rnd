package net.canang.corernd.core.dao.impl;

import net.canang.corernd.core.dao.RndActorDao;
import net.canang.corernd.core.dao.RndStudentDao;
import net.canang.corernd.core.dao.DaoSupport;
import net.canang.corernd.core.model.RndMetaState;
import net.canang.corernd.core.model.RndStudent;
import net.canang.corernd.core.model.impl.RndStudentImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rafizan.baharum
 * @since 7/20/13
 */
@Repository("studentDao")
public class RndStudentDaoImpl extends DaoSupport<Long, RndStudent, RndStudentImpl> implements RndStudentDao {

    @Autowired
    private RndActorDao actorDao;

    @Override
    public RndStudent findById(Long id) {
        return (RndStudent) actorDao.findById(id);
    }

    @Override
    public RndStudent findByMatrixNo(String matrixNo) {
        return (RndStudent) actorDao.findByIdentityNo(matrixNo);
    }

    @Override
    public List<RndStudent> find(Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndStudent a where " +
                "a.metadata.state = :state");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    @Override
    public List<RndStudent> find(String filter, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select a from RndStudent a where " +
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
        Query query = session.createQuery("select count(a) from RndStudent a where " +
                "a.metadata.state = :state");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Integer count(String filter) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(a) from RndStudent a where " +
                "(a.name like upper(:filter) " +
                "or upper(a.identityNo) like upper(:filter)) " +
                "and a.metadata.state = :state");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }
}
