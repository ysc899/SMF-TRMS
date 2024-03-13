class NdsApi {

	// 개발서버
	static baseUrl = "https://vc-rest.medidid.com:4001";
	// 상용서버
	// static baseUrl = "https://vc-rest.ansimpass.com:4001";
	
	static dbName = "did-metadata";
	static storeName = "metadata";
	static mySGIndexedDB = "";
	
    constructor(baseUrl) {
        if (!baseUrl) {
            throw new Error('baseUrl is null');
        }

        this.baseUrl = baseUrl;
    }
    
    static setBaseUrl(baseUrl){
        if (!baseUrl) {
            throw new Error('baseUrl is null');
        }

        this.baseUrl = baseUrl;
    }
    
    static initIndexedDB(){
		// indexedDb 선언(Vue.js 기준 beforeCreate에 선언)
	   	var myIndexedDB = new KeyValueStore(this.dbName, this.storeName);
	   	//Wallet 선언(Vue.js 기준 beforeCreate에 선언)
	   	var myWallet = new CryptoWallet();
	 	this.mySGIndexedDB = new SGIndexedDB(myIndexedDB);
    }
    
    static getIndexedDB(){
    	if(!this.mySGIndexedDB){
    		// indexedDb 선언(Vue.js 기준 beforeCreate에 선언)
		   	var myIndexedDB = new KeyValueStore(this.dbName, this.storeName);
		   	//Wallet 선언(Vue.js 기준 beforeCreate에 선언)
		   	var myWallet = new CryptoWallet();
		 	this.mySGIndexedDB = new SGIndexedDB(myIndexedDB);
    	}
    	
    	return this.mySGIndexedDB;
    }
    
    static dateFormat(date) {
        let month = date.getMonth() + 1;
        let day = date.getDate();
        let hour = date.getHours();
        let minute = date.getMinutes();
        let second = date.getSeconds();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;
        hour = hour >= 10 ? hour : '0' + hour;
        minute = minute >= 10 ? minute : '0' + minute;
        second = second >= 10 ? second : '0' + second;

        //return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
        return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute;
	}
    
    /* 약관조회 */
   	static getTerms(){
   		return $.ajax({
   			// 상용서버 약관 안나옴
			// url : "https://bc-rest.ansimpass.com:3000/terms/%EA%B3%B5%ED%86%B5"
			 url : "https://bc-rest.medidid.com:3000/terms/%EA%B3%B5%ED%86%B5"
			, type : "GET"
			, contentType: 'application/json; charset=UTF-8'
			, dataType: "json"
			, success : function(response){
				return response;
			},error:function(x,e){
			 	return x.responseJSON;
			}
		});
   	}
   	
    /* 4.1.1 [POST] Holder DID 생성  */
    static setDidHolder(userid){
    	return $.ajax({
			 url : this.baseUrl + "/api/v1/dids/holder"
			, type : "post"
			, data : {"name" : userid}
			, dataType: "json"
			, success : function(response){
				return response;
			},error:function(x,e){
			 	return x.responseJSON;
			}
		});
	}
	
    /* 4.1.2 [GET] Holder DID 조회 */
    static getDidHolder(userid){
    	return $.ajax({
			 url : this.baseUrl + "/api/v1/dids/holder/" + userid
			, type : "GET"
			, contentType: 'application/json; charset=UTF-8'
			, dataType: "json"
			, success : function(response){
				return response;
			},error:function(x,e){
			 	return x.responseJSON;
			}
		});
	}
	
	/* 4.1.3 [DELETE] Holder DID 삭제  */
    static deleteDidHolder(userid){
    	return $.ajax({
			 url : this.baseUrl + "/api/v1/dids/holder/" + userid
			, type : "delete"
			, dataType: "json"
			, success : function(response){
				return response;
			},error:function(x,e){
			 	return x.responseJSON;
			}
		});
	}
	
	/* 4.2.3 [POST] VC 생성 – Hospital(병원 신원확인)  */
    static getHospitalVC(userId, paramObj){
    	paramObj["credentialSubject"]["registerDate"] = dateFormat(new Date());
    	return $.ajax({
			 url : this.baseUrl + "/api/v1/vcs/issueVC"
			, type : "post"
			, data : paramObj
			, dataType: "json"
			, success : function(response){
				return response;
			},error:function(x,e){
			 	return x.responseJSON;
			}
		});
	}
	
	/* 4.3.1 [POST] 약관 동의 이력 등록 */
    static setAgreeHistory(historyParam){
    	historyParam["regDate"] = dateFormat(new Date()).substring(0,10);
    	return $.ajax({
			 url : this.baseUrl + "/api/v1/history/userTermsAgree"
			, type : "post"
			, data : historyParam
			, dataType: "json"
			, success : function(response){
				return response;
			},error:function(x,e){
			 	return x.responseJSON;
			}
		});
	}
	
	 

}