package kr.co.softtrain.common.web.did.util;

/**
 * <pre>
 * kr.co.softtrain.common.web.did.util
 * DIDConstants.java
 * </pre>
 * @title :  DID 관련 상수 정의
 * @author : kwc
 * @since : 2021. 08. 17.
 * @version : 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일			수정자				수정내용
 *  ---------------------------------------------------------------------------------
 *   2021. 08. 17.		kwc   			최초생성
 * 
 * </pre>
 */
public class DidConstants 
{
	/** 씨젠의료재단 한글명 */
	public static String[] smfNameKor = { "씨젠의료재단 씨젠의원",
			                              "씨젠의료재단 씨젠부산의원",
			                              "씨젠의료재단 씨젠대구의원",
			                              "씨젠의료재단 씨젠광주의원"};
	
	/** 씨젠의료재단 영문명 */
	public static String[] smfNameEng = { "Seegene Medical Foundation",
							              "Seegene Medical Foundation Busan,Gyeongnam Testing Center",
							              "Seegene Medical Foundation Daegu,Gyeongbuk Testing Center",
							              "Seegene Medical Foundation Gwangju,Honam Testing Center"};
	
	/** 씨젠의료재단 요양기관코드 */
	public static String[] smfInstitutionCode = { "11370319",
									              "21349428",
									              "37349767",
									              "36325953"};
	
	public static int getSmfBranchNo(String institutionCode)
	{
		for (int i = 0; i < smfInstitutionCode.length; i++)
		{
			if (smfInstitutionCode[i].equals(institutionCode))
				return i;
		}
		
		return -1;
	}
	
	/** 검사항목 PCR 검사 */
	public static final String TYPE_PCR = "P";
	public static final String TYPECODE_PCR = "COVI";
	
	/** 검사항목 항체검사 */
	public static final String TYPE_ANTIBODY = "I";
	public static final String TYPECODE_ANTIBODY = "ANTI";
	
	
}
