var tokenCenter = require('../util/jwtFactory');
var mongoose = require('mongoose');
var database;
var userInfoSchema;
var userModel;
var isDBReady = false;

//자동으로 실행된다.
function connectDB(){
    var databaseUrl = 'mongodb://localhost:27017/db';
    
    //mongoose.Promise = global.Prommise;
    mongoose.connect(databaseUrl);
    database = mongoose.connection;
    
    database.on('error', console.error.bind(console, 'mongoose connection error. '));
    database.on('open', function(){
        console.log('[database]mongo db connection established');
        isDBReady = true;
        
        userInfoSchema = mongoose.Schema({
            _id: 'string',
            password: 'string',
            nickname: 'string',
            email: 'string',
            createDate : 'string',
            lastConnectDate : 'string'
        });
        
        userModel = mongoose.model('userInfo', userInfoSchema);
        console.log('userModel 정의됨');
    });
    
    database.on('disconnected', function(){
        isDBReady = false;
        console.log('[database]mongo db disconnected. connection retry after 2 sec..');
        
        setInterval(connectDB, 2000);
    });
}
connectDB();

module.exports.update = function(query, update, callback){
    console.log('3');
    userModel.update(query, update, function(err, data){
        console.log('4');
        if(err){
            callback({error: true, result: false, message: JSON.stringify(err)});
        } else{
            console.log(data);
            callback({
                error: false, result: true
            });
        }
    })
}


//유저 로그인
module.exports.userLogin = function(id, pw, callback){
    if(isDBReady){
        
        //로그인 정보 확인
        userModel.findOne({'_id' : id, 'password' : pw}, function(err, data){
            if(err){ //데이터베이스에서 에러 발생시.
                callback({
                    error: true,
                    result: false,
                    message: JSON.stringify(err)
                });
            }
            
            //로그인 성공시
            if(data != null){
                
                callback({
                    error: false,
                    result: true,
                    nickname: data.nickname, //닉네임이 ''이면 첫접속이라는 뜻~
                    token: tokenCenter.signToken({ id : data._id })
                });
                
                //최종접속일 갱신
                data.lastConnectDate = Date("<YYYY-mm-ddTHH:MM:ss>");                
                data.save(function (err){
                    console.log(err);
                });
            } else{
                //로그인 실패시.
                callback({
                    error: false,
                    result: false,
                });
            }
        });
    } else{
        //데이터베이스가 애초에 준비되지 않은 경우. 재접속을 하긴 한다.
        callback({
            error: true,
            result: false,
            message: '데이터베이스가 준비되지 않았습니다.'
        });
    }
}

//회원 가입
module.exports.regist = function (id, pw, email, callback){
    if(isDBReady){
        //모든 정보는 String Type으로 들어가긴 간다.
        userModel.create({
            '_id' : id,
            'password' : pw,
            'email' : email,
            nickname : '',
            createDate : Date(),
            lastConnectDate : Date()            
        }, function(err, data){
            //데이터베이스에서의 에러
            if(err){
                callback({
                    error: true,
                    result: false,
                    message: JSON.stringify(err)
                });
            }
            
            //문제없이 추가된 경우
            if(data != null){
                callback({
                    error: false,
                    result: true,
                });
            }
            else{
                //문제있이 추가 안된 경우
                callback({
                    error: false,
                    result: false,
                });
            }
            
        });        
    } else{
        callback({
            error: true,
            result: false,
            message: '데이터베이스가 준비되지 않았습니다.'
        });
    }
}

module.exports.mongoose = mongoose;
module.exports.userModel = userModel;