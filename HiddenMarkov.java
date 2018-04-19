import java.io.*;
import java.util.*;

public class HiddenMarkov {
    public ArrayList<String> values, measurement, compCases;
    public HashMap<String, Double> probabilities;
    public ArrayList<Double> firstVal, secondVal;
    public int cases;
    public String sequence;
                    

//di ko pa rin gets measurement (is the E & F)
//values is S T

    public HiddenMarkov() {
        this.probabilities = new HashMap<String, Double>();
        this.values = new ArrayList<String>();
        this.measurement = new ArrayList<String>();
        this.compCases = new ArrayList<String>();
        this.cases = 0;
    }

    public void run(){
        loadFile();
        getTransitionProbabilities();
        getStates();
        saveFile();
    }

    public void getTransitionProbabilities(){
        char[] sq=sequence.toCharArray();
        char first = values.get(0).charAt(0);
        char second = values.get(1).charAt(0);
        getFirstState(sq);
        getTransitionProbability(sq,first,first);
        getTransitionProbability(sq,second,first);
        getTransitionProbability(sq,second,second);
        getTransitionProbability(sq,first,second);
    }

    public void getFirstState(char[] sq){
        String f = values.get(0);
        f =  f.concat("0");
        String s = values.get(1);
        s =  s.concat("0");
        if(sq[0] == values.get(0).charAt(0)){
            probabilities.put(f, 1.0);            
            probabilities.put(s, 0.0);            
        }
        else{
            probabilities.put(s, 1.0);            
            probabilities.put(f, 0.0);            
        }
    }

    public void getStates(){
        // for(int index = 0; index < compCases.size(); index++){
        //     getValueSubX(compCases.get(index));
        // }
        getValueSubX("S1 given E1");
        getValueSubX("T1 given E1");
    }



    public void getValueSubX(String value){
            String[] val = value.split("given"); //stores all the words from the line in values
            String comp = val[0]; //S1
            String measure = val[1]; //E1
            String[] c = comp.split("");
            String[] m = measure.split("");
            String state = c[0];
            int x = Integer.parseInt(c[1]);

            if(state.equals(values.get(0))){ //S
                System.out.println(comp + " " + state + " " + getOtherState(state) + " " + x);
                equationFirstVal(comp, state, getOtherState(state), x);
            }
            else if(state.equals(values.get(1))){ //T
                System.out.println(comp + " " + state + " " + getOtherState(state) + " " + x);
                equationSecondVal(comp, state, getOtherState(state), x);
            }
            else if(state.equals(measurement.get(0))){ //E

            }
            else if(state.equals(measurement.get(1))){ //F

            }
            // x.toString(x);
    }

    public String getOtherState(String s){ //good
        String ans = "";
        for(int index = 0; index < values.size(); index++){
            if(!(s.equals(values.get(index)))){
                ans = values.get(index);
            }
        }
        return ans;
    }
// P(S2) = P(SS)P(S1) + P(ST)P(T1)
//siz good
    public void equationFirstVal(String compStatement, String state,  String opState, int x){ //S1 , S, T,  1
    // P(S1) = P(SS)P(S0) + P(ST)P(T0)
        String Sminus = state.concat(Integer.toString(x-1)); //S0 
        String Tminus = opState.concat(Integer.toString(x-1));  //T0
        String SS = state.concat(state);  //SS
        String ST = state.concat(opState);  //ST

        if(probabilities.containsKey(Sminus) && probabilities.containsKey(Tminus)){ //if T0 & S0 exists fetch their probability
            Double dS = probabilities.get(Sminus); 
            Double dT = probabilities.get(Tminus);
            Double dSS = probabilities.get(SS);
            Double dST = probabilities.get(ST);
            Double ans = dSS * dS + dST * dT;
            System.out.println(Sminus + ": " + dS + " " + Tminus + ": " + dT + " " + SS + ": " + dSS + " " + ST + ": " + dST);
            System.out.println(dSS + "*" + dS + "+" + dT + "*" + dST + "=" + ans);
            probabilities.put(compStatement, ans);
        }
        else{
            if(probabilities.containsKey(Sminus) && !(probabilities.containsKey(Tminus))){
                equationSecondVal(Tminus, state, opState, x-1);
            }
            else if(!(probabilities.containsKey(Sminus)) && probabilities.containsKey(Tminus)){
                equationFirstVal(Sminus, state, opState, x-1);
            } 
            else{
                equationFirstVal(Sminus, state, opState, x-1);
                equationSecondVal(Tminus, state, opState, x-1);
            }
        }
    }

// P(T1) = P(TS)P(S0) + P(TT)P(T0)
// P(T2) = P(TS)P(S1) + P(TT)P(T1)
    public void equationSecondVal(String compStatement, String state,  String opState, int x){
        String Tminus = state.concat(Integer.toString(x-1)); //T0 
        String Sminus = opState.concat(Integer.toString(x-1));  //S0
        String TT = state.concat(state);  //TT
        String TS = state.concat(opState);  //TS

        if(probabilities.containsKey(Sminus) && probabilities.containsKey(Tminus)){ //if T0 & S0 exists fetch their probability
            Double dT = probabilities.get(Tminus);
            Double dS = probabilities.get(Sminus); 
            Double dTS = probabilities.get(TS);
            Double dTT = probabilities.get(TT);
            Double ans = dTS * dS + dTT * dT;
            System.out.println(Sminus + ": " + dS + " " + Tminus + ": " + dT + " " + TS + ": " + dTS + " " + TT + ": " + dTS);
            System.out.println(dTS + "*" + dS + "+" + dT + "*" + dTT + "=" + ans);
            probabilities.put(compStatement, ans);
        }
        else{
            if(probabilities.containsKey(Sminus) && !(probabilities.containsKey(Tminus))){
                equationSecondVal(Tminus, state, opState, x-1);
            }
            else if(!(probabilities.containsKey(Sminus)) && probabilities.containsKey(Tminus)){
                equationFirstVal(Sminus, state, opState, x-1);
            } 
            else{
                equationFirstVal(Sminus, state, opState, x-1);
                equationSecondVal(Tminus, state, opState, x-1);
            }
        }
    }

