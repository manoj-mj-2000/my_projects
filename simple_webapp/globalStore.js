global.globalStore = {};

const hostname = '127.0.0.1';
const port = 3000;

function setGlobalValue(value) {
    if(getGlobalValue() == null || getGlobalValue() == undefined || getGlobalValue() === '') {
        global.globalStore['value'] = value;
    }
    console.log(`Value set for value: = ${value}`);
}

function getGlobalValue() {
    console.log("Print value: ", global.globalStore['value']);
  return global.globalStore['value'];
}

function setGlobalTableName(name) {
    if(getGlobalTableName() == null || getGlobalTableName() == undefined || getGlobalTableName() === '') {
        global.globalStore['name'] = name; 
    }
    console.log(`Value set for name: = ${name}`);
}

function getGlobalTableName() {
    console.log("Print name: ", global.globalStore['name']);
  return global.globalStore['name'];
}

module.exports = { setGlobalValue, getGlobalValue, setGlobalTableName, getGlobalTableName };
