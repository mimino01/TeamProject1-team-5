package Conponent;

import Conponent.BoardLinkedList.LinkedList;

import java.util.Arrays;
import java.util.Objects;

public class Sort {
    LinkedList room = new LinkedList();
    String sortType;

    public Sort() {
    }

    public Sort(LinkedList room, String sortType) {
        this.room = room;
        this.sortType = sortType;
    }

    public LinkedList getRoom() {
        return room;
    }

    public void setRoom(LinkedList room) {
        this.room = room;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sort sort)) return false;
        return Objects.equals(room, sort.room) && Objects.equals(sortType, sort.sortType);
    }

    public String[][] toDeepArray() {
        return room.toDeepArray();
    }

    public static void descendingTime(String[][] array, int first, int last) {
        ascendingTime(array, first, last);

        for (int i = 0; true; i++) {
            if (last < 1) break;
            if (last - first == 2 * i) {

            }
        }
    }

    public static void ascendingTime(String[][] array, int first, int last) {
        if (first >= last) {
            return;
        }

        int low = Integer.parseInt(array[first + 1][3]);
        int high = Integer.parseInt(array[last][3]);
        int pivot = Integer.parseInt(array[first][3]);
        int copyPivot = first;
        int copyFirst = first;
        int copyLast = last;

        for (int i = 0; i < 100; i++) {
            if (copyFirst + 1 == copyLast) {
               
                if (pivot >= Integer.parseInt(array[copyFirst + 1][3])) {
                    
                    String[] temp = array[first].clone();
                    array[first] = array[copyFirst + 1].clone();
                    array[copyFirst + 1] = temp.clone();
                    copyPivot = copyFirst + 1;
                } else {
                    
                    String[] temp = array[first].clone();
                    array[first] = array[copyFirst].clone();
                    array[copyFirst] = temp.clone();
                    copyPivot = copyFirst;
                }
                break;
            } else if (copyFirst == copyLast) {
                
                String[] temp = array[first].clone();
                array[first] = array[copyFirst].clone();
                array[copyFirst] = temp.clone();
                copyPivot = copyFirst;
                break;
            } else if (pivot >= high && pivot <= low) {
                
                String[] temp = array[copyFirst + 1].clone();
                array[copyFirst + 1] = array[copyLast].clone();
                array[copyLast] = temp.clone();
                copyFirst++;
                copyLast--;
                low = Integer.parseInt(array[copyFirst + 1][3]);
                high = Integer.parseInt(array[copyLast][3]);
            } else if (pivot <= high) {
                
                copyLast--;
                high = Integer.parseInt(array[copyLast][3]);
            } else if (pivot > low) {
                
                copyFirst++;
                low = Integer.parseInt(array[copyFirst + 1][3]);
            } else if(pivot <= high && pivot > low) {
               
                copyLast--;
                high = Integer.parseInt(array[copyLast][3]);
                copyFirst++;
                low = Integer.parseInt(array[copyFirst + 1][3]);
            } else {
                System.out.println("Sort Error - Undefined what circumstances - node description : " + Arrays.deepToString(array));
            }
        }
        ascendingTime(array, first, copyPivot - 1);
        ascendingTime(array, copyPivot + 1, last);
    }
}
