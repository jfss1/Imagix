const path = require('path');
const express = require('express');
const app = express();
const port = 7000;
const http = require('http').createServer(app);
const io = require('socket.io')(http);
const botName = 'Robo';
const formatMessage = require('./utils/message');
const {userJoin, getCurrentUser, getRoomUsers, userLeave} = require('./utils/users');

//Define pasta onde esta o index da pagina
app.use(express.static(path.join(__dirname, 'public')));

/*io.on('connection', (socket) =>{
    socket.emit('welcome', "Hello and Welcome to the Socket.io server");
    console.log("New Client is Connected!");
});*/

//Executar quando o utilizador se conecta
io.on('connection', socket =>{
    //Verifica quando o utilizador entra no chat(imprime no terminal)
    console.log('New client');

    socket.on('joinRoom', ({username, room}) => {
        //Define a varaivel
        const user = userJoin(socket.id, username, room);

        socket.join(user.room);

        //Mesagem de boas vindas ao utilizador que entrar no chat
        socket.emit('message', formatMessage(botName, 'Hello and Welcome to the Socket.io server'));

        //Transmitir quando o utilizador se conectar
        socket.broadcast
        .to(user.room)
        .emit('message', 
        formatMessage(botName, `${user.username} has joined the chat`));
    });

    //Executa a messagem no chat do utilizador
    socket.on('chatMessage', msg => {
        //Verifica a mensagem do utilizador(imprime no terminal)
        console.log(msg);
        //define variavel
        const user = getCurrentUser(socket.id);
        //imprime no console do browser a mensagem do utilizador
        io.to(user.room).emit('message', formatMessage(user.username, msg));
    });
    
    //Executa quando o utilizador se desconecta
    socket.on('disconnet', () => {
        //Define a varaivel
        const user = userLeave(socket.id);

        if(user){
            //imprime no console do browser quando o utilizador desconeta
            io.emit(
                'message', 
                formatMessage(botName, `${user.username} has left the chat`)
            );
        }
    });
});

http.listen(port, () =>{
    console.log("Server is listning on localhost:" + port);
});

/*const gameRooms = ["PC", "Player"];

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
});*/
