package genetools.blast;

import java.util.ArrayList;

public class BlastHit {
	public String id;
	public String def;
	public String accession;
	public int length;
	
	public ArrayList<BlastHsp> blastHsps = new ArrayList<>();
}
