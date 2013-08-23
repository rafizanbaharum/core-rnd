package net.canang.corernd.core.model;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndGroupMember extends RndMetaObject {

    RndGroup getGroup();

    void setGroup(RndGroup group);

    RndPrincipal getMember();

    void setMember(RndPrincipal member);
}
