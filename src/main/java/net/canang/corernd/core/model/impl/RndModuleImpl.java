package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndMetadata;
import net.canang.corernd.core.model.RndModule;
import net.canang.corernd.core.model.RndSubModule;

import javax.persistence.*;
import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
@Table(name = "RND_MODULE")
@Entity(name = "RndModule")
public class RndModuleImpl implements RndModule {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ORDR")
    private Integer order;

    @OneToMany(targetEntity = RndSubModuleImpl.class, mappedBy = "module", fetch = FetchType.EAGER)
    private Set<RndSubModule> subModules;

    @Embedded
    private RndMetadata metadata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Set<RndSubModule> getSubModules() {
        return subModules;
    }

    public void setSubModules(Set<RndSubModule> subModules) {
        this.subModules = subModules;
    }

    public RndMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RndMetadata metadata) {
        this.metadata = metadata;
    }
}
