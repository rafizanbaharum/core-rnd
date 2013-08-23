package net.canang.corernd.core.dao;

import net.canang.corernd.core.model.RndStudent;
import net.canang.corernd.core.model.RndUser;

import java.util.List;

/**
 * @author rafizan.baharum
 * @since 7/20/13
 */
public interface RndStudentDao {

    RndStudent findById(Long id);

    RndStudent findByMatrixNo(String matrixId);

    List<RndStudent> find(Integer offset, Integer limit);

    List<RndStudent> find(String filter, Integer offset, Integer limit);

    Integer count();

    Integer count(String filter);

    void save(RndStudent student, RndUser user);

    void update(RndStudent student, RndUser user);

    void deactivate(RndStudent student, RndUser user);

}

