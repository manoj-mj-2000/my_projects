const { tableExists, createTableInDB } = require("./db");

function parseJSON(jsonStr){
    // console.log("In parse function"+jsonStr);
    try{
        var jsonData = JSON.parse(jsonStr);

        var keys = Object.keys(jsonData);
        iterateKeys(keys, jsonData);
        return "success";
    } catch(error){
        console.log("parsing json error");
    }
    return "failed";
}
function iterateKeys(keys, jsonData){
    try{
        keys.forEach(key => {
            if(Array.isArray(jsonData[key])){
                // console.log("The JSON data is an array."+key);
                tableChecking(key, jsonData[key]);
            } else if (typeof jsonData[key] == 'object' && jsonData[key] != null){
                // console.log("The JSON data is an object."+key);
                tableChecking(key, jsonData);
                var objKeys = Object.keys(jsonData[key]);
                iterateKeys(objKeys, jsonData[key]);
            } else {
                // console.log('The JSON data is neither an array nor an object.'+key);
            }
        });
    }
    catch (error){
        console.log("error in iterate keys "+error);
    }
}

async function tableChecking(key, json){
    try{
        console.log("table checking: "+key);
        const exists = await tableExists(key);
        if(!exists && json !== null){
            createTable(key, json);
        }
        return true;
    } catch(error){
        console.log("error in table checking: ",error);
        return false;
    }
}

async function createTable(tableName, json){
    var cols = new Set();
    var types = [];
    var tableQuery = "CREATE TABLE IF NOT EXISTS "+tableName+" ( id int primary key,";
    if(Array.isArray(json)) {
        json.forEach(jobj => {
            var jkeys = Object.keys(jobj);
            jkeys.forEach(key => {
                if(Array.isArray(jobj[key]) && jobj[key].length > 0){
                    // console.log(":TableName:"+tableName+"::String value:"+JSON.stringify(jobj[key]));
                    // setGlobalValue(tableName, jobj[key]);
                } else {
                    if(!cols.has(key)){
                        cols.add(key);
                        var dataType = "varchar";
                        if(typeof jobj[key] === 'number'){
                            dataType = 'int';
                        }
                        types.push(dataType);
                    }
                }
            });
        });
    } else if(typeof json[tableName] == 'object' && json[tableName] != null) {
        var jobj = json[tableName];
        var keys = Object.keys(jobj);
        keys.forEach(key =>{
            if(!Array.isArray(jobj[key])){
                if(!cols.has(key)){
                    cols.add(key);
                    var dataType = "varchar";
                    if(typeof jobj[key] === 'number'){
                        dataType = 'int';
                    }
                    types.push(dataType);
                }
            }
        });
    }
    let size = cols.size;
    var i=0;
    var values = cols.values();
    for(var val of values){
        tableQuery += val +" "+ types[i];
        if(i !== cols.size-1){
            tableQuery += ',';
        }
        i++;
    }
    tableQuery += ')';
    createTableInDB(tableQuery);
}

/*async function checkForLookup(){
    console.log("Check for lookup");
    console.log(getGlobalValueTable());
    console.log(getGlobalValueJson());
    if(typeof jsonArr[0] === 'string'){
        var inputString = jsonArr[0];
        var match = inputString.match(/'([^']+)'/);
        var memberValue = match ? match[1] : null;
        var pExists = tableChecking(primayTable, null);
        var fkTab = memberValue.split('.')[0];
        var fkVal = memberValue.split('.')[1];
        var fExists = tableChecking(fkTab, null);

        var fkTableName = primayTable+"_"+fkTab +"s";
        var tableQuery = "CREATE TABLE IF NOT EXISTS "+fkTableName+" ( ";
        if(pExists){
            tableQuery += primayTable +" int,";
        }
        if(fExists){
            tableQuery += fkTab +" int,";
        }
        tableQuery += "CONSTRAINT fk1 FOREIGN KEY("+primayTable+") REFERENCES "+primayTable+"(id),";
        tableQuery += "CONSTRAINT fk2 FOREIGN KEY("+fkTab+") REFERENCES "+fkTab+"(id))";
        console.log(tableQuery);
    }
    console.log("checkForLookupEnds");
}*/

module.exports = { parseJSON }; 