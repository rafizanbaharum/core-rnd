package net.canang.corernd.core.model;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndSubModule extends RndMetaObject {

    String getCode();

    void setCode(String code);

    String getAlias();

    void setAlias(String alias);

    String getDescription();

    void setDescription(String description);

    Integer getOrder();

    void setOrder(Integer order);

}
