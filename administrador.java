/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intecap;


import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class administrador extends JFrame {

    JTabbedPane pestañas = new JTabbedPane(); //las pestanas de arriba 
    
    JPanel vendedores = new JPanel();  // nuestro oanel que ira por encima de nuestro frame
    JPanel clientes = new JPanel();    
    JPanel productos = new JPanel();   

    private void inicio() {
        setTitle("Administrador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(50, 175, 900, 800);
        setVisible(true);

        
        productos.setLayout(null);
        clientes.setLayout(null);
        vendedores.setLayout(null);
        
        
        sucursales_vistas sv = new sucursales_vistas();
        sv.ejecutar();
        
        Productos_vistas pro = new Productos_vistas();
        pro.ejecutar();
        
        Clientes_vistas client = new Clientes_vistas();
        client.ejecutar();
        
        Vendedores_vistas vendor = new Vendedores_vistas();
        vendor.ejecutar();

        
        
        
        
        pestañas.addTab("Sucursales", sv.sucursales);
        pestañas.addTab("Productos", pro.productos);
        pestañas.addTab("Clientes", client.clientes);
        pestañas.addTab("Vendedores", vendor.vendedores);
        
        add(pestañas);
        

    }

    public void ejecutar() {

        inicio();
    }

    public static void main(String[] args) {
        administrador ad = new administrador();
        ad.ejecutar();
    }

}