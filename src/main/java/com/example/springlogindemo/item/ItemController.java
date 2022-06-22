package com.example.springlogindemo.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author aiden
 */

@RestController
@AllArgsConstructor
@RequestMapping(path = "/user/item")
public class ItemController {

    private ItemService itemService;

    /**
     * for looking up item
     * @return all items
     */
    @GetMapping
    public String getItems( )
    {
        return itemService.getItem();
    }

    /**
     * for making purchase
     * @param request request body
     * @return status
     */
    @PostMapping
    public String buyItem(@RequestBody BuyItemRequest request)
    {
        return itemService.buyItem(request);
    }

    // TODO: 6/22/22 security not imp, change it to /admin/item
    @PostMapping(path = "/add")
    public String addItem(@RequestBody AddItemRequest request)
    {
        return itemService.addItem(request);
    }



}
