const express = require('express');
const app = express();
const port = 0007;
const http = require('http').createServer();

const io = require('socket.io')(http);

/*io.on('connection', (socket) =>{
    socket.emit('welcome', "Hello and Welcome to the Socket.io server");

    console.log("New Client is Connected!");
});*/
const gameRooms = ["Room1", "Room2", "Room3"];

io.of("/games").on("connection", (socket)=>{
    console.log("New client")
    socket.emit("welcome", "Hello and Welcome to the Socket.io server");
    socket.on("joinRoom", (room) =>{
        if(gameRooms.includes(room)){  
            socket.join(room); 
            io.of("/games")
            .in(room)
            .emit("newUser", "new Player has joined the " + room);
            return socket.emit("success", "You have succefully Joined this Room");  
        }else{
            return socket.emit("err", "ERROR, No Room named " + room);
        }
    });
});
http.listen(port, () =>{
    console.log("Server is listning on localhost:" + port);
});
