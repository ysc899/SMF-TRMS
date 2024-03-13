package com.neodin.files;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import sun.management.resources.agent;

import jxl.Workbook;

import com.neodin.comm.Common;

public class DownloadCIT extends ResultDownload {   
	boolean isDebug = false;

	boolean isData = true;

	// String data[] = new String[18];
	// String[] _tmp = new String[3];
	// String remark[] = new String[4];
	// String remarkCode = "";
	public DownloadCIT(String id, String fdat, String tdat, Boolean isRewrite) {
		setId(id);
		setfDat(fdat);
		settDat(tdat);
		setIsRewrite(isRewrite.booleanValue());
		initialize();
	}

	public String appendBlanks(String src, int length) {
		String dest = src.trim().substring(0);
		if (src.trim().length() < length) {
			for (int i = 0; i < length - src.length(); i++)
				dest = dest + " ";
		} else {
			dest = src.substring(0, length);
		}
		return dest;
	}

	
	public void closeDownloadFile() {
		if (!isDebug) {
			try {
				workbook.write();
			} catch (Exception e) {
			} finally {
				try {
					if (workbook != null)
						workbook.close();
				} catch (Exception e) {
				}
			}
		}
	}
	

	public void makeDownloadFile() {
		row = 1;
		row2 = 1;
		if (!isDebug) {
			try {
				workbook = Workbook.createWorkbook(new File(savedir
						+ makeOutFile()));
				wbresult = workbook.createSheet("검사결과", 0);
				wbremark = workbook.createSheet("Remark", 1);
				String ArraryResult[] = null;
				ArraryResult = (new String[] { "접수일자", "환자번호", "접수번호", "환자명",
						"검사코드", "결과(단답형)", "결과(문장형)", "참고치", "단위", "보고일자",
						"접수일자", "최저치", "최고치", "이미지URL" });
				String ArraryRemark[] = { "검체번호", "Remark 번호", "Remark내용" };
				for (int i = 0; i < ArraryResult.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryResult[i]);
					wbresult.addCell(label);
				}
				for (int i = 0; i < ArraryRemark.length; i++) {
					label = new jxl.write.Label(i, 0, ArraryRemark[i]);
					wbremark.addCell(label);
				}
			} catch (Exception e) {
			}
		}
		// !
		// data = new String[18];
		// _tmp = new String[3];
		// remark = new String[4];
		// remarkCode = "";
	}

	public void processingData(int cnt) {

		// !
		try {

			// !
			String hosCode[] = (String[]) getDownloadData().get("병원코드");
			String rcvDate[] = (String[]) getDownloadData().get("접수일자");
			String rcvNo[] = (String[]) getDownloadData().get("접수번호");
			String specNo[] = (String[]) getDownloadData().get("검체번호");
			String chartNo[] = (String[]) getDownloadData().get("차트번호");

			
					
			// !
			String patName[] = (String[]) getDownloadData().get("수신자명");
			String inspectCode[] = (String[]) getDownloadData().get("검사코드");
			String inspectName[] = (String[]) getDownloadData().get("검사명");
			String seq[] = (String[]) getDownloadData().get("일련번호");
			String result[] = (String[]) getDownloadData().get("결과");

			// !
			String resultType[] = (String[]) getDownloadData().get("결과타입");
			String clientInspectCode[] = (String[]) getDownloadData().get("병원검사코드");
			String sex[] = (String[]) getDownloadData().get("성별");
//			String age[] = (String[]) getDownloadData().get("나이");
//			String securityNo[] = (String[]) getDownloadData().get("주민번호");

			// !
//			String highLow[] = (String[]) getDownloadData().get("결과상태");
			String lang[] = (String[]) getDownloadData().get("언어");
			String history[] = (String[]) getDownloadData().get("이력");
			String rmkCode[] = (String[]) getDownloadData().get("리마크코드");
			String cns[] = (String[]) getDownloadData().get("처방번호");
			// !
			String bdt[] = (String[]) getDownloadData().get("검사완료일");
//			String bgcd[] = (String[]) getDownloadData().get("보험코드");

			// !
//			String bbseq[] = (String[]) getDownloadData().get("요양기관번호");
//			String img[] = (String[]) getDownloadData().get("이미지여부"); // 내원번호
			String unit[] = (String[]) getDownloadData().get("참고치단위등");
//			String hosSamp[] = (String[]) getDownloadData().get("병원검체코드");

			// !
//			String inc[] = (String[]) getDownloadData().get("외래구분");
			StringTokenizer st = null;
			// !
			String data[] = new String[18];
//			String[] _tmp = new String[3];
			String remark[] = new String[4];
			String remarkCode = "";
			
			// !
			int tokenCnt = 0;
			String lastData = "";
			int lastindex = 0;
			boolean isNext = false;
			
			if (cnt == 400 && inspectCode[399].trim().length() == 7) {
				lastData = inspectCode[399].trim().substring(0, 5);
				lastindex = 399;
				isNext = true;

				while (lastData.equals(inspectCode[lastindex].trim().substring(0, 5)) && !(inspectCode[lastindex--].trim().substring(5).equals("00"))) {
					cnt--;
					if (inspectCode[lastindex].trim().substring(5).equals("00")) {
						cnt--;
					}
				}
				
				if(inspectCode[399].trim().substring(5).equals("00")){
					cnt--;
				}
			}
			
			for (int i = 0; i < cnt; i++) {
				//특검검사 크레아틴 보정후값만 적용되도록
				if ((inspectCode[i].trim().substring(0, 5).equals("05028")&&!inspectCode[i].trim().equals("0502802"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05029")&&!inspectCode[i].trim().equals("0502902"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05011")&&!inspectCode[i].trim().equals("0501102"))
						|| (inspectCode[i].trim().substring(0, 5).equals("05025")&&!inspectCode[i].trim().equals("0502502"))) { // 단독
					continue;
				}
				
				
				//cdy 67
				isData = true;
				data[2] = specNo[i].trim(); // 검체번호
				data[1] = chartNo[i]; // 차트번호
				try {
					data[0] = cns[i].replaceAll("N:","").substring(0, 8); // 처방번호
				} catch (Exception e) {
					data[0] = "";
				}
				//20190410 양태용 31453 병원만 hcdh 처방번호 잘라서
				if ( hosCode[i].trim().equals("31453")) {
					try {
						data[0] = cutHrcvDateNumber(cns[i])[0]; // 처방번호
					} catch (Exception eee) {
						data[0] = ""; // 처방번호
					}
				}

				data[3] = patName[i]; // 환자명
				data[4] = clientInspectCode[i].trim(); // 검사코드
				data[5] = ""; // 결과(문자)
				data[6] = ""; // 결과(문장)
				data[7] = ""; // 참고치
				data[8] = ""; // 단위
				data[11] = ""; // 단위
				data[12] = ""; // 단위
				data[15] = rcvDate[i].trim();
				data[16] = rcvNo[i].trim();
				// !
				data[9] = bdt[i]; // 보고일자

				// if (data[3].toString().trim().equals("최점복")) {
				// System.out.println("");
				// }
				try {
					data[10] = Common.getDateFormat(cns[i].trim().substring(9)).substring(2); // 접수일자
				} catch (Exception e) {
					data[10] = "";
				}
				// !
				String curDate = "";
				String curNo = "";

				// !
				//청구성섬 8146900,8146902 건너뛰도록함 
				if(hosCode[i].trim().equals("10005")&& (inspectCode[i].trim().equals("8146900")||inspectCode[i].trim().equals("8146902")))
				{
					continue;
				}

				if ((hosCode[i].trim().equals("29506")||hosCode[i].trim().equals("30011") ) 
						&& (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503"))) 
				{ // 단독
					continue;
				}
				
				if (hosCode[i].trim().equals("24068") && (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")
						 || inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125203") || inspectCode[i].trim().equals("7125900") || inspectCode[i].trim().equals("2106100")
						 || inspectCode[i].trim().equals("7124600"))) 
				{ // 단독
					continue;
				}
				
				
				if ((hosCode[i].trim().equals("29495")) && (
						inspectCode[i].trim().equals("1110100") || inspectCode[i].trim().equals("1110102")
						|| inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503")
						||inspectCode[i].trim().equals("7125200") || inspectCode[i].trim().equals("7125201") || inspectCode[i].trim().equals("7125203")
						||inspectCode[i].trim().equals("7125900") || inspectCode[i].trim().equals("7125902")
						)) 
				{ // 단독
					continue;
				}
				
				

				
				//군산차병원 Hbac1값만
				if (hosCode[i].trim().equals("13169") && (inspectCode[i].trim().equals("0009500") || inspectCode[i].trim().equals("0009502") || inspectCode[i].trim().equals("0009503"))) 
				{ // 단독
					continue;
				}
				//
				if (inspectCode[i].trim().length() == 7	&& // 부속이면서
						(hosCode[i].trim().equals("28756") )
						&& inspectCode[i].trim().substring(0, 5).equals("31001")) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} 	
				
				
				
				if (inspectCode[i].trim().length() == 7	&& // 부속이면서
						( hosCode[i].trim().equals("31453")) 
						&& (inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("72227")
								|| inspectCode[i].trim().substring(0, 5).equals("21677") || inspectCode[i].trim().substring(0, 5).equals("71259")
								|| inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("00911")
								|| inspectCode[i].trim().substring(0, 5).equals("00323") || inspectCode[i].trim().substring(0, 5).equals("21065")
								|| inspectCode[i].trim().substring(0, 5).equals("72206") || inspectCode[i].trim().substring(0, 5).equals("00901")
								|| inspectCode[i].trim().substring(0, 5).equals("71249") || inspectCode[i].trim().substring(0, 5).equals("71246")
								|| inspectCode[i].trim().substring(0, 5).equals("72229") || inspectCode[i].trim().substring(0, 5).equals("21683")
								|| inspectCode[i].trim().substring(0, 5).equals("72247") || inspectCode[i].trim().substring(0, 5).equals("11301")
								|| inspectCode[i].trim().substring(0, 5).equals("72245") || inspectCode[i].trim().substring(0, 5).equals("00209")
								|| inspectCode[i].trim().substring(0, 5).equals("21380") || inspectCode[i].trim().substring(0, 5).equals("71311")
								|| inspectCode[i].trim().substring(0, 5).equals("72240") || inspectCode[i].trim().substring(0, 5).equals("72182")
								|| inspectCode[i].trim().substring(0, 5).equals("00309") || inspectCode[i].trim().substring(0, 5).equals("71249")
								|| inspectCode[i].trim().substring(0, 5).equals("00938") || inspectCode[i].trim().substring(0, 5).equals("72241")
								)) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} 	
				
				
				
				if (inspectCode[i].trim().length() == 7	&& // 부속이면서
						(hosCode[i].trim().equals("29495"))&&(inspectCode[i].trim().substring(0, 5).equals("11301")
								||inspectCode[i].trim().substring(0, 5).equals("00901")
								||inspectCode[i].trim().substring(0, 5).equals("72205")
								||inspectCode[i].trim().substring(0, 5).equals("72205")
								||inspectCode[i].trim().substring(0, 5).equals("11052")
								||inspectCode[i].trim().substring(0, 5).equals("00804")
								||inspectCode[i].trim().substring(0, 5).equals("72182"))
						) 
				{
					if(inspectCode[i].trim().substring(0, 5).equals("11301")||inspectCode[i].trim().substring(0, 5).equals("11052")){
						data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t";
						data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) ;
						data[5] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t" ;
							if (++i == cnt)
								break;
						}
						i--;
						
					}else{
						data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
						data[5] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
						
					}
				} 
				
				// 20160408 Gram stain 담당자가 03  항목만 노출 요청 ( 다른항목 불필요 하다 하여 ) -> 단문 변경  
				 //|| inspectCode[i].trim().substring(0, 5).equals("71252")
				if (inspectCode[i].trim().length() == 7	&& // 부속이면서
						(hosCode[i].trim().equals("27772")||hosCode[i].trim().equals("29506")||hosCode[i].trim().equals("30011") || hosCode[i].trim().equals("29495") )&&
						(inspectCode[i].trim().substring(0, 5).equals("31001"))
						) 
				{
					
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{	
						
						//20160408 Gram stain 담당자가 00 01 12 항목만 노출 요청 ( 다른항목 불필요 하다 하여 )
//						if(inspectCode[i].trim().substring(0, 5).equals("31001")&&!(inspectCode[i].trim().equals("3100100")||inspectCode[i].trim().equals("3100101")||inspectCode[i].trim().equals("3100112"))){
//							if (++i == cnt)break;
//							
//							continue;
//						}
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						
						if (++i == cnt)
							break;
					}
					i--;
				} 
				
				//
				if (inspectCode[i].trim().length() == 7	&& // 부속이면서
						hosCode[i].trim().equals("23948")&& inspectCode[i].trim().substring(0, 5).equals("31001")) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				}
				
				
				if (inspectCode[i].trim().length() == 7	&& // 부속이면서
						hosCode[i].trim().equals("19339") 
						&& ( inspectCode[i].trim().substring(0, 5).equals("11052")
								|| inspectCode[i].trim().substring(0, 5).equals("00690") || inspectCode[i].trim().substring(0, 5).equals("00691")
								|| inspectCode[i].trim().substring(0, 5).equals("31001"))) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				}
				
				
				
				// 백제병원 20170516 양태용
				if ( hosCode[i].trim().equals("30484")&&
						(inspectCode[i].trim().substring(0, 5).equals("71252")||inspectCode[i].trim().substring(0, 5).equals("21649")
								||inspectCode[i].trim().substring(0, 5).equals("21650")||inspectCode[i].trim().substring(0, 5).equals("71259")
								||inspectCode[i].trim().substring(0, 5).equals("71032")||inspectCode[i].trim().substring(0, 5).equals("72182")
								||inspectCode[i].trim().substring(0, 5).equals("72183")||inspectCode[i].trim().substring(0, 5).equals("72189")
								||inspectCode[i].trim().substring(0, 5).equals("72242")||inspectCode[i].trim().substring(0, 5).equals("00323")
								||inspectCode[i].trim().substring(0, 5).equals("00320")||inspectCode[i].trim().substring(0, 5).equals("72194")
								||inspectCode[i].trim().substring(0, 5).equals("72227")||inspectCode[i].trim().substring(0, 5).equals("72236")
								||inspectCode[i].trim().substring(0, 5).equals("72229")||inspectCode[i].trim().substring(0, 5).equals("72228")
								||inspectCode[i].trim().substring(0, 5).equals("72230")||inspectCode[i].trim().substring(0, 5).equals("72231")
								||inspectCode[i].trim().substring(0, 5).equals("72232")||inspectCode[i].trim().substring(0, 5).equals("72245")
								||inspectCode[i].trim().substring(0, 5).equals("72233")||inspectCode[i].trim().substring(0, 5).equals("72020")
								||inspectCode[i].trim().substring(0, 5).equals("72234")||inspectCode[i].trim().substring(0, 5).equals("72235")
								)) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} 
				else if ((inspectCode[i].trim().length() == 7	&& // 부속이면서 Hba1c검사가 아닌 검사만 문장형태로출력되도록 군산차병원
						hosCode[i].trim().equals("13169")&&
						(inspectCode[i].trim().substring(0, 5).equals("31059")||inspectCode[i].trim().substring(0, 5).equals("31001")
								||inspectCode[i].trim().substring(0, 5).equals("71252")||inspectCode[i].trim().substring(0, 5).equals("71259")
								||inspectCode[i].trim().substring(0, 5).equals("72241")||inspectCode[i].trim().substring(0, 5).equals("11052")
								||inspectCode[i].trim().substring(0, 5).equals("11052")||inspectCode[i].trim().substring(0, 5).equals("72242")))) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} 
				//20200116 33262병원 11301만 요청  병원측 일괄 장문 정리가 안되어 일단 개벼로 해달라함 추후 요청건수 확정 되면 분기 처리 예정 
				else if (inspectCode[i].trim().length() == 7	&& // MS재건병원
						(hosCode[i].trim().equals("19159") || hosCode[i].trim().equals("33262"))
						&& (inspectCode[i].trim().substring(0, 5).equals("31001")||inspectCode[i].trim().substring(0, 5).equals("00095")||inspectCode[i].trim().substring(0, 5).equals("11301"))) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
								+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} else if (inspectCode[i].trim().length() == 7 && isHBV(inspectCode[i].trim().substring(0, 5))) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t" + "관  련  약  제";
					data[6] += getDivide() + "\r\n"	+ appendBlanks(inspectName[i], 30) + "\t"+ appendBlanks(result[i], 21) + "\t"
							+ getResultHBV(inspectCode[i].trim());
					data[5] = ""; // 문자결과
					data[5] = ""; // 참고치
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += getDivide() + "\r\n"+ appendBlanks(inspectName[i], 30) + "\t"+ appendBlanks(result[i], 21) + "\t"
								+ getResultHBV(inspectCode[i].trim());
						if (++i == cnt)
							break;
					}
					i--;
					// data[36] = data[35];
				} else if (isMAST(inspectCode[i].trim().substring(0, 5))) {
					Vector vmast = new Vector();
					data[6] =  appendBlanks("검사항목", 30) + appendBlanks("CLASS", 20) + appendBlanks("검사항목", 30) + appendBlanks("CLASS", 20);
					data[6] += getDivide() + "\r\n";
					data[5] = "";
					vmast.addElement(appendBlanks(inspectName[i].trim(), 30) + appendBlanks(result[i].trim(), 20));   
					// !
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						try {
							vmast.addElement(appendBlanks(inspectName[i], 26)+ appendBlanks(result[i++].substring(0, 1),8));
							if (inspectCode[i].trim().substring(0, 5).equals("00673")|| //
									inspectCode[i].trim().substring(0, 5).equals("00674")) 
							{
								vmast.addElement(appendBlanks(inspectName[i],26)+ appendBlanks(result[i++].substring(0,1), 8));
							} else {
								break;
							}
						} catch (Exception e) {
						}
						if (i >= cnt)
							break;
					}
					i--;
					data[6] = getResultMAST(data[6].toString(), vmast)+ getDivide() + "\r\n" + getMastRemark();
				} 
				else if (isMAST_Two(inspectCode[i].trim().substring(0, 5)) && (!hosCode[i].trim().equals("30487") && !hosCode[i].trim().equals("19339"))) {
					Vector vmast = new Vector();
					data[6] =  appendBlanks("검사항목", 30) + appendBlanks("CLASS", 20) + appendBlanks("검사항목", 30) + appendBlanks("CLASS", 20);
					data[6] += getDivide() + "\r\n";
					data[5] = "";
					vmast.addElement(appendBlanks(inspectName[i].trim(), 30) + appendBlanks(result[i].trim(), 20));   

					curDate = rcvDate[i];  
					curNo = rcvNo[i];						
					String thisTimeCode = inspectCode[i++].trim().substring(0, 5);
						for (;curDate.equals(rcvDate[i].trim()) && curNo.equals(rcvNo[i].trim());) {
						try {
//20160422 마스트 결과 수정작업 - 왜 써있는지 몰라 지움
//							vmast.addElement(appendBlanks(inspectName[i], 30) + appendBlanks(result[i++].trim(), 20));
						
								if (inspectCode[i].trim().substring(0, 5).equals("00683")||//  
								inspectCode[i].trim().substring(0, 5).equals("00684")
								|| inspectCode[i].trim().substring(0, 5).equals( "00687") || inspectCode[i].trim().substring(0, 5).equals( "00688") || inspectCode[i].trim().substring(0, 5).equals( "00689")
								 || inspectCode[i].trim().substring(0, 5).equals( "00690") || inspectCode[i].trim().substring(0, 5).equals( "00691")
								){
									

									//20160422 마스트 결과 수정작업
//									vmast.addElement(appendBlanks(inspectName[i], 30) + appendBlanks(result[i++].trim(), 20));
									 if(isMastPrint(inspectCode[i].trim())) 
											vmast.addElement(appendBlanks(inspectName[i], 40) + appendBlanks(result[i++].trim(), 20));
			                           else
			                              i++;
								
								}else{
								break;
							}   
						} catch (Exception e) {
						}
						
						if (i >= cnt)
							break;
					}
					i--;
					data[6] = getResultMAST_Two(data[6].toString(), vmast)+ getDivide() + "\r\n" + getMastRemark();
				} 
				else if (inspectCode[i].trim().length() == 7&& hosCode[i].trim().equals("23429")
						&& inspectCode[i].trim().substring(0, 5).equals("71252")) 
				{
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						if (inspectCode[i].trim().substring(5, 7).equals("02")) {
							data[6] = "";
							data[5] = result[i];
						}
						if (++i == cnt)
							break;
					}
					i--;
				}else if (inspectCode[i].trim().length() == 7&& hosCode[i].trim().equals("23429")
						&& inspectCode[i].trim().substring(0, 5).equals("71259")) 
				{
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						if (inspectCode[i].trim().substring(5, 7).equals("01")) {
							data[6] = "";
							data[5] = result[i];
						}
						if (++i == cnt)
							break;
					}
					i--;
				}else if (inspectCode[i].trim().length() == 7
						&& // 부속이면서
						hosCode[i].trim().equals("29506")
						&& (inspectCode[i].trim().substring(0, 5).equals("71297")||inspectCode[i].trim().substring(0, 5).equals("72192")
								||inspectCode[i].trim().substring(0, 5).equals("71313"))) {
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"
							+ appendBlanks("결    과", 21) + " \t"
							+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"
							+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						data[6] += "\r\n"
								+ appendBlanks(inspectName[i], 30)
								+ "\t"
								+ appendBlanks(result[i], 21)
								+ "\t"
								+ getReferenceValue(
										new String[] { inspectCode[i], lang[i],
												history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				}else if (inspectCode[i].trim().length() == 7
						&& // 부속이면서
						hosCode[i].trim().equals("23429")
						&& inspectCode[i].trim().substring(0, 5)
								.equals("71245")) {
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"
							+ appendBlanks("결    과", 21) + " \t"
							+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"
							+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						data[6] += "\r\n"
								+ appendBlanks(inspectName[i], 30)
								+ "\t"
								+ appendBlanks(result[i], 21)
								+ "\t"
								+ getReferenceValue(
										new String[] { inspectCode[i], lang[i],
												history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} else if (inspectCode[i].trim().length() == 7 && // 부속이면서
						!hosCode[i].trim().equals("10005") && // 청구성심병원 제외
						(//
						hosCode[i].trim().equals("11760") || //
						hosCode[i].trim().equals("19950") //
						)) {
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"
							+ appendBlanks("결    과", 21) + " \t"
							+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"
							+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim()
							.substring(0, 5); thisTimeCode
							.equals(inspectCode[i].trim().substring(0, 5))
							&& curDate.equals(rcvDate[i].trim())
							&& curNo.equals(rcvNo[i].trim());) {
						data[6] += "\r\n"
								+ appendBlanks(inspectName[i], 30)
								+ "\t"
								+ appendBlanks(result[i], 21)
								+ "\t"
								+ getReferenceValue(
										new String[] { inspectCode[i], lang[i],
												history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} else if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("10005") && // 청구성심병원 일때
						(inspectCode[i].trim().substring(0, 5).equals("11002")||inspectCode[i].trim().substring(0, 5).equals("71251") 
								|| inspectCode[i].trim().substring(0, 5).equals("00911") ||inspectCode[i].trim().substring(0, 5).equals("00941") 
								|| inspectCode[i].trim().substring(0, 5).equals("41133") || inspectCode[i].trim().substring(0, 5).equals("71252") 
								|| inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("71259") 
								|| inspectCode[i].trim().substring(0, 5).equals("71177") || inspectCode[i].trim().substring(0, 5).equals("71182") 
								|| inspectCode[i].trim().substring(0, 5).equals("71209") || inspectCode[i].trim().substring(0, 5).equals("72059") 
								|| inspectCode[i].trim().substring(0, 5).equals("31005") || inspectCode[i].trim().substring(0, 5).equals("00928") 
								|| inspectCode[i].trim().substring(0, 5).equals("71256") || inspectCode[i].trim().substring(0, 5).equals("71203")
								|| inspectCode[i].trim().substring(0, 5).equals("71202") || inspectCode[i].trim().substring(0, 5).equals("72067") 
								|| inspectCode[i].trim().substring(0, 5).equals("11052") || inspectCode[i].trim().substring(0, 5).equals("11026")
								|| inspectCode[i].trim().substring(0, 5).equals("72189") || inspectCode[i].trim().substring(0, 5).equals("72183")
								|| inspectCode[i].trim().substring(0, 5).equals("72242") || inspectCode[i].trim().substring(0, 5).equals("72018")
								|| inspectCode[i].trim().substring(0, 5).equals("81469") || inspectCode[i].trim().substring(0, 5).equals("71297")
								|| inspectCode[i].trim().substring(0, 5).equals("72206") || inspectCode[i].trim().substring(0, 5).equals("71298")
								|| inspectCode[i].trim().substring(0, 5).equals("72194") || inspectCode[i].trim().substring(0, 5).equals("71311") 
								|| inspectCode[i].trim().substring(0, 5).equals("21380") || inspectCode[i].trim().substring(0, 5).equals("21677") 
								|| inspectCode[i].trim().substring(0, 5).equals("72227") || inspectCode[i].trim().substring(0, 5).equals("72228") 
								|| inspectCode[i].trim().substring(0, 5).equals("72229") || inspectCode[i].trim().substring(0, 5).equals("72230") 
								|| inspectCode[i].trim().substring(0, 5).equals("72231") || inspectCode[i].trim().substring(0, 5).equals("72232") 
								|| inspectCode[i].trim().substring(0, 5).equals("72233") || inspectCode[i].trim().substring(0, 5).equals("72234") 
								|| inspectCode[i].trim().substring(0, 5).equals("72235") || inspectCode[i].trim().substring(0, 5).equals("72236") 
								|| inspectCode[i].trim().substring(0, 5).equals("72237") || inspectCode[i].trim().substring(0, 5).equals("21638")
								|| inspectCode[i].trim().substring(0, 5).equals("71311") || inspectCode[i].trim().substring(0, 5).equals("71313")
								|| inspectCode[i].trim().substring(0, 5).equals("72020") || inspectCode[i].trim().substring(0, 5).equals("72240") 
								|| inspectCode[i].trim().substring(0, 5).equals("21683") 
								)) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))	&& curDate.equals(rcvDate[i].trim())	&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)	+ "\t"+ appendBlanks(result[i], 21)	+ "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				} else if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("29883") && // 청구성심병원 일때
						( inspectCode[i].trim().substring(0, 5).equals("21677"))) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))	&& curDate.equals(rcvDate[i].trim())	&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)	+ "\t"+ appendBlanks(result[i], 21)	+ "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				}else if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("32998") && // 청구성심병원 일때
						(  inspectCode[i].trim().substring(0, 5).equals("72242") || inspectCode[i].trim().substring(0, 5).equals("72020")
								|| inspectCode[i].trim().substring(0, 5).equals("72245")|| inspectCode[i].trim().substring(0, 5).equals("31001")
								|| inspectCode[i].trim().substring(0, 5).equals("72182")|| inspectCode[i].trim().substring(0, 5).equals("72241")
								|| inspectCode[i].trim().substring(0, 5).equals("21677")|| inspectCode[i].trim().substring(0, 5).equals("11052")
								|| inspectCode[i].trim().substring(0, 5).equals("72189")|| inspectCode[i].trim().substring(0, 5).equals("72194")
								|| inspectCode[i].trim().substring(0, 5).equals("72227")|| inspectCode[i].trim().substring(0, 5).equals("72237")
								|| inspectCode[i].trim().substring(0, 5).equals("72261")
								)) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))	&& curDate.equals(rcvDate[i].trim())	&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)	+ "\t"+ appendBlanks(result[i], 21)	+ "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
						if (++i == cnt)
							break;
					}
					i--;
				}else if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("33118") && 
						( inspectCode[i].trim().substring(0, 5).equals("31001") || inspectCode[i].trim().substring(0, 5).equals("72185") 
								|| inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("71259")
								|| inspectCode[i].trim().substring(0, 5).equals("41081") || inspectCode[i].trim().substring(0, 5).equals("41075")
								|| inspectCode[i].trim().substring(0, 5).equals("41114") || inspectCode[i].trim().substring(0, 5).equals("11052")
								|| inspectCode[i].trim().substring(0, 5).equals("72189") || inspectCode[i].trim().substring(0, 5).equals("72242")
								|| inspectCode[i].trim().substring(0, 5).equals("00304") || inspectCode[i].trim().substring(0, 5).equals("72020")
								|| inspectCode[i].trim().substring(0, 5).equals("72244") || inspectCode[i].trim().substring(0, 5).equals("81469")
								|| inspectCode[i].trim().substring(0, 5).equals("00601")
								)) 
					{
					//	data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치";	20191113 김영상과장 요청
						data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"+ appendBlanks(result[i], 21) + "\t" + data[7];
						data[5] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))	&& curDate.equals(rcvDate[i].trim())	&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)	+ "\t"+ appendBlanks(result[i], 21)	+ "\t"+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}else if (inspectCode[i].trim().length() == 7 && //	
						(!hosCode[i].trim().equals("10005") && !hosCode[i].trim().equals("13169") && !hosCode[i].trim().equals("30950")
						&& !hosCode[i].trim().equals("30487")//20190405 서울 대정병원 예외 처리 요청 
						&& !hosCode[i].trim().equals("23948") && !hosCode[i].trim().equals("24068") && !hosCode[i].trim().equals("29495") 
					 	&& !hosCode[i].trim().equals("29506") &&!hosCode[i].trim().equals("32869") && !hosCode[i].trim().equals("30011") //32869 늘찬병원 이전코드 = 29848
					 	&& !hosCode[i].trim().equals("27772") && !hosCode[i].trim().equals("25177") && !hosCode[i].trim().equals("19339")
					 	&& !hosCode[i].trim().equals("33118") && !hosCode[i].trim().equals("32998"))
					 			&& (inspectCode[i].trim().substring(0, 5).equals("00095") 
					 	|| inspectCode[i].trim().substring(0, 5).equals("21061") 
					 	|| inspectCode[i].trim().substring(0, 5).equals("11052"))) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"+ "참    고    치"; 
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"+ appendBlanks(result[i], 21) + "\t" + data[7];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
				{
						String arrRefer[] =getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim().split("\r\n");
						String strRefer = arrRefer[0];
						for(int r=1;r < arrRefer.length;r++)
						{
							strRefer = strRefer+"\r\n"+insertBlanks(arrRefer[r].toString(), 55);
						}
						
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"+ strRefer;
						if (++i == cnt)
							break;
					}
					i--;
				}
				
				else if (inspectCode[i].trim().length() == 7 
						&& ( hosCode[i].trim().equals("10067") || hosCode[i].trim().equals("28896"))
						&& inspectCode[i].trim().substring(0, 5).equals("72185")) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) +  appendBlanks("결    과", 21) ;
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + result[i];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+  result[i];
						if (++i == cnt)
							break;
					}
					i--;
				}
				else if (inspectCode[i].trim().length() == 7 && hosCode[i].trim().equals("26569") && inspectCode[i].trim().substring(0, 5).equals("72185")) 
				{
					data[6] = appendBlanks("검  사  명 ", 30) +  appendBlanks("결    과", 21) ;
					data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + result[i];
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
					{
						data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+  result[i];
						if (++i == cnt)
							break;
					}
					i--;
				}
				
			
				else if (resultType[i].trim().equals("C")) {
					
					
					
					data[5] = result[i]; // 문자결과	
					remark[0] = inspectCode[i];
					remark[1] = lang[i];
					remark[2] = history[i];
					remark[3] = sex[i];
					
	
					
					try {
						if(hosCode[i].trim().equals("10005")||hosCode[i].trim().equals("30950"))
						{
							data[7] =getReferenceValue(remark);
							data[8] = getReferenceValue(remark);
							
						}
						else
						{
							data[7] = Common.getDataCut(getReferenceValue(remark),"     ")[0];
							data[8] = Common.getDataCut(getReferenceValue(remark),"     ")[1];
							
						}
					} catch (Exception e) {
					}
					
	
					//20190605 25177 평택 서울제일병원 71252 만 장문으로 처리 해 달라고 했으나 31001 도 추가로 묶어버림 문제되면 따로 만들어야함
					if (inspectCode[i].trim().length() == 7	&& // 부속이면서  ***20160816 양태용 추가****
							(hosCode[i].trim().equals("32869")||hosCode[i].trim().equals("25177"))
							&& (inspectCode[i].trim().substring(0, 5).equals("71252") || inspectCode[i].trim().substring(0, 5).equals("31001"))) 
					{
						data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
						data[5] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					
					if (inspectCode[i].trim().length() == 7	&& // 부속이면서
							( hosCode[i].trim().equals("31336"))
							&& (inspectCode[i].trim().substring(0, 5).equals("31001")||inspectCode[i].trim().substring(0, 5).equals("72194"))) 
					{
						data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
						data[5] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					} 	
					
					if (inspectCode[i].trim().length() == 7	
							&& (hosCode[i].trim().equals("30174")||hosCode[i].trim().equals("32626")) 
							&& (!inspectCode[i].trim().substring(0, 5).equals("71297")
									&& !inspectCode[i].trim().substring(0, 5).equals("00628")
									&& !inspectCode[i].trim().substring(0, 5).equals("00083")
									&& !inspectCode[i].trim().substring(0, 5).equals("00948")
									&& !inspectCode[i].trim().substring(0, 5).equals("00405")
									&& !inspectCode[i].trim().substring(0, 5).equals("05028")
									&& !inspectCode[i].trim().substring(0, 5).equals("05029")
									&& !inspectCode[i].trim().substring(0, 5).equals("21264"))) 
					{
						data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
						data[5] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					
					if (inspectCode[i].trim().length() == 7	
							&& (hosCode[i].trim().equals("30487")) 
							&& (inspectCode[i].trim().substring(0, 5).equals("11052")|| inspectCode[i].trim().substring(0, 5).equals("31001")
									 || inspectCode[i].trim().substring(0, 5).equals("31005")|| inspectCode[i].trim().substring(0, 5).equals("00690")
									 || inspectCode[i].trim().substring(0, 5).equals("00691"))) 
					{
						data[6] = appendBlanks("검  사  명 ", 30) + "\t"+ appendBlanks("결    과", 21) + " \t"	+ "참    고    치";
						data[6] += "\r\n" + appendBlanks(inspectName[i], 30) + "\t"	+ appendBlanks(result[i], 21) + "\t" + data[7];
						data[5] = "";
						curDate = rcvDate[i];
						curNo = rcvNo[i];
						for (String thisTimeCode = inspectCode[i++].trim().substring(0, 5); thisTimeCode.equals(inspectCode[i].trim().substring(0, 5))
								&& curDate.equals(rcvDate[i].trim())&& curNo.equals(rcvNo[i].trim());) 
						{
							data[6] += "\r\n"+ appendBlanks(inspectName[i], 30)+ "\t"+ appendBlanks(result[i], 21)+ "\t"
									+ getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
							if (++i == cnt)
								break;
						}
						i--;
					}
					
					
					// !
					st = new StringTokenizer(unit[i], ",");
					tokenCnt = st.countTokens();
					
					if (tokenCnt == 2) {
						data[8] = "";	
					} else {
						for (int j = 0; j < tokenCnt; j++) {
							if (j == 0)
								data[11] = st.nextToken();
							if (j == 1)
								data[12] = st.nextToken();
							if (j == 2)
								data[8] = st.nextToken();
							
						
							if (data[8].toString().trim().equals("index")) {
								data[8] = "";
								data[11] = "";
								data[12] = "";
							}
						}
					}
				} else {
					data[5] = ""; // 문자결과
					if (hosCode[i].trim().equals("10005")) { // 청구성심병원 일때
						if (Integer.parseInt(inspectCode[i].trim()) > 71031&& Integer.parseInt(inspectCode[i].trim()) < 71049) {
							data[6] = "";
						} else {
							data[6] = getTextResultValue(hosCode[i],rcvDate[i], rcvNo[i], inspectCode[i]); // 문장결과
						}
					} else {
						data[6] = getTextResultValue(hosCode[i], rcvDate[i],rcvNo[i], inspectCode[i]); // 문장결과
					}
				}

				
				//20170330 청구성심 리마크 중복으로 나와 해당 부분 주석 처리 및 부속검사에 리마크 찍히는 문제도 있어 해당부분 제외 양태용
//				if (hosCode[i].trim().equals("10005")) { // 청구성심병원 일때
//
//					if (data[15].toString().equals("20120824")&& data[16].toString().equals("42767")) {
//					} else
//						data[6] = data[6]+ "\r\n\r\n"+ getReamrkValue(hosCode[i], rcvDate[i],rcvNo[i], rmkCode[i]);
//				}
				
				//단문결과 리마크 포함하여 장문으로 처리
				if (hosCode[i].trim().equals("10005") 
						&& (inspectCode[i].trim().substring(0, 5).equals("71330")||inspectCode[i].trim().substring(0, 5).equals("71331"))) { // 청구성심병원 일때
					
						data[6] = data[5] + data[6];
						data[5] ="";
				}
				
				if (inspectCode[i].trim().substring(0, 5).equals("11026")|| inspectCode[i].trim().substring(0, 5).equals("11052")) {
					data[6] = data[6]+ "\r\n\r\n"+ getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i],rmkCode[i]);
					// System.out.println("");
				} else if (rmkCode[i].trim().length() > 0)
					try {
						if (!kumdata[0].trim().equals(data[15].trim())|| !kumdata[1].trim().equals(data[16].trim())|| !kumdata[2].trim().equals(remarkCode)) {
							remarkCode = rmkCode[i].trim();
							if (inspectCode[i].trim().substring(0, 5).equals("11026")|| inspectCode[i].trim().substring(0, 5).equals("11052"))
								data[6] = data[6]+ "\r\n\r\n"+ getReamrkValue(hosCode[i],rcvDate[i], rcvNo[i],rmkCode[i]);
							else if(hosCode[i].trim().equals("28462")){
								data[6] = data[6];
							}
							else
								data[6] = data[6]+ "\r\n\r\n"+ getReamrkValue(hosCode[i],rcvDate[i], rcvNo[i],rmkCode[i]);
							kumdata[0] = data[15].trim();
							kumdata[1] = data[16].trim();
							kumdata[2] = remarkCode;
						}
					} catch (Exception _ex) {
					}
				else
					remarkCode = "";
				if (data[15].toString().equals("20120824")
						&& data[16].toString().equals("42767")) {
					data[6] = "";
				}
				// !
				
							
				if (hosCode[i].trim().equals("10005")) { // 청구성심병원 일때
					if (Integer.parseInt(inspectCode[i].trim()) > 71031	&& Integer.parseInt(inspectCode[i].trim()) < 71049) {
						data[6] = "";
					}
					if (inspectCode[i].trim().equals("21296")|| inspectCode[i].trim().equals("11221")) {
						data[6] = "";
					}
				}
				if (resultType[i].trim().equals("C")
						&& hosCode[i].trim().equals("23429")) {
					try {

						// 춘애병원
						if (result[i].trim().toUpperCase().substring(0, 8).equals("NEGATIVE")&& result[i].trim().toUpperCase().indexOf("NEGATIVE") > -1&& result[i].trim().length() > 8) 
						{
							data[5] = data[5].trim().substring(0, 8) + " ("+ data[5].trim().substring(8).trim() + ")"; // 문자결과
						} 
						else if (result[i].trim().toUpperCase().substring(0,8).equals("POSITIVE")&& result[i].trim().toUpperCase().indexOf("POSITIVE") > -1	&& result[i].trim().length() > 8)
						{
							data[5] = data[5].trim().substring(0, 8) + " (" + data[5].trim().substring(8).trim() + ")"; // 문자결과
						}
					} catch (Exception rr) {
					}
				}
				else if(hosCode[i].trim().equals("10005") &&inspectCode[i].trim().equals("31056"))
				{ 
					data[6]="";
				}
				if (hosCode[i].trim().equals("24068") 
					   	&& (inspectCode[i].trim().substring(0, 5).equals("00673") 
								||inspectCode[i].trim().substring(0, 5).equals("00674")//
								||inspectCode[i].trim().substring(0, 5).equals("00683")//
								||inspectCode[i].trim().substring(0, 5).equals("00684")//
								|| inspectCode[i].trim().substring(0, 5).equals( "00687") || inspectCode[i].trim().substring(0, 5).equals( "00688") || inspectCode[i].trim().substring(0, 5).equals( "00689")
								|| inspectCode[i].trim().substring(0, 5).equals( "00690") || inspectCode[i].trim().substring(0, 5).equals( "00691")
								||inspectCode[i].trim().substring(0, 5).equals("11052")
								||inspectCode[i].trim().substring(0, 5).equals("00309"))){
					data[6] = "별지보고";
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					}
				if ((hosCode[i].trim().equals("30174")||hosCode[i].trim().equals("32626")) 
					   	&& (inspectCode[i].trim().substring(0, 5).equals("00690")||inspectCode[i].trim().substring(0, 5).equals("00691")
					   			||inspectCode[i].trim().substring(0, 5).equals("72182")||inspectCode[i].trim().substring(0, 5).equals("72241")
					   			||inspectCode[i].trim().substring(0, 5).equals("72218")||inspectCode[i].trim().substring(0, 5).equals("72205")
					   			||inspectCode[i].trim().substring(0, 5).equals("00309")||inspectCode[i].trim().substring(0, 5).equals("72242")
					   			||inspectCode[i].trim().substring(0, 5).equals("80014")||inspectCode[i].trim().substring(0, 5).equals("80015")
					   			||inspectCode[i].trim().substring(0, 5).equals("80016")||inspectCode[i].trim().substring(0, 5).equals("80017")
					   			||inspectCode[i].trim().substring(0, 5).equals("80018")||inspectCode[i].trim().substring(0, 5).equals("80029")
					   			||inspectCode[i].trim().substring(0, 5).equals("80067")||inspectCode[i].trim().substring(0, 5).equals("80069")
					   			||inspectCode[i].trim().substring(0, 5).equals("72206"))){
					data[6] = "별지보고";
					data[5] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
					}
				
