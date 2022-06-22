package com.example.springlogindemo.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author aiden
 */
@AllArgsConstructor
@ToString
@Getter
public class AddItemRequest {
    /*
     * request body
     * looks like a json {"" : ""  ... }
     *
     * something interesting I noticed,
     * if you only have one item,
     * it is considered an object not a string
     * so put at least 2
     */

    private final String itemName;
    private final Integer quantity;
    private final Float price;
}
