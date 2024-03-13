package kr.co.softtrain.common.service.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 공통등록처리
 * 
 * 
 * @author David Park
 * @since 2018.05.21
 * @version
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2018.05.21		David Park 			최초생성
 * 
 * </pre>
 */

@Repository("commonMapper")
public class commonMapper extends EgovAbstractMapper {

	Logger logger = LogManager.getLogger();
	
	@Override
	@Resource(name="sqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}
		
	
}
