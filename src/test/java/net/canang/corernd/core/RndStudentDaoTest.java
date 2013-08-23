package net.canang.corernd.core;

import net.canang.corernd.core.dao.RndStudentDao;
import net.canang.corernd.core.dao.RndUserDao;
import net.canang.corernd.core.model.RndStudent;
import net.canang.corernd.core.model.RndUser;
import net.canang.corernd.core.model.impl.RndStudentImpl;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author rafizan.baharum
 * @since 7/11/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {net.canang.corernd.core.config.RndCoreConfig.class})
public class RndStudentDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    private Logger log = LoggerFactory.getLogger(net.canang.corernd.core.RndStudentDaoTest.class);

    @Autowired
    private RndUserDao userDao;

    @Autowired
    private RndStudentDao studentDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    private RndUser root;

    @Before
    public void setUp() {
        log.debug("logging in user");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("root", "abc123");
        Authentication authed = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authed);
        root = userDao.findByUsername("root");
    }

    @Test
    @Rollback(value = false)
    public void createStudent() {
        RndStudent student = new RndStudentImpl();
        student.setIdentityNo("12345678");
        student.setNricNo("12345678");
        student.setName("Rafizan Baharum");
        student.setAddress1("Address 1");
        student.setAddress2("Address 2");
        student.setAddress3("Address 3");
        student.setEmail("rafizan.baharum@gmail.com");
        student.setPhone("1234");
        student.setFax("1234 3");
        studentDao.save(student, root);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().refresh(student);
    }
}
