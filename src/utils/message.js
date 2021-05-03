//define uma variavel
const moment = require('moment');

//define uma funcao que recebe o nome do utilizador, o texto e a hora
function formatMessage(username, text) {
  return {
    username,
    text,
    time: moment().format('h:mm a')
  };
}

module.exports = formatMessage;
