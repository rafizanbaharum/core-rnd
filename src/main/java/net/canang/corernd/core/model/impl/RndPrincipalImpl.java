package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndMetadata;
import net.canang.corernd.core.model.RndPrincipal;
import net.canang.corernd.core.model.RndPrincipalRole;
import net.canang.corernd.core.model.RndPrincipalType;

import javax.persistence.*;
import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
@Table(name = "RND_PRINCIPAL")
@Entity(name = "RndPrincipal")
@Inheritance(strategy = InheritanceType.JOINED)
public class RndPrincipalImpl implements RndPrincipal {

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="SEQ_RND_PRINCIPAL", name="SEQ_RND_PRINCIPAL")
    @GeneratedValue(generator="SEQ_RND_PRINCIPAL", strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOCKED")
    private boolean locked;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "PRINCIPAL_TYPE")
    private RndPrincipalType principalType;

    @OneToMany(targetEntity = RndPrincipalRoleImpl.class, mappedBy = "principal", fetch = FetchType.EAGER)
    private Set<RndPrincipalRole> roles;

    @Embedded
    private RndMetadata metadata = new RndMetadata();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public RndPrincipalType getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(RndPrincipalType principalType) {
        this.principalType = principalType;
    }

    public Set<RndPrincipalRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<RndPrincipalRole> roles) {
        this.roles = roles;
    }

    public RndMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RndMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "CmPrincipalImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + locked +
                '}';
    }
}
