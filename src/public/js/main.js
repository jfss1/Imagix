//vai buscar os atributos do html
const chatForm = document.getElementById('chat-form');
const chatMessages = document.querySelector('.chat-messages');

// Obtem nome de utilizador e sala de URL
const { username, room } = Qs.parse(location.search, {
    ignoreQueryPrefix: true,
});

//define o nome para o io
const socket = io();

// Entrar na sala do chat
socket.emit('joinRoom', { username, room });

// Messagem do servidor
socket.on('message', (message) => {
    //imprime no terminal
    console.log(message);
    //chama a funcao
    outputMessage(message);

    // Scroll down
    chatMessages.scrollTop = chatMessages.scrollHeight;
});

// Messagem para submeter
chatForm.addEventListener('submit', (e) => {
    e.preventDefault();
  
    // Obter o texto da mensagem
    let msg = e.target.elements.msg.value;
  
    // Emite mensagem para o servidor
    socket.emit('chatMessage', msg);

    // Limpar entrada
    e.target.elements.msg.value = '';
    e.target.elements.msg.focus();
});

//Mensagem do utilizador para o chat
function outputMessage(message) {
    const div = document.createElement('div');
    div.classList.add('message');
    const p = document.createElement('p');
    p.classList.add('meta');
    p.innerText = message.username;
    p.innerHTML += `<span>${message.time}</span>`;
    div.appendChild(p);
    const para = document.createElement('p');
    para.classList.add('text');
    para.innerText = message.text;
    div.appendChild(para);
    document.querySelector('.chat-messages').appendChild(div);
}
