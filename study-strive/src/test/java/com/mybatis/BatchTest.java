package com.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.web.entity.SysBall;
import com.web.mapper.SysBallMapper;
import com.tools.GenerateBall;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchTest {

	private Logger logger = LoggerFactory.getLogger(BatchTest.class);
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private SysBallMapper sysBallMapper;
	
	@Test
	public void batchSqlSession() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
		SysBallMapper mapper = sqlSession.getMapper(SysBallMapper.class);
		long begin = System.currentTimeMillis();
		for(int i=0 ; i<100000 ; i++) {
			SysBall sb = new SysBall();
			sb.setNum(ball());
			mapper.insert(sb);
		}
		long end = System.currentTimeMillis();
		sqlSession.commit();
		logger.info("sqlSession 10万数据入库耗时:{}",end-begin);
	}
	
	@Test
	public void batchReuse() {
		// 2.获取sqlSession
		long begin = System.currentTimeMillis();
		for(int i=0 ; i<100000 ; i++) {
			SysBall sb = new SysBall();
			sb.setNum(ball());
			sysBallMapper.insert(sb);
		}
		long end = System.currentTimeMillis();
		logger.info("reuse = 10万数据入库耗时:{}",end-begin);
	}
	
	private String ball() {
		ArrayList<Integer> list = GenerateBall.randomRedBall();
		list.add(GenerateBall.randomBlueBall());
		StringBuilder sb = new StringBuilder();
		for(int i=0 ; i<list.size() ; i++) {
			if(i<list.size()-1) {
				sb.append(list.get(i)).append(",");
			}else {
				sb.append(list.get(i));
			}
		}
		return sb.toString();
	}
}
