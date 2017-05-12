var AWS = require('aws-sdk');
var encrypt = require('../util/encrypt');
var tokenCenter = require('../util/jwtFactory');
AWS.config.update({
    region: 'us-west-2'
});
var dbClient = new AWS.DynamoDB.DocumentClient();
var userTableName = 'users';
var userInformationTableName = 'PlayerInformation';


//아이디가 있는 지 확인.
//콜백의 경우, 로그인 성공시 유저 데이터를 보낸다. 실패시 데이터 없이 보낸다.
var getUser = function(id, callback){
    console.log('database.getUser function');
    var params = {
        TableName: userTableName,
        Key: {
            "id": id
        }
    };
    
    dbClient.get(params, function(err, data){
       if(err){
           callback({
               error: true,
               data: null,
               message: JSON.stringify(err)
           });
       } else{
           console.log('database.getUser.getData function : ');
           console.dir(data);
           
           callback({
               error: false,
               data: data
           });
       }
    });
};

module.exports.userLogin = function (id, pw, callback) {
    getUser(id, function(result){
        if(result.error){
            callback({
                error: true,
                result: false,
                message: JSON.stringify(err)
            });
        } else{
            //아이디가 매치되는 경우. 로그인 성공
            if(result.data != null
               && Object.keys(result.data).length != 0 && result.data.Item.pw == pw){
                callback({
                    error: false,
                    result: true,
                    token: tokenCenter.signToken({id : result.data.Item.id})
                });
            }
            //아이디가 매치되지 않는 경우. 로그인 실패
            else{
                callback({
                    error: false,
                    result: false,
                });
            }
        }
    });
};

module.exports.regist = function (id, pw, email, callback) {
    console.log('database.regist function');
    var params = {
        TableName: userTableName,
        Item: {
            "id": id,
            "pw": pw,
            "email": email,
            "isFirst": true, // 첫 회원가입 후 유저정보 생성분기를 위함
            "RegistDay": new Date.now(),
            "LastConnectDay": new Date.now() // 추후 필요할 수도 있어서 저장
        }
    };
    
    //아이디가 존재하는지 확인
    getUser(id, function(result){
        if(result.error){
            callback(result);
        } else{
            //에러가 아닌경우.
            
            //기존 아이디가 존재하지 않는 경우
            if(Object.keys(result.data).length == 0){
                dbClient.put(params, function (err, data) {
                    if (err) {
                        //서버 문제 혹은 DynamoDB 처리결과에 문제가 있는 경우.
                        callback({
                            error: true,
                            result: false,
                            message: JSON.stringify(err)
                        });
                    } else {
                        //회원가입 완료
                        console.log('Registration Success');
                        callback({
                            error: false,
                            result: true
                        });
                    }
                });
            }
            //아이디가 있는 경우. 회원가입 실패
            else{
                console.log('Registration failed - already exist ID');
                callback({
                    error: false,
                    result: false
                });
            }
        }
    });
}

module.exports.getUserInformation = function (token){
    
}