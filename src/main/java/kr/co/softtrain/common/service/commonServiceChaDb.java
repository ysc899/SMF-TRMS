package kr.co.softtrain.common.service;

import java.util.List;
import java.util.Map;

/**
 * 공통 CRUD 비즈니스 인터페이스
 * 
 * @author David Park
 * @since 2018. 05. 21
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

public interface commonServiceChaDb {
	//단건조회
	public Object commonLog(Map<String,Object> parameterObject) throws Exception;
	
	//다건조회
	public List<Map<String, Object>> commonList(String queryId, Map<String,Object> parameterObject) throws Exception;

	//단건조회
	public Object commonOne(String queryId, Map<String,Object> parameterObject) throws Exception;

	//수정처리
	public void commonUpdate(String queryId, Map<String,Object> parameterObject) throws Exception;

	//등록처리
	public void commonInsert(String queryId, Map<String,Object> parameterObject) throws Exception;
	
	//삭제 처리
	public void commonDelete(String queryId, Map<String,Object> parameterObject) throws Exception;

	//단건조회
	public Object commonListOne(String queryId, Map<String,Object> parameterObject, List<Map<String, Object>> paramList) throws Exception;

	//List수정처리
	public void commonListUpdate(String queryId, Map<String,Object> parameterObject, List<Map<String, Object>> paramList,String RQueryId) throws Exception;
	
	//List등록처리
	public void commonListInsert(String queryId, Map<String,Object> parameterObject, List<Map<String, Object>> paramList) throws Exception;
	
	//List 상세 내역 조회후 List Update
	public void commonListUpdate2(String queryId, Map<String,Object> parameterObject, List<Map<String, Object>> paramList, String subListQueryId, String RQueryId) throws Exception;
	
	//List삭제처리
	public void commonListDelete(String queryId, Map<String,Object> parameterObject, List<Map<String, Object>> paramList,String RQueryId) throws Exception;
	
	
}
