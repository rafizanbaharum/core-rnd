package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndDocument;
import net.canang.corernd.core.model.RndFlowdata;
import net.canang.corernd.core.model.RndMetadata;

import javax.persistence.*;

/**
 * @author rafizan.baharum
 * @since 7/11/13
 */
@Table(name = "RND_DOCUMENT")
@Entity(name = "RndDocument")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class RndDocumentImpl implements RndDocument {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "REFERENCE_NO")
    private String referenceNo;

    @Column(name = "SOURCE_NO")
    private String sourceNo;

    @Embedded
    private RndMetadata metadata;

    @Embedded
    private RndFlowdata flowdata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }

    public RndMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RndMetadata metadata) {
        this.metadata = metadata;
    }

    public RndFlowdata getFlowdata() {
        return flowdata;
    }

    public void setFlowdata(RndFlowdata flowdata) {
        this.flowdata = flowdata;
    }
}
