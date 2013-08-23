package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndGroup;
import net.canang.corernd.core.model.RndGroupMember;
import net.canang.corernd.core.model.RndPrincipalType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
@Table(name = "RND_GROUP")
@Entity(name = "RndGroup")
public class RndGroupImpl extends RndPrincipalImpl implements RndGroup {

    @OneToMany(targetEntity = RndGroupMemberImpl.class, mappedBy = "group")
    Set<RndGroupMember> members;

    public RndGroupImpl() {
        setPrincipalType(RndPrincipalType.GROUP);
    }

    public Set<RndGroupMember> getMembers() {
        return members;
    }

    public void setMembers(Set<RndGroupMember> members) {
        this.members = members;
    }
}
