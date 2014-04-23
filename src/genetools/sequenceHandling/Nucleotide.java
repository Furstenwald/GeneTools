/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.sequenceHandling;

import java.util.*;

/**
 * This class should only be accessed by SequenceDNA and SequenceTools. Not directly.
 * @author kingcarlxx
 */
public class Nucleotide {

    public final static byte _A = 1;
    public final static byte _C = 2;
    public final static byte _G = 3;
    public final static byte _T = 4;
    public final static byte _U = 5;

    public final static byte _R = 6;//G or A
    public final static byte _Y = 7;//T or C
    public final static byte _K = 8;//G or T
    public final static byte _M = 9;//A or C
    public final static byte _S = 10;//G or C
    public final static byte _W = 11;//A or T
    public final static byte _B = 12;//G or T or C
    public final static byte _D = 13;//G or A or T
    public final static byte _H = 14;//A or C or T
    public final static byte _V = 15;//G or C or A
    public final static byte _N = 16;//A or G or C or T
    public final static byte __ = 0;//Gap

    public final static int alphabetSize = 17;

    /**
     * nucleotideString looks up chars and gives byte value
     */
    public final Map<Character,Byte> nucleotideChar = new HashMap<Character,Byte>();
    /**
     * nucleotideByte looks up Byte and gives char value
     */

    public final Map<Byte, Character> nucleotideByte = new HashMap<Byte, Character>();
    /**
     * complementByte looks up Byte and gives complement byte
     */
    public final Map<Byte, Byte> complementByte = new HashMap<Byte, Byte>();

    public Nucleotide()
    { 
        complementByte.put(_A, _T); //will be replaced in complement(), because it is dependent whether RNA or DNA
        complementByte.put(_C, _G);
        complementByte.put(_G, _C);
        complementByte.put(_T, _A);
        complementByte.put(_U, _A);
        complementByte.put(_R, _Y);
        complementByte.put(_Y, _R);
        complementByte.put(_K, _M);
        complementByte.put(_M, _K);
        complementByte.put(_S, _S);
        complementByte.put(_W, _W);
        complementByte.put(_B, _V);
        complementByte.put(_D, _H);
        complementByte.put(_H, _D);
        complementByte.put(_V, _B);
        complementByte.put(_N, _N);
        complementByte.put(__, __);

        nucleotideChar.put('A', _A);
        nucleotideChar.put('C', _C);
        nucleotideChar.put('G', _G);
        nucleotideChar.put('T', _T);
        nucleotideChar.put('U', _U);
        nucleotideChar.put('R', _R);
        nucleotideChar.put('Y', _Y);
        nucleotideChar.put('K', _K);
        nucleotideChar.put('M', _M);
        nucleotideChar.put('S', _S);
        nucleotideChar.put('W', _W);
        nucleotideChar.put('B', _B);
        nucleotideChar.put('D', _D);
        nucleotideChar.put('H', _H);
        nucleotideChar.put('V', _V);
        nucleotideChar.put('N', _N);
        nucleotideChar.put(' ', __);
        nucleotideChar.put('-', __);

        nucleotideByte.put(_A, 'A');
        nucleotideByte.put(_C, 'C');
        nucleotideByte.put(_G, 'G');
        nucleotideByte.put(_T, 'T');
        nucleotideByte.put(_U, 'U');
        nucleotideByte.put(_R, 'R');
        nucleotideByte.put(_Y, 'Y');
        nucleotideByte.put(_K, 'K');
        nucleotideByte.put(_M, 'M');
        nucleotideByte.put(_S, 'S');
        nucleotideByte.put(_W, 'W');
        nucleotideByte.put(_B, 'B');
        nucleotideByte.put(_D, 'D');
        nucleotideByte.put(_H, 'H');
        nucleotideByte.put(_V, 'V');
        nucleotideByte.put(_N, 'N');
        nucleotideByte.put(__, '-');
    }

    /**
     * compares nucleotides. Ambigous nucleotide <code>A</code> is expanded and compared to <code>B</code>. E.g.: A='N',B='C' is true, but vice versa is false. Can be used to compare RNA to DNA (U==T).
     * @param A
     * @param B
     * @return
     */
    public static boolean compareNucleotide(byte A, byte B)
    {
        if (A==_U)
            A=_T;
        if (B==_U)
            B=_T;

        if (A==B)
            return true;
        
        if (A==_R && (B ==_G || B == _A))
            return true;

        if (A==_Y && (B ==_T || B == _C))
            return true;

        if (A==_K && (B ==_G || B == _T))
            return true;

        if (A==_M && (B ==_A || B == _C))
            return true;

        if (A==_S && (B ==_G || B == _C))
            return true;

        if (A==_W && (B ==_A || B == _T))
            return true;

        if (A==_B && (B ==_G || B == _T || B == _C || B == _K || B == _S || B == _Y))
            return true;

        if (A==_D && (B ==_G || B == _A || B == _T || B == _R || B == _K || B == _W))
            return true;

        if (A==_H && (B ==_A || B == _T || B == _C || B == _W|| B == _M || B == _Y))
            return true;

        if (A==_V && (B ==_G || B == _A || B == _C || B == _R || B == _S || B == _M))
            return true;

        if (A==_N)
            return true;

        return false;
    }

