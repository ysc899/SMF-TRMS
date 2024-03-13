
function setReportViewer(mrd_path
						, param
						, viewerUrl
						, rdServerSaveDir
						, systemDownDir
						, downFileName
						, imgObj
						, dpi300_hos_gubun
						, imgEachHos
						, crownixVeiwer_print_YN){
	//console.log(rdServerSaveDir);
	//console.log(systemDownDir);
	//console.log("downFileName      ==== "+downFileName);
	//console.log(imgObj);
//	grid["I_LOGMNU"] = I_LOGMNU;
//	grid["I_LOGMNM"] = I_LOGMNM;
	
	var browser = navigator.userAgent.toLowerCase();
	
	var img_svg = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 500"><path d="M369.9 97.9L286 14C277 5 264.8-.1 252.1-.1H48C21.5 0 0 21.5 0 48v416c0 26.5 21.5 48 48 48h288c26.5 0 48-21.5 48-48V131.9c0-12.7-5.1-25-14.1-34zM332.1 128H256V51.9l76.1 76.1zM48 464V48h160v104c0 13.3 10.7 24 24 24h104v288H48zm32-48h224V288l-23.5-23.5c-4.7-4.7-12.3-4.7-17 0L176 352l-39.5-39.5c-4.7-4.7-12.3-4.7-17 0L80 352v64zm48-240c-26.5 0-48 21.5-48 48s21.5 48 48 48 48-21.5 48-48-21.5-48-48-48z"/></svg>';
	var pdf_svg = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 500"><path d="M369.9 97.9L286 14C277 5 264.8-.1 252.1-.1H48C21.5 0 0 21.5 0 48v416c0 26.5 21.5 48 48 48h288c26.5 0 48-21.5 48-48V131.9c0-12.7-5.1-25-14.1-34zM332.1 128H256V51.9l76.1 76.1zM48 464V48h160v104c0 13.3 10.7 24 24 24h104v288H48zm250.2-143.7c-12.2-12-47-8.7-64.4-6.5-17.2-10.5-28.7-25-36.8-46.3 3.9-16.1 10.1-40.6 5.4-56-4.2-26.2-37.8-23.6-42.6-5.9-4.4 16.1-.4 38.5 7 67.1-10 23.9-24.9 56-35.4 74.4-20 10.3-47 26.2-51 46.2-3.3 15.8 26 55.2 76.1-31.2 22.4-7.4 46.8-16.5 68.4-20.1 18.9 10.2 41 17 55.8 17 25.5 0 28-28.2 17.5-38.7zm-198.1 77.8c5.1-13.7 24.5-29.5 30.4-35-19 30.3-30.4 35.7-30.4 35zm81.6-190.6c7.4 0 6.7 32.1 1.8 40.8-4.4-13.9-4.3-40.8-1.8-40.8zm-24.4 136.6c9.7-16.9 18-37 24.7-54.7 8.3 15.1 18.9 27.2 30.1 35.5-20.8 4.3-38.9 13.1-54.8 19.2zm131.6-5s-5 6-37.3-7.8c35.1-2.6 40.9 5.4 37.3 7.8z"/></svg>';
	
	if (browser.indexOf("chrome") > -1) {
		img_svg = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 480 650"><path d="M369.9 97.9L286 14C277 5 264.8-.1 252.1-.1H48C21.5 0 0 21.5 0 48v416c0 26.5 21.5 48 48 48h288c26.5 0 48-21.5 48-48V131.9c0-12.7-5.1-25-14.1-34zM332.1 128H256V51.9l76.1 76.1zM48 464V48h160v104c0 13.3 10.7 24 24 24h104v288H48zm32-48h224V288l-23.5-23.5c-4.7-4.7-12.3-4.7-17 0L176 352l-39.5-39.5c-4.7-4.7-12.3-4.7-17 0L80 352v64zm48-240c-26.5 0-48 21.5-48 48s21.5 48 48 48 48-21.5 48-48-21.5-48-48-48z"/></svg>';
		pdf_svg = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 480 650"><path d="M369.9 97.9L286 14C277 5 264.8-.1 252.1-.1H48C21.5 0 0 21.5 0 48v416c0 26.5 21.5 48 48 48h288c26.5 0 48-21.5 48-48V131.9c0-12.7-5.1-25-14.1-34zM332.1 128H256V51.9l76.1 76.1zM48 464V48h160v104c0 13.3 10.7 24 24 24h104v288H48zm250.2-143.7c-12.2-12-47-8.7-64.4-6.5-17.2-10.5-28.7-25-36.8-46.3 3.9-16.1 10.1-40.6 5.4-56-4.2-26.2-37.8-23.6-42.6-5.9-4.4 16.1-.4 38.5 7 67.1-10 23.9-24.9 56-35.4 74.4-20 10.3-47 26.2-51 46.2-3.3 15.8 26 55.2 76.1-31.2 22.4-7.4 46.8-16.5 68.4-20.1 18.9 10.2 41 17 55.8 17 25.5 0 28-28.2 17.5-38.7zm-198.1 77.8c5.1-13.7 24.5-29.5 30.4-35-19 30.3-30.4 35.7-30.4 35zm81.6-190.6c7.4 0 6.7 32.1 1.8 40.8-4.4-13.9-4.3-40.8-1.8-40.8zm-24.4 136.6c9.7-16.9 18-37 24.7-54.7 8.3 15.1 18.9 27.2 30.1 35.5-20.8 4.3-38.9 13.1-54.8 19.2zm131.6-5s-5 6-37.3-7.8c35.1-2.6 40.9 5.4 37.3 7.8z"/></svg>';
	}
	
	var jpgItem = {
			index : 13,
			id : 'jpgItem',
			//svg : '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 500"><path d="M369.9 97.9L286 14C277 5 264.8-.1 252.1-.1H48C21.5 0 0 21.5 0 48v416c0 26.5 21.5 48 48 48h288c26.5 0 48-21.5 48-48V131.9c0-12.7-5.1-25-14.1-34zM332.1 128H256V51.9l76.1 76.1zM48 464V48h160v104c0 13.3 10.7 24 24 24h104v288H48zm32-48h224V288l-23.5-23.5c-4.7-4.7-12.3-4.7-17 0L176 352l-39.5-39.5c-4.7-4.7-12.3-4.7-17 0L80 352v64zm48-240c-26.5 0-48 21.5-48 48s21.5 48 48 48 48-21.5 48-48-21.5-48-48-48z"/></svg>',
			svg : img_svg,
			title : '이미지 다운',
			separator : true,
			handler : function() {
				//alert("pdf down");
				/**
				http://219.252.39.132:9001/ReportingServer/manager/index.html 에서
				
				서버환경에 sever tab 안에 
				report.save.dir ==> 레포트 서버가 설치된 서버의 절대 경로로 하면 해당 경로로 저장
				                ==> 경로 지정을 안하면 /ReportingServer/report 폴더로 지덩 됨
				                ==> 경로 예
				                http://219.252.39.132:9001/ReportingServer/report/2018/12/14/20181214205232643_2.jpg
				report.dir.pattern ==> 기본 날짜 지정 yyyy/mm/dd                
				*/
				m2soft.crownix.ReportingServerInvoker.debug = true;
				
				var invoker = new m2soft.crownix.ReportingServerInvoker(viewerUrl);
				invoker.addParameter('opcode', '500');
				invoker.addParameter('mrd_path', mrd_path);
				invoker.addParameter('mrd_param', param);
				invoker.addParameter('export_type', 'jpg');
				invoker.addParameter('protocol', 'sync');
				// export 중 에러 발생시 에러를 감지 할 수 있음.
				// 이중화 환경에서 "export_name"을 사용하는 경우에는 정상동작하지 않음.
				// 이중화 환경에서 "export_name"을 사용하는 경우, 파일 다운로드를 위해서 invoker_downloadProtocolFile.html을 사용하여야 함.
				
				invoker.invoke(function(response) {
					//로딩 띄우기
					callLoading(null,"on");
					setTimeout(function() {
						var ret = response.split('|');
						var resultCode = ret[0];
						var rdFilePathName = ret[1];
						
						var fileSavePath = rdServerSaveDir + "/"+rdFilePathName;

						if(resultCode == '1'){
							$.when(function () {
						        var def = jQuery.Deferred();
						        window.setTimeout(function () {
									// ImgLogSave.do 처리를 comm_reportFileDown.do 안에서 saveMCWRKIMG 으로 처리함. 실패시(I_STS = N)에만 ImgLogSave.do 를 실행함.
						        	// document.getElementById('file_down').src = "/comm_reportFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir)+"&file_name="+encodeURIComponent(downFileName+".jpg")+"&file_ext=jpg&dpi300_hos_gubun="+dpi300_hos_gubun+"&imgEachHos="+imgEachHos;
						        	// document.getElementById('file_down').src = "/comm_reportFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir+"/JPG")+"&file_name="+encodeURIComponent(downFileName+".jpg")+"&file_ext=jpg";

									// get 에서 Post 로 변경함. saveMCWRKIMG를 처리하기 위해서는 병원코드가 담겨 있는 imgObj 를 넘겨야 함.
									var f = document.reportForm;
									f.rdServerSaveDir.value = rdServerSaveDir;
									f.rdFilePathName.value = rdFilePathName;
									f.systemDownDir.value = encodeURIComponent(systemDownDir);
									f.file_name.value = encodeURIComponent(downFileName+".jpg");
									f.file_ext.value = "jpg";
									f.dpi300_hos_gubun.value = dpi300_hos_gubun;
									f.imgEachHos.value = imgEachHos;

									imgObj.I_STS = "Y";	// 일단은 저장하기 위해 Y로 세팅한다.
									f.recvObj.value = JSON.stringify(imgObj);
									f.action = "/comm_reportFileDown.do";
									f.target = "file_down";       //target을 iframe으로 잡는다.
									f.submit();                // submit 호출

									def.resolve();
						        }, 100);
						        return def.promise();
						    }())
						    .done(function () {
						    	if(resultCode == '1') {
						    		//로딩 닫기
						    		if(!isNull(imgObj["I_HOS"])){
						    			imgObj["I_STS"] = "Y";// 로그 저장
//										imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//										imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
// 						    			ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 comm_reportFileDown.do 에서 처리하므로 주석처리
						    		}
						    		callLoading(null,"off");
						    	}else{
						    		if(!isNull(imgObj["I_HOS"])){
							    		imgObj["I_STS"] = "N";// 로그 저장
										imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//										imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//										imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
							    		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장
						    		}
						    		callLoading(null,"off");
						    		alert("시스템 오류입니다. 관리자에게 문의하세요.");
						    	}
						    });
						}else{
				    		if(!isNull(imgObj["I_HOS"])){
								imgObj["I_STS"] = "N";// 로그 저장
								imgObj["I_ERR"] = " 이미지 저장 에러   "+response;//에러 로그 저장
//								imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//								imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
								ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장
				    		}
							callLoading(null,"off");
							alert("시스템 오류입니다. 관리자에게 문의하세요.");
						}
					}, 200);
				});
			}
	};
	
	var pdfItem = {
			index : 14,
			id : 'pdfItem',
			//svg : '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 500"><path d="M369.9 97.9L286 14C277 5 264.8-.1 252.1-.1H48C21.5 0 0 21.5 0 48v416c0 26.5 21.5 48 48 48h288c26.5 0 48-21.5 48-48V131.9c0-12.7-5.1-25-14.1-34zM332.1 128H256V51.9l76.1 76.1zM48 464V48h160v104c0 13.3 10.7 24 24 24h104v288H48zm250.2-143.7c-12.2-12-47-8.7-64.4-6.5-17.2-10.5-28.7-25-36.8-46.3 3.9-16.1 10.1-40.6 5.4-56-4.2-26.2-37.8-23.6-42.6-5.9-4.4 16.1-.4 38.5 7 67.1-10 23.9-24.9 56-35.4 74.4-20 10.3-47 26.2-51 46.2-3.3 15.8 26 55.2 76.1-31.2 22.4-7.4 46.8-16.5 68.4-20.1 18.9 10.2 41 17 55.8 17 25.5 0 28-28.2 17.5-38.7zm-198.1 77.8c5.1-13.7 24.5-29.5 30.4-35-19 30.3-30.4 35.7-30.4 35zm81.6-190.6c7.4 0 6.7 32.1 1.8 40.8-4.4-13.9-4.3-40.8-1.8-40.8zm-24.4 136.6c9.7-16.9 18-37 24.7-54.7 8.3 15.1 18.9 27.2 30.1 35.5-20.8 4.3-38.9 13.1-54.8 19.2zm131.6-5s-5 6-37.3-7.8c35.1-2.6 40.9 5.4 37.3 7.8z"/></svg>',
			svg : pdf_svg,
			title : 'PDF 다운',
			separator : true,
			handler : function() {
				//alert("pdf down");
				/**
				http://219.252.39.132:9001/ReportingServer/manager/index.html 에서
				
				서버환경에 sever tab 안에 
				report.save.dir ==> 레포트 서버가 설치된 서버의 절대 경로로 하면 해당 경로로 저장
				                ==> 경로 지정을 안하면 /ReportingServer/report 폴더로 지덩 됨
				                
				report.dir.pattern ==> 기본 날짜 지정 yyyy/mm/dd                
				*/
				m2soft.crownix.ReportingServerInvoker.debug = true;
				
				var invoker = new m2soft.crownix.ReportingServerInvoker(viewerUrl);
				invoker.addParameter('opcode', '500');
				invoker.addParameter('mrd_path', mrd_path);
				invoker.addParameter('mrd_param', param);
				// 브라우저에서 pdf 인쇄를 동작하게 하려면 /rpdfprintdialog 파라미터가 필요함
				//invoker.addParameter('mrd_param', '/rpdfprintdialog /rfn [sample.txt]');
				invoker.addParameter('export_type', 'pdf');
				invoker.addParameter('protocol', 'sync');
				//alert("pdf down2");
				// export 중 에러 발생시 에러를 감지 할 수 있음.
				// 이중화 환경에서 "export_name"을 사용하는 경우에는 정상동작하지 않음.
				// 이중화 환경에서 "export_name"을 사용하는 경우, 파일 다운로드를 위해서 invoker_downloadProtocolFile.html을 사용하여야 함.
				
				invoker.invoke(function(response) {
					callLoading(null,"on");
					
					var ret = response.split('|');
					var resultCode = ret[0];
					var rdFilePathName = ret[1];
					
					var fileSavePath = rdServerSaveDir + "/"+rdFilePathName;
					//console.log(rdServerSaveDir);
					//console.log(rdFilePathName);
					//console.log(systemDownDir);
					//console.log(downFileName);
					
					
					
					if(resultCode == '1'){
						$.when(function () {
					        var def = jQuery.Deferred();
					        window.setTimeout(function () {
					        	//document.getElementById('file_down').src = "/comm_reportFileDown.do?file_path="+fileSavePath+"&systemDownDir="+encodeURIComponent(systemDownDir+"/PDF")+"&file_name="+encodeURIComponent(downFileName+".pdf")+"&file_ext=pdf";
					        	//document.getElementById('file_down').src = "/comm_reportFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir+"/PDF")+"&file_name="+encodeURIComponent(downFileName+".pdf")+"&file_ext=pdf&dpi300_hos_gubun="+dpi300_hos_gubun;

								// get 에서 Post 로 변경함. saveMCWRKIMG를 처리하기 위해서는 병원코드가 담겨 있는 imgObj 를 넘겨야 함.
								var f = document.reportForm;
								f.rdServerSaveDir.value = rdServerSaveDir;
								f.rdFilePathName.value = rdFilePathName;
								f.systemDownDir.value = encodeURIComponent(systemDownDir+"/PDF");
								f.file_name.value = encodeURIComponent(downFileName+".pdf");
								f.file_ext.value = "pdf";
								f.dpi300_hos_gubun.value = dpi300_hos_gubun;
								f.imgEachHos.value = imgEachHos;

								imgObj.I_STS = "Y";	// 일단은 저장하기 위해 Y로 세팅한다.
								f.recvObj.value = JSON.stringify(imgObj);
								f.action = "/comm_reportFileDown.do";
								f.target = "file_down";       //target을 iframe으로 잡는다.
								f.submit();                // submit 호출

								def.resolve();
					        }, 100);
					        return def.promise();
					    }())
					    .done(function () {
					    	if(resultCode == '1') {
					    		//로딩 닫기
					    		callLoading(null,"off");
					    		if(!isNull(imgObj["I_HOS"])){
						    		imgObj["I_STS"] = "Y";// 로그 저장
									imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//									imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//									imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
// 					        		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 comm_reportFileDown.do 에서 처리하므로 주석처리
					    		}
					    	}else{
					    		if(!isNull(imgObj["I_HOS"])){
						    		imgObj["I_STS"] = "N";// 로그 저장
									imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//									imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//									imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
					        		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장
					    		}
					    		callLoading(null,"off");
					    		alert("시스템 오류입니다. 관리자에게 문의하세요.");
					    	}
					    });
					    
					}else{
			    		if(!isNull(imgObj["I_HOS"])){
							imgObj["I_STS"] = "N";// 로그 저장
							imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//							imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//							imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
			        		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장
			    		}
						callLoading(null,"off");
						alert("시스템 오류입니다. 관리자에게 문의하세요.");
					}					
				});
			}
	};
	
	var viewer = new m2soft.crownix.Viewer( viewerUrl, "crownix-viewer");
	viewer.addToolbarItem(jpgItem); //myItem으로 버튼을 생성한 후 addToolbarItem으로 버튼 추가
	viewer.addToolbarItem(pdfItem); //myItem으로 버튼을 생성한 후 addToolbarItem으로 버튼 추가
	viewer.hideToolbarItem(["save"]); //툴바의 버튼을 비활성
	var eventHandler= function(event){  
//		//console.log(event.isError);
		if(event.isError){
    		if(!isNull(imgObj["I_HOS"])){
//    			var I_LOGMNU = "<%=menu_cd%>";
//    			var I_LOGMNM = "<%=menu_nm%>";
//				imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//				imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
				imgObj["I_STS"] = "N";// 로그 저장
				imgObj["I_MSG"] = event.lastErrorMsg;// 로그 저장
				ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장
			}
		}
	}; 
	viewer.bind('report-finished', eventHandler);
    viewer.openFile(mrd_path, param,{timeout:180, pdfReaderNotFoundMessage:'PDF Reader를 찾을 수 없습니다. <br/><br/>인쇄를 위해서 아래 주소에서 PDF Reader를 설치하시기 바랍니다.<br/><br/><a href="https://get.adobe.com/reader"targer="_blank" style="text-decoration:underline;color:blue;">https://get.adobe.com/reader</a>'});
    
    // 2020.03.18 - 결과지출력 버튼 클릭 후, 인쇄 컬럼 아래 버튼을 클릭할 경우 인쇄창까지 바로 뜨도록 한다.
    if(crownixVeiwer_print_YN == "STSD_PRINT"){
    	viewer.print({isServerSide: true, limitedPage:100});
    }	
    
}

