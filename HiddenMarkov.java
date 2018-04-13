
import java.io.*;
import java.util.*;

public class HiddenMarkov {
    public ArrayList<String> values, measurement, compCases;
    public Double pES, pFS, pET, pFT;
    public int cases;
    public String sequence;

    public HiddenMarkov() {
        this.values = new ArrayList<String>();
        this.measurement = new ArrayList<String>();
        this.pES = 0.0;
        this.pFS = 0.0;
        this.pET = 0.0;
        this.pFT = 0.0;
        this.cases = 0;
        // classifyDictionary = new HashMap<String, ArrayList>();
        // String s="";
        // BagOfWords spam = new BagOfWords();
        // numberOfSpamMsgs = new File("all_data/spam/").listFiles().length;
        // for(int i = 1; i<numberOfSpamMsgs; i++){
        //     s = String.format("%03d", i);
        //     s = String.format("all_data/spam/" + s);
        //     spam.loadFile(s);
        // }
        // System.out.println("spam words " + spam.dictionarySize);
        // spam.saveFile(spam.dictionarySize, spam.numberOfWords, "outputSpam.txt");
        //
        // BagOfWords ham = new BagOfWords();
        // numberOfHamMsgs = new File("all_data/ham/").listFiles().length;
        // for(int i = 1; i<300; i++){
        //     s = String.format("%03d", i);
        //     s = String.format("all_data/ham/" + s);
        //     ham.loadFile(s);
        // }
        // System.out.println("ham words " + ham.dictionarySize);
        // ham.saveFile(ham.dictionarySize, ham.numberOfWords, "outputHam.txt");
        //
        // s = String.format("all_data/classify/001");
        // //loads all files in classify (atm 1 muna) and saves it in the dictionary
        // classifyLoadFile(s, classifyDictionary);
        //
        // Probability p = new Probability();
        // double pMessageSpam = 1;
        // double pMessageHam = 1;
        // double pSpam = p.pSpam(numberOfSpamMsgs, numberOfHamMsgs);
        // double pHam = p.pHam(pSpam);
        // System.out.println("pSPAM: " + pSpam);
        // System.out.println("pHAM: " + pHam);
        //
        // //gets pMessageSpam & pMessageHam by looping through the dictionary and saves each probability in a file
        // for (Map.Entry<String, ArrayList> entry : classifyDictionary.entrySet()) {
        //   double sVal;
        //   double hVal;
        //   double pWordSpam = 0;
        //   double pWordHam = 0;
        //
        //   //loops through the array of words of each file (which is saved as the value of each key <file>)
        //   for(int i = 0; i < entry.getValue().size(); i++){
        //     //gets MessageSpam & messageHam by getting the WordSpam (u get it by getting the #of occurences it is in Spam/Ham)
        //     if(spam.dictionary.containsKey(entry.getValue().get(i))){
        //         sVal = spam.dictionary.get(entry.getValue().get(i));
        //     }else{
        //       sVal = 0;
        //       System.out.println("SPAM NOT: " + entry.getValue().get(i) + "================================================");
        //     }
        //     pWordSpam = p.pWordSpam(spam.numberOfWords, sVal);
        //     if(pWordSpam == 0){
        //     }
        //
        //     System.out.println("pWordSpam : "+ pWordSpam+ " * pMessageSpam" +pMessageSpam+ " at word " + i + " is ");
        //     pMessageSpam = pWordSpam * pMessageSpam;
        //     System.out.println(pMessageSpam);
        //
        //     if(ham.dictionary.containsKey(entry.getValue().get(i))){
        //         hVal = ham.dictionary.get(entry.getValue().get(i));
        //     }else{
        //       hVal = 0;
        //       // System.out.println("HAM NOT: " + entry.getValue().get(i) + "================================================");
        //     }
        //     pWordHam = p.pWordHam(spam.numberOfWords, hVal);
        //     if(pWordSpam == 0){
        //     }
        //
        //     System.out.println("pWordHam : "+ pWordHam+ " * pMessageHam" +pMessageHam+ " at word " + i + " is ");
        //     pMessageHam = pWordHam * pMessageHam;
        //     System.out.println(pMessageHam);
        //     // System.out.println("pMESSAGEHAM is " + pMessageHam);
        //     System.out.println("");
        //
        //   }
        //
        //   // System.out.println("pMessageHam: " +pMessageHam);
        //   double pMessage = p.pMessage(pMessageSpam, pSpam, pMessageHam, pHam);
        //   System.out.println("pMessage: " +pMessage);
        //   double pSpamMessage = p.pSpamMessage(pMessageSpam, pMessage, pSpam);
        //   double pHamMessage = p.pHamMessage(pMessageHam, pMessage);
        //   System.out.println("pSpamMessage: " +pSpamMessage);
        //   System.out.println("pHamMessage: " +pHamMessage);
        //
        //   saveFile("outputClassify", classifyDictionary);
        //   classifyFile(entry.getKey(), "Ham", pSpamMessage);
        // }
        //
        //
        //
    }
    public void run(){
        loadFile();
        // saveFile();
    }

    public void loadFile() {
        try{
            FileInputStream fstream = new FileInputStream("hmm.in");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            int j = 0;
            while((line  = br.readLine()) != null)
            {
                System.out.println(line);

                String[] val = line.split(" "); //stores all the words from the line in values
                if(j==0){
                    for (String str : values) {
                        values.add(str);
                    }
                }
                else if(j==1){
                    for (String str : values) {
                        measurement.add(str);
                    }
                }
                else if(j==2){
                    pES = Double.parseDouble(val[0]);
                    pFS = Double.parseDouble(val[1]);
                }
                else if(j==3){
                    pET = Double.parseDouble(val[0]);
                    pFT = Double.parseDouble(val[1]);
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
            writer.write("\n ");

            for(int i = 0; i < measurement.size(); i++){
                writer.write(measurement.get(i) + " ");
            }

            writer.write("\n ");
            writer.write(pES + " " + pFS);
            writer.write("\n ");
            writer.write(pET + " " + pFT);
            writer.write("\n ");
            writer.write(cases);

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
