var jwt = require('jsonwebtoken');
var key = "wh0+e_Pr1ncezz";
var tokenFactory = [];

//payload example = { id : "android" }
module.exports.signToken = function(payload){
    var token = jwt.sign(payload, key, {
        algorithm : 'HS256', //"HS256", "HS384", "HS512", "RS256", "RS384", "RS512" default SHA256
        expiresIn : '24h' //expires in 24 hours
    });
    
    console.log('[JWT] signed - ' + payload.id + ' to ' + token);
    return token;
}

module.exports.verify = function(token){
    try{
        var decoded = jwt.verify(token, key);
        return decoded;
    } catch(err){
        console.log(token + ' occured error : ' + err);
        return null;
    }    
}