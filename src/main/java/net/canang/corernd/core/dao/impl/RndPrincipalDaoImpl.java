package net.canang.corernd.core.dao.impl;

import net.canang.corernd.core.RecursiveGroupException;
import net.canang.corernd.core.dao.RndPrincipalDao;
import net.canang.corernd.core.dao.RndRoleDao;
import net.canang.corernd.core.dao.DaoSupport;
import net.canang.corernd.core.model.RndMetaState;
import net.canang.corernd.core.model.RndPrincipal;
import net.canang.corernd.core.model.RndPrincipalType;
import net.canang.corernd.core.model.impl.RndPrincipalImpl;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
@Repository("principalDao")
public class RndPrincipalDaoImpl extends DaoSupport<Long, RndPrincipal, RndPrincipalImpl> implements RndPrincipalDao {

    // logger
    private static final Logger log = Logger.getLogger(RndPrincipalDaoImpl.class);

    private RndRoleDao roleDao;

    private boolean allowRecursiveGroup = false;

    private Set<String> groupName = null;

    //principal

    @Override
    public List<RndPrincipal> findAllPrincipals() {
        Session session = sessionFactory.getCurrentSession();
        List<RndPrincipal> results = new ArrayList<RndPrincipal>();
        Query query = session.createQuery("select p from RndUser p where p.metadata.state = :state order by p.name");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        results.addAll((List<RndPrincipal>) query.list());

        Query queryGroup = session.createQuery("select p from RndGroup p where p.metadata.state = :state order by p.name ");
        queryGroup.setInteger("state", RndMetaState.ACTIVE.ordinal());
        results.addAll((List<RndPrincipal>) queryGroup.list());

        return results;
    }

    @Override
    public List<RndPrincipal> findPrincipals(String filter) {
        Session session = sessionFactory.getCurrentSession();
        List<RndPrincipal> results = new ArrayList<RndPrincipal>();
        Query query = session.createQuery("select p from RndPrincipal p where p.metadata.state = :state and p.name like :filter order by p.name");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setString("filter", WILDCARD + filter + WILDCARD);
        results.addAll((List<RndPrincipal>) query.list());
        return results;
    }

    @Override
    public List<RndPrincipal> findPrincipals(String filter, RndPrincipalType type) {
        Session session = sessionFactory.getCurrentSession();
        List<RndPrincipal> results = new ArrayList<RndPrincipal>();
        Query query = session.createQuery("select p from RndPrincipal p where " +
                "p.metadata.state = :state " +
                "and p.name like :filter " +
                "and p.principalType = :principalType " +
                "order by p.name");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setInteger("principalType", type.ordinal());
        results.addAll((List<RndPrincipal>) query.list());
        return results;
    }

    /**
     * find principal
     *
     * @param offset offset
     * @param limit  limit
     * @return list of principals
     */
    @Override
    public List<RndPrincipal> findPrincipals(Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select p from RndPrincipal p");
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<RndPrincipal>) query.list();
    }

    public Set<GrantedAuthority> loadEffectiveAuthorities(RndPrincipal principal) throws RecursiveGroupException {
        return new HashSet<GrantedAuthority>(); // todo
    }

    @Override
    public RndPrincipal findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        log.debug("Searching for principal " + name);
        Query query = session.createQuery("select p from RndPrincipal p where p.name = :name");
        query.setString("name", name);
        return (RndPrincipal) query.uniqueResult();
    }
}
