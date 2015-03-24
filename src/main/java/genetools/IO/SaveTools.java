package genetools.IO;

import genetools.exceptions.BadGeneFileException;
import genetools.Species;

import java.io.*;


public class SaveTools {
	/**
	 * Saves Molecule to file
	 * @param molecule Molecule to save
	 * @param f Saved to this file
	 * @return
	 */
	public static boolean saveSpecies(Species species, File f) throws IOException
	{
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try
		{
			fos = new FileOutputStream(f);
			out = new ObjectOutputStream(fos);
			out.writeObject(species);
			out.close();
			fos.close();
			
			return true;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	
	/**
	 * Loads a Molecule from specified File f
	 * @param f
	 * @return
	 * @throws BadGeneFileException
	 */
	public static Species loadSpecies(File f) throws BadGeneFileException
	{
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		Species spec = null;
		try
		{
			fis = new FileInputStream(f);
			in = new ObjectInputStream(fis);
			spec = (Species)in.readObject();
			in.close();
			fis.close();
		}
		catch(StreamCorruptedException ex)
		{
			throw new BadGeneFileException(BadGeneFileException.NOT_A_GENEWARRIOR_FILE);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			throw (new BadGeneFileException(BadGeneFileException.IO_ERROR));
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
			throw new BadGeneFileException(BadGeneFileException.CAST_ERROR);
		}
		catch(ClassCastException ex)
		{
			ex.printStackTrace();
			throw new BadGeneFileException(BadGeneFileException.CAST_ERROR);
		}
		finally {
			try {
				in.close();
				fis.close();
			} catch (IOException e) {
				//ignore
			}
		}
		
		return spec;
	}
	
	
}
