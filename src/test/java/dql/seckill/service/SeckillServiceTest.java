package dql.seckill.service;

import dql.seckill.dto.Exposer;
import dql.seckill.dto.SeckillExecution;
import dql.seckill.entity.Seckill;
import dql.seckill.exception.RepeatKillException;
import dql.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("list={}", seckillList);
    }

    @Test
    public void getById() {
        long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }


    //集成测试代码完整逻辑，注意可重复执行
    @Test
    public void seckillLogic() {
        long id = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 1334943754L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", seckillExecution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }

        /**
         * Exposer{
         * exposed=true,
         * md5='b38b34df00e4ad3a907e6bee08a4554c',
         * seckillId=1000,
         * now=0, start=0, end=0}
         */
    }

    @Test
    public void executeSeckill() {
        long id = 1000L;
        long phone = 1334943754L;
        String md5 = "b38b34df00e4ad3a907e6bee08a4554c";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", seckillExecution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        }

//        dql.seckill.exception.SeckillException: seckill data rewrite
        /**
         * result=SeckillExecution{
         * seckillId=1000,
         * state=1,
         * stateInfo='秒杀成功',
         * successKilled=SuccessKilled{
         * seckillId=1000,
         * userPhone=1334943754,
         * state=0,
         * createTime=null,
         * seckill=Seckill{
         * seckillId=1000,
         * name='1000元秒杀iphone6',
         * number=96,
         * startTime=Fri May 14 13:00:00 CST 2021,
         * endTime=Sat May 15 13:00:00 CST 2021,
         * createTime=null}}}
         */
    }
}