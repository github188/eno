package com.energicube.eno.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayUtil {

    public static List<Integer> diffListInteger(List<Integer> lis1, List<Integer> lis2) {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(new Integer[lis1.size()]));
        Collections.copy(list, lis1);
        list.removeAll(lis2);
        return list;
    }
}
