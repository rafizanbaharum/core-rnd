package net.canang.corernd.core;

import net.canang.corernd.core.dao.RndGroupDao;
import net.canang.corernd.core.dao.RndUserDao;
import net.canang.corernd.core.model.RndGroup;
import net.canang.corernd.core.model.RndPrincipalType;
import net.canang.corernd.core.model.RndUser;
import net.canang.corernd.core.model.impl.RndGroupImpl;
import net.canang.corernd.core.model.impl.RndUserImpl;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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
public class RndUserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    private Logger log = LoggerFactory.getLogger(net.canang.corernd.core.RndUserDaoTest.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RndUserDao userDao;

    @Autowired
    private RndGroupDao groupDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RndUser root;

    @Before
    public void setUp() {
    }


    @Test
    @Rollback(value = true)
    public void findUser() {
        root = userDao.findByUsername("root");
        log.debug("root: " + root.getPassword());
        Assert.assertNotNull(root);
    }

    @Test
    @Rollback(value = false)
    public void addUser() {
        try {
            root = userDao.findByUsername("root");
            RndGroup group = groupDao.findByName("GroupLevel2");
            groupDao.addMember(group, root, root);
        } catch (RecursiveGroupException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (LockedGroupException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @Test
    @Rollback(value = false)
    public void createHierarchicalGroup() {
        try {
            root = userDao.findByUsername("root");

            RndGroup group = new RndGroupImpl();
            group.setName("RootGroup");
            groupDao.save(group, root);
            sessionFactory.getCurrentSession().flush();
            sessionFactory.getCurrentSession().refresh(group);

            RndGroup level1 = new RndGroupImpl();
            level1.setName("GroupLevel1");
            groupDao.save(level1, root);
            sessionFactory.getCurrentSession().flush();
            sessionFactory.getCurrentSession().refresh(level1);
            groupDao.addMember(group, level1, root);
            sessionFactory.getCurrentSession().flush();

            RndGroup level2 = new RndGroupImpl();
            level2.setName("GroupLevel2");
            groupDao.save(level2, root);
            sessionFactory.getCurrentSession().flush();
            sessionFactory.getCurrentSession().refresh(level2);
            groupDao.addMember(level1, level2, root);
            sessionFactory.getCurrentSession().flush();
        } catch (RecursiveGroupException e) {
            e.printStackTrace();
        } catch (LockedGroupException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback(value = true)
    public void createUser() {
        RndUser newUser = new RndUserImpl();
        newUser.setUsername("newuser");
        newUser.setRealname("New User");
        newUser.setEmail("test");
        newUser.setPassword(passwordEncoder.encodePassword("abc123", null));
        newUser.setLocked(false);
        newUser.setPrincipalType(RndPrincipalType.USER);
        userDao.save(newUser, root);
        RndUser saved = userDao.findByUsername("newuser");
        Assert.assertNotNull(saved);
    }

}
