package net.canang.corernd.core.dao.impl;

import net.canang.corernd.core.LockedGroupException;
import net.canang.corernd.core.RecursiveGroupException;
import net.canang.corernd.core.dao.RndGroupDao;
import net.canang.corernd.core.dao.DaoSupport;
import net.canang.corernd.core.model.*;
import net.canang.corernd.core.model.impl.RndGroupImpl;
import net.canang.corernd.core.model.impl.RndGroupMemberImpl;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
@Repository("groupDao")
public class RndGroupDaoImpl extends DaoSupport<Long, RndGroup, RndGroupImpl> implements RndGroupDao {

    private static final Logger log = Logger.getLogger(RndGroupDaoImpl.class);
    // =============================================================================
    // FINDER METHODS
    // =============================================================================

    @Override
    public RndGroup findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (RndGroup) session.get(RndGroupImpl.class, id);
    }

    @Override
    public List<RndGroup> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select g from RndGroup g order by g.name");
        return (List<RndGroup>) query.list();
    }

    @Override
    public List<String> findAllGroupName() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select g.name from RndGroup g");
        return (List<String>) query.list();
    }

    @Override
    public RndGroup findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select g from RndGroup g where g.name = :name");
        query.setString("name", name);
        return (RndGroup) query.uniqueResult();
    }

    @Override
    public List<RndPrincipal> findMembers(RndGroup group) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select gm.principal from RndGroupMember gm where " +
                "gm.group = :group " +
                "order by gm.principal.name");
        query.setEntity("group", group);
        return (List<RndPrincipal>) query.list();
    }

    /**
     * XXX: ClassCastException issues
     * XXX: select gm.principal wil get you abstract RndPrincipal
     * XXX: not extension classes
     *
     * @param group
     * @param type
     * @return
     */
    @Override
    public List<RndPrincipal> findMembers(RndGroup group, RndPrincipalType type) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        String selectGroup = "select g from RndGroup g where " +
                "id in (select gm.principal.id from RndGroupMember gm where " +
                "gm.group = :group " +
                "and gm.principal.principalType = :type )" +
                "order by g.name";
        String selectUser = "select u from RndUser u where " +
                "id in (select gm.principal.id from RndGroupMember gm where " +
                "gm.group = :group " +
                "and gm.principal.principalType = :type )" +
                "order by u.name";
        switch (type) {
            case USER:
                query = session.createQuery(selectUser);
                break;
            case GROUP:
                query = session.createQuery(selectGroup);
                break;
        }
        query.setEntity("group", group);
        query.setInteger("type", type.ordinal());
        return (List<RndPrincipal>) query.list();
    }

    @Override
    public List<RndGroup> findPrincipalGroups(RndPrincipal principal) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select gm.group from RndGroupMember gm inner join gm.principal where " +
                "gm.principal = :principal");
        query.setEntity("principal", principal);
        return (List<RndGroup>) query.list();
    }

    @Override
    public List<RndPrincipal> findMembers(RndGroup group, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select gm.principal from RndGroupMember gm where " +
                "gm.group = :group " +
                "order by gm.principal.name");
        query.setEntity("group", group);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<RndPrincipal>) query.list();
    }

    @Override
    public List<RndGroup> find(Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select g from RndGroup g order by g.name");
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<RndGroup>) query.list();
    }


    @Override
    public List<RndGroup> find(String filter, Integer offset, Integer limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select distinct g from RndGroup g where " +
                "g.name like upper(:filter) " +
                "and g.metadata.state = :state");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (List<RndGroup>) query.list();
    }

    @Override
    public List<RndGroup> findParentGroup(RndGroup group) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select g from RndGroup g inner join g.groupMembers m where m.principal = :group");
        query.setEntity("group", group);
        return (List<RndGroup>) query.list();
    }

    @Override
    public Set<RndGroup> findHierarchicalGroupAsNative(RndPrincipal principal) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT /*+ USE_NL(U) IGNORE_OPTIM_EMBEDDED_HINTS */ CONNECT_BY_ROOT p.id parent " +
                "FROM fs_principal p, fs_group g, fs_group_member m, fs_principal u " +
                "WHERE p.id = g.id " +
                "AND m.group_id = g.id " +
                "AND m.principal_id = u.id " +
                "and u.name = '" + principal.getName() + "' " +
                "connect by prior m.principal_id = m.group_id";
        sqlQuery = "SELECT DISTINCT parent FROM ( " + sqlQuery + ")";
        SQLQuery query = session.createSQLQuery(sqlQuery);
        query.addScalar("parent", LongType.INSTANCE);
        List<Long> results = (List<Long>) query.list();
        Set<RndGroup> groups = new HashSet<RndGroup>();
        for (Long result : results) {
            groups.add(findById(result));
        }
        return groups;
    }

    @Override
    public List<String> findHierarchicalGroupRoleAsNative(RndPrincipal principal) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT /*+ USE_HASH(U,M,G) ORDERED IGNORE_OPTIM_EMBEDDED_HINTS */ 'X' \n" +
                "FROM fs_principal p, " +
                "fs_group g, " +
                "fs_group_member m, " +
                "fs_principal u " +
                "WHERE p.id = g.id " +
                "AND m.group_id = g.id " +
                "AND m.principal_id = u.id " +
                "and u.name = '" + principal.getName() + "' " +
                "AND CONNECT_BY_ROOT p.id = FS_GROUP_ROLE.group_id \n" +
                "connect by prior m.principal_id = m.group_id";
        sqlQuery = "select role from fs_group_role where exists ( " + sqlQuery + " )";
        SQLQuery query = session.createSQLQuery(sqlQuery);
        query.addScalar("role", StringType.INSTANCE);
        return (List<String>) query.list();
    }

    @Override
    public Set<RndGroup> findHierarchicalGroupAsView(RndPrincipal principal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer count() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(g) from RndGroup g where " +
                "g.metadata.state = :state");
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Integer count(String filter) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(g) from RndGroup g where " +
                "g.name like upper(:filter) " +
                "and g.metadata.state = :state");
        query.setString("filter", WILDCARD + filter + WILDCARD);
        query.setInteger("state", RndMetaState.ACTIVE.ordinal());
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public boolean isMemberOf(RndGroup group, RndPrincipal principal) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(g) from RndGroupMember g where " +
                "g.group = :group " +
                "and g.principal = :principal");
        query.setEntity("group", group);
        query.setEntity("principal", principal);
        return ((Long) query.uniqueResult()).intValue() >= 1;
    }

    @Override
    public RndGroupMember findGroupMember(RndGroup group, RndPrincipal principal) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select g from RndGroupMember g where " +
                "g.group = :group " +
                "and g.principal = :principal");
        query.setEntity("group", group);
        query.setEntity("principal", principal);
        return (RndGroupMember) query.uniqueResult();
    }

