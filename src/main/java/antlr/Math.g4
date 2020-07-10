grammar Math;

express: express (PLUS|SUB|MUL|DIV) * NUMBER | NUMBER;
NUMBER: [0]|[1-9]+[0-9]*;
PLUS: 'p';
SUB: 's';
MUL: 'm';
DIV: 'd';
WS: [ \t\n\r]+ -> skip;
