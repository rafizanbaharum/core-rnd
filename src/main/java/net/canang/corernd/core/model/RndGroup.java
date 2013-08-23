package net.canang.corernd.core.model;

import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndGroup extends RndPrincipal {

    Set<RndGroupMember> getMembers();

    void setMembers(Set<RndGroupMember> members);

}