// =============================================================================
    // CRUD METHODS
    // =============================================================================

    @Override
    public void addMember(RndGroup group, RndPrincipal member, RndUser user) throws RecursiveGroupException, LockedGroupException {

        // validate
        Validate.notNull(group, "Group should not be null");
        Validate.notNull(member, "Group member should not be null");

        // check locked group
        if (group.isLocked()) {
            log.error("Group is locked");
            throw new LockedGroupException("Locked group");
        }

        // check recursive add
//        if (member instanceof RndGroup) {
//            if (isInRecursive(group, (RndGroup) member))
//                throw new RecursiveGroupException("Recursive user group detected");
//        }

        // session
        Session session = sessionFactory.getCurrentSession();

        // populate
        RndGroupMember groupMember = new RndGroupMemberImpl();
        groupMember.setGroup(group);
        groupMember.setMember(member);

        // prepare metadata
        RndMetadata metadata = new RndMetadata();
        metadata.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        metadata.setCreator(user.getId());
        groupMember.setMetadata(metadata);
        session.save(groupMember);
    }

    @Override
    public void addMembers(RndGroup group, List<RndPrincipal> principals, RndUser user) throws RecursiveGroupException, LockedGroupException {
        List<RndPrincipal> principalGroups = findMembers(group);
        List<RndPrincipal> newPrincipals = new ArrayList<RndPrincipal>();

        for (RndPrincipal principal : principals) {
            newPrincipals.add(principal);
        }

        for (RndPrincipal principalGroup : principalGroups) {
            if (!newPrincipals.contains(principalGroup)) {
                removeMember(group, principalGroup);
            }
        }

        for (RndPrincipal newGroup : newPrincipals) {
            if (!principalGroups.contains(newGroup)) {
                addMember(group, newGroup, user);
            }
        }
    }

    @Override
    public void removeMember(RndGroup group, RndPrincipal principal) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select g from RndGroupMember g where g.group = :group and g.principal = :principal");
        query.setEntity("group", group);
        query.setEntity("principal", principal);
        RndGroupMember groupMember = (RndGroupMember) query.uniqueResult();
        session.delete(groupMember);
    }

    private boolean isInRecursive(RndGroup parent, RndGroup child) {
        Set<RndGroup> hierarchicalGroup = findHierarchicalGroupAsNative(parent);
        return !hierarchicalGroup.add(child);
    }

}
