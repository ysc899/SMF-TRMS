<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>접수 포맷 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<script>

	var dataProvider1,gridView1;
	var pcd = "";
	var fnm = "";
	var resultCode = "";
	$(document).ready( function(){
		
	    
	    dataProvider1 = new RealGridJS.LocalDataProvider();
	    gridView1 = new RealGridJS.GridView("realgrid1");
	    gridView1.setDataSource(dataProvider1);
	    gridView1.setOptions({
	    	panel : {
				visible : false		
			},footer: {
                visible: false
            }
	    })
	    
	    gridView1.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	readOnly: false,
	    	deletable: false
	    })
	    
	    setStyles(gridView1);
	    gridView1.setStyles(basicGrayBlueSkin);

	    var fields1 = [
	    	{
	            "fieldName": "F120PCD"
	        },
	        {
	            "fieldName": "F120FNM"
	        },
	        {
	            "fieldName": "F120MBC"
	        },
	        {
	            "fieldName": "F910NAM"
	        }
	    ];
	    dataProvider1.setFields(fields1);
	    
	    var columns1 = [
	    	
	        {"name": "F120PCD", "fieldName": "F120PCD", "width": "80", "styles": {"textAlignment": "center"}, "header": {"text": "병원코드"}}
	      ,	{"name": "F120FNM", "fieldName": "F120FNM", "width": "200", "styles": {"textAlignment": "near"}, "header": {"text": "병원 명(사용자)"}}
	      , {"name": "F910NAM", "fieldName": "F910NAM", "width": "150", "styles": {"textAlignment": "near"}, "header": {"text": "담당"}}
	    ];
	    gridView1.setColumns(columns1);
	    
	    var checkBar = gridView1.getCheckBar();
	    checkBar.visible = false;
	    gridView1.setCheckBar(checkBar);
	    
		gridView1.onDataCellClicked = function (grid, index) {
	 		dataResult2(grid.getValue(index.itemIndex, 0));
	 		pcd = grid.getValue(index.itemIndex, 0);
	 		fnm = grid.getValue(index.itemIndex, 1);
		};
		
	    //dataResult1();
	    
	    gridView1.setStateBar({
			  visible: false  
		});
	    
	    //파일형식
	    var resultCode = "";
	    
		resultCode = getCode(I_LOGMNU, I_LOGMNM, "FILE_EXT", 'N');
		$('#I_S009EXT').html(resultCode);
		jcf.replace($("#I_S009EXT"));
		
	});
	
	function dataResult1(){
		
		var I_F120PCD = $("#searchWordCode").val();
		var I_F120FNM = $("#searchWordName").val();
		
		$('#I_F120PCD').val(I_F120PCD)
		$('#I_F120FNM').val(I_F120FNM)
		
		var formData = $("#excelFormat").serialize();
		ajaxCall("/sysRegExFormatList.do", formData);

	}
	
	function onResult(strUrl,response){
		
		if(strUrl == "/sysRegExFormatList.do"){
			var resultList = response.resultList;
			dataProvider1.setRows(resultList);		
			gridView1.setDataSource(dataProvider1);
			
			if(resultList.length > 0){
				var index2 = {
					    itemIndex: 0
					};
				gridView1.setCurrent(index2);
				//dataResult2(gridView1.getValue(index.itemIndex, 0));
				pcd = gridView1.getValue(index2.itemIndex, 0);
	 			fnm = gridView1.getValue(index2.itemIndex, 1);
				dataResult2(gridView1.getValue(index2.itemIndex, 0));
				
			}else{
				callLoading(null, "off");
			}
		}
		
		if(strUrl == "/sysRegExFormatDtl.do"){
			
			var resultList = response.resultList;
			
			$('#F120PCD').html(pcd);
			$('#F120FNM').html(fnm);
			if (resultList.length > 0){
				$('#I_S009STR').val(resultList[0]["S009STR"]);
				$('#I_S009EXT').val(resultList[0]["S009EXT"]);
				$('#I_S009COL').val(resultList[0]["S009COL"]);
				$('#I_S009001').val(resultList[0]["S009001"]);
				$('#I_S009002').val(resultList[0]["S009002"]);
				$('#I_S009003').val(resultList[0]["S009003"]);
				$('#I_S009004').val(resultList[0]["S009004"]);
				$('#I_S009005').val(resultList[0]["S009005"]);
				$('#I_S009006').val(resultList[0]["S009006"]);
				$('#I_S009007').val(resultList[0]["S009007"]);
				$('#I_S009008').val(resultList[0]["S009008"]);
				$('#I_S009009').val(resultList[0]["S009009"]);
				$('#I_S009010').val(resultList[0]["S009010"]);
				$('#I_S009011').val(resultList[0]["S009011"]);
				$('#I_S009012').val(resultList[0]["S009012"]);
				$('#I_S009013').val(resultList[0]["S009013"]);
				$('#I_S009014').val(resultList[0]["S009014"]);
				$('#I_S009015').val(resultList[0]["S009015"]);
				$('#I_S009016').val(resultList[0]["S009016"]);
				$('#I_S009017').val(resultList[0]["S009017"]);
				$('#I_S009018').val(resultList[0]["S009018"]);
				$('#I_S009019').val(resultList[0]["S009019"]);
				$('#I_S009020').val(resultList[0]["S009020"]);
				$('#I_S009021').val(resultList[0]["S009021"]);
				$('#I_S009022').val(resultList[0]["S009022"]);
				$('#I_S009023').val(resultList[0]["S009023"]);
				$('#I_S009024').val(resultList[0]["S009024"]);
				$('#I_S009044').val(resultList[0]["S009044"]);
				$('#I_S009045').val(resultList[0]["S009045"]);
			} else {
				$('#I_S009STR').val("");
				$('#I_S009EXT').val("XLSX");	
				$('#I_S009COL').val("");
				$('#I_S009001').val("");
				$('#I_S009002').val("");
				$('#I_S009003').val("");
				$('#I_S009004').val("");
				$('#I_S009005').val("");
				$('#I_S009006').val("");
				$('#I_S009007').val("");
				$('#I_S009008').val("");
				$('#I_S009009').val("");
				$('#I_S009010').val("");
				$('#I_S009011').val("");
				$('#I_S009012').val("");
				$('#I_S009013').val("");
				$('#I_S009014').val("");
				$('#I_S009015').val("");
				$('#I_S009016').val("");
				$('#I_S009017').val("");
				$('#I_S009018').val("");
				$('#I_S009019').val("");
				$('#I_S009020').val("");
				$('#I_S009021').val("");
				$('#I_S009022').val("");
				$('#I_S009023').val("");
				$('#I_S009024').val("");
				$('#I_S009044').val("");
				$('#I_S009045').val("");
			}
			jcf.replace($("#I_S009EXT"));
		}
		if(strUrl == "/sysRegExFormatSave.do"){
			CallMessage("221","alert");	//저장을 완료하였습니다.
		}
		
	}
	
	function dataResult2(pcd){
		//var I_S009HOS = pcd;
		$('#I_S009HOS').val(pcd);
		var formData = $("#excelFormat").serialize();
		ajaxCall("/sysRegExFormatDtl.do", formData);
		
	}
	

	//조회
	function search(){
		dataResult1();
	}
	
	function fnSave(){
		var formData = $("#excelFormat").serialize();
		//formData +="&S009EXT="+I_S009EXT;
		ajaxCall("/sysRegExFormatSave.do", formData);
	}
	
	//저장
	function saveRow(){
		
		CallMessage("203","confirm","",delRow);	//저장하시겠습니까?
				
	}
	
	function delRow(){
		var formData = $("#excelFormat").serialize();
		ajaxCall("/sysRegExFormatDel.do", formData);
		fnSave();
	}
	
	</script>
