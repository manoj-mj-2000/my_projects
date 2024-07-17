const http = require('http');
const fs = require('fs');
const path = require('path');
var parser = require('./parser');
var db = require('./db');

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer(async (req, res) => {
  if (req.method === 'GET' && req.url === '/') {
    // Serve the HTML file
    const filePath = path.join(__dirname, 'index.html');
    fs.readFile(filePath, (err, data) => {
      if (err) {
        res.statusCode = 500;
        res.setHeader('Content-Type', 'text/plain');
        res.end('Internal Server Error');
        return;
      }
      res.statusCode = 200;
      res.setHeader('Content-Type', 'text/html');
      res.end(data);
    });
  } else if (req.method === 'POST' && req.url === '/json/data') {
    // Handle the API request
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', () => {
      try {
        var response = parser.parseJSON(body);
        if(response === "success"){
          res.statusCode = 200;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({ status: 'success', recieved: 'Table created' }));
        } else {
          res.statusCode = 400;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({ status: 'failed', recieved: 'Error Occurred' }));
        }
      } catch (error) {
        console.log("Internal Server Error", error);
      }
    });
  } else if (req.method === 'GET' && req.url === '/json/load') {
    console.log("Loading Tables");
    try{
      var tableNdColumnns = await db.getTablesNdColumns();
      // console.log("Tables::: "+JSON.stringify( tableNdColumnns));
      res.statusCode = 200;
      res.setHeader('Content-Type', 'application/json');
      res.end(JSON.stringify({data: tableNdColumnns}));
    }
    catch(err){
      console.log("error in json/load "+err);
    }
  } else if(req.method === 'POST' && req.url === '/json/addColumn'){
    console.log("add column");
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', async () => {
      try {
        var result = await db.addColumn(body);
        console.log(result);
        if(result === "success"){
          res.statusCode = 200;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({result: "success"}));
        } else {
          res.statusCode = 400;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({result: "failed"}));
        }
      }
      catch(err){
        console.log(err);
      }
    });
  } else if(req.method == 'PUT' && req.url === '/json/editColumn'){
    console.log("edit column");
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', async () => {
      try {
        var result = await db.addColumn(body);
        console.log(result);
        if(result === "success"){
          res.statusCode = 200;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({result: "success"}));
        } else {
          res.statusCode = 400;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({result: "failed"}));
        }
      }
      catch(err){
        console.log(err);
      }
    });
  }
  else if(req.method === 'PUT' && req.url === '/json/rename'){
    console.log("rename");
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', async () => {
      try {
        var result = await db.renameTable(body);
        console.log(result);
        if(result === "success"){
          res.statusCode = 200;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({result: "success"}));
        } else {
          res.statusCode = 400;
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify({result: "failed"}));
        }
      }
      catch(err){
        console.log(err);
      }
    });
  } else if(req.method === 'POST' && req.url === '/json/createTable'){
    console.log("Create table");
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', async () => {
      var json = JSON.parse(body);
      var tableName = json.name;
      var tableQuery = "CREATE TABLE "+tableName+" ()";
      var result = await db.createTableInDB(tableQuery);
      if(result === "success"){
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({result: "success"}));
      } else {
        res.statusCode = 400;
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({result: "failed"}));
      }
    });
  }
  else {
    // Handle 404
    res.statusCode = 404;
    res.setHeader('Content-Type', 'text/plain');
    res.end('Not Found');
  }
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
