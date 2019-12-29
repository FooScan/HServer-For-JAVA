package top.test.init;

import top.hserver.core.interfaces.InitRunner;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;
import top.test.bean.User;

@Bean
public class RunInit implements InitRunner {

    @Autowired
    private User user;

    @Override
    public void init() {
        System.out.println("初始化方法：注入的User对象的名字是-->"+user.getName());
    }
}