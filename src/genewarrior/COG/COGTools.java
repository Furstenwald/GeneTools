/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genewarrior.COG;

import genewarrior.alignment.AlignmentTools;
import genewarrior.alignment.SubstitutionMatrix;
import genewarrior.sequenceHandling.SequenceAA;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author btserver
 */
public class COGTools {

    public static ArrayList<COGrank> rankSequence(SequenceAA seq, ArrayList<COGsequence> sequencelist, SubstitutionMatrix sm)
    {
        ArrayList<COGrank> ranks = new ArrayList<COGrank>();

        for (COGsequence seq2 : sequencelist)
        {
            ranks.add(new COGrank(AlignmentTools.quickalign(seq, seq2.seq, sm, AlignmentTools.Local), seq2.cog));
            //System.out.println("Cog: "+seq2.cog.CogName);
        }

        Collections.sort(ranks);
        return ranks;
    }

    public static ArrayList<COGsequence> loadSequences(File myva, File whog, COGList coglist)
    {
        ArrayList<COGsequence> liste = new ArrayList<COGsequence>();
        try {
           BufferedReader myvareader = new BufferedReader(new FileReader(myva));

           String output;
           String seq_name="";
           String seq ="";
           boolean isLactobacillales = true;
           int line=0;
            while ((output = myvareader.readLine()) != null)
            {
                line++;
                if (line%1000==0)
                    System.out.println(line/1000);
                if (output.startsWith(">"))
                {
                    if (!seq.isEmpty())
                    {
                        SequenceAA seqaa = new SequenceAA(seq);
                        BufferedReader whogReader = new BufferedReader(new FileReader(whog));
                        String output2;
                        String actual="";
                        search:
                        while ((output2 = whogReader.readLine()) != null)
                        {
                            if (output2.startsWith("["))
                                actual=output2;
                            else
                            {
                                String[] matches = output2.trim().split("\\s");
                                for (String e: matches)
                                {
                                    if (e.equals(seq_name)) //Found
                                    {
                                        String split[] = actual.split("\\s",3);
                                        String number = removeLeadingZeros(split[1].substring(3));
                                        int cognr = Integer.decode(number);
                                        liste.add(new COGsequence(seqaa,coglist.get(cognr)));
                                        break search;
                                    }
                                }
                            }
                        }
                        whogReader.close();
                    }
                    seq_name = output.substring(1).trim();
                    //Lactobacillales
                    if (seq_name.matches("L\\d+.*")||seq_name.matches("SPy\\d+.*")||seq_name.matches("SP\\d+.*"))
                    //Actinobacteria
                    //if (seq_name.matches("Cgl\\d+.*")||seq_name.matches("Rv\\d+.*")||seq_name.matches("ML\\d+.*"))
                    {
                        isLactobacillales = true;
                        System.out.println(seq_name);
                    }
                    else
                        isLactobacillales=false;
                    seq="";
                }
                else if (isLactobacillales)
                {
                    seq+=output.trim();
                }
            }

            myvareader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        }

        return liste;
    }

    public static void saveCog(COGList list, File saveto)
    {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveto));
            out.writeObject(list);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveSeqList(ArrayList<COGsequence> list, File saveto)
    {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveto));
            out.writeObject(list);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<COGsequence> loadSeqList(File openfile)
    {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(openfile));
            ArrayList<COGsequence> list = (ArrayList<COGsequence>)in.readObject();
            in.close();
            return list;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static COGList loadCog(File openfile)
    {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(openfile));
            COGList list = (COGList)in.readObject();
            in.close();
            return list;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static COGList readCogs(File funtxt, File whog)
    {
        COGList list = new COGList();
        Map<Character, COGfunction> functions = new HashMap<Character, COGfunction>();


        try {
            //READ fun.txt
            BufferedReader funreader = new BufferedReader(new FileReader(funtxt));
            String output;
            String category="";
            while ((output = funreader.readLine()) != null)
            {
                if (output.trim().startsWith("["))
                {
                    Character c = output.trim().charAt(1);
                    functions.put(c, new COGfunction(c, category, output.trim().substring(4)));
                }
                else if (!output.trim().isEmpty())
                {
                    category=output.trim();
                }
            }
            funreader.close();

            //READ whog
            BufferedReader whogreader = new BufferedReader(new FileReader(whog));

            Pattern number = Pattern.compile("COG\\d{4}");
            Matcher m = number.matcher("");

            while ((output = whogreader.readLine()) != null)
            {
                if (output.trim().startsWith("["))
                {
                    m.reset(output);
                    m.find();
                    int start = m.start();
                    COGfunction funct = functions.get(output.trim().charAt(1));
                    String name = output.substring(start+8);
                    int cognumber = Integer.decode(removeLeadingZeros(output.substring(start+3,start+7)));
                    list.addCOG(new COG(cognumber, name, funct));
                }
            }

            whogreader.close();


            return list;



        } catch (FileNotFoundException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(COGTools.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
    private static String removeLeadingZeros(String str)
    {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        int index = 0;
        for (; index < str.length();index++)
        {
            if (chars[index] != '0')
                break;
        }
        return (index == 0) ? str :str.substring(index);
    }
}
