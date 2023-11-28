package dot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DotParser {

    public State parseState(String line) {
        State state = new State();

        // Extract state name
        Pattern statePattern = Pattern.compile("\\s*(\\w+)\\s*\\[");
        Matcher stateNameMatcher = statePattern.matcher(line);
        if (stateNameMatcher.find()) {
            state.name = stateNameMatcher.group(1);
        }

        // Extract atomics from label
        Pattern atomicsPattern = Pattern.compile("=\"(.*?)\"");
        Matcher atomicsMatcher = atomicsPattern.matcher(line);

        if (atomicsMatcher.find()) {
            String labelsChain = atomicsMatcher.group(1);
            labelsChain = labelsChain.trim();
            List<String> labels = Arrays.asList(labelsChain.split(" "));

            for (int i = 0; i < labels.size(); i++) {
                state.labels.add(labels.get(i).trim());
            }
        }
        return state;

    }

    public void parseTransition(String line, Automata automata) {

        List<String> states = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            states.add(matcher.group());
        }
        for (int i = 0; i < states.size() - 1; i++) {
            Transition transition = new Transition();
            transition.from = getState(states.get(i), automata);
            transition.to = getState(states.get(i + 1), automata);

            if (transition.from != null && transition.to != null) {
                automata.transitions.add(transition);
            }

        }
    }

    public State getState(String name, Automata automata) {
        for (State state : automata.states) {
            if (state.name.equals(name)) {
                return state;
            }
        }
        return null;
    }

    private void setIntialState(String line, Automata automata) {

        // Get state name
        Pattern statePattern = Pattern.compile("(\\w+)\\[");
        Matcher stateNameMatcher = statePattern.matcher(line);

        if (stateNameMatcher.find()) {
            String stateName = stateNameMatcher.group(1);
            State state = getState(stateName, automata);
            state.isInitial = true;
            automata.initialState = state;

        }

    }

    public Automata parseDotFile(String path) throws IOException {
        Automata automata = new Automata();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                String sanitizedLine = line;

                if (sanitizedLine.contains("label") && sanitizedLine.contains("[") && sanitizedLine.contains("]")
                        && sanitizedLine.contains("=")) {
                    automata.states.add(parseState(sanitizedLine));
                } else if (sanitizedLine.contains("shape")) {
                    sanitizedLine = line.replaceAll(" ", "");
                    setIntialState(sanitizedLine, automata);
                } else if (sanitizedLine.contains("->")) {
                    sanitizedLine = line.replaceAll(" ", "");
                    parseTransition(sanitizedLine, automata);
                }
            }

            return automata;
        }
    }

}
