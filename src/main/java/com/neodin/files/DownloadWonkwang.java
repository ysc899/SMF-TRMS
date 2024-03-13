package com.neodin.files;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2006-04-03 오후 12:20:51)
 * @작성자 :  백기성
 */
import java.io.File;

import jxl.Workbook;

public class DownloadWonkwang extends ResultDownload {
	boolean isDebug = false;

	boolean isData = false;

	/**
	 * DownloadAMC 생성자 주석.
	 */
	public DownloadWonkwang() {
		super();
		initialize();
	}

	public DownloadWonkwang(String id, String fdat, String tdat,
			Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public String appendBlanks(String src, int length) {
		String dest = src.trim().substring(0);
		if (src.trim().length() < length) {
			for (int i = 0; i < length - src.length(); i++) {
				dest += " ";
			}
		} else
			dest = src.substring(0, length);
		return dest;
	}

	/**
	 * closeDownloadFile 메소드 주석.
	 */
	public void closeDownloadFile() {
		if (!isDebug && isData)
			try {
				workbook.write();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

	/**
	 * makeDownloadFile 메소드 주석.
	 */
	public void makeDownloadFile() {
		row = 1;

		try {

			// Start =============== >엑셀파일을 만든다 : 파일명 ,시트명
			// #####################################
			workbook = Workbook
					.createWorkbook(new File(savedir + makeOutFile()));
			wbresult = workbook.createSheet("Result", 0);

			String ArraryResult[] = null;
			ArraryResult = new String[] { "의뢰일", "수진자번호", "수신자명", "병원검사코드",
					"검체번호", "검사결과", "검사명", "검사코드", "H/L", "Remark", "참고치",
					"주민등록번호", "성별", "나이", "접수번호", };

			for (int i = 0; i < ArraryResult.length; i++) {
				label = new jxl.write.Label(i, 0, ArraryResult[i]);
				wbresult.addCell(label);
			}

		} catch (Exception e) {
			//System.out.println("OCS 파일쓰기 스레드 오류" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * processingData 메소드 주석.
	 */
	public void processingData(int cnt) {
		try {
			String[] hosCode = (String[]) getDownloadData().get("병원코드"); // 병원코드
			String[] rcvDate = (String[]) getDownloadData().get("접수일자");
			String[] rcvNo = (String[]) getDownloadData().get("접수번호");
			String[] specNo = (String[]) getDownloadData().get("검체번호");
			String[] chartNo = (String[]) getDownloadData().get("차트번호");
			String[] patName = (String[]) getDownloadData().get("수신자명"); //
			String[] inspectCode = (String[]) getDownloadData().get("검사코드"); //
			String[] inspectName = (String[]) getDownloadData().get("검사명"); //
			String[] seq = (String[]) getDownloadData().get("일련번호"); //
			String[] result = (String[]) getDownloadData().get("결과"); //
			String[] resultType = (String[]) getDownloadData().get("결과타입"); // 문자인자
																			// 문장인지
			String[] clientInspectCode = (String[]) getDownloadData().get(
					"병원검사코드"); //
			String[] sex = (String[]) getDownloadData().get("성별");
			String[] age = (String[]) getDownloadData().get("나이");
			String[] securityNo = (String[]) getDownloadData().get("주민번호");
			String[] highLow = (String[]) getDownloadData().get("결과상태");
			String[] lang = (String[]) getDownloadData().get("언어");
			String[] history = (String[]) getDownloadData().get("이력");
			String[] rmkCode = (String[]) getDownloadData().get("리마크코드");
//			String[] unit = (String[]) getDownloadData().get("참고치단위등");
//			String[] rdate = (String[]) getDownloadData().get("검사완료일");
//			String[] bcode = (String[]) getDownloadData().get("보험코드");
			String[] data = new String[17]; // 결과 필드갯수

//			String[] remark = new String[4]; // 참고치를 불러오기 위한 키값
//			boolean culture_flag = false;
//			StringTokenizer st = null;
//			int tokenCnt = 0;
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				isData = true;
				data[0] = rcvDate[i].trim(); // 접수일자
				data[1] = specNo[i].trim(); // 검체번호- 환자번호

				data[2] = patName[i]; // 수신자명
				data[3] = clientInspectCode[i];
				data[4] = chartNo[i]; // 챠트번호 - 검체번호

				if (resultType[i].trim().equals("C")) {
					data[5] = result[i]; // 결과
				} else {
					data[5] = getTextResultValue(hosCode[i], rcvDate[i],
							rcvNo[i], inspectCode[i]);
				}
				
				
				data[6] = inspectName[i]; // 검사명
				data[7] = inspectCode[i].trim(); // 검사코드
				data[8] = highLow[i];
				if (rmkCode[i].trim().length() > 0) { // 리마크

					try {
						if (!(kumdata[0].trim().equals(rcvDate[i].trim())
								&& kumdata[1].trim().equals(rcvNo[i].trim()) && kumdata[2]
								.trim().equals(rmkCode[i].trim()))) {
							data[9] = getReamrkValue(hosCode[i], rcvDate[i],
									rcvNo[i], rmkCode[i]); // 리마크내용
							// getRemark(new String [] { hosCode[i], rcvDate[i],
							// rcvNo[i], rmkCode[i] }).trim();

							//
							kumdata[0] = rcvDate[i].trim();
							kumdata[1] = rcvNo[i].trim();
							kumdata[2] = rmkCode[i].trim();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					data[9] = "";
				}
				data[10] = getReferenceValue(
						new String[] { inspectCode[i], lang[i], history[i],
								sex[i] }).trim();
				data[11] = securityNo[i];
				data[12] = sex[i];
				data[13] = age[i];
				data[14] = rcvNo[i];
				data[15] =  result[i]; // 검사명
				data[16] = result[i]; // // 검사명
				data[17] = result[i]; //
				data[18] = result[i]; //
				data[19] = result[i]; //
				data[20] = result[i]; //
				for (int k = 0; k < data.length; k++) {
					label = new jxl.write.Label(k, row, data[k]);
					wbresult.addCell(label);
				}
				row++;
			}

			//	
			if (cnt == DWN_LIMIT) {
				setParameters(new String[] { hosCode[cnt - 1],
						rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],
						seq[cnt - 1] });
			} else
				setParameters(null);
		} catch (Exception e) {
			setParameters(null);
			e.printStackTrace();
		}
	}
}
