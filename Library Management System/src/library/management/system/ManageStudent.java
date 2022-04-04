package library.management.system;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java .awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ManageStudent implements ActionListener,MenuListener{
    Connection con;
    Statement stmt;
    
    static String[] userType = {"User", "Y"};
    JFrame frame = new JFrame();
    JMenuBar menuBar;
    JMenu show, insert, update, delete;
    JTable result_container;
    DefaultTableModel tableModel;
    JScrollPane scroll_result;
    JLabel newid, newbatch, newname, newdept, newmobile, newbookissued, idorname;
    JTextField input_new_id, input_new_name, input_new_mobile;
    JComboBox department;
    JSpinner input_total_book_issued, batch_from, batch_to;
    SpinnerModel book_issued_values, batch_values_from, batch_values_to;
    JButton insert_new, delete_selected, show_result, fill_details, update_details;
    ManageStudent()
    {
        newid = new JLabel("ID: ");
        newbatch = new JLabel("Batch: ");
        newname = new JLabel("Name: ");
        newdept = new JLabel("Dept: ");
        newmobile = new JLabel("Mobile: ");
        newbookissued= new JLabel("Currently issued: ");
        idorname = new JLabel("ID/Name: ");
        newid.setFont(new Font("Ariel", Font.BOLD, 14));
        idorname.setFont(new Font("Ariel", Font.BOLD, 14));
        newbatch.setFont(new Font("Ariel", Font.BOLD, 14));
        newname.setFont(new Font("Ariel", Font.BOLD, 14));
        newdept.setFont(new Font("Ariel", Font.BOLD, 14));
        newmobile.setFont(new Font("Ariel", Font.BOLD, 14));
        newbookissued.setFont(new Font("Ariel", Font.BOLD, 14));
        String[] department_list = {"BCA", "MCA", "BSC", "BBA", "MBA", "BCOM", "BPharma", "MPharma"};
        department = new JComboBox(department_list);
        book_issued_values = new SpinnerNumberModel(0, 0, 3, 1);
        input_total_book_issued = new JSpinner(book_issued_values);
        batch_values_from = new SpinnerNumberModel(2022, 2000, 3000, 1);
        batch_values_to = new SpinnerNumberModel(2022, 2000, 3000, 1);
        batch_from = new JSpinner(batch_values_from);
        batch_to = new JSpinner(batch_values_to);
        input_new_id = new JTextField();
        input_new_name = new JTextField();
        input_new_mobile = new JTextField();
        input_new_id.setFont(new Font("Ariel", Font.BOLD, 14));
        input_new_name.setFont(new Font("Ariel", Font.BOLD, 14));
        input_new_mobile.setFont(new Font("Ariel", Font.BOLD, 14));
        department.setFont(new Font("Ariel", Font.BOLD, 14));
        input_total_book_issued.setFont(new Font("Ariel", Font.BOLD, 14));
        
        insert_new = new JButton("Insert");
        insert_new.setFont(new Font("Ariel", Font.BOLD, 14));
        update_details = new JButton("Update");
        update_details.setFont(new Font("Ariel", Font.BOLD, 14));
        delete_selected = new JButton("Delete Secleted");
        delete_selected.setFont(new Font("Ariel", Font.BOLD, 14));
        show_result = new JButton("Show");
        show_result.setFont(new Font("Ariel", Font.BOLD,14));  
        fill_details = new JButton("Show");
        fill_details.setFont(new Font("Ariel", Font.BOLD, 14));
        menuBar = new JMenuBar();
        
        show = new JMenu("Show");
        insert = new JMenu("Insert");
        update = new JMenu("Update");
        delete = new JMenu("Delete");
        
        show.addMenuListener(this);
        insert.addMenuListener(this);
        update.addMenuListener(this);
        delete.addMenuListener(this);
        show_result.addActionListener(this);
        fill_details.addActionListener(this);
        insert_new.addActionListener(this);
        update_details.addActionListener(this);
        delete_selected.addActionListener(this);
        
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Batch");
        tableModel.addColumn("Name");
        tableModel.addColumn("Dept");
        tableModel.addColumn("Mobile");
        tableModel.addColumn("No of issued book");
        result_container = new JTable(tableModel);
        result_container.getColumnModel().getColumn(1).setPreferredWidth(100);
        result_container.setFont(new Font("Ariel", Font.BOLD, 14));
        scroll_result = new JScrollPane (result_container);
        scroll_result.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        result_container.setDefaultEditor(Object.class, null);
        
        newid.setVisible(false);
        idorname.setVisible(true);
        newname.setVisible(false);
        newmobile.setVisible(false);
        newbatch.setVisible(false);
        newdept.setVisible(false);
        newbookissued.setVisible(false);
        input_new_id.setVisible(true);
        input_new_name.setVisible(false);
        input_new_mobile.setVisible(false);
        batch_from.setVisible(false);
        batch_to.setVisible(false);
        department.setVisible(false);
        input_total_book_issued.setVisible(false);
        insert_new.setVisible(false);
        delete_selected.setVisible(false);
        show_result.setVisible(true);
        fill_details.setVisible(false);
        update_details.setVisible(false);
          
        frame.getRootPane().setDefaultButton(show_result);
            
        tableModel.setRowCount(0);
        idorname.setBounds(310, 25, 100, 20);
        input_new_id.setBounds(450, 25, 150, 20);
        show_result.setBounds(400, 65, 150, 20);
        scroll_result.setBounds(25, 150, 930, 550);
            
        input_new_id.setText("");
           
        show.setSelected(true);
                
        menuBar.add(show);
        if(userType[1].equals("Y"))
        {
            menuBar.add(insert);
            menuBar.add(update);
            menuBar.add(delete);
        }
        frame.add(scroll_result);
        frame.add(newid);
        frame.add(idorname);
        frame.add(newname);
        frame.add(newmobile);
        frame.add(newbatch);
        frame.add(newdept);
        frame.add(newbookissued);
        frame.add(input_new_id);
        frame.add(input_new_name);
        frame.add(input_new_mobile);
        frame.add(batch_from);
        frame.add(batch_to);
        frame.add(department);
        frame.add(input_total_book_issued);
        frame.add(insert_new);
        frame.add(delete_selected);
        frame.add(show_result);
        frame.add(fill_details);
        frame.add(update_details);
        
        frame.setJMenuBar(menuBar);
        //Connecting to database
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "root");
            stmt = con.createStatement();
        }
        catch(SQLException e){}
        
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HomePage.main(userType);
                frame.dispose();
            }
        });
        frame.setLayout(null);
        frame.setTitle("Library Management - Manage Student");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == show_result)
        {
            tableModel.setRowCount(0);
            String IDorName = input_new_id.getText();
            if(IDorName.contains("\"") || IDorName.contains("\'") || IDorName.contains("-") || IDorName.contains("#") || IDorName.contains(";") || IDorName.contains("&") || IDorName.contains("^") || IDorName.contains("(") || IDorName.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in ID");
                input_new_id.setText("");
            }
            else
            {
                if(!IDorName.isEmpty())
                {
                    try
                    {
                        int id = Integer.parseInt(IDorName);
                        ResultSet rs = stmt.executeQuery("Select * from student_details where ID like '" + id +"%'");
                        while(rs.next())
                        {
                             tableModel.insertRow(tableModel.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
                        }
                    }
                    catch(NumberFormatException e2)
                    {
                        try
                    {
                        ResultSet rs = stmt.executeQuery("Select * from student_details where Name like '" + IDorName +"%' order by id");
                        while(rs.next())
                        {
                            tableModel.insertRow(tableModel.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
                        }
                    }
                    catch(SQLException e1){}
                    }
                    catch(SQLException e1){}
            }
                else
                    {
                        printFullTable();
                    }
            }
        }
        
        else if(e.getSource() == insert_new)
        {
            String ID = input_new_id.getText();
            String temp = input_new_name.getText();
            if(temp.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            String Name_1 = temp.substring(0, 1);
            Name_1 = Name_1.toUpperCase();
            String Name_2 = temp.substring(1);
            String Name = Name_1 + Name_2;
            String Mobile = input_new_mobile.getText();
            int From = (Integer) batch_from.getValue();
            int To = (Integer) batch_to.getValue();
            String Batch = "";
            if(From < To)
            {
                Batch = String.valueOf(From) + "-" + String.valueOf(To);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Select valid batch (From Year) - (To Year)");
            }
            int TotalBookIssued = (Integer) input_total_book_issued.getValue();
            String Department = (String) department.getSelectedItem();
            int count = 0;
            if(ID.contains("\"") || ID.contains("\'") || ID.contains("-") || ID.contains("#") || ID.contains(";") || ID.contains("&") || ID.contains("^") || ID.contains("(") || ID.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in ID");
            }
            else if(Name.contains("\"") || Name.contains("\'") || Name.contains("-") || Name.contains("#") || Name.contains(";") || Name.contains("&") || Name.contains("^") || Name.contains("(") || Name.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Name");
            }
            else if(Mobile.contains("\"") || Mobile.contains("\'") || Mobile.contains("-") || Mobile.contains("#") || Mobile.contains(";") || Mobile.contains("&") || Mobile.contains("^") || Mobile.contains("(") || Mobile.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Mobile");
            }
            else if(Mobile.length() != 10 && (Mobile.startsWith("6") || Mobile.startsWith("7") || Mobile.startsWith("8") || Mobile.startsWith("9")))
            {
                JOptionPane.showMessageDialog(null, "Mobile number must be 10 long \nMobile number must start from 6, 7, 8 or 9");
            }
            
            if(!ID.isEmpty() && !Name.isEmpty() && !Mobile.isEmpty() && !Batch.isEmpty())
            {
                try
                {
                    int id = Integer.parseInt(ID);
                    count = stmt.executeUpdate("Insert into student_details values(" + id +", '" + Batch + "', '" + Name + "', '" + Department + "', '" + Mobile + "', " + TotalBookIssued + ")");
                }
                catch(SQLException e1){
                System.out.print("Error");
                System.out.println(Batch);
                System.out.println(Department);
                System.out.println("" + TotalBookIssued);}
                catch(NumberFormatException e2)
                {
                    JOptionPane.showMessageDialog(null, "Enter valid ID");
                }
                
                if(count > 0)
                {
                    tableModel.insertRow(tableModel.getRowCount(), new Object[] {ID, Batch, Name, Department, Mobile, TotalBookIssued});
                    JOptionPane.showMessageDialog(null, "Record added Successfully");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            input_new_id.setText("");
            input_new_name.setText("");
            input_new_mobile.setText("");
            batch_from.setValue(2020);
            batch_to.setValue(2020);
            department.setSelectedIndex(0);
            input_total_book_issued.setValue(0);
        }
        else if(e.getSource() == fill_details)
        {
            String ID = input_new_id.getText();
            try
            {
                int id = Integer.parseInt(ID);
                ResultSet rs = stmt.executeQuery("Select * from student_details where id = " + id +"");
                while(rs.next())
                {
                    String temp = rs.getString(2);
                    int From = Integer.valueOf(temp.substring(0, 4));
                    int To = Integer.valueOf(temp.substring(5));
                    batch_from.setValue(From);
                    batch_to.setValue(To);
                    input_new_name.setText(rs.getString(3));
                    String Department = rs.getString(4);
                    department.setSelectedItem(Department);
                    input_new_mobile.setText(rs.getString(5));
                    int TotalIssued = rs.getInt(6);
                    input_total_book_issued.setValue(TotalIssued);
                }
            }
            catch(NumberFormatException|SQLException e1)
            {
                JOptionPane.showMessageDialog(null, "Enter valid id");
            }
        }
        else if(e.getSource() == update_details)
        {
            String ID = input_new_id.getText();
            String temp = input_new_name.getText();
            if(temp.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            String Name_1 = temp.substring(0, 1);
            Name_1 = Name_1.toUpperCase();
            String Name_2 = temp.substring(1);
            String Name = Name_1 + Name_2;
            int From = (Integer) batch_from.getValue();
            int To = (Integer) batch_to.getValue();
            String Batch = "";
            if(From < To)
            {
                Batch = String.valueOf(From) + "-" + String.valueOf(To);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Select valid batch (From Year) - (To Year)");
            }
            int TotalBookIssued = (Integer) input_total_book_issued.getValue();
            String Department = (String) department.getSelectedItem();
            String Mobile = input_new_mobile.getText();
            int count = 0;
            if(ID.contains("\"") || ID.contains("\'") || ID.contains("-") || ID.contains("#") || ID.contains(";") || ID.contains("&") || ID.contains("^") || ID.contains("(") || ID.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in ID");
            }
            else if(Name.contains("\"") || Name.contains("\'") || Name.contains("-") || Name.contains("#") || Name.contains(";") || Name.contains("&") || Name.contains("^") || Name.contains("(") || Name.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Name");
            }
            else if(Mobile.contains("\"") || Mobile.contains("\'") || Mobile.contains("-") || Mobile.contains("#") || Mobile.contains(";") || Mobile.contains("&") || Mobile.contains("^") || Mobile.contains("(") || Mobile.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Mobile");
            }
            else if(Mobile.length() != 10 && (Mobile.startsWith("6") || Mobile.startsWith("7") || Mobile.startsWith("8") || Mobile.startsWith("9")))
            {
                JOptionPane.showMessageDialog(null, "Mobile number must be 10 character long \nMobile number must start from 6, 7, 8 or 9");
            }
            
            if(!ID.isEmpty() && !Name.isEmpty() && !Batch.isEmpty())
            {
                try
                {
                    int id = Integer.parseInt(ID);
                    count = stmt.executeUpdate("Update student_details set Name = '" + Name + "', Batch = '" + Batch + "', Dept = '" + Department + "', Mobile = '" + Mobile +"', No_of_book_issued = " + TotalBookIssued + " where ID = " + id + "");
                }
                catch(SQLException e1){}
                catch(NumberFormatException e2)
                {
                    JOptionPane.showMessageDialog(null, "Enter valid ID");
                }
                
                if(count > 0)
                {
                    tableModel.insertRow(tableModel.getRowCount(), new Object[] {ID, Batch, Name, Department, Mobile, TotalBookIssued});
                    JOptionPane.showMessageDialog(null, "Record Updated Successfully");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            
            input_new_id.setText("");
            input_new_name.setText("");
            input_new_mobile.setText("");
        }
        else if(e.getSource() == delete_selected)
        {
            int[] index = result_container.getSelectedRows();
            for(int i = 0; i < index.length; i++)
            {
                try
                {
                    stmt.executeUpdate("Delete from student_details where ID = " + result_container.getValueAt(index[i], 0) + "");
                }
                catch(SQLException e2)
                {}
            }
            tableModel.setRowCount(0);
        }
    }
    
    @Override
    public void menuSelected(MenuEvent me){
        
        if(me.getSource() == show)
        {
            newid.setVisible(false);
            idorname.setVisible(true);
            newname.setVisible(false);
            newmobile.setVisible(false);
            newbatch.setVisible(false);
            newdept.setVisible(false);
            newbookissued.setVisible(false);
            input_new_id.setVisible(true);
            input_new_name.setVisible(false);
            input_new_mobile.setVisible(false);
            batch_from.setVisible(false);
            batch_to.setVisible(false);
            department.setVisible(false);
            input_total_book_issued.setVisible(false);
            insert_new.setVisible(false);
            delete_selected.setVisible(false);
            show_result.setVisible(true);
            fill_details.setVisible(false);
            update_details.setVisible(false);
            
            frame.getRootPane().setDefaultButton(show_result);
            
            tableModel.setRowCount(0);
            idorname.setBounds(310, 25, 100, 20);
            input_new_id.setBounds(450, 25, 150, 20);
            show_result.setBounds(400, 65, 150, 20);
            scroll_result.setBounds(25, 150, 930, 550);
            
            input_new_id.setText("");
            
            show.setSelected(true);
            insert.setSelected(false);
            update.setSelected(false);
            delete.setSelected(false);
        }
        
        else if(me.getSource() == insert)
        {
            tableModel.setRowCount(0);
            show.setSelected(false);
            insert.setSelected(true);
            update.setSelected(false);
            delete.setSelected(false);
            
            newid.setVisible(true);
            idorname.setVisible(false);
            newname.setVisible(true);
            newmobile.setVisible(true);
            newbatch.setVisible(true);
            newdept.setVisible(true);
            newbookissued.setVisible(true);
            input_new_id.setVisible(true);
            input_new_name.setVisible(true);
            input_new_mobile.setVisible(true);
            batch_from.setVisible(true);
            batch_to.setVisible(true);
            department.setVisible(true);
            input_total_book_issued.setVisible(true);
            show_result.setVisible(false);
            insert_new.setVisible(true);
            delete_selected.setVisible(false);
            fill_details.setVisible(false);
            update_details.setVisible(false);
            
            newid.setBounds(310, 15, 100, 20);
            newbatch.setBounds(310, 45, 100, 20);
            newname.setBounds(310, 75, 100, 20);
            newdept.setBounds(310, 105, 100, 20);
            newmobile.setBounds(310, 135, 100, 20);
            newbookissued.setBounds(310, 165, 130, 20);
        
            input_new_id.setBounds(450, 15, 150, 20);
            batch_from.setBounds(450, 45, 70, 20);
            batch_to.setBounds(530, 45, 70, 20);
            input_new_name.setBounds(450, 75, 150, 20);
            department.setBounds(450, 105, 150, 20);
            input_new_mobile.setBounds(450, 135, 150,20);
            input_total_book_issued.setBounds(450, 165, 150, 20);
            insert_new.setBounds(430, 205, 100, 25);
            scroll_result.setBounds(25, 260, 930, 450);
            
            frame.getRootPane().setDefaultButton(insert_new);
            
            input_new_id.setText("");
            input_new_name.setText("");
            input_new_mobile.setText("");
            batch_from.setValue(2020);
            batch_to.setValue(2020);
            department.setSelectedIndex(0);
            input_total_book_issued.setValue(0);
        
            input_new_id.grabFocus();
        }
        else if(me.getSource() == update)
        {   
            newid.setVisible(true);
            idorname.setVisible(false);
            newname.setVisible(true);
            newmobile.setVisible(true);
            newbatch.setVisible(true);
            newdept.setVisible(true);
            newbookissued.setVisible(true);
            input_new_id.setVisible(true);
            input_new_name.setVisible(true);
            input_new_mobile.setVisible(true);
            batch_from.setVisible(true);
            batch_to.setVisible(true);
            department.setVisible(true);
            input_total_book_issued.setVisible(true);
            show_result.setVisible(false);
            insert_new.setVisible(false);
            delete_selected.setVisible(false);
            fill_details.setVisible(true);
            update_details.setVisible(true);
            
            newid.setBounds(310, 15, 100, 20);
            newbatch.setBounds(310, 45, 100, 20);
            newname.setBounds(310, 75, 100, 20);
            newdept.setBounds(310, 105, 100, 20);
            newmobile.setBounds(310, 135, 100, 20);
            newbookissued.setBounds(310, 165, 130, 20);
        
            input_new_id.setBounds(450, 15, 150, 20);
            fill_details.setBounds(610, 15, 80, 20);
            batch_from.setBounds(450, 45, 70, 20);
            batch_to.setBounds(530, 45, 70, 20);
            input_new_name.setBounds(450, 75, 150, 20);
            department.setBounds(450, 105, 150, 20);
            input_new_mobile.setBounds(450, 135, 150,20);
            input_total_book_issued.setBounds(450, 165, 150, 20);
            update_details.setBounds(430, 205, 100, 25);
            scroll_result.setBounds(25, 260, 930, 450);
            tableModel.setRowCount(0);
            
            frame.getRootPane().setDefaultButton(update_details);
            
            input_new_id.setText("");
            input_new_name.setText("");
            input_new_mobile.setText("");
        
            input_new_id.grabFocus();
        
            show.setSelected(false);
            insert.setSelected(false);
            update.setSelected(true);
            delete.setSelected(false);
            
        }
        else if(me.getSource() == delete)
        {
            newid.setVisible(false);
            idorname.setVisible(true);
            newname.setVisible(false);
            newbatch.setVisible(false);
            batch_from.setVisible(false);
            batch_to.setVisible(false);
            newdept.setVisible(false);
            department.setVisible(false);
            newmobile.setVisible(false);
            input_new_id.setVisible(true);
            input_new_name.setVisible(false);
            input_new_mobile.setVisible(false);
            insert_new.setVisible(false);
            delete_selected.setVisible(true);
            show_result.setVisible(true);
            fill_details.setVisible(false);
            update_details.setVisible(false);
            
            frame.getRootPane().setDefaultButton(delete_selected);
            
            tableModel.setRowCount(0);
            idorname.setBounds(310, 25, 100, 20);
            input_new_id.setBounds(450, 25, 150, 20);
            show_result.setBounds(610, 25, 80, 20);
            delete_selected.setBounds(400, 65, 150, 40);
            scroll_result.setBounds(25, 150, 930, 550);
            
            input_new_id.setText("");
            
            show.setSelected(false);
            insert.setSelected(false);
            update.setSelected(false);
            delete.setSelected(true);
        }
    }

    @Override
    public void menuCanceled(MenuEvent e1) {}
    @Override    
    public void menuDeselected(MenuEvent e2) {}
    
    public void printFullTable(){
        
        try
            {
                 ResultSet rs = stmt.executeQuery("Select * from student_details order by ID");
                 while(rs.next())
                 {
                     tableModel.insertRow(tableModel.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
                 }
            }
            catch(SQLException e)
            {
                
            }
    }
    
    public static void main(String[] args) {
        
//        userType[0] = args[0];
//        userType[1] = args[1];
        new ManageStudent();
        
    }
}
