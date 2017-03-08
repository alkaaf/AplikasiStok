/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockie.Model;

import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author dalbo
 */
public class DBHelper<model> {

    public DBHelper() {
       
    }
    
    public int insert(String tabel, String[] kolom, String[] value){
        return 0;
    }
    
    public int delete(String tabel, int id){
        return 0;
    }
    
    public int delete(String tabel, String id){
        return 0;
    }
    
    public int update(){
        return 0;
    }
    
    public List<model> select(String tabel){
        return null;
    }
}
