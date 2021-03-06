/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author btserver
 */
public abstract class GeneWarriorElement implements Cloneable, Serializable {

	private static final long serialVersionUID = -7634092283396779826L;
	protected String shortcut;
    
    public enum ElementType {Species, Contig, Feature, ProteinProduct};
    protected GeneWarriorElement parent;

    /**
     * What type of Object is this <code>GeneWarriorElement</code>? Species, Contig, Feature or ProteinProduct
     * @return
     */
    public abstract ElementType getObjectType();

    /**
     * Returns the associated parent. E.g. the Feature for the ProteinProduct, the Contig for the Feature or the Species for the Contig.
     * A species returns null.
     * @return
     */
    public GeneWarriorElement getParent()
    {
        return parent;
    }

    public void setParent(GeneWarriorElement parent)
    {
        this.parent = parent;
    }

    public GeneWarriorElement() {
    }

    public String getShortcut() {
        return shortcut;
    }

    public abstract boolean setShortcut(String sc);

    /**
     * Creates randomly a 8digit shortcut number
     * @return
     */
    public static String generateShortcutNumber()
    {
        String output ="";
        Random rnd = new Random();
        for (int i=0;i<8;i++)
        {
            output=output+rnd.nextInt(10);
        }
        return output;
    }

}
