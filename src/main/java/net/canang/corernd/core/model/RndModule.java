package net.canang.corernd.core.model;

import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
public interface RndModule extends RndMetaObject {

    String getCode();

    void setCode(String code);

    String getAlias();

    void setAlias(String alias);

    String getDescription();

    void setDescription(String description);

    Integer getOrder();

    void setOrder(Integer order);

    Set<RndSubModule> getSubModules();

    void setSubModules(Set<RndSubModule> subModules);

}
