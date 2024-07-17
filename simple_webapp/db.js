const { Client } = require('pg');

const client = new Client({
  user: 'postgres',
  host: 'localhost',
  database: 'simple',
  password: '',
  port: 5432,
});

client.connect();

async function tableExists(tableName) {
    const query = `
      SELECT EXISTS (
        SELECT FROM information_schema.tables 
        WHERE table_schema = 'public' 
        AND table_name = $1
      );
    `;
    const values = [tableName];
    try {
      const res = await client.query(query, values);
      if (res.rows.length > 0) {
        return res.rows[0].exists;
      } else {
        console.error('No rows returned');
        return false;
      }
    } catch (err) {
      console.error('Error checking table existence:', err);
    }
  }

async function createTableInDB(createTableQuery){
    try{
      // console.log(createTableQuery);
      const res = await client.query(createTableQuery);
      // console.log(`Table created successfully`);
      return "success";
    }
    catch(error){
        console.log("Error in create table: ", error);
        return "failed"
    }
}

async function getTablesNdColumns(){
  try {
    const result = await client.query(`
      SELECT
        table_name,
        column_name,
        data_type
      FROM
        information_schema.columns
      WHERE
        table_schema = 'public'
      ORDER BY
        table_name,
        ordinal_position;`);

    const tables = {};

    result.rows.forEach(row => {
      if (!tables[row.table_name]) {
        tables[row.table_name] = [];
      }
      tables[row.table_name].push({
        column_name: row.column_name,
        data_type: row.data_type
      });
    });
    return tables;
  } catch (err) {
    console.error('Error executing query', err.stack);
    return {};
  }
}

async function editColumn(json){
  var obj = JSON.parse(json);
  var newName = obj.colName;
  var newType = obj.dataType;
  var oldName = obj.oldName;
  var oldType = obj.oldType;
  var table =  obj.table;

  try{
    if(oldType !== newType){
      var alterQuery = "ALTER TABLE "+table+" ALTER COLUMN "+oldName+" TYPE "+newType;
      var result = await client.query(alterQuery);
    }
    if(newName !== oldName){
      var alterQuery = "ALTER TABLE "+table+" RENAME COLUMN "+oldName+" TO "+newName;
      var result = await client.query(alterQuery);
    }
    return "success";
  }catch(err){
    console.log(err);
    return "failed";
  }
}

async function addColumn(json){
  var obj = JSON.parse(json);
  var name = obj.colName;
  var type = obj.dataType;
  var table = obj.table;

  try{
    var alterQuery = "ALTER TABLE "+table+" ADD "+name+" "+type;
    var result = await client.query(alterQuery);
    return "success";
  }catch(err){
    return "failed";
  }
}

async function renameTable(json){
  var obj = JSON.parse(json);
  var newName = obj.newName;
  var table = obj.table;
  try{
    var alterQuery = "ALTER TABLE "+table+" RENAME TO "+newName;
    var result = await client.query(alterQuery);
    return "success";
  }catch(err){
    return "failed";
  }
}

module.exports = {
  createTableInDB,
  tableExists,
  getTablesNdColumns,
  addColumn,
  renameTable,
  editColumn
};
