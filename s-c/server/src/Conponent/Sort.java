package Conponent;

import Conponent.BoardLinkedList.LinkedList;

import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class Sort {
    private static final int REVERSE  = -1;
    private static final int LEFT_FIRST  = -1;
    private static final int RIGHT_FIRST  = 1;
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

    public static void test() {
        System.out.println(compare("안녕","가하세요"));

    }

    public static boolean isEnglish(char ch){
        return (ch >= (int)'A' && ch <= (int)'Z')
                || (ch >= (int)'a' && ch <= (int)'z');
    }

    public static boolean isKorean(char ch) {
        return ch >= Integer.parseInt("AC00", 16)
                && ch <= Integer.parseInt("D7A3", 16);
    }

    public static boolean isNumber(char ch) {
        return ch >= (int)'0' && ch <= (int)'9';
    }

    public static boolean isSpecial(char ch) {
        return (ch >= (int)'!' && ch <= (int)'/') // !"#$%&'()*+,-./
                || (ch >= (int)':' && ch <= (int)'@') //:;<=>?@
                || (ch >= (int)'[' && ch <= (int)'`') //[\]^_`
                || (ch >= (int)'{' && ch <= (int)'~'); //{|}~
    }

    public static int compare(String left, String right) {

        left = StringUtils.upperCase(left).replaceAll(" ", "");
        right = StringUtils.upperCase(right).replaceAll(" ", "");

        int leftLen = left.length();
        int rightLen = right.length();
        int minLen = Math.min(leftLen, rightLen);

        for(int i = 0; i < minLen; ++i) {
            char leftChar = left.charAt(i);
            char rightChar = right.charAt(i);

            if (leftChar != rightChar) {
                if (isKoreanAndEnglish(leftChar, rightChar)
                        || isKoreanAndNumber(leftChar, rightChar)
                        || isEnglishAndNumber(leftChar, rightChar)
                        || isKoreanAndSpecial(leftChar, rightChar)) {
                    return (leftChar - rightChar) * REVERSE;
                } else if (isEnglishAndSpecial(leftChar, rightChar)
                        || isNumberAndSpecial(leftChar, rightChar)) {
                    if (isEnglish(leftChar) || isNumber(leftChar)) {
                        return LEFT_FIRST;
                    } else {
                        return RIGHT_FIRST;
                    }
                } else {
                    return leftChar - rightChar;
                }
            }
        }

        return leftLen - rightLen;
    }

    private static boolean isKoreanAndEnglish(char ch1, char ch2) {
        return (isEnglish(ch1) && isKorean(ch2))
                || (isKorean(ch1) && isEnglish(ch2));
    }

    private static boolean isKoreanAndNumber(char ch1, char ch2) {
        return (isNumber(ch1) && isKorean(ch2))
                || (isKorean(ch1) && isNumber(ch2));
    }

    private static boolean isEnglishAndNumber(char ch1, char ch2) {
        return (isNumber(ch1) && isEnglish(ch2))
                || (isEnglish(ch1) && isNumber(ch2));
    }

    private static boolean isKoreanAndSpecial(char ch1, char ch2) {
        return (isKorean(ch1) && isSpecial(ch2))
                || (isSpecial(ch1) && isKorean(ch2));
    }

    private static boolean isEnglishAndSpecial(char ch1, char ch2) {
        return (isEnglish(ch1) && isSpecial(ch2))
                || (isSpecial(ch1) && isEnglish(ch2));
    }

    private static boolean isNumberAndSpecial(char ch1, char ch2) {
        return (isNumber(ch1) && isSpecial(ch2))
                || (isSpecial(ch1) && isNumber(ch2));
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

    public static void destination(String[][] array, int first, int last) {
        defaltSort(array, first, last, 1);
    }

    public static void startingTime(String[][] array, int first, int last) {
        defaltSort(array, first, last, 4);
    }

    public static void defaltSort(String[][] array, int first, int last, int type) {
        if (type == 1) {
            if (first >= last) {
                return;
            }

//            int low = Integer.parseInt(array[first + 1][type]);
//            int high = Integer.parseInt(array[last][type]);
//            int pivot = Integer.parseInt(array[first][type]);
            String low = array[first + 1][type];
            String high = array[last][type];
            String pivot = array[first][type];
            int copyPivot = first;
            int copyFirst = first;
            int copyLast = last;

            for (int i = 0; i < 100; i++) {
                if (copyFirst + 1 == copyLast) {
                    //노드 1개 남았을때
//                    if (pivot >= Integer.parseInt(array[copyFirst + 1][3])) {
                    if (0 < compare(pivot,array[copyFirst + 1][type])) {
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
//                } else if (pivot >= high && pivot <= low) {
                } else if (0 < compare(pivot,high) && 0 > compare(pivot,low)) {
                    //앞 노드와 뒷 노드 스왑
                    String[] temp = array[copyFirst + 1].clone();
                    array[copyFirst + 1] = array[copyLast].clone();
                    array[copyLast] = temp.clone();
                    copyFirst++;
                    copyLast--;
                    low = array[copyFirst + 1][type];
                    high = array[copyLast][type];
//                } else if (pivot <= high) {
                } else if (0 > compare(pivot,high)) {
                    //뒷 노드 한칸 전진
                    copyLast--;
                    high = array[copyLast][type];
//                } else if (pivot > low) {
                } else if (0 < compare(pivot,low)) {
                    //첫 노드 한칸 전진
                    copyFirst++;
                    low = array[copyFirst + 1][3];
//                } else if (pivot <= high && pivot > low) {
                } else if (0 > compare(pivot,high) && 0 > compare(pivot,low)) {
                    //정렬 필요 없음 (첫 노드, 뒷 노드 한칸 전진)
                    copyLast--;
                    high = array[copyLast][type];
                    copyFirst++;
                    low = array[copyFirst + 1][type];
                } else {
                    System.out.println("Sort Error - Undefined what circumstances - node description : " + Arrays.deepToString(array));
                }
            }
            defaltSort(array, first, copyPivot - 1, type);
            defaltSort(array, copyPivot + 1, last, type);
        } else {
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
                    if (pivot >= Integer.parseInt(array[copyFirst + 1][type])) {
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
                } else if (pivot <= high && pivot > low) {
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
}