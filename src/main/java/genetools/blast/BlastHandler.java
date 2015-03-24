package genetools.blast;

import genetools.sequenceHandling.Position;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BlastHandler extends DefaultHandler {
	ArrayList<BlastHit> hits = new ArrayList<>();
	boolean inHit = false;
	boolean ishitid;
	String hitid;
	boolean ishitdef;
	String hitdef;
	boolean ishitaccession;
	String hitaccession;
	boolean ishitlength;
	int hitlength;

	boolean inHSP = false;
	boolean inScore;
	int score;
	boolean inEvalue;
	String evalue;
	boolean inQueryFrom;
	int queryfrom;
	boolean inQueryEnd;
	int queryend;
	boolean inHitFrom;
	int hitfrom;
	boolean inHitEnd;
	int hitend;
	boolean inIdentity;
	int identity;
	boolean inPositive;
	int positive;
	boolean inLength;
	int alLength;
	boolean inHitframe;
	boolean isReverse;

	ArrayList<BlastHsp> blastHsps = new ArrayList<>();

	public ArrayList<BlastHit> getOutput() {
		return hits;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("HIT")) {
			inHit = true;
		}
		switch (qName.toUpperCase()) {
		case "HIT":
			inHit = true;
			break;
		case "HIT_ID":
			ishitid = true;
			break;
		case "HIT_DEF":
			ishitdef = true;
			break;
		case "HIT_ACCESSION":
			ishitaccession = true;
			break;
		case "HIT_LEN":
			ishitlength = true;
			break;
		case "HSP":
			inHSP = true;
			break;
		case "HSP_SCORE":
			inScore = true;
			break;
		case "HSP_EVALUE":
			inEvalue = true;
			break;
		case "HSP_QUERY-FROM":
			inQueryFrom = true;
			break;
		case "HSP_QUERY-TO":
			inQueryEnd = true;
			break;
		case "HSP_HIT-FROM":
			inHitFrom = true;
			break;
		case "HSP_HIT-TO":
			inHitEnd = true;
			break;
		case "HSP_HIT-FRAME":
			inHitframe = true;
			break;
		case "HSP_IDENTITY":
			inIdentity = true;
			break;
		case "HSP_POSITIVE":
			inPositive = true;
			break;
		case "HSP_ALIGN-LEN":
			inLength = true;
			break;
		default:
			break;
		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
		if (ishitid) {
			hitid = new String(ch, start, length);
		}
		if (ishitdef) {
			hitdef = new String(ch, start, length);
		}
		if (ishitaccession) {
			hitaccession = new String(ch, start, length);
		}
		if (ishitlength) {
			hitlength = Integer.parseInt(new String(ch, start, length));
		}
		if (inScore) {
			score = Integer.parseInt(new String(ch, start, length));
		}
		if (inEvalue) {
			evalue = (new String(ch, start, length));
		}
		if (inQueryFrom) {
			queryfrom = Integer.parseInt(new String(ch, start, length));
		}
		if (inQueryEnd) {
			queryend = Integer.parseInt(new String(ch, start, length));
		}
		if (inHitFrom) {
			hitfrom = Integer.parseInt(new String(ch, start, length));
		}
		if (inHitEnd) {
			hitend = Integer.parseInt(new String(ch, start, length));
		}
		if (inHitframe) {
			isReverse = ((new String(ch, start, length)).startsWith("-"));
		}
		if (inIdentity) {
			identity = Integer.parseInt(new String(ch, start, length));
		}
		if (inPositive) {
			positive = Integer.parseInt(new String(ch, start, length));
		}
		if (inLength) {
			alLength = Integer.parseInt(new String(ch, start, length));
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		switch (qName.toUpperCase()) {
		case "HIT":
			inHit = false;
			BlastHit newHit = new BlastHit();
			newHit.accession = hitaccession;
			hitaccession = null;
			newHit.id = hitid;
			hitid = null;
			newHit.def = hitdef;
			hitdef = null;
			newHit.length = hitlength;
			hitlength = -1;
			newHit.blastHsps = cloneArray(blastHsps);
			hits.add(newHit);
			blastHsps = new ArrayList<>();
			break;
		case "HIT_ID":
			ishitid = false;
			break;
		case "HIT_DEF":
			ishitdef = false;
			break;
		case "HIT_ACCESSION":
			ishitaccession = false;
			break;
		case "HIT_LEN":
			ishitlength = false;
			break;
		case "HSP":
			inHSP = false;
			BlastHsp blastHsp = new BlastHsp();
			blastHsp.score = score;
			score = -1;
			blastHsp.evalue = evalue;
			evalue = null;
			blastHsp.queryStart = queryfrom;
			queryfrom = -1;
			blastHsp.queryEnd = queryend;
			queryend = -1;
			blastHsp.hitPosition = new Position(hitfrom-1, hitend-1, isReverse);
			hitfrom = -1;
			hitend = -1;
			isReverse = false;
			blastHsp.identity = identity;
			identity = -1;
			blastHsp.positive = positive;
			positive = -1;
			blastHsp.alignLength = alLength;
			alLength = -1;
			blastHsps.add(blastHsp);
			break;
		case "HSP_SCORE":
			inScore = false;
			break;
		case "HSP_EVALUE":
			inEvalue = false;
			break;
		case "HSP_QUERY-FROM":
			inQueryFrom = false;
			break;
		case "HSP_QUERY-TO":
			inQueryEnd = false;
			break;
		case "HSP_HIT-FROM":
			inHitFrom = false;
			break;
		case "HSP_HIT-TO":
			inHitEnd = false;
			break;
		case "HSP_HIT-FRAME":
			inHitframe = false;
			break;
		case "HSP_IDENTITY":
			inIdentity = false;
			break;
		case "HSP_POSITIVE":
			inPositive = false;
			break;
		case "HSP_ALIGN-LEN":
			inLength = false;
		default:
			break;
		}
	}// finito end element

	public static ArrayList<BlastHsp> cloneArray(ArrayList<BlastHsp> original) {
		ArrayList<BlastHsp> copy = new ArrayList<BlastHsp>();
		for (BlastHsp h : original) {
			copy.add(new BlastHsp(h));
		}
		return copy;
	}
}
