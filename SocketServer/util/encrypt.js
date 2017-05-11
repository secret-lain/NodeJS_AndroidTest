/*
Desc : 암호화와 복호화를 한다.
Dependency : crypto-js

method :
- decrypt(string encryptionData) return decryptionJsonObject
- encrypt(object jsonInfo) return string encryptionData
*/
var crypto = require('crypto-js');
var secret = 'white_L0v3r';

var decrypt = function(encryptionData){
    var decryptionDataByte = crypto.AES.decrypt(encryptionData, secret);
    var decryptionDataObject = JSON.parse(decryptionDataByte.toString(crypto.enc.Utf8));

    return decryptionDataObject;
}

var encrypt = function(object){
    var encryptionData = crypto.AES.encrypt(JSON.stringify(object), secret);
    return encryptionData;
}

//같은 정보를 인코딩하면 결과가 같아지고, 그렇다고 키를 난수로 하면 복호화가 안된다.
//그러므로 시간 데이터를 추가하여 합친다.
module.exports.getEncryption = function(jsonInfo){
    var container = [];
    var createTime = Date.now();

    container.push(jsonInfo);
    container.push({'createTime':createTime});

    encrypt(container);
    return encryptionData + '';
};

//input encyprtedSessionId
module.exports.getID = function(encryptionData){
    var userInfo = decrypt(encryptionData);

    return userInfo[0].id;
}
