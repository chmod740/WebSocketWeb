package me.hupeng.websocket;

import me.hupeng.websocket.bean.User;
import me.hupeng.websocket.util.Toolkit;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MainSetup implements Setup{
    public static Ioc ioc;
    @Override
    public void destroy(NutConfig arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(NutConfig conf) {
        MainSetup.ioc = conf.getIoc();
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, "me.hupeng.websocket", false);

        if (dao.count(User.class) == 0){
            User user = new User();
            user.setUsername("admin");
            Toolkit.generatePasswd(user, "123456");
            dao.insert(user);
        }
    }
}
