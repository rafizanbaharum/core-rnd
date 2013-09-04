package net.canang.corernd.core.dao;

import net.canang.corernd.core.LockedGroupException;
import net.canang.corernd.core.RecursiveGroupException;
import net.canang.corernd.core.model.*;

import java.util.List;
import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/11/13
 */
public interface RndGroupDao {

    // finders

    RndGroup findById(Long id);

    RndGroup findByName(String name);

    List<RndGroup> findAll();

    List<String> findAllGroupName();

    List<RndPrincipal> findMembers(RndGroup group);

    List<RndPrincipal> findMembers(RndGroup group, RndPrincipalType type);

    List<RndGroup> findPrincipalGroups(RndPrincipal principal);

    List<RndPrincipal> findMembers(RndGroup group, Integer offset, Integer limit);

    List<RndGroup> find(Integer offset, Integer limit);

    List<RndGroup> find(String filter, Integer offset, Integer limit);

    List<RndGroup> findParentGroup(RndGroup group);

    Set<RndGroup> findHierarchicalGroupAsNative(RndPrincipal principal);

    RndGroupMember findGroupMember(RndGroup group, RndPrincipal principal);

    Integer count();

    Integer count(String filter);

    boolean isMemberOf(RndGroup group, RndPrincipal principal);

    void save(RndGroup group, RndUser user);

    void update(RndGroup group, RndUser user);

    void deactivate(RndGroup group, RndUser user);

    void remove(RndGroup group, RndUser user) throws LockedGroupException;

    void addMember(RndGroup group, RndPrincipal principal, RndUser user) throws RecursiveGroupException, LockedGroupException;

    void addMembers(RndGroup group, List<RndPrincipal> principals, RndUser user) throws RecursiveGroupException, LockedGroupException;

    void removeMember(RndGroup group, RndPrincipal principal) throws LockedGroupException;

}
