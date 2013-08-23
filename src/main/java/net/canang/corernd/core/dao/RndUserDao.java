package net.canang.corernd.core.dao;

import net.canang.corernd.core.model.RndActor;
import net.canang.corernd.core.model.RndGroup;
import net.canang.corernd.core.model.RndUser;

import java.util.List;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
public interface RndUserDao {

    RndUser findById(Long id);

    RndUser findByUsername(String username);

    RndUser findByActor(RndActor actor);

    RndUser findByRealName(String realname);

    List<RndUser> find(Integer offset, Integer limit);

    List<RndUser> find(String filter, Integer offset, Integer limit);

    List<RndGroup> findUserGroups(RndUser user);

    Integer count();

    Integer count(String filter);

    boolean isExists(String username);

    // cruds

    void save(RndUser suser, RndUser user);

    void update(RndUser suser, RndUser user);

    void deactivate(RndUser suser, RndUser user);

    void remove(RndUser suser, RndUser user);


}
