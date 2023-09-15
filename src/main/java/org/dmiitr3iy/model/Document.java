package org.dmiitr3iy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Document implements Comparable<Document>{
    private Size size;
    private Type type;
    private long printTime;


    @Override
    public int compareTo(Document o) {
        return Long.compare(this.printTime,o.getPrintTime());
    }
}
