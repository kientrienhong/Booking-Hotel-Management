/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblInvoice;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblInvoiceError implements Serializable{
    private String searchError; 

    public TblInvoiceError(String searchError) {
        this.searchError = searchError;
    }

    public TblInvoiceError() {
    }

    public String getSearchError() {
        return searchError;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }
    
}
