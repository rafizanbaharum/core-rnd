package net.canang.corernd.core.model;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndMetaObject {

    Long getId();

    RndMetadata getMetadata();

    void setMetadata(RndMetadata metadata);
}
