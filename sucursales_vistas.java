
package intecap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class sucursales_vistas {
    JPanel sucursales = new JPanel();
    JTable tabla = new JTable();
    JScrollPane sp = new JScrollPane();

    private void botones() 
    {
//*****************************************************************************
//aca creamos el boton de crear

        sucursales.setLayout(null);
        JButton crear = new JButton("Crear");
        crear.setBounds(500, 100, 130, 50);
        sucursales.add(crear);

        ActionListener funcion_crear = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear();
            }
        };
        crear.addActionListener(funcion_crear);
       
//*******************************************************************************
//boton de carga masiva 
        JButton carga = new JButton("Carga Masiva");
        carga.setBounds(670, 100, 130, 50);
        sucursales.add(carga);

        ActionListener funcion_carga = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    carga_masiva();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(sucursales_vistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(sucursales_vistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                
            }
        };

        carga.addActionListener(funcion_carga);   
     //   -----------------------------------------------------------------------------------------------
     // aca creamos el boton de actualizar
        JButton actualizar = new JButton("Actualizar");
        actualizar.setBounds(500, 250, 130, 50);
        sucursales.add(actualizar);
        
        
        ActionListener funcion_actualizar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actulizar();
            }
        };
        actualizar.addActionListener(funcion_actualizar);
        
     //**************************************************************************************************
     //creamos el boton de eliminar
        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(670, 250, 130, 50);
        sucursales.add(eliminar);
        
        ActionListener funcion_eliminar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar_opcion();
            }
        };

        eliminar.addActionListener(funcion_eliminar);
        
     // creamos el boton para poder exportarlo en PDF

         JButton exportar = new JButton("Exportar PDF");
        exportar.setBounds(500, 400, 300, 50);
        sucursales.add(exportar);
        
        ActionListener funcion_pdf = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SucursalesDAO sd = new SucursalesDAO();
                try {
                    sd.pdf();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(sucursales_vistas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(sucursales_vistas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        exportar.addActionListener(funcion_pdf);
 
    }
    
    //fin de los botones 
   
    // Libreria de cargar un archivoi de carga masiva
    private String leerarchivo() {

        JPanel c1 = new JPanel();
        JFileChooser fc = new JFileChooser();
        int op = fc.showOpenDialog(c1);
        String content = "";
        if (op == JFileChooser.APPROVE_OPTION) {

            File pRuta = fc.getSelectedFile();
            String ruta = pRuta.getAbsolutePath();
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            try {
                archivo = new File(ruta);
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea = "";

                while ((linea = br.readLine()) != null) {

                    content += linea + "\n";
                }
                return content;

            } catch (FileNotFoundException ex) {
                String resp = (String) JOptionPane.showInputDialog(null, "No se encontro el archivo");
            } catch (IOException ex) {
                String resp = (String) JOptionPane.showInputDialog(null, "No se pudo abrir el archivo");
            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    String resp = (String) JOptionPane.showInputDialog(null, "No se encontro el archivo");
                    return "";
                }

            }
            return content;

        }
        return null;

    }
    
    private void carga_masiva() throws FileNotFoundException, IOException, ParseException {
		

		String archivo_retorno = leerarchivo();

		JsonParser parse = new JsonParser();
		JsonArray matriz = parse.parse(archivo_retorno).getAsJsonArray();

		
		for (int i = 0; i < matriz.size(); i++) {
			JsonObject objeto = matriz.get(i).getAsJsonObject();
			SucursalesDAO sd = new SucursalesDAO();
                        sd.crear(objeto.get("codigo").getAsInt(), objeto.get("nombre").getAsString(), objeto.get("direccion").getAsString(), objeto.get("correo").getAsString(), objeto.get("telefono").getAsInt());			
		}
		
		
	}
        
        //termnina archi de carga masiva
//******************************************************************************

    private void tabla() {

        int fila = tabla.getSelectedRow();
        String columnas[] = {"Código", "Nombre", "Dirección", "Correo", "Teléfono"};
        SucursalesDAO sd = new SucursalesDAO();
        Object filas[][] = sd.listar_tabla();
        tabla = new JTable(filas, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(10, 20, 430, 600);
        sucursales.add(sp);

    }

    private void crear() {

        JFrame frame_sucursal = new JFrame();
        frame_sucursal.setTitle("Nueva Sucursal");
        frame_sucursal.setLocationRelativeTo(null);
        frame_sucursal.setBounds(50, 175, 350, 400);
        frame_sucursal.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frame_sucursal.add(p1);

        JLabel l1 = new JLabel("Código");
        
        l1.setBounds(50, 20, 80, 50);
        p1.add(l1);

        JTextField t1 = new JTextField();
       // t1.setText(""); //listar
      
        t1.setBounds(150, 32, 130, 25);
        p1.add(t1);

        JLabel l2 = new JLabel("Nombre");
        l2.setBounds(50, 80, 80, 50);
        p1.add(l2);

        JTextField t2 = new JTextField();
        t2.setBounds(150, 92, 130, 25);
        p1.add(t2);

        JLabel l3 = new JLabel("Dirección");
        l3.setBounds(50, 140, 80, 50);
        p1.add(l3);

        JTextField t3 = new JTextField();
        t3.setBounds(150, 152, 130, 25);
        p1.add(t3);

        JLabel l4 = new JLabel("Correo");
        l4.setBounds(50, 200, 80, 50);
        p1.add(l4);

        JTextField t4 = new JTextField();
        t4.setBounds(150, 212, 130, 25);
        p1.add(t4);

        JLabel l5 = new JLabel("Télefono");
        l5.setBounds(50, 260, 80, 50);
        p1.add(l5);

        JTextField t5 = new JTextField();
        t5.setBounds(150, 272, 130, 25);
        p1.add(t5);

        JButton b1 = new JButton("Guardar");
        b1.setBounds(130, 330, 80, 15);
        p1.add(b1);

        ActionListener guardar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SucursalesDAO sd = new SucursalesDAO();
                sd.crear(Integer.parseInt(t1.getText()), t2.getText(), t3.getText(), t4.getText(), Integer.parseInt(t5.getText()));
                frame_sucursal.setVisible(false);
            }
        };

        b1.addActionListener(guardar);

    }
    
    private void actulizar(){
        JFrame frame_sucursal = new JFrame();
        frame_sucursal.setTitle("Actualizar");
        frame_sucursal.setLocationRelativeTo(null);
        frame_sucursal.setBounds(50, 175, 350, 400);
        frame_sucursal.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frame_sucursal.add(p1);

        JLabel l1 = new JLabel("Código");        
        l1.setBounds(50, 20, 80, 50);
        p1.add(l1);

        JTextField t1 = new JTextField();    //listar
        t1.setEnabled(false);
        t1.setBounds(150, 32, 130, 25);
        p1.add(t1);

        JLabel l2 = new JLabel("Nombre");
        l2.setBounds(50, 80, 80, 50);
        p1.add(l2);

        JTextField t2 = new JTextField();
        t2.setText("");
        t2.setBounds(150, 92, 130, 25);
        p1.add(t2);

        JLabel l3 = new JLabel("Dirección");
        l3.setBounds(50, 140, 80, 50);
        p1.add(l3);

        JTextField t3 = new JTextField();
        t3.setText("");
        t3.setBounds(150, 152, 130, 25);
        p1.add(t3);

        JLabel l4 = new JLabel("Correo");
        l4.setBounds(50, 200, 80, 50);
        p1.add(l4);

        JTextField t4 = new JTextField();
        t4.setText("");
        t4.setBounds(150, 212, 130, 25);
        p1.add(t4);

        JLabel l5 = new JLabel("Télefono");
        l5.setBounds(50, 260, 80, 50);
        p1.add(l5);

        JTextField t5 = new JTextField();
        t5.setText("");
        t5.setBounds(150, 272, 130, 25);
        p1.add(t5);

        JButton b1 = new JButton("Actualizar");
        b1.setBounds(130, 330, 80, 15);
        p1.add(b1);

        ActionListener Actualizar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SucursalesDAO sd = new SucursalesDAO();
                sd.modificar(Integer.parseInt(t1.getText()), t2.getText(), t3.getText(), t4.getText(), Integer.parseInt(t5.getText()));
                frame_sucursal.setVisible(false);
            }
            int fila = tabla.getSelectedRow();          
            SucursalesDAO sd = new SucursalesDAO();
            Object filas[][] = sd.listar_tabla();
        };

        b1.addActionListener(Actualizar);

        
        
        
      
    
    
    }
    
    private void eliminar_opcion(){
    
    SucursalesDAO sf = new SucursalesDAO();
    sf.eliminar(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0)+""));
    
    }
    
    

    public void ejecutar() {
        botones();
        tabla();
    }

}