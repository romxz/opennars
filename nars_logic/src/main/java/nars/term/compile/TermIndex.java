package nars.term.compile;

import javassist.scopedpool.SoftValueHashMap;
import nars.MapIndex;
import nars.Memory;
import nars.Op;
import nars.bag.impl.CacheBag;
import nars.nal.Compounds;
import nars.term.Term;
import nars.term.Termed;
import nars.term.compound.GenericCompound;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.function.Consumer;

/**
 *
 */
public interface TermIndex extends Compounds, CacheBag<Termed, Termed> {


    void forEach(Consumer<? super Termed> c);

    default <T extends Termed> T getTerm(Termed t) {
        Termed u = get(t);
        if (u == null)
            return null;
        return (T)u.term();
    }

    class UncachedTermIndex implements TermIndex {

        /** build a new instance on the heap */
        @Override public Term getTerm(Op op, Term[] t, int relation) {
            return new UncachedGenericCompound(op, t, relation);
        }

        @Override
        public Termed get(Object key) {
            return (Termed)key;
        }

        @Override
        public void forEach(Consumer<? super Termed> c) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Object remove(Termed key) {
            throw new RuntimeException("n/a");
        }

        @Override
        public Termed put(Termed termed, Termed termed2) {
            throw new RuntimeException("n/a");
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void start(Memory n) {
            throw new RuntimeException("should not be used by Memory");
        }

        private class UncachedGenericCompound extends GenericCompound {

            public UncachedGenericCompound(Op op, Term[] t, int relation) {
                super(op, t, relation);
            }

            @Override
            public Term clone(Term[] replaced) {
                return the(op(), replaced, relation);
            }
        }
    }

//    class GuavaIndex extends GuavaCacheBag<Term,Termed> implements TermIndex {
//
//
//        public GuavaIndex(CacheBuilder<Term, Termed> data) {
//            super(data);
//        }
//
//        @Override
//        public void forEachTerm(Consumer<Termed> c) {
//            data.asMap().forEach((k,v) -> {
//                c.accept(v);
//            });
//        }
//
//
//
//    }

    /** default memory-based (Guava) cache */
    static TermIndex memory(int capacity) {
//        CacheBuilder builder = CacheBuilder.newBuilder()
//            .maximumSize(capacity);
        return new MapIndex(
            new HashMap(capacity),new HashMap(capacity*2)
            //new UnifriedMap()
        );
    }
    static TermIndex memorySoft(int capacity) {
        return new MapIndex(
                new SoftValueHashMap(capacity),
                new SoftValueHashMap(capacity*2)
        );
    }
    static TermIndex memoryWeak(int capacity) {
        return new MapIndex(
                new WeakHashMap(capacity),
                new WeakHashMap(capacity*2)
        );
    }
    default void print(PrintStream out) {
        forEach(out::println);
    }

//    /** for long-running processes, this uses
//     * a weak-value policy */
//    static TermIndex memoryAdaptive(int capacity) {
//        CacheBuilder builder = CacheBuilder.newBuilder()
//            .maximumSize(capacity)
//            .recordStats()
//            .weakValues();
//        return new GuavaIndex(builder);
//    }
}