//				if (hosCode[i].trim().equals("30484") 
//					   	&& (inspectCode[i].trim().substring(0, 5).equals("72182") 
//								||inspectCode[i].trim().substring(0, 5).equals("72183")//
//								||inspectCode[i].trim().substring(0, 5).equals("72185")
//								||inspectCode[i].trim().substring(0, 5).equals("72189")//
//								||inspectCode[i].trim().substring(0, 5).equals("72242")//
//								||inspectCode[i].trim().substring(0, 5).equals("00323") 
//								||inspectCode[i].trim().substring(0, 5).equals("00320")
//								||inspectCode[i].trim().substring(0, 5).equals("00319")
//								||inspectCode[i].trim().substring(0, 5).equals("71297")
//								||inspectCode[i].trim().substring(0, 5).equals("21677")
//								||inspectCode[i].trim().substring(0, 5).equals("71325")
//								||inspectCode[i].trim().substring(0, 5).equals("21280")
//								||inspectCode[i].trim().substring(0, 5).equals("21281")
//								||inspectCode[i].trim().substring(0, 5).equals("21282")
//								||inspectCode[i].trim().substring(0, 5).equals("21283")
//								||inspectCode[i].trim().substring(0, 5).equals("00309")
//								
//																
//								)){
//					data[5] = "스캔 참조";
//					data[6] = "";
//					data[7] = "";
//					
//					curDate = rcvDate[i];
//					curNo = rcvNo[i];
//					}
//				
				
				//컬쳐&센시 합치기 20170906 양태용 추가 20170901 이후 컬쳐와 센시가 통합되어 하나로 나오도록 처리 
				
				if ((inspectCode[i].trim().equals("31100")||inspectCode[i].trim().equals("31101")
								||inspectCode[i].trim().equals("31102")||inspectCode[i].trim().equals("31103")
								||inspectCode[i].trim().equals("31104")||inspectCode[i].trim().equals("31105")
								||inspectCode[i].trim().equals("31106")||inspectCode[i].trim().equals("31107")
								||inspectCode[i].trim().equals("31108")||inspectCode[i].trim().equals("31109")
								||inspectCode[i].trim().equals("31110")||inspectCode[i].trim().equals("31111")
								||inspectCode[i].trim().equals("31112")||inspectCode[i].trim().equals("31113")
								||inspectCode[i].trim().equals("31114")||inspectCode[i].trim().equals("31115")
								||inspectCode[i].trim().equals("31116")||inspectCode[i].trim().equals("31117")
								||inspectCode[i].trim().equals("31118")||inspectCode[i].trim().equals("31119")
								||inspectCode[i].trim().equals("31120")||inspectCode[i].trim().equals("31121")
								||inspectCode[i].trim().equals("31122")||inspectCode[i].trim().equals("31123")
								||inspectCode[i].trim().equals("31124")||inspectCode[i].trim().equals("31125")
								||inspectCode[i].trim().equals("31126")||inspectCode[i].trim().equals("31127")
								||inspectCode[i].trim().equals("31128"))) {
					
					data[6] = getTextResultValue(hosCode[i],rcvDate[i], rcvNo[i], inspectCode[i]);
			//		data[8] = data[3].trim();
					try {
						if ((inspectCode[i + 1].substring(0, 5).equals("32000")||inspectCode[i + 1].substring(0, 5).equals("32001")
								)
								&& rcvNo[i].equals(rcvNo[i + 1])
								&& rcvDate[i].equals(rcvDate[i + 1])) {
							data[6] = data[6] + "\r\n" +getTextResultValue(hosCode[i],rcvDate[i], rcvNo[i], inspectCode[i + 1]);
			//				data[8] = data[3].trim();
							i++;
							// culture_flag = true;
						} else {
							data[6] = getTextResultValue(hosCode[i],rcvDate[i], rcvNo[i], inspectCode[i]);
			//				data[8] = data[3].trim();
						}
					} catch (Exception e) {
						data[6] = getTextResultValue(hosCode[i],rcvDate[i], rcvNo[i], inspectCode[i]);
			//			data[8] = data[3].trim();
					}
				}
				
				
				
				//백제병원 단답형 리마크 포함해서 장문으로 처리 요청
				
				if (hosCode[i].trim().equals("30484") 
					   	&& (inspectCode[i].trim().substring(0, 5).equals("72039") || inspectCode[i].trim().substring(0, 5).equals("71046") 
					   			|| inspectCode[i].trim().substring(0, 5).equals("71034") || inspectCode[i].trim().substring(0, 5).equals("72227") 
					   			|| inspectCode[i].trim().substring(0, 5).equals("72236") 
								)){
					
					data[6] = data[5] + data[6];
					data[5] = "";
					
					}
				
				//건양대학교부여병원 일부검사 장문 처리
				
				if (hosCode[i].trim().equals("19339") 
					   	&& (inspectCode[i].trim().substring(0, 5).equals("31019") || inspectCode[i].trim().substring(0, 5).equals("31059") 
					   			|| inspectCode[i].trim().substring(0, 5).equals("31077") || inspectCode[i].trim().substring(0, 5).equals("31072"))){
					
					data[6] = data[5] + data[6];
					data[5] = "";
					
					}
				
				
				//20200910 00804 검사 단문쪽 삭제 요청 32998 병원
				if ((hosCode[i].trim().equals("32998")) && (
						inspectCode[i].trim().equals("00804")
						)) 
				{ // 단독
					data[5] = "";
				}
				
				
				//백제병원 단답형 리마크 포함해서 장문으로 처리 요청
				
				if ((hosCode[i].trim().equals("30484")) && ( inspectCode[i].trim().equals("31019") || inspectCode[i].trim().equals("31077") )) {
					if(data[6].indexOf("중　간　결　과　보　고")>=0)
					{
						continue;
					}
					
				}
				
				// 이샘병원 참고치 포함하여 장문 처리 요청 20191212 
				
				if (hosCode[i].trim().equals("33118") 
					   	&& (inspectCode[i].trim().substring(0, 5).equals("41004") || inspectCode[i].trim().substring(0, 5).equals("41092") 
					   			|| inspectCode[i].trim().substring(0, 5).equals("41097") || inspectCode[i].trim().substring(0, 5).equals("41114")
								)){
					
					data[6] = data[5] + "\r\n\r\n" + getReferenceValue(new String[] { inspectCode[i], lang[i],history[i], sex[i] }).trim();
					data[5] = "";
					
					}
				
				if(hosCode[i].trim().equals("27056"))
				{ 
					data[6] = data[6].replaceAll("전문의:", "\r\n전문의:");
				}
				
				// !
				if(hosCode[i].trim().equals("27772")||hosCode[i].trim().equals("29495")||hosCode[i].trim().equals("29506")||hosCode[i].trim().equals("27157")||hosCode[i].trim().equals("30011")){
					data[0]=rcvDate[i];
				}
				
				if ((hosCode[i].trim().equals("31453")) 
						&& (inspectCode[i].trim().equals("71330") || inspectCode[i].trim().equals("71331")  
								|| inspectCode[i].trim().equals("41279") || inspectCode[i].trim().equals("71338"))) 
				{ // 단독
					data[6]= "결과 : " + data[5] + "\r\n<리마크>\r\n" + getReamrkValue(hosCode[i], rcvDate[i], rcvNo[i], rmkCode[i]);
				}
				

				if ((hosCode[i].trim().equals("29495")) && (inspectCode[i].trim().equals("52001"))) 
				{ // 단독
					data[6]= data[6].replaceAll("\r\n\r\n", "\r\n");
				}
				
				if ((hosCode[i].trim().equals("29495")) && (inspectCode[i].trim().equals("61008"))) 
				{ // 단독
					data[6]= data[6].replaceAll("<Non-Gyn Cytology                   >", "<Non-Gyn Cytology>");
				}
  
			// 20180214 ======================================================================================================================================
				if("23429".equals(hosCode[i].trim())){
				String gno = specNo[i].trim();//검체번호
				if("".equals(gno)){
					gno="검체번호";
				}
				
				String hGcd = clientInspectCode[i].trim();
				if("".equals(hGcd)){
					hGcd = inspectCode[i].substring(0, 5).trim();
				}
				
				
				data[13] = "https://www.seegenemedical.com/resultImg/23429/"+chartNo[i].trim()+ "_"+gno.trim()+".JPG";

				}
				
				
				
				if(hosCode[i].trim().equals("30484") 
					   	&& (inspectCode[i].trim().substring(0, 5).equals("00319") 
								||inspectCode[i].trim().substring(0, 5).equals("00320") 
								||inspectCode[i].trim().substring(0, 5).equals("00323")
								||inspectCode[i].trim().substring(0, 5).equals("21638")
								||inspectCode[i].trim().substring(0, 5).equals("71259")
								||inspectCode[i].trim().substring(0, 5).equals("71297") 
								||inspectCode[i].trim().substring(0, 5).equals("72182")
								||inspectCode[i].trim().substring(0, 5).equals("72183")
								||inspectCode[i].trim().substring(0, 5).equals("72185")
								||inspectCode[i].trim().substring(0, 5).equals("72189")
								||inspectCode[i].trim().substring(0, 5).equals("72242")
								||inspectCode[i].trim().substring(0, 5).equals("21280")
								||inspectCode[i].trim().substring(0, 5).equals("21281")
								||inspectCode[i].trim().substring(0, 5).equals("21282")
								||inspectCode[i].trim().substring(0, 5).equals("21283")
								||inspectCode[i].trim().substring(0, 5).equals("00309")
								||inspectCode[i].trim().substring(0, 5).equals("21677")
								||inspectCode[i].trim().substring(0, 5).equals("00304")
								||inspectCode[i].trim().substring(0, 5).equals("00317")
								||inspectCode[i].trim().substring(0, 5).equals("00318")
								||inspectCode[i].trim().substring(0, 5).equals("72020")
								||inspectCode[i].trim().substring(0, 5).equals("72245")
								
								)){
					String gno = specNo[i].trim();//검체번호
					if("".equals(gno)){
						gno="검체번호";
					}
					
					String hGcd = clientInspectCode[i].trim();
					if("".equals(hGcd)){
						hGcd = inspectCode[i].substring(0, 5).trim();
					}
					
					
					data[13] = "https://www.seegenemedical.com/resultImg/30484/"+chartNo[i].trim()+ "_"+gno.trim()+".JPG";
					data[5] = "스캔 참조";
					data[6] = "";
					data[7] = "";
					curDate = rcvDate[i];
					curNo = rcvNo[i];
				}
				
				if("27157".equals(hosCode[i].trim())){
					String gno = specNo[i].trim();//검체번호
					if("".equals(gno)){
						gno="검체번호";
					}
					
					String hGcd = clientInspectCode[i].trim();
					if("".equals(hGcd)){
						hGcd = inspectCode[i].substring(0, 5).trim();
					}
					
					
					data[13] = "https://www.seegenemedical.com/resultImg/27157/"+patName[i].trim()+"_"+chartNo[i].trim()+ "_"+gno.trim()+"_"+hGcd+"_01.JPG";
					
				}
		// 		20180214 ------------------------------------------------------------------------------------------------------------------------------------		
