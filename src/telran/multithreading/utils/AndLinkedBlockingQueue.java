package telran.multithreading.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AndLinkedBlockingQueue<E> implements BlockingQueue<E> {

	LinkedList<E> list = new LinkedList<>();

    int limit = Integer.MAX_VALUE;

    Lock lock = new ReentrantLock();

    Condition waitingConsumer = lock.newCondition();
    Condition waitingProducer = lock.newCondition();

    public AndLinkedBlockingQueue(int limit) {
        this.limit = limit;
    }

    public AndLinkedBlockingQueue() {
    }

    private void checkValue (Object value){
        if (value == null) throw new NullPointerException();
    }

    @Override
    public boolean add(Object o) {
        checkValue(o);
        lock.lock();
        boolean res;
        try {
            if (remainingCapacity() > 0) {
                res = list.add((E) o);
                waitingConsumer.signal();
            } else throw new RuntimeException();
            return res;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean offer(Object o) {
        checkValue(o);
        lock.lock();
        boolean res = false;
        try {
            if (remainingCapacity() > 0) {
                res = list.offer((E)o);
                if (res) waitingConsumer.signal();
            }
        } finally {
            lock.unlock();
        }
        return res;
    }

    @Override
    public E remove() {
        E res;
        lock.lock();
        try{
            res = list.remove();
            if (res != null) waitingProducer.signal();
        }finally {
            lock.unlock();
        }
        return res;
    }

    @Override
    public E poll() {
        E res;
        lock.lock();
       try{
          res = list.poll();
          if (res!=null) waitingProducer.signal();
       } finally {
           lock.unlock();
       }
        return res;
    }

    @Override
    public E element() {
        lock.lock();
        E res;
        try {
            res = list.element();
        } finally {
            lock.unlock();
        }
        return res;
    }

    @Override
    public E peek() {
        E e;
        lock.lock();
        try {
            e = list.peek();
        } finally {
            lock.unlock();
        }
        return e;
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean offer(Object o, long timeout, TimeUnit unit) throws InterruptedException {
        checkValue(o);
        boolean res = false;
        boolean cont = true;
        lock.lock();
        try {
            while (!res && cont) {
                res = offerWithoutLock((E) o);
                if (!waitingProducer.await(timeout, unit)) {
                    cont = false;
                }
            }
            if (res) waitingConsumer.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }

    private boolean offerWithoutLock (E e) {
        boolean res = false;
        if (remainingCapacity() != 0) {
            res = list.offer(e);
        }
        return res;
    }

    @Override
    public void put(E o) throws InterruptedException {
        checkValue(o);
        lock.lock();
        try{
            while(remainingCapacity()>0){
                waitingProducer.await();
            }
            list.add((E)o);
            waitingConsumer.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (size() == 0){
                waitingConsumer.await();
            }
            E res = list.remove();
            waitingProducer.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E res = null;
        boolean cont = true;
        lock.lock();
        try {
            while (res == null && cont) {
                res = list.poll();
                if (!waitingConsumer.await(timeout, unit)) {
                    cont = false;
                }
            }
            if (res!=null) waitingProducer.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int remainingCapacity() {
        return limit - size();
    }

    @Override
    public boolean remove(Object o) {
        lock.lock();
        boolean res;
        try {
            res = list.remove(o);
            if (res) waitingProducer.signal();
        return res;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean addAll(Collection c) {
        boolean res;
        lock.lock();
        try {
            res = list.addAll(c);
            if (res) waitingConsumer.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            list.clear();
            waitingProducer.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean retainAll(Collection c) {
        lock.lock();
        boolean res;
        try {
            res = list.retainAll(c);
            if (res) waitingProducer.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        checkValue(o);
        lock.lock();
        try {
            return list.contains(o);
        } finally {
            lock.unlock();
        }
    }

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		// TODO Auto-generated method stub
		return 0;
	}
}