    /**
     * Returns Invalid Positions of String. USE <code>SequenceTools.invalidPositionsDNA(String)</code> TO DO THIS TASK!
     * @param seq
     * @return
     */
    public int[] returnInvalidPositions(String seq)
    {
        char[] seqArray = seq.toUpperCase().toCharArray();
        ArrayList<Integer> invalids = new ArrayList<Integer>();
        
        for (int i=0;i<seqArray.length;i++)
        {
            if (nucleotideChar.get(seqArray[i])==null)
                invalids.add(i);
        }
        
        int[] invalidIndex = new int[invalids.size()];

        
        for (int i=0;i<invalidIndex.length;i++)
        {
            invalidIndex[i] = (int)invalids.get(i);
        }
        
        return invalidIndex;
    }
    /**
     * Converts String sequence to Byte Array.
     * @param seq Sequence to convert to Byte[]
     * @return byte for each nucleotide or gap if no valid nucleotide
     */
    public byte[] string2bytearray(String seq)
    {
        byte[] nucleotides = new byte[seq.length()];
        char[] seqArray = seq.toUpperCase().toCharArray();
        for (int i=0;i<seqArray.length;i++)
        {
            if (nucleotideChar.get(seqArray[i])==null)
                nucleotides[i] = __;
            else
                nucleotides[i] = nucleotideChar.get(seqArray[i]);
        }
        return nucleotides;
    }
    
    /**
     * Converts ArrayList of String sequence to Byte Array (concatenation of the array to one sequence; helper function for reading fasta files).
     * @param seq ArrayList of Sequence to convert to Byte[], all ordered
     * @return byte for each nucleotide or gap if no valid nucleotide
     */
    public byte[] string2bytearray(ArrayList<String> seq)
    {
        int length = 0;
        for (String s : seq)
            length+=s.length();
        
        byte[] nucleotides = new byte[length];
        
        int pos = 0;
        for (String s : seq)
        {
             char[] seqArray = s.toUpperCase().toCharArray();
             for (int i=0;i<seqArray.length;i++)
            {
                if (nucleotideChar.get(seqArray[i])==null)
                    nucleotides[pos++] = __;
                else
                    nucleotides[pos++] = nucleotideChar.get(seqArray[i]);
            }
        }
       
        
        return nucleotides;
    }

        /**
         * Converts Byte Array to String sequence
         * @param seq
         * @return
         */
    public String bytearray2string(byte[] seq)
    {
        char[] nucleotides = new char[seq.length];

        for (int i=0;i<nucleotides.length;i++)
        {
            if (nucleotideByte.get(seq[i])==null)
                nucleotides[i]='-';
            else
                nucleotides[i]=nucleotideByte.get(seq[i]);
        }

        return new String(nucleotides);
    }

    /**
     * Delete Gaps in byte-array
     * @param seq
     * @return
     */
    public byte[] killGaps(byte[] seq)
    {
        //count gaps
        int gaps = 0;
        for (byte d : seq)
        {
            if (d==__)
                gaps++;
        }

        if (gaps==0)
            return seq;

        //delete gaps
        byte[] seq_nogaps = new byte[seq.length-gaps];
        int pos = 0; //count position

        for (int i=0;i<seq.length;i++)
        {
            if (seq[i]!=__)
            {
                seq_nogaps[pos]=seq[i];
                pos++;
            }
        }

        return seq_nogaps;

    }

    /**
     * Returns complement of byteArray
     * @param seq
     * @param isRNA if true complement of A is U, if false complement of A is T
     * @return
     */
    public byte[] complement(byte[] seq, boolean isRNA)
    {
        byte[] revnucleotides = new byte[seq.length];

        if (isRNA)
            complementByte.put(_A, _U);
        else
            complementByte.put(_A, _T);

        for (int i=0;i<seq.length;i++)
        {
            if (complementByte.get(seq[i])==null)
                revnucleotides[i] = __;
            else
                revnucleotides[i] = complementByte.get(seq[i]);
        }

        complementByte.remove(_A);
        return revnucleotides;
    }

    /**
     * Changes Byte in given Byte-Array to another Byte. (Change all nucleotides in sequence).
     * @param seq
     * @param fromByte
     * @param toByte
     * @return
     */
    public byte[] changeByte(byte[] seq, byte fromByte, byte toByte)
    {
        for (int i=0; i<seq.length; i++)
        {
            if (seq[i]==fromByte)
                seq[i] = toByte;
        }

        return seq;
    }

    /**
     * Counts nucleotides in given Byte-Array.
     * @param seq
     * @return int[17]: counts of nucleotides; Index: Nucleotide-Value (Byte-Value); Value: Occurence of Nucleotide in given Sequence.
     */
    public int[] countNucleots(byte[] seq)
    {
        int[] count = new int[alphabetSize];

        for (int i=0; i<seq.length;i++)
        {
            count[(int)seq[i]]++;
        }
        return count;
    }


}
