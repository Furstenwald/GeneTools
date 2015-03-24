package genetools.IO;

import genetools.Species;
import genetools.sequenceHandling.NamedSequenceDNA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class downloadNCBI {
	public static ArrayList<NamedSequenceDNA> downloadFASTA(String nucid)
	{
	    File temp = null;
	    try {
	            URL url = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id="+nucid+"&rettype=fasta&retmode=text");
	            URLConnection yc = url.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	            String inputLine;
	            temp = File.createTempFile("genew", ".tmp");
	            temp.createNewFile();
	            FileWriter schreiber = new FileWriter(temp);
	            BufferedWriter bschreiber = new BufferedWriter(schreiber);


	        while ((inputLine = in.readLine()) != null) {
	        	//System.out.println(inputLine);
	            if (inputLine.equalsIgnoreCase("nothing has been found"))
	            {
	                System.out.println("Not a valid nuc id: "+ nucid);
	                in.close();
	                bschreiber.close();
	                schreiber.close();
	                temp.delete();
	                return null;
	            }
	            bschreiber.write(inputLine+"\n");
	        }
	        in.close();
	        bschreiber.close();
	        schreiber.close();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        try {
	        	ArrayList<NamedSequenceDNA> dgb = FastaIO.readFastaDNA(temp);
	        	temp.delete();
	            return dgb;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	    return null;
	}
	
	public static Species downloadGenebank(String genomeid)
	{
	    File temp = null;
	    try {
	            URL url = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id="+genomeid+"&rettype=gp&retmode=text");
	            URLConnection yc = url.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	            String inputLine;
	            temp = File.createTempFile("genew", ".tmp");
	            temp.createNewFile();
	            FileWriter schreiber = new FileWriter(temp);
	            BufferedWriter bschreiber = new BufferedWriter(schreiber);


	        while ((inputLine = in.readLine()) != null) {
	            if (inputLine.equalsIgnoreCase("nothing has been found"))
	            {
	                System.out.println("Not a valid genomeid: "+ genomeid);
	                in.close();
	                bschreiber.close();
	                schreiber.close();
	                temp.delete();
	                return null;
	            }
	            bschreiber.write(inputLine+"\n");
	        }
	        in.close();
	        bschreiber.close();
	        schreiber.close();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        try {
	            genbankRead dgb = new genbankRead(temp);
	            temp.delete();
	        	
	            return dgb.getSpecies();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	    return null;
	}
}
