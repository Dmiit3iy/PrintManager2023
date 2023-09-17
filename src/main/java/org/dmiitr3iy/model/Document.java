package org.dmiitr3iy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Document{
    @NonNull
    private Size size;
    @NonNull
    private Type type;
    @NonNull
    private long printTime;
}
