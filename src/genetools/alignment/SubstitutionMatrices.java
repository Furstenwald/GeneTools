/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.alignment;

/**
 * Data obtained from ftp://ftp.ncbi.nih.gov/blast/matrices/
 * @author btserver
 */
public final class SubstitutionMatrices {

public SubstitutionMatrix[] blosum = new SubstitutionMatrix[16];
public SubstitutionMatrix[] pam = new SubstitutionMatrix[25];
public SubstitutionMatrix dna =  new SubstitutionMatrix();

public SubstitutionMatrices()
{
    dna = new SubstitutionMatricesDNA().dna;
    SubstitutionMatricesBlosum1 blosum1 = new SubstitutionMatricesBlosum1();
    SubstitutionMatricesBlosum2 blosum2 = new SubstitutionMatricesBlosum2();
    SubstitutionMatricesBlosum3 blosum3 = new SubstitutionMatricesBlosum3();
    for (int i=0;i<7;i++)
        blosum[i] = blosum1.blosum[i];
    for (int i=7;i<12;i++)
        blosum[i] = blosum2.blosum[i];
    for (int i=12;i<16;i++)
        blosum[i] = blosum3.blosum[i];

    SubstitutionMatricesPAM1 pam1 = new SubstitutionMatricesPAM1();
    SubstitutionMatricesPAM2 pam2 = new SubstitutionMatricesPAM2();
    SubstitutionMatricesPAM3 pam3 = new SubstitutionMatricesPAM3();
    SubstitutionMatricesPAM4 pam4 = new SubstitutionMatricesPAM4();
    SubstitutionMatricesPAM5 pam5 = new SubstitutionMatricesPAM5();

    for (int i=0;i<6;i++)
        pam[i] = pam1.pam[i];
    for (int i=6;i<13;i++)
        pam[i] = pam2.pam[i];
    pam[13] = pam3.pam[13];
    for (int i=14;i<20;i++)
        pam[i] = pam4.pam[i];
    for (int i=20;i<25;i++)
        pam[i] = pam5.pam[i];
}
}
