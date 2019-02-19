grammar lapr;

@parser::members {

    public int valContador;
        public List<Integer> escalaValores;
        public Integer minEscala;
        public Integer maxEscala;

        private boolean dynamicInbounds(Double val, String op, Double lim) {
            if (op.equals("<")) {
                return val < lim;
            } else if (op.equals("<=")) {
                return val <= lim;
            } else if (op.equals(">")) {
                return val > lim;
            } else if (op.equals(">=")) {
                return val >= lim;
            } else if (op.equals("==")) {
                return val == lim;
            } else if (op.equals("!=")) {
                return val != lim;
            }
            return false;
        }

        private void processComparator(Token valT, Token opT, Token limT, Token outT) {
            Integer out = Integer.parseInt(outT.getText());
            if (valT.getText().equalsIgnoreCase("val")) {
                // caso de atribuicao
                this.valContador++;
                if (out < minEscala) {
                    minEscala = out;
                }
                if (out > maxEscala) {
                    maxEscala = out;
                }
            } else {
                // caso de teste da escala
                if (this.valContador == 0) {
                    Double val = Double.parseDouble(valT.getText());
                    String op = opT.getText();
                    Double lim = Double.parseDouble(limT.getText());

                    if (dynamicInbounds(val, op, lim)) {
                        // caso de sucesso no teste
                        this.escalaValores.add(out);
                    }
                }
            }
        }

}


expressao   : 'if' comparador ;

comparador  : '(' VAL OP LIM ') then ' OUT NL? {System.out.println("over"); processComparator($VAL, $OP, $LIM, $OUT);}
            | '(' VAL OP LIM ') then ' OUT 'else' expressao NL? {System.out.println(" not over"); processComparator($VAL, $OP, $LIM, $OUT);}
            ;


OUT         : [1-9][0-9]* ;

LIM         : [0-9]+[.][0-9][0-9] ;

VAL         : [0-9]+([.][0-9]+)?
            | 'val' ;

OP          : '<' | '<=' | '>' | '>=' | '==' | '!=' ;

NL          : '\r'? '\n' | '\r';

WS          : [ ] -> skip;
