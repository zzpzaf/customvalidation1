package com.zzpzaf.se.devxperiences.posts.customvalidation.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.zzpzaf.se.devxperiences.posts.customvalidation.DataObjects.Item;
import com.zzpzaf.se.devxperiences.posts.customvalidation.DataObjects.ItemDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
//@RequestMapping("/api")
public class ProductsController {

    private List<Item> itemsList = new ArrayList<>();
    private Integer mid;

    @GetMapping(value = "/items")
    public ResponseEntity<List<Item>> getAllItems(@RequestParam(name = "vendorId", required = false) Integer vendorId) {
        
        List<Item> returnItemsList = new ArrayList<>();
        
        if (itemsList.size() == 0) {
            itemsList = getInitialItems();
            mid = itemsList.get(itemsList.size()-1).getItemId() + 1;
        }
        
        try {

            if (vendorId == null) {
                returnItemsList = itemsList;
            }else {
                returnItemsList = itemsList.stream().filter(i -> i.getItemVendorId() == vendorId).collect(Collectors.toList());
            }

            if (returnItemsList.size() > 0) {
                return new ResponseEntity<>(returnItemsList, HttpStatus.OK); 
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            //System.out.println(" ===>>>>>>  ERROR ! " + e.getMessage() );
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }


    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") Integer id) {


        if (itemsList.size() == 0) {
            itemsList = getInitialItems();
            mid = itemsList.get(itemsList.size()-1).getItemId() + 1;
        }


        Item item;

        //System.out.println(" ===>>>>>>   Item Id: " + id );

        item = itemsList.stream().filter(i -> i.getItemId() == id).collect(Collectors.toList()).get(0);

        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@Valid @RequestBody ItemDTO dto) {

        if (itemsList.size() == 0) {
            itemsList = getInitialItems();
            mid = itemsList.get(itemsList.size()-1).getItemId() + 1;
        }


        Item item = new Item();
        item.setItemId(mid);
        item.setItemName(dto.getItemName());


        if (dto.getItemVendorId() <= 0) {
            item.setItemVendorId(0);
        } else {
            item.setItemVendorId(dto.getItemVendorId());
        }

        if (dto.getItemModelYear() <= 0) {
            item.setItemModelYear(2020);
        } else {
            item.setItemModelYear(dto.getItemModelYear());
        }

        if (dto.getItemListPrice() <= 0) {
            item.setItemListPrice(0f);
        } else {
            item.setItemListPrice(dto.getItemListPrice());
        }



        try {
            itemsList.add(item);            
        } catch (Exception e) {
            //TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        item = itemsList.stream().filter(i -> i.getItemId() == mid).collect(Collectors.toList()).get(0);

        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

    }


    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItemById(@PathVariable("id") Integer id, @Valid @RequestBody ItemDTO dto) {

        if (itemsList.size() == 0) {
            itemsList = getInitialItems();
            mid = itemsList.get(itemsList.size()-1).getItemId() + 1;
        }

        
        Item item;
      
        if ( itemsList.stream().filter(i -> i.getItemId() == id).collect(Collectors.toList()).size() > 0) {
            item = itemsList.stream().filter(i -> i.getItemId() == id).collect(Collectors.toList()).get(0);

            item.setItemName(dto.getItemName());
            if (dto.getItemVendorId() > 0) item.setItemVendorId(dto.getItemVendorId());
            if (dto.getItemModelYear() > 0) item.setItemModelYear(dto.getItemModelYear());
            if (dto.getItemListPrice() > 0) item.setItemListPrice(dto.getItemListPrice());
            
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



    @DeleteMapping("/items/{id}")
    public ResponseEntity<Item> deleteItemById(@PathVariable("id") Integer id) {

        if (itemsList.size() == 0) {
            itemsList = getInitialItems();
            mid = itemsList.get(itemsList.size()-1).getItemId() + 1;
        }

        Item item=null;
      
        if ( itemsList.stream().filter(i -> i.getItemId() == id).collect(Collectors.toList()).size() > 0) {
            item = itemsList.stream().filter(i -> i.getItemId() == id).collect(Collectors.toList()).get(0);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 

        try {
            itemsList.remove(item);            
        } catch (Exception e) {
            //TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ( item != null && !itemsList.contains(item)) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }











    private List<Item> getInitialItems() {
        List<Item> itemsList =  new ArrayList<>();

        itemsList.add(new Item(1, "Wooden Pencil", 3, 1998, 2.65f ));
        itemsList.add(new Item(2, "Basic Notebook", 2, 2005, 3.75f ));
        itemsList.add(new Item(3, "Silica Eraser", 1, 2017, 1.15f ));
        itemsList.add(new Item(4, "Soft Polymer Eraser", 1, 2020, 1.05f ));
        itemsList.add(new Item(5, "Soft Ballpoint Pen", 1, 2019, 2.95f ));
        itemsList.add(new Item(6, "Paper Dossier", 2, 2019, 3.70f ));
        itemsList.add(new Item(7, "A4 Glossy Paper Pack 250", 2, 2019, 6.15f ));
        itemsList.add(new Item(8, "Rubber Bands Small Pack", 1, 2019, 0.50f ));
        itemsList.add(new Item(9, "Fancy Jotter Notepad", 2, 2019, 1.65f ));
        itemsList.add(new Item(10, "Fountain Metal Brass Pen", 3, 2019, 5.10f ));
        itemsList.add(new Item(11, "Black Permanent Marker", 1, 2019, 3.40f ));
        itemsList.add(new Item(12, "Rubber Pencil", 3, 2019, 2.80f ));


        return itemsList;

    }



    
}
