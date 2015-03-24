package genetools;

import java.util.ArrayList;

/**
 *
 * @author btserver
 */
public class Species  extends GeneWarriorElement{
    private static final long serialVersionUID = 604094988740974903L;
    public String name = "no name set";
    public String shortname = "no name set";

    public ArrayList<Contig> contigs = new ArrayList<Contig>();


    public boolean setShortcut(String sc)
    {
        if (!sc.matches("@S\\d{8}@"))
            return false;
        shortcut = sc;
        return true;
    }

    @Override
    public GeneWarriorElement getParent()
    {
        return null;
    }

    public void addContig(Contig contig)
    {
        contig.setParent(this);
        contigs.add(contig);
        return;
    }

    public ElementType getObjectType()
    {
        return GeneWarriorElement.ElementType.Species;
    }

}