//				if (!isDebug) {
//					//이미지 경로
//					// patName[] specNo[i] clientInspectCode[i].trim() _01
//					
//					if("23429".equals(hosCode[i].trim())){
//						String gno = specNo[i].trim();//검체번호
//						if("".equals(gno)){
//							gno="검체번호";
//						}
//						
//						String hGcd = clientInspectCode[i].trim();
//						if("".equals(hGcd)){
//							hGcd = inspectCode[i].substring(0, 5).trim();
//						}
//						
//						
//						data[13] = "https://www.seegenemedical.com/resultImg/23429/"+chartNo[i].trim()+ "_"+gno.trim()+".JPG";
//						if (cnt == 400
//								&& inspectCode[i].toString().trim().length() == 7 && i == 399)
//						{
//							// ! 사용하는 결과는 11개
//							for (int k = 0; k < 14; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);	}
//						} else {
//							for (int k = 0; k < 14; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);
//							}
//						}
//					}else{
//						if (cnt == 400
//								&& inspectCode[i].toString().trim().length() == 7 && i == 399)
//						{
//							// ! 사용하는 결과는 11개
//							for (int k = 0; k < 13; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);	}
//						} else {
//							for (int k = 0; k < 13; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);
//							}
//						}
//
//					}
//				}
//				if (!isDebug) {
//					//이미지 경로
//					// patName[] specNo[i] clientInspectCode[i].trim() _01
//					
//					if(hosCode[i].trim().equals("30484") 
//						   	&& (inspectCode[i].trim().substring(0, 5).equals("00319") 
//									||inspectCode[i].trim().substring(0, 5).equals("00320") 
//									||inspectCode[i].trim().substring(0, 5).equals("00323")
//									||inspectCode[i].trim().substring(0, 5).equals("21638")
//									||inspectCode[i].trim().substring(0, 5).equals("71259")
//									||inspectCode[i].trim().substring(0, 5).equals("71297") 
//									||inspectCode[i].trim().substring(0, 5).equals("72182")
//									||inspectCode[i].trim().substring(0, 5).equals("72183")
//									||inspectCode[i].trim().substring(0, 5).equals("72185")
//									||inspectCode[i].trim().substring(0, 5).equals("72189")
//									||inspectCode[i].trim().substring(0, 5).equals("72242")
//									||inspectCode[i].trim().substring(0, 5).equals("21280")
//									||inspectCode[i].trim().substring(0, 5).equals("21281")
//									||inspectCode[i].trim().substring(0, 5).equals("21282")
//									||inspectCode[i].trim().substring(0, 5).equals("21283")
//									||inspectCode[i].trim().substring(0, 5).equals("00309")
//									||inspectCode[i].trim().substring(0, 5).equals("21677")
//									||inspectCode[i].trim().substring(0, 5).equals("00304")
//									||inspectCode[i].trim().substring(0, 5).equals("00317")
//									||inspectCode[i].trim().substring(0, 5).equals("00318")
//									)){
//						String gno = specNo[i].trim();//검체번호
//						if("".equals(gno)){
//							gno="검체번호";
//						}
//						
//						String hGcd = clientInspectCode[i].trim();
//						if("".equals(hGcd)){
//							hGcd = inspectCode[i].substring(0, 5).trim();
//						}
//						
//						
//						data[13] = "https://www.seegenemedical.com/resultImg/30484/"+chartNo[i].trim()+ "_"+gno.trim()+".JPG";
//						data[5] = "스캔 참조";
//						data[6] = "";
//						data[7] = "";
//						curDate = rcvDate[i];
//						curNo = rcvNo[i];
//						
//						if (cnt == 400
//								&& inspectCode[i].toString().trim().length() == 7 && i == 399)
//						{
//							// ! 사용하는 결과는 11개
//							for (int k = 0; k < 14; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);	}
//						} else {
//							for (int k = 0; k < 14; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);
//							}
//						}
//					}else{
//						if (cnt == 400
//								&& inspectCode[i].toString().trim().length() == 7 && i == 399)
//						{
//							// ! 사용하는 결과는 11개
//							for (int k = 0; k < 13; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);	}
//						} else {
//							for (int k = 0; k < 13; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);
//							}
//						}
//
//					}
//				}
//				
//				if (!isDebug) {
//					//이미지 경로
//					// patName[] specNo[i] clientInspectCode[i].trim() _01
//					
//					if("27157".equals(hosCode[i].trim())){
//						String gno = specNo[i].trim();//검체번호
//						if("".equals(gno)){
//							gno="검체번호";
//						}
//						
//						String hGcd = clientInspectCode[i].trim();
//						if("".equals(hGcd)){
//							hGcd = inspectCode[i].substring(0, 5).trim();
//						}
//						
//						
//						data[13] = "https://www.seegenemedical.com/resultImg/27157/"+patName[i].trim()+"_"+chartNo[i].trim()+ "_"+gno.trim()+"_"+hGcd+"_01.JPG";
//						
//						if (cnt == 400
//								&& inspectCode[i].toString().trim().length() == 7 && i == 399)
//						{
//							// ! 사용하는 결과는 11개
//							for (int k = 0; k < 14; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);	}
//						} else {
//							for (int k = 0; k < 14; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);
//							}
//						}
//					}else{
//						if (cnt == 400
//								&& inspectCode[i].toString().trim().length() == 7 && i == 399)
//						{
//							// ! 사용하는 결과는 11개
//							for (int k = 0; k < 13; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);	}
//						} else {
//							for (int k = 0; k < 13; k++) {	label = new jxl.write.Label(k, row, data[k]);	wbresult.addCell(label);
//							}
//						}
//
//					}
//				}
				if (!isDebug) {
					for (int k = 0; k < data.length; k++) {
						label = new jxl.write.Label(k, row, data[k]);
						wbresult.addCell(label);
					}
				} 
				data = new String[17];
				row++;
			}
