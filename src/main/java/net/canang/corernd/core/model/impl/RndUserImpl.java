package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndActor;
import net.canang.corernd.core.model.RndPrincipalType;
import net.canang.corernd.core.model.RndUser;

import javax.persistence.*;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
@Table(name = "RND_USER")
@Entity(name = "RndUser")
public class RndUserImpl extends RndPrincipalImpl implements RndUser {

    @Column(name = "REALNAME")
    private String realname;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne(targetEntity = RndActorImpl.class)
    @JoinColumn(name = "ACTOR_ID")
    private RndActor actor;

    public RndUserImpl() {
        setPrincipalType(RndPrincipalType.USER);
    }

    public String getUsername() {
        return getName();
    }

    public void setUsername(String username) {
        setName(username);
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RndActor getActor() {
        return actor;
    }

    public void setActor(RndActor actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "CmUserImpl{" +
                "name='" + getName() + '\'' +
                "realname='" + realname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
