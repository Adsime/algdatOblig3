/**
 * Created by Adrian on 02/10/2015.
 */
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;


/*
Adrian Siim Melsom, s236308, HINGDATA14HA
Håkon Thorkildsen Smørvik, s929559, HINGDATA14HA
Baljit Singh Sarai, s169947, INFORMATIK14HA
  */

public class DobbeltLenketListe<T> implements Liste<T>
{

    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        // konstruktør
        private Node(T verdi, Node<T> forrige, Node<T> neste)
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int antallEndringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        if(indeks == 0 && antall == 0) {
            return null;
        }
        Node<T> n;
        if(indeks < antall/2) {
            n = hode;
            for(int i = 0; i < indeks; i++) {
                n = n.neste;
            }
            return n;
        } else {
            n = hale;
            for(int i = antall-1; i > indeks; i--) {
                n = n.forrige;
            }
            return n;
        }
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        antallEndringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        Objects.requireNonNull(a, "Tabellen a er null!");
        if(a.length == 0) {
            hode = hale = null;
            antall = 0;
            antallEndringer = 0;
        } else {
            Node<T> forrige = null;
            for(T t : a) {
                if(t != null) {
                    if(antall == 0) {
                        hode = hale = forrige = new Node<>(t, null, null);
                        antall++;
                    } else {
                        hale = forrige = forrige.neste = new Node<>(t, forrige, null);
                        antall++;
                    }
                }
            }
        }
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Verdien er null!");
        Node<T> n = new Node<>(verdi, null, null);
        if(tom()) {
            hode = n;
            hale = n;
            antallEndringer = ++antall;
            return true;
        } else {
            hale.neste = n;
            n.forrige = hale;
            hale = n;
            antall++;
            antallEndringer++;
            return true;
        }
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "verdi er null");
        indeksKontroll(indeks, true);
        Node<T> ny = new Node<>(verdi, null, null);

        if(antall == 0) {
            hode = hale = ny;
        } else if(indeks == antall) {
            ny.forrige = hale;
            hale.neste = ny;
            hale = ny;
        } else {
            Node<T> n = finnNode(indeks);
            if(n.forrige == null) {
                hode = ny;
                ny.neste = n;
                n.forrige = ny;
            } else {
                ny.forrige = n.forrige;
                ny.neste = n;
                n.forrige.neste = ny;
                n.forrige = ny;
            }
        }
        antall++;
        antallEndringer++;
    }

    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {
        Node<T> n = hode;
        int indeks = 0;
        while(n != null) {
            if(n.verdi.equals(verdi)) {
                return indeks;
            }
            indeks++;
            n = n.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi);
        Node<T> n = finnNode(indeks);
        Node<T> ny = new Node<>(nyverdi, null, null);
        ny.forrige = n.forrige;
        ny.neste = n.neste;
        if(n.neste == null) {
            n.forrige.neste = ny;
            hale = ny;
        } else if(n.forrige == null) {
            n.neste.forrige = ny;
            hode = ny;
        } else {
            n.neste.forrige = ny;
            n.forrige.neste = ny;
        }
        antallEndringer++;
        return n.verdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        if(verdi == null) {
            return false;
        }
        Node<T> n = hode;
        while(n != null) {
            if(verdi.equals(n.verdi)) {
                if(n.forrige == null && n.neste == null) {
                    hode = null;
                    hale = null;
                } else if(n.forrige == null) {
                    hode = n.neste;
                    n.neste.forrige = null;
                } else if(n.neste == null) {
                    hale = n.forrige;
                    n.forrige.neste = null;
                } else {
                    n.forrige.neste = n.neste;
                    n.neste.forrige = n.forrige;
                }
                antall--;
                antallEndringer++;
                return true;
            }
            n = n.neste;
        }
        return false;
    }

    @Override
    public T fjern(int indeks)
    {
        indeksKontroll(indeks, false);
        Node<T> n = finnNode(indeks);
        if(hale == hode) {
            hode = hale = null;
        } else if(n == hale) {
            hale = hale.forrige;
            hale.neste = null;
        } else if(n == hode) {
            hode = hode.neste;
            hode.forrige = null;
        } else {
            n.neste.forrige = n.forrige;
            n.forrige.neste = n.neste;
        }
        antall--;
        antallEndringer++;
        return n.verdi;
    }

    @Override
    public void nullstill()
    {
        hode = null;
        hale = null;
        antall = 0;
        antallEndringer++;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder("[");
        Node<T> n = hode;
        while(n != null) {
            if(n.neste != null) {
                s.append(n.verdi).append(",").append(" ");
                n = n.neste;
            } else{
                s.append(n.verdi);
                n = n.neste;
            }
        }
        s.append("]");
        return s.toString();
    }

    public String omvendtString() {

        StringJoiner s = new StringJoiner(", ", "[", "]");
        Node<T> n = hale;
        while(n != null) {
            s.add(n.verdi.toString());
            n = n.forrige;
        }
        return s.toString();
    }

    @Override
    public Iterator<T> iterator()
    {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        if(action == null) throw new NullPointerException();
        Node<T> n = hode;
        while(n != null) {
            action.accept(n.verdi);
            n = n.neste;
        }
    }

    @Override
    public boolean fjernHvis(Predicate<? super T> p) {
        Node<T> n = hode;
        boolean noeFjernet = false;
        while(n != null) {
            if(p.test(n.verdi)) {
                fjern(n.verdi);
                noeFjernet = true;
            }
            n = n.neste;
        }
        return noeFjernet;
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int forventetAntallEndringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            forventetAntallEndringer = antallEndringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            denne = finnNode(indeks);
            fjernOK = false;
            forventetAntallEndringer = antallEndringer;
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            if(forventetAntallEndringer != antallEndringer) {
                throw new ConcurrentModificationException();
            } if(!hasNext()) throw new NoSuchElementException();
            Node<T> temp = denne;
            denne = denne.neste;
            fjernOK = true;
            return temp.verdi;
        }

        @Override
        public void remove()
        {
            if(!fjernOK) throw new IllegalStateException();
            if(forventetAntallEndringer != antallEndringer) throw new ConcurrentModificationException();
            fjernOK = false;
            if(antall == 1) {
                hode = hale = null;
            } else if(denne == null) {
                hale = hale.forrige;
                hale.neste = null;
            } else if(denne.forrige == hode) {
                hode = denne;
                hode.forrige = null;
            } else {
                denne.forrige.forrige.neste = denne;
                denne.forrige = denne.forrige.forrige;
            }
            antall--;
            forventetAntallEndringer = ++antallEndringer;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            if(action == null) throw new NullPointerException();
            while(hasNext()) action.accept(next());
        }
    } // DobbeltLenketListeIterator
} // DobbeltLenketListe
