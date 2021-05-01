const io = require("socket.io-client");

let game = io.connect("http://localhost:0007/games");

game.on("welcome", (msg) =>{
    console.log("Received: ", msg);
});
game.emit("joinRoom", "Room1");
game.on("newUser", (res)=> console.log(res));
game.on("err", (err) => console.log(err));
game.on("success", (res) => console.log(res)); 