package net.canang.corernd.core.model;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndActor extends RndMetaObject {

    String getIdentityNo();

    void setIdentityNo(String identityNo);

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    String getAddress1();

    void setAddress1(String address1);

    String getAddress2();

    void setAddress2(String address2);

    String getAddress3();

    void setAddress3(String address3);

    String getPhone();

    void setPhone(String phone);

    String getFax();

    void setFax(String fax);

    RndActorType getActorType();

    void setActorType(RndActorType actorType);
}
