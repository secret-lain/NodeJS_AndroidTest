//auth = 로그인과 유저등록과 같이 계정관련 이슈를 담당한다.

var database = require('../util/mongoose');
var token
var express = require('express');
var router = express.Router();


//아직 안써먹음
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

//회원가입
router.post('/', (req, res, next) => {
    console.log('/users/regist reached');
    database.regist(req.body.id, req.body.pw, req.body.email, function(result){
        if(result.error){
            res.status(status).json(result);
        } else{
            res.json(result);
        }
    });
});

//로그인시도
router.get('/login', (req, res, next) => {
    console.log('[' + req.query.id + '] user is try to login');
    database.userLogin(req.query.id, req.query.pw,function(result){
        if(result.error){
            res.status(status).json(result);
        } else{
            res.json(result);
        }
    });
});


module.exports = router;