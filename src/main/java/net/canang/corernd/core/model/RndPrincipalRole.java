package net.canang.corernd.core.model;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndPrincipalRole extends RndMetaObject {

    RndRoleType getRoleType();

    RndPrincipal getPrincipal();

}
