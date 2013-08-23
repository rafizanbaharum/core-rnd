package net.canang.corernd.core.dao;

import net.canang.corernd.core.RecursiveGroupException;
import net.canang.corernd.core.model.RndPrincipal;
import net.canang.corernd.core.model.RndPrincipalType;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * @author rafizan.baharum
 * @since 7/12/13
 */
public interface RndPrincipalDao {

    //principal

    RndPrincipal findById(Long id);

    RndPrincipal findByName(String name);

    List<RndPrincipal> findAllPrincipals();

    List<RndPrincipal> findPrincipals(String filter);

    List<RndPrincipal> findPrincipals(String filter, RndPrincipalType type);

    List<RndPrincipal> findPrincipals(Integer offset, Integer limit);

    Set<GrantedAuthority> loadEffectiveAuthorities(RndPrincipal principal) throws RecursiveGroupException;

}
