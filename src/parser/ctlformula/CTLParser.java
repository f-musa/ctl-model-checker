package parser.ctlformula;

import java.util.ArrayList;
import java.util.List;

public class CTLParser {

    public static void main(String[] args) {
        // Test your formula here, please
        String formula = "A X (( P = > Q ) v A G( Q ) )";
        Formula parsedExpression = parse(formula.replace(" ",""));
        System.out.println(parsedExpression);
    }

    public static Formula parse(String formula) {
        List<String> tokens = tokenize(formula);
        Parser parser = new Parser(tokens);
        Formula result = parser.parseFormula();
        if (parser.hasNext()) {
            throw new IllegalArgumentException("Unexpected token at end: " + parser.peek());
        }
        return result;
    }

        public static List<String> tokenize(String formula) {
            List<String> tokens = new ArrayList<>();
            String token = "";

            for (int i = 0; i < formula.length(); i++) {
                char c = formula.charAt(i);

                if ((i + 1 < formula.length()) && (c == '=' && formula.charAt(i + 1) == '>')) {
                    if (!token.isEmpty()) {
                        tokens.add(token);
                        token = "";
                    }
                    tokens.add("=>");
                    i++;
                    continue;
                }

                if (c == ' ' || c == '(' || c == ')' || c == '^' || c == 'v') {
                    if (!token.isEmpty()) {
                        tokens.add(token);
                        token = "";
                    }
                    if (c != ' ') {
                        tokens.add(Character.toString(c));
                    }
                } else {
                    token += c;
                    if (token.equals("EX")) {
                        tokens.add(token);
                        token = "";
                    }
                    else if (token.equals("EF")) {
                        tokens.add(token);
                        token = "";
                    }
                    else if (token.equals("AX")) {
                        tokens.add(token);
                        token = "";
                    }
                    else if(token.equals("AG")){
                        tokens.add(token);
                        token = "";
                    }
                    else if(token.equals("AF")){
                        tokens.add(token);
                        token = "";
                    }
                    else if(token.equals("not")){
                        tokens.add(token);
                        token = "";
                    }
                    else if((token.equals("E") && formula.charAt(i+1)!='X')&& formula.charAt(i+1)!='F' &&
                            formula.charAt(i+1)!='G' ){
                         tokens.add(token);
                         token = "";
                    }
                    else if((token.equals("A") && formula.charAt(i+1)!='G')&& formula.charAt(i+1)!='X' &&
                            formula.charAt(i+1)!='F' ){
                            tokens.add(token);
                            token = "";
                    }
                }
            }

            if (!token.isEmpty()) {
                tokens.add(token);
            }

            return tokens;
        }


    static class Parser {
        private final List<String> tokens;
        private int index = 0;

        public Parser(List<String> tokens) {
            this.tokens = tokens;
        }

        boolean hasNext() {
            return index < tokens.size();
        }

        String peek() {
            return hasNext() ? tokens.get(index) : null;
        }

        String consume() {
            System.out.println(index +  tokens.get(index)+" nothing");
            return tokens.get(index++);
        }



        // Inside the Parser class

        Formula parseFormula() {
            Formula left = parseOrAnd();

            while (peek() != null && peek().equals("=>")) {
                consume(); // Consume '=>'
                Formula right = parseOrAnd(); // Assume that '=>' has lower precedence than 'v' and '^'
                left = new Implication(left, right);
            }

            return left;
        }

        // A new helper method to handle 'v' and '^'
        Formula parseOrAnd() {
            Formula left = parseFactor();

            while (peek() != null && (peek().equals("v") || peek().equals("^"))) {
                String op = consume();
                Formula right = parseFactor();
                if ("v".equals(op)) {
                    left = new Or(left, right);
                } else if ("^".equals(op)) {
                    left = new And(left, right);
                }
            }

            return left;
        }


