package net.canang.corernd.core.model;

import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndPrincipal extends RndMetaObject {

    String getName();

    void setName(String name);

    boolean isLocked();

    void setLocked(boolean locked);

    RndPrincipalType getPrincipalType();

    void setPrincipalType(RndPrincipalType principalType);

    Set<RndPrincipalRole> getRoles();

    void setRoles(Set<RndPrincipalRole> roles);

}
