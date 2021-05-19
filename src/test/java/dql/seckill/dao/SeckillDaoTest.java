package dql.seckill.dao;

import dql.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }

    @Test
    public void queryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /**
         * 1000元秒杀iphone6
         * Seckill
         * {seckillId=1000,
         * name='1000元秒杀iphone6',
         * number=100,
         * startTime=Sun Nov 01 13:00:00 CST 2015,
         * endTime=Mon Nov 02 14:00:00 CST 2015,
         * createTime=Wed May 12 00:49:29 CST 2021
         */
    }

    @Test
    public void queryAll() {
        /**
         * Caused by: org.apache.ibatis.binding.BindingException:
         * Parameter 'offset' not found. Available parameters are [arg1, arg0, param1, param2]
         */
        //java没有保存形参的记录

        /**
         * Seckill{seckillId=1000, name='1000元秒杀iphone6', number=100, startTime=Sun Nov 01 13:00:00 CST 2015, endTime=Mon Nov 02 14:00:00 CST 2015, createTime=Wed May 12 00:49:29 CST 2021}
         * Seckill{seckillId=1001, name='500元秒杀ipad2', number=200, startTime=Sun Nov 01 13:00:00 CST 2015, endTime=Mon Nov 02 14:00:00 CST 2015, createTime=Wed May 12 00:49:29 CST 2021}
         * Seckill{seckillId=1002, name='300元秒杀小米6', number=300, startTime=Sun Nov 01 13:00:00 CST 2015, endTime=Mon Nov 02 14:00:00 CST 2015, createTime=Wed May 12 00:49:29 CST 2021}
         * Seckill{seckillId=1003, name='200元秒杀红米note', number=400, startTime=Sun Nov 01 13:00:00 CST 2015, endTime=Mon Nov 02 14:00:00 CST 2015, createTime=Wed May 12 00:49:29 CST 2021}
         */
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }
}