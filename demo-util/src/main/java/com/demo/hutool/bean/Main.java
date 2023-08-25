package com.demo.hutool.bean;

import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanPath;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.DynaBean;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.lang.Opt;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>JavaBean</h1>
 *
 * <p>
 * createDate 2022/03/09 13:51:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        beanUtil();
        dynaBean();
        beanPath();
        beanDesc();
        opt();
    }

    /**
     * Bean工具
     */
    private static void beanUtil() {
        log.info("---------- Bean工具 ----------");
        /*是否为Bean对象*/
        log.info("HashMap是否为Bean对象:" + BeanUtil.isBean(HashMap.class));
        log.info("Person是否为Bean对象:" + BeanUtil.isBean(Person.class));

        /*内省Introspector*/
        PropertyDescriptor[] propertyDescriptors = BeanUtil.getPropertyDescriptors(SubPerson.class);
        log.info("内省Introspector:");
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            log.info("descriptor:" + descriptor);
        }

        /*Bean属性注入*/
        Person person = BeanUtil.fillBean(new Person(), new ValueProvider<String>() {
            @Override
            public Object value(String key, Type valueType) {
                switch (key) {
                    case "name":
                        return "张三";
                    case "age":
                        return 18;
                    default:
                        return null;
                }
            }

            @Override
            public boolean containsKey(String key) {
                //总是存在key
                return true;
            }
        }, CopyOptions.create());
        log.info("Bean属性注入:" + person);

        /*使用Map填充bean*/
        Map<String, Object> map = new HashMap<>(3);
        map.put("name", "Joe");
        map.put("age", 12);
        map.put("openId", "ABC");
        SubPerson subPerson = BeanUtil.fillBeanWithMap(map, new SubPerson(), false);
        log.info("使用Map填充bean:" + subPerson);

        /*使用Map填充bean，忽略大小写*/
        Map<String, Object> map2 = new HashMap<>(3);
        map2.put("Name", "Joe");
        map2.put("aGe", 12);
        map2.put("openId", "ABC");
        SubPerson subPerson2 = BeanUtil.fillBeanWithMapIgnoreCase(map2, new SubPerson(), false);
        log.info("使用Map填充bean，忽略大小写:" + subPerson2);

        /*对象或Map转Bean*/
        Map<String, Object> map3 = new HashMap<>(2);
        map3.put("a_name", "Joe");
        map3.put("b_age", 12);
        // 设置别名，用于对应bean的字段名
        Map<String, String> mapping = new HashMap<>(2);
        mapping.put("a_name", "name");
        mapping.put("b_age", "age");
        Person person2 = BeanUtil.toBean(map3, Person.class, CopyOptions.create().setFieldMapping(mapping));
        log.info("对象或Map转Bean:" + person2);

        /*对象或Map转Bean，忽略大小写*/
        Map<String, Object> map4 = new HashMap<>(2);
        map4.put("a_name", "Joe");
        map4.put("b_age", 12);
        Person person3 = BeanUtil.toBeanIgnoreCase(map4, Person.class, false);
        log.info("对象或Map转Bean，忽略大小写:" + person3);

        /*Bean转为Map*/
        SubPerson subPerson1 = new SubPerson();
        subPerson1.setAge(14);
        subPerson1.setOpenId("11213232");
        subPerson1.setName("测试A11");
        subPerson1.setSubName("sub名字");
        Map<String, Object> map5 = BeanUtil.beanToMap(subPerson1);
        log.info("Bean转为Map:" + map5);

        /*Bean转Bean*/
        SubPerson subPerson3 = new SubPerson();
        subPerson3.setIsSlow(true);
        subPerson3.setName("测试");
        subPerson3.setSubName("sub测试");
        Map<String, Object> map6 = new HashMap<>(3);
        BeanUtil.copyProperties(subPerson3, map6);
        log.info("Bean转Bean:" + map6);

        /*Alias注解*/
        /*Bean转Map*/
        SubPersonWithAlias subPersonWithAlias = new SubPersonWithAlias();
        subPersonWithAlias.setSubName("sub名字");
        subPersonWithAlias.setSlow(true);
        // Bean转换为Map时，自动将subName修改为aliasSubName
        Map<String, Object> map7 = BeanUtil.beanToMap(subPersonWithAlias);
        log.info("Alias注解Bean转Map:" + map7.get("aliasSubName"));
        /*Map转Bean*/
        Map<String, Object> map8 = new HashMap<>(2);
        map8.put("aliasSubName", "sub名字");
        map8.put("slow", true);
        SubPersonWithAlias subPersonWithAlias2 = BeanUtil.toBean(map8, SubPersonWithAlias.class);
        log.info("Alias注解Map转Bean:" + subPersonWithAlias2.getSubName());
    }

    /**
     * 动态Bean
     */
    private static void dynaBean() {
        log.info("---------- 动态Bean ----------");
        /*通过对象创建Bean*/
        User user = new User();
        DynaBean bean = DynaBean.create(user);
        bean.set("name", "李华");
        bean.set("age", 12);
        log.info("通过对象创建Bean:" + bean);

        /*通过反射创建Bean*/
        DynaBean bean2 = DynaBean.create(User.class);
        bean2.set("name", "李华2");
        bean2.set("age", 123);
        log.info("通过反射创建Bean:" + bean2);

        /*执行对象中的任意方法*/
        Object invoke = bean2.invoke("testMethod");
        log.info("执行对象中的任意方法:" + invoke);
    }

    /**
     * 表达式解析
     */
    private static void beanPath() {
        log.info("---------- 表达式解析 ----------");
        // 设置值
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setName("name");
        List<String> list = new ArrayList<>(1);
        list.add("abc");
        Map<String, String> map = new HashMap<>(1);
        map.put("key", "value");
        user.setList(list);
        user.setMap(map);
        users.add(user);
        log.info("users:" + users);
        // 绑定值
        Map<String, Object> tempMap = new HashMap<>(1);
        tempMap.put("users", users);
        // 获取值
        BeanPath beanPath = new BeanPath("users[0]");
        log.info("users[0]:" + beanPath.get(tempMap));
        BeanPath beanPath2 = new BeanPath("users[0].name");
        log.info("users[0].name:" + beanPath2.get(tempMap));
        BeanPath beanPath3 = new BeanPath("users[0].list[0]");
        log.info("users[0].list[0]:" + beanPath3.get(tempMap));
        BeanPath beanPath4 = new BeanPath("users[0].map['key']");
        log.info("users[0].map['key']:" + beanPath4.get(tempMap));
    }

    /**
     * Bean描述
     */
    private static void beanDesc() {
        log.info("---------- Bean描述 ----------");
        /*字段getter方法获取*/
        BeanDesc desc = BeanUtil.getBeanDesc(User.class);
        log.info("获取Bean的简单类名:" + desc.getSimpleName());
        log.info("获得字段名对应的字段对象:" + desc.getField("age").getName());
        log.info("获取Getter方法:" + desc.getGetter("age").getName());
        log.info("获取Setter方法:" + desc.getSetter("age").getName());
        /*字段属性赋值*/
        User user = new User();
        desc.getProp("name").setValue(user, "张三");
        log.info("字段属性赋值:" + user);
    }

    /**
     * 空检查属性获取
     */
    private static void opt() {
        log.info("---------- 空检查属性获取 ----------");
        User user = new User();
        user.setName("name");
        String address = Opt.ofNullable(user).map(User::getSchool).map(User.School::getAddress).get();
        log.info("空检查属性获取:" + address);
    }

}
