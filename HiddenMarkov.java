import java.io.*;
import java.util.*;

public class HiddenMarkov {
    public ArrayList<String> values, measurement, compCases;
    public HashMap<String, Double> probabilities;
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
        saveFile();
    }

    public void getTransitionProbabilities(){
        char[] sq=sequence.toCharArray();
        getS0(sq);
        getTransitionProbability(sq,'S','S');
        getTransitionProbability(sq,'T','S');
        getTransitionProbability(sq,'T','T');
        getTransitionProbability(sq,'S','T');
    }

    public void getS0(char[] sq){
        if(sq[0] == values.get(0).charAt(0)){
            probabilities.put("S0", 1.0);            
        }
        else{
            probabilities.put("S0", 0.0);            
        }
    }
    
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