</head>
<body>
	<!-- content_inner -->
	<div class="content_inner">
		<!-- 검색영역 -->
		<div class="container-fluid">
			<div class="con_wrap col-md-12 srch_wrap">
				<div class="title_area open">
					<h3 class="tit_h3">검색영역</h3>
						<a href="#" class="btn_open">검색영역 접기</a>
				</div>
				<div class="con_section row">
			 		<p class="srch_txt text-center" style="display:none">조회하려면 검색영역을 열어주세요.</p>                               
					<div class="srch_box">
						<div class="srch_box_inner">
						    <div class="srch_item">
						        <label for="" class="label_basic">병원코드</label>
						        <input type="text" class="srch_put numberOnly" id="searchWordCode" onKeydown="if(event.keyCode==13) search()" name="searchWordCode" maxlength="5">
						    </div>
						    <div class="srch_item">
						        <label for="" class="label_basic">병원명</label>
						        <input type="text" class="srch_put" id="searchWordName" onKeydown="if(event.keyCode==13) search()" name="searchWordName" maxlength="20">
						    </div>               
						</div>
						  
						<div class="btn_srch_box">
						      <button type="button" class="btn_srch" onClick="javascript:search()">조회</button>
						      <!-- <input type="button" value="조회" onClick="javascript:search()"> -->
						</div> 
					</div>
				</div>
			</div>
		</div>
		<!-- 검색영역 end -->
		<div class="container-fluid">
			<div class="con_wrap col-md-5 col-sm-12">
				<div class="title_area">
                	<h3 class="tit_h3">병원</h3>
                </div>
				<div class="con_section overflow-scr">
	                <div class="tbl_type">
	                	<div id="realgrid1" class="realgridH"></div>
	                </div>
	            </div>
	         </div>
	         <div class="con_wrap col-md-7 col-sm-12">
	         	<div class="title_area">
	                <h3 class="tit_h3">엑셀 포맷</h3>
	                <div class="tit_btn_area">
	                    <button type="button" class="btn btn-default btn-sm" onClick="javascript:saveRow()">저장</button>
	                </div>
                </div>	
	         	<div class="con_section overflow-scr">
	         		<div class="tbl_type">
	         			<form id="excelFormat">
	         			<input id="I_LOGMNU" name="I_LOGMNU" type="hidden" value="<%=menu_cd%>"/>
						<input id="I_LOGMNM" name="I_LOGMNM" type="hidden" value="<%=menu_nm%>"/>
						<input id="I_S009HOS" name="I_S009HOS" type="hidden"/>
						<input id="I_F120PCD" name="I_F120PCD" type="hidden"/>
						<input id="I_F120FNM" name="I_F120FNM" type="hidden"/>
						<input id="S009EXT" name="S009EXT" type="hidden"/>
	         			<table class="table table-bordered table-condensed tbl_l-type">
	         				<colgroup>
	                        	<col width="15%">
                                <col width="35%">
                                <col width="15%">
                                <col width="35%">
                            </colgroup>
                            <tbody>
                            	<tr>
                            		<th scope="row" id="F120PCD">&nbsp;</th>
                            		<td ><span id="F120FNM">&nbsp;</span>
                            		<th scope="row">파일형식</th>
                           			<td>
                           				<div class="select_area">
                           					<select id="I_S009EXT" name="I_S009EXT" class="form-control" ></select>
                           				</div>
                           			</td>
                            	</tr>
                            	<tr>
                            		<th scope="row">데이터시작줄</th>
                           			<td><input type="number" id="I_S009STR" name="I_S009STR" class="form-control" maxlength="5"></td>
                           			<th scope="row">열 구분자</th>
                           			<td><input type="text" id="I_S009COL" name="I_S009COL" class="form-control" maxlength="6"></td>
                            	</tr>
                            	<tr>
                            		<th scope="row">내원번호</th>
                                    <td><input type="text" id="I_S009001" name="I_S009001" class="form-control engOnly" maxlength="3"></td>
                                    <th scope="row">수진자명</th>
                                    <td><input type="text" id="I_S009013" name="I_S009013" class="form-control engOnly" maxlength="3"></td>
                            	</tr>
                            	<tr>
                            		<th scope="row">외래구분</th>
                                    <td><input type="text" id="I_S009002" name="I_S009002" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">주민번호</th>
                                    <td><input type="text" id="I_S009014" name="I_S009014" class="form-control engOnly" placeholder="" maxlength="3"></td>
                            	</tr>
                            	<tr>
                                	<th scope="row">의뢰일자</th>
                                    <td><input type="text" id="I_S009003" name="I_S009003" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">나이</th>
                                    <td><input type="text" id="I_S009015" name="I_S009015" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">검체번호</th>
                                    <td><input type="text" id="I_S009004" name="I_S009004" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">성별</th>
                                    <td><input type="text" id="I_S009016" name="I_S009016" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">처방번호</th>
                                    <td><input type="text" id="I_S009005" name="I_S009005" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">과명</th>
                                    <td><input type="text" id="I_S009017" name="I_S009017" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">처방코드</th>
                                    <td><input type="text" id="I_S009006" name="I_S009006" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">병동</th>
                                    <td><input type="text" id="I_S009018" name="I_S009018" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">처방명</th>
                                    <td><input type="text" id="I_S009007" name="I_S009007" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">참고사항</th>
                                    <td><input type="text" id="I_S009019" name="I_S009019" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">검체명</th>
                                    <td><input type="text" id="I_S009008" name="I_S009008" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">검사파트</th>
                                    <td><input type="text" id="I_S009020" name="I_S009020" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">일련번호</th>
                                    <td><input type="text" id="I_S009009" name="I_S009009" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">의뢰기관</th>
                                    <td><input type="text" id="I_S009021" name="I_S009021" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">검체코드</th>
                                    <td><input type="text" id="I_S009010" name="I_S009010" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">처방일자</th>
                                    <td><input type="text" id="I_S009022" name="I_S009022" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">여유필드</th>
                                    <td><input type="text" id="I_S009011" name="I_S009011" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">혈액형</th>
                                    <td><input type="text" id="I_S009023" name="I_S009023" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">차트번호</th>
                                    <td><input type="text" id="I_S009012" name="I_S009012" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">진료의사</th>
                                    <td><input type="text" id="I_S009024" name="I_S009024" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                                <tr>
                                	<th scope="row">프로파일</th>
                                    <td><input type="text" id="I_S009045" name="I_S009045" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                    <th scope="row">생년월일</th>
                                    <td><input type="text" id="I_S009044" name="I_S009044" class="form-control engOnly" placeholder="" maxlength="3"></td>
                                </tr>
                            </tbody>
	         			</table>
	         			</form>
	         		</div>
	         	</div>
	         </div>
		</div>
	</div>
	<!-- content_inner end-->
	

	<!-- <div align="right">
		<label>병원코드</label><input type="text" id="searchWordCode" name="searchWordCode">
		<label>병원명</label><input type="text" id="searchWordName" name="searchWordName">
		
		<input type="button" value="저장" onClick="javascript:saveRow()">
	</div>
	<div style="width : 100%; height: 210px; margin:auto">
		<div style="width : 49%; height: 200px;position:absolute; left:10px;">
			<label>병원</label>
			<div id="realgrid1" style="width : 100%; height: 200px;"></div>
		</div>
		
		<div style="width : 49%; height: 200px;position:absolute; right:10px;">
			<form id="excelFormat">
			<label>엑셀 포맷</label>
			<table style="border:1px solid black;" border=1>
				<tr height="30px">
					<td><input type="text" id="F120PCD" name="F120PCD"></td>
					<td colspan="3"><input type="text" id="F120FNM" name="F120FNM"></td>
				</tr>
				<tr>
					<td>파일 형식</td>
					<td><select id="S009EXT" name="S009EXT">
						</select>
					</td>
					<td>열 구분자</td>
					<td><input type="text" id="S009COL" name="S009COL"></td>
				</tr>
				<tr>
					<td>내원번호</td>
					<td><input type="text" id="S009001" name="S009001"></td>
					<td>수진자명</td>
					<td><input type="text" id="S009013" name="S009013"></td>
				</tr>
				<tr>
					<td>외래구분</td>
					<td><input type="text" id="S009002" name="S009002"></td>
					<td>주민번호</td>
					<td><input type="text" id="S009014" name="S009014"></td>
				</tr>
				<tr>
					<td>의뢰일자</td>
					<td><input type="text" id="S009003" name="S009003"></td>
					<td>나이</td>
					<td><input type="text" id="S009015" name="S009015"></td>
				</tr>
				<tr>
					<td>검체번호</td>
					<td><input type="text" id="S009004" name="S009004"></td>
					<td>성별</td>
					<td><input type="text" id="S009016" name="S009016"></td>
				</tr>
				<tr>
					<td>처방번호</td>
					<td><input type="text" id="S009005" name="S009005"></td>
					<td>과명</td>
					<td><input type="text" id="S009017" name="S009017"></td>
				</tr>
				<tr>
					<td>처방코드</td>
					<td><input type="text" id="S009006" name="S009006"></td>
					<td>병동</td>
					<td><input type="text" id="S009018" name="S009018"></td>
				</tr>
				<tr>
					<td>처방명</td>
					<td><input type="text" id="S009007" name="S009007"></td>
					<td>참고사항</td>
					<td><input type="text" id="S009019" name="S009019"></td>
				</tr>
				<tr>
					<td>검체명</td>
					<td><input type="text" id="S009008" name="S009008"></td>
					<td>검사파트</td>
					<td><input type="text" id="S009020" name="S009020"></td>
				</tr>
				<tr>
					<td>일련번호</td>
					<td><input type="text" id="S009009" name="S009009"></td>
					<td>의뢰기관</td>
					<td><input type="text" id="S009021" name="S009021"></td>
				</tr>
				<tr>
					<td>검체코드</td>
					<td><input type="text" id="S009010" name="S009010"></td>
					<td>처방일자</td>
					<td><input type="text" id="S009022" name="S009022"></td>
				</tr>
				<tr>
					<td>여유필드</td>
					<td><input type="text" id="S009011" name="S009011"></td>
					<td>혈액형</td>
					<td><input type="text" id="S009023" name="S009023"></td>
				</tr>
				<tr>
					<td>차트번호</td>
					<td><input type="text" id="S009012" name="S009012"></td>
					<td>진료의사</td>
					<td><input type="text" id="S009024" name="S009024"></td>
				</tr>
				<tr>
					<td>프로파일</td>
					<td><input type="text" id="S009045" name="S009045"></td>
					<td>생년월일</td>
					<td><input type="text" id="S009044" name="S009044"></td>
				</tr>
				
			</table>
			</form>
		</div>
	</div> -->
</body>
</html>


