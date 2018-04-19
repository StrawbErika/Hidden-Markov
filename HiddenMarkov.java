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
        for(int index = 0; index < compCases.size(); index++){
            // getValueSubX(compCases.get(index));
            // System.out.println(compCases.get(index));            
        }
        getValueSubX("S1 given E1");
        getValueSubX("S2 given F2");
        getValueSubX("T3 given E3");
    }



    public void getValueSubX(String value){
            String[] val = value.split(" given "); //stores all the words from the line in values

            String comp = val[0]; //S1
            String[] c = comp.split("");
            String state = c[0]; //S
            int x = Integer.parseInt(c[1]); //1

            String measure = val[1]; //E1
            String[] m = measure.split("");
            String stateMeasure = m[0]; //E
            int y = Integer.parseInt(m[1]); //1


            if(state.equals(values.get(0))){ //S
                equationFirstVal(comp, state, getOtherState(state), x);
            }
            else{ //T
                equationFirstVal(comp, state, getOtherState(state), x);
            }
            equationMeasure(measure, stateMeasure, y);
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


    public void equationFirstVal(String compStatement, String state,  String opState, int x){ //S1 , S, T,  1
        String givenState = state.concat(Integer.toString(x-1));  
        String opposingState = opState.concat(Integer.toString(x-1));  
        String GS2 = state.concat(state);   
        String GSOP = state.concat(opState);  

        if(probabilities.containsKey(givenState) && probabilities.containsKey(opposingState)){ //if T0 & S0 exists fetch their probability
            Double dGivenState = probabilities.get(givenState); 
            Double dOpState = probabilities.get(opposingState);

            Double dGS2 = probabilities.get(GS2);
            Double dGSOP = probabilities.get(GSOP);

            Double ans = dGS2 * dGivenState + dGSOP * dOpState;
            // System.out.println(givenState + ": " + dGivenState + " " + opposingState + ": " + dOpState + " " + GS2 + ": " + dGS2 + " " + GSOP + ": " + dGSOP;

            probabilities.put(compStatement, ans);
        }
        else{
            if(probabilities.containsKey(givenState) && !(probabilities.containsKey(opposingState))){
                equationFirstVal(opposingState, state, opState, x-1);
            }
            else if(!(probabilities.containsKey(givenState)) && probabilities.containsKey(opposingState)){
                equationFirstVal(givenState, state, opState, x-1);
            } 
            else{
                equationFirstVal(givenState, state, opState, x-1);
                equationFirstVal(opposingState, state, opState, x-1);
            }
        }
    }
 
    public void equationMeasure(String compStatement, String state, int x){ //E1 , E , 1
        String givenFirst = state.concat(values.get(0)); //ES  
        String givenSecond = state.concat(values.get(1));  //ET
        String firstValX = (values.get(0)).concat(Integer.toString(x)); //S1
        String secondValX = (values.get(1)).concat(Integer.toString(x)); //T1

        System.out.println(givenFirst + " " + givenSecond + " " + firstValX + " " + secondValX);

        if(probabilities.containsKey(firstValX) && !(probabilities.containsKey(secondValX))){
            equationFirstVal(secondValX, values.get(1), values.get(0), x);
        }
        else if(!(probabilities.containsKey(firstValX)) && probabilities.containsKey(secondValX)){
            equationFirstVal(firstValX, values.get(0), values.get(1), x);
        } 
        else{
            equationFirstVal(firstValX, values.get(0), values.get(1), x);
            equationFirstVal(secondValX, values.get(1), values.get(0), x);
        }

        Double dGivenFirst = probabilities.get(givenFirst); 
        Double dGivenSecond = probabilities.get(givenSecond); 
        Double dFirstValX = probabilities.get(firstValX); 
        Double dSecondValX = probabilities.get(secondValX); 

        System.out.println(dGivenFirst + " " + dGivenSecond + " " + dFirstValX + " " + dSecondValX);
        
        Double ans = dGivenFirst * dFirstValX + dGivenSecond * dSecondValX;

        System.out.println(ans);

        probabilities.put(compStatement, ans);
    }

    public void getValueGivenMeasure(String value){
        String[] val = value.split(" given "); //stores all the words from the line in values

        String comp = val[0]; //S1
        String[] c = comp.split("");
        String state = c[0]; //S
        int x = Integer.parseInt(c[1]); //1

        String measure = val[1]; //E1
        String[] m = measure.split("");
        String stateMeasure = m[0]; //E
        int y = Integer.parseInt(m[1]); //1

    
    }

//S1E1 = (ES*S1)/E1
    public void equationValueGivenMeasure(String compStatement, String state, String measure, int x){ //S1 given E1 , S , E, 1
        String measureState = measure.concat(state); //ES
        String[] val = compStatement.split(" given "); //stores all the words from the line in values
        
        Double mS = probabilities.get(measureState);
        Double stateX = probabilities.get(val[0]);
        Double measureX = probabilities.get(val[0]);

        Double ans = (mS * stateX)/measureX;

        probabilities.put(compStatement, ans);
    }
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

