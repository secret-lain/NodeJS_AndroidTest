var database = require('../util/database');
var express = require('express');
var router = express.Router();


// /users 하부구조
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.put('/regist', (req, res, next) => {
    console.log('/users/regist reached');
    database.regist(req.body.id, req.body.pw, req.body.email, function(result){
        res.json(result);
    });
});

router.post('/login', (req, res, next) => {
    console.log('/users/regist reached');
    database.userLogin(req.body.id, req.body.pw,function(result){
        res.json(result);
    });
});

module.exports = router;
