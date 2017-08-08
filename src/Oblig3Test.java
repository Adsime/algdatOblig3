/**
 * Created by Adrian on 02/11/2015.
 */
////// Oblig3Test ////////////////////////////////////////////////

/*
Adrian Siim Melsom, s236308, HINGDATA14HA
Håkon Thorkildsen Smørvik, s929559, HINGDATA14HA
Baljit Singh Sarai, s169947, INFORMATIK14HA
*/

        import java.util.*;

public class Oblig3Test
{
 public static void main(String[] args)
    {
        int antallFeil = 0;

        test(0);
        test(1);
        test(2);
        System.out.println((int)(1/2));


        int t = 0;
        for(int i = 1; i < 5; i++) {
            t += i/2;
        }
        System.out.println(t);
        t = 0;
        for(int i = 1; i < 5; i+=2) {
            t += i/2;
        }
        System.out.println(t);



        antallFeil += oppgave1();
        antallFeil += oppgave2();
        antallFeil += oppgave3();
        antallFeil += oppgave4();
        antallFeil += oppgave5();
        antallFeil += oppgave6();
        antallFeil += oppgave7();
        antallFeil += oppgave8();
        antallFeil += oppgave9();
        antallFeil += oppgave10();


        if (antallFeil == 0)
        {
            System.out.println("Gratulerer!! Du passerte testen!");
        }
        else
        {
            System.out.println
                    ("\nDette må forbedres. Du har minst " + antallFeil + " feil!");
        }
    }

    public static void test(int oline) {
        int ola = oline%3;
        int kari = 3; int nils = 4; int jens = 2;
        switch (ola) {
            case 0: kari = jens; break;
            case 1: nils = kari; break;
            case 2: kari = ola; break;
            default: nils = oline;
        }
        System.out.println(nils + oline);
    }


    // OPPGAVE 1 ////////////////////////////////////////////////

    public static int oppgave1()
    {
        int antallFeil = 0;
        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        try
        {
            if (tre.antall() != 0)
            {
                antallFeil++;
                System.out.println("Oppgave 1a: Feil antall i et tomt tre!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 1b: Skal ikke kaste unntak for et tomt tre");
        }

        tre.leggInn(1); tre.leggInn(2); tre.leggInn(3);

        if (tre.antall() != 3)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 1c: Antall blir ikke oppdatert!");
        }

        return antallFeil;

    }  // slutt på Oppgave 1


    // OPPGAVE 2 ////////////////////////////////////////////////