//			if (cnt == 400) {
//				String cd = "";
//				if (inspectCode[cnt - 1].toString().trim().length() == 7) {
//					if (inspectCode[cnt - 1].trim().substring(0, 5).equals("11026")	|| inspectCode[cnt - 1].trim().substring(0, 5)	.equals("11052")) 
//					{
//						cd = inspectCode[cnt - 1].substring(0, 5) + "01";
//					} else {
//						cd = inspectCode[cnt - 1].substring(0, 5) + "00";
//					}
//					setParameters(new String[] { hosCode[cnt - 1],
//							rcvDate[cnt - 1], rcvNo[cnt - 1], cd, seq[cnt - 1] });
//				}
			if (cnt == 400 || isNext) {
				setParameters(new String[] { hosCode[cnt - 1],rcvDate[cnt - 1], rcvNo[cnt - 1], inspectCode[cnt - 1],seq[cnt - 1] });
			} else {
				setParameters(null);
			}
		} catch (Exception _ex) {
			setParameters(null);
		}
	}
	public int strByte(String str){
		  int len = str.length();
		  int cnt = 0;
		  for(int i=0; i<len; i++){
		   //-----------------------
		   // 256 이하면 1byte짜리
		   // 256이상이면 2byte짜리
		   //-----------------------   
		   if(str.charAt(i) < 256)
		    cnt++;
		   else
		    cnt = cnt + 2;
		  }
		  return cnt;
		 }



}
