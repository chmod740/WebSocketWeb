package me.hupeng.websocket.module;

import me.hupeng.websocket.bean.User;
import me.hupeng.websocket.util.Toolkit;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.HashMap;
import java.util.Map;

@IocBean
@Ok("json")
@Fail("http:500")
public class PublicModule {
    @Inject
    Dao dao;


    @At("login")
    public Object login(@Param("username")String username, @Param("password")String password){
        if (username != null && password != null && !username.equals("") && !password.equals("")){
            User user = dao.fetch(User.class, Cnd.where("username", "=", username));
            if (user!= null && user.getPassword().equals(Toolkit.passwordEncode(password, user.getSalt()))){
                String ak = R.sg(64).next();
                user.setAccessKey(ak);
                Map<String, Object>data = new HashMap<>();
                data.put("ak", ak);
                return Toolkit.getSuccessResult(data);
            }
        }
        return Toolkit.getFailResult(-1, null);
    }
}
