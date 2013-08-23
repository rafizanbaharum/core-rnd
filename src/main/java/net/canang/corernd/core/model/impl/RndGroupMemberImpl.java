package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndGroup;
import net.canang.corernd.core.model.RndGroupMember;
import net.canang.corernd.core.model.RndMetadata;
import net.canang.corernd.core.model.RndPrincipal;

import javax.persistence.*;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
@Table(name = "RND_GROUP_MEMBER")
@Entity(name = "RndGroupMember")
public class RndGroupMemberImpl implements RndGroupMember {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = RndGroupImpl.class)
    @JoinColumn(name = "GROUP_ID")
    private RndGroup group;

    @OneToOne(targetEntity = RndPrincipalImpl.class)
    @JoinColumn(name = "MEMBER_ID")
    private RndPrincipal member;

    @Embedded
    private RndMetadata metadata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RndGroup getGroup() {
        return group;
    }

    public void setGroup(RndGroup group) {
        this.group = group;
    }

    public RndPrincipal getMember() {
        return member;
    }

    public void setMember(RndPrincipal member) {
        this.member = member;
    }

    public RndMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RndMetadata metadata) {
        this.metadata = metadata;
    }
}
