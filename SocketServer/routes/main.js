//인게임 정보변경을 담당한다.
var database = require('../util/mongoose');
var tokenCenter = require('../util/jwtFactory');
var express = require('express');
var router = express.Router();

/* GET home page. */
/*router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});*/

router.put('/nickname', function (req, res) {
    var decodedToken = tokenCenter.verify(req.body.token);
    if (decodedToken == null && req.body.nickname === undefined) {
        res.redirect('/');
    } else {
        console.log('1');
        database.update({
                '_id': decodedToken.id
            }, {
                $set: {
                    nickname: req.body.nickname
                }
            },
            function (result) {
            console.log('2');
                res.json(result);
            });
    }
});

module.exports = router;
