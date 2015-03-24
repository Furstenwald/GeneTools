package genetools.blast;

import genetools.sequenceHandling.Position;

/**
 * High-scoring segment pairs
 * @author r.follador
 *
 */
public class BlastHsp {
	public int score;
	public String evalue;
	public int queryStart;
	public int queryEnd;
	public Position hitPosition;
	public int identity;
	public int positive;
	public int alignLength;
	
		
	public String toString() {
		return score+" "+evalue+" Query: "+queryStart+"-"+queryEnd+" Hit: "+hitPosition.getStartPos()+"-"+hitPosition.getEndPos()+(hitPosition.isReverse()?" Reverse":" Forward")+" ID/Pos "+identity+"/"+positive+" Length: "+alignLength;
	}
	
	public BlastHsp(BlastHsp original) {
		this.score = original.score;
		this.evalue = original.evalue;
		this.queryStart = original.queryStart;
		this.queryEnd = original.queryEnd;
		this.hitPosition = new Position(original.hitPosition.getStartPos(), original.hitPosition.getEndPos(), original.hitPosition.isReverse());
		this.identity = original.identity;
		this.positive = original.positive;
		this.alignLength = original.alignLength;
	}
	
	public BlastHsp() {
		
	}
}
