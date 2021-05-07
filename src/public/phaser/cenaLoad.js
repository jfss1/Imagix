class cenaLoad extends Phaser.Scene{
 
    constructor(){
        super('cenaLoad');
    }
    
    preload(){
        this.load.json('imagens', '../json/imagem.json');
        
    }

    create(){
        //ver o json
        console.log(this.cache.json.get('imagens').length);
        //criar imagens de cada json
        for(let i = 0; i<this.cache.json.get('imagens').length; i++){
            console.log(this.cache.json.get('imagens')[i].solution);
            console.log(this.cache.json.get('imagens')[i].path);
            this.load.image(this.cache.json.get('imagens')[i].solution, "../".concat(this.cache.json.get('imagens')[i].path))
        }
        this.scene.start('cenaMundo');
    }
}
