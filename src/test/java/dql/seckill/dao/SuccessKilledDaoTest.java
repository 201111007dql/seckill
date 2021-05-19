package dql.seckill.dao;

import dql.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        /**
         * 第一次 1
         * 第二次 0
         */
        long id = 1001L;
        long phone = 15261816119L;
        int i = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount=" + i);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1001L;
        long phone = 15261816119L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}