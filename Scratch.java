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