        Formula parseFactor() {
            if (peek().equals("(")) {
                consume(); // Consume '('
                Formula inner = parseFormula();
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' but found: " + peek());
                }
                consume(); // Consume ')'
                return inner;
            }
            else if ("AX".equals(peek())) {
                consume(); // consume 'EX'
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'EX' but found: " + peek());
                }
                consume(); // consume '('
                Formula inner = parseFormula();
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' but found: " + peek());
                }
                consume(); // consume ')'
                return new AX(inner);
            }
            else if ("AF".equals(peek())) {
                consume(); // consume 'EX'
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'EF' but found: " + peek());
                }
                consume(); // consume '('
                Formula inner = parseFormula();
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' but found: " + peek());
                }
                consume(); // consume ')'
                return new AF(inner);
            }
            else if ("AG".equals(peek())) {
                consume(); // consume 'EX'
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'EF' but found: " + peek());
                }
                consume(); // consume '('
                Formula inner = parseFormula();
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' but found: " + peek());
                }
                consume(); // consume ')'
                return new AG(inner);
            }
            else if ("not".equals(peek())) {
                consume();
                Formula negated = parseFactor();
                return new Not(negated);
            }
            else if ("EX".equals(peek())) {
                consume(); // consume 'EX'
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'EX' but found: " + peek());
                }
                consume(); // consume '('
                Formula inner = parseFormula();
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' but found: " + peek());
                }
                consume(); // consume ')'
                return new EX(inner);
            }
            else if ("EF".equals(peek())) {
                consume(); // consume 'EX'
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'EF' but found: " + peek());
                }
                consume(); // consume '('
                Formula inner = parseFormula();
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' but found: " + peek());
                }
                consume(); // consume ')'
                return new EF(inner);
            }
            else if ("EG".equals(peek())) {
                consume(); // consume 'EX'
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'EF' but found: " + peek());
                }
                consume(); // consume '('
                Formula inner = parseFormula();
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' but found: " + peek());
                }
                consume(); // consume ')'
                return new EG(inner);
            }

            else if (peek().equals("E")) {
                consume(); // consume 'E'
                // Ensure the next character is '('
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'E' but found: " + peek());
                }
                consume(); // consume '('
                Formula untilFormula = parseUntil();
                // Ensure the next character is ']'
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' after 'Until' formula but found: " + peek());
                }
                consume(); // consume ')'
                return new E(untilFormula);
            }
            else if("A".equals(peek())){
                consume(); // consume 'A'
                // Ensure the next character is '('
                if (!peek().equals("(")) {
                    throw new IllegalArgumentException("Expected '(' after 'A' but found: " + peek());
                }
                consume(); // consume '('
                Formula untilFormula = parseUntil();
                // Ensure the next character is ')'
                if (!peek().equals(")")) {
                    throw new IllegalArgumentException("Expected ')' after 'Until' formula but found: " + peek());
                }
                consume(); // consume ')'
                return new Always(untilFormula);
            }
            else {
                return new Atomic(consume());
            }
        }

        Formula parseUntil() {

            Formula firstPart = parseFormula();
            consume(); // ')'

            if (!peek().equals("U")) {
                throw new IllegalArgumentException("Expected 'U' for the 'Until' operator but found: " + peek());
            }

            consume(); // consume 'U'


            consume();
            Formula secondPart = parseFormula();



            return new Until(firstPart, secondPart);
        }

    }

    interface Formula {
        String toString();
    }

    static class EX implements Formula {
        private final Formula formula;

        public EX(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "EX(" + formula.toString() + ")";
        }
    }

    static class EF implements Formula {
        private final Formula formula;

        public EF(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "EF(" + formula.toString() + ")";
        }
    }

    static class EG implements Formula {
        private final Formula formula;

        public EG(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "EG(" + formula.toString() + ")";
        }
    }

    static class AX implements Formula {
        private final Formula formula;

        public AX(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "AX(" + formula.toString() + ")";
        }
    }
    static class AF implements Formula {
        private final Formula formula;

        public AF(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "AF(" + formula.toString() + ")";
        }
    }

    static class AG implements Formula {
        private final Formula formula;

        public AG(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "AG(" + formula.toString() + ")";
        }
    }

    static class Atomic implements Formula {
        private final String name;

        public Atomic(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName()+"("+name+")";
        }
    }

    static class Or implements Formula {
        private final Formula left;
        private final Formula right;

        public Or(Formula left, Formula right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() +"("+left.toString()+","+ right.toString()+")";
        }
    }

    static class And implements Formula {
        private final Formula left;
        private final Formula right;

        public And(Formula left, Formula right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() +"("+left.toString()+","+ right.toString()+")";
        }
    }




    static class Not implements Formula {
        private final Formula formula;

        public Not(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName()+ "("+formula.toString()+")";
        }
    }

    static class E implements Formula {
        private final Formula formula;

        public E(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "E(" + formula.toString()+")";
        }
    }

    static class Always implements Formula {
        private final Formula formula;

        public Always(Formula formula) {
            this.formula = formula;
        }

        @Override
        public String toString() {
            return "A(" + formula.toString()+")";
        }
    }

    static class Until implements Formula {
        private final Formula left;
        private final Formula right;

        public Until(Formula left, Formula right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return  getClass().getSimpleName()+ "(" + left.toString() + "," + right.toString() + ")";
        }
    }

    static class Implication implements Formula {
        private final Formula left;
        private final Formula right;

        public Implication(Formula left, Formula right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Implication(" + left.toString() + " => " + right.toString() + ")";
        }
    }


}
