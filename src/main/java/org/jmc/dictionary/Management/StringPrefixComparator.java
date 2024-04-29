package org.jmc.dictionary.Management;

import java.util.Comparator;

public class StringPrefixComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int prefixLength = Math.min(o1.length(), o2.length());
        return o1.substring(0, prefixLength).compareTo(o2);
    }
}
