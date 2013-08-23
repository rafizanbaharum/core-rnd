package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndActorType;
import net.canang.corernd.core.model.RndInstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
@Table(name = "RND_INSTRUCTOR")
@Entity(name = "RndInstructor")
public class RndInstructorImpl extends RndActorImpl implements RndInstructor {

    public RndInstructorImpl() {
        setActorType(RndActorType.INSTRUCTOR);
    }

    public String getStaffNo() {
        return getIdentityNo();
    }

    public void setStaffNo(String staffNo) {
        setIdentityNo(staffNo);
    }

}