    public void equationMeasure(){
        
    }

//iterate over probability arraylist
    
    //x given y : followed by x (x ung after y)
    public void getTransitionProbability(char[] sq, char x, char y){
        Double yCount = getValueCount(sq, y);
        Double probability = 0.0;
        Double count = 0.0;
        for(int i = 0; i < sq.length; i++){
            if(i+1 < sq.length){
                if((sq[i] == x) && (sq[i+1] == y)){
                    count += 1.0;
                }
            }
        }
        probability = count / yCount;  
        System.out.println(Character.toString(x) + Character.toString(y) + ": " + count + "/ " + yCount +" = " + probability);
        String s = Character.toString(x);
        s = s.concat(Character.toString(y)); 
        probabilities.put(s, probability);
    }

    public Double getValueCount(char[] sq, char x){
        Double count = 0.0;
        for(int i = 0; i < sq.length; i++){
            if(sq[i] == x){
                count += 1.0;
            }
        }
        return count;
    }


    public void loadFile() {
        try{
            FileInputStream fstream = new FileInputStream("hmm.in");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line, first, second;

            int j = 0;
            while((line  = br.readLine()) != null)
            {

                String[] val = line.split(" "); //stores all the words from the line in values
                if(j==0){
                    for (String str : val) {
                        values.add(str);
                        // S T 
                    }
                }
                else if(j==1){
                    for (String str : val) {
                        measurement.add(str);
                        // E F 
                    }
                }
                else if(j==2){
                    first = measurement.get(0);
                    first = first.concat(values.get(0));
                    probabilities.put(first, Double.parseDouble(val[0]));

                    second = measurement.get(1);
                    second = second.concat(values.get(0));
                    probabilities.put(second, Double.parseDouble(val[1]));
                }
                else if(j==3){
                    first = measurement.get(0);
                    first = first.concat(values.get(1));
                    probabilities.put(first, Double.parseDouble(val[0]));

                    second = measurement.get(1);
                    second = second.concat(values.get(1));
                    probabilities.put(second, Double.parseDouble(val[1]));
                }
                else if(j==4){
                    sequence = line;
                }
                else if(j==5){
                    cases = Integer.parseInt(line);
                }
                else if(j > 5){
                    compCases.add(line);
                }
                j++;

            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveFile() { //writes file
        String filename = "hmm.out";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for(int i = 0; i < values.size(); i++){
                writer.write(values.get(i) + " ");
            }
            writer.write("\n");

            for(int i = 0; i < measurement.size(); i++){
                writer.write(measurement.get(i) + " ");
            }
            writer.write("\n");

            for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
                writer.write(entry.getKey()+" : "+entry.getValue() + "\n");
            }

            writer.write(sequence + "\n");
            // System.out.println(cases);
            // writer.write(cases);

            for(int i = 0; i < compCases.size(); i++){
                writer.write(compCases.get(i) + "\n");
            }

            writer.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            System.out.println("Error writing file '" + filename + "'");
        }
    }
}

