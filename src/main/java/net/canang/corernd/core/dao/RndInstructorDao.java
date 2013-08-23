package net.canang.corernd.core.dao;

import net.canang.corernd.core.model.RndInstructor;
import net.canang.corernd.core.model.RndUser;

import java.util.List;

/**
 * @author rafizan.baharum
 * @since 7/20/13
 */
public interface RndInstructorDao {

    RndInstructor findById(Long id);

    RndInstructor findByMatrixNo(String matrixId);

    List<RndInstructor> find(Integer offset, Integer limit);

    List<RndInstructor> find(String filter, Integer offset, Integer limit);

    Integer count();

    Integer count(String filter);

    void save(RndInstructor instructor, RndUser user);

    void update(RndInstructor instructor, RndUser user);

    void deactivate(RndInstructor instructor, RndUser user);

}
