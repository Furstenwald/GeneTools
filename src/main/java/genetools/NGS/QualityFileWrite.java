package genetools.NGS;

import java.io.*;

/**
 *
 * @author corona
 */
public class QualityFileWrite {

    BufferedWriter out;
    public QualityFileWrite(File FASTQ) throws FileNotFoundException, IOException
    {
        out = new BufferedWriter(new FileWriter(FASTQ));
    }
    

    public void write(QualitySequenceDNA qs) throws IOException
    {
        out.write("@"+qs.getName());out.newLine();
        
        out.write(qs.getSequence().toString());out.newLine();
        out.write("+");out.newLine();
        
        out.write(NGSTools.convertQualities(qs.getQuality()));
        out.newLine();
    }
  
    public void close() throws IOException {
        out.close();
    }

}
