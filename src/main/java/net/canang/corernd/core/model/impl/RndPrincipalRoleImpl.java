package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndMetadata;
import net.canang.corernd.core.model.RndPrincipal;
import net.canang.corernd.core.model.RndPrincipalRole;
import net.canang.corernd.core.model.RndRoleType;

import javax.persistence.*;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
@Table(name = "RND_PRINCIPAL_ROLE")
@Entity(name = "RndPrincipalRole")
public class RndPrincipalRoleImpl implements RndPrincipalRole {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ROLE_TYPE")
    private RndRoleType roleType;

    @OneToOne(targetEntity = RndPrincipalImpl.class)
    @JoinColumn(name = "PRINCIPAL_ID")
    private RndPrincipal principal;

    @Embedded
    private RndMetadata metadata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RndRoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RndRoleType roleType) {
        this.roleType = roleType;
    }

    public RndPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(RndPrincipal principal) {
        this.principal = principal;
    }

    public RndMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RndMetadata metadata) {
        this.metadata = metadata;
    }
}