    public static int oppgave2()
    {
        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        int antallFeil = 0;

        tre.leggInn(1);

        try
        {
            if (tre.antall(1) != 1)
            {
                antallFeil++;
                System.out.println("Oppgave 2a: Feil antall(T)-metoden!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 2b: Skal ikke kaste unntak her!");
        }

        // Nytt tre
        tre = new ObligSBinTre<>(Comparator.naturalOrder());
        int[] a = {1,7,1,6,1,5,1,4,1,1,1,3};
        for (int verdi : a) tre.leggInn(verdi);

        if (tre.antall(7) != 1 || tre.antall(6) != 1
                || tre.antall(5) != 1 || tre.antall(4) != 1
                || tre.antall(3) != 1 || tre.antall(2) != 0
                || tre.antall(1) != 7 || tre.antall(0) != 0)
        {
            antallFeil++;
            System.out.println("Oppgave 2c: Feil antall(T)-metoden!");
        }

        return antallFeil;

    }  // slutt på Oppgave 2


    // OPPGAVE 3 ////////////////////////////////////////////////

    public static int oppgave3()
    {
        int antallFeil = 0;
        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        String s;

        try
        {
            s = tre.toString();
            if (!s.equals("[]"))
            {
                antallFeil++;
                System.out.println("Oppgave 3a: Feil i toString() for et tomt tre!!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 3b: Skal ikke kaste unntak for et tomt tre!");
        }

        // legger inn 10
        tre.leggInn(10);

        s = tre.toString();
        if (!s.equals("[10]"))
        {
            antallFeil++;
            System.out.println("Oppgave 3c: Feil i toString() for et tre med kun en verdi!");
        }

        // legger inn flere verdier
        int[] a = {6,14,1,8,12,3,7,9,11,13,2,5,4};
        for (int verdi : a) tre.leggInn(verdi);

        try
        {
            s = tre.toString();
            if (!s.equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]"))
            {
                antallFeil++;
                System.out.println("Oppgave 3d: Feil i toString()! Men feilen kan");
                System.out.println("ligge i leggInn() eller i nesteInorden().");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 3e: Her kastes et unntak! Det skal ikke skje!");
        }

        // Et nytt tre
        tre = new ObligSBinTre<>(Comparator.naturalOrder());

        // legger inn 10 flere ganger
        for (int i = 0; i < 4; i++) tre.leggInn(10);

        s = tre.toString();
        if (!s.equals("[10, 10, 10, 10]"))
        {
            antallFeil++;
            System.out.println("Oppgave 3f: Feil i toString()! Men feilen kan");
            System.out.println("ligge i leggInn() eller i nesteInorden().");
        }

        // Et nytt tre
        tre = new ObligSBinTre<>(Comparator.naturalOrder());

        int[] b = {5,4,3,2,1};
        for (int k : b) tre.leggInn(k);

        s = tre.toString();
        if (!s.equals("[1, 2, 3, 4, 5]"))
        {
            antallFeil++;
            System.out.println("Oppgave 3g: Feil i toString()! Men feilen kan");
            System.out.println("ligge i leggInn() eller i nesteInorden().");
        }

        return antallFeil;

    }  // slutt på Oppgave 3


////// hjelpemetoder //////////////////////////

    public static void bytt(int[] a, int i, int j)
    {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static boolean nestePermutasjon(int[] a)
    {
        int n = a.length, i = n - 2;  // i starter nest bakerst i a
        while (i >= 0 && a[i] > a[i+1]) i--;  // går forover i a

        // hvis i nå er lik -1, må a = {n,n-1, . . . , 2,1}
        if (i < 0) return false;  // a er den siste

        int j = n - 1;  // j starter bakerst i a
        for (int x = a[i]; a[j] < x; j--);     // den første større enn x
        bytt(a,i,j);    // bytter om

        for (j = n; ++i < --j; ) bytt(a,i,j);  // snur det til høyre for i
        return true;    // a inneholder en ny permutasjon
    }

// OPPGAVE 4 ////////////////////////////////////////////////

    public static int oppgave4()
    {
        int antallFeil = 0;

        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        String s;

        try
        {
            s = tre.omvendtString();
            if (!s.equals("[]"))
            {
                antallFeil++;
                System.out.println("Oppgave 4a: Feil i omvendtString() for et tomt tre!!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 4b: Skal ikke kaste unntak for et tomt tre!");
        }

        int[][] a = { {1,2,3,4,5}, {5,4,3,2,1}, {4,2,5,1,3},
                {2,1,4,3,5}, {1,4,2,5,3}, {4,1,5,3,2} };


        for (int[] b : a)
        {
            tre = new ObligSBinTre<>(Comparator.naturalOrder());

            for (int k : b) tre.leggInn(k);
            s = tre.omvendtString();

            if (!s.equals("[5, 4, 3, 2, 1]"))
            {
                antallFeil++;
                System.out.println("Oppgave 4c: Feil i omvendtString()! Treet");
                System.out.println("ble bygget opp med verdiene " + Arrays.toString(b));
                break;
            }
        }

        return antallFeil;

    }  // slutt på Oppgave 4


    // OPPGAVE 5 ////////////////////////////////////////////////

    public static int oppgave5()
    {
        int antallFeil = 0;

        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        String s;

        tre.leggInn(6);
        tre.fjern(6);

        s = tre.toString();
        if (!s.equals("[]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5a: Feil i fjern(T)!");
        }

        int[] a = {6,3,9,1,5,7,10,2,4,8,11,6,8};
        for (int verdi : a) tre.leggInn(verdi);

        boolean fjernet = tre.fjern(12);
        s = tre.toString();

        if (!s.equals("[1, 2, 3, 4, 5, 6, 6, 7, 8, 8, 9, 10, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5b: Feil i fjern(T)! Tallet 12 er ikke i treet!");
        }

        if (fjernet == true)
        {
            antallFeil++;
            System.out.println("Oppgave 5c: Feil i fjern(T)! Skal returnere false når");
            System.out.println("verdien ikke er i treet.");
        }

        if (tre.antall() != 13)
        {
            antallFeil++;
            System.out.println("Oppgave 5d: Feil i fjern(T)! Variabelen antall skal");
            System.out.println("ikke endres for en mislykket fjerning.");
        }

        fjernet = tre.fjern(2);
        s = tre.toString();

        if (!s.equals("[1, 3, 4, 5, 6, 6, 7, 8, 8, 9, 10, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5e: Feil i fjern(T)!");
        }

        if (fjernet == false)
        {
            antallFeil++;
            System.out.println("Oppgave 5f: Feil i fjern(T)! Skal returnere true");
            System.out.println("for en vellykket fjerning.");
        }

        if (tre.antall() != 12)
        {
            antallFeil++;
            System.out.println("Oppgave 5g: Feil i fjern(T)! Variabelen antall skal");
            System.out.println("reduseres med 1 for en vellykket fjerning.");
        }

        tre.fjern(4);
        s = tre.toString();

        if (!s.equals("[1, 3, 5, 6, 6, 7, 8, 8, 9, 10, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5h: Feil i fjern(T)!");
        }

        tre.fjern(6);
        s = tre.toString();

        if (!s.equals("[1, 3, 5, 6, 7, 8, 8, 9, 10, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5i: Feil i fjern(T)!");
        }

        tre.fjern(8);
        s = tre.toString();

        if (!s.equals("[1, 3, 5, 6, 7, 8, 9, 10, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5j: Feil i fjern(T)!");
        }

        tre.fjern(10); tre.fjern(11); tre.fjern(8); tre.fjern(7);
        s = tre.toString();

        if (!s.equals("[1, 3, 5, 6, 9]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5k: Feil i fjern(T)!");
        }

        tre.fjern(1); tre.fjern(3); tre.fjern(5); tre.fjern(9);

        s = tre.toString();

        if (!s.equals("[6]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5l: Feil i fjern(T)!");
        }

        tre.nullstill();

        if (tre.antall() != 0)
        {
            antallFeil++;
            System.out.println("Oppgave 5m: Feil i nullstill() - antall er feil!");
        }

        s = tre.toString();

        if (!s.equals("[]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5n: Feil i nullstill()!");
        }

        try
        {
            tre.nullstill();
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 5o: Skal ikke kaste unntak når et tomt tre nullstilles!");
        }

        try
        {
            if (tre.fjernAlle(0) != 0)
            {
                antallFeil++;
                System.out.println("Oppgave 5p: Feil i fjernAlle(T) for tomt tre!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 5q: Kaster unntak i fjernAlle(T) for tomt tre!");
        }

        tre.leggInn(0);

        try
        {
            if (tre.fjernAlle(0) != 1)
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 5r: Feil i fjernAlle(T) for tre med en verdi!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 5s: Kaster unntak i fjernAlle(T) for tre med en verdi!");
        }

        int[] b = {1,4,1,3,1,2,1,1};
        for (int verdi : b) tre.leggInn(verdi);

        if (tre.fjernAlle(1) != 5)
        {
            antallFeil++;
            System.out.println("Oppgave 5t: Feil i fjernAlle(T)!");
        }

        s = tre.toString();
        if (!s.equals("[2, 3, 4]"))
        {
            antallFeil++;
            System.out.println("Oppgave 5u: Feil i fjernAlle(T)!");
        }

        tre = new ObligSBinTre<>(Comparator.naturalOrder());

        Random r = new Random();
        for (int i = 0; i < 500_000; i++) tre.leggInn(r.nextInt(1_000_000));

        long tid = System.currentTimeMillis();
        tre.nullstill();
        tid = System.currentTimeMillis() - tid;

        if (tid < 10)
        {
            antallFeil++;
            System.out.println("Oppgave 5v: Har du kodet nullstill() ved kun");
            System.out.println("nullstille hode og antall? Alle nodeverdier og");
            System.out.println("pekere i treet skal nulles!");
        }

        return antallFeil;

    }  // slutt på Oppgave 5


    // OPPGAVE 6 ////////////////////////////////////////////////

    public static int oppgave6()
    {
        int antallFeil = 0;

        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        String s = null;

        try
        {
            s = tre.høyreGren();
            if (!s.equals("[]"))
            {
                antallFeil++;
                System.out.println("Oppgave 6a: Det skal bli [] for et tomt tre!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 6b: Det skal ikke kastes unntak for et tomt tre!");
        }

        tre.leggInn(3);
        s = tre.høyreGren();

        if (!s.equals("[3]"))
        {
            antallFeil++;
            System.out.print("Oppgave 6c: Feil - du har " + s + ", det skal");
            System.out.println(" være [3].");
        }

        int[] a = {1,8,2,4,7,5,6,6};
        for (int verdi : a) tre.leggInn(verdi);

        s = tre.høyreGren();
        if (!s.equals("[3, 8, 4, 7, 5, 6, 6]"))
        {
            antallFeil++;
            System.out.print("Oppgave 6d: Feil - du har " + s + ", det skal");
            System.out.println(" være [3, 8, 4, 7, 5, 6, 6].");
        }

        // Et nytt tre
        tre = new ObligSBinTre<>(Comparator.naturalOrder());

        tre.leggInn(4); tre.leggInn(3); tre.leggInn(2); tre.leggInn(1);

        s = tre.høyreGren();
        if (!s.equals("[4, 3, 2, 1]"))
        {
            antallFeil++;
            System.out.print("Oppgave 6e: Feil - du har " + s + ", det skal");
            System.out.println(" være [4, 3, 2, 1].");
        }

        s = tre.lengstGren();

        if (!s.equals("[4, 3, 2, 1]"))
        {
            antallFeil++;
            System.out.print("Oppgave 6f: Feil - du har " + s + ", det skal");
            System.out.println(" være [4, 3, 2, 1].");
        }

        // Et nytt tre
        ObligSBinTre<String> tre2 =
                new ObligSBinTre<>(Comparator.naturalOrder());

        try
        {
            s = tre2.lengstGren();
            if (!s.equals("[]"))
            {
                antallFeil++;
                System.out.print("Oppgave 6g: Feil - du har " + s + ", det skal");
                System.out.println(" være [].");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 6h: Det skal ikke kastes unntak for et tomt tre!");
        }

        tre2.leggInn("F");
        s = tre2.lengstGren();

        if (!s.equals("[F]"))
        {
            antallFeil++;
            System.out.print("Oppgave 6i: Feil - du har " + s + ", det skal");
            System.out.println(" være [F].");
        }

        tre2.leggInn("B"); tre2.leggInn("AAAAAAAAAAAAAAAAAAAA");
        String[] verdi = "HDGOCEKPJMIL".split("");
        for (String t : verdi) tre2.leggInn(t);

        s = tre2.lengstGren();

        if (!s.equals("[F, H, O, K, M, L]"))
        {
            antallFeil++;
            System.out.print("Oppgave 6j: Feil - du har " + s + ", det skal");
            System.out.println(" være [F, H, O, K, M, L].");
        }

        return antallFeil;

    }  // slutt på Oppgave 6


    // OPPGAVE 7 ////////////////////////////////////////////////

    public static int oppgave7()
    {
        int antallFeil = 0;

        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        String[] s = tre.grener();

        try
        {
            if (s.length != 0)
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 7a: Feil i grener() for tomt tre!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 7b: Metoden grener() skal ikke kaste unntak for et tomt tre!");
        }

        tre.leggInn(10);
        s = tre.grener();
        String t = Arrays.toString(s);

        if (!t.equals("[[10]]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 7c: Feil i grener() for et tre med en verdi!");
        }

        tre.leggInn(6); tre.leggInn(9); tre.leggInn(7); tre.leggInn(8);
        t = Arrays.toString(tre.grener());

        if (!t.equals("[[10, 6, 9, 7, 8]]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 7d: Feil grener() for tre med en gren!");
        }

        tre.nullstill();

        int[] a = {4,1,6,3,5,8,2,7,9};
        for (int verdi : a) tre.leggInn(verdi);
        s = tre.grener();

        if (!s[0].equals("[4, 1, 3, 2]") || !s[1].equals("[4, 6, 5]")
                || !s[2].equals("[4, 6, 8, 7]") || !s[3].equals("[4, 6, 8, 9]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 7e: Feil i metoden grener()!");
        }

        return antallFeil;

    }  // slutt på Oppgave 7


    // OPPGAVE 8 ////////////////////////////////////////////////

    public static int oppgave8()
    {
        int antallFeil = 0;

        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        String s = tre.bladnodeverdier();

        try
        {
            if (!s.equals("[]"))
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 8a: Skal returnere [] for tomt tre!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 8b: Skal ikke kaste unntak for et tomt tre!");
        }

        tre.leggInn(10);
        s = tre.bladnodeverdier();

        if (!s.equals("[10]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 8c: Feil - du har " + s + ", det skal være [10].");
        }

        tre.leggInn(11);
        s = tre.bladnodeverdier();

        if (!s.equals("[11]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 8d: Feil - du har " + s + ", det skal være [11].");
        }

        int[] a = {7,5,8,15,6,9,13,16,12,14};
        for (int verdi : a) tre.leggInn(verdi);
        s = tre.bladnodeverdier();

        if (!s.equals("[6, 9, 12, 14, 16]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 8e: Feil - du har " + s + ", det skal være [6, 9, 12, 14, 16].");
        }

        tre.nullstill();

        for (int i = 1; i <= 5; i++) tre.leggInn(i);
        s = tre.bladnodeverdier();

        if (!s.equals("[5]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 8f: Feil - du har " + s + ", det skal være [5].");
        }

        tre.nullstill();

        for (int i = 5; i >= 1; i--) tre.leggInn(i);
        s = tre.bladnodeverdier();

        if (!s.equals("[1]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 8g: Feil - du har " + s + ", det skal være [1].");
        }

        return antallFeil;

    }  // slutt på Oppgave 8


    // OPPGAVE 9 ////////////////////////////////////////////////

    public static int oppgave9()
    {
        int antallFeil = 0;

        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        Iterator<Integer> i = tre.iterator();

        try
        {
            i.next();
            antallFeil++;
            System.out.println("Oppgave 9a: Skal kaste unntak når treet er tomt!");
        }
        catch (Exception e)
        {
            if (!(e instanceof NoSuchElementException))
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 9b: Skal kaste NoSuchElementException her!");
            }
        }

        tre.leggInn(10);
        i = tre.iterator();

        if (i.next().compareTo(10) != 0)
        {
            antallFeil++;
            System.out.println("Oppgave 9c: Her skal next() returnere 10!");
        }

        try
        {
            i.next();
            antallFeil++;
            System.out.println
                    ("Oppgave 9d: Skal kaste unntak når det ikke er flere igjen!");
        }
        catch (Exception e)
        {
            if (!(e instanceof NoSuchElementException))
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 9e: Skal kaste NoSuchElementException her!");
            }
        }

        tre.nullstill();

        int[] a = {5,2,8,1,4,6,9,3,7};
        for (int k : a) tre.leggInn(k);

        List<Integer> liste = new ArrayList<>();
        tre.forEach(verdi -> liste.add(verdi));
        String s = liste.toString();

        if (!s.equals("[1, 3, 7, 9]"))
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 9f: Feil - du har " + s + ", det skal være [1, 3, 7, 9].");
            System.out.println
                    ("            Din iterator skal kun gå gjennom bladnodene!");

        }

        tre.nullstill();
        tre.leggInn(1); tre.leggInn(2); tre.leggInn(3); tre.leggInn(4);

        int verdi = tre.iterator().next();
        if (verdi != 4)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 9g: Treet har kun 4 som bladnodeverdi!");
        }

        return antallFeil;

    }  // slutt på Oppgave 9


// OPPGAVE 10 ////////////////////////////////////////////////

    public static int oppgave10()
    {
        int antallFeil = 0;

        ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        Iterator<Integer> i = tre.iterator();

        try
        {
            i.remove();
            antallFeil++;
            System.out.println("Oppgave 10a: Skal kaste unntak når treet er tomt!");
        }
        catch (Exception e)
        {
            if (!(e instanceof IllegalStateException))
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 10b: Skal kaste IllegalStateException her!");
            }
        }

        tre.leggInn(2); tre.leggInn(1); tre.leggInn(3);
        i = tre.iterator();
        i.next();i.remove();
        try
        {
            i.remove();
            antallFeil++;
            System.out.println("Oppgave 10c: Ikke tillatt med remove() her!");
        }
        catch (Exception e)
        {
            if (!(e instanceof IllegalStateException))
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 10d: Skal kaste IllegalStateException her!");
            }
        }

        tre.nullstill();

        int[] a = {7,1,11,3,8,12,2,5,10,4,6,9};
        for (int verdi : a) tre.leggInn(verdi);

        try
        {
            tre.fjernHvis(x -> x % 2 == 0);  // fjerner bladnoder med partall

            if (!tre.toString().equals("[1, 3, 5, 7, 8, 9, 10, 11]"))
            {
                antallFeil++;
                System.out.println("Oppgave 10e: Feil i metoden remove()!");
            }

            if (tre.antall() != 8)
            {
                antallFeil++;
                System.out.println
                        ("Oppgave 10f: Feil - må ha antall-- i remove()!");
            }
        }
        catch (Exception e)
        {
            antallFeil++;
            System.out.println
                    ("Oppgave 10g: Skal ikke kaste unntak her!");
        }

        tre.fjernHvis(x -> x % 2 != 0);  // fjerner bladnoder med oddetall

        if (!tre.toString().equals("[1, 3, 7, 8, 10, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 10h: Feil i metoden remove()!");
        }

        tre.fjernHvis(x -> true);

        if (!tre.toString().equals("[1, 7, 8, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 10i: Feil i metoden remove()!");
        }

        tre.fjernHvis(x -> true);

        if (!tre.toString().equals("[7, 11]"))
        {
            antallFeil++;
            System.out.println("Oppgave 10j: Feil i metoden remove()!");
        }

        tre.fjernHvis(x -> true);

        if (!tre.toString().equals("[7]"))
        {
            antallFeil++;
            System.out.println("Oppgave 10k: Feil i metoden remove()!");
        }

        tre.fjernHvis(x -> true);

        if (!tre.toString().equals("[]"))
        {
            antallFeil++;
            System.out.println("Oppgave 10l: Feil i metoden remove()!");
        }

        return antallFeil;

    }  // slutt på Oppgave 10

} // Oblig3Test
