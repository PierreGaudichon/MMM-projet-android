let fs = require('fs');

let events = require('./fr-esr-fete-de-la-science-17.json');
let small = events.slice(0, 100);
fs.writeFileSync('fr-esr-fete-de-la-science-17.medium.json', JSON.stringify(small), 'utf8');
