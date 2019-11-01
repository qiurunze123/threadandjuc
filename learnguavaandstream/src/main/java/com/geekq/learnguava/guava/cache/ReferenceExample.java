package com.geekq.learnguava.guava.cache;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReferenceExample
{

    public static void main(String[] args) throws InterruptedException
    {
        //Strong Reference
        /*int counter = 1;

        List<Ref> container = new ArrayList<>();

        for (; ; )
        {
            int current = counter++;
            container.add(new Ref(current));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.MILLISECONDS.sleep(500);
        }*/

//        SoftReference<Ref> reference = new SoftReference<>(new Ref(0));

        /**
         * detected the JVM process will be OOM then try to GC soft reference.
         *
         */

        //soft reference
       /* int counter = 1;

        List<SoftReference<Ref>> container = new ArrayList<>();

        for (; ; )
        {
            int current = counter++;
            container.add(new SoftReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.SECONDS.sleep(1);
        }*/

        /**
         * Weak reference
         *
         * The reference will be collected when GC.
         */
        /*int counter = 1;

        List<WeakReference<Ref>> container = new ArrayList<>();

        for (; ; )
        {
            int current = counter++;
            container.add(new WeakReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.MILLISECONDS.sleep(200);
        }*/

        Ref ref = new Ref(10);
        ReferenceQueue queue = new ReferenceQueue<>();
        MyPhantomReference reference = new MyPhantomReference(ref, queue, 10);
        ref = null;

        System.out.println(reference.get());

        System.gc();
//        TimeUnit.SECONDS.sleep(1);
        Reference object = queue.remove();
        ((MyPhantomReference) object).doAction();

       /* System.gc();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(ref);
        System.out.println(reference.get().index);*/

        /**
         * File file = new File();
         * file.create();
         *
         *
         *
         *
         */
    }

    private static class MyPhantomReference extends PhantomReference<Object>
    {

        private int index;

        public MyPhantomReference(Object referent, ReferenceQueue<? super Object> q, int index)
        {
            super(referent, q);
            this.index = index;
        }

        public void doAction()
        {
            System.out.println("The object " + index + " is GC.");
        }
    }

    private static class Ref
    {

        private byte[] data = new byte[1024 * 1024];

        private final int index;

        private Ref(int index)
        {
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable
        {
            System.out.println("The index [" + index + "] will be GC.");
        }
    }
}
