var mysql = require('mysql');
var connection = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : 'dk!tpr1tmdhkd',
    port : 3306,
    database : 'myDatabase'
});

module.exports.userLogin = function(id, pw, callback){
    connection.connect();
    connection.query('SELECT count(*) as isExist from users where id = ? and pw = ?',[id, pw], function(err, results){
        if(err){
            console.error(err);
//            throw err;
            callback({
                error: true,
                result: false,
                message: JSON.stringify(err)
            });
        }
        
        console.log(results[0].isExist);
    });
    connection.end();
}

module.exports.regist = function (id, pw, email, callback){
    
}