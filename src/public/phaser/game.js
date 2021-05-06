var config = {
    type: Phaser.AUTO,
    parent: 'conteudo', 
    width: 1280,
    height: 720,
    backgroundColor: "#ff6699",
    physics: {
        default: 'arcade',
        arcade:{
            gravity: {
                y:200
            },
            debug:false
        }
    },
    scene:[
       cenaMundo,
       cenaLoad
    ]
};


var game = new Phaser.Game(config);
