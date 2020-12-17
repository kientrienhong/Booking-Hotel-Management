/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.cart;

import java.util.HashMap;
import java.util.Map;
import trienhk.tblroom.TblRoomDTO;

/**
 *
 * @author Treater
 */
public class CartObject {

    private Map<String, TblRoomDTO> items;

    public Map<String, TblRoomDTO> getItems() {
        return items;
    }

    public void add(TblRoomDTO dto) {
        if (this.getItems() == null) {
            this.items = new HashMap<>();
        }

        String id = dto.getIdHotel() + "_" + dto.getIdRoomType();

        if (this.items.containsKey(id)) {
            int quantity = this.items.get(id).getTotalAmount() + dto.getTotalAmount();
            dto.setTotalAmount(quantity);
        }

        this.items.put(id, dto);
    }

    public void delete(String id) {
        if (this.getItems() == null) {
            return;
        }

        if (this.getItems().get(id) != null) {
            this.getItems().remove(id);
            if (this.getItems().isEmpty()) {
                this.items = null;
            }
        }
    }

    public void modifyQuantityOfRoom(String id, boolean isIncrease) {
        if (this.getItems() == null) {
            return;
        }
        TblRoomDTO dto = this.getItems().get(id);
        if (dto != null) {
            int quantity = dto.getTotalAmount();
            if (isIncrease) {
                dto.setTotalAmount(quantity + 1);
            } else {
                if (dto.getTotalAmount() == 1) {
                    this.delete(id);
                } else {
                    dto.setTotalAmount(quantity - 1);
                }
            }
        }
    }

    public int getQuantityOfRoom(String id) {
        if (this.getItems() == null) {
            return -1;
        }
        TblRoomDTO dto = this.getItems().get(id);
        if (dto != null){
            return dto.getTotalAmount();
        }
        
        return -1;
    }

    public double totalPrice() {
        double sum = 0;

        for (String item : items.keySet()) {
            sum += items.get(item).getPrice() * items.get(item).getTotalAmount();
        }

        return sum;
    }
    
    public int getTotalItemsAmount(){
        int sum = 0; 
        
        for(String item: items.keySet()){
            sum += items.get(item).getTotalAmount();
        }
        
        return sum;
    }
}
