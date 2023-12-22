package org.bezrukov;

import java.text.MessageFormat;
import java.util.*;

public class BezrukovList<E> extends AbstractList<E> implements List<E>, RandomAccess {

    private int actualSize = 0;
    private Object[] container;
    private static final int INITIAL_CAPACITY = 16;
    private static final int CAPACITY_MULTIPLIER = 2;

    public BezrukovList() {
        container = new Object[INITIAL_CAPACITY];
    }

    public BezrukovList(Collection<E> collection) {
        Object[] copy = collection.toArray();
        actualSize = copy.length;
        container = copy;
    }

    @Override
    public void add(int index, E element) {
        indexCheckAdd(index);
        if (actualSize == container.length){
            Object[] newContainer = new Object[container.length * CAPACITY_MULTIPLIER];
//            System.arraycopy(container, 0, newContainer, 0, container.length); // Можно и так.
            for (int i = 0; i < container.length; i++) {
                newContainer[i] = container[i];
            }
            container = newContainer;
        }

        for (int i = actualSize; i > index; i--) {
            container[i] = container[i-1];
        }
        container[index] = element;
        actualSize++;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        E[] copy = (E[]) c.toArray();
        if (copy.length == 0) {
            return false;
        }
        for (E e : copy) {
            add(e);
        }
        return true;
    }

    @Override
    public void clear() {
        container = new Object[INITIAL_CAPACITY];
        actualSize = 0;
    }

    @Override
    public E get(int index) {
        indexCheckExists(index);
        return (E) container[index];
    }

    @Override
    public E remove(int index) {
        indexCheckExists(index);
        E removedElement = (E) container[index];
        for (int i = index; i < container.length-1; i++) {
            container[i] = container[i+1];
        }
        actualSize--;
        return removedElement;
    }

    @Override
    public boolean isEmpty() {
        return actualSize == 0;
    }

    @Override
    public boolean remove(Object o) {
        int pos = -1;
        for(int i = 0; i < actualSize; i++) {
            if (container[i].hashCode() == o.hashCode() && container[i].equals(o)) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            return false;
        }

        remove(pos);
        return true;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        // MergeSort
        container = mergeSort(container, c, actualSize);
    }

    @Override
    public int size() {
        return actualSize;
    }

    private Object[] mergeSort(Object[] core, Comparator comparator, int size) {
        if (size < 2) {
            return core;
        }
        Object[] firstPart = new Object[size / 2];
        System.arraycopy(core, 0, firstPart, 0, size / 2);
        Object[] secondPart = new Object[size - size / 2];
        System.arraycopy(core, size / 2, secondPart, 0, size - size / 2);
        firstPart = mergeSort(firstPart, comparator, firstPart.length);
        secondPart = mergeSort(secondPart, comparator, secondPart.length);


        Object [] result = new Object[firstPart.length + secondPart.length];

        int i=0;
        int j=0;
        int k=0;
        while(i < firstPart.length && j < secondPart.length) {
            if(comparator.compare(firstPart[i], secondPart[j]) < 0) {
                result[k++] = firstPart[i++];
            }else {
                result[k++] = secondPart[j++];
            }
        }
        while(i < firstPart.length) {
            result[k++] = firstPart[i++];
        }
        while(j < secondPart.length) {
            result[k++] = secondPart[j++];
        }

        return result;
    }

    private void indexCheckExists(int index){
        if (index >= actualSize || index < 0) {
            throw new IndexOutOfBoundsException(MessageFormat.format(
                    "Provided index - {0} is greatest or equal than collection size - {1}",
                    index,
                    actualSize
            ));
        }
    }

    private void indexCheckAdd(int index){
        if (index > actualSize || index < 0) {
            throw new IndexOutOfBoundsException(MessageFormat.format(
                    "Provided index - {0} is greatest than collection size - {1}",
                    index,
                    actualSize
            ));
        }
    }
}
