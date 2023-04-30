package telran.multithreading.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLinkedBlockingQueue<E> implements BlockingQueue<E> {
	
	private final AtomicInteger count = new AtomicInteger();
	LinkedList<E> list = new LinkedList<>();
	int limit = Integer.MAX_VALUE;
	Lock lock = new ReentrantLock();
	Condition prodWait = lock.newCondition();
	Condition consWait = lock.newCondition();
	
	public MyLinkedBlockingQueue(int limit) {
		this.limit = limit;
	}
	
	public MyLinkedBlockingQueue() {
		this(100);
	}
	

	@Override
	public E remove() {
		lock.lock();
		E res = null;
		try {
			if (isEmpty()) {
				throw new NullPointerException();
			}
			res = list.removeFirst();
			prodWait.signal();
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public E poll() {
		E res = null;
		lock.lock();
		try {
			res = list.poll();
			if (res != null) {
			prodWait.signal();
			}
				
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public E element() {
		lock.lock();
		E res = null;
		try {
			res = list.element();
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public E peek() {
		lock.lock();
		E res = null;
		try {
			res = list.peek();
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public int size() {
		
		return list.size();
	}

	@Override
	public boolean isEmpty() {

		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		
		return list.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray() {
		lock.lock();
	try {
		return (E[]) list.toArray();
	} finally {
		lock.unlock();
	}
		
	}

	@Override
	public <T> T[] toArray(T[] a) {
		lock.lock();
		try {
			return list.toArray(a);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		lock.lock();
		try {
			return list.containsAll(c);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
			for (E e : c) {
				list.add(e);
			}
		return !c.isEmpty();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		lock.lock();
		try {
			list.removeAll(c);
			prodWait.signal();
		} finally {
			lock.unlock();
		}
			
		return c.isEmpty();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		lock.lock();
		try {
			list.retainAll(c);
		} finally {
			lock.unlock();
		}
		return !isEmpty();
	}

	@Override
	public void clear() {
		removeAll(list);
	}

	@Override
	public boolean add(E e) {
		boolean res = false;
		lock.lock();
		try {
			if (list.size() == limit) {
				throw new IllegalStateException();
			}
			res = list.add(e);
			consWait.signal();
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public boolean offer(E e) {
		lock.lock();
		try {
			boolean res = false;
			if (list.size() < limit) {
				res = list.offer(e);
				if (res) {
					prodWait.signal();
				}
			}
			return res;
		} finally {
			lock.unlock();
		}
		
	}

	@Override
	public void put(E e) throws InterruptedException {
		lock.lock();
		try {
			while (list.size() == limit) {
				prodWait.await();
			}
			list.add(e);
			consWait.signal();
		} finally {
			lock.unlock();
		}

	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		lock.lock();
		try {
			if (list.size() == limit ) {
				consWait.await(timeout, unit);
			}
			return add(e);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		lock.lock();
		E res = null;
		try {
			while (isEmpty()) {
				consWait.await();
			}
			res = list.remove();
			prodWait.signal();
		} finally {
			lock.unlock();
		}
		return res;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		lock.lock();
		try {
			if (isEmpty()) {
				consWait.await(timeout, unit);
			}
			poll();
		} finally {
			lock.unlock();
		}
		return null;
	}

	@Override
	public int remainingCapacity() {
		lock.lock();
		try {
			return limit - list.size();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		lock.lock();
		boolean res = false;
		try {
			res = list.remove(o);
			prodWait.signal();
			return res;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public boolean contains(Object o) {
		lock.lock();
		try {
			return list.contains(o);
		} finally {
			lock.unlock();
		}
		
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		
		throw new UnsupportedOperationException();
	}



}