function downloadReport(viewerUrl, mrd_path, param, rdServerSaveDir, systemDownDir,downFileName, file_ext){
	var invoker = new m2soft.crownix.ReportingServerInvoker(viewerUrl);
	invoker.addParameter('opcode', '500');
	invoker.addParameter('mrd_path', mrd_path);
	invoker.addParameter('mrd_param', param);
	// 브라우저에서 pdf 인쇄를 동작하게 하려면 /rpdfprintdialog 파라미터가 필요함
	//invoker.addParameter('mrd_param', '/rpdfprintdialog /rfn [sample.txt]');
	//파일 확장자
	invoker.addParameter('export_type', file_ext.toLowerCase() );
	invoker.addParameter('protocol', 'sync');
	//alert("pdf down2");
	// export 중 에러 발생시 에러를 감지 할 수 있음.
	// 이중화 환경에서 "export_name"을 사용하는 경우에는 정상동작하지 않음.
	// 이중화 환경에서 "export_name"을 사용하는 경우, 파일 다운로드를 위해서 invoker_downloadProtocolFile.html을 사용하여야 함.
	
	invoker.invoke(function(response) {
		//로딩 띄우기
		callLoading(null,"on");
		
		var ret = response.split('|');
		var resultCode = ret[0];
		var rdFilePathName = ret[1];
			
		var fileSavePath = rdServerSaveDir + "/"+rdFilePathName
		
		if(resultCode == '1'){
			$.when(function () {
		        var def = jQuery.Deferred();
		        window.setTimeout(function () {
		        	//document.getElementById('file_down').src = "/comm_reportFileDown.do?file_path="+fileSavePath+"&systemDownDir="+encodeURIComponent(systemDownDir+"/PDF")+"&file_name="+encodeURIComponent(downFileName+".pdf")+"&file_ext=pdf";
		        	document.getElementById('file_down').src = "/comm_reportFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir+"/PDF")+"&file_name="+encodeURIComponent(downFileName+"."+file_ext.toLowerCase() )+"&file_ext="+file_ext+"&dpi300_hos_gubun="+dpi300_hos_gubun;
		        	def.resolve();
		        }, 100);
		        return def.promise();
		    }())
		    .done(function () {
		    	if(resultCode == '1') {
		    		//로딩 닫기
		    		callLoading(null,"off");
		    	}else{
		    		callLoading(null,"off");
		    		alert("시스템 오류입니다. 관리자에게 문의하세요.");
		    	}
		    });
		}else{
			callLoading(null,"off");
			alert("시스템 오류입니다. 관리자에게 문의하세요.");
		}
		
	    
	});
}

