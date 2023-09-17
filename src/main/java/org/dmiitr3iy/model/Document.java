package org.dmiitr3iy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Document implements Comparable<Document>{
    @NonNull
    private Size size;
    @NonNull
    private Type type;
    @NonNull
    private long printTime;


    @Override
    public int compareTo(Document o) {
        return Long.compare(this.printTime,o.getPrintTime());
    }
}
