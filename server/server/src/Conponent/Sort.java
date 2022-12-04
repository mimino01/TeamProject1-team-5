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

    public static void distance(String[][] array, int first, int last) {

    }

    public static void descendingTime(String[][] array, int first, int last) {
        ascendingTime(array, first, last);

        for (int i = 0; i < ((last - first + 1) / 2); i++) {
            String[] temp = array[i];
            array[i] = array[last - i];
            array[last - i] = temp;
        }
    }

    public static void ascendingTime(String[][] array, int first, int last) {
        defaltSort(array, first, last, 3);
    }

    public static void defaltSort(String[][] array, int first, int last, int type) {
        if (first >= last) {
            return;
        }

        int low = Integer.parseInt(array[first + 1][type]);
        int high = Integer.parseInt(array[last][type]);
        int pivot = Integer.parseInt(array[first][type]);
        int copyPivot = first;
        int copyFirst = first;
        int copyLast = last;

        for (int i = 0; i < 100; i++) {
            if (copyFirst + 1 == copyLast) {
                //노드 1개 남았을때
                if (pivot >= Integer.parseInt(array[copyFirst + 1][3])) {
                    //기준점이 앞 노드보다 낮을떄
                    String[] temp = array[first].clone();
                    array[first] = array[copyFirst + 1].clone();
                    array[copyFirst + 1] = temp.clone();
                    copyPivot = copyFirst + 1;
                } else {
                    //기준점이 앞 노드보다 높을때
                    String[] temp = array[first].clone();
                    array[first] = array[copyFirst].clone();
                    array[copyFirst] = temp.clone();
                    copyPivot = copyFirst;
                }
                break;
            } else if (copyFirst == copyLast) {
                //노드 0개 남았을때
                String[] temp = array[first].clone();
                array[first] = array[copyFirst].clone();
                array[copyFirst] = temp.clone();
                copyPivot = copyFirst;
                break;
            } else if (pivot >= high && pivot <= low) {
                //앞 노드와 뒷 노드 스왑
                String[] temp = array[copyFirst + 1].clone();
                array[copyFirst + 1] = array[copyLast].clone();
                array[copyLast] = temp.clone();
                copyFirst++;
                copyLast--;
                low = Integer.parseInt(array[copyFirst + 1][type]);
                high = Integer.parseInt(array[copyLast][type]);
            } else if (pivot <= high) {
                //뒷 노드 한칸 전진
                copyLast--;
                high = Integer.parseInt(array[copyLast][type]);
            } else if (pivot > low) {
                //첫 노드 한칸 전진
                copyFirst++;
                low = Integer.parseInt(array[copyFirst + 1][3]);
            } else if(pivot <= high && pivot > low) {
                //정렬 필요 없음 (첫 노드, 뒷 노드 한칸 전진)
                copyLast--;
                high = Integer.parseInt(array[copyLast][type]);
                copyFirst++;
                low = Integer.parseInt(array[copyFirst + 1][type]);
            } else {
                System.out.println("Sort Error - Undefined what circumstances - node description : " + Arrays.deepToString(array));
            }
        }
        defaltSort(array, first, copyPivot - 1, type);
        defaltSort(array, copyPivot + 1, last, type);
    }
}
