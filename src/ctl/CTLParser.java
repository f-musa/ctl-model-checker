package ctl;

import java.util.ArrayList;
import java.util.List;

import ctl.ctlformula.*;;
public class CTLParser {

    
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
                    else if(token.equals("true")){
                        tokens.add(token);
                        token = "";
                    }
                    else if(token.equals("false")){
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
            return tokens.get(index++);
        }



        // Inside the Parser class

        Formula parseFormula() {
            Formula left = parseOrAnd();

            while (peek() != null && peek().equals("=>")) {
                consume(); // Consume '=>'
                Formula right = parseOrAnd(); // Assume that '=>' has lower precedence than 'v' and '^'
                left = new Or(new Not(left), right);
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
                return new Not(new EX(new Not(inner)));
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
                return new Always(new Until(generateTrue(),inner));
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
                return new Not(new E(new Until(generateTrue(),new Not(inner))));
            }
            else if ("not".equals(peek())) {
                consume();
                Formula negated = parseFactor();
                return new Not(negated);
            }
            else if("false".equals(peek())){
                consume();
                return  new Not(generateTrue());
            }
            else if("true".equals(peek())){
                consume();
                return  generateTrue();
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
                return new E (new Until(generateTrue(),inner));
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
                return new  Not(new Always(new Until(generateTrue(),new Not(inner))));
            }

            else if (peek().equals("E")) {
                consume(); // consume 'E'
                // Ensure the next character is '('
                if (!peek().equals("(") && !peek().equals("true")&&!peek().equals("false")) {
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

        public  Or generateTrue(){
            return new Or(new Atomic("1"),new Not(new Atomic("1")));
        }

    }




}
