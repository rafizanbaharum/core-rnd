package net.canang.corernd.core.model;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndUser extends RndPrincipal {

    String getUsername();

    void setUsername(String username);

    String getRealname();

    void setRealname(String realname);

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);

    RndActor getActor();

    void setActor(RndActor actor);

}
