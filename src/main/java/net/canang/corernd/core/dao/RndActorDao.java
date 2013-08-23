package net.canang.corernd.core.dao;

import net.canang.corernd.core.model.RndActor;
import net.canang.corernd.core.model.RndActorType;
import net.canang.corernd.core.model.RndUser;

import java.util.List;

/**
 * @author rafizan.baharum
 * @since 7/20/13
 */
public interface RndActorDao {

    RndActor findById(Long id);

    RndActor findByIdentityNo(String identityNo);

    RndActor findByNricNo(String nricNo);

    List<RndActor> find(Integer offset, Integer limit);

    List<RndActor> find(String filter, Integer offset, Integer limit);

    List<RndActor> find(RndActorType type, Integer offset, Integer limit);

    List<RndActor> find(RndActorType type, String filter, Integer offset, Integer limit);

    Integer count();

    Integer count(String filter);

    Integer count(RndActorType type);

    void save(RndActor actor, RndUser user);

    void update(RndActor actor, RndUser user);

    void deactivate(RndActor actor, RndUser user);
}