//pdf로 파일을 다운 후 jpg로 변경
function downloadReportPdfJpg(viewerUrl, mrd_path, param, rdServerSaveDir, systemDownDir,downFileName, file_ext){
	var invoker = new m2soft.crownix.ReportingServerInvoker(viewerUrl);
	invoker.addParameter('opcode', '500');
	invoker.addParameter('mrd_path', mrd_path);
	invoker.addParameter('mrd_param', param);
	// 브라우저에서 pdf 인쇄를 동작하게 하려면 /rpdfprintdialog 파라미터가 필요함
	//invoker.addParameter('mrd_param', '/rpdfprintdialog /rfn [sample.txt]');
	//파일 확장자
	invoker.addParameter('export_type', "pdf" );
	invoker.addParameter('protocol', 'sync');
	//alert("pdf down2");
	// export 중 에러 발생시 에러를 감지 할 수 있음.
	// 이중화 환경에서 "export_name"을 사용하는 경우에는 정상동작하지 않음.
	// 이중화 환경에서 "export_name"을 사용하는 경우, 파일 다운로드를 위해서 invoker_downloadProtocolFile.html을 사용하여야 함.
	
	invoker.invoke(function(response) {
		//로딩 띄우기
		callLoading(null,"on");
		
		var ret = response.split('|');
		var resultCode = ret[0];
		var rdFilePathName = ret[1];
			
		var fileSavePath = rdServerSaveDir + "/"+rdFilePathName
		
		if(resultCode == '1'){
			$.when(function () {
		        var def = jQuery.Deferred();
		        window.setTimeout(function () {
		        	//document.getElementById('file_down').src = "/comm_reportFileDown.do?file_path="+fileSavePath+"&systemDownDir="+encodeURIComponent(systemDownDir+"/PDF")+"&file_name="+encodeURIComponent(downFileName+".pdf")+"&file_ext=pdf";
		        	document.getElementById('file_down').src = "/comm_reportPDFJPGFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir+"/"+ file_ext.toUpperCase() )+"&file_name="+encodeURIComponent(downFileName)+"&file_ext="+file_ext+"&dpi300_hos_gubun="+dpi300_hos_gubun;
		        	def.resolve();
		        }, 100);
		        return def.promise();
		    }())
		    .done(function () {
		    	if(resultCode == '1') {
		    		//로딩 닫기
		    		callLoading(null,"off");
		    	}else{
		    		callLoading(null,"off");
		    		alert("시스템 오류입니다. 관리자에게 문의하세요.");
		    	}
		    });
		}else{
			callLoading(null,"off");
			alert("시스템 오류입니다. 관리자에게 문의하세요.");
		}
		
	    
	});
}
	
	
	function downloadZipReport(viewerUrl, mrd_path, param, rdServerSaveDir, systemDownDir,downFileName, file_ext,recvObj, dpi300_hos_gubun, imgEachHos){
		
		var invoker = new m2soft.crownix.ReportingServerInvoker(viewerUrl);
		invoker.addParameter('opcode', '500');
		invoker.addParameter('mrd_path', mrd_path);
		invoker.addParameter('mrd_param', param);
		// 브라우저에서 pdf 인쇄를 동작하게 하려면 /rpdfprintdialog 파라미터가 필요함
		//invoker.addParameter('mrd_param', '/rpdfprintdialog /rfn [sample.txt]');
		//파일 확장자
		invoker.addParameter('export_type', file_ext.toLowerCase() );
		invoker.addParameter('protocol', 'sync');
		//alert("pdf down2");
		// export 중 에러 발생시 에러를 감지 할 수 있음.
		// 이중화 환경에서 "export_name"을 사용하는 경우에는 정상동작하지 않음.
		// 이중화 환경에서 "export_name"을 사용하는 경우, 파일 다운로드를 위해서 invoker_downloadProtocolFile.html을 사용하여야 함.
		
//		console.log("### cjw ### reportPop.js : dpi300_hos_gubun : 00 : "+dpi300_hos_gubun);
		
		invoker.invoke(function(response) {
			//로딩 띄우기
			var ret = response.split('|');
			var resultCode = ret[0];
			var rdFilePathName = ret[1];
			var fileSavePath = rdServerSaveDir + "/"+rdFilePathName;
			
			var dpi300_hos_gubun_val = dpi300_hos_gubun;
//			console.log("### cjw ### reportPop.js : dpi300_hos_gubun_val : 01 : "+dpi300_hos_gubun_val);
			
			if(resultCode == '1'){
				$.when(function () {
			        var def = jQuery.Deferred();
			        window.setTimeout(function () {
			    		var f = document.reportForm;
			    		f.rdFilePathName.value		=  rdFilePathName;
			    		f.file_name.value			=  (downFileName+"."+file_ext.toLowerCase() );
			    		
//			        	console.log("   downFileName    ===   "+downFileName);

			    		FileNm += downFileName+"."+file_ext.toLowerCase() +";";
			    		
		    			recvObj.I_STS = "Y";
		    			recvObj.rdFilePathName = rdFilePathName;
		    			recvObj.file_name = (downFileName+"."+file_ext.toLowerCase() );
		    			
//		    			console.log("### cjw ### reportPop.js : dpi300_hos_gubun_val : 02 : "+dpi300_hos_gubun_val);
		    			recvObj.dpi300_hos_gubun = dpi300_hos_gubun_val;
//		    			console.log("### cjw ### reportPop.js : dpi300_hos_gubun_val : 03 : "+dpi300_hos_gubun_val);
//		    			console.log("### cjw ### reportPop.js : listCnt : "+listCnt);
		    			
//						formData.push({ name: "I_LOGEFG",value : "FD"});
		    			
		    			
		    			recvObj.systemDownDir = systemDownDir;
//		    			console.log("systemDownDir : "+systemDownDir);

						recvObj.imgEachHos = imgEachHos;

						console.log("### cjw ### reportPop.js : recvImgReportFileDown.do 호출 ");
						//ajaxCall("/recvImgReportFileDown.do",recvObj,false);
			        	if(listCnt>1){
			        		console.log("### cjw ### reportPop.js : recvImgReportFileDown.do 호출 ");
			        		ajaxCall("/recvImgReportFileDown.do",recvObj,false);
			        	}else{
			        		console.log("### cjw ### reportPop.js : recvImgReportOneFileDown.do 호출 ");
				    		callLoading(null,"off");
				    		f.downfile_name.value =  (downFileName+"."+file_ext.toLowerCase() );
			    			recvObj.downfile_name = encodeURIComponent(downFileName+"."+file_ext.toLowerCase() );
			    			recvObj.file_name = (downFileName+"."+file_ext.toLowerCase() );

				    		f.recvObj.value				=  JSON.stringify(recvObj);
		    				f.action = "/recvImgReportOneFileDown.do";
		    				f.target = "file_down";       //target을 iframe으로 잡는다.
		    				f.submit();                // submit 호출
			        		def.resolve();
			        	}
			        }, 200);
			        return def.promise();
			    }())
			    .done(function () {
			    	if(resultCode == '1') {
			    		//로딩 닫기
//			    		callLoading(null,"off");
			    	}else{
			    		errorCnt++;
						errorImg += recvObj.F100NAM+"("+ recvObj.P_F010FKN+""+ recvObj.P_HOS_GCD+")"+"</br>";
		    			recvObj.I_STS = "N";//에러 로그 저장
		    			recvObj.I_ERR = "이미지 저장 에러  "+response;//에러 로그 저장
		        		ajaxCall("/ImgLogSave.do",recvObj,false);//에러 로그 저장
			    	}
			    });
			}else{
				var sp_hos_gcd,sp_f010fkn;
				var P_HOS_GCD =  recvObj.P_HOS_GCD;
				var P_F010FKN =  recvObj.P_F010FKN;
				if(P_HOS_GCD.indexOf("||")>-1){
					sp_hos_gcd =  recvObj.P_HOS_GCD.split("||" );
					sp_f010fkn =  recvObj.P_F010FKN.split("||" );
		            $.each(sp_hos_gcd,function(idx,v){
		            	errorImg += recvObj.F100NAM+" ["+ sp_hos_gcd[idx]+"] "+ sp_f010fkn[idx]+"</br>";
					});
				}else{
					errorImg += recvObj.F100NAM+" ["+ P_HOS_GCD+"] "+ P_F010FKN+""+"</br>";
				}
				
				errorCnt++;
    			recvObj.I_ERR = " 이미지 저장 에러  "+response;//에러 로그 저장
    			recvObj.I_STS = "N";//에러 로그 저장
        		ajaxCall("/ImgLogSave.do",recvObj,false);//에러 로그 저장
			}
		});
	}

	// sms link로 방문하여 결과지 다운로드 시 사용
	function setMobileReportViewer(mrd_path
							, param
							, viewerUrl
							, rdServerSaveDir
							, systemDownDir
							, downFileName
							, imgObj
							, dpi300_hos_gubun
							, crownixVeiwer_print_YN){
		//console.log(rdServerSaveDir);
		//console.log(systemDownDir);
		//console.log("downFileName      ==== "+downFileName);
		//console.log(imgObj);
//		grid["I_LOGMNU"] = I_LOGMNU;
//		grid["I_LOGMNM"] = I_LOGMNM;
		
		var viewer = new m2soft.crownix.Viewer( viewerUrl, "crownix-viewer");
		viewer.hideToolbar();
		var eventHandler= function(event){  
//			//console.log(event.isError);
			if(event.isError){
	    		if(!isNull(imgObj["I_HOS"])){
//	    			var I_LOGMNU = "<%=menu_cd%>";
//	    			var I_LOGMNM = "<%=menu_nm%>";
//					imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//					imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
					imgObj["I_STS"] = "N";// 로그 저장
					imgObj["I_MSG"] = event.lastErrorMsg;// 로그 저장
					ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장
				}
			}
		}; 
		viewer.bind('report-finished', eventHandler);
	    viewer.openFile(mrd_path, param,{timeout:180, pdfReaderNotFoundMessage:'PDF Reader를 찾을 수 없습니다. <br/><br/>인쇄를 위해서 아래 주소에서 PDF Reader를 설치하시기 바랍니다.<br/><br/><a href="https://get.adobe.com/reader"targer="_blank" style="text-decoration:underline;color:blue;">https://get.adobe.com/reader</a>'});
	    
	    // 2020.03.18 - 결과지출력 버튼 클릭 후, 인쇄 컬럼 아래 버튼을 클릭할 경우 인쇄창까지 바로 뜨도록 한다.
	    if(crownixVeiwer_print_YN == "STSD_PRINT"){
	    	viewer.print({isServerSide: true, limitedPage:100});
	    }
	    
	    return {
	    	jpgDownHandler: function() {
				//alert("pdf down");
				/**
				http://219.252.39.132:9001/ReportingServer/manager/index.html 에서
				
				서버환경에 sever tab 안에 
				report.save.dir ==> 레포트 서버가 설치된 서버의 절대 경로로 하면 해당 경로로 저장
				                ==> 경로 지정을 안하면 /ReportingServer/report 폴더로 지덩 됨
				                ==> 경로 예
				                http://219.252.39.132:9001/ReportingServer/report/2018/12/14/20181214205232643_2.jpg
				report.dir.pattern ==> 기본 날짜 지정 yyyy/mm/dd                
				*/
				m2soft.crownix.ReportingServerInvoker.debug = true;
				
				var invoker = new m2soft.crownix.ReportingServerInvoker(viewerUrl);
				invoker.addParameter('opcode', '500');
				invoker.addParameter('mrd_path', mrd_path);
				invoker.addParameter('mrd_param', param);
				invoker.addParameter('export_type', 'jpg');
				invoker.addParameter('protocol', 'sync');
				// export 중 에러 발생시 에러를 감지 할 수 있음.
				// 이중화 환경에서 "export_name"을 사용하는 경우에는 정상동작하지 않음.
				// 이중화 환경에서 "export_name"을 사용하는 경우, 파일 다운로드를 위해서 invoker_downloadProtocolFile.html을 사용하여야 함.
				
				invoker.invoke(function(response) {
					//로딩 띄우기
					callLoading(null,"on");
					setTimeout(function() {
						var ret = response.split('|');
						var resultCode = ret[0];
						var rdFilePathName = ret[1];
						
						var fileSavePath = rdServerSaveDir + "/"+rdFilePathName;
			
						if(resultCode == '1'){
							$.when(function () {
						        var def = jQuery.Deferred();
						        window.setTimeout(function () {
						        	
						        	//document.getElementById('file_down').src = "/sms_reportFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir)+"&file_name="+encodeURIComponent(downFileName+".jpg")+"&file_ext=jpg&dpi300_hos_gubun="+dpi300_hos_gubun + "&ishiddenframe=Y";
						        	//document.getElementById('file_down').src = "/comm_reportFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir+"/JPG")+"&file_name="+encodeURIComponent(downFileName+".jpg")+"&file_ext=jpg";

									// get 에서 Post 로 변경함. saveMCWRKIMG를 처리하기 위해서는 병원코드가 담겨 있는 imgObj 를 넘겨야 함.
									var f = document.reportForm;
									f.rdServerSaveDir.value = rdServerSaveDir;
									f.rdFilePathName.value = rdFilePathName;
									f.systemDownDir.value = encodeURIComponent(systemDownDir);
									f.file_name.value = encodeURIComponent(downFileName+".jpg");
									f.file_ext.value = "jpg";

									imgObj.I_STS = "Y";	// 일단은 저장하기 위해 Y로 세팅한다.
									f.recvObj.value = JSON.stringify(imgObj);
									f.action = "/sms_reportFileDown.do";
									f.target = "file_down";       //target을 iframe으로 잡는다.
									f.submit();                // submit 호출

						            def.resolve();
						        }, 100);
						        return def.promise();
						    }())
						    .done(function () {
						    	if(resultCode == '1') {
						    		//로딩 닫기
						    		if(!isNull(imgObj["I_HOS"])){
						    			imgObj["I_STS"] = "Y";// 로그 저장
//										imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//										imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
//						    			ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 - 2021.10.14 : sms link로 결과지 조회 시 Log 남기지 않는다.
						    		}
						    		callLoading(null,"off");
						    	}else{
						    		if(!isNull(imgObj["I_HOS"])){
							    		imgObj["I_STS"] = "N";// 로그 저장
										imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//										imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//										imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
//							    		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 - 2021.10.14 : sms link로 결과지 조회 시 Log 남기지 않는다.
						    		}
						    		callLoading(null,"off");
						    		alert("시스템 오류입니다. 관리자에게 문의하세요.");
						    	}
						    });
						}else{
				    		if(!isNull(imgObj["I_HOS"])){
								imgObj["I_STS"] = "N";// 로그 저장
								imgObj["I_ERR"] = " 이미지 저장 에러   "+response;//에러 로그 저장
//								imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//								imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
//								ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 - 2021.10.14 : sms link로 결과지 조회 시 Log 남기지 않는다.
				    		}
							callLoading(null,"off");
							alert("시스템 오류입니다. 관리자에게 문의하세요.");
						}
					}, 200);
				});
			},
			pdfDownHandler: function() {
				//alert("pdf down");
				/**
				http://219.252.39.132:9001/ReportingServer/manager/index.html 에서
				
				서버환경에 sever tab 안에 
				report.save.dir ==> 레포트 서버가 설치된 서버의 절대 경로로 하면 해당 경로로 저장
				                ==> 경로 지정을 안하면 /ReportingServer/report 폴더로 지덩 됨
				                
				report.dir.pattern ==> 기본 날짜 지정 yyyy/mm/dd                
				*/
				m2soft.crownix.ReportingServerInvoker.debug = true;
				
				var invoker = new m2soft.crownix.ReportingServerInvoker(viewerUrl);
				invoker.addParameter('opcode', '500');
				invoker.addParameter('mrd_path', mrd_path);
				invoker.addParameter('mrd_param', param);
				// 브라우저에서 pdf 인쇄를 동작하게 하려면 /rpdfprintdialog 파라미터가 필요함
				//invoker.addParameter('mrd_param', '/rpdfprintdialog /rfn [sample.txt]');
				invoker.addParameter('export_type', 'pdf');
				invoker.addParameter('protocol', 'sync');
				//alert("pdf down2");
				// export 중 에러 발생시 에러를 감지 할 수 있음.
				// 이중화 환경에서 "export_name"을 사용하는 경우에는 정상동작하지 않음.
				// 이중화 환경에서 "export_name"을 사용하는 경우, 파일 다운로드를 위해서 invoker_downloadProtocolFile.html을 사용하여야 함.
				
				invoker.invoke(function(response) {
					callLoading(null,"on");
					
					var ret = response.split('|');
					var resultCode = ret[0];
					var rdFilePathName = ret[1];
					
					var fileSavePath = rdServerSaveDir + "/"+rdFilePathName;
					//console.log(rdServerSaveDir);
					//console.log(rdFilePathName);
					//console.log(systemDownDir);
					//console.log(downFileName);
					
					
					
					if(resultCode == '1'){
						$.when(function () {
					        var def = jQuery.Deferred();
					        window.setTimeout(function () {
					        	//document.getElementById('file_down').src = "/comm_reportFileDown.do?file_path="+fileSavePath+"&systemDownDir="+encodeURIComponent(systemDownDir+"/PDF")+"&file_name="+encodeURIComponent(downFileName+".pdf")+"&file_ext=pdf";
					        	//document.getElementById('file_down').src = "/sms_reportFileDown.do?rdServerSaveDir="+rdServerSaveDir+"&rdFilePathName="+rdFilePathName+"&systemDownDir="+encodeURIComponent(systemDownDir+"/PDF")+"&file_name="+encodeURIComponent(downFileName+".pdf")+"&file_ext=pdf&dpi300_hos_gubun="+dpi300_hos_gubun + "&ishiddenframe=Y";

								// get 에서 Post 로 변경함. saveMCWRKIMG를 처리하기 위해서는 병원코드가 담겨 있는 imgObj 를 넘겨야 함.
								// pdf 일때는 saveMCWRKIMG 로 저장할 필요가 없기때문에 get 유지해도 되지만 일관성을 위해 post 로 변경.
								var f = document.reportForm;
								f.rdServerSaveDir.value = rdServerSaveDir;
								f.rdFilePathName.value = rdFilePathName;
								f.systemDownDir.value = encodeURIComponent(systemDownDir+"/PDF");
								f.file_name.value = encodeURIComponent(downFileName+".pdf");
								f.file_ext.value = "pdf";

								imgObj.I_STS = "Y";	// 일단은 저장하기 위해 Y로 세팅한다.
								f.recvObj.value = JSON.stringify(imgObj);
								f.action = "/sms_reportFileDown.do";
								f.target = "file_down";       //target을 iframe으로 잡는다.
								f.submit();                // submit 호출
					        	def.resolve();
					        }, 100);
					        return def.promise();
					    }())
					    .done(function () {
					    	if(resultCode == '1') {
					    		//로딩 닫기
					    		callLoading(null,"off");
					    		if(!isNull(imgObj["I_HOS"])){
						    		imgObj["I_STS"] = "Y";// 로그 저장
									imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//									imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//									imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
//					        		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 - 2021.10.14 : sms link로 결과지 조회 시 Log 남기지 않는다.
					    		}
					    	}else{
					    		if(!isNull(imgObj["I_HOS"])){
						    		imgObj["I_STS"] = "N";// 로그 저장
									imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//									imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//									imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
//					        		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 - 2021.10.14 : sms link로 결과지 조회 시 Log 남기지 않는다.
					    		}
					    		callLoading(null,"off");
					    		alert("시스템 오류입니다. 관리자에게 문의하세요.");
					    	}
					    });
					    
					}else{
			    		if(!isNull(imgObj["I_HOS"])){
							imgObj["I_STS"] = "N";// 로그 저장
							imgObj["I_ERR"] = " 이미지 저장 에러  "+response;//에러 로그 저장
//							imgObj["I_LOGMNU"] = I_LOGMNU;// 로그 저장
//							imgObj["I_LOGMNM"] = I_LOGMNM;// 로그 저장
//			        		ajaxCall("/ImgLogSave.do",imgObj,false);// 로그 저장 - 2021.10.14 : sms link로 결과지 조회 시 Log 남기지 않는다.
			    		}
						callLoading(null,"off");
						alert("시스템 오류입니다. 관리자에게 문의하세요.");
					}					
				});
			}
	    };
	}
