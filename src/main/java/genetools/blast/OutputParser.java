package genetools.blast;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class OutputParser {
	public static ArrayList<BlastHit> parseBlastOutputXml(File f) {
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			BlastHandler handler = new BlastHandler();
			saxParser.parse(f, handler);
			return handler.getOutput();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
