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
import java.util.Calendar;

public class ManageBook implements ActionListener,MenuListener{
    Connection con;
    Statement stmt;
    
    static String[] userType = {"User", "Y"};
    JFrame frame = new JFrame();
    JMenuBar menuBar;
    JMenu show, insert, update, delete;
    JTable result_container;
    DefaultTableModel tableModel;
    JScrollPane scroll_result;
    JLabel newisbn, newtitle, newpublicationyear, newauthor, newcoursename, newtotalcopies, newcopiesavail, newshelfid, newshelflayer, isbntitleauthor;
    JTextField input_new_isbn, input_new_title, input_new_author, input_total_copies, input_total_copies_avail, input_new_shelf_id, input_new_shelf_layer;
    JComboBox course_name;
    JSpinner publication_year;
    SpinnerModel publication_year_values;
    JButton insert_new, delete_selected, show_result, fill_details, update_details;
    ManageBook()
    {
        newisbn = new JLabel("ISBN: ");
        newtitle = new JLabel("Title: ");
        newpublicationyear = new JLabel("Publication Year: ");
        newauthor = new JLabel("Author: ");
        newcoursename = new JLabel("Course: ");
        newtotalcopies = new JLabel("Total Copies: ");
        newcopiesavail = new JLabel("Copies Available: ");
        newshelfid = new JLabel("Shelf ID: ");
        newshelflayer = new JLabel("Shelf Layer: ");
        isbntitleauthor = new JLabel("ISBN/Title/Author: ");
        newisbn.setFont(new Font("Ariel", Font.BOLD, 14));
        newtitle.setFont(new Font("Ariel", Font.BOLD, 14));
        newpublicationyear.setFont(new Font("Ariel", Font.BOLD, 14));
        newauthor.setFont(new Font("Ariel", Font.BOLD, 14));
        newcoursename.setFont(new Font("Ariel", Font.BOLD, 14));
        newtotalcopies.setFont(new Font("Ariel", Font.BOLD, 14));
        newcopiesavail.setFont(new Font("Ariel", Font.BOLD, 14));
        newshelfid.setFont(new Font("Ariel", Font.BOLD, 14));
        newshelflayer.setFont(new Font("Ariel", Font.BOLD, 14));
        isbntitleauthor.setFont(new Font("Ariel", Font.BOLD, 14));
        String[] department_list = {"BCA", "MCA", "BSC", "BBA", "MBA", "BCOM", "BPharma", "MPharma"};
        course_name = new JComboBox(department_list);
        course_name.setFont(new Font("Ariel", Font.BOLD, 14));
        publication_year_values = new SpinnerNumberModel(2010, 2000, Calendar.getInstance().get(Calendar.YEAR), 1);
        publication_year = new JSpinner(publication_year_values);
        publication_year.setFont(new Font("Ariel", Font.BOLD, 14));
        input_new_isbn = new JTextField();
        input_new_title = new JTextField();
        input_new_author = new JTextField();
        input_total_copies = new JTextField();
        input_total_copies_avail = new JTextField();
        input_new_shelf_id = new JTextField();
        input_new_shelf_layer = new JTextField();
        input_new_isbn.setFont(new Font("Ariel", Font.BOLD, 14));
        input_new_title.setFont(new Font("Ariel", Font.BOLD, 14));
        input_new_author.setFont(new Font("Ariel", Font.BOLD, 14));
        input_total_copies.setFont(new Font("Ariel", Font.BOLD, 14));
        input_total_copies_avail.setFont(new Font("Ariel", Font.BOLD, 14));
        input_new_shelf_id.setFont(new Font("Ariel", Font.BOLD, 14));
        input_new_shelf_layer.setFont(new Font("Ariel", Font.BOLD, 14));
        
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
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Title");
        tableModel.addColumn("Publication Year");
        tableModel.addColumn("Author");
        tableModel.addColumn("Course");
        tableModel.addColumn("Total Copies");
        tableModel.addColumn("Available Copies");
        tableModel.addColumn("Shelf ID");
        tableModel.addColumn("Shelf Layer");
        result_container = new JTable(tableModel);
        result_container.getColumnModel().getColumn(1).setPreferredWidth(100);
        result_container.setFont(new Font("Ariel", Font.BOLD, 14));
        scroll_result = new JScrollPane (result_container);
        scroll_result.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        result_container.setDefaultEditor(Object.class, null);
        
        newisbn.setVisible(false);
        isbntitleauthor.setVisible(true);
        newtitle.setVisible(false);
        newpublicationyear.setVisible(false);
        newauthor.setVisible(false);
        newcoursename.setVisible(false);
        newtotalcopies.setVisible(false);
        newcopiesavail.setVisible(false);
        newshelfid.setVisible(false);
        newshelflayer.setVisible(false);
        input_new_isbn.setVisible(true);
        input_new_title.setVisible(false);
        publication_year.setVisible(false);
        input_new_author.setVisible(false);
        course_name.setVisible(false);
        input_total_copies.setVisible(false);
        input_total_copies_avail.setVisible(false);
        input_new_shelf_id.setVisible(false);
        input_new_shelf_layer.setVisible(false);
        insert_new.setVisible(false);
        delete_selected.setVisible(false);
        show_result.setVisible(true);
        fill_details.setVisible(false);
        update_details.setVisible(false);
          
        frame.getRootPane().setDefaultButton(show_result);
            
        tableModel.setRowCount(0);
        isbntitleauthor.setBounds(310, 25, 100, 20);
        input_new_isbn.setBounds(450, 25, 150, 20);
        show_result.setBounds(400, 65, 150, 20);
        scroll_result.setBounds(25, 150, 930, 550);
            
        input_new_isbn.setText("");
           
        show.setSelected(true);
                
        menuBar.add(show);
        if(userType[1].equals("Y"))
        {
            menuBar.add(insert);
            menuBar.add(update);
            menuBar.add(delete);
        }
        frame.add(scroll_result);
        frame.add(newisbn);
        frame.add(isbntitleauthor);
        frame.add(newtitle);
        frame.add(newpublicationyear);
        frame.add(newauthor);
        frame.add(newcoursename);
        frame.add(newtotalcopies);
        frame.add(newcopiesavail);
        frame.add(newshelfid);
        frame.add(newshelflayer);
        frame.add(input_new_isbn);
        frame.add(input_new_title);
        frame.add(publication_year);
        frame.add(input_new_author);
        frame.add(course_name);
        frame.add(input_total_copies);
        frame.add(input_total_copies_avail);
        frame.add(input_new_shelf_id);
        frame.add(input_new_shelf_layer);
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
        
        frame.setSize(1200,800);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HomePage.main(userType);
                frame.dispose();
                try
                {
                    con.close();
                }
                catch(SQLException e1){}
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
            String ISBNorTitleorAuthor = input_new_isbn.getText();
            if(ISBNorTitleorAuthor.contains("\"") || ISBNorTitleorAuthor.contains("\'") || ISBNorTitleorAuthor.contains("-") || ISBNorTitleorAuthor.contains("#") || ISBNorTitleorAuthor.contains(";") || ISBNorTitleorAuthor.contains("&") || ISBNorTitleorAuthor.contains("^") || ISBNorTitleorAuthor.contains("(") || ISBNorTitleorAuthor.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Enter valid ISBN or Title or Name");
                input_new_isbn.setText("");
            }
            else
            {
                if(!ISBNorTitleorAuthor.isEmpty())
                {
                    try
                    {
                        int id = Integer.parseInt(ISBNorTitleorAuthor);
                        ResultSet rs = stmt.executeQuery("Select * from student_details where ISBN like '" + id +"%'");
                        while(rs.next())
                        {
                             tableModel.insertRow(tableModel.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)});
                        }
                    }
                    catch(NumberFormatException e2)
                    {
                        try
                    {
                        ResultSet rs = stmt.executeQuery("Select * from student_details where Title like '" + ISBNorTitleorAuthor +"%' or Author like '" + ISBNorTitleorAuthor + "' order by ISBN");
                        while(rs.next())
                        {
                            tableModel.insertRow(tableModel.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)});
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
            int ISBN;
            try
            {
                ISBN = Integer.parseInt(input_new_isbn.getText());
                if(ISBN <= 0)
                {
                    JOptionPane.showMessageDialog(null, "ISBN numebr must be greater than 0");
                    ISBN = 0;
                }
            }
            catch(NumberFormatException isbn)
            {
                JOptionPane.showMessageDialog(null, "Enter valid ISBN number");
                ISBN = 0;
            }
            String temp_title = input_new_title.getText();
            if(temp_title.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            String Title_1 = temp_title.substring(0, 1);
            Title_1 = Title_1.toUpperCase();
            String Title_2 = temp_title.substring(1);
            String Title = Title_1 + Title_2;
            int Publication_Year = (Integer) publication_year.getValue();
            String temp_author = input_new_author.getText();
            if(temp_author.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Author Field can't be empty");
            }
            String Author_1 = temp_author.substring(0,1);
            Author_1 = Author_1.toUpperCase();
            String Author_2 = temp_author.substring(1);
            String Author = Author_1 + Author_2;
            String Course = (String) course_name.getSelectedItem();
            int Total_Copies, Total_Copies_Avail;
            try
            {
                Total_Copies = Integer.parseInt(input_total_copies.getText());
                Total_Copies_Avail = Integer.parseInt(input_total_copies_avail.getText());
                if((Total_Copies < 0 || Total_Copies_Avail < 0) && (Total_Copies_Avail > Total_Copies))
                {
                    JOptionPane.showMessageDialog(null, "Entered number must be greater than or equal to 0 \nTotal Copies must be greater than Available Copies");
                    Total_Copies = 0;
                    Total_Copies_Avail = 0;
                }
            }
            catch(NumberFormatException num)
            {
                JOptionPane.showMessageDialog(null, "Enter only numeric values in Total Copies and Available Copies");
                Total_Copies = 0;
                Total_Copies_Avail = 0;
            }
            String Shelf_ID = input_new_shelf_id.getText();
            String Shelf_Layer = input_new_shelf_layer.getText();
            int count = 0;
            if(Title.contains("\"") || Title.contains("\'") || Title.contains("-") || Title.contains("#") || Title.contains(";") || Title.contains("&") || Title.contains("^") || Title.contains("(") || Title.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Title");
            }
            else if(Author.contains("\"") || Author.contains("\'") || Author.contains("-") || Author.contains("#") || Author.contains(";") || Author.contains("&") || Author.contains("^") || Author.contains("(") || Author.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Author");
            }
            else if(Shelf_ID.contains("\"") || Shelf_ID.contains("\'") || Shelf_ID.contains("-") || Shelf_ID.contains("#") || Shelf_ID.contains(";") || Shelf_ID.contains("&") || Shelf_ID.contains("^") || Shelf_ID.contains("(") || Shelf_ID.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Shelf ID");
            }
            else if(Shelf_Layer.contains("\"") || Shelf_Layer.contains("\'") || Shelf_Layer.contains("-") || Shelf_Layer.contains("#") || Shelf_Layer.contains(";") || Shelf_Layer.contains("&") || Shelf_Layer.contains("^") || Shelf_Layer.contains("(") || Shelf_Layer.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Shelf_Layer");
            }
            
            if(ISBN != 0 && !Title.isEmpty() && !Author.isEmpty() && !Shelf_ID.isEmpty() && !Shelf_Layer.isEmpty() && Total_Copies != 0 && Total_Copies_Avail != 0)
            {
                try
                {
                    count = stmt.executeUpdate("Insert into book_details values(" + ISBN +", '" + Title + "', '" + Publication_Year + "', '" + Author + "', '" + Course + "', " + Total_Copies + ", " + Total_Copies_Avail + ", '" + Shelf_ID + "', '" + Shelf_Layer + "')");
                }
                catch(SQLException e1){
                System.out.print("Error");
                }
                
                if(count > 0)
                {
                    tableModel.insertRow(tableModel.getRowCount(), new Object[] {ISBN, Title, Publication_Year, Author, Course, Total_Copies, Total_Copies_Avail, Shelf_ID, Shelf_Layer});
                    JOptionPane.showMessageDialog(null, "Record added Successfully");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            input_new_isbn.setText("");
            input_new_title.setText("");
            publication_year.setValue(Calendar.getInstance().get(Calendar.YEAR));
            input_new_author.setText("");
            course_name.setSelectedIndex(0);
            input_total_copies.setText("");
            input_total_copies_avail.setText("");
            input_new_shelf_id.setText("");
            input_new_shelf_layer.setText("");
        }
        else if(e.getSource() == fill_details)
        {
            String ISBN = input_new_isbn.getText();
            try
            {
                int isbn = Integer.parseInt(ISBN);
                ResultSet rs = stmt.executeQuery("Select * from book_details where id = " + isbn +"");
                while(rs.next())
                {
                    input_new_title.setText(rs.getString(2));
                    publication_year.setValue(rs.getString(3));
                    input_new_author.setText(rs.getString(4));
                    course_name.setSelectedItem(rs.getString(5));
                    input_total_copies.setText(String.valueOf(rs.getInt(6)));
                    input_total_copies_avail.setText(String.valueOf(rs.getString(7)));
                    input_new_shelf_id.setText(rs.getString(8));
                    input_new_shelf_layer.setText(rs.getString(9));
                }
            }
            catch(NumberFormatException|SQLException e1)
            {
                JOptionPane.showMessageDialog(null, "Enter valid ISBN");
            }
        }
        else if(e.getSource() == update_details)
        {
            int ISBN;
            try
            {
                ISBN = Integer.parseInt(input_new_isbn.getText());
                if(ISBN <= 0)
                {
                    JOptionPane.showMessageDialog(null, "ISBN numebr must be greater than 0");
                    ISBN = 0;
                }
            }
            catch(NumberFormatException isbn)
            {
                JOptionPane.showMessageDialog(null, "Enter valid ISBN number");
                ISBN = 0;
            }
            String temp_title = input_new_title.getText();
            if(temp_title.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            String Title_1 = temp_title.substring(0, 1);
            Title_1 = Title_1.toUpperCase();
            String Title_2 = temp_title.substring(1);
            String Title = Title_1 + Title_2;
            int Publication_Year = (Integer) publication_year.getValue();
            String temp_author = input_new_author.getText();
            if(temp_author.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Author Field can't be empty");
            }
            String Author_1 = temp_author.substring(0,1);
            Author_1 = Author_1.toUpperCase();
            String Author_2 = temp_author.substring(1);
            String Author = Author_1 + Author_2;
            String Course = (String) course_name.getSelectedItem();
            int Total_Copies, Total_Copies_Avail;
            try
            {
                Total_Copies = Integer.parseInt(input_total_copies.getText());
                Total_Copies_Avail = Integer.parseInt(input_total_copies_avail.getText());
                if((Total_Copies < 0 || Total_Copies_Avail < 0) && (Total_Copies_Avail > Total_Copies))
                {
                    JOptionPane.showMessageDialog(null, "Entered number must be greater than or equal to 0 \nTotal Copies must be greater than Available Copies");
                    Total_Copies = 0;
                    Total_Copies_Avail = 0;
                }
            }
            catch(NumberFormatException num)
            {
                JOptionPane.showMessageDialog(null, "Enter only numeric values in Total Copies and Available Copies");
                Total_Copies = 0;
                Total_Copies_Avail = 0;
            }
            String Shelf_ID = input_new_shelf_id.getText();
            String Shelf_Layer = input_new_shelf_layer.getText();
            int count = 0;
            if(Title.contains("\"") || Title.contains("\'") || Title.contains("-") || Title.contains("#") || Title.contains(";") || Title.contains("&") || Title.contains("^") || Title.contains("(") || Title.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Title");
            }
            else if(Author.contains("\"") || Author.contains("\'") || Author.contains("-") || Author.contains("#") || Author.contains(";") || Author.contains("&") || Author.contains("^") || Author.contains("(") || Author.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Author");
            }
            else if(Shelf_ID.contains("\"") || Shelf_ID.contains("\'") || Shelf_ID.contains("-") || Shelf_ID.contains("#") || Shelf_ID.contains(";") || Shelf_ID.contains("&") || Shelf_ID.contains("^") || Shelf_ID.contains("(") || Shelf_ID.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Shelf ID");
            }
            else if(Shelf_Layer.contains("\"") || Shelf_Layer.contains("\'") || Shelf_Layer.contains("-") || Shelf_Layer.contains("#") || Shelf_Layer.contains(";") || Shelf_Layer.contains("&") || Shelf_Layer.contains("^") || Shelf_Layer.contains("(") || Shelf_Layer.contains(")"))
            {
                JOptionPane.showMessageDialog(null, "Invalid Characters in Shelf_Layer");
            }
            
            if(ISBN != 0 && !Title.isEmpty() && !Author.isEmpty() && !Shelf_ID.isEmpty() && !Shelf_Layer.isEmpty() && Total_Copies != 0 && Total_Copies_Avail != 0)
            {
                try
                {
                    count = stmt.executeUpdate("Update book_details set Title = '" + Title + "', Publication_Year = '" + Publication_Year + "', Author = '" + Author + "', Course_Name = '" + Course +"', Total_Copies = " + Total_Copies + ", Total_Copies_Available = " + Total_Copies_Avail + ", Shelf_ID = '" + Shelf_ID + "', Shelf_Layer = '" + Shelf_Layer + "' where ISBN = " + ISBN + "");
                }
                catch(SQLException e1){}
                
                
                if(count > 0)
                {
                    tableModel.insertRow(tableModel.getRowCount(), new Object[] {ISBN, Title, Publication_Year, Author, Course, Total_Copies, Total_Copies_Avail, Shelf_ID, Shelf_Layer});
                    JOptionPane.showMessageDialog(null, "Record Updated Successfully");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Fields can not be empty");
            }
            
            input_new_isbn.setText("");
            input_new_title.setText("");
            publication_year.setValue(Calendar.getInstance().get(Calendar.YEAR));
            input_new_author.setText("");
            course_name.setSelectedIndex(0);
            input_total_copies.setText("");
            input_total_copies_avail.setText("");
            input_new_shelf_id.setText("");
            input_new_shelf_layer.setText("");
        }
        else if(e.getSource() == delete_selected)
        {
            int[] index = result_container.getSelectedRows();
            for(int i = 0; i < index.length; i++)
            {
                try
                {
                    stmt.executeUpdate("Delete from book_details where ISBN = " + result_container.getValueAt(index[i], 0) + "");
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
            newisbn.setVisible(false);
            isbntitleauthor.setVisible(true);
            newtitle.setVisible(false);
            newpublicationyear.setVisible(false);
            newauthor.setVisible(false);
            newcoursename.setVisible(false);
            newtotalcopies.setVisible(false);
            newcopiesavail.setVisible(false);
            newshelfid.setVisible(false);
            newshelflayer.setVisible(false);
            input_new_isbn.setVisible(true);
            input_new_title.setVisible(false);
            publication_year.setVisible(false);
            input_new_author.setVisible(false);
            course_name.setVisible(false);
            input_total_copies.setVisible(false);
            input_total_copies_avail.setVisible(false);
            input_new_shelf_id.setVisible(false);
            input_new_shelf_layer.setVisible(false);
            insert_new.setVisible(false);
            delete_selected.setVisible(false);
            show_result.setVisible(true);
            fill_details.setVisible(false);
            update_details.setVisible(false);
            
            frame.getRootPane().setDefaultButton(show_result);
            
            tableModel.setRowCount(0);
            isbntitleauthor.setBounds(450, 25, 150, 20);
            input_new_isbn.setBounds(590, 25, 150, 20);
            show_result.setBounds(530, 65, 150, 20);
            scroll_result.setBounds(25, 150, 1125, 550);
            
            input_new_isbn.setText("");
            
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
            
            newisbn.setVisible(true);
            isbntitleauthor.setVisible(false);
            newtitle.setVisible(true);
            newpublicationyear.setVisible(true);
            newauthor.setVisible(true);
            newcoursename.setVisible(true);
            newtotalcopies.setVisible(true);
            newcopiesavail.setVisible(true);
            newshelfid.setVisible(true);
            newshelflayer.setVisible(true);
            input_new_isbn.setVisible(true);
            input_new_title.setVisible(true);
            publication_year.setVisible(true);
            input_new_author.setVisible(true);
            course_name.setVisible(true);
            input_total_copies.setVisible(true);
            input_total_copies_avail.setVisible(true);
            input_new_shelf_id.setVisible(true);
            input_new_shelf_layer.setVisible(true);
            insert_new.setVisible(true);
            delete_selected.setVisible(false);
            show_result.setVisible(false);
            fill_details.setVisible(false);
            update_details.setVisible(false);
            
            newisbn.setBounds(310, 15, 100, 20);
            newtitle.setBounds(310, 45, 100, 20);
            newpublicationyear.setBounds(310, 75, 100, 20);
            newauthor.setBounds(310, 105, 100, 20);
            newcoursename.setBounds(310, 135, 100, 20);
            newtotalcopies.setBounds(310, 165, 130, 20);
            newcopiesavail.setBounds(310, 195, 130, 20);
            newshelfid.setBounds(310, 225, 130, 20);
            newshelflayer.setBounds(310, 255, 130, 20);
        
            input_new_isbn.setBounds(450, 15, 150, 20);
            input_new_title.setBounds(450, 45, 150, 20);
            publication_year.setBounds(450, 75, 150, 20);
            input_new_author.setBounds(450, 105, 150, 20);
            course_name.setBounds(450, 135, 150,20);
            input_total_copies.setBounds(450, 165, 150, 20);
            input_total_copies_avail.setBounds(450, 195, 150, 20);
            input_new_shelf_id.setBounds(450, 225, 150, 20);
            input_new_shelf_layer.setBounds(450, 255, 150, 20);
            insert_new.setBounds(430, 285, 100, 25);
            scroll_result.setBounds(25, 315, 1125, 450);
            
            frame.getRootPane().setDefaultButton(insert_new);
            
            input_new_isbn.setText("");
            input_new_title.setText("");
            publication_year.setValue(Calendar.getInstance().get(Calendar.YEAR));
            input_new_author.setText("");
            course_name.setSelectedIndex(0);
            input_total_copies.setText("");
            input_total_copies_avail.setText("");
            input_new_shelf_id.setText("");
            input_new_shelf_layer.setText("");
        
            input_new_isbn.grabFocus();
        }
        else if(me.getSource() == update)
        {
            tableModel.setRowCount(0);
            show.setSelected(false);
            insert.setSelected(false);
            update.setSelected(true);
            delete.setSelected(false);
            
            newisbn.setVisible(true);
            isbntitleauthor.setVisible(false);
            newtitle.setVisible(true);
            newpublicationyear.setVisible(true);
            newauthor.setVisible(true);
            newcoursename.setVisible(true);
            newtotalcopies.setVisible(true);
            newcopiesavail.setVisible(true);
            newshelfid.setVisible(true);
            newshelflayer.setVisible(true);
            input_new_isbn.setVisible(true);
            input_new_title.setVisible(true);
            publication_year.setVisible(true);
            input_new_author.setVisible(true);
            course_name.setVisible(true);
            input_total_copies.setVisible(true);
            input_total_copies_avail.setVisible(true);
            input_new_shelf_id.setVisible(true);
            input_new_shelf_layer.setVisible(true);
            insert_new.setVisible(false);
            delete_selected.setVisible(false);
            show_result.setVisible(false);
            fill_details.setVisible(true);
            update_details.setVisible(true);
            
            newisbn.setBounds(310, 15, 100, 20);
            newtitle.setBounds(310, 45, 100, 20);
            newpublicationyear.setBounds(310, 75, 100, 20);
            newauthor.setBounds(310, 105, 100, 20);
            newcoursename.setBounds(310, 135, 100, 20);
            newtotalcopies.setBounds(310, 165, 130, 20);
            newcopiesavail.setBounds(310, 195, 130, 20);
            newshelfid.setBounds(310, 225, 130, 20);
            newshelflayer.setBounds(310, 255, 130, 20);
        
            input_new_isbn.setBounds(450, 15, 150, 20);
            input_new_title.setBounds(450, 45, 150, 20);
            publication_year.setBounds(450, 75, 150, 20);
            input_new_author.setBounds(450, 105, 150, 20);
            course_name.setBounds(450, 135, 150,20);
            input_total_copies.setBounds(450, 165, 150, 20);
            input_total_copies_avail.setBounds(450, 195, 150, 20);
            input_new_shelf_id.setBounds(450, 225, 150, 20);
            input_new_shelf_layer.setBounds(450, 255, 150, 20);
            insert_new.setBounds(430, 285, 100, 25);
            scroll_result.setBounds(25, 315, 1125, 450);
            
            frame.getRootPane().setDefaultButton(update_details);
            
            input_new_isbn.setText("");
            input_new_title.setText("");
            publication_year.setValue(Calendar.getInstance().get(Calendar.YEAR));
            input_new_author.setText("");
            course_name.setSelectedIndex(0);
            input_total_copies.setText("");
            input_total_copies_avail.setText("");
            input_new_shelf_id.setText("");
            input_new_shelf_layer.setText("");
        
            input_new_isbn.grabFocus();
            
        }
        else if(me.getSource() == delete)
        {
            newisbn.setVisible(false);
            isbntitleauthor.setVisible(true);
            newtitle.setVisible(false);
            newpublicationyear.setVisible(false);
            newauthor.setVisible(false);
            newcoursename.setVisible(false);
            newtotalcopies.setVisible(false);
            newcopiesavail.setVisible(false);
            newshelfid.setVisible(false);
            newshelflayer.setVisible(false);
            input_new_isbn.setVisible(true);
            input_new_title.setVisible(false);
            publication_year.setVisible(false);
            input_new_author.setVisible(false);
            course_name.setVisible(false);
            input_total_copies.setVisible(false);
            input_total_copies_avail.setVisible(false);
            input_new_shelf_id.setVisible(false);
            input_new_shelf_layer.setVisible(false);
            insert_new.setVisible(false);
            delete_selected.setVisible(false);
            show_result.setVisible(true);
            fill_details.setVisible(false);
            update_details.setVisible(false);
            
            frame.getRootPane().setDefaultButton(delete_selected);
            
            tableModel.setRowCount(0);
            isbntitleauthor.setBounds(310, 25, 100, 20);
            input_new_isbn.setBounds(450, 25, 150, 20);
            show_result.setBounds(400, 65, 150, 20);
            scroll_result.setBounds(25, 150, 1125, 550);
            
            input_new_isbn.setText("");
            
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
                 ResultSet rs = stmt.executeQuery("Select * from book_details order by ISBN");
                 while(rs.next())
                 {
                     tableModel.insertRow(tableModel.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)});
                 }
            }
            catch(SQLException e)
            {
                
            }
    }
    
    public static void main(String[] args) {
        
//        userType[0] = args[0];
//        userType[1] = args[1];
        new ManageBook();
        
    }
}
