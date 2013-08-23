package net.canang.corernd.core.model.impl;

import net.canang.corernd.core.model.RndActorType;
import net.canang.corernd.core.model.RndStudent;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author rafizan.baharum
 * @since 7/10/13
 */
@Table(name = "RND_STUDENT")
@Entity(name = "RndStudent")
public class RndStudentImpl extends RndActorImpl implements RndStudent {

    public RndStudentImpl() {
        setActorType(RndActorType.STUDENT);
    }

    public String getMatrixNo() {
        return getIdentityNo();
    }

    public void setMatrixNo(String matrixNo) {
        setIdentityNo(matrixNo);
    }


}
