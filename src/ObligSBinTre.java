/**
 * Created by Adrian on 28/10/2015.
 */

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
        {
            this.verdi = verdi;
            venstre = v; høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString(){ return "" + verdi;}

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder

    private final Comparator<? super T> comp;       // komparator

    public ObligSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi);
        Node<T> n = rot, q = null;
        int cmp = 0;

        while(n != null) {
            q = n;
            cmp = comp.compare(verdi, q.verdi);
            n = (cmp < 0) ? n.venstre : n.høyre;
        }

        n = new Node<>(verdi, null);
        if(q == null) rot = n;
        else if(cmp < 0) {
            q.venstre = n;
            n.forelder = q;
        }
        else {
            q.høyre = n;
            n.forelder = q;
        }
        antall++;
        return true;
    }

    @Override
    public boolean inneholder(T verdi)
    {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) return false;
        Node<T> p = rot, q = null;
        while (p != null) {
            int cmp = comp.compare(verdi,p.verdi);
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }
            else break;
        }
        if (p == null) return false;

        if (p.venstre == null || p.høyre == null) {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
            if(b != null) b.forelder = q;
        }
        else
        {
            Node<T> s = p, r = p.høyre;
            while (r.venstre != null)
            {
                s = r;
                r = r.venstre;
            }

            p.verdi = r.verdi;

            if (s != p) s.venstre = r.høyre;
            else {
                s.høyre = r.høyre;
                r.høyre.forelder = s;
            }
            r.forelder = s;
        }

        antall--;
        return true;
    }

    public int fjernAlle(T verdi)
    {
        int antallFjernet = 0;
        boolean fjernet = fjern(verdi);
        while(fjernet) {
            antallFjernet++;
            fjernet = fjern(verdi);
        }
        return antallFjernet;
    }

    @Override
    public int antall()
    {
        return antall;
    }

    public int antall(T verdi)
    {
        int returnValue = 0;
        Node<T> n = rot;
        int cmp;

        while(n != null) {
            cmp = comp.compare(n.verdi, verdi);
            if(cmp < 0) n = n.høyre;
            else if(cmp > 0) n = n.venstre;
            else {
                returnValue++;
                n = n.høyre;
            }
        }
        return returnValue;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public void nullstill()
    {
        Node<T> n = rot;
        if(n == null) return;
        while(n.venstre != null) n = n.venstre;
        Node<T> p = n;
        while(n != null) {
            n = nesteInorden(n);
            p.verdi = null;
            p = n;
        }
        antall = 0;
        rot = null;
    }

    private static <T> Node<T> nesteInorden(Node<T> p)
    {
        if(p.høyre != null) {
            p = p.høyre;
            while(p.venstre != null) p = p.venstre;
            return p;
        }
        while(p.forelder != null && p.forelder.høyre == p) p = p.forelder;
        return p.forelder;
    }

    @Override
    public String toString()
    {
        StringJoiner s = new StringJoiner(", ","[","]");
        if(antall == 0 || rot == null) return s.toString();
        Node<T> n = rot;
        while(n.venstre != null) n = n.venstre;
        while(n != null) {
            if(n != null) s.add(n.verdi.toString());
            n = nesteInorden(n);
        }
        return s.toString();
     }

    public String omvendtString()
    {
        StringJoiner s = new StringJoiner(", ","[","]");
        if(antall == 0) return s.toString();
        Node<T> n = rot;
        Stack<Node<T>> stakk = new Stack<>();
        for(; n.høyre != null; n = n.høyre) stakk.add(n);

        while(true) {
            s.add(n.verdi.toString());

            if(n.venstre != null) {
                for(n = n.venstre; n.høyre != null; n = n.høyre) {
                    stakk.add(n);
                }
            } else if(!stakk.empty()) n = stakk.remove(stakk.size()-1);
            else break;
        }
        return s.toString();
    }

    public String høyreGren()
    {
        StringJoiner s = new StringJoiner(", ","[","]");
        if(rot == null) return s.toString();
        Node<T> n = rot;
        while(n != null) {
            s.add(n.verdi.toString());
            if(n.høyre == null) n = n.venstre;
            else n = n.høyre;
        }
        return s.toString();
    }

    public String lengstGren()
    {
        StringJoiner s = new StringJoiner(", ","[","]");
        if (tom()) return s.toString();
        Stack<Node<T>> kø = new Stack<>();
        kø.add(rot);
        Node<T> p = null;

        while (!kø.isEmpty()) {
            p = kø.remove(0);

            if (p.venstre != null) kø.add(p.venstre);
            if (p.høyre != null) kø.add(p.høyre);
        }
        Node<T> n = rot;

        while(n != null) {
            s.add(n.toString());
            if(n.venstre == null) n=n.høyre;
            else if(n.høyre == null) n=n.venstre;
            else {
                n = (comp.compare(p.verdi,n.verdi) >= 0) ? n.høyre : n.venstre;
            }
        }
        return s.toString();
    }

    public String[] grener()
    {
        StringJoiner s;
        Stack<Node<T>> stakk = new Stack<>();
        Stack<Node<T>> maxSaver = new Stack<>();
        if(tom()) return new String[0];
        String[] returnValue;

        stakk.add(rot);
        Node<T> n;
        Node<T> t;

        while(!stakk.empty()) {
            n = stakk.remove(stakk.size()-1);
            if(n.høyre == null && n.venstre == null) maxSaver.add(n);
            if (n.venstre != null) stakk.add(n.venstre);
            if (n.høyre != null) stakk.add(n.høyre);
        }

        returnValue = new String[maxSaver.size()];
        for(int i = 0; i < returnValue.length; i++) {
            n = rot;
            t = maxSaver.remove(maxSaver.size()-1);
            s = new StringJoiner(", ", "[", "]");
            while(n != null) {
                s.add(n.toString());
                if (n.venstre == null) n = n.høyre;
                else if (n.høyre == null) n = n.venstre;
                else {
                    n = (comp.compare(t.verdi, n.verdi) >= 0) ? n.høyre : n.venstre;
                }
            }
            returnValue[i] = s.toString();
        }
        return returnValue;
    }

    public String bladnodeverdier() {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        if(tom()) return s.toString();
        finnBladNoder(s, rot);
        return s.toString();

    }

    private void finnBladNoder(StringJoiner s, Node<T> n) {
        if(n.høyre == null && n.venstre == null) s.add(n.verdi.toString());
        if(n.venstre != null) finnBladNoder(s, n.venstre);
        if(n.høyre != null) finnBladNoder(s, n.høyre);
    }

    @Override
    public Iterator<T> iterator()
    {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T>
    {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;

        private BladnodeIterator()  // konstruktør
        {
            q = rot;
            while(q != null) {
                p = q;
                if(q.venstre != null) q = q.venstre;
                else q = q.høyre;
            }
            q = rot;
        }

        @Override
        public boolean hasNext()
        {
            return p != null;  // Denne skal ikke endres!
        }

        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException("Ingen fler bladnoder!");
            while (p != null) {
                if (p.venstre == null && p.høyre == null) {
                    q = p;
                    p = nesteInorden(p);
                    removeOK = true;
                    break;
                }
                p = nesteInorden(p);
            }
            return q.verdi;
        }

        @Override
        public void remove() {
            if(!removeOK) throw new IllegalStateException();

            if(rot == q) {
                p = q = rot = null;
                antall--;
                removeOK = false;
                return;
            }

            if(q.forelder.høyre == q) {
                q.forelder.høyre = null;
            } else {
                q.forelder.venstre = null;
            }
            antall--;
            removeOK = false;

            if(p != null && nesteInorden(p) == null) {
                while(p != null) {
                    if(p.venstre != null) p = p.venstre;
                    else p = p.høyre;
                }
            }
        }



    } // BladnodeIterator

} // ObligSBinTre

/*
@Override
        public T next() {
            if(tom() || rot == null) throw new NoSuchElementException("Ingen fler bladnoder!");
            while (p != null) {
                if (p.høyre == null && p.venstre == null) {
                    q = p;
                    removeOK = true;
                    T t = p.verdi;
                    p = nesteInorden(p);
                    return t;
                }
                p = nesteInorden(p);
            }
            throw new NoSuchElementException("Ingen flere bladnoder!");
        }

        @Override
        public void remove() {
            if(tom() || !removeOK) throw new IllegalStateException();
            if(removeOK) {
                if(rot == q) {
                    p = q = rot = null;
                    antall--;
                    removeOK = false;
                    return;
                }
                Node<T> forelder = q.forelder;
                if(forelder.venstre == q) {
                    forelder.venstre = null;
                } else {
                    forelder.høyre = null;
                }
                antall--;
                removeOK = false;
            }
            if(!tom() && p == null) {
                q = rot;
                while(q != null) {
                    p = q;
                    if(q.venstre != null) q = q.venstre;
                    else q = q.høyre;
                }
                q = rot;
            }
        }
 */
