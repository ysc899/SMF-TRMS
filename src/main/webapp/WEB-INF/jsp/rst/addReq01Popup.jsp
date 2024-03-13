<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>추가의뢰 등록</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
	<meta name="title" content="BASIC"/>
	
	<%@ include file="/inc/common.inc"%>
	<%@ include file="/inc/popup.inc"%>
	
	<script type="text/javascript">
	var I_LOGMNU = "ADDREQ";
	var I_LOGMNM = "추가의뢰 등록";
	var addCode = "";
	var header = "";
	var insertData = "";
	var dataProvider5, gridView5, dataProvider6, gridView6;
	$(document).ready( function(){
		$(".notQuote").keyup(function(event){
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
	            var inputVal = $(this).val();
	            //$(this).val(inputVal.replace(/[&apos;]/gi,''));
	            $(this).val(inputVal.replace(/[a-z0-9]/gi,''));
	            //console.log(inputVal);
	        }
	    });
		
		//이전 추가 의뢰 목록
	    dataProvider5 = new RealGridJS.LocalDataProvider();
		gridView5 = new RealGridJS.GridView("realgrid5");
	    setStyles(gridView5);
	    gridView5.setHeader({
	    	height:40
	    })
	    gridView5.setDataSource(dataProvider5);
	    gridView5.setEditOptions({
	    	insertable: false,
	    	deletable: false,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields5 = [
	    	{fieldName: "CHK"},
	    	{fieldName: "R001GCD"},
	    	{fieldName: "F010SNM"},
	    	{fieldName: "F010STS"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider5.setFields(fields5);
	  //이미지 버튼 생성
		var columns5 = [
	        {name: "R001GCD", fieldName: "R001GCD", width: "50", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검사코드"}}
	      ,	{name: "F010SNM", fieldName: "F010SNM", width: "250", styles: {textAlignment: "near"},    renderer:{showTooltip: true}, header: {text: "검사명"}}
	      ]
	      
	  	// 2021.02.08
	  	// 해당조건이 true 인 경우, 체크박스를 비활성화 한다.
		gridView5.setCheckableExpression("values['F010STS'] = 'Y'", true);
	  
		gridView5.setColumns(columns5);
	  
		gridView5.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    }
		
		var checkBar5 = gridView5.getCheckBar();
		checkBar5.visible = true;
	    gridView5.setCheckBar(checkBar5);
	    
	    var stateBar5 = gridView5.getStateBar();
	    stateBar5.visible = false;
	    gridView5.setStateBar(stateBar5);
	    
	    
	  //기타 항목
	    dataProvider6 = new RealGridJS.LocalDataProvider();
		gridView6 = new RealGridJS.GridView("realgrid6");
	    setStyles(gridView6);
	    gridView6.setHeader({
	    	height:40
	    })
	    gridView6.setDataSource(dataProvider6);
	    gridView6.setEditOptions({
	    	insertable: true,
	    	deletable: true,
	    	//readOnly: true
	    	editable:false
	    })
	    var fields6 = [
	    	{fieldName: "CHK"},
	    	{fieldName: "R001GCD"},
	    	{fieldName: "F010SNM"},
	    	{fieldName: "I_LOGMNU"},
	        {fieldName: "I_LOGMNM"}
	    ];
	    dataProvider6.setFields(fields6);
	  //이미지 버튼 생성
		var columns6 = [
	        {name: "R001GCD", fieldName: "R001GCD", width: "50", styles: {textAlignment: "center"},    renderer:{showTooltip: true}, header: {text: "검사코드"}}
	      ,	{name: "F010SNM", fieldName: "F010SNM", width: "250", styles: {textAlignment: "near"},    renderer:{showTooltip: true}, header: {text: "검사명"}}
	      ]
	      
		gridView6.setColumns(columns6);
	  
		gridView6.onShowTooltip = function (grid, index, value) {
	        var column = index.column;
	        var itemIndex = index.itemIndex;
	         
	        var tooltip = value;
	        
	        return tooltip;
	    }
		
		gridView6.setCheckableExpression("value['CHK'] = '0'", true);
		
		var checkBar6 = gridView6.getCheckBar();
		checkBar6.visible = true;
	    gridView6.setCheckBar(checkBar6);
	    
	    var stateBar6 = gridView6.getStateBar();
	    stateBar6.visible = false;
	    gridView6.setStateBar(stateBar6);
	    
	    
	    
		
		//$("#R001CLT").datepicker('setDate', 0);
		//$("#R001GDT").datepicker('setDate', 0);
		
		//$("#R001CLT").datepicker();
		//$("#R001GDT").datepicker();
		//var yearAgo = new Date();
        //yearAgo.setDate(yearAgo.getDate() - 7);
        //$("#I_FDT").datepicker('setDate',yearAgo);
        //$("#I_TDT").datepicker('setDate',new Date);
	    
	});
	
	function a_datepicker(obj){
    	$(obj).datepicker({
			dateFormat: 'yy-mm-dd'
		}).datepicker('show');
    }
	
	function a_keyup(obj){
		if ((event.keyCode != 8)) {
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
	    		
				var inputVal = $(obj).val();
	    		//console.log(inputVal);
	    			inputVal = inputVal.replace(/[^0-9(.)~_\-]/gi,"");
		    		inputVal = inputVal.replace(/\./gi,'');
		    		inputVal = inputVal.replace(/-/gi,'');
	    		var textlength = inputVal.length;
	    		
	    		if(textlength>8){
	    			inputVal = inputVal.substr(0,8);
	    			textlength = inputVal.length;
	    		}
	    		if (textlength == 4) {
	    			inputVal = inputVal + "-";
	    		}else if (textlength == 5||textlength == 6) {
	    			inputVal = inputVal.substr(0,4) + "-"+inputVal.substr(4,2) ;
	    		} else if (textlength == 7||textlength == 8) {
	    			inputVal = inputVal.substr(0,4) + "-"+inputVal.substr(4,2)  + "-"+inputVal.substr(6,2) ;
	    		}
	    		//console.log(inputVal);
	    		$(obj).val(inputVal);
			}
		}
	}
	
	function addReq01Call(formData){
		ajaxCall("/addReq00List.do", formData);
	}
	
	function addReq01Call_item(formData){
		ajaxCall("/addReq00List_item.do", formData);
	}
	
	// ajax 호출 result function
	function onResult(strUrl,response){
		if(strUrl == "/addReq00List.do" || strUrl == "/addReq00List_item.do"){
			
			var testReq01List =  response.testReq01List;
			var rstUserDtl =  response.rstUserDtl;
			
			//var rstUserDtl =  response.rstUserDtl;
			
			var addReqHeader = "";
			
			header = testReq01List;
			insertData = rstUserDtl;
			
			if (testReq01List.length > 0){
				var gkdList = testReq01List[0].R001GKD.split("!");
				var chk1Str = "";
				var chk2Str = "";
				var chk3Str = "";
				var chk4Str = "";
				var chk5Str = "";
				var chk6Str = "";
				var chk7Str = "";
				var chk8Str = "";
				var chk9Str = "";
				var chk10Str = "";
				
				//console.log("gkdList = " + gkdList);
				
				for (var i in gkdList){
					if(gkdList[i] == "Serum"){
						chk1Str = gkdList[i]; 	
					}		
					
					if(gkdList[i] == "EDTA W/B"){
						chk2Str = gkdList[i]; 	
					}
					
					if(gkdList[i] == "Heparin W/B"){
						chk3Str = gkdList[i]; 	
					}
					
					if(gkdList[i] == "R.U(Fast morning)"){
						chk4Str = gkdList[i]; 	
					}
					
					if(gkdList[i] == "24'h Urine"){
						chk5Str = gkdList[i]; 	
					}
					
					if(gkdList[i] == "Stool"){
						chk6Str = gkdList[i]; 	
					}
					
					if(gkdList[i] == "Joint fluid"){
						chk7Str = gkdList[i]; 	
					}
					
					if(gkdList[i] == "CSF"){
						chk8Str = gkdList[i]; 	
					}
					
					if(gkdList[i] == "Sputum"){
						chk9Str = gkdList[i]; 	
					}
					
					if(gkdList[i].split(":")[0] == "기타검체"){
						chk10Str = gkdList[i]; 	
					}
				}
				
				addReqHeader += "<tr>";                                                                     
				addReqHeader += 	"<th scope='col' colspan='10' class='text-center'>추가 검사의뢰서</th>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>의뢰기관명</th>";
				addReqHeader += 		"<td>"+testReq01List[0].R001HNM+"</td>";
				addReqHeader += 	"<th scope='row'>의뢰기관코드</th>";
				addReqHeader += 		"<td>"+testReq01List[0].R001HOS+"</td>";
				addReqHeader += 	"<th scope='row'>수진자명</th>";
				addReqHeader += 		"<td>"+testReq01List[0].R001NAM+"</td>";
				addReqHeader += 	"<th scope='row'>Chart No.</th>";
				addReqHeader += 		"<td colspan='3'>"+testReq01List[0].R001CHN+"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>주민번호</th>";
				addReqHeader += 		"<td>"+testReq01List[0].R001JMN+"</td>";
				addReqHeader += 	"<th scope='row'>나이</th>";
				addReqHeader += 		"<td>"+testReq01List[0].R001AGE+"</td>";
				addReqHeader += 	"<th scope='row'>성별</th>";
				addReqHeader += 		"<td>"+testReq01List[0].R001SEXNM+"</td>";
				addReqHeader += 	"<th scope='row'>임신 주수</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' style='width:40px' value='"+testReq01List[0].R001PGY.split("!")[0]+"' id='R001PGY0' name='R001PGY0' maxlength='3' oninput='maxLengthCheck(this)' > 주";
				addReqHeader += 			"<input type='text' style='width:40px' value='"+testReq01List[0].R001PGY.split("!")[1]+"' id='R001PGY1' name='R001PGY1' maxlength='3' oninput='maxLengthCheck(this)' > 수";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>인종</th>";
				addReqHeader += 		"<td></td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>의사명</th>";
				addReqHeader += 		"<td>"+testReq01List[0].R001DTC+"</td>";
				addReqHeader += 	"<th scope='row'>진료과</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='form-control' value='"+testReq01List[0].R001TRM+"' id='R001TRM' name='R001TRM' maxlength='22'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>병동</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='form-control' value='"+testReq01List[0].R001WRD+"' id='R001WRD' name='R001WRD' maxlength='22'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>검체채취일시</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='fr_date calendar_put' value='"+testReq01List[0].R001CLT.substr(0,4) + "-"+testReq01List[0].R001CLT.substr(4,2)  + "-"+testReq01List[0].R001CLT.substr(6,2)+"' id='R001CLT' name='R001CLT' style='width:130px' onclick='javascript:a_datepicker(this);' onkeyup='javascript:a_keyup(this)' >";
            	addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>검체의뢰일시</th>";
				addReqHeader += 		"<td>";
				addReqHeader +=	 			"<input type='text' class='fr_date calendar_put' value='"+testReq01List[0].R001GDT.substr(0,4) + "-"+testReq01List[0].R001GDT.substr(4,2)  + "-"+testReq01List[0].R001GDT.substr(6,2)+"' id='R001GDT' name='R001GDT' style='width:130px' onclick='javascript:a_datepicker(this);' onkeyup='javascript:a_keyup(this)'>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>검체종류</th>";
				addReqHeader += 		"<td colspan='9'>";
				addReqHeader += 			"<ul class='chk_lst'>";
				addReqHeader += 				"<li>";
				if(chk1Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk1' name='chk1' value='Serum'> <label for='chk1'>혈청(Serum;s)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk1' name='chk1' value='Serum' checked> <label for='chk1'>혈청(Serum;s)</label>";
				}
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				if(chk2Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk2' name='chk2' value='EDTA W/B'> <label for='chk2'>전혈(EDTA, Herarin ; W.B)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk2' name='chk2' value='EDTA W/B' checked> <label for='chk2'>전혈(EDTA, Herarin ; W.B)</label>";
				}
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li style='width:300px;'>";
				if(chk3Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk3' name='chk3' value='Heparin W/B'> <label for='chk3'>혈장(EDTA, Herarin, Sod. citrate; P)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk3' name='chk3' value='Heparin W/B' checked> <label for='chk3'>혈장(EDTA, Herarin, Sod. citrate; P)</label>";
				}
				
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				if(chk4Str == ""){
					addReqHeader += 					"  <input type='checkbox' id='chk4' name='chk4' value='R.U(Fast morning)'> <label for='chk4'>단회뇨(RU)</label>";	
				} else {
					addReqHeader += 					"  <input type='checkbox' id='chk4' name='chk4' value='R.U(Fast morning)' checked> <label for='chk4'>단회뇨(RU)</label>";
				}
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				if(chk5Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk5' name='chk5' value='24'h Urine'> <label for='chk5'>축뇨(DU, 총량 : mL)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk5' name='chk5' value='24'h Urine' checked> <label for='chk5'>축뇨(DU, 총량 : mL)</label>";
				}
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				if(chk6Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk6' name='chk6' value='Stool'> <label for='chk6'>분변(Stool)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk6' name='chk6' value='Stool' checked> <label for='chk6'>분변(Stool)</label>";
				}
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				if(chk7Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk7' name='chk7' value='Joint fluid'> <label for='chk7'>관절액(Joint Fluid)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk7' name='chk7' value='Joint fluid' checked> <label for='chk7'>관절액(Joint Fluid)</label>";
				}
				
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li style='width:300px;'>";
				if(chk8Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk8' name='chk8' value='CSF'> <label for='chk8'>뇌척수액(CSF)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk8' name='chk8' value='CSF' checked> <label for='chk8'>뇌척수액(CSF)</label>";
				}
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				if(chk9Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk9' name='chk9' value='Sputum'> <label for='chk9'>객담(Sputum)</label>";	
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk9' name='chk9' value='Sputum' checked> <label for='chk9'>객담(Sputum)</label>";
				}
				                                                    
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				if(chk10Str == ""){
					addReqHeader += 					"<input type='checkbox' id='chk10' name='chk10' value='기타검체'> <label for='chk10'>기타</label>";
					addReqHeader += 					"<input type='text' class='' id='chk10s' name='chk10s' maxlength='30'>";
				} else {
					addReqHeader += 					"<input type='checkbox' id='chk10' name='chk10' value='기타검체' checked> <label for='chk10'>기타</label>";
					addReqHeader += 					"<input type='text' class='' value='"+chk10Str.split(":")[1]+"' id='chk10s' name='chk10s' maxlength='30'>";
				}
				
				           
				addReqHeader += 				"</li>";
				addReqHeader += 			"</ul>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>임상소견</th>";
				addReqHeader += 		"<td colspan='9'>";
				addReqHeader += 			"<input type='text' class='form-control onlyHangul' value='"+testReq01List[0].R001OPN+"' id='R001OPN' name='R001OPN' maxlength='100'>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
	        	
				$('#addReqHeader').html(addReqHeader);
			} else {
				addReqHeader += "<tr>";                                                                     
				addReqHeader += 	"<th scope='col' colspan='10' class='text-center'>추가 검사의뢰서</th>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>의뢰기관명</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].HOSN+"</td>";
				addReqHeader += 	"<th scope='row'>의뢰기관코드</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].HOS+"</td>";
				addReqHeader += 	"<th scope='row'>수진자명</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].NAM+"</td>";
				addReqHeader += 	"<th scope='row'>Chart No.</th>";
				addReqHeader += 		"<td colspan='3'>"+rstUserDtl[0].CHN+"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>주민번호</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].JN+"</td>";
				addReqHeader += 	"<th scope='row'>나이</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].AGE+"</td>";
				addReqHeader += 	"<th scope='row'>성별</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].SEX+"</td>";
				addReqHeader += 	"<th scope='row'>임신 주수</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' style='width:40px' value='' id='R001PGY0' name='R001PGY0' maxlength='3' oninput='maxLengthCheck(this)'> 주";
				addReqHeader += 			"<input type='text' style='width:40px' value='' id='R001PGY1' name='R001PGY1' maxlength='3' oninput='maxLengthCheck(this)'> 수";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>인종</th>";
				addReqHeader += 		"<td></td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>의사명</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].DPTN+"</td>";
				addReqHeader += 	"<th scope='row'>진료과</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='form-control' value='' id='R001TRM' name='R001TRM' maxlength='22'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>병동</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='form-control' value='' id='R001WRD' name='R001WRD' maxlength='22'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>검체채취일시</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='fr_date calendar_put' id='R001CLT' name='R001CLT' style='width:130px' onclick='javascript:a_datepicker(this);' onkeyup='javascript:a_keyup(this)'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>검체의뢰일시</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='fr_date calendar_put' id='R001GDT' name='R001GDT' style='width:130px' onclick='javascript:a_datepicker(this);' onkeyup='javascript:a_keyup(this)'>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>검체종류</th>";
				addReqHeader += 		"<td colspan='9'>";
				addReqHeader += 			"<ul class='chk_lst'>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk1' name='chk1' value='Serum'> <label for='chk1'>혈청(Serum;s)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk2' name='chk2' value='EDTA W/B'> <label for='chk2'>전혈(EDTA, Herarin ; W.B)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li style='width:300px;'>";
				addReqHeader += 					"<input type='checkbox' id='chk3' name='chk3' value='Heparin W/B'> <label for='chk3'>혈장(EDTA, Herarin, Sod. citrate; P)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk4' name='chk4' value='R.U(Fast morning)'> <label for='chk4'>단회뇨(RU)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk5' name='chk5' value='24'h Urine'> <label for='chk5'>축뇨(DU, 총량 : mL)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk6' name='chk6' value='Stool'> <label for='chk6'>분변(Stool)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk7' name='chk7' value='Joint fluid'> <label for='chk7'>관절액(Joint Fluid)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li style='width:300px;'>";
				addReqHeader += 					"<input type='checkbox' id='chk8' name='chk8' value='CSF'> <label for='chk8'>뇌척수액(CSF)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk9' name='chk9' value='Sputum'> <label for='chk9'>객담(Sputum)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk10' name='chk10' value='기타검체'> <label for='chk10'>기타</label>";
				addReqHeader += 					"<input type='text' class='' id='chk10s' name='chk10s' maxlength='30'>";
				addReqHeader += 				"</li>";
				addReqHeader += 			"</ul>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>임상소견</th>";
				addReqHeader += 		"<td colspan='9'>";
				addReqHeader += 			"<input type='text' class='form-control onlyHangul' value='' id='R001OPN' name='R001OPN' maxlength='100'>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				
	        	
	        	/*
				addReqHeader += "<tr>";                                                                     
				addReqHeader += 	"<th scope='col' colspan='8' class='text-center'>검사의뢰서</th>";
				addReqHeader += 	"<td colspan='2' rowspan='2' class='text-center'>";
				addReqHeader += 		"바  코  드";
				addReqHeader += 	"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>의뢰기관명</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].HOSN+"</td>";
				addReqHeader += 	"<th scope='row'>의뢰기관코드</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].HOS+"</td>";
				addReqHeader += 	"<th scope='row'>수진자명</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].NAM+"</td>";
				addReqHeader += 	"<th scope='row'>Chart No.</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].CHN+"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>주민번호</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].JN+"</td>";
				addReqHeader += 	"<th scope='row'>나이</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].AGE+"</td>";
				addReqHeader += 	"<th scope='row'>성별</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].SEX+"</td>";
				addReqHeader += 	"<th scope='row'>임신 주수</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' style='width:40px' value='' id='R001PGY0' name='R001PGY0'> 주";
				addReqHeader += 			"<input type='text' style='width:40px' value='' id='R001PGY1' name='R001PGY1'> 수";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>인종</th>";
				addReqHeader += 		"<td></td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>의사명</th>";
				addReqHeader += 		"<td>"+rstUserDtl[0].DPTN+"</td>";
				addReqHeader += 	"<th scope='row'>진료과</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='form-control' value='' id='R001TRM' name='R001TRM'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>병동</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='form-control' value='' id='R001WRD' name='R001WRD'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>검체채취일시</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='tex' class='form-control' value='' id='R001CLT' name='R001CLT'>";
				addReqHeader += 		"</td>";
				addReqHeader += 	"<th scope='row'>검체의뢰일시</th>";
				addReqHeader += 		"<td>";
				addReqHeader += 			"<input type='text' class='form-control' value='' id='R001GDT' name='R001GDT'>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>검체종류</th>";
				addReqHeader += 		"<td colspan='9'>";
				addReqHeader += 			"<ul class='chk_lst'>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk1' name='chk1' value='Serum'> <label for='chk1'>혈청(Serum;s)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk2' name='chk2' value='EDTA W/B'> <label for='chk2'>전혈(EDTA, Herarin ; W.B)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk3' name='chk3' value='Heparin W/B'> <label for='chk3'>혈장(EDTA, Herarin, Sod. citrate; P)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk4' name='chk4' value='R.U(Fast morning)'> <label for='chk4'>단회뇨(RU)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk5' name='chk5' value='24'h Urine'> <label for='chk5'>축뇨(DU, 총량 : mL)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk5' name='chk5' value='24'h Urine'> <label for='chk5'>축뇨(DU, 총량 : mL)</label>";
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk6' name='chk6' value='Stool'> <label for='chk6'>분변(Stool)</label>";	
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk7' name='chk7' value='Joint fluid'> <label for='chk7'>관절액(Joint Fluid)</label>";	
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk8' name='chk8' value='CSF'> <label for='chk8'>뇌척수액(CSF)</label>";	
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk9' name='chk9' value='Sputum'> <label for='chk9'>객담(Sputum)</label>";	
				addReqHeader += 				"</li>";
				addReqHeader += 				"<li>";
				addReqHeader += 					"<input type='checkbox' id='chk10' name='chk10' value='기타검체'> <label for='chk10'>기타</label>";
				addReqHeader += 					"<input type='text' class=''>";
				addReqHeader += 				"</li>";
				addReqHeader += 			"</ul>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				addReqHeader += "<tr>";
				addReqHeader += 	"<th scope='row'>임상소견</th>";
				addReqHeader += 		"<td colspan='9'>";
				addReqHeader += 			"<input type='text' class='form-control' value='' id='R001OPN' name='R001OPN'>";
				addReqHeader += 		"</td>";
				addReqHeader += "</tr>";
				*/
				$('#addReqHeader').html(addReqHeader);
			}
			
			var addReq01List =  response.addReq01List;
			addCode = addReq01List;
			
			var addReq1 = "";
			var addReq2 = "";
			var addReq3 = "";
			var addReq4 = "";
			
			
			
			for (var i in addReq01List){
				if(addReq01List[i].X == "1"){
					/*
					addReq1 +=         "<tr>";
					addReq1 +=             "<td rowspan='2'>";
					addReq1 +=                 "<label>";
					*/
					if(addReq01List[i].CHK1 == 1 || addReq01List[i].CHK2 == 1 || addReq01List[i].CHK3 == "N"){
						addReq1 +=         "<tr style='background-color:#f6f6f6'>";
						addReq1 +=             "<td rowspan='2'>";
						addReq1 +=                 "<label>";
						addReq1 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq' onclick='return false;' disabled>";
						addReq1 +=                 "</label>";
						addReq1 +=             "</td>";
						addReq1 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq1 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq1 +=         "</tr>";
						addReq1 +=         "<tr style='background-color:#f6f6f6'>";
						addReq1 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						//addReq1 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq1 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq1 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq1 +=         "</tr>";
					} else {
						addReq1 +=         "<tr>";
						addReq1 +=             "<td rowspan='2'>";
						addReq1 +=                 "<label>";
						addReq1 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq'>";
						addReq1 +=                 "</label>";
						addReq1 +=             "</td>";
						addReq1 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq1 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq1 +=         "</tr>";
						addReq1 +=         "<tr>";
						addReq1 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						//addReq1 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq1 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq1 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq1 +=         "</tr>";
					}
					/*
					addReq1 +=                 "</label>";
					addReq1 +=             "</td>";
					addReq1 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
					addReq1 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
					addReq1 +=         "</tr>";
					addReq1 +=         "<tr>";
					addReq1 +=         	"<td>"+addReq01List[i].GCD +"</td>";
					addReq1 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
					addReq1 +=         "</tr>";
					*/
				}
				
				if(addReq01List[i].X == "2"){
					/*
					addReq2 +=         "<tr>";
					addReq2 +=             "<td rowspan='2'>";
					addReq2 +=                 "<label>";
					*/
					if(addReq01List[i].CHK1 == 1 || addReq01List[i].CHK2 == 1 || addReq01List[i].CHK3 == "N"){
						addReq2 +=         "<tr style='background-color:#f6f6f6'>";
						addReq2 +=             "<td rowspan='2'>";
						addReq2 +=                 "<label>";
						addReq2 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq' onclick='return false;' disabled>";
						addReq2 +=                 "</label>";
						addReq2 +=             "</td>";
						addReq2 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq2 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq2 +=         "</tr>";
						addReq2 +=         "<tr style='background-color:#f6f6f6'>";
						addReq2 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						//addReq2 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq2 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq2 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq2 +=         "</tr>";
					} else {
						addReq2 +=         "<tr>";
						addReq2 +=             "<td rowspan='2'>";
						addReq2 +=                 "<label>";
						addReq2 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq'>";
						addReq2 +=                 "</label>";
						addReq2 +=             "</td>";
						addReq2 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq2 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq2 +=         "</tr>";
						addReq2 +=         "<tr>";
						addReq2 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						//addReq2 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq2 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq2 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq2 +=         "</tr>";
					}
					/*
					addReq2 +=                 "</label>";
					addReq2 +=             "</td>";
					addReq2 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
					addReq2 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
					addReq2 +=         "</tr>";
					addReq2 +=         "<tr>";
					addReq2 +=         	"<td>"+addReq01List[i].GCD +"</td>";
					addReq2 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
					addReq2 +=         "</tr>";
					*/
				}
					
				if(addReq01List[i].X == "3"){
					/*
					addReq3 +=         "<tr>";
					addReq3 +=             "<td rowspan='2'>";
					addReq3 +=                 "<label>";
					*/
					if(addReq01List[i].CHK1 == 1 || addReq01List[i].CHK2 == 1 || addReq01List[i].CHK3 == "N"){
						addReq3 +=         "<tr style='background-color:#f6f6f6'>";
						addReq3 +=             "<td rowspan='2'>";
						addReq3 +=                 "<label>";
						addReq3 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq' onclick='return false;' disabled>";
						addReq3 +=                 "</label>";
						addReq3 +=             "</td>";
						addReq3 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq3 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq3 +=         "</tr>";
						addReq3 +=         "<tr style='background-color:#f6f6f6'>";
						addReq3 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						//addReq3 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq3 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq3 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq3 +=         "</tr>";
					} else {
						addReq3 +=         "<tr>";
						addReq3 +=             "<td rowspan='2'>";
						addReq3 +=                 "<label>";
						addReq3 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq'>";
						addReq3 +=                 "</label>";
						addReq3 +=             "</td>";
						addReq3 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq3 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq3 +=         "</tr>";
						addReq3 +=         "<tr>";
						addReq3 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						//addReq3 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq3 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq3 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq3 +=         "</tr>";
					}
					/*
					addReq3 +=                 "</label>";
					addReq3 +=             "</td>";
					addReq3 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
					addReq3 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
					addReq3 +=         "</tr>";
					addReq3 +=         "<tr>";
					addReq3 +=         	"<td>"+addReq01List[i].GCD +"</td>";
					addReq3 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
					addReq3 +=         "</tr>";
					*/
				}
				
				if(addReq01List[i].X == "4"){
					/*
					addReq4 +=         "<tr>";
					addReq4 +=             "<td rowspan='2'>";
					addReq4 +=                 "<label>";
					*/
					if(addReq01List[i].CHK1 == 1 || addReq01List[i].CHK2 == 1 || addReq01List[i].CHK3 == "N"){
						addReq4 +=         "<tr style='background-color:#f6f6f6'>";
						addReq4 +=             "<td rowspan='2'>";
						addReq4 +=                 "<label>";
						addReq4 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq' onclick='return false;' disabled>";
						addReq4 +=                 "</label>";
						addReq4 +=             "</td>";
						addReq4 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq4 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq4 +=         "</tr>";
						addReq4 +=         "<tr style='background-color:#f6f6f6'>";
						addReq4 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						//addReq4 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq4 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq4 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq4 +=         "</tr>";
					} else {
						addReq4 +=         "<tr>";
						addReq4 +=             "<td rowspan='2'>";
						addReq4 +=                 "<label>";
						addReq4 +=                     "<input type='checkbox' value='"+addReq01List[i].GCD +"' id='"+addReq01List[i].GCD +"' name='addReq'>";
						addReq4 +=                 "</label>";
						addReq4 +=             "</td>";
						addReq4 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
						addReq4 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
						addReq4 +=         "</tr>";
						addReq4 +=         "<tr>";
						addReq4 +=         	"<td>"+addReq01List[i].GCD +"</td>";
						if(addReq01List[i].S_CD == "U01"){
							addReq4 +=             "<td style='font-size:11px'>"+addReq01List[i].S_NAME + "</td>";
						}else{
							addReq4 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
						}
						addReq4 +=         "</tr>";
					}
					/*
					addReq4 +=                 "</label>";
					addReq4 +=             "</td>";
					addReq4 +=             "<td rowspan='2'>"+addReq01List[i].G_NAME + "</td>";
					addReq4 +=             "<td colspan='2'>"+addReq01List[i].O_CD + "</td>";
					addReq4 +=         "</tr>";
					addReq4 +=         "<tr>";
					addReq4 +=         	"<td>"+addReq01List[i].GCD +"</td>";
					addReq4 +=             "<td>"+addReq01List[i].S_NAME + "</td>";
					addReq4 +=         "</tr>";
					*/
				}
			}
			
			$('#addReq1').html(addReq1);
			$('#addReq2').html(addReq2);
			$('#addReq3').html(addReq3);
			$('#addReq4').html(addReq4);
			
			
			var addReq01grid =  response.addReq01grid;
			gridView5.closeProgress();
			dataProvider5.clearRows();
			dataProvider5.setRows(addReq01grid);
			gridView5.setDataSource(dataProvider5);
			
			var addReq02List =  response.addReq02List;
			gridView6.closeProgress();
			dataProvider6.clearRows();
			dataProvider6.setRows(addReq02List);
			gridView6.setDataSource(dataProvider6);
			
			reSize();
			
		}
		
		
		//if(strUrl == "/addReq00List.do"){
			//addCode =  response.resultList;
		//}
		
		if(strUrl == "/addReq01gridUpdate.do"){
			CallMessage("221","alert");  //수정 완료
			closeModal();
		}
		if(strUrl == "/addReq01gridInsert.do"){
			CallMessage("221","alert");  //저장 완료
			closeModal();
		}
		
		
	}
	
	
	function reSize(){
		setTimeout("callResize()", 100);
		
	}
	
	function etcItemSearch(){
		var gridValus = $("#popupForm").serialize();
		
		fnOpenPopup2("/testSearchMA.do","검사 항목 조회",gridValus,callFunPopup2);	
	}
	
	function callFunPopup2(url,iframe,gridValus){
		iframe.gridViewRead(gridValus);	
	}
	
	function fnCloseModal(obj){
		for (var i in obj){
			var values = [];
			var chk = "";
			if(i != 0){
				values[0] = 0;
				values[1] = obj[i].F010GCD;				
				values[2] = obj[i].F010FKN;
				for(var c in dataProvider6.getRows(0, dataProvider6.getRowCount())){
					if(dataProvider6.getValue(c,"R001GCD") == obj[i].F010GCD){
						chk = "Y";
					} 
				}
				if (chk != "Y"){
					dataProvider6.insertRow(0, values);
				}
			}
		}
	}
	
	
	//삭제
	function deleteRow(){
		var dataRows = gridView6.getCheckedRows();
		if(dataRows.length == 0){
			CallMessage("243","alert");	//선택 내역이 없습니다.
			return;	
		} else {
			dataProvider6.removeRows(dataRows, true);
		}
		
	}
	
	function addItem(){
		var dataRows = gridView5.getCheckedRows();
		
		for (var i in dataRows){
			//console.log(dataProvider5.getValue(dataRows[i], "R001GCD"));
			//$('#').attr("style", checked);
			//console.log(dataProvider5.getValue(dataRows[i], "R001GCD"));
			//document.getElementById(dataProvider5.getValue(dataRows[i], "R001GCD")).setAttribute("checked", "true");
			if(document.getElementById(dataProvider5.getValue(dataRows[i], "R001GCD")) != null){
				document.getElementById(dataProvider5.getValue(dataRows[i], "R001GCD")).setAttribute("checked", "true");	
			} else {
				var values = [];
				var cnt = 0;
				
				values[0] = 0;
				values[1] = dataProvider5.getValue(dataRows[i], "R001GCD");				
				values[2] = dataProvider5.getValue(dataRows[i], "F010SNM");
				
				//console.log(dataProvider6.getRowCount());
				if(dataProvider6.getRowCount() > 0){
					for(var c in dataProvider6.getRows(0, dataProvider6.getRowCount())){
						if(dataProvider6.getValue(c,"R001GCD") == dataProvider5.getValue(dataRows[i], "R001GCD")){
							cnt++;
							//dataProvider6.insertRow(0, values);
						}
					}
					
					if(cnt == 0){
						dataProvider6.insertRow(0, values);
					} 
				} else {
					dataProvider6.insertRow(0, values);
				}
			}
			//console.log(document.getElementById(dataProvider5.getValue(dataRows[i], "R001GCD")));
			
		}
		
		$('#add1').click();
	}
	
	function saveData(){
		//검사의뢰서
		var popupFormData = $("#popupForm").serializeArray();
		var R001GKD = "";
		var R001PGY = "";
		var chk10str = "";
		var R001STS = "";
		var stsCnt = 0;
		
		var createRows = dataProvider6.getStateRows('created');
		var saveChkCnt = 0;
		
		//저장여부 체크 항목
		for(var c in addCode){
			//기존에 추가검사 의뢰건이 있는경우
			if(addCode[c].CHK1 == 1 || addCode[c].CHK2 == 1){
				//console.log("addCode[c].GCD = " + addCode[c].GCD);
				saveChkCnt++;
			}
			//신규 추가검사 의뢰건이 있는경우
			for(var i in popupFormData){
				if(addCode[c].GCD == popupFormData[i].value){
					//console.log("addCode[c].GCD = " + addCode[c].GCD);
					saveChkCnt++;
				}
			}
		}
		
		//기타항목 추가건이 있는경우
		if(createRows.length > 0){
			saveChkCnt++;
		}
		
		console.log("saveChkCnt = " + saveChkCnt);
		//저장여부 체크
		if(saveChkCnt == 0){
			CallMessage("205","alert",["저장될 검사항목 "]);  //수정 완료
			return;
		}
		//추가 의뢰서 저장
		for(var i in popupFormData){
			//임신 주수
			if(popupFormData[i].name == "R001PGY0"){
				R001PGY += popupFormData[i].value;
			}
			if(popupFormData[i].name == "R001PGY1"){
				R001PGY += "!"+popupFormData[i].value;
			}
			
			// 검체종류
			if(popupFormData[i].name == "chk1"){
				R001GKD += "!"+popupFormData[i].value;
			}	
			if(popupFormData[i].name == "chk2"){
				R001GKD += "!"+popupFormData[i].value;
			}
			if(popupFormData[i].name == "chk3"){
				R001GKD += "!"+popupFormData[i].value;
			}
			if(popupFormData[i].name == "chk4"){
				R001GKD += "!"+popupFormData[i].value;
			}
			if(popupFormData[i].name == "chk5"){
				R001GKD += "!"+"24'h Urine";
			}
			if(popupFormData[i].name == "chk6"){
				R001GKD += "!"+popupFormData[i].value;
			}
			if(popupFormData[i].name == "chk7"){
				R001GKD += "!"+popupFormData[i].value;
			}
			if(popupFormData[i].name == "chk8"){
				R001GKD += "!"+popupFormData[i].value;
			}
			if(popupFormData[i].name == "chk9"){
				R001GKD += "!"+popupFormData[i].value;
			}
			if(popupFormData[i].name == "chk10"){
				chk10str += "!"+popupFormData[i].value;
			}			if(chk10str != "" && popupFormData[i].name == "chk10s"){
				chk10str += ":"+popupFormData[i].value;
				R001GKD += chk10str;
			}
			
			// ========== Start : 20190830 - 기타항목에 추가하는 종목이 있을 경우, 접수상태가 변경되지 않는 문제가 있어서 추가함.
			if(popupFormData[i].value == "ADDREQ"){
				stsCnt++;
			}
			// ========== End : 20190830 - 기타항목에 추가하는 종목이 있을 경우, 접수상태가 변경되지 않는 문제가 있어서 추가함.
			
			if(popupFormData[i].name == "addReq"){
				var saveData = [];
				var chk = "N";
				
				/*
				for(var c in dataProvider6.getRows(0, dataProvider6.getRowCount())){
					console.log(dataProvider6.getValue(c,"R001GCD") + " = " + popupFormData[i].value);
					if(dataProvider6.getValue(c,"R001GCD") == popupFormData[i].value){
						 
						chk = "Y";
					} 
				}
				*/
				for(var c in addCode){
					//console.log(addCode[c].GCD+ " = " +popupFormData[i].value+ " = " +addCode[c].CHK1+ " = " +addCode[c].CHK2);
							
					if(addCode[c].GCD == popupFormData[i].value && (addCode[c].CHK1 == 1 || addCode[c].CHK2 == 1)){
						chk = "Y";
					}
				}
				//console.log(chk);
				if (chk != "Y"){
					stsCnt++;
					//console.log(popupFormData[i].value+ "= 2");
					saveData += "&I_LOGMNU="+ $('#I_LOGMNU').val();
					saveData += "&I_LOGMNM="+ $('#I_LOGMNM').val();
					saveData += "&I_DAT="+ $('#I_DAT').val();
					saveData += "&I_JNO="+ $('#I_JNO').val();
					saveData += "&F010GCD="+ popupFormData[i].value;
					saveData += "&F010TCD="+ "A01";
					saveData += "&I_REJ="+ "";
					saveData += "&I_STS="+ "STANDBY";
					ajaxCall("/addReq01gridSave.do", saveData);
				}
			}	
		}
		//기타 항목
		//var dataRows = gridView6.getCheckedRows();
		
		//console.log("createRows = " + createRows);
		if(createRows.length > 0 ){
			var saveData2 = [];
			for (var r in createRows){
				var GCD = dataProvider6.getValue(createRows[r], "R001GCD");
				saveData2 = "";
				saveData2 += "&I_LOGMNU="+ $('#I_LOGMNU').val();
				saveData2 += "&I_LOGMNM="+ $('#I_LOGMNM').val();
				saveData2 += "&I_DAT="+ $('#I_DAT').val();
				saveData2 += "&I_JNO="+ $('#I_JNO').val();
				saveData2 += "&F010GCD="+ GCD;
				saveData2 += "&F010TCD="+ "A01";
				saveData2 += "&I_REJ="+ "";
				saveData2 += "&I_STS="+ "STANDBY";
				//console.log("GCD = "+ GCD);
				ajaxCall("/addReq01gridSave.do", saveData2);
				/*
				var GCD = dataProvider6.getValue(dataRows[r], "R001GCD");
				for(var cre in createRows){
					if(dataRows[r] == createRows[cre]){
						stsCnt++;
								
					}
				}
				*/
				
				
			}	
		}
		
		
		var saveData3 = [];
		
		saveData3 += "&I_LOGMNU="+ $('#I_LOGMNU').val();
		saveData3 += "&I_LOGMNM="+ $('#I_LOGMNM').val();
		saveData3 += "&I_DAT="+ $('#I_DAT').val();
		saveData3 += "&I_JNO="+ $('#I_JNO').val();
		saveData3 += "&I_R001PGY="+ R001PGY;
		saveData3 += "&I_R001TRM="+ $('#R001TRM').val();
		saveData3 += "&I_R001WRD="+ $('#R001WRD').val();
		saveData3 += "&I_R001CLT="+ $('#R001CLT').val();
		saveData3 += "&I_R001GDT="+ $('#R001GDT').val();
		saveData3 += "&I_R001GKD="+ R001GKD;
		saveData3 += "&I_R001OPN="+ $('#R001OPN').val();
		
		//saveData3 += "&R001OPN="+ R001OPN;
		if(header.length > 0){
			if(stsCnt > 0 && header[0].R001STS == "C"){
				saveData3 += "&I_R001STS=P";	
			} else {
				saveData3 += "&I_R001STS="+header[0].R001STS;
			}
			
			popupFormData += saveData3;
			ajaxCall("/addReq01gridUpdate.do", popupFormData);
		} else {
			saveData3 += "&I_R001HOS="+ insertData[0].HOS;
			saveData3 += "&I_R001HNM="+ insertData[0].HOSN;
			saveData3 += "&I_R001CHN="+ insertData[0].CHN;
			saveData3 += "&I_R001NAM="+ insertData[0].NAM;
			saveData3 += "&I_R001JMN="+ insertData[0].JN;
			saveData3 += "&I_R001AGE="+ insertData[0].AGE;
			saveData3 += "&I_R001SEX="+ insertData[0].SEX;
			saveData3 += "&I_R001DTC="+ insertData[0].DPTN;
			
			saveData3 += "&I_R001STS=S";
			popupFormData += saveData3;
			ajaxCall("/addReq01gridInsert.do", popupFormData);
		}
	}
	
	function userData2(userData){
		$('#I_DAT').val(userData.I_DAT);
		$('#I_JNO').val(userData.I_JNO);
		$('#I_HOS').val(userData.I_HOS);
		$('#I_RGN1').val(userData.I_RGN1);
		$('#I_RGN2').val(userData.I_RGN2);
		$('#I_RGN3').val(userData.I_RGN3);
		$('#I_HAK').val(userData.I_HAK);
		$('#I_SAB').val(userData.I_SAB);
	}
	
	function maxLengthCheck(object){
	    if (object.value.length > object.maxLength){
	      //object.maxLength : 매게변수 오브젝트의 maxlength 속성 값입니다.
	      object.value = object.value.slice(0, object.maxLength);
	    }    
	  }
	
	$("input:text[numberOnly]").on("keyup", function() {
	    $(this).val($(this).val().replace(/[^0-9]/g,""));
	});
	</script>
    
</head>
<body style="width : 1300px; overflow:auto">
	<form id="popupForm" name="popupForm">
	<input id="I_LOGMNU" name="I_LOGMNU" type="hidden"/>
	<input id="I_LOGMNM" name="I_LOGMNM" type="hidden"/>
	<input id="I_DAT" name="I_DAT" type="hidden">
	<input id="I_JNO" name="I_JNO" type="hidden">
	<input id="I_HOS" name="I_HOS" type="hidden">
	<input id="I_RGN1" name="I_RGN1" type="hidden">
	<input id="I_RGN2" name="I_RGN2" type="hidden">
	<input id="I_RGN3" name="I_RGN3" type="hidden">
	<input id="I_HAK" name="I_HAK" type="hidden">
	<input id="I_SAB" name="I_SAB" type="hidden">
		<div class="tbl_type" style="overflow:auto">
            <table class="table table-bordered table-condensed tbl_l-type" id="addList">
            	<colgroup>
                	<col width="100px">
                    <col width="150px">
                    <col width="100px">
                    <col width="150px">
                    <col width="100px">
                    <col width="150px">
                    <col width="100px">
                    <col width="150px">
                    <col width="100px">
                    <col width="150px">
                </colgroup>
                <tbody id="addReqHeader">
                	
                </tbody>
            </table>
        </div>
        
        <div id="Tab" class="modal_tab">	
            <ul class="nav nav-tabs">
                <li class="active"><a  href="#1" data-toggle="tab" onClick="javascript:reSize()" id="add1">추가 의뢰서</a></li>
                <li><a href="#2" data-toggle="tab" onClick="javascript:reSize()" id="add2">이전 추가 의뢰 목록</a></li>
                
                <!-- 20191127 - 기타항목추가, 삭제 2개 버튼 위치 변경 -->
                <div class="tit_btn_area modal_btn_area">
                	<button type="button" style="margin-bottom:20px;" class="btn btn-default btn-sm " data-toggle="modal" data-target="#ect_plus-Modal" onClick="javascript:etcItemSearch()">기타항목 추가</button>
                    <button type="button" style="margin-bottom:20px;" class="btn btn-default btn-sm" onClick="javascript:deleteRow()">삭제</button>
                </div>
            </ul>
        
            <div class="tab-content ">
                <div class="tab-pane active" id="1">
                    
                    <div class="container-fluid">                
                        <div class="con_wrap col-md-9 col-sm-12">
                        	<p class="top_txt">임상화학, 면역, 혈청 검사</p>
                        	<div class="con_wrap col-md-4 col-sm-12">
                            	<div class="tbl_type" style="overflow:auto">
                                    <table class="table table-bordered table-condensed tbl_c-type">
                                        <colgroup>
                                            <col width="50px">
                                            <col >
                                            <col width="22%">
                                            <col width="28%">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">CHK</th>
                                                <th scope="col" rowspan="2">검사명</th>
                                                <th scope="col" colspan="2">보험코드</th>
                                            </tr>
                                            <tr>
                                                <th scope="col">씨젠코드</th>
                                                <th scope="col">검체</th>
                                            </tr>
                                        </thead>
                                        <tbody id="addReq1">
                                            
                                        </tbody>
                                    </table>
                                </div> 
                            </div>   
                            
                            <div class="con_wrap col-md-4 col-sm-12">
                            	<div class="tbl_type" style="overflow:auto">
                                    <table class="table table-bordered table-condensed tbl_c-type">
                                        <colgroup>
                                            <col width="50px">
                                            <col >
                                            <col width="22%">
                                            <col width="28%">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">CHK</th>
                                                <th scope="col" rowspan="2">검사명</th>
                                                <th scope="col" colspan="2">보험코드</th>
                                            </tr>
                                            <tr>
                                                <th scope="col">씨젠코드</th>
                                                <th scope="col">검체</th>
                                            </tr>
                                        </thead>
                                        <tbody id="addReq2">
                                            
                                        </tbody>
                                    </table>
                                </div> 
                            </div> 
                            
                            <div class="con_wrap col-md-4 col-sm-12">
                            	<div class="tbl_type" style="overflow:auto">
                                    <table class="table table-bordered table-condensed tbl_c-type">
                                        <colgroup>
                                            <col width="50px">
                                            <col >
                                            <col width="22%">
                                            <col width="28%">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">CHK</th>
                                                <th scope="col" rowspan="2">검사명</th>
                                                <th scope="col" colspan="2">보험코드</th>
                                            </tr>
                                            <tr>
                                                <th scope="col">씨젠코드</th>
                                                <th scope="col">검체</th>
                                            </tr>
                                        </thead>
                                        <tbody id="addReq3">
                                            
                                        </tbody>
                                    </table>
                                </div> 
                            </div>                            
                            
                        </div>
                        
                        <div class="con_wrap col-md-3 col-sm-12">
                            <p class="top_txt top_txt_v2">혈액, 검경, 미생물 등 검사</p>
                            <div class="con_wrap col-md-12 col-sm-12">
                            	<div class="tbl_type" style="overflow:auto">
                                    <table class="table table-bordered table-condensed tbl_c-type">
                                        <colgroup>
                                            <col width="50px">
                                            <col >
                                            <col width="22%">
                                            <col width="28%">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">CHK</th>
                                                <th scope="col" rowspan="2">검사명</th>
                                                <th scope="col" colspan="2">보험코드</th>
                                            </tr>
                                            <tr>
                                                <th scope="col">ND코드</th>
                                                <th scope="col">검체</th>
                                            </tr>
                                        </thead>
                                        <tbody id="addReq4">
                                            
                                        </tbody>
                                    </table>
                                </div> 
                            </div>
                        </div>
                    </div>
                    <div class="modal_title_area">
                        <h5 class="tit_h5">기타 항목</h5>
                        <!-- 20191127 - 기타항목추가, 삭제 2개 버튼 위치 변경 -->
                        <!-- <div class="tit_btn_area modal_btn_area">
                        	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#ect_plus-Modal" onClick="javascript:etcItemSearch()">기타항목 추가</button>
                            <button type="button" class="btn btn-default btn-sm" onClick="javascript:deleteRow()">삭제</button>
                        </div> -->
                    </div>
                    <div class="tbl_type" style="overflow:auto">
                   	    <div id="realgrid6" class="realgridH" style="height:400px;"></div>
                 	</div>
                 	
                 	<div class="modal-footer">
		            	<div class="min_btn_area">
		                	<button type="button" class="btn btn-primary" onClick="javascript:saveData()">확인</button>
		                    <button type="button" class="btn btn-info" data-dismiss="modal" onClick="javascript:closeModal()">닫기</button>
		                </div>
		            </div>
                    
                </div>
                <div class="tab-pane" id="2">
                	<!-- <div class="tit_btn_area modal_btn_area">
                        <button type="button" class="btn btn-primary" onClick="javascript:addItem()">항목추가</button>
                        <button type="button" class="btn btn-info" onClick="javascript:closeModal()">닫기</button>
                    </div> -->
                   	<div class="tbl_type" style="overflow:auto">
                   	    <div id="realgrid5" class="realgridH" style="height:400px;">
                   	    </div>
                 	</div>
                 	<div class="modal-footer">
                 		<div  class="min_btn_area">
                 			<button type="button" class="btn btn-primary" onClick="javascript:addItem()">항목추가</button>
                    		<button type="button" class="btn btn-info" data-dismiss="modal" onClick="javascript:closeModal()">닫기</button>
                 		</div>
                 	
                 	</div>
                </div>
            </div>
        </div>
        <!-- <div class="modal-footer">
            	<div class="min_btn_area">
                	<button type="button" class="btn btn-primary" onClick="javascript:saveData()">추가</button>
                    <button type="button" class="btn btn-info" data-dismiss="modal" onClick="javascript:closeModal()">닫기</button>
                </div>
            </div> -->
	 </form>
</body>
</html>