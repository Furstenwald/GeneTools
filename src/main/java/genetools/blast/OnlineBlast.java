package genetools.blast;

import genetools.sequenceHandling.SequenceAA;
import genetools.sequenceHandling.SequenceDNA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class OnlineBlast {

	public static ArrayList<BlastHit> doBLASTn(SequenceDNA seq) {
		return doBLAST(seq.toString(), "blastn", "nr");
	}
	
	public static ArrayList<BlastHit> doBLASTp(SequenceAA seq) {
		return doBLAST(seq.toString(), "blastp", "nr");
	}
	
	public static ArrayList<BlastHit> doTBLASTn(SequenceAA seq) {
		return doBLAST(seq.toString(), "tblastn", "nr");
	}


	private static ArrayList<BlastHit> doBLAST(String seq, String program, String database) {
		try {
			URL url = new URL(
					"http://www.ncbi.nlm.nih.gov/blast/Blast.cgi?QUERY="
							+ seq
							+ "&DATABASE="+database+"&HITLIST_SIZE=500&FILTER=L&FORMAT_TYPE=XML&PROGRAM="+program+"&CLIENT=web&SERVICE=plain&NCBI_GI=on&PAGE=Nucleotides&CMD=Put");
			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;

			String RID = null;

			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
				if (inputLine.equalsIgnoreCase("nothing has been found")) {
					System.out.println("Nothing found");
					in.close();
				}
				inputLine = inputLine.trim();
				if (inputLine.startsWith("RID = ")) {
					RID = inputLine.substring(6);
				}
			}
			in.close();

			if (RID == null) {
				System.err.println("No RID received");
				System.exit(0);
			} else {
				System.err.println("RID: " + RID);
			}

			File temp = null;
			WaitForReady: while (true) {
				Thread.sleep(5000);
				url = new URL(
						"http://www.ncbi.nlm.nih.gov/blast/Blast.cgi?RID="
								+ RID
								+ "&CMD=Get&FORMAT_TYPE=XML&NCBI_GI=yes&SHOW_OVERVIEW=no");
				yc = url.openConnection();
				in = new BufferedReader(new InputStreamReader(
						yc.getInputStream()));

				boolean ready = false;

				temp = new File("temp");
				temp.createNewFile();
				FileWriter schreiber = new FileWriter(temp);
				BufferedWriter bschreiber = new BufferedWriter(schreiber);

				while ((inputLine = in.readLine()) != null) {
					if (!ready) {
						//System.out.println(inputLine);
						inputLine = inputLine.trim();
						if (inputLine.startsWith("Status=WAITING")) {
							System.err.println("Waiting...");
							continue WaitForReady;
						} else if (inputLine.startsWith("<BlastOutput>")) {
							ready = true;
						}
					}
					if (ready) { // ready, read output
						bschreiber.write(inputLine);
						//System.out.println("*"+inputLine);
						bschreiber.newLine();
					}
				} // end of file
				bschreiber.close();
				schreiber.close();
				break;
			} // end of wait

			return OutputParser.parseBlastOutputXml(temp);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
