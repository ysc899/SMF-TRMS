/*
Copyright SecureKey Technologies Inc. All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/

/**
 * SGIndexedDB is key value based store
 * @class
 */
class SGIndexedDB {
    constructor(indexedDB) {
        if (!indexedDB) {
            throw new Error('indexedDB is null');
        }

        this.indexedDB = indexedDB;
    }

    async createHolderDid(user_id, data) {
        //세션에서 아이디 정보가져오기
        var user_id = user_id;
        let exist = false;
        //기존의 인덱스db 데이터 로드
        await this.indexedDB.get(user_id).then((result) => {
            if (result) {
                exist = true;
            }
        });
        if (exist) {
            console.log("이미 wallet이 생성되어 있습니다.");
        } else {
            // indexedDB 넣을 데이터 불러오기
            this.dbdata = {
                id: user_id,
                did: data.did,
                value: data,
                certData: {
                    ci: "ci",
                    name: "name",
                    hp_no: "010-0000-0000",
                    email_addr: "kkk@ns.co.kr",
                },
                vcList: [],
            };
            this.indexedDB.store(user_id, this.dbdata);
            this.indexedDB.getAll().then((result) => {
                this.dataArray = result;
                console.log(this.dataArray);
            });
        }
    }
    
    async deleteHolder(user_id) { 
    	//세션에서아이디정보가져오기
	    /*this.$q.dialog({
	        title: "Confirm",
	        message: `Holder ${user_id}의wallet을정말로삭제하시겠습니까?`,
	        cancel: true,
	        persistent: true,
	    }).onOk(() => { 
	    	//indexedDB에서아이디정보기반데이터삭제하기
	        this.indexedDB.delete(user_id);
	        this.indexedDB.getAll().then((result) => {
	            this.dataArray = result;
	            console.log(this.dataArray);
	        });
	    }).onCancel(() => {}).onDismiss(() => {});*/
	    
	    // 자체 confirm 으로 변경
	    this.indexedDB.delete(user_id);
        this.indexedDB.getAll().then((result) => {
            this.dataArray = result;
            console.log(this.dataArray);
        });
	}
	
	async createHospitalVc(user_id, data) {
	    //세션에서 아이디 정보가져오기
	    //기존의 인덱스db 데이터 로드
	    await this.indexedDB.get(user_id).then((result) => {
	        console.log(result);
	        this.dbdata = result;
	    });
	    // indexedDB 넣을 데이터 불러오기
	    this.dbdata = {
	        id: this.dbdata.id,
	        did: this.dbdata.did,
	        value: this.dbdata.value,
	        certData: this.dbdata.certData,
	        vcList: this.dbdata.vcList,
	    };
	    //새로생성 할 vc 데이터
	    /*var hospitalVcExample = {
	        issuerName: data.issuerName,
	        vcType: data.vcType,
	        credentialSubject: {
	            hospitalID: "myhospital36",
	            hospitalCode: "11370319",
	            hospitalName: "삼육의료원",
	            hospitalChief: "홍길동",
	            hospitalPhone: "010-1234-5678",
	            registerDate: "2021-08-12 11:35"
	        }
	    };*/
	    
	    //vcData 넣기
	    this.dbdata.vcList.push(data);
	    // this.indexedDB.store(this.key, { id: this.key, value: this.val });
	    this.indexedDB.store(user_id, this.dbdata);
	    this.indexedDB.getAll().then((result) => {
	        this.dataArray = result;
	        console.log(this.dataArray);
	    });
	}

}