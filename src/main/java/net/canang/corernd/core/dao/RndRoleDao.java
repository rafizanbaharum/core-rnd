package net.canang.corernd.core.dao;

import net.canang.corernd.core.model.RndPrincipal;
import net.canang.corernd.core.model.RndRoleType;
import net.canang.corernd.core.model.RndUser;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
public interface RndRoleDao {

    void grant(RndPrincipal principal, RndRoleType roleType, RndUser user);

    void grant(RndPrincipal principal, RndRoleType[] roleTypes, RndUser user);

    void revoke(RndPrincipal principal, RndRoleType roleType, RndUser user);

    void revoke(RndPrincipal principal, RndRoleType[] roleTypes, RndUser user);

    void revokeAll(RndPrincipal principal, RndUser user);

    void overwrite(RndPrincipal principal, RndRoleType roleType, RndUser user);

    void overwrite(RndPrincipal principal, RndRoleType[] roleTypes, RndUser user);

    void update(RndPrincipal principal, RndRoleType[] roleTypes, RndUser user);

}
