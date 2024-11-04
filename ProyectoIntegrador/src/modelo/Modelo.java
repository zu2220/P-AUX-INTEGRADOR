
package modelo;

import clases.Ambiente;
import clases.Insumo;
import clases.InsumoPorAgotarse;
import clases.InsumoPorCaducar;
import clases.ServicioSolicitado;
import static conexionDB.DataSource.DataSource;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Modelo {
    
    public List<Insumo> getInsumos(){
        List<Insumo> insumos = new ArrayList<>();
        
        try{
            String query = "select codigo_insumo,nombre_insumo,cantidad_insumo,categoria_insumo ";
                   query+= "from INVENTARIO";
            PreparedStatement pstmt = DataSource().prepareStatement(query);
           
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                Insumo insumo = new Insumo();
                insumo.setCodigo(rs.getString(1));
                insumo.setNombre(rs.getString(2));
                insumo.setCantidad(rs.getDouble(3));
                insumo.setCategoria(rs.getString(4));
                insumos.add(insumo);
            }
        }catch(Exception ex){System.out.println(ex);}
        return insumos;
    }
    
    public boolean AgregarInsumo(Insumo insumo){
     boolean flag= false ; 
     try{
      String Query= " INSERT INTO INVENTARIO values(?, ?, ?, ?, ?, ?)";
      
            PreparedStatement  stmt = DataSource().prepareStatement(Query);
            
            stmt.setString(1,insumo.getCodigo());
            stmt.setString(2, insumo.getNombre());
            stmt.setDouble(3, insumo.getCantidad());
            stmt.setString(4, insumo.getCategoria());
            stmt.setDate(5, insumo.getFechavencimiento());
            stmt.setDouble(6, insumo.getPrecio());
            
            
            flag = stmt.execute();
      
      
      
     }  catch (SQLException ex) { 
           System.out.println(ex.getMessage());
 
        } 
        return flag;
       
    
    }
    
   public boolean ReducirStock(String codigoInsumo, double cantidadInsumo){
       boolean flag= false;
       String Query= "exec ReducirStock ?, ?";
       
         try{
         PreparedStatement  stmt = DataSource().prepareStatement(Query);
            stmt.setDouble(1, cantidadInsumo);
            stmt.setString(2, codigoInsumo);
            flag= stmt.execute();
            
         }
         catch(SQLException ex){
             System.out.println(ex.getMessage());
         }
       return flag;
   }  
 
   public boolean AñadirCantidadInsumo(Double cantidad, String codigoInsumo){
       boolean flag= false;
       String Query= "exec AñadirCantidadInsumo ?, ?";
       Query+= "select* from INVENTARIO";
       try{
           PreparedStatement  stmt = DataSource().prepareStatement(Query);
           stmt.setDouble(1, cantidad);
            stmt.setString(2, codigoInsumo);
            flag= stmt.execute();
            
       }catch(SQLException ex){
       
       System.out.println(ex.getMessage()); 
       }
       
       return flag;
   }
   
   public List<InsumoPorAgotarse> getInsumosPorAgotarse(){
       List<InsumoPorAgotarse> insumosPorAgotarse = new ArrayList<>();
       
       try{
           String query = "";
           PreparedStatement pstmt = DataSource().prepareStatement(query);
           ResultSet rs = pstmt.executeQuery();
           
           while(rs.next()){
               InsumoPorAgotarse insumo = new InsumoPorAgotarse();
               insumo.setNombre(rs.getString(1));
               insumo.setCantidadRestante(rs.getDouble(2));
               insumosPorAgotarse.add(insumo);
           }
           
       }catch(Exception ex){System.out.println(ex);}
       
       return insumosPorAgotarse;
   }
   
   public List<InsumoPorCaducar> getInsumosPorCaducar(){
       List<InsumoPorCaducar> insumosPorCaducar = new ArrayList<>();
       
       try{
           String query = "";
           PreparedStatement pstmt = DataSource().prepareStatement(query);
           ResultSet rs = pstmt.executeQuery();
           
           while(rs.next()){
               InsumoPorCaducar insumo = new InsumoPorCaducar();
               insumo.setNombre(rs.getString(1));
               insumo.setDiasRestantes(rs.getInt(2));
               insumosPorCaducar.add(insumo);
           }
           
       }catch(Exception ex){System.out.println(ex);}
       
       return insumosPorCaducar;
       
   }
    
    
    /*
    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    <<<<<<<< HECTOR, NO TOQUES EL CÓDIGO QUE ESTÁ ABAJO POR FAVOR >>>>>>>>>>>>>>>>>>>>>>
    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    */
    
    public List<Ambiente> getAmbientes(String tipo_ambiente){
        List<Ambiente> ambientes = new ArrayList<>();
        
        try{
            String query = "select* from Ambientes ";
                   query+= "where tipo_ambiente='?'";
            PreparedStatement pstmt = DataSource().prepareStatement(query);
            
            pstmt.setString(1, tipo_ambiente);
            ResultSet rs = pstmt.executeQuery();
            
            //Recuperación de ambientes
            while(rs.next()){
                Ambiente ambiente = new Ambiente();
                ambiente.setNombreAmbiente(rs.getString(1));
                ambiente.setTipodeAmbiente(rs.getString(2));
                
                ambientes.add(ambiente);
            }
        }catch(Exception ex){System.out.println(ex);}
        return ambientes;
    }
    
    public List<ServicioSolicitado> getServiciosPorTipo(String tipo_servicio){
        List<ServicioSolicitado> servicios = new ArrayList<>();
        
        try{
            String query = "select* ServiciosSolicitados where tipo_servicio = ?";
            PreparedStatement pstmt = DataSource().prepareStatement(query);
            pstmt.setString(1, tipo_servicio);
            ResultSet rs = pstmt.executeQuery();
            
            //capturamos servicios con tipo_servicio igual al parametro del método
            while(rs.next()){
                ServicioSolicitado servicio = new ServicioSolicitado();
                servicio.setCodigoServicio(rs.getString(1));
                //servicio
            }
            
        }catch(Exception ex){System.out.println(ex);}
        return servicios;
    }
    
    /*
    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<METODOS GENERALES>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    */
    
    public boolean validarCredenciales(String user_name, String password){
        boolean flag = false;
        try{
            String query = "select* from Users where nombre_usuario = ? and contrasena_usuario = ?";
            PreparedStatement pstmt = DataSource().prepareCall(query);
            pstmt.setString(1, user_name);
            pstmt.setString(2,password);
            flag = pstmt.execute();
        }catch(Exception ex){System.out.println(ex);}
        
        return flag;
    }
    
}
